// $Log: UserDAOI.java,v $
// Revision 1.27  2014/11/23 21:53:43  tw
// https://team.decodetron.de:10443/index.php?c=task&a=view_task&id=3939
//
// Revision 1.26  2014/11/06 13:12:26  tw
// Testanbindung: History-DB Benutzerrechte.
//
// Revision 1.25  2014/09/09 21:31:37  tw
// Aep-Benutzerr-Rechteverwaltung: Benutzer Anlegen, Backup.
//
// Revision 1.24  2014/08/25 01:42:58  tw
// .
//
// Revision 1.23  2014/08/16 14:16:56  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung: Anbindung Textfelder.
//
// Revision 1.22  2014/08/15 14:39:36  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.21  2014/08/15 10:47:40  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.20  2014/08/14 10:09:54  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.19  2014/08/13 14:41:48  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.18  2014/08/12 01:03:12  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.17  2014/08/08 16:04:42  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.16  2014/07/25 13:50:11  tw
// Umstellung d. Benutzerverwaltung auf Prepared-Statements.
//
// Revision 1.15  2014/07/18 22:16:29  tw
// Bugfix: Schnittstelle Benutzerverwaltung, Berechtigung Superadmin / Admin.
//
// Revision 1.14  2014/06/12 12:28:21  tw
// Kommentar ergaenzt.
//
// Revision 1.13  2014/05/06 00:43:40  tw
// Backup: Benutzer anlegen.
//
// Revision 1.12  2014/05/04 20:22:48  tw
// Benutzer anlegen: Neue Schnittstelle: getDistinctByColName
//
// Revision 1.11  2014/03/27 12:06:55  tw
// Vorbereitung, CR-Defektenlisten f. Lieferanten: Markup-freie Tabellengestaltung.
//
// Revision 1.10  2014/03/27 01:20:43  tw
// Vorbereitung, CR-Defektenlisten f. Lieferanten: Markup-freie Tabellengestaltung.
//
// Revision 1.9  2014/02/28 15:39:49  tw
// Anpassung an neue DB-Struktur: Gebietssleiter.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.8  2014/02/20 03:52:16  tw
// Statistik-Schnittstellen implementiert.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.7  2014/02/14 00:09:59  tw
// Schnittstellenanpassung: User koennen mehrere Gruppen besitzen.
//
// Revision 1.6  2014/02/13 23:07:13  tw
// Schnittstellenanpassung: User koennen mehrere Gruppen besitzen.
//
// Revision 1.5  2014/02/13 13:05:46  tw
// Benutzerverwaltung, dynamischer Suchfilteraufbau, Schnittstellenanpassung.
//
// Revision 1.4  2014/02/04 13:22:08  tw
// Schnittstellenanpassung Benutzerverwaltung.
//
// Revision 1.3  2014/02/03 16:41:09  tw
// Implementierung Benutzerverwaltung
//
// Revision 1.2  2014/01/24 16:40:12  tw
// Schnittstellen aufgeraeumt. Neue limitierte Benutzerschnittstelle.
//
// Revision 1.1  2014/01/24 13:39:07  tw
// Schnittstellen aufgeraeumt. Neue limitierte Benutzerschnittstelle.
//
// Revision 1.4  2013/12/18 14:48:11  tw
// AEP-Token-Anmeldung implementiert.
//
// Revision 1.3  2013/12/05 14:36:55  tw
// ImplementierungTokenanmeldung.
//
// Revision 1.2  2013/12/03 15:46:16  tw
// Token-Login Schnittstellenvorbereitung.
//
// Revision 1.1  2013/11/21 17:40:02  tw
// Neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.7  2013/11/14 23:11:31  tw
// .
//
// Revision 1.6  2013/11/14 23:05:38  tw
// Passwort-andern Schnittstelle
//
// Revision 1.5  2013/11/13 00:29:59  tw
// Servicefunktionen fuer Berechtigungskonzept implementiert.
//
// Revision 1.4  2013/11/06 22:39:25  tw
// Neue Schnittstellentests f. flexiblere Abfragen.
//
// Revision 1.3  2013/11/05 23:02:09  tw
// db-schnittstellen aufgerumt.
//
// Revision 1.2  2013/11/05 19:31:31  tw
// db-schnittstellen aufgeraeumt.
//
// Revision 1.1  2013/11/02 21:46:42  tw
// ClubBestand-DB-Schicht. 1. Schritte zur Veralgemeinerung.
//
// Revision 1.4  2013/09/17 02:05:43  tw
// Login-Logout-Mechanismus implementiert.
//
// Revision 1.3  2013/09/16 14:35:39  tw
// Backup, Einbau: Login-Mechanismus
//
// Revision 1.2  2013/08/07 13:27:26  tw
// Standart-DB Schnittstellen inclusive Tests f�r User-Bearbeitung implementiert.
//
// Revision 1.1  2013/08/07 01:46:51  tw
// AEP LoginModul als simpelst-keine-Zusatzbibliotheken-Variante. Erster Wurf.
//
// Revision 1.7  2012/07/27 13:58:06  tw
// Neue Schnittstellen/Tests.
//
// Revision 1.6  2012/07/24 21:01:37  tw
// Neue Tabellenstruktur Knoten/Dokument2 eingef�hrt.
//
// Revision 1.5  2012/05/14 16:12:14  tw
// Mappen Anlegen/Aendern/Loeschen: Implementierung fortgesetzt.
//
// Revision 1.4  2012/04/03 13:37:44  tw
// Archiv-Anlegen-Implementiert.
//
// Revision 1.3  2012/03/22 01:48:21  tw
// Sprachen Gimmick hinzugef�gt.
//
// Revision 1.2  2012/03/02 01:35:49  tw
// Backup
//
// Revision 1.1  2012/03/01 23:40:26  tw
// Grundgeruest erstellt.
//
//

