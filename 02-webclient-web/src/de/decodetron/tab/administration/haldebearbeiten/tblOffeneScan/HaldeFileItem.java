// $Log: HaldeFileItem.java,v $
// Revision 1.2  2015/07/21 19:45:30  tw
// import entfernt
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
// Revision 1.1  2015/01/30 02:44:52  tw
// Haldenbearbeitung 1. Wurf.
//
//

package de.decodetron.tab.administration.haldebearbeiten.tblOffeneScan;

import java.io.Serializable;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.decodetron.Const;
import de.decodetron.event.ChangeEvent;
import de.decodetron.tab.administration.haldebearbeiten.HaldeBearbeitenModel;

/**
 * @author Thomas Winter
 * @since 29.01.2015
 */
public class HaldeFileItem<HaldeFile> extends Item<HaldeFile> {

    private boolean highlite = false;

    /**
     * 
     * @param id
     * @param index
     * @param model
     *            ist vom Typ: DetachableHaldeFileModel
     */
    public HaldeFileItem(String id, int index, IModel<HaldeFile> model, final HaldeBearbeitenModel hbm) {
        super(id, index, model);
        add(AttributeModifier.append("style", new Model<Serializable>() {
            public Serializable getObject() {
                return isHighlighted() ? "background-color:#80b6ed;" : "";
            }
        }));
        add(new AjaxEventBehavior("onclick") {
            protected void onEvent(AjaxRequestTarget target) {

                de.decodetron.bo.HaldeFile fs = (de.decodetron.bo.HaldeFile) getDefaultModelObject();
                
                // Sichern des alten Wertes
                hbm.addClickedFundstelleOld(hbm.getClickedFundstelle());                
                hbm.addClickedFundstelle(fs);

                HaldeFileItem<HaldeFile> oldItem = (HaldeFileItem<HaldeFile>) hbm
                        .getHaldeFileItemHighlitable();
                HaldeFileItem<HaldeFile> newItem = HaldeFileItem.this;

                if ((oldItem != null) && (oldItem != newItem)) {
                    oldItem.removeHighlight();
                }

                if ((newItem != null) && (newItem != oldItem)) {
                    newItem.toggleHighlite();
                    hbm.addHaldeFileItemHighlitable((HaldeFileItem<de.decodetron.bo.HaldeFile>) newItem);
                    new ChangeEvent(HaldeFileItem.this, target, fs, Const.KEY_HALDE_HALDEFILE_KLICK).fire();
                }
            }
        });
    }

    public boolean toggleHighlite() {
        return highlite = !highlite;
    }

    public boolean isHighlighted() {
        return highlite;
    }

    public void removeHighlight() {
        highlite = false;
    }
}
