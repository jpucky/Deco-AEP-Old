// $Log: TabRecherche.java,v $
// Revision 1.29  2017/06/23 11:57:58  tw
// Mobilmachung der Headrevision.
//
// Revision 1.28  2017/06/21 19:44:17  tw
// Mobilmachung der Headrevision.
//
// Revision 1.27  2016/02/05 15:47:51  tw
// CR 3956: Interner Umbau: Events separiert.
//
// Revision 1.26  2016/01/31 17:00:15  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.25  2016/01/13 12:16:08  tw
// CR 3956: Interner Umbau: Vorbereitung eigener Panelkomponenten.
//
// Revision 1.24  2016/01/11 22:50:35  tw
// CR 3956: Interner Umbau: Lucene-Felder.
//
// Revision 1.23  2015/10/19 12:59:46  tw
// CR intern: Erweiterung der DB-Schnittstelle "Fundstelle" f. Archivierungsdatum.
//
// Revision 1.22  2015/07/20 14:51:05  tw
// Vereinfachung d. Komponente: LblDokumentScan.
//
// Revision 1.21  2015/02/12 00:58:08  tw
// Haldenbearbeitung: Grossflaechiger Skriptumbau. Zwangsumstellung auf JQuery 1.7.1.
//
// Revision 1.20  2015/02/11 11:47:49  tw
// Haldenbearbeitung: Aktivieren der Autocomplete-Funktion. Verlagern der Skripte.
//
// Revision 1.19  2014/10/02 12:36:52  tw
// Lieferantenname Textfeldanbindung.
//
// Revision 1.18  2014/07/30 16:08:37  tw
// Scanbelege, Lucene Objektmapper erstellt, Aufraeumarbeiten.
//
// Revision 1.17  2014/05/28 11:18:59  tw
// Login-Begrenzer Bugfix.
//
// Revision 1.16  2014/03/20 02:22:22  tw
// Recherche: Paginierung, Funktionstest. Event-Konstanten zusammengefasst.
//
// Revision 1.15  2014/03/01 23:59:48  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Recherche: Paginierung.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.14  2014/02/05 17:30:13  tw
// Anpassung an neue DB-Struktur
//
// Revision 1.13  2014/01/30 17:39:11  tw
// Umbau: Belegart-Auswahl. Backup.
//
// Revision 1.12  2014/01/29 09:49:30  tw
// Vorbereitung: Belegart: EK / Benutzerverwaltung.
//
// Revision 1.11  2013/12/24 00:24:27  tw
// Speicherleck PDF-Anzeige beseitigt.
//
// Revision 1.10  2013/11/18 21:49:47  tw
// auswaehlbarer von- bis suchbereich implementiert. aufraeumarbeiten.
//
// Revision 1.9  2013/11/17 16:18:44  tw
// Recherche an den Multifilter angebunden. Modulumstellung auf KZ.
//
// Revision 1.8  2013/11/15 21:48:03  tw
// Tabellenspalten werden per seperater DB-Funktion abgerufen.
//
// Revision 1.7  2013/11/14 14:09:04  tw
// Schnittstellen Datenfilterung implementiert.
//
// Revision 1.6  2013/11/14 01:14:02  tw
// Anbindung Recherche-Datenfilterung
//
// Revision 1.5  2013/11/13 23:13:40  tw
// Vorbereitung: Schnittstellen fuer Datenfilter.
//
// Revision 1.4  2013/11/03 12:18:42  tw
// ClubBestand: Anbindung an DB.
//
// Revision 1.3  2013/11/02 00:51:32  tw
// Statistik-Modul, erster Wurf implementiert.
//
// Revision 1.2  2013/10/31 00:14:26  tw
// Konfiguration der Quelldatenverzeichnisse von aussen moeglich.
//
// Revision 1.1  2013/10/30 12:23:00  tw
// Anbindung eines Tab-Reiters.
//
//

package de.decodetron.tab.recherche;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.resource.JQueryPluginResourceReference;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPApplication;
import de.decodetron.AEPModel;
import de.decodetron.Const;
import de.decodetron.tab.recherche.event.IRecOnFundstelleClickEvent;
import de.decodetron.tab.recherche.event.RecOnFundstelleClickEvent;
import de.decodetron.tab.recherche.panel.FundstellenTxtComponents;

/**
 * @author Thomas Winter
 * @since 30.10.2013b
 */
public class TabRecherche extends Panel {

    public static String MENUEITEM = "Recherche"; // Fix in der DB vergeben !!!
    // public static String MODULNAME_TB = "tb"; // Fix in der DB vergeben !!!
    public static String FS = System.getProperty("file.separator");

    @Override
    public void renderHead(IHeaderResponse response) {

        AEPApplication.initDatePicker(response);

        // ///////////////////////
        // /// Fixierter Tabellenkopf ...
        // /
        response.render(CssHeaderItem.forReference(new CssResourceReference(TabRecherche.class,
                "../../../../css/tableDefaultTheme.css")));
        response.render(JavaScriptHeaderItem.forReference(new JQueryPluginResourceReference(TabRecherche.class,
                "../../../../js/jquery.fixedheadertable.js")));
        // Die Aktualisierung erfolgt weiter "unten".
    }

    public TabRecherche(String id, IModel<?> model) {
        super(id, model);
        add(new FundstellenSuche("searchForm", getDefaultModel()));
    }

    /**
     * Blendet ein Hilfe-Hinweis ein, wenn die PDF-Adresse nicht null ist.
     
    private class ShowKlickHint extends Label implements IRecOnFundstelleClickEvent {
        public ShowKlickHint(String id, final IModel<?> mm) {
            super(id, mm);
            // setRenderBodyOnly(true);
            setOutputMarkupId(true);
            setEscapeModelStrings(false);
            setDefaultModel(new Model<String>() {
                public String getObject() {

                    ValueMap vm = (ValueMap) mm.getObject();
                    Object o = vm.get(Const.KEYFUNDSTELLE);
                    StringBuffer buf = new StringBuffer();
                    if (o == null) {
                        buf.append("<div class=\"viewpdf\">");
                        buf.append("<div>Bitte w\u00E4hlen Sie eine Fundstelle aus,<br>");
                        buf.append("damit ihnen ein Dokument angezeigt wird.</div>");
                        buf.append("</div>");
                        // buf.append("<div class=\"version\">");
                        // buf.append(AEPApplication.BUILDNUMBER);
                        // buf.append("</div>");
                    }
                    if (Const.FILENOTFOUNDEXCEPTION.equals(o)) {
                        buf.append("<div class=\"viewpdf\">");
                        buf.append("<div>Das angeforderte PDF wurde nicht gefunden!</div>");
                        buf.append("</div>");
                    }
                    return buf.toString();
                }
            });
        }

        @Override
        public void onFundstelleClick(RecOnFundstelleClickEvent ev) {
            ev.update(ShowKlickHint.this);
        }
    }
    */
}
