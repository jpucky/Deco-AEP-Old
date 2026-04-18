// $Log: Recherche.java,v $
// Revision 1.10  2016/01/12 21:26:35  tw
// CR 3956: Interner Umbau: Lucene-Felder. Rueckbau d. Textfeld-Id Verlaengerung.
//
// Revision 1.9  2016/01/11 22:50:18  tw
// CR 3956: Interner Umbau: Lucene-Felder.
//
// Revision 1.8  2015/04/17 11:31:56  tw
// Zuruf v. Martin: Scan-Dokument-Suchfeld soll Case-insensitive sein!
//
// Revision 1.7  2015/01/23 17:14:47  tw
// Kommentare ergaenzt.
//
// Revision 1.6  2014/11/25 13:50:39  tw
// Bugfix: Ticket 3939. Superadmins duerfen ALLE Belege sehen.
//
// Revision 1.5  2014/11/24 14:06:54  tw
// Bugfix, Scanbelegfilter.
//
// Revision 1.4  2014/11/23 21:53:43  tw
// https://team.decodetron.de:10443/index.php?c=task&a=view_task&id=3939
//
// Revision 1.3  2014/10/03 15:06:13  tw
// Logfileausgabe.
//
// Revision 1.2  2014/10/02 12:36:33  tw
// Lieferantenname Textfeldanbindung.
//
// Revision 1.1  2014/10/01 11:24:31  tw
// Absplittung, Scanindex vom restlichen Index. Testanbindung.
//
//

package de.decodetron.dao.recherche;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.util.BytesRef;

import de.decodetron.bo.FilterItem;
import de.decodetron.bo.FilterItemList;
import de.decodetron.bo.Section;
import de.decodetron.bo.User;
import de.decodetron.dao.user.UserDAOJDBC;
import de.decodetron.util.Const;

/**
 * Übersicht über die Verwendung der Lucene-Spalten:<br>
 * 
 * <pre>
 *  Verwendungstyp: LS,NB,GS,SR             | impdate | impoffset  |  belegart  |  datum  | belegnr      | kundennr      | seitenzahl | 
 *  Verwendungstyp: EK                      | impdate | impoffset  |  belegart  |  datum  | belegnr      | lieferantennr | seitenzahl | 
 *  Verwendungstyp: SCLF, SCRG, SCSD        | impdate | impoffset  |  belegart  |  datum  | belegnr      | lieferantennr | seitenzahl | bestellung | lieferantenname
 *  Verwendungstyp: SCRT                    | impdate | impoffset  |  belegart  |  datum  | belegnr (gpe)| kundennr (bga)| seitenzahl |
 * </pre>

 * @author Thomas Winter
 * @since 01.10.2014
 */
public class Recherche {

    private static Logger log = Logger.getLogger(Recherche.class);
    private static HashMap<String, String> COLMAPPER = new HashMap<String, String>();

    public Recherche() {
        // // Mapping von FilterIdentifier auf Lucene-Spaltenbezeichner
        // COLMAPPER.put("KundenNr", "kundennummer");
        // COLMAPPER.put("LieferantNr", "kundennummer"); // es gibt hier keine lieferantennr!
        // COLMAPPER.put("*", "*"); // es gibt hier keine lieferantennr!

        // // //////////////////////////////////////
        // // /// Client-Textfelder
        // // /
        // COLMAPPER.put("txt-kdnr", "kundennummer");
        // COLMAPPER.put("txt-lfsnr", "belegnummer");
        // COLMAPPER.put("txt-blgdatevon", "datum");
        // COLMAPPER.put("txt-blgdatebis", "datum");
        // COLMAPPER.put("txt-scdoctype", "scdokument");
        // COLMAPPER.put("txt-lieferantname", "lieferantenname");

        // Mapping von FilterIdentifier auf Lucene-Spaltenbezeichner
        COLMAPPER.put("KundenNr", Const.LUC_KUNDENNUMMER);
        COLMAPPER.put("LieferantNr", Const.LUC_KUNDENNUMMER); // es gibt hier keine lieferantennr!
        COLMAPPER.put("*", "*"); // es gibt hier keine lieferantennr!

        // //////////////////////////////////////
        // /// Client-Textfelder
        // /
        COLMAPPER.put("txt-kdnr", Const.LUC_KUNDENNUMMER);
        COLMAPPER.put("txt-lfsnr", Const.LUC_BELEGNUMMER);
        //COLMAPPER.put("txt-lfsnr-scan", Const.LUC_BESTELLUNG);
        COLMAPPER.put("txt-blgdatevon", Const.LUC_IMPDATE);
        COLMAPPER.put("txt-blgdatebis", Const.LUC_IMPDATE);
        //COLMAPPER.put("txt-scdoctype", Const.LUC_BELEGNUMMER);
        COLMAPPER.put("txt-scdoctype", Const.LUC_BESTELLUNG);
        COLMAPPER.put("txt-lieferantname", Const.LUC_LIEFERANTNAME);
    }

