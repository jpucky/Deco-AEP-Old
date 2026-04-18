// $Log: AutoCompletePanel.java,v $
// Revision 1.1  2015/01/30 21:24:32  tw
// Haldenbearbeitung 1. Wurf.
//
//

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * @author Thomas Winter
 * @since 30.01.2015
 */
public class AutoCompletePanel extends Panel {
    
    public static final String TEXTFIELD_ID = "autoFieldText";

    public AutoCompletePanel(String id) {
        super(id);
    }

    public AutoCompletePanel(String id, IModel model) {
        super(id, model);
    }
}
