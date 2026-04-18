// $Log: AutoCompleteColumn.java,v $
// Revision 1.1  2015/01/30 21:24:31  tw
// Haldenbearbeitung 1. Wurf.
//
//

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

/**
 * @author Thomas Winter
 * @since 30.01.2015
 */
public class AutoCompleteColumn extends AbstractColumn {

    public AutoCompleteColumn(IModel displayModel) {
        super(displayModel);
    }

    public AutoCompleteColumn(IModel displayModel, Object sortProperty) {
        super(displayModel, sortProperty);
    }

    @Override
    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
        // TODO Auto-generated method stub

    }

}
