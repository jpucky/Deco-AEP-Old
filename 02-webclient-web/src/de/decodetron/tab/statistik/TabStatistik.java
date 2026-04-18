// $Log: TabStatistik.java,v $
// Revision 1.52  2020/02/26 19:24:10  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.51  2015/02/27 16:39:40  tw
// Skriptumbau: Bugfix f. Statistik.
//
// Revision 1.50  2015/02/12 00:58:09  tw
// Haldenbearbeitung: Grossflaechiger Skriptumbau. Zwangsumstellung auf JQuery 1.7.1.
//
// Revision 1.49  2015/02/11 11:47:49  tw
// Haldenbearbeitung: Aktivieren der Autocomplete-Funktion. Verlagern der Skripte.
//
// Revision 1.48  2015/02/01 10:44:46  tw
// Umzug der Defaultanzeige.
//
// Revision 1.47  2015/01/25 14:34:35  tw
// Vorabentwurf f. Lueckenprotokoll implementiert.
//
// Revision 1.46  2014/10/14 16:32:03  tw
// Haldenstatus, Anpassung an csv.
//
// Revision 1.45  2014/05/21 14:12:04  tw
// Versuchsblase: Neudefinition v. Lieferanten.
//
// Revision 1.44  2014/05/02 09:51:50  tw
// Backup: Benutzer anlegen.
//
// Revision 1.43  2014/04/24 13:36:05  tw
// Aufraeumarbeiten: Listeneintraege 'Statistik' werden jetzt sauber als Sections verwaltet.
//
// Revision 1.42  2014/04/24 12:17:02  tw
// Hotfix: Defektenlisten-Extrawurst-Section.
//
// Revision 1.41  2014/02/05 13:47:58  tw
// Anpassung an neue DB-Struktur
//
// Revision 1.40  2014/01/23 11:38:56  tw
// Bugifx classcastexception f. groupid 2.
//
// Revision 1.39  2013/12/15 22:28:12  tw
// Einbau: Fixed Tableheader.
//
// Revision 1.38  2013/12/12 15:37:23  tw
// Tabelle zeichnet sich nur so gross wie eingabefelder.
//
// Revision 1.37  2013/12/09 14:09:18  tw
// Von-Bis Suchfeld, Statistik.
//
// Revision 1.36  2013/12/09 09:39:43  tw
// Neue Schnittstelle: Von-bis Datum Statistik
//
// Revision 1.35  2013/12/08 17:32:19  tw
// Datepicker-Von, Statistik.
//
// Revision 1.34  2013/12/05 15:45:39  tw
// .csv - Listen fuer alle Listen
//
// Revision 1.33  2013/12/02 21:58:27  tw
// Spalten: KundenNr, LieferscheinNr werden fuer alle Lieferanten in allen Listen gefiltert.
//
// Revision 1.32  2013/12/02 17:33:58  tw
// Vorbereitungen fuer gezieltes Spaltenfiltern.
//
// Revision 1.31  2013/12/02 13:52:24  tw
// Vorbereitungen fuer gezieltes Spaltenfiltern.
//
// Revision 1.30  2013/12/02 12:33:53  tw
// Neue Schnittstelle um unnoetige Spalten auszublenden.
//
// Revision 1.29  2013/11/29 12:32:37  tw
// Neue Liste: Valuta.
//
// Revision 1.28  2013/11/28 17:18:11  tw
// Neue Liste: Ueberweiser.
//
// Revision 1.27  2013/11/28 14:35:02  tw
// Neue Liste: Transfusion.
//
// Revision 1.26  2013/11/28 13:42:27  tw
// Anbindung Liste:Tierarznei.
//
// Revision 1.25  2013/11/27 13:45:19  tw
// TODO: https://team.decodetron.de:10443/index.php?c=task&a=view_task&id=3929
// Bugfix: https://team.decodetron.de:10443/index.php?c=task&a=view_task&id=3933 1a.
//
// Revision 1.24  2013/11/25 14:55:22  tw
// Defektenliste: limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.23  2013/11/21 22:10:45  tw
// Limitierter Datenausgabe: Anbindung Btm-Liste erster Wurf.
//
// Revision 1.22  2013/11/21 17:40:46  tw
// Vorbereitung: neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.21  2013/11/17 02:11:19  tw
// Mapping zurueckgenommen
//
// Revision 1.20  2013/11/17 01:54:41  tw
// Mapping der Statistik-Daten.
//
// Revision 1.19  2013/11/15 21:48:03  tw
// Tabellenspalten werden per seperater DB-Funktion abgerufen.
//
// Revision 1.18  2013/11/14 14:09:04  tw
// Schnittstellen Datenfilterung implementiert.
//
// Revision 1.17  2013/11/13 01:31:34  tw
// Statistik - Listen Berechtigungskonzept, Bugfix.
//
// Revision 1.16  2013/11/13 01:18:16  tw
// Neuer Belegtyp Sammelrechnungen optisch hinzugefuegt.
//
// Revision 1.15  2013/11/10 20:26:10  tw
// Statistik: Reimporte
//
// Revision 1.14  2013/11/09 19:01:12  tw
// Defektenliste, Zusammenfassungsarbeiten, Nullponter-Exception Sortierung behoben.
//
// Revision 1.13  2013/11/08 12:50:25  tw
// Club-Abverkauf implementiert.
//
// Revision 1.12  2013/11/08 09:48:53  tw
// chargenliste implementiert.
//
// Revision 1.11  2013/11/08 00:28:16  tw
// BTM-Liste implementiert.
//
// Revision 1.10  2013/11/07 22:42:09  tw
// Clubliste geradegezogen.
//
// Revision 1.9  2013/11/07 22:00:30  tw
// Clubliste geradegezogen.
//
// Revision 1.8  2013/11/05 23:03:23  tw
// Experimente, um CVS-Listen generischer Lesen zu koennen.
//
// Revision 1.7  2013/11/05 03:20:16  tw
// Paginierung begonnen.
//
// Revision 1.6  2013/11/04 14:50:40  tw
// Anbindung der Filterfelder. Fehlende Spalten nachgetragen.
//
// Revision 1.5  2013/11/04 11:32:31  tw
// Anbindung der Filterfelder.
//
// Revision 1.4  2013/11/03 21:06:35  tw
// Statistik: Ausrollen f. Testumgebung.
//
// Revision 1.3  2013/11/03 12:18:42  tw
// ClubBestand: Anbindung an DB.
//
// Revision 1.2  2013/11/02 00:51:32  tw
// Statistik-Modul, erster Wurf implementiert.
//
// Revision 1.1  2013/10/30 12:23:00  tw
// Anbindung eines Tab-Reiters.
//
//

