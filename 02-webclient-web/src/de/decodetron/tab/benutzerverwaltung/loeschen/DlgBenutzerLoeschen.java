// $Log: DlgBenutzerLoeschen.java,v $
// Revision 1.3  2014/08/11 12:32:08  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.2  2014/06/28 16:00:38  tw
// Benutzer bearbeiten finalisiert. Busy-Indicator f. Statistik u. Benutzer Bearbeiten.
//
// Revision 1.1  2014/05/12 16:42:36  tw
// Aufraeumarbeiten, Benutzer anlegen loeschen.
//
//

package de.decodetron.tab.benutzerverwaltung.loeschen;

import java.util.Iterator;
import java.util.Set;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPApplication;
import de.decodetron.Const;
import de.decodetron.bo.User;
import de.decodetron.event.AbstractEvent;
import de.decodetron.event.ChangeEvent;
import de.decodetron.event.EventListenerInterface;

/**
 * Der Lösch-Button. Beim Klick darauf erfolgt nochmal eine Nachfrage.
 * 
 * @author Thomas Winter
 * @since 11.05.2014
 */
public class DlgBenutzerLoeschen extends Panel {

    public DlgBenutzerLoeschen(String id, IModel<?> model) {
        super(id, model);
        Form form = new Form("confirmForm", model);
        form.add(new DeleteLink("ben-delete", model));
        add(form);
        setOutputMarkupId(true);
    }

    private class DeleteLink extends Link implements EventListenerInterface {

        public DeleteLink(String id, IModel model) {
            super(id, model);
            add(AttributeModifier.append("onclick", jsMethod()));
        }

        private String jsMethod() {
            StringBuilder sb = new StringBuilder();
            sb.append("var answer = confirm('Wollen Sie die ausgewählten Benutzter wirklich löschen?');");
            sb.append("if(answer){$.fn.showViewBusy();}else{return false;};");
            return sb.toString();
        }

        @Override
        public void onClick() {

            ValueMap map = (ValueMap) getModel().getObject();
            Set<User> changedUserList = (Set<User>) map.get(Const.KEY_DELETE_USERLIST);
            for (Iterator<User> iterator = changedUserList.iterator(); iterator.hasNext();) {
                User user = iterator.next();
                AEPApplication.get().getDBUser().delete(user);
                // System.out.println("User: " + user.getLogin());
            }
            changedUserList.clear();
        }

        @Override
        public boolean isVisible() {
            ValueMap map = (ValueMap) DlgBenutzerLoeschen.this.getDefaultModelObject();
            Set<User> user2DeleteList = (Set<User>) map.get(Const.KEY_DELETE_USERLIST);
            return user2DeleteList.size() > 0 ? true : false;
        }

        @Override
        public void notifyAjaxEvent(AbstractEvent event) {

            if (event instanceof ChangeEvent) {
                ChangeEvent ce = ((ChangeEvent) event);
                String ident = ce.getIdentifier();
                if (Const.KEY_BENDELETE_SELEKTED.equals(ident)) {
                    ce.update(DlgBenutzerLoeschen.this);
                }
            }
        }
    }

}