package de.decodetron.dao.user;

import java.util.HashMap;
import java.util.List;

import de.decodetron.bo.DataRecord;
import de.decodetron.bo.FilterItemList;
import de.decodetron.bo.Group;
import de.decodetron.bo.LimitInfo;
import de.decodetron.bo.PreparedStatementO;
import de.decodetron.bo.Section;
import de.decodetron.bo.SortInfo;
import de.decodetron.bo.User;
import de.decodetron.bo.UserSectionData;
import de.decodetron.dao.DAOI;
import de.decodetron.exc.DAOException;
import de.decodetron.fac.DAOFactoryJDBC;

/**
 * Spezielle-DB-Methoden f�r User Objekte.
 * 
 * @author Thomas Winter
 * @since 23.02.2012
 */
public interface UserDAOI extends DAOI {

    public User findByLogin(String login);

    /**
     * Returns the user from the database matching the given login and password, otherwise null.
     * Erwartet das Passwort verschlüsselt.
     * 
     * @param email
     *            The login of the user to be returned.
     * @param password
     *            The password of the user to be returned.
     * @return The user from the database matching the given login and password, otherwise null.
     * @throws DAOException
     *             If something fails at database level.
     */
    public User findByLoginPwd(String login, String pwd);

    /**
     * Eine andere Umschreibung für: findByLoginPwd. Erwartet das Passwort im Klartext,
     * verschlüsselt es und führt findByLoginPwd aus. Das Login wird in kleinbuchstaben gewandelt.
     * 
     * @return User
     */
    public User login(String login, String pwd);

    /**
     * Returns the user from the database matching the given ID, otherwise null.
     * 
     * @param id
     *            The ID of the user to be returned.
     * @return The user from the database matching the given ID, otherwise null.
     * @throws DAOException
     *             If something fails at database level.
     */
    public User findById(Long id) throws DAOException;

