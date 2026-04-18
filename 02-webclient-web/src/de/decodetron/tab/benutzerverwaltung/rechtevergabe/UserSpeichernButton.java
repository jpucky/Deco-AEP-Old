// $Log: UserSpeichernButton.java,v $
// Revision 1.4  2014/11/06 13:13:04  tw
// Anbindung: History-DB Benutzerrechte.
//
// Revision 1.3  2014/10/29 14:58:59  tw
// Bugfix: setReuseitems f. sinnvollerer Errorhandling. Vorbereitung: Benutzer kopieren.
//
// Revision 1.2  2014/10/10 12:05:22  tw
// Implementierung: Benutzerrechte Vertriebsleiter.
//
// Revision 1.1  2014/09/10 16:15:42  tw
// Aep-Benutzerr-Rechteverwaltung: Aufraeumarbeiten, Klassen entdroeselt.
//
//

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;

import de.decodetron.AEPApplication;
import de.decodetron.AEPModel;
import de.decodetron.bo.UserSectionData;
import de.decodetron.data.Util;
import de.decodetron.security.LoginSession;

/**
 * Nachgelagerter Validate-Listener da die Validierung der Textfelder in der Liste nicht
 * funktioniert! Mit setReuseItems(true) würde es wahrscheinlich funktionieren. TODO: Checken wie
 * man diesen Mechanismus für den DataTable nachbaut oder aktiviert! Bisher bin ich an dieser Stelle
 * gescheitert!
 * 
 * @author Thomas Winter
 * @since 10.09.2014
 */
public class UserSpeichernButton extends UserRechteButton {

    private static Logger log = Logger.getLogger(UserSpeichernButton.class);

    public UserSpeichernButton(String id, IModel<?> iModel) {
        super(id, iModel);
        /**
         * Ist nötig, da das Model normalerweise zur Beschriftung verwendet wird!
         */
        add(AttributeAppender.replace("value", "Speichern"));
        add(AttributeAppender.append("class", "saveuser"));

        /**
         * Nur dann wird auch die IValidator Prüfung in der Form ausgelöst!
         * setDefaultFormProcessing(true); // Default == true!
         */
        add(new IValidator<String>() {
            @Override
            public void validate(IValidatable<String> validatable) {

                // //////////////////////////
                // /// Auswertung der Fehlermeldungen;
                // /// Vorsicht mit dem Löschen der Models! !!! Das hier wird VOR Submit
                // /// ausgelöst !!!
                // /
                boolean errorOccured = false;
                AEPModel vm = (AEPModel) getDefaultModelObject();
                List<String> errList = vm.getBenVerwaltungModel().getErrorList();
                if (errList.size() > 0) {
                    for (Iterator<String> iterator = errList.iterator(); iterator.hasNext();) {
                        errorOccured = true;
                        error(iterator.next());
                    }
                }

                // //////////////////////////
                // /// Löschen aller Fehlermeldungen;
                // /// und aller geänderten Nutzer im Speicher !?
                // /
                initErrorList();
                if (errorOccured) {
                    //initUserSectionChangeModel();
                    //initUserSectionOrignialModel();
                }
            }
        });
    }

    @Override
    public void onSubmit() {

        // showMap();
        StringBuffer sb = new StringBuffer("</br>");
        List<UserSectionData> debug = getUserSectionDatas();

        try {
            for (Iterator<UserSectionData> iterator = debug.iterator(); iterator.hasNext();) {
                UserSectionData userSectionData = iterator.next();
                AEPApplication.get().getDBUser().updateUserSection(LoginSession.get().getUser(), userSectionData);
                sb.append(Util.getCurrentTimeStamp());
                sb.append(" : ");
                sb.append(userSectionData.getLogin()).append("</br>");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            // //////////////////////////
            // /// Löschen aller geänderten Benutzer;
            // /
            initUserSectionChangeModel();
            initUserSectionOrignialModel();
        }

        if (debug.size() > 0) {
            info("Gespeichert: " + sb.toString());
        }

        // //////////////////////////
        // /// Löschen aller User mit null - Login;
        //
        // AEPApplication.get().getDBUser().deleteInvalidUser();
    }
    
    @Override
    public void onError() {
        // TODO Auto-generated method stub
        super.onError();
    }
}
