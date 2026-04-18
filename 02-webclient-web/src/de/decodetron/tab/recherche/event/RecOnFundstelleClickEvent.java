// $Log: RecOnFundstelleClickEvent.java,v $
// Revision 1.2  2017/06/21 19:44:17  tw
// Mobilmachung der Headrevision.
//
// Revision 1.1  2016/02/05 15:48:07  tw
// CR 3956: Interner Umbau: Events separiert.
//
//

package de.decodetron.tab.recherche.event;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

/**
 * Ehemals: Const.KEYFUNDSTELLEKLICK.
 * 
 * @author Thomas Winter
 * @since 05.02.2016
 */
public class RecOnFundstelleClickEvent implements IVisitor{

    private Component source;
    private AjaxRequestTarget requestTarget;

    public RecOnFundstelleClickEvent(Component src, AjaxRequestTarget rt, Object ch) {
        source = src;
        requestTarget = rt;
    }

    public RecOnFundstelleClickEvent(Component src, AjaxRequestTarget rt) {
        this(src, rt, null);
    }

    public Component getSource() {
        return source;
    }

    public void fire() {
        Page page = source.getPage();
        if (page instanceof IRecOnFundstelleClickEvent) {
            ((IRecOnFundstelleClickEvent) page).onFundstelleClick(this);
        }
        page.visitChildren(IRecOnFundstelleClickEvent.class, this);
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
        ((IRecOnFundstelleClickEvent) object).onFundstelleClick(this);
    }

}
