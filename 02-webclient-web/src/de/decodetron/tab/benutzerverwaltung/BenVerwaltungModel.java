// $Log: BenVerwaltungModel.java,v $
// Revision 1.7  2014/09/13 15:22:54  tw
// Rechteverwaltung: Bugfix: Textfeldaenderungen werden richtig verarbeitet f. d. Liste d. geaenderten Benutzer.
//
// Revision 1.6  2014/09/12 15:04:16  tw
// Aep-Benutzerr-Rechteverwaltung: Setzten des Defaultpasswortes.
//
// Revision 1.5  2014/08/26 14:37:22  tw
// Aep-Benutzerr-Rechteverwaltung: Benutzer Loeschen implementiert.
//
// Revision 1.4  2014/08/25 15:14:24  tw
// Aep-Benutzerr-Rechteverwaltung: Bugfix, Model auraeumen.
//
// Revision 1.3  2014/08/24 22:18:54  tw
// Aep-Benutzerr-Rechteverwaltung: Bugfix, Model auraeumen.
//
// Revision 1.2  2014/08/21 22:04:51  tw
// Aep-Benutzerr-Rechteverwaltung: Layout.
//
// Revision 1.1  2014/08/18 12:35:15  tw
// Modelumgestaltung.
//
//

package de.decodetron.tab.benutzerverwaltung;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.wicket.util.value.ValueMap;

import de.decodetron.Const;
import de.decodetron.bo.UserSectionData;

/**
 * @author Thomas Winter
 * @since 18.08.2014
 */
public class BenVerwaltungModel extends ValueMap {

    public BenVerwaltungModel() {
        // initUserList2Change();
        // initUserListOriginal();
    }

    // //////////////////////////////////////////////////////////////////////////////
    // /// Benutzerdaten zum LÖSCHEN
    // /
    public void addUser2Delete(UserSectionData u) {
        getUser2Delete().add(u);
    }

    public Set<UserSectionData> getUser2Delete() {
        return (Set<UserSectionData>) get(Const.KEY_DELETE_USERLIST);
    }

    public void initUserDeleteModel() {
        put(Const.KEY_DELETE_USERLIST, new LinkedHashSet<UserSectionData>());
    }

    // //////////////////////////////////////////////////////////////////////////////
    // /// Benutzerdaten zum Passwort ändern
    // /
//    public void addUser2PwdReset(UserSectionData u) {
//        getUser2PwdReset().add(u);
//    }
//
//    public Set<UserSectionData> getUser2PwdReset() {
//        return (Set<UserSectionData>) get(Const.KEY_BENW_RESETPWD2);
//    }
//
//    public void initUserPwdResetModel() {
//        put(Const.KEY_BENW_RESETPWD2, new LinkedHashSet<UserSectionData>());
//    }
    
    // //////////////////////////////////////////////////////////////////////////////
    // /// Benutzerdaten zum ÄNDERN
    // /
    public void initUserList2Change() {
        put(Const.KEY_USER_SECTION_2CHANGE, new HashMap<Long, UserSectionData>());
    }

    public HashMap<Long, UserSectionData> getUserList2Change() {
        return (HashMap<Long, UserSectionData>) get(Const.KEY_USER_SECTION_2CHANGE);
    }

    // //////////////////////////////////////////////////////////////////////////////
    // /// Original Benutzerdaten für Vergleiche
    // /
    public void initUserListOriginal() {
        put(Const.KEY_USER_SECTION_ORIGINAL, new LinkedHashSet<UserSectionData>());
    }

    @SuppressWarnings("unchecked")
    public LinkedHashSet<UserSectionData> getUserListOriginal() {
        return (LinkedHashSet<UserSectionData>) get(Const.KEY_USER_SECTION_ORIGINAL);
    }

    /**
     * @param userList
     *            the userList to set
     */
    public void addUserListOriginal(List<UserSectionData> ulist) {
        getUserListOriginal().addAll(ulist);
    }

    public void addUserListOriginal(UserSectionData user) {
        getUserListOriginal().add(user);
    }

    // //////////////////////////////////////////////////////////////////////////////
    // /// Fehlermeldungen
    // /
    /**
     * @return the userSectionErrorList
     */
    @SuppressWarnings("unchecked")
    public List<String> getErrorList() {
        return (List<String>) get(Const.KEY_USER_SECTION_ERROR);
    }

    /**
     * @param userSectionErrorList
     *            the userSectionErrorList to set
     */
    public void setErrorList(List<String> errorList) {
        put(Const.KEY_USER_SECTION_ERROR, errorList);
    }
}