    /**
     * Returns a list of all users from the database ordered by user ID. The list is never null and
     * is empty when the database does not contain any user.
     * 
     * @return A list of all users from the database ordered by user ID.
     * @throws DAOException
     *             If something fails at database level.
     */
    public List<User> getAllUser() throws DAOException;

    /**
     * Gibt alle Benutzer der View Extrawurst-View zurück. Das sind die Benutzer, die Scanninbelege
     * beginnend mit 990* nicht sehen dürfen.
     * 
     * @return
     * @throws DAOException
     */
    public List<User> getAllUser990erExtraWurst() throws DAOException;

    /**
     * Liefert u.u. nicht alle Spaltenbezeichner, je nach Benutzerberechtigung.
     * 
     * @param User
     *            user
     * @param String
     *            tableName
     * @return DataRecord
     */
    public DataRecord getColumNamesAsDataRecord(User user, String tableName);

    /**
     * Create the given user in the database. The user ID must be null, otherwise it will throw
     * IllegalArgumentException. After creating, the DAO will set the obtained ID in the given user.
     * 
     * @param user
     *            The user to be created in the database.
     * @throws IllegalArgumentException
     *             If the user ID is not null.
     * @throws DAOException
     *             If something fails at database level.
     */
    public void insert(User user) throws IllegalArgumentException, DAOException;

    /**
     * Erzeugt neben dem User noch einen Eintrag in der Historientabelle.
     * 
     * @param User
     *            creator
     * @param User
     *            user2Insert
     * @throws IllegalArgumentException
     * @throws DAOException
     */
    public void insertH(User creator, User user2Insert) throws IllegalArgumentException, DAOException;

    /**
     * Die ID der Gruppe muss null sein. Nach dem erfolgreichen Erzeugen wird die ID in der Gruppe
     * gesetzt.
     * 
     * @param Group
     *            group
     * @throws IllegalArgumentException
     * @throws DAOException
     */
    public void insert(Group group) throws IllegalArgumentException, DAOException;

    /**
     * Update the given user in the database. The user ID must not be null, otherwise it will throw
     * IllegalArgumentException. Note: the password will NOT be updated. Use changePassword()
     * instead.
     * 
     * @param user
     *            The user to be updated in the database.
     * @throws IllegalArgumentException
     *             If the user ID is null.
     * @throws DAOException
     *             If something fails at database level.
     */
    public void update(User user) throws IllegalArgumentException, DAOException;

    /**
     * Speichert/Updatet alle Benutzer der Liste. Legt neue Gruppen bei Bedarf an.
     * 
     * @param User
     *            , der Anlegende Benutzer.
     * @param List
     *            <UserSectionData> userList
     */
    public void updateUserSection(User creator, List<UserSectionData> userList);

    /**
     * Speichert/Updatet einen einzelnen Benutze. Legt neue Gruppen bei Bedarf an. Siehe auch:
     * {@link #updateUserSection(List)}
     * 
     * @param User
     *            , der Anlegende Benutzer.
     * @param UserSectionData
     *            user
     */
    public void updateUserSection(User creator, UserSectionData user);

    /**
     * Spricht wohl für sich. Erwartet das Passwort im Klartext und speichert es verschlüsselt ab.
     * 
     * @param Long
     *            userId
     * @param String
     *            passwd
     */
    public void changePassword(Long userId, String passwd);

    /**
     * Delete the given user from the database. After deleting, the DAO will set the ID of the given
     * user to null.
     * 
     * @param user
     *            The user to be deleted from the database.
     * @throws DAOException
     *             If something fails at database level.
     */
    public void delete(User user) throws DAOException;

    /**
     * Ein Löschen des Benutzers erzeugt einen Eintrag in der Historientabelle.
     * 
     * @param User
     *            actor, Der auslösende Benutzer.
     * @param User
     *            user2Delete
     * @throws DAOException
     */
    public void deleteH(User actor, User user2Delete) throws DAOException;