package de.decodetron.tab.statistik;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.IAjaxCallListener;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.markup.html.form.select.IOptionRenderer;
import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.extensions.markup.html.form.select.SelectOptions;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.WildcardCollectionModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPModel;
import de.decodetron.Const;
import de.decodetron.bo.Section;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.SectionComparator;
import de.decodetron.tab.empty.ViewContEmpty;
import de.decodetron.tab.statistik.btm.ViewContBtm;
import de.decodetron.tab.statistik.chargen.ViewContChargen;
import de.decodetron.tab.statistik.club.abverkauf.ViewContClubAbverk;
import de.decodetron.tab.statistik.club.bestand.ViewContClubBest;
import de.decodetron.tab.statistik.defekte.ViewContDefekte;
import de.decodetron.tab.statistik.hochpreiser.ViewContHochpreis;
import de.decodetron.tab.statistik.reimporte.ViewContReimporte;
import de.decodetron.tab.statistik.testliste.ClubBestandPanel;
import de.decodetron.tab.statistik.tierarznei.ViewContTierarznei;
import de.decodetron.tab.statistik.transfusion.ViewContTransfusion;
import de.decodetron.tab.statistik.ueberweiser.ViewContUeberweiser;
import de.decodetron.tab.statistik.valuta.ViewContValuta;

/**
 * Von der Form lostgeloeste Komponente. Je nach gewaehlter Liste, wird ein eigener Container
 * eingeblendet.
 * 
 * @author Thomas Winter
 * @since 30.10.2013
 */
public class TabStatistik extends Panel {

