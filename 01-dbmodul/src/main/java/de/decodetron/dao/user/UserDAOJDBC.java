// $Log: UserDAOJDBC.java,v $
// Revision 1.56  2018/07/05 15:43:11  tw
// Bugfix: 11. Spalte knr hinzugefuegt.
//
// Revision 1.55  2017/06/22 13:36:07  tw
// Mobilmachung der Headrevision.
//
// Revision 1.54  2015/12/03 16:40:06  tw
// Bugfix: AEP - Bug Benutzerverwaltung (Filter/Gebiet).
//
// Revision 1.53  2015/02/11 14:33:59  tw
// Bugfix: Feng-ID: 3945. Apotheker duerfen ihr Passwort nicht aendern.
//
// Revision 1.52  2015/01/23 17:14:47  tw
// Kommentare ergaenzt.
//
// Revision 1.51  2014/11/23 21:53:43  tw
// https://team.decodetron.de:10443/index.php?c=task&a=view_task&id=3939
//
// Revision 1.50  2014/11/06 13:12:26  tw
// Testanbindung: History-DB Benutzerrechte.
//
// Revision 1.49  2014/10/10 12:05:07  tw
// Implementierung: Benutzerrechte Vertriebsleiter.
//
// Revision 1.48  2014/09/12 15:03:50  tw
// Aep-Benutzerr-Rechteverwaltung: Setzten des Defaultpasswortes.
//
// Revision 1.47  2014/09/09 21:31:37  tw
// Aep-Benutzerr-Rechteverwaltung: Benutzer Anlegen, Backup.
//
// Revision 1.46  2014/08/25 10:43:32  tw
// Bugfix: getSection4User
//
// Revision 1.45  2014/08/25 01:49:58  tw
// *** empty log message ***
//
// Revision 1.44  2014/08/25 01:42:58  tw
// .
//
// Revision 1.43  2014/08/20 13:43:28  tw
// Reihenfolge der Sektions-Ids vereinheitlicht durch SQL-Abfrage: "order by s.[MenuItemId], s.[SubSection], s.[Section]"
//
// Revision 1.42  2014/08/18 13:09:56  tw
// Bugfix: Aep-Benutzer-Rechteverwaltung.
//
// Revision 1.41  2014/08/16 14:16:56  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung: Anbindung Textfelder.
//
// Revision 1.40  2014/08/15 15:09:14  tw
// .
//
// Revision 1.39  2014/08/15 15:07:49  tw
// .
//
// Revision 1.38  2014/08/15 14:39:36  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.37  2014/08/15 10:47:40  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.36  2014/08/14 10:09:54  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.35  2014/08/14 01:15:24  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.33  2014/08/12 21:40:47  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.32  2014/08/12 12:02:51  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.30  2014/08/08 16:04:42  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.29  2014/07/25 13:50:11  tw
// Umstellung d. Benutzerverwaltung auf Prepared-Statements.
//
// Revision 1.28  2014/07/18 22:16:29  tw
// Bugfix: Schnittstelle Benutzerverwaltung, Berechtigung Superadmin / Admin.
//
// Revision 1.27  2014/07/17 15:04:24  tw
// Admins bekommen alle Spalten zu sehen.
//
// Revision 1.26  2014/06/12 15:37:11  tw
// Benutzer bearbeiten: Vorbereitung f. korrekte Gruppenverarbeitung.
//
// Revision 1.25  2014/05/28 11:32:24  tw
// Versuchsblase: Neudefinition v. Lieferanten. => Ausgebaut!
//
// Revision 1.24  2014/05/21 13:58:44  tw
// Versuchsblase: Neudefinition v. Lieferanten.
//
// Revision 1.23  2014/05/08 21:03:11  tw
// Bugfix: Fehlende Attribute ergaenzt, Insert(User)
//
// Revision 1.22  2014/05/06 00:43:40  tw
// Backup: Benutzer anlegen.
//
// Revision 1.21  2014/05/04 20:22:48  tw
// Benutzer anlegen: Neue Schnittstelle: getDistinctByColName
//
// Revision 1.20  2014/04/15 09:07:04  tw
// Bugfix login == null
//
// Revision 1.19  2014/04/03 14:44:51  tw
// Bean-Property-Bug behoben.
//
// Revision 1.18  2014/03/28 17:22:33  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.17  2014/03/27 12:20:42  tw
// task&id=3936: Korrekte Umsetzung setIsLieferant ...
//
// Revision 1.16  2014/03/27 12:06:55  tw
// Vorbereitung, CR-Defektenlisten f. Lieferanten: Markup-freie Tabellengestaltung.
//
// Revision 1.15  2014/03/27 01:20:43  tw
// Vorbereitung, CR-Defektenlisten f. Lieferanten: Markup-freie Tabellengestaltung.
//
// Revision 1.14  2014/02/28 15:39:49  tw
// Anpassung an neue DB-Struktur: Gebietssleiter.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.13  2014/02/26 15:00:16  tw
// Anpassung an neue DB-Struktur: Gebietssleiter.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.12  2014/02/25 16:21:59  tw
// Bugfix: Splitting.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.11  2014/02/20 03:52:16  tw
// Statistik-Schnittstellen implementiert.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.10  2014/02/14 00:09:59  tw
// Schnittstellenanpassung: User koennen mehrere Gruppen besitzen.
//
// Revision 1.9  2014/02/13 23:07:13  tw
// Schnittstellenanpassung: User koennen mehrere Gruppen besitzen.
//
// Revision 1.8  2014/02/13 13:05:46  tw
// Benutzerverwaltung, dynamischer Suchfilteraufbau, Schnittstellenanpassung.
//
// Revision 1.7  2014/02/05 13:44:04  tw
// Anpassung an neue DB-Struktur
//
// Revision 1.6  2014/02/04 13:22:08  tw
// Schnittstellenanpassung Benutzerverwaltung.
//
// Revision 1.5  2014/02/03 16:41:09  tw
// Implementierung Benutzerverwaltung
//
// Revision 1.4  2014/01/31 01:40:16  tw
// Adminberechtigung angepasst.
//
// Revision 1.3  2014/01/29 09:48:22  tw
// Vorbereitung: Belegart: EK
//
// Revision 1.2  2014/01/24 16:40:12  tw
// Schnittstellen aufgeraeumt. Neue limitierte Benutzerschnittstelle.
//
// Revision 1.1  2014/01/24 13:39:07  tw
// Schnittstellen aufgeraeumt. Neue limitierte Benutzerschnittstelle.
//
// Revision 1.8  2013/12/18 14:48:11  tw
// AEP-Token-Anmeldung implementiert.
//
// Revision 1.7  2013/12/18 13:09:42  tw
// Altes Feld rausgeworfen.
//
// Revision 1.6  2013/12/05 14:36:55  tw
// ImplementierungTokenanmeldung.
//
// Revision 1.5  2013/12/03 15:46:16  tw
// Token-Login Schnittstellenvorbereitung.
//
// Revision 1.4  2013/12/02 22:04:10  tw
// Bugfix Usermapping.
//
// Revision 1.3  2013/12/02 21:58:18  tw
// Spalten: KundenNr, LieferscheinNr werden fuer alle Lieferanten in allen Listen gefiltert.
//
// Revision 1.2  2013/12/02 17:33:45  tw
// Vorbereitungen fuer gezieltes Spaltenfiltern.
//
// Revision 1.1  2013/11/21 17:40:02  tw
// Neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.13  2013/11/17 13:41:59  tw
// Neue Schnittstelle f. User mit mehrfachfiltern implementiert.
//
// Revision 1.12  2013/11/14 23:11:31  tw
// .
//
// Revision 1.11  2013/11/14 23:05:38  tw
// Passwort-andern Schnittstelle
//
// Revision 1.10  2013/11/14 01:34:42  tw
// aaaaaaaaaaaaaaargh umlaute.
//
// Revision 1.9  2013/11/13 23:13:20  tw
// Vorbereitung: Schnittstellen fuer Datenfilter.
//
// Revision 1.8  2013/11/13 16:13:38  tw
// Sectionsplitting geaendert.
//
// Revision 1.7  2013/11/13 01:50:24  tw
// Tests an aktuelle Tabellensituation angepasst.
//
// Revision 1.6  2013/11/13 00:29:59  tw
// Servicefunktionen fuer Berechtigungskonzept implementiert.
//
// Revision 1.5  2013/11/06 22:39:25  tw
// Neue Schnittstellentests f. flexiblere Abfragen.
//
// Revision 1.4  2013/11/05 23:02:09  tw
// db-schnittstellen aufgerumt.
//
// Revision 1.3  2013/11/05 19:31:31  tw
// db-schnittstellen aufgeraumt.
//
// Revision 1.2  2013/11/02 23:16:39  tw
// Bugfix.
//
// Revision 1.1  2013/11/02 21:46:42  tw
// ClubBestand-DB-Schicht. 1. Schritte zur Veralgemeinerung.
//
// Revision 1.6  2013/09/17 02:05:43  tw
// Login-Logout-Mechanismus implementiert.
//
// Revision 1.5  2013/09/16 14:35:39  tw
// Backup, Einbau: Login-Mechanismus
//
// Revision 1.4  2013/08/12 14:16:55  tw
// Tests f�r Nebenl�ufige Performance-Tests Erstellt.
//
// Revision 1.3  2013/08/12 10:49:50  tw
// Neues Attribut: anlagedatum erstellt.
//
// Revision 1.2  2013/08/07 13:27:26  tw
// Standart-DB Schnittstellen inclusive Tests f�r User-Bearbeitung implementiert.
//
// Revision 1.1  2013/08/07 01:46:51  tw
// AEP LoginModul als simpelst-keine-Zusatzbibliotheken-Variante. Erster Wurf.
//
//

