// $Log: FundstellenSuche.java,v $
// Revision 1.71  2017/07/20 15:59:33  tw
// Version 1.21-H: BUGIFX: Fixe Reihenfolge der Belegtypen zeigt Belegtypen an, die nicht der Gruppe zugeordnet sind.
//
// Revision 1.70  2017/07/18 14:16:02  tw
// Version 1.20-H. Neuer Belegtyp: Monatsberichte.
//
// Revision 1.69  2017/07/05 20:56:16  tw
// Version 1.19-H. Mobilmachung der Headrevision.
//
// Revision 1.68  2017/06/23 11:57:58  tw
// Mobilmachung der Headrevision.
//
// Revision 1.67  2017/06/21 19:44:16  tw
// Mobilmachung der Headrevision.
//
// Revision 1.66  2016/02/05 15:24:32  tw
// CR 3956: Interner Umbau: Events separiert.
//
// Revision 1.65  2016/01/31 17:00:14  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.64  2016/01/19 23:15:06  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.63  2016/01/13 12:16:08  tw
// CR 3956: Interner Umbau: Vorbereitung eigener Panelkomponenten.
//
// Revision 1.62  2016/01/12 21:26:22  tw
// CR 3956: Interner Umbau: Lucene-Felder. Rueckbau d. Textfeld-Id Verlaengerung.
//
// Revision 1.61  2016/01/11 22:50:35  tw
// CR 3956: Interner Umbau: Lucene-Felder.
//
// Revision 1.60  2015/12/16 20:41:39  tw
// CR 3953: Archivierung von Retourenbelegen.
//
// Revision 1.59  2015/07/20 14:51:05  tw
// Vereinfachung d. Komponente: LblDokumentScan.
//
// Revision 1.58  2014/12/17 22:07:29  tw
// Recherche: aktuelles Jahr verwenden.
//
// Revision 1.57  2014/10/27 14:46:54  tw
// Bugfix in der Recherche Gesamtanzahl / Aufraeumarbeiten.
//
// Revision 1.56  2014/10/03 12:01:38  tw
// Lieferantenname Textfeldanbindung.
//
// Revision 1.55  2014/10/02 12:36:52  tw
// Lieferantenname Textfeldanbindung.
//
// Revision 1.54  2014/10/01 22:02:53  tw
// Lieferantenname Textfeldanbindung.
//
// Revision 1.53  2014/08/01 14:21:42  tw
// Scanbelege, Oberflaechenapassungen, Fundstellensuche.
//
// Revision 1.52  2014/08/01 13:00:02  tw
// Scanbelege, Oberflaechenapassungen, Fundstellensuche.
//
// Revision 1.51  2014/07/31 14:10:16  tw
// Scanbelege, Oberflaechenapassungen.
//
// Revision 1.50  2014/07/29 21:19:58  tw
// Schnittstellenanpassung Scanbelege.
//
// Revision 1.49  2014/07/18 14:12:20  tw
// Labelanpassung Scannbelege.
//
// Revision 1.48  2014/07/17 22:05:20  tw
// Extrawurst-Label f. Scanbelege erstellt.
//
// Revision 1.47  2014/04/14 13:41:00  tw
// Fix. f. Recherche-Textfelder.
//
// Revision 1.46  2014/03/20 02:22:22  tw
// Recherche: Paginierung, Funktionstest. Event-Konstanten zusammengefasst.
//
// Revision 1.45  2014/03/18 14:05:53  tw
// Recherche: Paginierung, Styleanbindung, Funktionstest.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.44  2014/03/18 02:28:09  tw
// Recherche: Paginierung, Styleanbindung, Funktionstest.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.43  2014/03/18 02:22:55  tw
// Recherche: Paginierung, Styleanbindung, Funktionstest.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.42  2014/03/04 16:21:48  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Recherche: Paginierung.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.41  2014/03/03 19:29:51  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Recherche: Paginierung.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.40  2014/03/02 00:10:46  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Recherche: Paginierung.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.39  2014/03/01 23:59:48  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Recherche: Paginierung.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.38  2014/02/26 15:00:50  tw
// Anpassung an neue DB-Struktur: Gebietssleiter.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.37  2014/02/07 15:44:08  tw
// Menueitems werden aus db gelesen.
//
// Revision 1.36  2014/02/06 03:03:47  tw
// Scanbelege, Anpassung an neue DB-Struktur
//
// Revision 1.34  2014/02/05 13:47:58  tw
// Anpassung an neue DB-Struktur
//
// Revision 1.33  2014/02/02 16:57:41  tw
// Layoutanpassung: DatePicker.
//
// Revision 1.32  2014/02/01 14:19:17  tw
// Mapping Belegart dynamisiert.
//
// Revision 1.31  2014/01/31 17:44:01  tw
// Anpassung der Defaulteinstellung/DropDownBoxen beim Laden der Seite.
//
// Revision 1.30  2014/01/31 13:32:11  tw
// Umlaute!
//
// Revision 1.29  2014/01/31 13:28:24  tw
// Belegart nur fuer Superadmins sichtbar.
//
// Revision 1.28  2014/01/31 01:44:00  tw
// Variables Label Kundennr/Lieferantnr.
//
// Revision 1.27  2014/01/30 23:19:16  tw
// Belegart: EK. Spaltenbezeichner Anpassung.
//
// Revision 1.26  2014/01/30 17:39:11  tw
// Umbau: Belegart-Auswahl. Backup.
//
// Revision 1.25  2014/01/30 08:07:50  tw
// Umbau: Belegart-Auswahl. Backup.
//
// Revision 1.24  2014/01/29 23:00:59  tw
// Umbau: Belegart-Auswahl. Backup.
//
// Revision 1.23  2014/01/29 19:52:54  tw
// Umbau: Belegart-Auswahl.
//
// Revision 1.22  2014/01/29 09:49:30  tw
// Vorbereitung: Belegart: EK / Benutzerverwaltung.
//
// Revision 1.21  2014/01/23 11:38:56  tw
// Bugifx classcastexception f. groupid 2.
//
// Revision 1.20  2014/01/10 01:16:07  tw
// Statistik: Listenausgabe als pdf. Erster Durchstich d. Defektenliste.
//
// Revision 1.19  2013/11/18 21:49:47  tw
// auswaehlbarer von- bis suchbereich implementiert. aufraeumarbeiten.
//
// Revision 1.18  2013/11/18 10:45:55  tw
// Multifilter an die Statistik angebunden.
//
// Revision 1.17  2013/11/18 07:51:21  tw
// Multifilter an die Recherche angebunden.
//
// Revision 1.16  2013/11/17 16:18:44  tw
// Recherche an den Multifilter angebunden. Modulumstellung auf KZ.
//
// Revision 1.15  2013/11/16 17:43:51  tw
// Von- Bis Suche implementiert.
//
// Revision 1.14  2013/11/14 14:09:04  tw
// Schnittstellen Datenfilterung implementiert.
//
// Revision 1.13  2013/11/14 01:32:20  tw
// tss
//
// Revision 1.12  2013/11/14 01:27:10  tw
// Bugfix: Oberflaechenaktualisierung.
//
// Revision 1.11  2013/11/14 01:14:02  tw
// Anbindung Recherche-Datenfilterung
//
// Revision 1.10  2013/11/13 23:13:40  tw
// Vorbereitung: Schnittstellen fuer Datenfilter.
//
// Revision 1.9  2013/11/13 15:58:21  tw
// Sammelrechnungsfilter eingebaut.
//
// Revision 1.8  2013/11/13 01:17:39  tw
// Statistik - Listen ans Berechtigungskonzept angebunden.
//
// Revision 1.7  2013/11/12 02:09:45  tw
// Jetzt aber!
//
// Revision 1.4  2013/11/12 00:02:51  tw
// Belegart vorerst hinzugef�gt.
//
// Revision 1.3  2013/11/10 23:32:17  tw
// backup
//
// Revision 1.2  2013/11/10 20:26:10  tw
// Statistik: Reimporte
//
// Revision 1.1  2013/11/02 00:51:06  tw
// Statistik-Modul, erster Wurf implementiert.
//
// Revision 1.10  2013/10/30 12:23:00  tw
// Anbindung eines Tab-Reiters.
//
// Revision 1.9  2013/10/29 16:06:56  tw
// - aep user im import.sql integriert.
// - billig-baumdarstellung implementiert.
//
// Revision 1.8  2013/10/29 00:25:48  tw
// Combobox: Dokumenttyp an Server angeflanscht.
//
// Revision 1.7  2013/10/27 20:28:38  tw
// Umlaute
//
// Revision 1.6  2013/10/27 17:42:28  tw
// PDF-Ablage geandert. Layoutanpassung: PDF-Ansicht als Overflow:hidden.
//
// Revision 1.5  2013/10/27 17:25:40  tw
// PDF-Ablage geändert. Layoutanpassung: PDF-Ansicht als Overflow:hidden.
//
// Revision 1.4  2013/10/25 11:17:10  tw
// Bugfixing f. Rollout.
//
// Revision 1.3  2013/10/25 00:28:37  tw
// Anpassung an Rollout.
//
// Revision 1.2  2013/10/07 11:57:40  tw
// codierung geändert.
//
// Revision 1.1  2013/10/06 23:50:13  tw
// Aufräumarbeiten. Beseitigen globaler Variablen.
//
//

