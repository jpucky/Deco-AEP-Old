// $Log: DefaultPasswordChangeValidator.java,v $
// Revision 1.2  2014/02/11 16:30:02  tw
// Bestaetigungsdialog f. Passwortaenderung.
//
// Revision 1.1  2014/02/11 02:10:23  tw
// Zwangsteuerung f. Standartpasswortaenderung implementiert.
//
//

package de.decodetron.security.pwd;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.util.lang.Objects;

import de.decodetron.Const;

/**
 * @author Thomas Winter
 * @since 11.02.2014
 */
public class DefaultPasswordChangeValidator extends AbstractFormValidator {

    private final FormComponent<?>[] components;
    private static Logger log = Logger.getLogger(DefaultPasswordChangeValidator.class);

    /**
     * Construct.
     * 
     * @param formComponent1
     *            a form component
     * @param formComponent2
     *            a form component
     */
    public DefaultPasswordChangeValidator(FormComponent<?> formComponent1, FormComponent<?> formComponent2) {
        if (formComponent1 == null) {
            throw new IllegalArgumentException("argument formComponent1 cannot be null");
        }
        if (formComponent2 == null) {
            throw new IllegalArgumentException("argument formComponent2 cannot be null");
        }
        components = new FormComponent[] { formComponent1, formComponent2 };
    }

    @Override
    public FormComponent<?>[] getDependentFormComponents() {
        return components;
    }

    @Override
    public void validate(Form<?> form) {

        final FormComponent<?> formComponent1 = components[0];
        final FormComponent<?> formComponent2 = components[1];

        log.debug("pwd1: " + formComponent1.getInput());
        log.debug("pwd2: " + formComponent2.getInput());
        
        if (Objects.equal(formComponent1.getInput(), Const.STANDARTPWD)
                || Objects.equal(formComponent2.getInput(), Const.STANDARTPWD)) {
            error(formComponent1, "PasswordChange.pwdchange.error");
        }
    }

}
