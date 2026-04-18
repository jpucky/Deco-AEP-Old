// $Log: InheritTest.java,v $
// Revision 1.1  2016/01/31 17:01:04  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
//

package de.decodetron.tab.recherche.panel;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.decodetron.AEPModel;
import de.decodetron.tab.statistik.TxtFilter;

/**
 * @author Thomas Winter
 * @since 25.01.2016
 */
public class InheritTest extends BasePanelRecherche {

    public InheritTest(String id, IModel<AEPModel> model) {
        super(id, model);
        setOutputMarkupId(true);
        
        //add(new TxtFilter("txt-kdnr", ""));
    }

}
