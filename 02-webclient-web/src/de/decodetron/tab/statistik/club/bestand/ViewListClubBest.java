// $Log: ViewListClubBest.java,v $
// Revision 1.12  2015/07/21 00:33:58  tw
// CR Feng-ID: 3952. Grundfunktionalitaet erstellt.
//
// Revision 1.11  2014/08/19 09:54:14  tw
// csv-button limiterung.
//
// Revision 1.10  2014/06/28 16:00:39  tw
// Benutzer bearbeiten finalisiert. Busy-Indicator f. Statistik u. Benutzer Bearbeiten.
//
// Revision 1.9  2014/01/16 17:01:53  tw
// Listenausgabe als pdf. Abverkauf-Reimporte
//
// Revision 1.8  2014/01/16 12:34:51  tw
// Anzeige Treffermenge. Pdfgenerierung.
//
// Revision 1.7  2013/12/05 20:10:53  tw
// Bugfix Suche. Performancesteigerung: Defektenliste.
//
// Revision 1.6  2013/12/05 15:45:39  tw
// .csv - Listen fuer alle Listen
//
// Revision 1.5  2013/12/02 21:54:12  tw
// Spalten: KundenNr, LieferscheinNr werden fuer alle Lieferanten in allen Listen gefiltert.
//
// Revision 1.4  2013/12/02 12:33:54  tw
// Neue Schnittstelle um unnoetige Spalten auszublenden.
//
// Revision 1.3  2013/11/25 16:01:30  tw
// ueberfluessige listen-initalisierung entfernt.
//
// Revision 1.2  2013/11/25 14:55:22  tw
// Defektenliste: limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.1  2013/11/25 13:16:59  tw
// Clubabverkauf: limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.6  2013/11/18 10:45:56  tw
// Multifilter an die Statistik angebunden.
//
// Revision 1.5  2013/11/15 21:48:03  tw
// Tabellenspalten werden per seperater DB-Funktion abgerufen.
//
// Revision 1.4  2013/11/14 14:09:04  tw
// Schnittstellen Datenfilterung implementiert.
//
// Revision 1.3  2013/11/09 19:01:12  tw
// Defektenliste, Zusammenfassungsarbeiten, Nullponter-Exception Sortierung behoben.
//
// Revision 1.2  2013/11/08 12:50:25  tw
// Club-Abverkauf implementiert.
//
// Revision 1.1  2013/11/08 12:00:26  tw
// Umbennenung.
//
// Revision 1.2  2013/11/08 00:28:16  tw
// BTM-Liste implementiert.
//
// Revision 1.1  2013/11/07 21:59:39  tw
// Clubliste geradegezogen.
//
// Revision 1.3  2013/11/06 23:38:18  tw
// Generischer Sortierer.
//
// Revision 1.2  2013/11/06 22:40:05  tw
// Neue Schnittstellentests f. flexiblere Abfragen.
//
// Revision 1.1  2013/11/05 23:04:05  tw
// Experimente, um CVS-Listen generischer Lesen zu koennen.
//
// Revision 1.2  2013/11/05 12:37:21  tw
// Spaltensortierung f. Clubverkauf implementiert.
//
// Revision 1.1  2013/11/05 03:38:09  tw
// Paginierung begonnen.
//
//

package de.decodetron.tab.statistik.club.bestand;

import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.decodetron.AEPApplication;
import de.decodetron.AEPClassLoader;
import de.decodetron.Const;
import de.decodetron.bo.DataRecord;
import de.decodetron.tab.statistik.AEPOrderByBorder;
import de.decodetron.tab.statistik.CreateCsvLink;
import de.decodetron.tab.statistik.CreatePdfLink;
import de.decodetron.tab.statistik.ListSortable;
import de.decodetron.tab.statistik.StatistikListPanel;
import de.decodetron.tab.statistik.TrefferLabel;

/**
 * Experimente, um CVS-Listen generischer Lesen zu können.
 * 
 * @author Thomas Winter
 * @since 04.11.2013
 */
public class ViewListClubBest extends StatistikListPanel {

    public ViewListClubBest(String id, IModel<?> model) {
        super(id, model);
        setOutputMarkupId(true);

        DataRecord tblHeader = AEPApplication.get().getDBClubBestand()
                .getColumNamesAsDataRecord(Const.TABLENAME_BESTAND);

        BestandDataProvider dp = new BestandDataProvider(model);
        final ListSortable dataView;
        add(dataView = new ListSortable("clubverkauftestliste", dp, tblHeader));
        add(new PagingNavigator("navigator", dataView));

        int colNr = 0;
        while (colNr < tblHeader.getSize() - 1) {
            colNr++;
            add(new AEPOrderByBorder(tblHeader.getColItem(colNr), String.valueOf(colNr), dp) {
                protected void onSortChanged() {
                    dataView.setCurrentPage(dataView.getCurrentPage());
                }
            });
        }
        add(new CreateCsvLink("csvdownload", getDefaultModel(), dp, tblHeader));
        add(new CreatePdfLink("pdfdownload", getDefaultModel(), dp, tblHeader, AEPClassLoader.XSL_TEMPLATE_AEP_HOCH));
        add(new TrefferLabel("treffer", getDefaultModel()));
    }
}
