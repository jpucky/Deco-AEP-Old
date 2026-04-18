// $Log: AEPOrderByBorder.java,v $
// Revision 1.5  2014/02/14 17:03:24  tw
// Implementierung: Passwort zuruecksetzen.
//
// Revision 1.4  2014/02/05 13:47:58  tw
// Anpassung an neue DB-Struktur
//
// Revision 1.3  2013/12/05 15:45:39  tw
// .csv - Listen fuer alle Listen
//
// Revision 1.2  2013/12/02 21:54:12  tw
// Spalten: KundenNr, LieferscheinNr werden fuer alle Lieferanten in allen Listen gefiltert.
//
// Revision 1.1  2013/12/02 17:34:16  tw
// Vorbereitungen f�r gezieltes Spaltenfiltern.
//
//

package de.decodetron.tab.statistik;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;

import de.decodetron.security.LoginSession;

/**
 * Sortierbarer Tabellenkopf der für Lieferanten bestimmte Tabellenköpfe ausblendet.
 * 
 * @author Thomas Winter
 * @since 02.12.2013
 */
public class AEPOrderByBorder extends OrderByBorder {

    String colName = "";

    public AEPOrderByBorder(String id, String property, ISortStateLocator stateLocator) {
        super(id, property, stateLocator);
        colName = id;

        if (LoginSession.get().getUser().getIsSuperAdmin()) {
            return;
        }
        
        if (LoginSession.get().getUser().getIsLieferant()) {

            if ("LieferantNr".equals(colName)
                    && "LieferantNr".equals(LoginSession.get().getCurrentSection().getFilteridentifier())) {
                setVisible(false);
            }

            if ("KundenNr".equals(colName) && "KundenNr".equals(LoginSession.get().getCurrentSection().getFilteridentifier())) {
                setVisible(false);
            }
        }
    }
}
