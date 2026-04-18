// $Log: ViewListTierarznei.java,v $
// Revision 1.8  2014/08/19 09:54:14  tw
// csv-button limiterung.
//
// Revision 1.7  2014/06/28 16:00:40  tw
// Benutzer bearbeiten finalisiert. Busy-Indicator f. Statistik u. Benutzer Bearbeiten.
//
// Revision 1.6  2014/01/17 10:35:47  tw
// Listenausgabe als pdf.
//
// Revision 1.5  2013/12/05 20:10:53  tw
// Bugfix Suche. Performancesteigerung: Defektenliste.
//
// Revision 1.4  2013/12/05 15:45:39  tw
// .csv - Listen fuer alle Listen
//
// Revision 1.3  2013/12/02 21:54:12  tw
// Spalten: KundenNr, LieferscheinNr werden fuer alle Lieferanten in allen Listen gefiltert.
//
// Revision 1.2  2013/12/02 12:33:54  tw
// Neue Schnittstelle um unnoetige Spalten auszublenden.
//
// Revision 1.1  2013/11/28 13:42:28  tw
// Anbindung Liste:Tierarznei.
//
//

package de.decodetron.tab.statistik.tierarznei;

import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
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
 * @since 28.11.2013
 */
public class ViewListTierarznei extends StatistikListPanel {

    public ViewListTierarznei(String id, IModel<?> model) {
        super(id, model);
        setOutputMarkupId(true);

        DataRecord tblHeader = AEPApplication.get().getDBTierarzneiFilter()
                .getColumNamesAsDataRecord(Const.TABLENAME_TIERARZNEI);

        TierarzneiDataProvider dp = new TierarzneiDataProvider(model);

        final ListSortable dataView;
        add(dataView = new ListSortable("tierarzneiliste", dp, tblHeader));
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
        add(new CreatePdfLink("pdfdownload", getDefaultModel(), dp, tblHeader, AEPClassLoader.XSL_TEMPLATE_AEP_QUER_13));
        add(new TrefferLabel("treffer", getDefaultModel()));
    }
}