    /**
     * Löscht alle User deren login null ist.
     * 
     * @throws DAOException
     */
    public void deleteInvalidUser() throws DAOException;

    /**
     * Nach erfolgreichem Löschvorgang wird die Id auf null gesetzt.
     * 
     * @param Group
     *            group
     * @throws DAOException
     */
    public void delete(Group group) throws DAOException;

    /**
     * Returns true if the given login exist in the database.
     * 
     * @param email
     *            The login which is to be checked in the database.
     * @return True if the given login exist in the database.
     * @throws DAOException
     *             If something fails at database level.
     */
    public boolean existLogin(String email) throws DAOException;

    /**
     * Sucht die Gruppe anhand der Kurzschreibweise und gibt die Id zurück.
     * 
     * @param Long
     *            groupKz
     * @return boolean
     * @throws DAOException
     */
    Long getGroupId4Kz(String groupKz) throws DAOException;

    /**
     * // * Besorgt die Gruppe für eine User-ID. // * // * @deprecated // * @param Long // * userId
     * // * @return Group //
     */
    // public Group getGroupForUser(Long userId);

    /**
     * 13.02.2014, Planänderung: In Zukunft können User mehrere Gruppen besitzen. Was für die
     * Sections-Besorgung galt, gilt jetzt auch für die Gruppen. Das Ergebnis muss durch Kombination
     * von: "gib mir alle Gruppen-Ids eines Users",
     * "splitte die Strings der multi-value-felder auf",
     * "besorge die Gruppe und leg sie in eine Liste", erledigt werden.
     * 
     * Von Helmut angetriggert, war ursprünglich die Idee die Gruppen sinnvoller granulieren zu
     * können und Dopplungen zu vermeinden, glaube ich.
     * 
     * @param Long
     *            userId
     * @return List<Group>
     */
    public List<Group> getGroups4User(Long userId);

    /**
     * Holt die Gruppe für die Id.
     * 
     * @param Long
     *            gId
     * @return Group
     */
    public Group getGroupForId(Long gId);

    /**
     * 26.02.2014: Einführung von Gebietsleitern. Gebe mir alle FilterItems der User die der
     * GebietId zugehörig sind.
     * 
     * Müsste eigentlich in einem Ausdruck zu erschlagen sein:
     * 
     * <pre>
     * select u.filter from user u 
     * where u.gebiet like '2'
     * or u.gebiet like '2,%'
     * or u.gebiet like '%,2';
     * </pre>
     * 
     * Falls es schiefgeht, muss alles mit dieser Funktion zusammengesammelt werden:
     * 
     * @see #getFilter4UserId(Long)
     * 
     * @param String
     *            gebietId
     * @return List<String>
     */
    public List<String> getFilter4GebietId(String gebietId);

    /**
     * Überlagerte Funktion. Grundfunktionalität siehe:
     * 
     * @see #getFilter4GebietId(String)
     * @param List
     *            <String> gebietIds
     * @return List<String>
     */
    public List<String> getFilter4GebietId(List<String> gebietIds);

    /**
     * 
     * @param Long
     *            userId
     * @return List<String>
     */
    public List<String> getFilter4UserId(Long userId);

    /**
     * Besort die Section für eine Gruppen-ID.
     * 
     * @param Long
     *            groupId
     * @return Section
     */
    public Section getSectionForId(Long secId);

    /**
     * Liefert alle Sektionen.
     * 
     * @return List<Section>
     */
    public List<Section> getAllSections();

    /**
     * Da die Sections leider so dämlich als multi-values in ein Feld gelegt wuden, lässt sich
     * dieser Aufruf leider nicht mit einem SQL-String erschlagen. Das Ergebnis muss durch
     * Kombination von getGroup!S!ForUser(Long userId) und getSectionForId(Long secId) erzeugt
     * werden.<br>
     * <br>
     * 14.02.2014, Nachtrag: Benutzer können jetzt mehrere Gruppen haben. Die Funktion stellt alle
     * Sektionen OHNE Dopplungen zusammen!
     * 
     * @param Long
     *            userId
     * @return List<Section>
     */
    public List<Section> getSectionsForUser(Long userId);

