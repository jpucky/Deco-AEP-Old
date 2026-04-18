// $Log: DlgBenutzerAnlegen.java,v $
// Revision 1.6  2014/06/28 16:00:38  tw
// Benutzer bearbeiten finalisiert. Busy-Indicator f. Statistik u. Benutzer Bearbeiten.
//
// Revision 1.5  2014/06/12 12:29:20  tw
// Benutzer bearbeiten: Vorbereitung f. korrekte Gruppenverarbeitung.
//
// Revision 1.4  2014/06/05 15:35:29  tw
// Backup: Benutzer anlegen, aendern, loeschen.
//
// Revision 1.3  2014/06/01 12:50:27  tw
// Benutzer aendern, Grundstruktur.
//
// Revision 1.2  2014/05/13 10:57:09  tw
// Pruefung auf existierendes Login.
//
// Revision 1.1  2014/05/12 16:45:21  tw
// Aufraeumarbeiten, Benutzer anlegen loeschen.
//
// Revision 1.7  2014/05/08 00:24:41  tw
// Backup: Benutzer anlegen.
//
// Revision 1.6  2014/05/07 21:34:05  tw
// Change Request: Doppelte Defektenlisten.
//
// Revision 1.5  2014/05/06 00:43:57  tw
// Backup: Benutzer anlegen.
//
// Revision 1.4  2014/05/05 21:03:03  tw
// Backup: Benutzer anlegen.
//
// Revision 1.3  2014/05/04 20:23:26  tw
// Backup: Benutzer anlegen.
//
// Revision 1.2  2014/05/03 15:09:49  tw
// Hintergrund modaler Dialog geaendert.
//
// Revision 1.1  2014/05/03 00:07:37  tw
// Backup: Benutzer anlegen.
//
// Revision 1.1  2014/05/02 09:52:29  tw
// Backup: Benutzer anlegen.
//
// Revision 1.3  2014/02/18 02:23:51  tw
// Individualisierung: Modaler Dialog.
//
// Revision 1.2  2014/02/15 11:58:48  tw
// Implementierung: Passwort zuruecksetzen.
//
// Revision 1.1  2014/02/11 16:30:03  tw
// Bestaetigungsdialog f. Passwortaenderung.
//
//

package de.decodetron.tab.benutzerverwaltung.anlegen;

import java.util.Set;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.Const;
import de.decodetron.bo.User;
import de.decodetron.tab.benutzerverwaltung.BenutzerPanel;

/**
 * @author Thomas Winter
 * @since 11.02.2014
 */
public class DlgBenutzerAnlegen extends Panel {

    public DlgBenutzerAnlegen(String id, final IModel model) {
        super(id, model);

        final BenutzerPanel benAnlegenPnl = new BenutzerPanel("modalBenAnlegen", getDefaultModel()) {
            @Override
            public void onConfirmP(AjaxRequestTarget target) {
                onConfirmA(target);
            }

            @Override
            public void onCancelP(AjaxRequestTarget target) {
                onCancelA(target);
            }
        };

        AjaxLink<String> confirmButton = new AjaxLink<String>("confirmButton", new Model("Action!")) {

            @Override
            public void onClick(AjaxRequestTarget target) {
                ValueMap map = (ValueMap) model.getObject();
                Set<User> changedUserList = (Set<User>) map.get(Const.KEY_DELETE_USERLIST);
                changedUserList.clear();

                benAnlegenPnl.initInputDlg(null);
                benAnlegenPnl.getModalWindow().show(target);
                // getModalWindow().show(target, null);
                target.appendJavaScript("$.fn.changeMaskStyle('wicket-mask-extra-dark');");
            }
        };

        Form form = new Form("confirmForm", model);
        form.add(confirmButton);
        form.add(benAnlegenPnl.getModalWindow());
        add(form);
    }

    public void onConfirmA(AjaxRequestTarget target) {
        // Bei Bedarf überschreiben ...
    }

    public void onCancelA(AjaxRequestTarget target) {
        // Bei Bedarf überschreiben ...
    }

}
