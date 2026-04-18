// $Log: ChangedUserListWrapper.java,v $
// Revision 1.1  2014/10/10 12:06:41  tw
// Implementierung: Benutzerrechte Vertriebsleiter.
//
//

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import org.apache.wicket.markup.html.WebMarkupContainer;

import de.decodetron.AEPModel;
import de.decodetron.Const;
import de.decodetron.event.AbstractEvent;
import de.decodetron.event.ChangeEvent;
import de.decodetron.event.EventListenerInterface;
import de.decodetron.security.LoginSession;

/**
 * Ein Wrapper für die Liste der gänderten Benutzer, da sich Liste ja bekanntlich nicht
 * ajaxifizieren lassen. Das wird so eingestellt, dass das nur für Superadmins sichtbar ist.
 * 
 * @author Thomas Winter
 * @since 07.10.2014
 */
public class ChangedUserListWrapper extends WebMarkupContainer implements EventListenerInterface {

    public ChangedUserListWrapper(String id, AEPModel model) {
        super(id);
        setOutputMarkupId(isVisible());
        add(new ChangedUserListNew("changeduserlist", model));
    }

    private void updateForm(ChangeEvent ce) {
        if (isVisible()) {
            ce.update(ChangedUserListWrapper.this);
        }
    }

    @Override
    public void notifyAjaxEvent(AbstractEvent event) {

        if (event instanceof ChangeEvent) {
            ChangeEvent ce = ((ChangeEvent) event);
            String ident = ce.getIdentifier();
            if (Const.KEY_USER_ATTRIB_2CHANGE_SELECTED.equals(ident)) {
                updateForm(ce);
            }
        }
    }

    @Override
    public boolean isVisible() {
        return LoginSession.get().getUser().getIsSuperAdmin();
    }

}
