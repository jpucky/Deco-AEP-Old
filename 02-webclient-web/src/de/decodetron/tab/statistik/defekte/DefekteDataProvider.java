// $Log: DefekteDataProvider.java,v $
// Revision 1.25  2020/02/26 19:24:11  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.24  2014/05/13 01:21:52  tw
// Dataprovider Cache - Optimierungen.
//
// Revision 1.23  2014/05/12 20:41:50  tw
// Aufraeumarbeiten, Benutzer anlegen loeschen.
//
// Revision 1.22  2014/05/08 20:59:13  tw
// Bugfix: Fehlerhafte Treffermenge wenn Suchergebnis leer.
//
// Revision 1.21  2014/05/07 21:34:05  tw
// Change Request: Doppelte Defektenlisten.
//
// Revision 1.20  2014/04/28 21:57:52  tw
// Backup: Benutzer anlegen.
//
// Revision 1.19  2014/04/28 11:11:15  tw
// Schnittstellen- umzug/umbenennung Filter=>Statistik
//
// Revision 1.18  2014/04/24 13:36:05  tw
// Aufraeumarbeiten: Listeneintraege 'Statistik' werden jetzt sauber als Sections verwaltet.
//
// Revision 1.17  2014/04/16 09:14:26  tw
// .
//
// Revision 1.16  2014/04/15 20:02:08  tw
// Schnittstellensaeuberung: AEP-Plus-Abverkauf.
//
// Revision 1.15  2014/04/01 01:00:07  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.14  2014/03/31 21:46:25  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.13  2014/03/30 10:44:17  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.12  2014/03/28 23:58:14  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.11  2014/03/28 23:02:49  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.10  2014/03/28 17:23:44  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.9  2014/03/26 21:03:10  tw
// Vorbereitung, CR-Defektenlisten f. Lieferanten: Markup-freie Tabellengestaltung.
//
// Revision 1.8  2014/02/05 13:47:58  tw
// Anpassung an neue DB-Struktur
//
// Revision 1.7  2014/01/16 12:34:51  tw
// Anzeige Treffermenge. Pdfgenerierung.
//
// Revision 1.6  2014/01/15 21:44:32  tw
// Bufix: Beseitigung aller KZs. Verbergen alle bisherigen Aenderungen.
//
// Revision 1.5  2013/12/09 14:09:18  tw
// Von-Bis Suchfeld, Statistik.
//
// Revision 1.4  2013/12/05 20:10:53  tw
// Bugfix Suche. Performancesteigerung: Defektenliste.
//
// Revision 1.3  2013/12/02 12:33:54  tw
// Neue Schnittstelle um unnoetige Spalten auszublenden.
//
// Revision 1.2  2013/11/27 13:45:19  tw
// TODO: https://team.decodetron.de:10443/index.php?c=task&a=view_task&id=3929
// Bugfix: https://team.decodetron.de:10443/index.php?c=task&a=view_task&id=3933 1a.
//
// Revision 1.1  2013/11/25 14:55:23  tw
// Defektenliste: limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
//

package de.decodetron.tab.statistik.defekte;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPApplication;
import de.decodetron.Const;
import de.decodetron.bo.DataRecord;
import de.decodetron.bo.FilterItemList;
import de.decodetron.bo.LimitInfo;
import de.decodetron.bo.SortInfo;
import de.decodetron.bo.User;
import de.decodetron.dao.statistik.defekte.DAODefekte;
import de.decodetron.dao.statistik.defekte.DAOIDefekte;
import de.decodetron.data.Util;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.statistik.TxtFilter;

/**
 * Nachtrag, 15.04.2014: <br>
 * Die Liste dient momentan als Vorlage für alle alten u. evtl. neuen Listen: <br>
 * - Eine Schnittstellenfunktion pro size und init.<br>
 * - Diese bekommen den User mit übergeben.<br>
 * - Textfeldparameter werden mittels FilterItemList übertragen!<br>
 * - Die Oberfläche wird mit reduziertem Markup erzeugt.
 * 
 * @author Thomas Winter
 * @since 25.11.2013
 */