package de.decodetron.tab.recherche;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPModel;
import de.decodetron.Const;
import de.decodetron.bo.Section;
import de.decodetron.data.Util;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.recherche.event.RecOnBelegartSelektiert;
import de.decodetron.tab.recherche.panel.FundstellenTxtComponents;
import de.decodetron.tab.statistik.TxtFilter;

/**
 * @author Thomas Winter
 * @since 06.10.2013
 */

public class FundstellenSuche extends Form {

    private String selectedMake = null;
    private static String KEYALLES = "Alles";

    /**
     * Die Beleg-Map muss nach diesem Key-Value Prinzip gefüllt werden.<br>
     * 
     * <pre>
     * Tagesbelege=[Alles, Gutschriften, Lieferscheine, Nachbelastungen],
     * Sammelrechnungen=[Sammelrechnungen],
     * Scanbelege=[Alles, Lieferscheine, Rechnungen, Retouren, sonstige Dokumente],
     * Einkaufsaufträge=[Einkaufsaufträge]
     * 
     * <pre>
     */
    // private Map<String, List<String>> ddBelege = new HashMap<String, List<String>>();
    LinkedHashMap<String, List<String>> ddBelege = new LinkedHashMap<String, List<String>>();

    private static Logger log = Logger.getLogger(FundstellenSuche.class);

