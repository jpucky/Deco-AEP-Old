// $Log: ViewListChargen.java,v $
// Revision 1.15  2014/08/19 09:54:13  tw
// csv-button limiterung.
//
// Revision 1.14  2014/06/28 16:00:39  tw
// Benutzer bearbeiten finalisiert. Busy-Indicator f. Statistik u. Benutzer Bearbeiten.
//
// Revision 1.13  2014/01/16 17:01:52  tw
// Listenausgabe als pdf. Abverkauf-Reimporte
//
// Revision 1.12  2013/12/05 20:10:53  tw
// Bugfix Suche. Performancesteigerung: Defektenliste.
//
// Revision 1.11  2013/12/05 15:45:39  tw
// .csv - Listen fuer alle Listen
//
// Revision 1.10  2013/12/02 21:54:12  tw
// Spalten: KundenNr, LieferscheinNr werden fuer alle Lieferanten in allen Listen gefiltert.
//
// Revision 1.9  2013/12/02 12:33:53  tw
// Neue Schnittstelle um unnoetige Spalten auszublenden.
//
// Revision 1.8  2013/11/25 16:01:30  tw
// ueberfluessige listen-initalisierung entfernt.
//
// Revision 1.7  2013/11/25 15:33:52  tw
// Reimporte: limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.6  2013/11/25 12:22:16  tw
// Chargendoku: limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.5  2013/11/18 10:45:56  tw
// Multifilter an die Statistik angebunden.
//
// Revision 1.4  2013/11/15 21:48:03  tw
// Tabellenspalten werden per seperater DB-Funktion abgerufen.
//
// Revision 1.3  2013/11/15 09:27:52  tw
// Reimporte u. Chargen ans Berechtigungssystem angeflanscht.
//
// Revision 1.2  2013/11/09 19:01:12  tw
// Defektenliste, Zusammenfassungsarbeiten, Nullponter-Exception Sortierung behoben.
//
// Revision 1.1  2013/11/08 09:47:11  tw
// chargenliste implementiert.
//
// Revision 1.1  2013/11/07 22:43:04  tw
// Hinweis Listentyp eingebaut.
//
//

package de.decodetron.tab.statistik.chargen;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
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
 * @author Thomas Winter
 * @since 07.11.2013
 */
public class ViewListChargen extends StatistikListPanel {

    private DataRecord tblHeader;
    private ChargenDataProvider dp;

    public ViewListChargen(String id, IModel<?> model) {
        super(id, model);
        setOutputMarkupId(true);
        // initChargenListe();

        // ValueMap map = (ValueMap) getDefaultModelObject();
        // List<DataRecord> list = (List<DataRecord>) map.get(Const.KEY_LISTEN_DATA);
        tblHeader = AEPApplication.get().getDBChargenFilter().getColumNamesAsDataRecord(Const.TABLENAME_CHARGEN);

        // UniversalDataProvider dp = new UniversalDataProvider(list, model);
        dp = new ChargenDataProvider(model);
        final ListSortable dataView;
        add(dataView = new ListSortable("chargenliste", dp, tblHeader));
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
        add(new CreatePdfLink("pdfdownload", getDefaultModel(), dp, tblHeader,
                AEPClassLoader.XSL_TEMPLATE_AEP_QUER_CHARGEN));
        add(new TrefferLabel("treffer", getDefaultModel()));
    }

}
