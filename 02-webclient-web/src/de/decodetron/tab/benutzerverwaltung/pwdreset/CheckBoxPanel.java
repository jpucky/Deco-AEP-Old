// $Log: CheckBoxPanel.java,v $
// Revision 1.3  2014/09/12 15:04:16  tw
// Aep-Benutzerr-Rechteverwaltung: Setzten des Defaultpasswortes.
//
// Revision 1.2  2014/06/12 12:29:20  tw
// Benutzer bearbeiten: Vorbereitung f. korrekte Gruppenverarbeitung.
//
// Revision 1.1  2014/05/30 22:20:30  tw
// Pwdreset, Aufraeumarbeiten
//
//

package de.decodetron.tab.benutzerverwaltung.pwdreset;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.Const;
import de.decodetron.bo.User;

/**
 * @author Thomas Winter
 * @since 31.05.2014
 */
public class CheckBoxPanel extends Panel {

    public CheckBoxPanel(String id, User user, IModel<?> iModel) {
        super(id, iModel);
        add(new AEPCheckBox("check", user));
    }

    /**
     * Alle User die das standart-Passwort bereits gesetzt haben, werden vorselektiert und disabeld.
     */
    private class AEPCheckBox extends AjaxCheckBox {

        private User user = null;

        public AEPCheckBox(String id, User u) {
            super(id, new PropertyModel(u, "selected"));
            user = u;
            user.setSelected(de.decodetron.util.Const.STANDARTPWD_VER.equals(user.getPasswd()) ? Boolean.TRUE
                    : Boolean.FALSE);
        }

        @Override
        public boolean isEnabled() {
            return !user.getSelected();
        }

        protected void onUpdate(AjaxRequestTarget target) {
            // target.appendJavaScript("if(!confirm('Do you really want to perform this action?')) return false;");
            target.appendJavaScript("$.fn.showPwdReset(\"" + user.getLogin() + "\")");
            user.setPasswd(de.decodetron.util.Const.STANDARTPWD_VER);
            // changedUser.add(u);

            ValueMap map = (ValueMap) CheckBoxPanel.this.getDefaultModelObject();
            Set<User> changedUserList = new LinkedHashSet<User>();
            map.put(Const.KEY_USER_2CHANGE, changedUserList);
            changedUserList.add(user);

            // Zurücknehmen der Selektion für den Fall eines Abbruches.
            // add(AttributeModifier.append("class", "pwdressel"));
            target.add(AEPCheckBox.this);
            // System.out.println("user: " + u.getLogin() + " / " + u.getSelected());
            // AEPApplication.get().getDBUser().update(u);
        }
    }
}
