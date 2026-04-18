// $Log: ChangeEvent.java,v $
// Revision 1.4  2015/07/21 19:49:53  tw
// Fehlerhafte Packagestruktur korrigiert.
//
// Revision 1.3  2015/07/01 21:13:04  tw
// First Revision.
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
// Revision 1.2  2012/06/06 13:15:58  tw
// Neue Reitermimik implementiert.
//
// Revision 1.1  2012/05/30 23:13:42  tw
// Benachrichtigungmimik
//
//

package de.decodetron.event;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * Ein Change-Event um Objekte abzufeuern. Ein requestTarget muss nicht zwingend angegeben sein!
 * 
 * @author Thomas Winter
 * @since 25.05.2012
 */
public class ChangeEvent extends AbstractEvent {

    private Object change;
    private String identifier;

    /**
     * 
     * @param Component
     *            source
     * @param AjaxRequestTarget
     *            requestTarget
     * @param Object
     *            ch
     * @param String
     *            ident
     */
    public ChangeEvent(Component source, AjaxRequestTarget requestTarget, Object ch, String ident) {
        this(source, requestTarget, ch);
        identifier = ident;
    }

    public ChangeEvent(Component source, AjaxRequestTarget requestTarget, Object ch) {
        super(source, requestTarget);
        change = ch;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Object getChange() {
        return change;
    }
}
