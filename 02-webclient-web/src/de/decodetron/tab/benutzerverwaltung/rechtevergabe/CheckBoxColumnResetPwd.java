// $Log: CheckBoxColumnResetPwd.java,v $
// Revision 1.2  2014/09/13 15:22:54  tw
// Rechteverwaltung: Bugfix: Textfeldaenderungen werden richtig verarbeitet f. d. Liste d. geaenderten Benutzer.
//
// Revision 1.1  2014/09/12 15:04:41  tw
// Aep-Benutzerr-Rechteverwaltung: Setzten des Defaultpasswortes.
//
//

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import java.util.HashMap;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.decodetron.AEPModel;
import de.decodetron.Const;
import de.decodetron.bo.UserSectionData;
import de.decodetron.event.ChangeEvent;

/**
 * Checkbox um das Passwort zurückzusetzen. Das ganze Thema wird hier nochmal geballt abgehandelt:
 * https://cwiki.apache.org/confluence/display/WICKET/Getting+user+confirmation
 * 
 * @author Thomas Winter
 * @since 11.09.2014
 */
public class CheckBoxColumnResetPwd<T, S> extends CheckboxColumn<T, S> {

    private UserSectionData user;

    public CheckBoxColumnResetPwd(String title, String expression, IModel<AEPModel> m) {
        super(Model.of(title), expression, m);
    }

    @Override
    public Component getHeader(String componentId) {
        return new DivMultiLineLabel<String>(componentId, getDisplayModel());
    }

    @Override
    public String getCssClass() {
        return "rotate";
    }

    protected boolean isEnabled(T rowObject) {
        return !(de.decodetron.util.Const.STANDARTPWD_VER).equals(user.getPasswd());
    };

    @Override
    protected FormComponent<Boolean> newCheckBox(String id, final IModel<Boolean> model, final T rowObject) {
        final FormComponent<Boolean> checkbox = super.newCheckBox(id, model, rowObject);

        user = (UserSectionData) rowObject;

        checkbox.add(new AjaxFormComponentUpdatingBehavior("onclick") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                Boolean checked = checkbox.getModelObject();
                CheckBoxColumnResetPwd.this.onUpdate(checkbox, rowObject, checked, target);
                target.appendJavaScript("$.fn.showPwdReset2(\"" + user.getLogin() + "\")");
            }
        });

        return checkbox;
    }

    /**
     * Auch ne Möglichkeit. Nützt mir aber nix, da ich auf den Abbruch nicht reagieren kann!
     */
    public class JavascriptEventConfirmation extends AttributeModifier {
        public JavascriptEventConfirmation(String event, String msg) {
            super(event, new Model(msg));
        }

        protected String newValue(final String currentValue, final String replacementValue) {
            String prefix = "var conf = confirm('" + replacementValue + "'); " + "if (!conf) return false; ";
            String result = prefix;
            if (currentValue != null) {
                result = prefix + currentValue;
            }
            return result;
        }
    }

    protected void onUpdate(FormComponent<Boolean> checkbox, T rowObject, boolean value, AjaxRequestTarget target) {
        UserSectionData user = (UserSectionData) rowObject;
        user.setPasswd(de.decodetron.util.Const.STANDARTPWD_VER);
        AEPModel vm = (AEPModel) getModel().getObject();
        HashMap<Long, UserSectionData> hm = vm.getBenVerwaltungModel().getUserList2Change();
        hm.put(user.getId(), user);
        new ChangeEvent(checkbox, target, null, Const.KEY_USER_ATTRIB_2CHANGE_SELECTED).fire();
    }
}
