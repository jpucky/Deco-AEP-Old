// $Log: AbstractEvent.java,v $
// Revision 1.7  2016/02/05 15:24:31  tw
// CR 3956: Interner Umbau: Events separiert.
//
// Revision 1.6  2016/01/31 17:00:14  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.5  2015/07/21 19:49:53  tw
// Fehlerhafte Packagestruktur korrigiert.
//
// Revision 1.4  2015/07/01 21:13:04  tw
// First Revision.
//
// Revision 1.3  2014/02/07 02:07:05  tw
// Restzeitaktualisierung bei Aktivitaeten implementiert.
//
// Revision 1.2  2013/10/24 16:48:10  tw
// Anpassungen an aktuelle Daten
//
// Revision 1.1  2013/10/07 12:10:42  tw
// Aufräumarbeiten. Beseitigen globaler Variablen.
//
// Revision 1.1  2012/06/08 21:08:10  tw
// CVS: Client neu Eingespielt.
//
// Revision 1.1  2012/05/30 23:13:42  tw
// Benachrichtigungmimik
//
//

package de.decodetron.event;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

/**
 * @author Thomas Winter
 * @since 25.05.2012
 */
public class AbstractEvent {

    Component source;
    AjaxRequestTarget requestTarget;

    protected AbstractEvent(Component src, AjaxRequestTarget rt) {
        source = src;
        requestTarget = rt;
    }

    public Component getSource() {
        return source;
    }

    public void fire() {
        Page page = source.getPage();
        if (page instanceof EventListenerInterface) {
            ((EventListenerInterface) page).notifyAjaxEvent(this);
        }
        page.visitChildren(EventListenerInterface.class, new AjaxEventVisitor(this));
    }

    public void update(Component component) {
        if (requestTarget != null) {
            requestTarget.add(component);
        }
    }

    public AjaxRequestTarget getTarget() {
        return requestTarget;
    }

    protected static class AjaxEventVisitor implements IVisitor {

        AbstractEvent event;

        protected AjaxEventVisitor(AbstractEvent ev) {
            event = ev;
        }

        public void component(Object object, IVisit visit) {
            ((EventListenerInterface) object).notifyAjaxEvent(event);
        }
    }
}
