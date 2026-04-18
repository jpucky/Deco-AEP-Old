// $Log: LblSuperAdmin.java,v $
// Revision 1.1  2016/01/19 23:16:10  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
//

package de.decodetron.tab.recherche.panel;

import org.apache.wicket.markup.html.basic.Label;

import de.decodetron.security.LoginSession;

/**
 * Dieses Label ist nur für Superadmins sichtbar.
 * 
 * @author Thomas Winter
 * @since 14.01.2016
 */
public class LblSuperAdmin extends Label {

    public LblSuperAdmin(String id, String label) {
        super(id, label);
    }

    @Override
    public boolean isVisible() {
        return LoginSession.get().getUser().getIsSuperAdmin();
    }
}
