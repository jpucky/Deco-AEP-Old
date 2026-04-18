// $Log: HaldeFileTable.java,v $
// Revision 1.4  2015/04/13 14:50:17  tw
// CR Feng-ID: 3950#3
//
// Revision 1.3  2015/03/17 22:10:09  tw
// CR Feng-ID: 3947
//
// Revision 1.2  2015/02/11 14:08:13  tw
// Haldenbearbeitung: Auskommentieren der Ansicht fuer Vorab-Realease.
//
// Revision 1.1  2015/02/09 12:45:36  tw
// Haldenbearbeitung: Bugfix. Aufraeumarbeiten.
//
// Revision 1.1  2015/02/07 17:06:27  tw
// Haldenbearbeitung: Datensatz-Loeschen-Schnittstelle implementiert.
//
// Revision 1.3  2015/02/04 16:23:27  tw
// Haldenbearbeitung: Speichern v. Buchungsdatensaetzen implementiert.
//
// Revision 1.2  2015/02/03 21:45:31  tw
// Haldenbearbeitung: Diverse Layout-Korrekturen an Tabelle "offene Scandateien".
//
// Revision 1.1  2015/02/03 00:52:57  tw
// Haldenbearbeitung: Einlesen v. Archivierungsdatensaetzen ermoeglicht.
//
// Revision 1.4  2015/02/01 11:29:04  tw
// Haldenbearbeitung Bugfix Nummerierung.
//
// Revision 1.3  2015/02/01 11:08:43  tw
// Haldenbearbeitung Korrektur. f. Produktionseinsatz.
//
// Revision 1.2  2015/01/31 17:53:03  tw
// Haldenbearbeitung Korrektur. f. Produktionseinsatz.
//
// Revision 1.1  2015/01/30 02:44:53  tw
// Haldenbearbeitung 1. Wurf.
//
//

package de.decodetron.tab.administration.haldebearbeiten.tblOffeneScan;

import java.text.NumberFormat;
import java.util.Locale;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.decodetron.Const;
import de.decodetron.bo.HaldeFile;
import de.decodetron.data.Util;
import de.decodetron.event.AbstractEvent;
import de.decodetron.event.ChangeEvent;
import de.decodetron.event.EventListenerInterface;
import de.decodetron.tab.administration.haldebearbeiten.HaldeBearbeiten;
import de.decodetron.tab.administration.haldebearbeiten.HaldeBearbeitenModel;
import de.decodetron.tab.recherche.BootstrapPagingNavigator;

/**
 * Wrapper, um die Aktualisierung der Tabelle zu ermöglichen.
 * 
 * @author Thomas Winter
 * @since 29.01.2015
 */
public class HaldeFileTable extends WebMarkupContainer implements EventListenerInterface {

    private HaldeBearbeitenModel hbm;

    public HaldeFileTable(String id, IModel<?> model, HaldeBearbeiten baldebearbeiten, HaldeBearbeitenModel hbm) {
        super(id, model);
        this.hbm = hbm;
        setOutputMarkupId(true);
        HaldeFileViewSortable dataView;
        add(dataView = new HaldeFileViewSortable("pdffiles", model, hbm));
        baldebearbeiten.add(new BootstrapPagingNavigator("navigator", dataView));
    }

    private void updateWraper(ChangeEvent ce) {
        ce.update(HaldeFileTable.this);
    }

    @Override
    public void notifyAjaxEvent(AbstractEvent event) {
        if (event instanceof ChangeEvent) {
            ChangeEvent ce = ((ChangeEvent) event);
            String ident = ce.getIdentifier();

            if (Const.KEY_HALDE_HALDEFILE_KLICK.equals(ident)) {
                updateWraper(ce);
            }
        }
    }

    private class HaldeFileViewSortable extends DataView<HaldeFile> {

        private int nr = 0;
        private HaldeBearbeitenModel hbm;
        private NumberFormat NF = NumberFormat.getInstance(Locale.GERMANY);

        public void renderHead(IHeaderResponse response) {
            response.render(OnDomReadyHeaderItem.forScript("$.fn.hideViewBusy();"));
        }

        protected HaldeFileViewSortable(String id, IModel model, HaldeBearbeitenModel hm) {
            super(id, new HaldeFileDataProvider(model, hm));
            hbm = hm;
            // Kein Itemreuse, sonst funktioniert die Farbänderung nicht!
            setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
            setItemsPerPage(100);
        }

        @Override
        protected void populateItem(final Item<HaldeFile> item) {
            final HaldeFile f = item.getModelObject();
            // item.add(new Label("filenr", Model.of(f.getId())));
            // item.add(new Label("filename", Model.of(f.getFileName())));
            item.add(new FileLabel("filename", item.getModelObject()));
            item.add(new Label("filedate", Model.of(Util.inOutParser(f.getFileChangeDate(), "dd.MM.yyyy hh:mm"))));
            item.add(new Label("filesize", Model.of(Util.formatBytes(Long.valueOf(f.getFileSize()), true, 0))));
            item.add(AttributeModifier.append("class", new Model<String>() {
                public String getObject() {
                    return (item.getIndex() % 2 == 1) ? "dk" : "";
                }
            }));
            item.add(AttributeModifier.append("class", new Model<String>() {
                @Override
                public String getObject() {
                    return f.getBuchungssatzTmpExist() ? "ex" : "";
                }
            }));
            item.add(AttributeModifier.append("class", new Model<String>() {
                @Override
                public String getObject() {
                    return f.getBuchungssatzSaveExist() ? "sv" : "";
                }
            }));
        }

        protected Item<HaldeFile> newItem(String id, int index, final IModel model) {
            return new HaldeFileItem<HaldeFile>(id, index, model, hbm);
        }

        /**
         * Spezielles Filelabel, da der Dateinamen Längenmässig aus der Reihe tanzen kann, erhält er
         * ein "Tooltip".
         */
        private class FileLabel extends Label {

            public FileLabel(String id, final HaldeFile model) {
                super(id, model);
                setDefaultModel(new Model<String>() {
                    public String getObject() {
                        return model.getFileName();
                    }
                });
                add(AttributeModifier.append("title", new Model<String>() {
                    @Override
                    public String getObject() {
                        return model.getFileName();
                    }
                }));
            }
        };

    }

}
