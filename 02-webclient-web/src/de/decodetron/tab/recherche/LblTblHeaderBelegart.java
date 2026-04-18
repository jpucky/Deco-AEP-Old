// $Log: LblTblHeaderBelegart.java,v $
// Revision 1.2  2014/08/01 13:00:02  tw
// Scanbelege, Oberflaechenapassungen, Fundstellensuche.
//
// Revision 1.1  2014/07/18 14:14:11  tw
// Labelanpassung Scannbelege.
//
// Revision 1.1  2014/07/17 22:05:49  tw
// Extrawurst-Label f. Scanbelege erstellt.
//
// Revision 1.2  2014/01/31 13:32:11  tw
// Umlaute!
//
// Revision 1.1  2014/01/31 13:28:24  tw
// Belegart nur fuer Superadmins sichtbar.
//
//

package de.decodetron.tab.recherche;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

import de.decodetron.security.LoginSession;

/**
 * Suchergebnis - Tabellenüberschrift. Die Belegart soll nur noch zu Debugzwecken für Superadmins
 * sichtbar sein.
 * 
 * @author Thomas Winter
 * @since 31.01.2014
 */
public class LblTblHeaderBelegart extends Label {

    public LblTblHeaderBelegart(String id) {
        super(id);
        setDefaultModel(Model.of("Typ"));
    }

    @Override
    public boolean isVisible() {
        return LoginSession.get().getUser().getIsSuperAdmin();
    }
}
