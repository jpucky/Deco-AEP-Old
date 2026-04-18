// $Log: BenutzerAendernLink.java,v $
// Revision 1.6  2014/09/12 15:04:16  tw
// Aep-Benutzerr-Rechteverwaltung: Setzten des Defaultpasswortes.
//
// Revision 1.5  2014/06/28 16:00:38  tw
// Benutzer bearbeiten finalisiert. Busy-Indicator f. Statistik u. Benutzer Bearbeiten.
//
// Revision 1.4  2014/06/12 15:36:24  tw
// Benutzer bearbeiten: Vorbereitung f. korrekte Gruppenverarbeitung.
//
// Revision 1.3  2014/06/12 12:29:20  tw
// Benutzer bearbeiten: Vorbereitung f. korrekte Gruppenverarbeitung.
//
// Revision 1.2  2014/06/05 15:35:29  tw
// Backup: Benutzer anlegen, aendern, loeschen.
//
// Revision 1.1  2014/06/01 12:50:27  tw
// Benutzer aendern, Grundstruktur.
//
//

package de.decodetron.tab.benutzerverwaltung.aendern;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPApplication;
import de.decodetron.Const;
import de.decodetron.bo.Group;
import de.decodetron.bo.User;
import de.decodetron.dlgcomponents.ModalWindowUser;
import de.decodetron.event.ChangeEvent;

/**
 * @author Thomas Winter
 * @since 01.06.2014
 */
public class BenutzerAendernLink extends Panel {

    public BenutzerAendernLink(String id, IModel<User> model, final ModalWindowUser mw) {
        super(id, model);

        add(new AjaxLink<User>("benutzerAendern") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                initInputDlg((User) getParent().getDefaultModelObject(), mw);
                // target.appendJavaScript("$.fn.changeMaskStyle('wicket-mask-extra-dark');");
                mw.show(target);
            }
        });
    }

    /**
     * Intitialisiert den Dialog. Null setzt die Felder zurück.
     * 
     * @param User
     *            u
     */
    private void initInputDlg(User u, ModalWindowUser mw) {

        ValueMap map = (ValueMap) mw.getDefaultModelObject();
        map.put(Const.KEY_USER_2CHANGE, u == null ? null : u);
        map.put("userID", u == null ? null : u.getId());
        map.put("Vorname", u == null ? null : u.getVorname());
        map.put("Nachname", u == null ? null : u.getNachname());
        map.put("Login", u == null ? null : u.getLogin());
        // map.put("Passwort 1", u == null ? null : u.getPasswd());
        // map.put("Passwort 2", u == null ? null : u.getPasswd());
        map.put("Filter", u == null ? null : u.getFilter());

        // Die dämliche Checkbox lässt sich nur so überreden ...
        map.put(Const.KEY_BENW_RESETPWD, de.decodetron.util.Const.STANDARTPWD_VER.equals(u.getPasswd()) ? Boolean.TRUE
                : Boolean.FALSE);
        new ChangeEvent(this, null, map.get(Const.KEY_BENW_RESETPWD), Const.DEFAULTPWDSET).fire();

        List<Group> groupList = AEPApplication.get().getDBUser().getGroups4User(u.getId());
        map.put("Gruppen", u == null ? null : groupList);
    }

}
