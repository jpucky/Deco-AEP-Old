// $Log: ModalWindowUser.java,v $
// Revision 1.7  2014/07/18 22:42:17  tw
// Layoutkorrektur, Modaler Dialog, Groessenanpassung f. Admins.
//
// Revision 1.6  2014/06/28 16:00:37  tw
// Benutzer bearbeiten finalisiert. Busy-Indicator f. Statistik u. Benutzer Bearbeiten.
//
// Revision 1.5  2014/06/14 12:52:32  tw
// Anbindung d. Checkbox f. Standartpasswort.
//
// Revision 1.4  2014/06/12 15:43:59  tw
// .
//
// Revision 1.3  2014/06/12 12:29:20  tw
// Benutzer bearbeiten: Vorbereitung f. korrekte Gruppenverarbeitung.
//
// Revision 1.2  2014/06/05 15:35:28  tw
// Backup: Benutzer anlegen, aendern, loeschen.
//
// Revision 1.1  2014/06/01 12:50:27  tw
// Benutzer aendern, Grundstruktur.
//
//

package de.decodetron.dlgcomponents;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

import de.decodetron.security.LoginSession;

/**
 * Modales Fenster jür Benutzerdaten.
 * 
 * 
 * @author Thomas Winter
 * @since 01.06.2014
 */
public class ModalWindowUser extends ModalWindow {

    ResourceReference CSS = new CssResourceReference(ModalWindow.class, "../../../../../../../../css/w_modal.css");

    public ModalWindowUser(String id, IModel model) {
        super(id, model);

        setCssClassName("custom");
        setHeightUnit("px");
        setInitialHeight(LoginSession.get().getUser().getIsSuperAdmin() ? 600 : 510);
        setMinimalHeight(LoginSession.get().getUser().getIsSuperAdmin() ? 600 : 510);

        setWidthUnit("px");
        setInitialWidth(460);
        setMinimalWidth(460);

        setCookieName(id);
    }

    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);

        if (CSS != null) {
            response.render(CssHeaderItem.forReference(CSS));
        }
    }
}