package de.decodetron.dao.user;

import static de.decodetron.util.DAOUtil.close;
import static de.decodetron.util.DAOUtil.prepareStatement;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import de.decodetron.bo.DataRecord;
import de.decodetron.bo.FilterItem;
import de.decodetron.bo.FilterItemList;
import de.decodetron.bo.Group;
import de.decodetron.bo.HistoryBr;
import de.decodetron.bo.LimitInfo;
import de.decodetron.bo.PreparedStatementO;
import de.decodetron.bo.Section;
import de.decodetron.bo.SortInfo;
import de.decodetron.bo.User;
import de.decodetron.bo.UserSectionData;
import de.decodetron.dao.DAO;
import de.decodetron.exc.DAOException;
import de.decodetron.fac.DAOFactoryJDBC;
import de.decodetron.util.Const;
import de.decodetron.util.CryptoTools;
import de.decodetron.util.Util;

/**
 * User - Schnittstellen für die Tabelle: User. Soll alles schön low-level sein!.<br>
 * <br>
 * Die Factory-Konstruktion ist geklaut von der Seite:
 * http://balusc.blogspot.de/2008/07/dao-tutorial-data-layer.html
 * 
 * @author Thomas Winter
 * @since 23.02.2012
 */
public class UserDAOJDBC extends DAO implements UserDAOI {

    private static StringBuilder SQL_UPTDATE_USER = new StringBuilder();
    private static LinkedHashMap<String, Section> SECTION_KZ_LEVEL_MAPPING = new LinkedHashMap<String, Section>();

    static {
        SQL_UPTDATE_USER.append("UPDATE User SET ");
        SQL_UPTDATE_USER.append(Const.TBL_USER_VORNAME).append(" = ?, ");
        SQL_UPTDATE_USER.append(Const.TBL_USER_NACHNAME).append(" = ?, ");
        SQL_UPTDATE_USER.append(Const.TBL_USER_LOGIN).append(" = ?, ");
        SQL_UPTDATE_USER.append(Const.TBL_USER_PASSWD).append(" = ?, ");
        SQL_UPTDATE_USER.append(Const.TBL_USER_ANLAGEDATUM).append(" = ?, ");
        SQL_UPTDATE_USER.append(Const.TBL_USER_FILTER).append(" = ?, ");
        SQL_UPTDATE_USER.append(Const.TBL_USER_GROUPID).append(" = ?, ");
        SQL_UPTDATE_USER.append(Const.TBL_USER_VLEITUNG).append(" = ?, ");
        SQL_UPTDATE_USER.append(Const.TBL_USER_GEBIET).append(" = ? ");
        SQL_UPTDATE_USER.append(" WHERE ").append(Const.TBL_USER_ID).append(" = ?");
    }