//public class DefekteDataProvider extends AEPDataProvider {
public class DefekteDataProvider extends SortableDataProvider{

    private IModel<ValueMap> model;
    private DataRecord tblHeader = null;
    private int itemsPerPage = Const.ITEMS_PER_PAGE;

    public DefekteDataProvider(IModel<ValueMap> m) {
        model = m;
        setSort("1", SortOrder.DESCENDING);
    }

    protected DAOIDefekte getContactsDB() {
        return AEPApplication.get().getDBDefekteFilter();
    }

    @Override
    public Iterator<DataRecord> iterator(long first, long count) {
        final SortParam<String> sort = getSort();
        refreshSortInfo(sort.isAscending(), sort.getProperty(), first, count);
        ValueMap map = model.getObject();
        List<DataRecord> debug = initDefektListe(map);
        return debug.iterator();
    }

    /**
     * Setzt die Sortierinformationen Systemweit u.a für Suche-Starten.
     * 
     * @param Boolean
     *            isAscending
     * @param String
     *            colIndex
     * @param long first
     */
    private void refreshSortInfo(Boolean isAscending, String colIndex, long first, long count) {
        if (model != null) {
            ValueMap map = model.getObject();
            map.put(Const.KEY_LIST_ISASCENDING, isAscending);
            map.put(Const.KEY_LIST_COLNR2SORT, Integer.valueOf(colIndex));
            map.put(Const.KEY_LIST_OFFSET, first);
            //map.put(Const.KEYHITSPERPAGE, size());
            map.put(Const.KEYSHOWPERPAGE, count);
            map.put(Const.KEYDOCTOTAL, getContactsDB().countAllRecords(Const.TABLENAME_DEFEKTE));
        }
    }

    @Override
    //public long aepSize() {
    public long size(){

        ValueMap map = model.getObject();
        List<TxtFilter> listTxtFields = (List<TxtFilter>) map.get(Const.KEY_LIST_FILTERSUCHE);
        FilterItemList fList = Util.mapSearchFields(listTxtFields);

        String colName = LoginSession.get().getCurrentSection().getFilteridentifier();
        User user = LoginSession.get().getUser();
        long cnt = getContactsDB().countDefekteData(user, colName, fList);
        // Ist nötig wenn cnt == 0, da der Iterator nicht aktualisiert!
        map.put(Const.KEYHITSPERPAGE, cnt);
        map.put(Const.KEYSHOWPERPAGE, cnt);
        return cnt;
    }

    public void setItemPerPage(int itmPP) {
        itemsPerPage = itmPP;
    }

    private List<DataRecord> initDefektListe(ValueMap map) {

        List<DataRecord> list = null;
        List<TxtFilter> listTxtFields = (List<TxtFilter>) map.get(Const.KEY_LIST_FILTERSUCHE);
        FilterItemList fList = Util.mapSearchFields(listTxtFields);

        String colName = LoginSession.get().getCurrentSection().getFilteridentifier();
        Integer sortColNr = (Integer) map.get(Const.KEY_LIST_COLNR2SORT);
        String sortOrder = (Boolean) map.get(Const.KEY_LIST_ISASCENDING) ? "asc" : "desc";
        Long sortOffset = (Long) map.get(Const.KEY_LIST_OFFSET);

        User user = LoginSession.get().getUser();
        SortInfo sortInfo = new SortInfo(sortOrder, sortColNr);
        LimitInfo limitInfo = new LimitInfo(itemsPerPage, sortOffset);
        list = getContactsDB().getDefekteData(user, colName, sortInfo, limitInfo, fList);

        return list;
    }

    /**
     * Gecachter TableHeader.
     * 
     * @return DataRecord
     */
    public DataRecord getTableHeader() {
        if (tblHeader == null) {
            DAODefekte daoDefekte = AEPApplication.get().getDBDefekteFilter();
            tblHeader = daoDefekte.getDefekteColNames(LoginSession.get().getUser());
        }
        return tblHeader;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public IModel model(Object object) {
        return new Model((Serializable) object);
    }
}
