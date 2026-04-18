// $Log: RecOnSearchEvent.java,v $
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
 * Suche-Event. Ehemals: Const.KEYSTARTSEARCH
 * 
 * @author Thomas Winter
 * @since 05.02.2016
 */
public class RecOnSearchEvent implements IVisitor {

    private Component source;
    private AjaxRequestTarget requestTarget;

    public RecOnSearchEvent(Component src, AjaxRequestTarget rt, Object ch) {
        source = src;
        requestTarget = rt;
    }

    protected RecOnSearchEvent(Component src, AjaxRequestTarget rt) {
        this(src, rt, null);
    }

    public Component getSource() {
        return source;
    }

    public void fire() {
        Page page = source.getPage();
        if (page instanceof IRecOnSearchEvent) {
            ((IRecOnSearchEvent) page).onSearchEvent(this);
        }
        page.visitChildren(IRecOnSearchEvent.class, this);
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
        ((IRecOnSearchEvent) object).onSearchEvent(this);
    }

}
