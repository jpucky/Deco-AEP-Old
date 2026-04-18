// $Log: BusaDeleteContainer.java,v $
// Revision 1.1  2015/03/24 23:21:20  tw
// CR Feng-ID: 3947#7
//
//

package de.decodetron.tab.administration.haldebearbeiten.tblBusaBearbeiten;

import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;

import de.decodetron.Const;
import de.decodetron.bo.HaldeBuchungssatzScan;
import de.decodetron.event.AbstractEvent;
import de.decodetron.event.ChangeEvent;
import de.decodetron.event.EventListenerInterface;
import de.decodetron.tab.administration.haldebearbeiten.HaldeBearbeitenModel;

/**
 * @author Thomas Winter
 * @since 25.03.2015
 */
public class BusaDeleteContainer extends WebMarkupContainer implements EventListenerInterface {

    public BusaDeleteContainer(String id, HaldeBearbeitenModel model) {
        super(id, Model.of(model));
        add(new BusaDelete("deleteAllBusa", model));
        setOutputMarkupPlaceholderTag(true);
    }

    @Override
    public boolean isVisible() {
        HaldeBearbeitenModel hbm = ((HaldeBearbeitenModel) getDefaultModelObject());
        List<HaldeBuchungssatzScan> lHaldeBS = hbm.getHaldeBuchungssaetze();
        boolean buchngssaetzeVorhanden = lHaldeBS.size() > 0;
        return buchngssaetzeVorhanden;
    }

    @Override
    public void notifyAjaxEvent(AbstractEvent event) {
        ChangeEvent ce = ((ChangeEvent) event);
        String ident = ce.getIdentifier();

        if (Const.KEY_HALDE_BUSA_ADDED.equals(ident)) {
            ce.update(BusaDeleteContainer.this);
        } else if (Const.KEY_HALDE_BUSA_MODIFIED.equals(ident)) {
            ce.update(BusaDeleteContainer.this);
        } else if (Const.KEY_HALDE_BUSA_DELETED.equals(ident)) {
            ce.update(BusaDeleteContainer.this);
        } else if (Const.KEY_HALDE_BUSA_SAVED.equals(ident)) {
            ce.update(BusaDeleteContainer.this);
        }
    }

}