    private static final String SQL_FIND_BY_ID = "SELECT * FROM User WHERE id = ?";
    private static final String SQL_FIND_BY_LOGIN_AND_PASSWORD = "SELECT * FROM User WHERE login = ? AND passwd = ?";
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM User WHERE login = ?";
    private static final String SQL_LIST_ORDER_BY_ID = "SELECT * FROM User ORDER BY id";
    private static final String SQL_INSERT_USER = "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_INSERT_GROUP = "INSERT INTO groups VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = SQL_UPTDATE_USER.toString();
    private static final String SQL_DELETE_USER = "DELETE FROM User WHERE id = ?";
    private static final String SQL_DELETE_INVALIDUSER = "DELETE FROM User WHERE login is null";
    private static final String SQL_DELETE_GROUP = "DELETE FROM groups WHERE id = ?";
    private static final String SQL_EXIST_LOGIN = "SELECT id FROM User WHERE login = ?";
    private static final String SQL_GET_ID4_GROUP_BY_KZ = "SELECT id FROM Groups g WHERE g.kz like ?;";
    // private static final String SQL_SECTION_KZ_LEVEL_MAPPING =
    // "select i.[KZ], substr(i.[InfoText],0, 3) as 'LEVEL' from [main].[Info] i order by i.[InfoText] asc";
    private static final String SQL_SECTION_KZ_LEVEL_MAPPING = "select s.* from sections s where s.[KZ] not null order by s.[MenuItemId] asc";
    private static final String SQL_VIEW_990EXTRAWURST = "select * from v_990ewurst e";
    //private static final String SQL_VIEW_990EXTRAWURST_FILTER = "select distinct filter from v_990ewurst";

    public static final String PROP_USER_LOGIN = "userLogin";
    public static List<Long> USERID_990ER_LIST = new ArrayList<Long>();
    public static String FILTER_990ER_LIST = "";

    public static final int HEX = 16;
    public static final int BIN = 2;
    public static final int STR = 1;

    public static final String COL_FILTER = "Filter";
    public static final String COL_GROUPID = "GroupIds";

    // Vars ---------------------------------------------------------------------------------------

    private DAOFactoryJDBC daoFactory;
    private Logger log = Logger.getLogger(UserDAOJDBC.class);

    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct an User DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     * 
     * @param daoFactory
     *            The DAOFactory to construct this User DAO for.
     */
    public UserDAOJDBC(DAOFactoryJDBC daoFactory) {
        super(daoFactory);
        this.daoFactory = daoFactory;
        // initSectionKZLevelMapping();
    }

