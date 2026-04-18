// $Log: CreateCsvLink.java,v $
// Revision 1.5  2019/07/17 20:19:41  tw
// CR: PDF-Tabellenkoepfe: Ansicht wie auf der Webseite.  CSV - Timer 20 Sek Limit beseitigt (CreateCsvLink.setCacheDuration)
//
// Revision 1.3  2014/08/19 21:51:56  tw
// Aep-Benutzerr-Rechteverwaltung: Layout.
//
// Revision 1.2  2014/08/19 09:54:13  tw
// csv-button limiterung.
//
// Revision 1.1  2014/01/16 12:34:51  tw
// Anzeige Treffermenge. Pdfgenerierung.
//
//

package de.decodetron.tab.statistik;

import java.io.File;
import java.io.Serializable;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.time.Duration;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.Const;
import de.decodetron.bo.DataRecord;
import de.decodetron.data.Util;
import de.decodetron.security.LoginSession;

/**
 * @author Thomas Winter
 * @since 16.01.2014
 */
public class CreateCsvLink extends DownloadLink {

    private IModel model = null;
    
    public CreateCsvLink(String id, final IModel mm, final SortableDataProvider<Serializable, String> dp, final DataRecord tblHeader) {
        super(id, new AbstractReadOnlyModel<File>() {
            @Override
            public File getObject() {
                String sec = LoginSession.get().getCurrentSection().getKz();
                return new Util().createCSV(dp, tblHeader, sec, Const.ITEMS_PER_PAGE);
            }
        });

        model = mm;
        setDeleteAfterDownload(true);
        setCacheDuration(Duration.NONE);
    }
    
    @Override
    public boolean isVisible() {
        ValueMap vm = (ValueMap) model.getObject();
        Long hits = (Long) vm.get(Const.KEYHITSPERPAGE);
        hits = hits == null ? 0 : hits;
        return hits < 32768;
    }

}
