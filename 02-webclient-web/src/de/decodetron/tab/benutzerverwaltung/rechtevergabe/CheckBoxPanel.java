// $Log: CheckBoxPanel.java,v $
// Revision 1.4  2014/08/26 00:24:18  tw
// .
//
// Revision 1.1  2014/08/14 14:57:07  tw
// .
//
// Revision 1.2  2014/08/09 11:47:19  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
//

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * Statisches Checkbox-Panel. Eigentlich kann man nix mit anfangen. Dient nur zu Ansicht. Oder falls
 * alles in einem Schwung am Ende abeschickt wird.
 * 
 * @author Thomas Winter
 * @since 09.08.2014
 */
public class CheckBoxPanel extends Panel {

    public static final String CHECKBOX_ID = "checkbox";

    public CheckBoxPanel(String id, IModel<?> model) {
        super(id, model);
    }
}
