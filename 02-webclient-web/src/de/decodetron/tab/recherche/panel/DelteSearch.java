// $Log: DelteSearch.java,v $
// Revision 1.2  2016/01/31 17:00:15  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.1  2016/01/19 23:16:09  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
//

package de.decodetron.tab.recherche.panel;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;

import de.decodetron.tab.recherche.RechercheModel;
import de.decodetron.tab.statistik.TxtFilter;

/**
 * @author Thomas Winter
 * @since 14.01.2016
 */
public class DelteSearch extends Link<RechercheModel> {

    public DelteSearch(String id, RechercheModel model) {
        super(id, Model.of(model));
    }

    @Override
    public void onClick() {
        //ValueMap map = (ValueMap) getDefaultModel().getObject();
        //List<TxtFilter> filterList = (List<TxtFilter>) map.get(Const.KEY_LIST_FILTERSUCHE);
        List<TxtFilter> filterList = ((RechercheModel)getDefaultModelObject()).getSuchItems();
        for (Iterator<TxtFilter> iterator = filterList.iterator(); iterator.hasNext();) {
            TxtFilter txtFilter = iterator.next();
            //System.out.println("Debug: " + txtFilter.getText());
            txtFilter.setText("");
        }
        // map.put(txt-kdnr", "316*");
    }

}