    /**
     * Neue Query mit weniger Parametern. Die Text-Suchfelder stecken jetzt in der FilterItemList.
     * 
     * @param User
     *            user
     * @param List
     *            <String> keyblgart
     * @param List
     *            <Section> sections
     * @param List
     *            <String> filterList
     * @param FilterItemList
     *            listTxtFields
     * @return BooleanQuery
     */
    public BooleanQuery getQueryNew(User user, List<String> keyblgart, List<Section> sections, List<String> filterList,
            FilterItemList listTxtFields) {

        BooleanQuery bqNew = new BooleanQuery();

        // ///////////////////////////////////////////////////////////////////////////////
        // /// Verarbeitung der Text-Suchfelder
        // /
        List<FilterItem> item = listTxtFields.getFilterItems();
        for (Iterator<FilterItem> iterator = item.iterator(); iterator.hasNext();) {

            FilterItem filterItem = iterator.next();
            String filterIdent = getLuceneColName(filterItem.getFilterIdent());
            String filterText = filterItem.getFilterText();

            // 14.04.2015 Das Ding soll case-insensitive sein!
            // if("scdokument".equals(filterIdent)){
            if (Const.LUC_BELEGNUMMER.equals(filterIdent)) {
                filterText = filterText.toUpperCase();
            }

            if (Const.LUC_IMPDATE.equals(filterIdent)) {

                // //////////////////////////////////////
                // /// Das Datum wird gesondet behandelt. Vorsicht, wenn nur ein bis-datum angegeben
                // ist, existiert kein Eintrag in der FilterItemList! Dann erscheint bis hier in der
                // von - variablen.
                // /
                String fromDate = filterText;
                FilterItem fitem = iterator.hasNext() ? iterator.next() : null;
                String toDate = "";
                if (fitem != null && "txt-blgdatebis".equals(fitem.getFilterIdent())) {
                    toDate = fitem.getFilterText();
                } else {
                    // ///////////////////////////////////
                    // /// Nur Von - Feld und Text-Suchfelder
                    // /
                    if (fitem != null) {
                        bqNew.add(
                            new WildcardQuery(new Term(getLuceneColName(fitem.getFilterIdent()), fitem.getFilterText())),
                            BooleanClause.Occur.MUST);
                    }
                }

                if (!fromDate.isEmpty() && toDate.isEmpty() && "txt-blgdatevon".equals(filterItem.getFilterIdent())) {
                    // Von -
                    bqNew.add(new WildcardQuery(new Term(filterIdent, filterText)), BooleanClause.Occur.MUST);
                } else if (!fromDate.isEmpty() && !toDate.isEmpty()) {
                    // Von - Bis
                    Query query = new TermRangeQuery(Const.LUC_IMPDATE, new BytesRef(new String(fromDate)),
                            new BytesRef(new String(toDate)), true, true);
                    bqNew.add(query, BooleanClause.Occur.MUST);
                } else if (!fromDate.isEmpty() && toDate.isEmpty()) {
                    // - Bis
                    Query query = new TermRangeQuery(Const.LUC_IMPDATE, new BytesRef(new String("")), new BytesRef(
                            new String(fromDate)), true, true);
                    bqNew.add(query, BooleanClause.Occur.MUST);
                }

            } else {

                // ///////////////////////////////////
                // /// Text-Suchfelder
                // /
                if (filterText.length() > 0) {
                    bqNew.add(new WildcardQuery(new Term(filterIdent, filterText)), BooleanClause.Occur.MUST);
                }
            }
        }
        // /
        // /// Verarbeitung der Text-Suchfelder ENDE
        // ///////////////////////////////////////////////////////////////////////////////

        // Change Request:
        // https://team.decodetron.de:10443/index.php?c=task&a=view_task&id=3939
        // Kein Benutzer, ausser ein paar auserwählten, dürfen Scanbelege beginnend mit
        // LieferantenNr: 990* sehen.
        //
        if (isScanBeleg(keyblgart) && !is990erUser(user.getId().longValue()) && !user.getIsSuperAdmin()
        // && filterText.startsWith("990*")
        // && Const.LUC_KUNDENNUMMER.equals(filterIdent)
        ) {
            bqNew.add(new WildcardQuery(new Term(Const.LUC_KUNDENNUMMER, "990*")), BooleanClause.Occur.MUST_NOT);
        }

        if (isScanBeleg(keyblgart)) {
            // bestellung
        }

        // Belegarten
        if (keyblgart.size() > 0) {
            BooleanQuery subquery = new BooleanQuery();
            for (Iterator<String> iterator = keyblgart.iterator(); iterator.hasNext();) {
                String txtbelegart = iterator.next();
                subquery.add(new WildcardQuery(new Term(Const.LUC_BELEGART, txtbelegart)), BooleanClause.Occur.SHOULD);
            }
            bqNew.add(subquery, BooleanClause.Occur.MUST);
        }
        // /
        // ////
        // ////////////////////////////////////////////////////////////////////////////

        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // !!! !!! Benutzerspezifischer Datenfilter - Sicherheitsbereich !!!
        // !
        for (Iterator<Section> iterator = sections.iterator(); iterator.hasNext();) {

            Section section = iterator.next();
            String colName = getLuceneColName(section.getFilteridentifier());
            if ("*".equals(colName) || colName == null) {
                // Kein FilterIdentifier? Dann brauchen wir auch nicht nach Benutzer-FilterItems zu
                // gucken und überspringen diesen Teil hier.
                continue;
            }

            BooleanQuery subquery = new BooleanQuery();
            // List<String> filterList = LoginSession.get().getUser().getAllFilter();
            for (Iterator<String> iter = filterList.iterator(); iter.hasNext();) {
                String filterItem = iter.next();
                if (filterItem == null || filterItem.length() == 0) {
                    // Dann muss das Item ausgeschlossen werden => Filteritem null!
                    subquery.add(new WildcardQuery(new Term(colName, filterItem)), BooleanClause.Occur.MUST_NOT);
                } else {
                    subquery.add(new WildcardQuery(new Term(colName, filterItem)), BooleanClause.Occur.SHOULD);
                }
            }

            if (booleanClauseExisting(subquery, bqNew)) {
                continue;
            }

            bqNew.add(subquery, BooleanClause.Occur.MUST);
        }

        log.debug("Lucene-Query: " + bqNew);
        return bqNew;
    }

