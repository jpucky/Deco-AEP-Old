// $Log: UserAbbrechenButton.java,v $
// Revision 1.2  2014/10/10 12:05:22  tw
// Implementierung: Benutzerrechte Vertriebsleiter.
//
// Revision 1.1  2014/09/10 16:15:42  tw
// Aep-Benutzerr-Rechteverwaltung: Aufraeumarbeiten, Klassen entdroeselt.
//
//

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.model.IModel;

/**
 * @author Thomas Winter
 * @since 10.09.2014
 */
public class UserAbbrechenButton extends UserRechteButton {

    public UserAbbrechenButton(String id, IModel<?> iModel) {
        super(id, iModel);
        /**
         * Ist nötig, da das Model normalerweise zur Beschriftung verwendet wird!
         */
        add(AttributeAppender.replace("value", "Abbrechen"));
        add(AttributeAppender.append("class", "abbortuser"));
    }

    public void onSubmit() {
        initErrorList();
        initUserSectionChangeModel();
        initUserSectionOrignialModel();
    }
}
