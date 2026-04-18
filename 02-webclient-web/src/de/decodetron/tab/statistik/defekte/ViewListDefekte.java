// $Log: ViewListDefekte.java,v $
// Revision 1.28  2020/02/28 22:13:06  tw
// Doppelten Defekte-Request entfernt. DB-Anbindung ueber neueren Treiber.
//
// Revision 1.27  2020/02/26 19:24:12  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.26  2014/08/19 09:54:14  tw
// csv-button limiterung.
//
// Revision 1.25  2014/08/12 21:41:07  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.24  2014/08/08 16:05:02  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.23  2014/06/28 16:00:39  tw
// Benutzer bearbeiten finalisiert. Busy-Indicator f. Statistik u. Benutzer Bearbeiten.
//
// Revision 1.22  2014/05/13 01:21:52  tw
// Dataprovider Cache - Optimierungen.
//
// Revision 1.21  2014/04/01 01:00:07  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.20  2014/03/31 21:46:25  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.19  2014/03/28 23:02:49  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.18  2014/03/28 17:23:44  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.17  2014/03/27 15:36:23  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Tabellengestaltung.
//
// Revision 1.16  2014/01/22 15:33:51  tw
// Tests Hinweisdialog.
//
// Revision 1.15  2014/01/16 17:01:53  tw
// Listenausgabe als pdf. Abverkauf-Reimporte
//
// Revision 1.14  2014/01/16 12:34:51  tw
// Anzeige Treffermenge. Pdfgenerierung.
//
// Revision 1.13  2014/01/15 21:44:32  tw
// Bufix: Beseitigung aller KZs. Verbergen alle bisherigen Aenderungen.
//
// Revision 1.12  2014/01/10 01:16:07  tw
// Statistik: Listenausgabe als pdf. Erster Durchstich d. Defektenliste.
//
// Revision 1.11  2013/12/05 20:16:30  tw
// Bugfix Suche. Performancesteigerung: Defektenliste.
//
// Revision 1.10  2013/12/05 20:10:53  tw
// Bugfix Suche. Performancesteigerung: Defektenliste.
//
// Revision 1.9  2013/12/05 15:45:39  tw
// .csv - Listen fuer alle Listen
//
// Revision 1.8  2013/12/02 21:54:12  tw
// Spalten: KundenNr, LieferscheinNr werden fuer alle Lieferanten in allen Listen gefiltert.
//
// Revision 1.7  2013/12/02 12:33:54  tw
// Neue Schnittstelle um unnoetige Spalten auszublenden.
//
// Revision 1.6  2013/11/25 16:01:30  tw
// ueberfluessige listen-initalisierung entfernt.
//
// Revision 1.5  2013/11/25 14:55:22  tw
// Defektenliste: limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.4  2013/11/18 10:45:56  tw
// Multifilter an die Statistik angebunden.
//
// Revision 1.3  2013/11/15 21:48:03  tw
// Tabellenspalten werden per seperater DB-Funktion abgerufen.
//
// Revision 1.2  2013/11/14 16:00:06  tw
// Defektenliste ans Berechtigungssystem angebunden.
//
// Revision 1.1  2013/11/09 19:01:12  tw
// Defektenliste, Zusammenfassungsarbeiten, Nullponter-Exception Sortierung behoben.
//
//

package de.decodetron.tab.statistik.defekte;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPClassLoader;
import de.decodetron.Const;
import de.decodetron.bo.DataRecord;
import de.decodetron.bo.User;
import de.decodetron.data.Util;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.statistik.CreatePdfLink;
import de.decodetron.tab.statistik.StatistikListPanel;
import de.decodetron.tab.statistik.TrefferLabel;

/**
 * @author Thomas Winter
 * @since 09.11.2013
 */
public class ViewListDefekte extends StatistikListPanel {

    
    public ViewListDefekte(String id, IModel<?> model) {
        super(id, model);
        setOutputMarkupId(true);
        DefekteDataProvider dp = new DefekteDataProvider((IModel<ValueMap>) model);

        final DataTable<DataRecord, String> dataTable;
        List<IColumn<DataRecord, String>> columns = new ArrayList<IColumn<DataRecord, String>>();

        int colNr = -1; // RecId muss dargestellt werden, da die Column sie ausblendet!
        while (colNr++ < dp.getTableHeader().getSize() - 1) {
            String colName = dp.getTableHeader().getColItem(colNr);
            ResourceModel colLabel = new ResourceModel("labeltable." + colName, colName);
            columns.add(new StatistikColumn(colLabel, String.valueOf(colNr), colName));
        }

        // Even Odd
        add(dataTable = new DataTable<DataRecord, String>("table", columns, dp, Const.ITEMS_PER_PAGE) {
            @Override
            protected Item<DataRecord> newRowItem(final String id, final int index, final IModel<DataRecord> model) {
                return new OddEvenItem<DataRecord>(id, index, model);
            }
        });

        // Tabellenkopf (Trefferanzeige)
        User user = LoginSession.get().getUser();
        dataTable.addTopToolbar(new HeadersToolbar<String>(dataTable, dp));
        add(new PagingNavigator("navigator", dataTable));
        add(new MyCreateCsvLink("csvdownload", dp));
        add(new CreatePdfLink("pdfdownload", getDefaultModel(), dp, dp.getTableHeader(),
                user.getIsLieferant() ? AEPClassLoader.XSL_TEMPLATE_AEP_HOCH_DEFEKT_LIEF
                        : AEPClassLoader.XSL_TEMPLATE_AEP_HOCH_DEFEKT));
        add(new TrefferLabel("treffer", getDefaultModel()));
        //add(new Label("info", dp.size()));
    }

    /**
     * Eigener CSVLink mit erhöhter Stepweite f. bessere Performance.
     */
    private class MyCreateCsvLink extends DownloadLink {

        public MyCreateCsvLink(String id, final DefekteDataProvider dp) {
            super(id, new AbstractReadOnlyModel<File>() {
                @Override
                public File getObject() {
                    String sec = LoginSession.get().getCurrentSection().getKz();
                    dp.setItemPerPage(Const.ITEMS_PER_DOWNLOAD); // set
                    File file = new Util().createCSV(dp, dp.getTableHeader(), sec, Const.ITEMS_PER_DOWNLOAD);
                    dp.setItemPerPage(Const.ITEMS_PER_PAGE); // reset
                    return file;
                }
            });

            setDeleteAfterDownload(true);
        }
        
        @Override
        public boolean isVisible() {
            ValueMap vm = (ValueMap) ViewListDefekte.this.getDefaultModelObject();
            Long hits = (Long) vm.get(Const.KEYHITSPERPAGE);
            hits = hits == null ? 0 : hits;
            return hits < 32768;
        }
    }
}
