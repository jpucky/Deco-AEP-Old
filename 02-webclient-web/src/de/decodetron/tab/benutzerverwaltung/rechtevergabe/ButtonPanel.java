// $Log: ButtonPanel.java,v $
// Revision 1.1  2014/10/29 14:58:59  tw
// Bugfix: setReuseitems f. sinnvollerer Errorhandling. Vorbereitung: Benutzer kopieren.
//
//

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * @author Thomas Winter
 * @since 29.10.2014
 */
public class ButtonPanel extends Panel {

    public static final String BUTTON_ID = "buttonpnl";

    public ButtonPanel(String id, IModel<?> model) {
        super(id, model);
    }
}
