// $Log: DeleteUserLink.java,v $
// Revision 1.4  2014/11/06 13:13:03  tw
// Anbindung: History-DB Benutzerrechte.
//
// Revision 1.3  2014/09/12 15:04:16  tw
// Aep-Benutzerr-Rechteverwaltung: Setzten des Defaultpasswortes.
//
// Revision 1.2  2014/09/10 16:15:42  tw
// Aep-Benutzerr-Rechteverwaltung: Aufraeumarbeiten, Klassen entdroeselt.
//
// Revision 1.1  2014/08/26 14:40:15  tw
// Aep-Benutzerr-Rechteverwaltung: Benutzer Loeschen implementiert.
//
//

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import java.util.Iterator;
import java.util.Set;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

import de.decodetron.AEPApplication;
import de.decodetron.AEPModel;
import de.decodetron.bo.UserSectionData;
import de.decodetron.security.LoginSession;

/**
 * Ein versteckter Link um das Löschen u.u mehrerer Benutzer zu ermöglichen.
 * 
 * @author Thomas Winter
 * @since 26.08.2014
 */
public class DeleteUserLink extends Link<String> {

    public DeleteUserLink(String id, IModel model) {
        super(id, model);
        add(AttributeModifier.append("class", "deleteUserR invisible"));
    }

    @Override
    public void onClick() {
        AEPModel model = (AEPModel) getDefaultModelObject();
        Set<UserSectionData> set = model.getBenVerwaltungModel().getUser2Delete();
        for (Iterator<UserSectionData> iterator = set.iterator(); iterator.hasNext();) {
            UserSectionData userSectionData = iterator.next();
            AEPApplication.get().getDBUser().deleteH(LoginSession.get().getUser(), userSectionData);
        }
        model.getBenVerwaltungModel().initUserDeleteModel();
    }
}
