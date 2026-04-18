// $Log: CreatePdfLink.java,v $
// Revision 1.9  2020/02/26 19:24:10  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.8  2019/07/21 11:23:24  tw
// Umlaute entfernt argh.
//
// Revision 1.7  2019/07/21 11:11:16  tw
// Transfusion, Button fuer leere Abfrage eingebaut.
//
// Revision 1.6  2019/02/05 17:08:57  tw
// Versionsnummer per Hand hinzugefuegt zwecks auslesen per NowNewBookmarker.
//
// Revision 1.5  2014/01/22 15:32:49  tw
// Bugfix Nullpointerexception
//
// Revision 1.4  2014/01/20 14:17:59  tw
// PDF Footerkorrektur.
//
// Revision 1.3  2014/01/17 22:04:44  tw
// PDF-Downloadlimit auf 10000 Datensaetze erhoeht.
//
// Revision 1.2  2014/01/16 17:01:52  tw
// Listenausgabe als pdf. Abverkauf-Reimporte
//
// Revision 1.1  2014/01/16 12:34:51  tw
// Anzeige Treffermenge. Pdfgenerierung.
//
//

package de.decodetron.tab.statistik;

import java.io.File;
import java.io.Serializable;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.time.Duration;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.Const;
import de.decodetron.bo.DataRecord;
import de.decodetron.bo.Section;
import de.decodetron.data.Util;
import de.decodetron.security.LoginSession;

/**
 * Pdf-Download-Button. Blendet sich nur ein, wenn die zu verarbeitenden Datnsätze 10000 nicht
 * überschreiten.
 * 
 * @author Thomas Winter
 * @since 16.01.2014
 */
public class CreatePdfLink extends DownloadLink {

    private IModel model = null;

    public CreatePdfLink(String id, final IModel mm, final SortableDataProvider<Serializable, String> dp,
            final DataRecord tblHeader, final String xslTemplate) {

        super(id, new AbstractReadOnlyModel<File>() {
            @Override
            public File getObject() {
                Section sec = LoginSession.get().getCurrentSection();
                // dp.setItemPerPage(Const.ITEMS_PER_DOWNLOAD); // Häppchen für das Daten-XML!
                Util util = new Util();
                File file = util.createPDF(dp, tblHeader, sec, Const.ITEMS_PER_PAGE, xslTemplate);
                // dp.setItemPerPage(Const.ITEMS_PER_PAGE);
                return file;
            }
        });

        model = mm;

        setOutputMarkupId(true);
        setDeleteAfterDownload(true);
        setCacheDuration(Duration.NONE);

        // Das geht! Es fehlt nur das Ende!
        // add(new AttributeModifier("onclick", "return $.fn.showViewBusy();") {

        // add(new AttributeModifier("onclick",
        // "return alert('Bitte reduzieren Sie das Suchergebnis!');") {
        // @Override
        // public boolean isEnabled(Component component) {
        // ValueMap vm = (ValueMap) model.getObject();
        // Long hits = (Long) vm.get(Const.KEYHITSPERPAGE);
        // if(hits < 100) detachModels();
        // return hits < 100;
        // }
        // });
    }

    @Override
    public boolean isVisible() {
        ValueMap vm = (ValueMap) model.getObject();
        Long hits = (Long) vm.get(Const.KEYHITSPERPAGE);
        hits = hits == null ? 0 : hits;
        return hits < 10000;
    }
}
