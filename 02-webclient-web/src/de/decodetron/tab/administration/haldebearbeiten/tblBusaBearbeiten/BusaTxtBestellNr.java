// $Log: BusaTxtBestellNr.java,v $
// Revision 1.6  2015/04/13 12:13:18  tw
// CR Feng-ID: 3950#2
//
// Revision 1.5  2015/03/19 13:06:21  tw
// CR Feng-ID: 3947#4
//
// Revision 1.4  2015/03/18 22:40:25  tw
// CR Feng-ID: 3947#4
//
// Revision 1.3  2015/02/27 09:35:36  tw
// Haldenbearbeitung: Datum-Musterpruefung implementiert.
//
// Revision 1.2  2015/02/26 12:46:46  tw
// Haldenbearbeitung: Fehlerpruefung, Bugfixing.
//
// Revision 1.1  2015/02/19 14:33:27  tw
// Haldenbearbeitung: Aenderung des Speicherverzeichnisses.
//
//

package de.decodetron.tab.administration.haldebearbeiten.tblBusaBearbeiten;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import de.decodetron.Const;
import de.decodetron.bo.HaldeBuchungssatzScan;
import de.decodetron.event.ChangeEvent;

/**
 * @author Thomas Winter
 * @since 19.02.2015
 */
public class BusaTxtBestellNr extends TextField<HaldeBuchungssatzScan> {

    public BusaTxtBestellNr(String id, final HaldeBuchungssatzScan busa) {

        super(id, new PropertyModel<HaldeBuchungssatzScan>(busa, id));
        add(new AjaxFormComponentUpdatingBehavior("onChange") {

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                BusaTxtBestellNr.this.add(AttributeModifier.replace("class", Model.of("")));
                target.add(BusaTxtBestellNr.this);

                new ChangeEvent(BusaTxtBestellNr.this, target, busa, Const.KEY_HALDE_BUSA_MODIFIED).fire();
            }

//            @Override
//            protected void onError(AjaxRequestTarget target, RuntimeException e) {
//
//                BusaTxtBestellNr.this.add(AttributeModifier.replace("class", Model.of("redalert")));
//                target.add(BusaTxtBestellNr.this);
//
//                new ChangeEvent(BusaTxtBestellNr.this, target, null, Const.KEY_HALDE_BUSA_ERROROCCURED).fire();
//                super.onError(target, e);
//            }
        });

        add(new NrValidator<HaldeBuchungssatzScan>());
        
        // 18.03.2015 Kein Pflichtfeld!
        //setRequired(true);
    }

    private class NrValidator<T> implements IValidator<T> {
        public void validate(IValidatable<T> validatable) {
            String txtValue = (String) validatable.getValue();
            if (!txtValue.matches(Const.REG_EX_ISNUMBER)) {
                validatable.error(new ValidationError().addKey("error.notanumber"));
            }
        }
    }

}
