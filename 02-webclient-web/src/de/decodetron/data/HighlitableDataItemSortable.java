// $Log: HighlitableDataItemSortable.java,v $
// Revision 1.8  2017/07/04 01:07:32  tw
// Mobilmachung der Headrevision. Beseitigung des PDF-Speicherlecks.
//
// Revision 1.7  2017/06/21 19:44:16  tw
// Mobilmachung der Headrevision.
//
// Revision 1.6  2016/02/05 15:47:50  tw
// CR 3956: Interner Umbau: Events separiert.
//
// Revision 1.5  2015/01/23 17:19:38  tw
// Doppelklickversuche implementiert.
//
// Revision 1.4  2014/03/20 02:22:22  tw
// Recherche: Paginierung, Funktionstest. Event-Konstanten zusammengefasst.
//
// Revision 1.3  2014/03/06 00:49:13  tw
// Recherche: Paginierung, Oberflaechenanbindung (auskommentiert).
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.2  2014/03/04 16:21:48  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Recherche: Paginierung.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.1  2014/03/02 00:00:28  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Recherche: Paginierung.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
//

package de.decodetron.data;

import java.io.Serializable;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPModel;
import de.decodetron.Const;
import de.decodetron.bo.Fundstelle;
import de.decodetron.event.ChangeEvent;
import de.decodetron.tab.recherche.RechercheModel;
import de.decodetron.tab.recherche.event.RecOnFundstelleClickEvent;

/**
 * @author Thomas Winter
 * @since 01.03.2014
 */
public class HighlitableDataItemSortable<T> extends Item<T> {

    private boolean highlite = false;

    public HighlitableDataItemSortable(String id, int index, final IModel<T> model, final RechercheModel rm) {
        super(id, index, model);

        add(new AttributeModifier("style", new Model<Serializable>() {
            public Serializable getObject() {
                return isHighlighted() ? "background-color:#80b6ed;" : "";
            }
        }));        
        
        add(new AjaxEventBehavior("onclick") {
            protected void onEvent(AjaxRequestTarget target) {

                HighlitableDataItemSortable<T> oldItem = (HighlitableDataItemSortable<T>) rm.getSelectedItem();
                HighlitableDataItemSortable<T> newItem = HighlitableDataItemSortable.this;

                if ((oldItem != null) && (newItem != oldItem)) {
                    oldItem.removeHighlight();
                }
                if ((newItem != null) && (newItem != oldItem)) {
                    newItem.toggleHighlite();
                    
                    rm.addFundstelle((Fundstelle) getDefaultModelObject());
                    
                    rm.addSelectedItem(newItem);
                    new RecOnFundstelleClickEvent(HighlitableDataItemSortable.this, target).fire();
                }
            }
        });

        
        add(new AjaxEventBehavior("ondblclick") {
            protected void onEvent(AjaxRequestTarget target) {

                /**
                 * Der Einfachklick hat uns schon ein DataItem in die Tonne gelegt. Das holen wir uns ...
                 */
                Fundstelle fs = (Fundstelle) getDefaultModelObject();
                HighlitableDataItemSortable<T> oldItem = (HighlitableDataItemSortable<T>) rm.get(Const.KEYTOGGLEDITEM);
                if(oldItem != null){
                    System.out.println("double click ...: " + fs.getId());
                    new ChangeEvent(oldItem, target, fs, Const.KEYFUNDSTELLEDBLKLICK).fire();
                }
            }
        });
    }
    
    public boolean toggleHighlite() {
        return highlite = !highlite;
    }

    public boolean isHighlighted() {
        return highlite;
    }

    public void removeHighlight() {
        highlite = false;
    }
}