    /**
     * TODO: sectionKz muss aus DB kommen !
     * 
     * @param keyblgart
     * @return boolean
     */
    private boolean isScanBeleg(List<String> keyblgart) {
        List<String> sectionKz = Arrays.asList("SCLF", "SCRG", "SCRT", "SCSD");
        if (keyblgart == null || sectionKz == null) {
            return false;
        } else {
            return CollectionUtils.containsAny(keyblgart, sectionKz);
        }
    }

    /**
     * NUR diese Benutzer dürfen die 990er Liste sehen.
     * 
     * @param id
     * @return
     */
    private boolean is990erUser(Long id) {
        return UserDAOJDBC.USERID_990ER_LIST.contains(id);
    }

    /**
     * Mapped die Lucene-Indexbezeichner auf die, die an der Clientoberfläche vergebenen
     * txt-Feldbezeichner.
     * 
     * @param String
     *            s
     * @return String
     */
    public String getLuceneColName(String s) {
        return COLMAPPER.get(s);
    }

    /**
     * Prüft, ob eine Anweisung schon vorhanden ist, um Dopplungen zu vermeiden.
     * 
     * @param BooleanQuery
     *            queryNew
     * @param BooleanQuery
     *            queryOld
     * @return boolean
     */
    public boolean booleanClauseExisting(BooleanQuery queryNew, BooleanQuery queryOld) {

        boolean bcExisting = false;
        BooleanClause[] bcNew = queryNew.getClauses();
        BooleanClause[] bcOld = queryOld.getClauses();

        for (int i = 0; i < bcNew.length; i++) {
            for (int k = 0; k < bcOld.length; k++) {
                String n = bcNew[i] != null ? bcNew[i].toString() : "";// kundennummer:*
                String o = bcOld[k] != null ? bcOld[k].toString() : "";// +kundennummer:* =>
                                                                       // Kreuzprüfung nötig!
                if (n.contains(o) || o.contains(n)) {
                    bcExisting = true;
                    break;
                }
            }
        }

        return bcExisting;
    }

    /**
     * Mapper für ein org.apache.lucene.document.Document.
     * 
     * @param Class
     *            <?> clazz, z.B: Fundstelle.
     * @param Document
     *            document
     * @return Object
     * @throws Exception
     */
    public Object mapObject(Class<?> clazz, Document document) throws Exception {

        Object obj = null;

        try {
            obj = clazz.newInstance();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        List<?> restFields = document.getFields();
        for (Iterator<?> iterator = restFields.iterator(); iterator.hasNext();) {

            Field field = (Field) iterator.next();
            if (field == null) {
                continue;
            }

            String fieldName = field.name();
            String fieldContent = document.get(field.name());
            BeanUtils.setProperty(obj, fieldName, fieldContent);
        }

        return obj;
    }
}
