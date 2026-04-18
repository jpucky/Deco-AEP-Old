// $Log: ViewListValuta.java,v $
// Revision 1.9  2020/02/26 19:24:13  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.8  2014/08/19 09:54:14  tw
// csv-button limiterung.
//
// Revision 1.7  2014/06/28 16:00:40  tw
// Benutzer bearbeiten finalisiert. Busy-Indicator f. Statistik u. Benutzer Bearbeiten.
//
// Revision 1.6  2014/01/17 10:35:48  tw
// Listenausgabe als pdf.
//
// Revision 1.5  2013/12/05 20:10:53  tw
// Bugfix Suche. Performancesteigerung: Defektenliste.
//
// Revision 1.4  2013/12/05 15:45:40  tw
// .csv - Listen fuer alle Listen
//
// Revision 1.3  2013/12/02 21:54:12  tw
// Spalten: KundenNr, LieferscheinNr werden fuer alle Lieferanten in allen Listen gefiltert.
//
// Revision 1.2  2013/12/02 12:33:54  tw
// Neue Schnittstelle um unnoetige Spalten auszublenden.
//
// Revision 1.1  2013/11/29 12:32:38  tw
// Neue Liste: Valuta.
//
//

package de.decodetron.tab.statistik.valuta;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
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
 * @since 29.11.2013
 */
public class ViewListValuta extends StatistikListPanel {
    
    public ViewListValuta(String id, IModel<?> model) {
        super(id, model);
        setOutputMarkupId(true);

        DataRecord tblHeader = AEPApplication.get().getDBValutaFilter()
                .getColumNamesAsDataRecord(Const.TABLENAME_VALUTA);

        ValutaDataProvider dp = new ValutaDataProvider(model);

        final ListSortable dataView;
        add(dataView = new ListSortable("valutaliste", dp, tblHeader));
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
