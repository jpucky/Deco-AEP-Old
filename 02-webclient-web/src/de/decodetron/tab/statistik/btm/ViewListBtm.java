// $Log: ViewListBtm.java,v $
// Revision 1.29  2016/01/31 17:00:16  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.28  2014/08/19 09:54:13  tw
// csv-button limiterung.
//
// Revision 1.27  2014/06/28 16:00:38  tw
// Benutzer bearbeiten finalisiert. Busy-Indicator f. Statistik u. Benutzer Bearbeiten.
//
// Revision 1.26  2014/01/22 15:33:51  tw
// Tests Hinweisdialog.
//
// Revision 1.25  2014/01/20 14:17:59  tw
// PDF Footerkorrektur.
//
// Revision 1.24  2014/01/17 21:45:55  tw
// Bufix: ClubKz auch aus dem Suchbereich entfernt.
//
// Revision 1.23  2014/01/17 21:39:27  tw
// Bufix: ClubKz auch aus dem Suchbereich entfernt.
//
// Revision 1.22  2014/01/17 10:35:47  tw
// Listenausgabe als pdf.
//
// Revision 1.21  2014/01/16 17:01:52  tw
// Listenausgabe als pdf. Abverkauf-Reimporte
//
// Revision 1.20  2013/12/05 20:10:53  tw
// Bugfix Suche. Performancesteigerung: Defektenliste.
//
// Revision 1.19  2013/12/05 15:45:39  tw
// .csv - Listen fuer alle Listen
//
// Revision 1.18  2013/12/04 14:17:19  tw
// csv-liste fuer btm implementiert.
//
// Revision 1.17  2013/12/03 20:45:47  tw
// Vorbereitung: .csv, .pdf - button
//
// Revision 1.16  2013/12/02 21:58:27  tw
// Spalten: KundenNr, LieferscheinNr werden fuer alle Lieferanten in allen Listen gefiltert.
//
// Revision 1.15  2013/12/02 17:33:58  tw
// Vorbereitungen fuer gezieltes Spaltenfiltern.
//
// Revision 1.14  2013/12/02 12:33:53  tw
// Neue Schnittstelle um unnoetige Spalten auszublenden.
//
// Revision 1.13  2013/11/25 16:01:30  tw
// ueberfluessige listen-initalisierung entfernt.
//
// Revision 1.12  2013/11/21 22:10:45  tw
// Limitierter Datenausgabe: Anbindung Btm-Liste erster Wurf.
//
// Revision 1.11  2013/11/21 17:40:46  tw
// Vorbereitung: neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.10  2013/11/18 10:45:55  tw
// Multifilter an die Statistik angebunden.
//
// Revision 1.9  2013/11/15 21:48:03  tw
// Tabellenspalten werden per seperater DB-Funktion abgerufen.
//
// Revision 1.8  2013/11/14 15:49:40  tw
// Filter - Schnittstellennormalisierung.
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
// Revision 1.4  2013/11/13 15:59:02  tw
// Vorbereitung: Datenfilterung.
//
// Revision 1.3  2013/11/09 19:01:12  tw
// Defektenliste, Zusammenfassungsarbeiten, Nullponter-Exception Sortierung behoben.
//
// Revision 1.2  2013/11/08 09:48:53  tw
// chargenliste implementiert.
//
// Revision 1.1  2013/11/08 00:27:02  tw
// BTM-Liste implementiert.
//
//

package de.decodetron.tab.statistik.btm;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.util.encoding.UrlEncoder;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.value.ValueMap;

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
 * @since 08.11.2013
 */
public class ViewListBtm extends StatistikListPanel {

    private BtmDataProvider dp;
    private DataRecord tblHeader;
    private static Logger log = Logger.getLogger(ViewListBtm.class);

    public ViewListBtm(String id, IModel<?> model) {
        super(id, model);
        setOutputMarkupId(true);

        tblHeader = AEPApplication.get().getDBBtmFilter().getColumNamesAsDataRecord(Const.TABLENAME_BTM);

        dp = new BtmDataProvider(model);
        final ListSortable dataView;
        add(dataView = new ListSortable("btmliste", dp, tblHeader));
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
        // add(new TestLink("testdownload", getDefaultModel()));
    }

    @Deprecated
    private class TestLink extends Link {

        Long hits = null;
        IModel model = null;

        public TestLink(String id, IModel mm) {
            super(id, new LoadableDetachableModel<File>() {
                protected File load() {
                    return new File("D:\\tmp\\pwd-otdb.jpg");
                }
            });
            model = mm;
            add(new AttributeModifier("onclick", "return alert('Ergebnis einschränken: " + hits + " ');") {
                @Override
                public boolean isEnabled(Component component) {
                    ValueMap vm = (ValueMap) model.getObject();
                    hits = (Long) vm.get(Const.KEYHITSPERPAGE);
                    return hits > 100;
                }
            });
        }

        @Override
        // public void onClick(AjaxRequestTarget target) {
        public void onClick() {
            ValueMap vm = (ValueMap) model.getObject();
            hits = (Long) vm.get(Const.KEYHITSPERPAGE);
            System.out.println("Download here ...: " + hits);

            if (hits < 100) {

                final File file = (File) getModelObject();

                String fileName = UrlEncoder.QUERY_INSTANCE.encode(file.getName(), getRequest().getCharset());

                IResourceStream resourceStream = new FileResourceStream(new org.apache.wicket.util.file.File(file));
                getRequestCycle().scheduleRequestHandlerAfterCurrent(new ResourceStreamRequestHandler(resourceStream) {
                    @Override
                    public void respond(IRequestCycle requestCycle) {
                        super.respond(requestCycle);
                        if (false) {
                            Files.remove(file);
                        }
                    }
                }.setFileName(file.getName()).setContentDisposition(ContentDisposition.ATTACHMENT));
            } else {
                // add(new AttributeModifier("onclick", "return alert('User selected: " + hits +
                // " ');"));
            }

            // target.add(ViewListBtm.this);
        }

    }

    private class AlertMessage extends Label {

        public AlertMessage(String id, IModel<?> model) {
            super(id, model);
            setEscapeModelStrings(false);
            setDefaultModel(new Model<String>() {
                public String getObject() {
                    return "alert('Some message');";
                }
            });
            setVisible(false);
        }
    }

    /**
     * Eigener CSVLink mit erhöhter Stepweite f. bessere Performance.
     */
    private class TestDownload extends DownloadLink {

        public TestDownload(String id) {
            super(id, new AbstractReadOnlyModel<File>() {
                @Override
                public File getObject() {
                    return new File("D:\\Daten\\Dokumente\\Schluesselverwaltung\\Keystore\\pwd-otdb.jpg");
                }
            });

            setDeleteAfterDownload(true);
        }
    }
}
