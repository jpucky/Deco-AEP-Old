// $Log: TransfusionPdfLink.java,v $
// Revision 1.1  2019/07/21 11:25:07  tw
// Transfusion, Button fuer leere Abfrage eingebaut.
//
//

package de.decodetron.tab.statistik;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.time.Duration;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.Const;
import de.decodetron.bo.DataRecord;
import de.decodetron.bo.FilterItemList;
import de.decodetron.bo.Section;
import de.decodetron.data.Util;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.statistik.transfusion.TransfusionFilterAttr;

/**
 * @author Thomas Winter
 * @since 20.07.2019
 */
public class TransfusionPdfLink extends DownloadLink {
    private IModel<?> model = null;

    public TransfusionPdfLink(String id, final IModel<?> mm, final SortableDataProvider<Serializable, String> dp,
            final DataRecord tblHeader, final String xslTemplate) {

        super(id, new AbstractReadOnlyModel<File>() {
            @Override
            public File getObject() {
                Section sec = LoginSession.get().getCurrentSection();
                ValueMap map = (ValueMap) mm.getObject();                
                List<TxtFilter> listTxtFields = (List<TxtFilter>) map.get(Const.KEY_LIST_FILTERSUCHE);
                FilterItemList fList = Util.mapSearchFields(listTxtFields);
                
                Util util = new Util();
                File file = util.createEmptyTransfusionPDF(fList, tblHeader, sec, xslTemplate);
                return file;
            }
        });

        model = mm;
        setOutputMarkupId(true);
        setDeleteAfterDownload(true);
        setCacheDuration(Duration.NONE);
    }

    @Override
    public boolean isVisible() {
        
        ValueMap vm = (ValueMap) model.getObject();
        Long hits = (Long) vm.get(Const.KEYHITSPERPAGE);
        hits = hits == null ? 0 : hits;
        return hits == 0 && LoginSession.get().getUser().getIsSuperAdmin();
    }
}
