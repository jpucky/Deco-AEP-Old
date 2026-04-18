// $Log: EventListenerInterface.java,v $
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
// Aufr�umarbeiten. Beseitigen globaler Variablen.
//
// Revision 1.3  2013/10/07 12:00:04  tw
// Aufräumarbeiten. Beseitigen globaler Variablen.
//
// Revision 1.2  2012/09/06 15:14:51  tw
// Volume-Zähler implementiert.
//
// Revision 1.1  2012/06/08 21:08:10  tw
// CVS: Client neu Eingespielt.
//
// Revision 1.1  2012/05/30 23:13:42  tw
// Benachrichtigungmimik
//
//

package de.decodetron.event;

/**
 * Zu dem Thema hab ich später das hier gefunden:
 * https://cwiki.apache.org/WICKET/migration-to-wicket-15.html (Inter-component events)
 * 
 * @author Thomas Winter
 * @since 25.05.2012
 */
public interface EventListenerInterface {
    public void notifyAjaxEvent(AbstractEvent event);
}