    /**
     * Dient im Moment letztendlich dazu das Masterpasswort auszulesen.
     * 
     * @param String
     *            login
     * @return String
     */
    public String getPasswdForLogin(String login);

    /**
     * Liefert eine Liste aller erlaubten IP-Adressen.
     * 
     * @return List<String>
     */
    public List<String> getIPAdressesAllowed();

    /**
     * Gibt alle Benutzer für die Benutzerverwaltung portionsweise zurück. Liefert je nach
     * Benutzerberechtigung alle User ausser die Superadmins. Ausserdem nur die, denen eine
     * existierende Gruppe zugeordnet wurde.
     * 
     * <pre>
     * select a.* from user a, groups g
     * where a.groupid = g.id
     * and g.kz not like 'su'
     * order by a.groupid asc limit 1000 offset 0;
     * </pre>
     * 
     * @param User
     *            user
     * @param SortInfo
     *            sortInfo
     * @param LimitInfo
     *            limitInfo
     * @param FilterItemList
     *            listTxtFields
     * @return List<User>
     */
    public List<User> getUser4Verwaltung(User user, SortInfo sortInfo, LimitInfo limitInfo, FilterItemList listTxtFields);

    /**
     * 
     * Gleiche Funktion wie: getUser4Verwaltung(User, SortInfo, LimitInfo, FilterItemList), nur mit
     * anderem Rückgabewert.
     * 
     * @see#getUser4Verwaltung(User, SortInfo, LimitInfo, FilterItemList)
     * @param User
     *            user
     * @param SortInfo
     *            sortInfo
     * @param LimitInfo
     *            limitInfo
     * @param FilterItemList
     *            listTxtFields
     * @return List<UserSectionData>
     */
    public List<UserSectionData> getUserSection4Verwaltung(User user, SortInfo sortInfo, LimitInfo limitInfo,
            FilterItemList listTxtFields);

    /**
     * Zählt je nach Benutzerberechtigung alle Benutzer ausser die Superadmins.
     * 
     * @param User
     *            user
     * @param FilterItemList
     *            listTxtFields
     * @return Long
     */
    public Long countUser4Verwaltung(User user, FilterItemList listTxtFields);

    /**
     * Gibt alle Gruppen zurück. Nicht-Superadmins bekommen alle außer der Superusergruppe.
     * 
     * @return List<Group>
     */
    List<Group> getAllGroups(User u);

    List<User> executeSQLStatement4PSUser(PreparedStatementO ps, DAOFactoryJDBC daoFactory);

    HashMap<String, Section> getSectionKZLevelMapping();

    /**
     * Generiert einen Hexwert der als Gruppenbezeichner dienen soll. In dem Namen spiegelt sich das
     * Muster der ausgewählten Sektionen. Sprich: gleiche Anzahl Sektionen vom gleichen Typ ergeben
     * den gleichen Gruppenbezeichner.<br>
     * <br>
     * 
     * @param UserSectionData
     *            user
     * @param int returnType: Mögliche Typen: HEX, BIN, STR
     * @return String
     */
    String generateGroupName4User(UserSectionData user, int returnType);

    /**
     * Wie {@link #generateGroupName4User(UserSectionData, int)}. Benutzt defaultmässig den
     * HEX-Returntype.
     * 
     * @param UserSectionData
     *            user
     * @return String
     */
    String generateGroupName4User(UserSectionData user);

    /**
     * Experimentell. Gehört eigentlich in die Sektion !?
     * 
     * @param kz
     * @return
     */
    // String getLevel4Kz(String kz);
}
