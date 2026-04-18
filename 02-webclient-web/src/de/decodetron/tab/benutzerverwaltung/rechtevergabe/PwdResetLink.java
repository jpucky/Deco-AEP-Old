// $Log: PwdResetLink.java,v $
// Revision 1.2  2014/09/13 15:22:54  tw
// Rechteverwaltung: Bugfix: Textfeldaenderungen werden richtig verarbeitet f. d. Liste d. geaenderten Benutzer.
//
// Revision 1.1  2014/09/12 15:04:41  tw
// Aep-Benutzerr-Rechteverwaltung: Setzten des Defaultpasswortes.
//
//

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

import de.decodetron.AEPApplication;
import de.decodetron.AEPModel;
import de.decodetron.bo.UserSectionData;

/**
 * @author Thomas Winter
 * @since 12.09.2014
 */
public class PwdResetLink extends Link<String> {

    public PwdResetLink(String id, IModel model) {
        super(id, model);
        add(AttributeModifier.append("class", "pwdreset2 invisible"));
    }

    @Override
    public void onClick() {

        AEPModel model = (AEPModel) getDefaultModelObject();
        HashMap<Long, UserSectionData> hm = model.getBenVerwaltungModel().getUserList2Change();
        for (Iterator<Long> iterator = hm.keySet().iterator(); iterator.hasNext();) {
            Long key = iterator.next();
            UserSectionData user = hm.get(key);
            AEPApplication.get().getDBUser().update(user);
        }

        model.getBenVerwaltungModel().initUserList2Change();
    }

}
