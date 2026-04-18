// $Log: RecOnBelegartSelektiert.java,v $
// Revision 1.1  2016/02/05 15:24:48  tw
// CR 3956: Interner Umbau: Events separiert.
//
//

package de.decodetron.tab.recherche.event;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

/**
 * Belegart selektiert, siehe ehemals: Const.KEY_BELART_SELECTED.
 * 
 * @author Thomas Winter
 * @since 05.02.2016
 * @see IRecOnBelegartSelektiert
 */
public class RecOnBelegartSelektiert implements IVisitor {

    private Component source;
    private AjaxRequestTarget requestTarget;

    public RecOnBelegartSelektiert(Component src, AjaxRequestTarget rt, Object ch) {
        source = src;
        requestTarget = rt;
    }

    protected RecOnBelegartSelektiert(Component src, AjaxRequestTarget rt) {
        this(src, rt, null);
    }

    public Component getSource() {
        return source;
    }

    public void fire() {
        Page page = source.getPage();
        if (page instanceof IRecOnBelegartSelektiert) {
            ((IRecOnBelegartSelektiert) page).onBelegartSelektiert(this);
        }
        page.visitChildren(IRecOnBelegartSelektiert.class, this);
    }

    public void update(Component component) {
        if (requestTarget != null) {
            requestTarget.add(component);
        }
    }

    public AjaxRequestTarget getTarget() {
        return requestTarget;
    }

    @Override
    public void component(Object object, IVisit visit) {
        ((IRecOnBelegartSelektiert) object).onBelegartSelektiert(this);
    }

}
