// $Log: HaldeFileDataProvider.java,v $
// Revision 1.3  2015/03/23 22:47:17  tw
// CR Feng-ID: 3947#6
//
// Revision 1.2  2015/03/19 22:41:05  tw
// CR Feng-ID: 3947#4
//
// Revision 1.1  2015/03/17 22:10:36  tw
// CR Feng-ID: 3947
//
// Revision 1.1  2015/02/09 12:45:36  tw
// Haldenbearbeitung: Bugfix. Aufraeumarbeiten.
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

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPApplication;
import de.decodetron.Const;
import de.decodetron.bo.HaldeFile;
import de.decodetron.dao.halde.HaldeDAOI;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.administration.haldebearbeiten.HaldeBearbeitenModel;

/**
 * @author Thomas Winter
 * @since 29.01.2015
 */
public class HaldeFileDataProvider extends SortableDataProvider<HaldeFile, String> {

    private ValueMap vm;
    private HaldeBearbeitenModel hbm;

    public HaldeFileDataProvider(IModel model, HaldeBearbeitenModel hm) {
        vm = (ValueMap) model.getObject();
        this.hbm = hm;
    }

    protected HaldeDAOI getContactsDB() {
        return AEPApplication.get().getDBHalde(this.hbm.getSelectedBelegart());
    }

    @Override
    public Iterator<HaldeFile> iterator(long first, long count) {
        String login = LoginSession.get().getUser().getLogin();
        List<HaldeFile> list = getContactsDB().getAllHaldeFiles(login).subList((int) first, (int) first + (int) count);
        return list.iterator();
    }

    @Override
    public long size() {
        String login = LoginSession.get().getUser().getLogin();
        long size = (long) getContactsDB().getAllHaldeFiles(login).size();
        vm.put(Const.KEYDOCTOTAL, size);
        return size;
    }

    @Override
    public IModel<HaldeFile> model(final HaldeFile hf) {
        return new HaldeFileModelObject(hf);
    }

}
