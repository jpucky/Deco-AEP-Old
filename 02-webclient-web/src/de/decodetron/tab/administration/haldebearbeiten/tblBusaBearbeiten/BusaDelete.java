// $Log: BusaDelete.java,v $
// Revision 1.1  2015/03/24 23:21:20  tw
// CR Feng-ID: 3947#7
//
//

package de.decodetron.tab.administration.haldebearbeiten.tblBusaBearbeiten;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.Model;

import de.decodetron.AEPApplication;
import de.decodetron.Const;
import de.decodetron.bo.HaldeBuchungssatzScan;
import de.decodetron.bo.HaldeFile;
import de.decodetron.dao.halde.HaldeDAOI;
import de.decodetron.event.AbstractEvent;
import de.decodetron.event.ChangeEvent;
import de.decodetron.event.EventListenerInterface;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.administration.haldebearbeiten.HaldeBearbeitenModel;

/**
 * Der "-" Button um alle Datensätze zu löschen. Ist nur sichtbar, wenn geänderte Datensätze
 * vorhanden sind.
 * 
 * @author Thomas Winter
 * @since 24.03.2015
 */
public class BusaDelete extends AjaxLink<HaldeBearbeitenModel> { // implements EventListenerInterface {

    public BusaDelete(String id, HaldeBearbeitenModel model) {
        super(id, Model.of(model));
        //setOutputMarkupPlaceholderTag(true);
    }

    private HaldeDAOI getContactsDB() {
        return AEPApplication.get().getDBHalde(getModelObject().getSelectedBelegart());
    }

//    @Override
//    public boolean isVisible() {
//        List<HaldeBuchungssatzScan> lHaldeBS = getModelObject().getHaldeBuchungssaetze(); 
//        boolean buchngssaetzeVorhanden = lHaldeBS.size() > 0;
//        return buchngssaetzeVorhanden;
//    }
    
//    @Override
//    public void notifyAjaxEvent(AbstractEvent event) {
//        ChangeEvent ce = ((ChangeEvent) event);
//        String ident = ce.getIdentifier();
//
//        if (Const.KEY_HALDE_BUSA_ADDED.equals(ident)) {
//            ce.update(BusaDelete.this);
//        } else if (Const.KEY_HALDE_BUSA_MODIFIED.equals(ident)) {
//            ce.update(BusaDelete.this);
//        } 
//    }

    @Override
    public void onClick(AjaxRequestTarget target) {
        HaldeFile haldeFile = getModelObject().getClickedFundstelle();
        getContactsDB().removeAllDatensaetze(haldeFile, LoginSession.get().getUser().getLogin());

        target.add(BusaDelete.this);
        new ChangeEvent(BusaDelete.this, target, null, Const.KEY_HALDE_BUSA_DELETED).fire();
        
        //getModelObject().addHaldeBuchungssaetze(new ArrayList<HaldeBuchungssatzScan>());
        //getModelObject().addHaldeBuchungssaetzeOrig(new ArrayList<HaldeBuchungssatzScan>());
        //getModelObject().initHaldeBuchungssaetzeChanged();
    }

}
