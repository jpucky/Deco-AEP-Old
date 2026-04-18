// $Log: ViewListEmpty.java,v $
// Revision 1.1  2015/02/01 10:43:17  tw
// Umzug der Defaultanzeige.
//
// Revision 1.2  2015/01/25 14:34:35  tw
// Vorabentwurf f. Lueckenprotokoll implementiert.
//
// Revision 1.1  2013/11/07 22:43:04  tw
// Hinweis Listentyp eingebaut.
//
//

package de.decodetron.tab.empty;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.Const;

/** 
 * @author Thomas Winter
 * @since 07.11.2013
 */
public class ViewListEmpty extends Panel {

    public ViewListEmpty(String id, IModel<?> model) {
        super(id, model);
        ValueMap map = (ValueMap) model.getObject();
        add(new Label("navicomboact", Model.of((String)map.get(Const.KEY_NAVI_COMBOACTION))));
    }

}