    /**
     * Vorsicht, getModel() ist bereits in der Form vergeben.
     * 
     * @return RechercheModel
     */
    public RechercheModel getModelRm() {
        return ((AEPModel) FundstellenSuche.this.getDefaultModelObject()).getRechercheModel();
    }

    public FundstellenSuche(String id, IModel m) {
        super(id, m);
        setOutputMarkupId(true);

        getModelRm().initSuchItems();

        final HashMap<String, List<String>> lucyBelegartMap = initDropdownBelegarten(getDefaultModel());
        final DropDownChoice<String> ddBelegTyp;

        // Zurücksetzen/Initialisieren der u.u. alten Fundstelle
        // ((ValueMap) getDefaultModelObject()).put(Const.KEYFUNDSTELLE, null);
        getModelRm().addFundstelle(null);

        TxtFilter txtDateVon;
        TxtFilter txtDateBis;

        // Aktuelles Jahr
        add(new Label("curryear", Model.of(Util.getCurrentYear())));
        add(txtDateVon = new TxtFilter("txt-blgdatevon"));
        add(txtDateBis = new TxtFilter("txt-blgdatebis"));

        getModelRm().addSuchItem(txtDateVon);
        getModelRm().addSuchItem(txtDateBis);

        // //////////////////////////////////////////////////////
        // /// Belegart
        // /
        ddBelegTyp = new DropDownChoice<String>("listentyp", new Model<String>(), modelListenTyp);
        add(new DDBelegArt("belegartt",//
                new PropertyModel<String>(this, "selectedMake"),//
                modelBart,//
                ddBelegTyp)//
        );
        add(new DDBelegTypContainer("listentypC", getDefaultModel(), ddBelegTyp));

        ddBelegTyp.add(new AjaxFormComponentUpdatingBehavior("onChange") {
            protected void onUpdate(AjaxRequestTarget target) {

                log.debug("gewaehlt2: " + ddBelegTyp.getModelObject());
                ValueMap vm = (ValueMap) getDefaultModelObject();
                vm.put(Const.KEY_DD_BELEGTYP, ddBelegTyp.getModelObject());

                // Zurücksetzen der u.u. alten Fundstelle
                // vm.put(Const.KEYFUNDSTELLE, null);
                getModelRm().addFundstelle(null);

                String itemKey = vm.get(Const.KEY_DD_BELEGART) + "-" + vm.get(Const.KEY_DD_BELEGTYP);
                vm.put(Const.KEY_SELECTED_BELEGART, lucyBelegartMap.get(itemKey));
                // triggerSearch("", "", "", "", "", target);

                // Update d. Suchfelder u. Tabellenueberschriften.
                // new ChangeEvent(FundstellenSuche.this, target, null,
                // Const.KEY_BELART_SELECTED).fire();
                new RecOnBelegartSelektiert(FundstellenSuche.this, target, null).fire();
            }
        });

        // PropertyModel pm = new PropertyModel<String>(this, "selectedFSLabel");
        // add(new ActionPanel("klickhint", (IModel<AEPModel>) getDefaultModel()));
        add(new LblShowKlickHint("klickhint", getDefaultModel()));
        add(new AnsichtPDFBeleg("pdfbeleg", getDefaultModel()));
        add(new FundstellenTxtComponents("txtPanelRecherche", (IModel<AEPModel>) getDefaultModel()));
    }

