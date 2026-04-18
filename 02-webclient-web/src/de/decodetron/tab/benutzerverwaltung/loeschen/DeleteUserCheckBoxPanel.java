// $Log: DeleteUserCheckBoxPanel.java,v $
// Revision 1.2  2014/07/29 21:19:58  tw
// Schnittstellenanpassung Scanbelege.
//
// Revision 1.1  2014/05/30 22:04:04  tw
// DeleteUser, Aufraeumarbeiten
//
//

package de.decodetron.tab.benutzerverwaltung.loeschen;

import java.util.Set;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.Const;
import de.decodetron.bo.User;
import de.decodetron.event.ChangeEvent;

/**
 * @author Thomas Winter
 * @since 30.05.2014
 */
public class DeleteUserCheckBoxPanel extends Panel {

    public DeleteUserCheckBoxPanel(String id, User u, IModel<?> m) {
        super(id, m);
        add(new DeleteUserCheckBox("check", u));
    }

    private class DeleteUserCheckBox extends AjaxCheckBox {

        public DeleteUserCheckBox(String id, User u) {
            super(id, new PropertyModel(u, "selected"));
        }

        @Override
        protected void onUpdate(AjaxRequestTarget target) {

            ValueMap map = (ValueMap) DeleteUserCheckBoxPanel.this.getDefaultModelObject();
            Set<User> user2DeleteList = (Set<User>) map.get(Const.KEY_DELETE_USERLIST);
            User u = (User) ((PropertyModel) getModel()).getInnermostModelOrObject();

            if (getModel().getObject()) {
                user2DeleteList.add(u);
            } else {
                user2DeleteList.remove(u);
            }
            // System.out.println("ViewListBenutzer: " + user2DeleteList.size());
            target.add(DeleteUserCheckBox.this);
            new ChangeEvent(this, target, null, Const.KEY_BENDELETE_SELEKTED).fire();
        }
    }
}
