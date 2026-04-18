// $Log: RecOnPagingClickEvent.java,v $
// Revision 1.2  2016/02/05 15:24:32  tw
// CR 3956: Interner Umbau: Events separiert.
//
// Revision 1.1  2016/01/31 17:01:04  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
//

package de.decodetron.tab.recherche.event;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

/**
 * Paginierungs-Klick-Event.
 * 
 * @author Thomas Winter
 * @since 29.01.2016
 */
public class RecOnPagingClickEvent implements IVisitor {

    private Component source;
    private AjaxRequestTarget requestTarget;

    public RecOnPagingClickEvent(Component src, AjaxRequestTarget rt, Object ch) {
        source = src;
        requestTarget = rt;
    }

    protected RecOnPagingClickEvent(Component src, AjaxRequestTarget rt) {
        this(src, rt, null);
    }

    public Component getSource() {
        return source;
    }

    public void fire() {
        Page page = source.getPage();
        if (page instanceof IRecOnPagingClickEvent) {
            ((IRecOnPagingClickEvent) page).onPagingClickEvent(this);
        }
        page.visitChildren(IRecOnPagingClickEvent.class, this);
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
        ((IRecOnPagingClickEvent) object).onPagingClickEvent(this);
    }
}