    public List<User> getAllUser() throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<User>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(mapUser(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return users;
    }

    public List<User> getAllUser990erExtraWurst() throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<User>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL_VIEW_990EXTRAWURST);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(mapUser(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return users;
    }

    public void insert(User user) throws IllegalArgumentException, DAOException {
        if (user.getId() != null) {
            throw new IllegalArgumentException("User is already created, the user ID is not null.");
        }

        checkUserConstraints(user);

        Object[] values = {//
        user.getId(),// 1
                user.getVorname(),// 2
                user.getNachname(),// 3
                user.getLogin().toLowerCase(),// 4
                user.getPasswd(),// 5
                user.getAnlagedatum(), // 6
                user.getFilter(), // 7
                user.getGroupIds(), // 8
                user.getVertriebsleitung(),// 9
                user.getGebiet() // 10
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_INSERT_USER, true, values);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating user failed, no rows affected.");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1));
            } else {
                throw new DAOException("Creating user failed, no generated key obtained.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, generatedKeys);
        }
    }

    @Override
    public void insertH(User actor, User user2Insert) throws IllegalArgumentException, DAOException {

        insert(user2Insert);

        // //////////////////////////////
        // /// Historie aktualisieren
        // /
        HistoryBr hBenuzterrechte = new HistoryBr();
        hBenuzterrechte.setId_user(actor.getId());
        hBenuzterrechte.setAktion(HistoryBr.AKTION_ANLEGEN);
        hBenuzterrechte.setTimeStamp(System.currentTimeMillis());
        hBenuzterrechte.setXmlUserData(Util.object2XmlString(createUserSectionData(user2Insert)));

        getDBHistoryBr().insert(hBenuzterrechte);
    }

    public void insert(Group group) throws IllegalArgumentException, DAOException {
        if (group.getId() != null) {
            throw new IllegalArgumentException("Group is already created, the group ID is not null.");
        }

        Object[] values = {//
        group.getId(),// 1
                group.getName(),// 2
                group.getKz(),// 3
                group.getSectionids(),// 4
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_INSERT_GROUP, true, values);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating user failed, no rows affected.");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                group.setId(generatedKeys.getLong(1));
            } else {
                throw new DAOException("Creating user failed, no generated key obtained.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, generatedKeys);
        }
    }

    public void update(User user) throws IllegalArgumentException, DAOException {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User is not created yet, the user ID is null.");
        }

        checkUserConstraints(user);

        Object[] values = {//
        user.getVorname(),//
                user.getNachname(),//
                user.getLogin().toLowerCase(),//
                user.getPasswd(),//
                user.getAnlagedatum(),//
                user.getFilter(),//
                user.getGroupIds(),//
                user.getVertriebsleitung(),//
                user.getGebiet(),//
                user.getId() // VORSICHT, ID AM ENDE!
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_UPDATE, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating user failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement);
        }
    }

    /**
     * Eine Billigprüfung, ob er Vertriebsleiter ist.
     */
    private boolean isUserVertriebsleiter(User user) {
        if (user == null) {
            return false;
        }

        if (user.getAllVertriebsleitungen() != null && user.getAllVertriebsleitungen().size() >= 1
                && user.getAllVertriebsleitungen().get(0).length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Regel: <br>
     * 1. Ein Vertriebsleiter muss einen leeren Filter haben. <br>
     * 2. Ist ein User einem Gebiet zugeordnet darf kein '*' im Filter enthalten sein.
     */
    private void checkUserConstraints(User user) {

        // 1.
        if (isUserVertriebsleiter(user)) {
            user.setFilter(null);
        }

        // 2.
        if ((user.getFilter() != null) && (user.getFilter().length() > 1) && (user.getGebiet() != null)
                && (user.getGebiet().length() > 0)) {
            user.setFilter(user.getFilter().replaceAll("\\*", ""));
        }
    }

    public void delete(User user) throws DAOException {
        Object[] values = { user.getId() };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_DELETE_USER, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting user failed, no rows affected.");
            } else {
                user.setId(null);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement);
        }
    }

    @Override
    public void deleteH(User actor, User user2Delete) throws DAOException {

        // //////////////////////////////
        // /// Historie aktualisieren
        // /
        HistoryBr hBenuzterrechte = new HistoryBr();
        hBenuzterrechte.setId_user(actor.getId());
        hBenuzterrechte.setAktion(HistoryBr.AKTION_LOESCHEN);
        hBenuzterrechte.setTimeStamp(System.currentTimeMillis());
        hBenuzterrechte.setXmlUserData(Util.object2XmlString(createUserSectionData(user2Delete)));

        // //////////////////////////////
        // /// erst jetzt löschen, nicht vorher!
        // /
        delete(user2Delete);

        getDBHistoryBr().insert(hBenuzterrechte);
    }

    public void deleteInvalidUser() throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_DELETE_INVALIDUSER, false);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                // throw new DAOException("Deleting user failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement);
        }
    }

    public void delete(Group group) throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_DELETE_GROUP, false, new Object[] { group.getId() });
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting group failed, no rows affected.");
            } else {
                group.setId(null);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement);
        }
    }

    /**
     * Erzeugt eine nach MenuItemId sortierte LinkedHashMap.
     */
    public LinkedHashMap<String, Section> getSectionKZLevelMapping() {

        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        LinkedHashMap<String, Section> sectionKzLevelMapping = new LinkedHashMap<String, Section>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SECTION_KZ_LEVEL_MAPPING);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String kz = resultSet.getString("KZ");
                // String level = resultSet.getString("MenuItemId");
                // sectionKzLevelMapping.put(kz, level);
                Section secTmp = mapSection(resultSet);
                sectionKzLevelMapping.put(kz, secTmp);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return sectionKzLevelMapping;
    }

    @Override
    public User findById(Long id) throws DAOException {
        return find(SQL_FIND_BY_ID, id);
    }

    public User findByLogin(String login) throws DAOException {
        return find(SQL_FIND_USER_BY_LOGIN, login);
    }

    @Override
    public User findByLoginPwd(String login, String password) throws DAOException {
        login = login == null ? "" : login;
        User u = find(SQL_FIND_BY_LOGIN_AND_PASSWORD, login, password);
        return u;
    }

    public User login(String login, String password) throws DAOException {
        login = login == null ? "" : login;
        User u = findByLoginPwd(login.toLowerCase(), new CryptoTools().encryptOneWaySHA1(password));

        // ////////////////////////////////////////////////////////
        // /// Die Liste soll bei jedem Login frisch geladen werden
        // /
        USERID_990ER_LIST.clear();
        if (u != null) {
            for (Iterator<User> iterator = getAllUser990erExtraWurst().iterator(); iterator.hasNext();) {
                USERID_990ER_LIST.add(iterator.next().getId());
            }
        }

        return u;
    }

    /**
     * Returns the user from the database matching the given SQL query with the given values.
     * 
     * @param sql
     *            The SQL query to be executed in the database.
     * @param values
     *            The PreparedStatement values to be set.
     * @return The user from the database matching the given SQL query with the given values.
     * @throws DAOException
     *             If something fails at database level.
     */
    private User find(String sql, Object... values) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = mapUser(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return user;
    }

    @Override
    public boolean existLogin(String email) throws DAOException {

        Object[] values = { email };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean exist = false;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_EXIST_LOGIN, false, values);
            resultSet = preparedStatement.executeQuery();
            exist = resultSet.next();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return exist;
    }

    public Long getGroupId4Kz(String groupKz) throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Long id = -1L;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_GET_ID4_GROUP_BY_KZ, false, new Object[] { groupKz });
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getLong("Id");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return id;
    }

    /**
     * Usermapping per Reflection. Eine Versuch das Auslesen der Daten flexibler zu gestalten und
     * dennoch nicht auf Objekte verzichten zu müssen.
     * 
     * @param resultSet
     *            The ResultSet of which the current row is to be mapped to an User.
     * @return The mapped User from the current row of the given ResultSet.
     * @throws SQLException
     *             If something fails at database level.
     */
    private User mapUser(ResultSet resultSet) throws SQLException {

        User user = new User();

        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int colnr = rsmd.getColumnCount();
            for (int i = 1; i <= colnr; ++i) {
                String debugColName = lowerCaseFirstChar(rsmd.getColumnName(i));
                BeanUtils.setProperty(user, debugColName, resultSet.getString(debugColName));
            }

        } catch (Exception e) {
            System.out.println("###############################################");
            System.out.println("#" + user.getLogin());
            System.out.println("###############################################");
            e.printStackTrace();
        }
        // user.setId(resultSet.getLong(Const.TBL_USER_ID));
        // user.setLogin(resultSet.getString(Const.TBL_USER_LOGIN));
        // user.setVorname(resultSet.getString(Const.TBL_USER_VORNAME));
        // user.setNachname(resultSet.getString(Const.TBL_USER_NACHNAME));
        // user.setPasswd(resultSet.getString(Const.TBL_USER_PASSWD));
        // user.setAnlagedatum(resultSet.getString(Const.TBL_USER_ANLAGEDATUM));
        // user.setGroupid(resultSet.getString(Const.TBL_USER_GROUPID));

        List<Group> gl = getGroups4User(user.getId());
        if (gl != null) {
            for (Iterator<Group> iterator = gl.iterator(); iterator.hasNext();) {
                Group g = iterator.next();
                user.setIsSuperAdmin("SU".equalsIgnoreCase(g.getKz()) || "*".equals(g.getSectionids()));
                user.setIsAdministrator("ADM".equalsIgnoreCase(g.getKz()) || user.getIsSuperAdmin());
                user.setIsLieferant("LIEF".equalsIgnoreCase(g.getKz()));
                user.setIsApotheker("APO".equalsIgnoreCase(g.getKz()));
            }
        }

        /**
         * Testblase um den Lieferanten neu zu definieren.
         * 
         * List<Section> secList = getSectionsForUser(user.getId()); if (secList != null) { int cnt
         * = 0; for (Iterator<Section> iterator = secList.iterator(); iterator.hasNext();) { Section
         * section = iterator.next(); if ("clubbestand".equalsIgnoreCase(section.getKz())) { cnt++;
         * } else if ("clubabver".equalsIgnoreCase(section.getKz())) { cnt++; } else if
         * ("ververl".equalsIgnoreCase(section.getKz())) { cnt++; } } user.setIsLieferant(cnt == 3
         * && !user.getIsSuperAdmin()); }
         */

        // ///////////////////////////////////////////////////////////////
        // /// Issers oder nicht
        // /
        user.setIsVertriebsleiter(isUserVertriebsleiter(user));

        // ///////////////////////////////////////////////////////////////
        // /// Sammeln der Filter falls Vertriebsleiter ...
        // /
        user.addFilter(getFilter4GebietId(user.getAllVertriebsleitungen()));

        return user;
    }

    private Group mapGroup(ResultSet resultSet) throws SQLException {
        Group group = new Group();

        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int colnr = rsmd.getColumnCount();
            for (int i = 1; i <= colnr; ++i) {
                String debugColName = rsmd.getColumnName(i).toLowerCase();
                BeanUtils.setProperty(group, debugColName, resultSet.getString(debugColName));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return group;
    }

    private Section mapSection(ResultSet resultSet) throws SQLException {
        Section section = new Section();
        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int colnr = rsmd.getColumnCount();
            for (int i = 1; i <= colnr; ++i) {
                String debugColName = rsmd.getColumnName(i).toLowerCase();
                BeanUtils.setProperty(section, debugColName, resultSet.getString(debugColName));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return section;
    }

    public List<String> getFilter4GebietId(List<String> gebietIds) {
        List<String> filterList = new ArrayList<String>();
        for (Iterator<String> iterator = gebietIds.iterator(); iterator.hasNext();) {
            String gebietId = iterator.next();
            List<String> tmpList = getFilter4GebietId(gebietId);
            filterList.addAll(tmpList);
        }
        return filterList;
    }

    /**
     * Besorgt alle einem Gebiet zugehörigen Filter (Benutzer u. deren Filter).
     */
    public List<String> getFilter4GebietId(String gebietId) {

        List<String> filter = new ArrayList<String>();
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        if (gebietId == null || gebietId.length() == 0) {
            return filter;
        }

        try {
            connection = daoFactory.getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("select u.filter from user u ");
            sb.append("where u.gebiet like '").append(gebietId).append("' ");
            sb.append("or u.gebiet like '").append(gebietId).append(",%' ");
            sb.append("or u.gebiet like '%,").append(gebietId).append("'");
            sb.append("or u.gebiet like '%,").append(gebietId).append(",%';");

            // /StringBuilder sb = new StringBuilder();
            // sb.append("select u.filter, u.gebiet from user u where u.gebiet not null");
            // sb.append("select u.filter, u.gebiet from user u where u.gebiet = ").append(gebietId);
            preparedStatement = connection.prepareStatement(sb.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                List<String> filterTmp = Util.toList(resultSet.getString(1));
                // List<String> gebieteTmp = Util.toList(resultSet.getString(2));
                // if (gebieteTmp != null && gebieteTmp.size() > 0 && gebieteTmp.contains(gebietId))
                // {
                filter.addAll(filterTmp);
                // }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return filter;
    }

    // TODO: Ungetestet !!!
    @Override
    public List<String> getFilter4UserId(Long userId) {

        List<String> filter = new ArrayList<String>();
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("select u.filter from user u ");
            sb.append("where u.id=").append(userId).append(";");
            preparedStatement = connection.prepareStatement(sb.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String s = resultSet.getString(0);
                if (s != null) {
                    filter.add(s);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return filter;
    }

    @Override
    public List<Group> getGroups4User(Long userId) {

        List<Group> groupList = new ArrayList<Group>();

        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();

            StringBuilder sb = new StringBuilder();
            sb.append("select u.groupids from user u where u.id = ");
            sb.append(String.valueOf(userId)).append(";");

            String result = null;
            preparedStatement = connection.prepareStatement(sb.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getString(1);
            }

            if (result != null) {
                List<String> lgroupIds = Util.toList(result);
                for (Iterator<String> iterator = lgroupIds.iterator(); iterator.hasNext();) {
                    String groupId = iterator.next();
                    Group g = getGroupForId(Long.valueOf(groupId));
                    if (g != null) { // z.B. Gruppen mit der ID 0!
                        groupList.add(g);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return groupList;
    }

    @Override
    public Section getSectionForId(Long secId) {

        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Section section = null;

        try {
            connection = daoFactory.getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("select * from sections s ");
            sb.append("where s.id=").append(secId).append(";");
            preparedStatement = connection.prepareStatement(sb.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                section = mapSection(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return section;
    }

    public Group getGroupForId(Long gId) {

        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Group group = null;

        try {
            connection = daoFactory.getConnection();
            StringBuilder sb = new StringBuilder();
            // sb.append("select * from group g ");
            // sb.append("where g.id=").append(gId).append(";");
            sb.append("select * from groups where id = ?");

            // preparedStatement = connection.prepareStatement(sb.toString());
            preparedStatement = prepareStatement(connection, sb.toString(), false, gId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                group = mapGroup(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return group;
    }

    @Override
    public List<Section> getAllSections() {
        return getAllSections("select * from sections s");
    }

    public List<Section> getAllSections(String sql) {

        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Section> sections = new ArrayList<Section>();

        if (sql == null || sql.length() == 0) {
            return sections;
        }

        try {
            connection = daoFactory.getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append(sql);
            sb.append(" order by s.[MenuItemId], s.[SubSection], s.[Section] asc;");
            preparedStatement = connection.prepareStatement(sb.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sections.add(mapSection(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return sections;
    }

    @Override
    public List<Section> getSectionsForUser(Long userId) {

        StringBuffer sql = new StringBuffer();
        Set<Section> list = new LinkedHashSet<Section>();
        // Group group = getGroupForUser(userId);
        List<Group> groupList = getGroups4User(userId);

        if (groupList == null || groupList.size() == 0) {
            StringBuffer b = new StringBuffer();
            b.append("\n################################################################\n");
            b.append("Der Benutzer mit der ID: ");
            b.append(userId);
            b.append(" besitzt eine nicht gueltige Gruppe! ").append("\n");
            b.append("################################################################\n");
            log.error(b.toString());
            return null;
        }

        int groupCnt = 0;
        for (Iterator<Group> iterator = groupList.iterator(); iterator.hasNext();) {

            if (++groupCnt > 1) {
                sql.append(" or ");
            }
            Group group = iterator.next();
            List<String> sectionid = Util.toList(group.getSectionids());

            if (!sectionid.get(0).isEmpty() && (groupCnt == 1)) {
                sql.append("select * from sections s");
            }

            if (sectionid.get(0).startsWith("*")) {
                list.addAll(getAllSections());
            } else {

                for (int i = 0; i < sectionid.size(); i++) {
                    String secid = sectionid.get(i) != null ? sectionid.get(i).trim() : null;
                    if (secid.matches("\\d+")) {
                        sql.append(!sql.toString().contains("where") ? " where " : "");
                        sql.append(" s.id = ").append(secid);
                        sql.append((i < (sectionid.size() - 1)) ? " or " : "");
                        // list.add(getSectionForId(Long.valueOf(secid)));
                    }
                }
            }
        }

        list.addAll(getAllSections(sql.toString()));

        return new ArrayList<Section>(list);
    }

    @Override
    public void changePassword(Long userId, String passwd) {
        User user = findById(userId);
        user.setPasswd(new CryptoTools().encryptOneWaySHA1(passwd));
        update(user);
    }

    @Override
    public String getPasswdForLogin(String login) {
        User u = find(SQL_FIND_USER_BY_LOGIN, login);
        return u.getPasswd();
    }

    @Override
    public List<String> getIPAdressesAllowed() {

        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<String> ipallowed = new ArrayList<String>();

        try {
            connection = daoFactory.getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("select * from ipallowed;");
            preparedStatement = connection.prepareStatement(sb.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ipallowed.add(resultSet.getString(Const.TBL_IPALLOWED_IP));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return ipallowed;
    }

    @Override
    public List<User> getUser4Verwaltung(User user, SortInfo sortInfo, LimitInfo limitInfo, FilterItemList listTxtFields) {

        // ////////////////////////////////////////////////
        // /// Prepared-Statement
        // /
        StringBuilder ps = new StringBuilder();
        ps.append("select u.* from user u ");

        PreparedStatementO ps1 = getFilterSQLPS(user.getIsSuperAdmin(), listTxtFields);
        ps.append(ps1.getSql());

        // ///////////////////////////////////////////////
        // /// Order ...
        // /
        ps.append(" order by ");
        ps.append("u.").append(sortInfo.getColIndex2Sort()).append(" ");
        ps.append(" ").append(sortInfo.getSortOrder()).append(" ");

        // ///////////////////////////////////////////////
        // /// Limit ...
        // /
        ps.append(" limit ").append(limitInfo.getLimit().toString()).append(" ");
        ps.append(" offset ").append(limitInfo.getOffset().toString()).append(";");
        ps.append(";");

        PreparedStatementO all = new PreparedStatementO();
        all.setSql(ps.toString());
        all.setValues(Util.concatAllArrays(ps1.getValues()));

        return executeSQLStatement4PSUser(all, daoFactory);
    }

    @Override
    public Long countUser4Verwaltung(User user, FilterItemList listTxtFields) {

        StringBuilder ps = new StringBuilder();
        ps.append("select count(*) from user u ");

        PreparedStatementO ps1 = getFilterSQLPS(user.getIsSuperAdmin(), listTxtFields);
        ps.append(ps1.getSql());
        ps.append(";");

        PreparedStatementO all = new PreparedStatementO();
        all.setSql(ps.toString());
        all.setValues(Util.concatAllArrays(ps1.getValues()));

        return executeSQLCountStatemet4PS(all, daoFactory);
    }

    // /**
    // * Liefert zu dem kurzzeichen den jeweiligen Level. Der Zugriff erfolgt aus der HashMap. Es
    // * erfolgt also kein DB-Zugriff!
    // *
    // * @param String
    // * kz
    // * @return String level 01-X
    // */
    // public String getLevel4Kz(String kz) {
    //
    // if (SECTION_KZ_LEVEL_MAPPING.size() == 0) {
    // // Initialiserung
    // SECTION_KZ_LEVEL_MAPPING.putAll(getSectionKZLevelMapping());
    // }
    //
    // Set<String> keyset = SECTION_KZ_LEVEL_MAPPING.keySet();
    // for (Iterator<String> iterator = keyset.iterator(); iterator.hasNext();) {
    // String sectionKz = iterator.next();
    // Section section = SECTION_KZ_LEVEL_MAPPING.get(sectionKz);
    // if (sectionKz.equals(kz)) {
    // break;
    // }
    //
    // }
    // return "";
    // }

    @Override
    public List<UserSectionData> getUserSection4Verwaltung(User user, SortInfo sortInfo, LimitInfo limitInfo,
            FilterItemList listTxtFields) {

        long start = System.currentTimeMillis();
        List<User> userList = getUser4Verwaltung(user, sortInfo, limitInfo, listTxtFields);
        // System.out.println("Performance - Check, Stufe 1: " + (System.currentTimeMillis() -
        // start));

        // start = System.currentTimeMillis();
        List<UserSectionData> lUserSection = new ArrayList<UserSectionData>();

        if (SECTION_KZ_LEVEL_MAPPING.size() == 0) {
            // Initialiserung
            SECTION_KZ_LEVEL_MAPPING.putAll(getSectionKZLevelMapping());
        }

        // Ist mir im Moment wurscht, dass das ein Schritt zuviel ist.
        for (Iterator<User> iterator = userList.iterator(); iterator.hasNext();) {

            User userTmp = iterator.next();
            // List<Section> sections4User = getSectionsForUser(userTmp.getId());
            // if (sections4User == null) {
            // continue;
            // }

            UserSectionData userSectionData = createUserSectionData(userTmp);

            /**
             * UserSectionData userSectionData = new UserSectionData(userTmp);
             * 
             * // ///////////////////////////////////////////////////////////////// // /// Setzten
             * ALLER Sektionslevel // / initSectionInfo(userSectionData);
             * 
             * // ///////////////////////////////////////////////////////////////// // ///
             * Defaultpasswort // /
             * userSectionData.setIsDefaultPasswd(Const.STANDARTPWD_VER.equals(userTmp.getPasswd())
             * ? Boolean.TRUE : Boolean.FALSE);
             * 
             * if (sections4User != null) { for (Iterator<Section> iterator2 =
             * sections4User.iterator(); iterator2.hasNext();) { Section section = iterator2.next();
             * String sectionKz = section.getKz(); if (sectionKz == null) { continue; }
             * 
             * // ///////////////////////////////////////////////////////////////// // /// Setzten
             * der Sektionsattribute die der Benutzer auch hat. // / String setterName =
             * sectionKz.toLowerCase(); try { BeanUtils.setProperty(userSectionData, setterName +
             * ".isSet", Boolean.TRUE); } catch (Exception e) { e.printStackTrace(); } } }
             */

            lUserSection.add(userSectionData);
        }

        // System.out.println("Performance - Check, Stufe 2: " + (System.currentTimeMillis() -
        // start));
        return lUserSection;
    }

    /**
     * Überträgt die User-Sektionen auf das UserSectionData. Nebenbei auch die Information ob das
     * Passwort Default ist. Die Information wird über die Kette User/Group/SectionId ermittelt und
     * per Reflection auf das UserSectionData übertragen.
     * 
     * @param User
     *            user
     * @param UserSectionData
     *            usd
     */
    private UserSectionData createUserSectionData(User user) {

        UserSectionData userSectionData = new UserSectionData(user);

        // /////////////////////////////////////////////////////////////////
        // /// Defaultpasswort
        // /
        userSectionData.setIsDefaultPasswd(Const.STANDARTPWD_VER.equals(user.getPasswd()) ? Boolean.TRUE
                : Boolean.FALSE);

        List<Section> sections4User = getSectionsForUser(user.getId());

        /**
         * Hier werden nur jene Sektionen durchlaufen, die der Benutzer hat. Um den Sektionslevel zu
         * setzten müssen ALLE Sektionen durchlaufen werden.
         */
        if (sections4User != null) {
            for (Iterator<Section> iterator2 = sections4User.iterator(); iterator2.hasNext();) {
                Section section = iterator2.next();
                String sectionKz = section.getKz();
                if (sectionKz == null) {
                    continue;
                }

                String setterName = sectionKz.toLowerCase();
                try {
                    // BeanUtils.setProperty(userSectionData, sectionKz + ".level",
                    // section.getMenuitemid());
                    BeanUtils.setProperty(userSectionData, setterName + ".isSet", Boolean.TRUE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        initSectionInfo(userSectionData);

        return userSectionData;
    }

    /**
     * Setzt die UserSectionData.SectionInfo.level - Felder per Reflektion. In diesem Fall werden
     * ALLE Sektions-Level gesetzt, da das später für die farblich Darstellung wichtig ist.
     * 
     * @param SectionInfo
     *            si
     */
    private void initSectionInfo(UserSectionData userSectionData) {

        Set<String> keyset = SECTION_KZ_LEVEL_MAPPING.keySet();
        for (Iterator<String> iterator = keyset.iterator(); iterator.hasNext();) {
            String sectionKz = iterator.next();
            Section section = SECTION_KZ_LEVEL_MAPPING.get(sectionKz);
            try {
                BeanUtils.setProperty(userSectionData, sectionKz + ".level", section.getMenuitemid());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateUserSection(User creator, List<UserSectionData> userList) {
        for (Iterator<UserSectionData> iterator = userList.iterator(); iterator.hasNext();) {
            UserSectionData userSectionData = iterator.next();
            updateUserSection(creator, userSectionData);
        }
    }

    @Override
    public void updateUserSection(User creator, UserSectionData userSectionData) {

        if (userSectionData == null) {
            return;
        }

        String groupName = generateGroupName4User(userSectionData, HEX);

        // //////////////////////////////////////////////////////
        // /// Checken, ob eine neue Gruppe erstellt werden muss,
        // /// bzw. Gruppen-ID beim Benutzer eintragen.
        // /
        Long groupId = getGroupId4Kz(groupName);
        userSectionData.setGroupIds(String.valueOf(groupId));

        if (-1L != groupId.longValue()) {
            // GRUPPE EXISTIERT
            // UPDATE USER
            // groupIds4User.append(",").append(groupId);
        } else {
            // GRUPPE EXISTIERT NICHT
            // INSERT GROUP
            // UPDATE USER
            Group group = new Group();
            group.setKz(groupName);
            group.setName(groupName);
            group.setSectionids(generateGroupName4User(userSectionData, STR));
            insert(group);

            userSectionData.setGroupIds(String.valueOf(group.getId()));
        }

        // //////////////////////////////
        // /// Benutzerdaten speichern.
        // /
        if (userSectionData.getId() != null) {
            update(userSectionData);
        } else {
            // insert(userSectionData);
        }

        // //////////////////////////////
        // /// Historie aktualisieren
        // /
        HistoryBr hBenuzterrechte = new HistoryBr();
        hBenuzterrechte.setId_user(creator.getId());
        hBenuzterrechte.setAktion(HistoryBr.AKTION_AENDERN);
        hBenuzterrechte.setTimeStamp(System.currentTimeMillis());
        hBenuzterrechte.setXmlUserData(Util.object2XmlString(userSectionData));

        getDBHistoryBr().insert(hBenuzterrechte);
    }

    public String generateGroupName4User(UserSectionData user, int returnType) {

        String groupName = null;
        String levelOld = "";
        String levelNew = "";

        StringBuffer sectionIds = new StringBuffer();
        StringBuffer binaryString = new StringBuffer();
        Set<String> keyset = SECTION_KZ_LEVEL_MAPPING.keySet();
        for (Iterator<String> itrAllSections = keyset.iterator(); itrAllSections.hasNext();) {

            String sectionKz = itrAllSections.next();
            Section secTmp = SECTION_KZ_LEVEL_MAPPING.get(sectionKz);

            try {

                levelOld = levelNew;
                levelNew = BeanUtils.getProperty(user, sectionKz + ".level");
                String isSet = BeanUtils.getProperty(user, sectionKz + ".isSet");
                // System.out.println(String.format("%-12s ", sectionKz) + levelNew + ": " + isSet);

                if (!levelNew.equals(levelOld)) {
                    binaryString.append(":").append(levelNew).append(":");
                }

                sectionIds.append("true".equalsIgnoreCase(isSet) ? secTmp.getId() + "," : "");
                binaryString.append("true".equalsIgnoreCase(isSet) ? "1" : "0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        sectionIds.trimToSize();
        if (sectionIds.toString().endsWith(",")) {
            sectionIds.replace(sectionIds.length() - 1, sectionIds.length(), "");
        }

        switch (returnType) {
            case HEX: {
                groupName = generateGroupKey(binaryString.toString());
                break;
            }
            case BIN: {
                groupName = binaryString.toString();
                break;
            }
            case STR: {
                groupName = (sectionIds.length() == 0) ? null : sectionIds.toString();
                break;
            }
            default: {
                groupName = generateGroupKey(binaryString.toString());
                break;
            }
        }

        log.debug("id  : " + sectionIds);
        log.debug("bin : " + binaryString.toString());
        log.debug("hex : " + groupName);

        return groupName;
    }

    public String generateGroupName4User(UserSectionData user) {
        return generateGroupName4User(user, HEX);
    }

    /**
     * Schnippelt den Levelbezeichner raus und erzeugt einen Hex-Gruppenbezeichner. Beispiel:
     * FFF.FFF.1
     * 
     * @param String
     *            binaryKey
     * @return String
     */
    private String generateGroupKey(String binaryKey) {

        StringBuffer hex = new StringBuffer();
        String[] split = binaryKey.split(":\\d:");
        for (int i = 0; i < split.length; i++) {
            String tmpBin = split[i];
            if (tmpBin != null && tmpBin.length() > 0) {
                String debug = binaryString2HexString(tmpBin);
                hex.append(debug.toUpperCase());
                hex.append(i < split.length - 1 ? "." : "");
            }
        }
        return hex.toString();
    }

    private static String binaryString2HexString(String binaryString) {
        return Integer.toHexString(Integer.parseInt(binaryString, 2));
    }

    private static String hexString2BinaryString(String hexString) {
        return new BigInteger(hexString, 16).toString(2);
    }

    /**
     * Baut die Where / And Klauseln für die Benutzerabfragen als Prepared-Statement Variante
     * zusammen. Nur Superuser dürfen auch Superuser sehen!
     * 
     * @param boolean isSuperAdmin
     * @param FilterItemList
     *            listTxtFields
     * @return String
     */
    private PreparedStatementO getFilterSQLPS(boolean isSuperAdmin, FilterItemList listTxtFields) {

        List<String> valuesL = new ArrayList<String>();
        PreparedStatementO ps = new PreparedStatementO();

        StringBuilder filter = new StringBuilder();
        // ///////////////////////////////////////////////
        // /// Is Admin? ...
        // /
        if (!isSuperAdmin) {

            // //////////////////////////////////////////////////////////
            // /// Das hier funktioniert nur so lange die Benutzer EINE Gruppe haben!
            // /
            // filter.append(", groups g ");
            // filter.append(" where u.groupids = g.id ");
            // filter.append("and g.kz not like 'su' "); // hardcodiert!

            // //////////////////////////////////////////////////////////
            // /// ARGH. Schmerz!
            // /
            filter.append("where u.[GroupIds] not like '1' ");
            filter.append("and u.[GroupIds] not like '1,%' ");
            filter.append("and u.[GroupIds] not like '%,1,%' ");
            filter.append("and u.[GroupIds] not like '%,1' ");
        }
        // ///////////////////////////////////////////////
        // /// Filter ...
        // /
        if (listTxtFields != null && listTxtFields.getSize() > 0) {
            List<FilterItem> itm = listTxtFields.getFilterItems();
            for (int i = 0; i < itm.size(); i++) {
                FilterItem fi = itm.get(i);
                filter.append((i == 0 && filter.indexOf("where") == -1) ? "where" + " u." : " and" + " u.");
                filter.append(fi.getFilterIdent());
                // filter.append(" like '%");
                filter.append(" like ");
                filter.append("?");
                // filter.append(fi.getFilterText());
                valuesL.add("%" + fi.getFilterText() + "%");
                // filter.append("%'");
            }
        }

        ps.setSql(filter.toString());
        ps.setValues(valuesL.toArray(new String[valuesL.size()]));

        return ps;
    }

    @Override
    public DataRecord getColumNamesAsDataRecord(User user, String tableName) {

        DataRecord columNames = new DataRecord();
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(createSelectStart4ColNames(user, tableName));
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int colNr = rsmd.getColumnCount();
            for (int i = 1; i <= colNr; ++i) {
                columNames.addColItem(rsmd.getColumnName(i));
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return columNames;
    }

    /**
     * Erstellt das Sql-Statement:
     * 
     * <pre>
     *   select [colname] from [tablename] u.
     * </pre>
     * 
     * Restriktionen für Leute die keine Superadmins sind, wären hier möglich. Wird momentan nicht
     * verwendet!
     * 
     * @param User
     *            user
     * @param String
     *            tableName
     * @return String
     */
    private String createSelectStart4ColNames(User user, String tableName) {

        List<String> tblName = getColumNames(tableName);
        if (!user.getIsSuperAdmin()) {
            // tblName.remove(COL_FILTER);
            // tblName.remove(COL_GROUPID);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        for (int i = 0; i < tblName.size(); i++) {
            String colNameTmp = tblName.get(i);
            sb.append(colNameTmp);
            sb.append((i >= 0) && (i < tblName.size() - 1) ? ", " : "");
        }
        sb.append(" from ");
        sb.append(tableName);
        sb.append(" u ");
        return sb.toString();
    }

    @Override
    public List<Group> getAllGroups(User u) {

        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Group> groupList = new ArrayList<Group>();

        try {
            connection = daoFactory.getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("select g.* from groups g ");
            if (!u.getIsSuperAdmin()) {
                sb.append("where g.KZ not like 'SU' ");
            }
            sb.append("order by g.name asc;");
            preparedStatement = connection.prepareStatement(sb.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                groupList.add(mapGroup(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return groupList;
    }

    public List<User> executeSQLStatement4PSUser(PreparedStatementO ps, DAOFactoryJDBC daoFactory) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> lUser = new ArrayList<User>();

        log.debug(ps.getSql());

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, ps.getSql(), false, (Object[]) ps.getValues());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                lUser.add(mapUser(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return lUser;
    }

}
