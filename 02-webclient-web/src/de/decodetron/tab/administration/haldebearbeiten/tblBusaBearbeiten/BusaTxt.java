// $Log: BusaTxt.java,v $
// Revision 1.5  2015/03/19 13:06:21  tw
// CR Feng-ID: 3947#4
//
// Revision 1.4  2015/02/27 09:35:36  tw
// Haldenbearbeitung: Datum-Musterpruefung implementiert.
//
// Revision 1.3  2015/02/19 14:33:27  tw
// Haldenbearbeitung: Aenderung des Speicherverzeichnisses.
//
// Revision 1.2  2015/02/17 21:46:26  tw
// Haldenbearbeitung: Erste Implementierung d. Eingabeueberpruefungen.
//
// Revision 1.1  2015/02/16 14:10:01  tw
// Haldenbearbeitung: Autocomplete Aufraeumarbeiten.
//
//

package de.decodetron.tab.administration.haldebearbeiten.tblBusaBearbeiten;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.behavior.AttributeAppender;
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
 * @since 16.02.2015
 */
public class BusaTxt extends TextField<HaldeBuchungssatzScan> {

    private String modelValue = "ChangedNormally";
    private boolean focusGained = false;

    public BusaTxt(String id, final HaldeBuchungssatzScan busa) {

        super(id, new PropertyModel<HaldeBuchungssatzScan>(busa, id));
        add(new AjaxFormComponentUpdatingBehavior("onChange") {

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                BusaTxt.this.add(AttributeModifier.replace("class", Model.of("")));
                target.add(BusaTxt.this);

                new ChangeEvent(BusaTxt.this, target, busa, Const.KEY_HALDE_BUSA_MODIFIED).fire();
            }

            @Override
            protected void onError(AjaxRequestTarget target, RuntimeException e) {

                BusaTxt.this.add(AttributeModifier.replace("class", Model.of("redalert")));
                target.add(BusaTxt.this);

                // System.out.println("Error-Objekt: \"" + getModelObject() + "\"");
                new ChangeEvent(BusaTxt.this, target, null, Const.KEY_HALDE_BUSA_ERROROCCURED).fire();
                super.onError(target, e);
            }
        });

        /**
         * 
         * </pre> add(new AjaxEventBehavior("onfocus") {
         * 
         * @Override protected void onEvent(AjaxRequestTarget target) { if (!focusGained) {
         *           modelValue = "ChangedViaDefaultSetModel"; target.add(this.getComponent());
         *           focusGained = true; System.out.println("focus gained " +
         *           getComponent().getDefaultModelObject()); System.out.println("focus gained " +
         *           getComponent().getFeedbackMessages()); target.add(BusaTxt.this); } } });
         * 
         *           add(new AjaxEventBehavior("onblur") {
         * @Override protected void onEvent(AjaxRequestTarget target) { modelValue =
         *           "ChangedNormally"; target.add(this.getComponent()); focusGained = false;
         *           System.out.println("focus lost " + getComponent().getDefaultModelObject());
         *           target.add(BusaTxt.this); } }); </pre>
         */

        add(new TestValidator<HaldeBuchungssatzScan>());
        // add(new ErrorDecorationBehavior());
        setRequired(true);

        // setAuto(true);
    }

    private class TestValidator<T> implements IValidator<T> {
        public void validate(IValidatable<T> validatable) {
            String txtValue = (String) validatable.getValue();
            if ("yyy".equals(txtValue)) {
                ValidationError err = new ValidationError();
                err.addKey("error.test");
                validatable.error(err);
            }
        }
    }

}