    /**
     * Master - DDBox.
     */
    private class DDBelegArt extends DropDownChoice<String> {

        private DropDownChoice<String> childDD_;

        public DDBelegArt(String id, IModel model, IModel choices, final DropDownChoice<String> childDD) {
            super(id, model, choices);
            childDD_ = childDD;
            add(new AjaxFormComponentUpdatingBehavior("onChange") {
                @Override
                protected void onUpdate(AjaxRequestTarget target) {

                    ValueMap vm = (ValueMap) FundstellenSuche.this.getDefaultModelObject();
                    log.debug("gewaehlt1: " + getModelObject());
                    vm.put(Const.KEY_DD_BELEGART, getModelObject());

                    // Zurücksetzen der u.u. alten Fundstelle
                    // vm.put(Const.KEYFUNDSTELLE, null);
                    getModelRm().addFundstelle(null);

                    HashMap<String, List<String>> lucyBelegartMap = (HashMap<String, List<String>>) vm
                            .get(Const.KEY_LIST_RECHERCHE_BELEGART);
                    log.debug("subitem : " + getFirstItemFromChildList(getModelObject()) + " belegartcheck: "
                            + lucyBelegartMap.get(getFirstItemFromChildList(getModelObject())));

                    // Setzen der korrekten Ansicht in der ChildBox.
                    // Das sollte automatisch passieren!
                    childDD.setDefaultModelObject(getFirstItemFromChildList(getModelObject()));

                    String itemKey = getModelObject() + "-" + getFirstItemFromChildList(getModelObject());
                    vm.put(Const.KEY_SELECTED_BELEGART, lucyBelegartMap.get(itemKey));

                    // triggerSearch("", "", "", "", "", target);
                    // new ChangeEvent(FundstellenSuche.this, target, null,
                    // Const.KEY_BELART_SELECTED).fire();
                    new RecOnBelegartSelektiert(FundstellenSuche.this, target, null).fire();
                }
            });

            /**
             * 22.06.2017: Die bei den Benutzern momentan gewohnte Reihenfolge ist:<br>
             * 1.) Sammelrechnungen (vorselektiert)<br>
             * 2.) Tagesbelege<br>
             * 3.) Scanbelege<br>
             * 4.) Einkaufsaufträge<br>
             * 5.) Monatsberichte
             * 
             * Diese Reihenfolge muss so bleiben! Pruefung auf null bei remove nicht noetig, gibt
             * keine Exception.
             */
            List<String> sr = ddBelege.get("Sammelrechnungen");
            List<String> tb = ddBelege.get("Tagesbelege");
            List<String> sc = ddBelege.get("Scanbelege");
            List<String> ek = ddBelege.get("Einkaufsaufträge");
            List<String> mb = ddBelege.get("Monatsberichte");

            ddBelege.remove("Sammelrechnungen");
            ddBelege.remove("Tagesbelege");
            ddBelege.remove("Scanbelege");
            ddBelege.remove("Einkaufsaufträge");
            ddBelege.remove("Monatsberichte");

            if (sr != null) {
                ddBelege.put("Sammelrechnungen", sr);
            }
            if (tb != null) {
                ddBelege.put("Tagesbelege", tb);
            }
            if (sc != null) {
                ddBelege.put("Scanbelege", sc);
            }
            if (ek != null) {
                ddBelege.put("Einkaufsaufträge", ek);
            }
            if (mb != null) {
                ddBelege.put("Monatsberichte", mb);
            }

            // 1. Eintrag vorselektieren
            if (ddBelege.keySet() != null && ddBelege.keySet().size() > 0) {
                setDefaultModelObject(ddBelege.keySet().iterator().next());
            }
        }