    private Panel listviewcontainer;
    public static String MENUEITEM = "Statistik"; // Fix in der DB vergeben !!!

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(OnDomReadyHeaderItem.forScript("$.fn.hideViewBusy();"));
    }

    public TabStatistik(String id, final IModel<AEPModel> model) {

        super(id, model);
        setOutputMarkupId(true);

        final Select<String> listenTyp = new Select<String>("listentyp");
        List<Section> secList = LoginSession.get().getSections4MenuItem(MENUEITEM);
        Section dummy = new Section();
        dummy.setSection(Const.KEY_LISTENTYP_DEFAULTEMPTY);
        secList.add(dummy);

        Collections.sort(secList, new SectionComparator());
        WildcardCollectionModel<Section> wcm = new WildcardCollectionModel<Section>(secList);
        listenTyp.add(new SelectOptions<Section>("listentypitem", wcm, renderer));
        listenTyp.add(new AjaxFormComponentUpdatingBehavior("onchange") {

            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {

                super.updateAjaxAttributes(attributes);
                IAjaxCallListener ajaxCallListener = new AjaxCallListener() {
                    @Override
                    public CharSequence getSuccessHandler(Component component) {
                        return "$.fn.hideViewBusy();";
                    }

                    @Override
                    public CharSequence getBeforeSendHandler(Component component) {
                        return "$.fn.showViewBusy();";
                    }

                    @Override
                    public CharSequence getFailureHandler(Component component) {
                        return "$.fn.hideViewBusy();";
                    }
                };
                attributes.getAjaxCallListeners().add(ajaxCallListener);
            }

            protected void onUpdate(AjaxRequestTarget target) {

                // target.appendJavaScript("$.fn.showViewBusy();");
                // ((ValueMap) model.getObject()).put(Const.KEY_NAVI_COMBOACTION,
                // "Bitte wählen Sie eine Liste aus.");

                Section menuItem = (Section) listenTyp.getDefaultModelObject();
                ValueMap map = (ValueMap) getDefaultModelObject();
                LoginSession.get().setCurrentSection(menuItem);

                if (Const.KEY_LISTENTYP_KLEIN.equals(menuItem.getKz())) {
                    Panel p1 = new ClubBestandPanel("listviewcontainer", model);
                    listviewcontainer.replaceWith(listviewcontainer = p1);
                } else if (ViewContDefekte.MODULNAME.equals(menuItem.getKz())) {
                    Panel p2 = new ViewContDefekte("listviewcontainer", model);
                    listviewcontainer.replaceWith(listviewcontainer = p2);
                } else if (ViewContDefekte.MODULNAMEL.equals(menuItem.getKz())) {
                    Panel p2 = new ViewContDefekte("listviewcontainer", model);
                    listviewcontainer.replaceWith(listviewcontainer = p2);
                } else if (ViewContClubBest.MODULNAME.equals(menuItem.getKz())) {
                    Panel p3 = new ViewContClubBest("listviewcontainer", model);
                    listviewcontainer.replaceWith(listviewcontainer = p3);
                } else if (ViewContClubAbverk.MODULNAME.equals(menuItem.getKz())) {
                    Panel p4 = new ViewContClubAbverk("listviewcontainer", model);
                    listviewcontainer.replaceWith(listviewcontainer = p4);
                } else if (ViewContBtm.MODULNAME.equals(menuItem.getKz())) {
                    Panel p6 = new ViewContBtm("listviewcontainer", model);
                    listviewcontainer.replaceWith(listviewcontainer = p6);
                } else if (ViewContChargen.MODULNAME.equals(menuItem.getKz())) {
                    Panel p7 = new ViewContChargen("listviewcontainer", model);
                    listviewcontainer.replaceWith(listviewcontainer = p7);
                } else if (ViewContReimporte.MODULNAME.equals(menuItem.getKz())) {
                    Panel p8 = new ViewContReimporte("listviewcontainer", model);
                    listviewcontainer.replaceWith(listviewcontainer = p8);
                } else if (ViewContHochpreis.MODULNAME.equals(menuItem.getKz())) {
                    Panel p9 = new ViewContHochpreis("listviewcontainer", model);
                    listviewcontainer.replaceWith(listviewcontainer = p9);
                } else if (ViewContTierarznei.MODULNAME.equals(menuItem.getKz())) {
                    Panel p10 = new ViewContTierarznei("listviewcontainer", model);
                    listviewcontainer.replaceWith(listviewcontainer = p10);
                } else if (ViewContTransfusion.MODULNAME.equals(menuItem.getKz())) {
                    Panel p11 = new ViewContTransfusion("listviewcontainer", model);
                    listviewcontainer.replaceWith(listviewcontainer = p11);
                } else if (ViewContUeberweiser.MODULNAME.equals(menuItem.getKz())) {
                    Panel p11 = new ViewContUeberweiser("listviewcontainer", model);
                    listviewcontainer.replaceWith(listviewcontainer = p11);
                } else if (ViewContValuta.MODULNAME.equals(menuItem.getKz())) {
                    Panel p11 = new ViewContValuta("listviewcontainer", model);
                    listviewcontainer.replaceWith(listviewcontainer = p11);
                } else if (Const.KEY_LISTENTYP_DEFAULTEMPTY.equals(menuItem.getKz())) {
                    Panel p5 = new ViewContEmpty("listviewcontainer", model);
                    listviewcontainer.replaceWith(listviewcontainer = p5);
                } else {
                    Panel p9 = new ViewContEmpty("listviewcontainer", model);
                    listviewcontainer.replaceWith(listviewcontainer = p9);
                }

                map.put(Const.KEY_BTN_FILTERSUCHE, null);
                map.put(Const.KEYDOCTOTAL, null); // alle Dokumente
                map.put(Const.KEYHITSPERPAGE, null); // HITSPERPAGE, alle Treffer
                map.put(Const.KEYSHOWPERPAGE, null); // SHOWPERPAGE

                target.add(TabStatistik.this);
            }
        });

        add(listenTyp);

        // ///////////////////////////////////////////////////////////////
        // /// Voreinstellung. Die beiden müssen zueinander passen!
        // /
        ((ValueMap) model.getObject()).put(Const.KEY_NAVI_COMBOACTION, "Bitte wählen Sie eine Liste aus.");
        listenTyp.setDefaultModelObject(Const.KEY_LISTENTYP_DEFAULTEMPTY);
        add(listviewcontainer = new ViewContEmpty("listviewcontainer", model));
    }

    /**
     * Für den Menüeintrag an der Oberfläche wird "getSection()" verwendet.
     */
    IOptionRenderer<Section> renderer = new IOptionRenderer<Section>() {
        private static final long serialVersionUID = 1L;

        @Override
        public String getDisplayValue(Section object) {
            return object.getSection();
        }

        @Override
        public IModel<Section> getModel(Section value) {
            return new Model<Section>(value);
        }
    };
}
