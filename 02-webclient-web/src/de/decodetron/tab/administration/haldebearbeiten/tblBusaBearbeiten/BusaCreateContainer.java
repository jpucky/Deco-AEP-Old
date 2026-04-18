// $Log: BusaCreateContainer.java,v $
// Revision 1.1  2015/03/24 23:21:20  tw
// CR Feng-ID: 3947#7
//
//

package de.decodetron.tab.administration.haldebearbeiten.tblBusaBearbeiten;

import java.util.HashMap;
import java.util.Set;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;

import de.decodetron.Const;
import de.decodetron.bo.HaldeBuchungssatzScan;
import de.decodetron.event.AbstractEvent;
import de.decodetron.event.ChangeEvent;
import de.decodetron.event.EventListenerInterface;
import de.decodetron.tab.administration.haldebearbeiten.HaldeBearbeitenModel;

/**
 * Ein Container um den Link und das Label Ein- und Ausbzublenden. Ist sichtbar, wenn eine
 * Fundstelle selektiert ist UND noch keine geänderten Datensätze vorhanden sind.
 * 
 * @author Thomas Winter
 * @since 24.03.2015
 */
public class BusaCreateContainer extends WebMarkupContainer implements EventListenerInterface {

    public BusaCreateContainer(String id, HaldeBearbeitenModel model) {
        super(id, Model.of(model));
        add(new BusaCreate("createBusa", model));
        setOutputMarkupPlaceholderTag(true);
    }

    @Override
    public boolean isVisible() {
        HaldeBearbeitenModel hbm = ((HaldeBearbeitenModel) getDefaultModelObject());
        HashMap<String, HaldeBuchungssatzScan> hm = hbm.getBuchungssaetzeChanged();
        Set<String> keyset = hm.keySet();

        boolean changedDataEmpty = keyset.isEmpty();
        boolean fsClicked = hbm.getClickedFundstelle() != null ? true : false;

        return fsClicked && changedDataEmpty;
    }

    @Override
    public void notifyAjaxEvent(AbstractEvent event) {
        if (event instanceof ChangeEvent) {
            ChangeEvent ce = ((ChangeEvent) event);
            String ident = ce.getIdentifier();

            if (Const.KEY_HALDE_HALDEFILE_KLICK.equals(ident)) {
                ce.update(BusaCreateContainer.this);
            } else if (Const.KEY_HALDE_BUSA_MODIFIED.equals(ident)) {
                ce.update(BusaCreateContainer.this);
            } else if (Const.KEY_HALDE_BUSA_DELETED.equals(ident)) {
                ce.update(BusaCreateContainer.this);
            }else if (Const.KEY_HALDE_BUSA_SAVED.equals(ident)) {
                ce.update(BusaCreateContainer.this);
            }
        }
    }

}
