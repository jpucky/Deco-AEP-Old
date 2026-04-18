// $Log: SuperAdminLabeledDropDown.java,v $
// Revision 1.2  2014/08/21 22:04:51  tw
// Aep-Benutzerr-Rechteverwaltung: Layout.
//
// Revision 1.1  2014/07/17 14:10:26  tw
// Bugfix: Combobox ff, Benutzerverwaltung.
//
//

package de.decodetron.dlgcomponents;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;

import de.decodetron.security.LoginSession;

/**
 * Blendet diese und Subkomponenten nur für Superadmins ein.
 * 
 * @author Thomas Winter
 * @since 17.07.2014
 */
public class SuperAdminLabeledDropDown extends MarkupContainer {

    public SuperAdminLabeledDropDown(String id, String idDD) {
        super(id);
        add(new DropDownChoice<String>(idDD).setEnabled(false));
    }

    @Override
    public boolean isVisible() {
        return LoginSession.get().getUser().getIsSuperAdmin();
    }
}