        /**
         * Das setzen des 1. Eintrages l�st das Event hier aus.
         */
        @Override
        protected void onModelChanged() {
            // System.out.println("do2: " + getModelObject());

            ValueMap vm = (ValueMap) FundstellenSuche.this.getDefaultModelObject();
            vm.put(Const.KEY_DD_BELEGART, getModelObject());
            String itemKey = getModelObject() + "-" + getFirstItemFromChildList(getModelObject());

            HashMap<String, List<String>> lucyBelegartMap = (HashMap<String, List<String>>) vm
                    .get(Const.KEY_LIST_RECHERCHE_BELEGART);

            vm.put(Const.KEY_SELECTED_BELEGART, lucyBelegartMap.get(itemKey));
            childDD_.setDefaultModelObject(getFirstItemFromChildList(getModelObject()));
            super.onModelChanged();
        }

        private String getFirstItemFromChildList(String key) {
            List<? extends String> subItemList = ddBelege.get(key);
            String subItemFirst = ((subItemList == null) ? "" : subItemList.get(0));
            return subItemFirst;
        }
    }

    /**
     * DD-Boxen und Lucene-Mapper werden jetzt aus DB initialisiert.
     * 
     * @param Form
     *            form
     * @param LblShow
     *            showlabel
     * @param LblTotal
     *            totalLabel
     */
    private HashMap<String, List<String>> initDropdownBelegarten(final IModel<?> model) {

        /**
         * Die Belegarten-Map hat am Ende folgendes Aussehen:
         * 
         * <pre>
         *  Tagesbelege-Nachbelastungen       =[NB]
         *  Tagesbelege-Lieferscheine         =[LS]
         *  Tagesbelege-Gutschriften          =[GS]
         *  Tagesbelege-Alles                 =[GS, LS, NB]
         *  
         *  Einkaufsaufträge-Einkaufsaufträge =[EK]
         *  Sammelrechnungen-Sammelrechnungen =[SR]
         *  
         *  Scanbelege-sonstige Dokumente     =[SCSD]
         *  Scanbelege-Lieferscheine          =[SCLF]
         *  Scanbelege-Retouren               =[SCRT]
         *  Scanbelege-Rechnungen             =[SCRG]
         *  Scanbelege-Alles                  =[SCLF, SCRG, SCRT, SCSD]
         * 
         * </pre>
         */
        HashMap<String, List<String>> lucyBelegartMap = new HashMap<String, List<String>>();
        List<Section> sectionRechercheList = LoginSession.get().getSections4MenuItem(TabRecherche.MENUEITEM);
        for (Iterator<Section> iterator = sectionRechercheList.iterator(); iterator.hasNext();) {

            Section section = iterator.next();
            String sectionName = section.getSection();
            String sectionNameSub = section.getSubsection();
            String sectionNameKZ = section.getKz().toUpperCase();

            // ////////////////////////////////////////////////////////////////////
            // /// DD-Boxen initialisieren
            // /
            if (ddBelege.containsKey(sectionName)) {
                if (sectionNameSub != null) {
                    List<String> sublist = ddBelege.get(sectionName);
                    sublist.add(sectionNameSub);
                }
            } else {
                List<String> ltmp = (new ArrayList<String>());
                if (sectionNameSub != null) {
                    ltmp.add(KEYALLES);
                }
                ltmp.add(sectionNameSub != null ? sectionNameSub : sectionName);
                ddBelege.put(sectionName, ltmp);
            }

            // ////////////////////////////////////////////////////////////////////
            // /// LuceneMap initialisieren
            // /
            String itemKey = sectionName + "-" + (sectionNameSub != null ? sectionNameSub : sectionName);
            if (!lucyBelegartMap.containsKey(itemKey)) {
                List<String> ltmp = (new ArrayList<String>());
                ltmp.add(section != null ? sectionNameKZ : "");
                lucyBelegartMap.put(itemKey, ltmp);
            } else {
                lucyBelegartMap.get(itemKey).add(sectionNameKZ);
            }

            // Extrawurst Alles-Schlüssel ...
            String itemKeyAlles = sectionName + "-" + KEYALLES;

            if (sectionNameSub != null) {
                if (!lucyBelegartMap.containsKey(itemKeyAlles)) {
                    List<String> ltmp = (new ArrayList<String>());
                    ltmp.add(sectionNameKZ);
                    lucyBelegartMap.put(itemKeyAlles, ltmp);
                } else {
                    lucyBelegartMap.get(itemKeyAlles).add(sectionNameKZ);
                }
            }
        }

        // TODO: Feng-Eintrag hierfür erstellen !!!
        List<String> remlist = ddBelege.get("Scanbelege");
        if (remlist != null && remlist.size() > 0) {
            remlist.remove("Alles");
        }

        ValueMap map = (ValueMap) model.getObject();
        map.put(Const.KEY_LIST_RECHERCHE_BELEGART, lucyBelegartMap);
        // System.out.println(lucyBelegartMap);
        // LucyBelegartMap.put("Tagesbelege", Arrays.asList("LS", "GS", "NB"));
        // LucyBelegartMap.put("Lieferscheine", Arrays.asList("LS"));
        return lucyBelegartMap;
    }

    IModel<List<? extends String>> modelBart = new AbstractReadOnlyModel<List<? extends String>>() {
        @Override
        public List<String> getObject() {
            return new ArrayList<String>(ddBelege.keySet());
        }
    };

    IModel<List<? extends String>> modelListenTyp = new AbstractReadOnlyModel<List<? extends String>>() {
        @Override
        public List<String> getObject() {
            List<String> models = ddBelege.get(selectedMake);
            if (models == null) {
                models = Collections.emptyList();
            }
            return models;
        }
    };

}
