// $Log: UserTest.java,v $
// Revision 1.19  2014/11/23 21:53:44  tw
// https://team.decodetron.de:10443/index.php?c=task&a=view_task&id=3939
//
// Revision 1.18  2014/10/01 11:24:31  tw
// Absplittung, Scanindex vom restlichen Index. Testanbindung.
//
// Revision 1.17  2014/07/18 22:16:29  tw
// Bugfix: Schnittstelle Benutzerverwaltung, Berechtigung Superadmin / Admin.
//
// Revision 1.16  2014/05/06 00:43:40  tw
// Backup: Benutzer anlegen.
//
// Revision 1.15  2014/05/04 20:22:48  tw
// Benutzer anlegen: Neue Schnittstelle: getDistinctByColName
//
// Revision 1.14  2014/04/15 09:07:04  tw
// Bugfix login == null
//
// Revision 1.13  2014/03/27 12:06:55  tw
// Vorbereitung, CR-Defektenlisten f. Lieferanten: Markup-freie Tabellengestaltung.
//
// Revision 1.12  2014/03/27 01:20:43  tw
// Vorbereitung, CR-Defektenlisten f. Lieferanten: Markup-freie Tabellengestaltung.
//
// Revision 1.11  2014/02/28 15:39:49  tw
// Anpassung an neue DB-Struktur: Gebietssleiter.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.10  2014/02/13 13:05:47  tw
// Benutzerverwaltung, dynamischer Suchfilteraufbau, Schnittstellenanpassung.
//
// Revision 1.9  2014/02/05 13:44:04  tw
// Anpassung an neue DB-Struktur
//
// Revision 1.8  2014/02/04 13:22:08  tw
// Schnittstellenanpassung Benutzerverwaltung.
//
// Revision 1.7  2014/02/04 02:15:09  tw
// Testanpassung.
//
// Revision 1.6  2014/02/03 16:41:09  tw
// Implementierung Benutzerverwaltung
//
// Revision 1.5  2014/01/24 16:40:12  tw
// Schnittstellen aufgeraeumt. Neue limitierte Benutzerschnittstelle.
//
// Revision 1.4  2014/01/24 13:39:07  tw
// Schnittstellen aufgeraeumt. Neue limitierte Benutzerschnittstelle.
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
// Revision 1.8  2013/11/17 13:42:00  tw
// Neue Schnittstelle f. User mit mehrfachfiltern implementiert.
//
// Revision 1.7  2013/11/14 23:11:31  tw
// .
//
// Revision 1.6  2013/11/14 23:05:38  tw
// Passwort-andern Schnittstelle
//
// Revision 1.5  2013/11/14 14:08:47  tw
// Schnittstellen Datenfilterung implementiert.
//
// Revision 1.4  2013/11/13 01:50:24  tw
// Tests an aktuelle Tabellensituation angepasst.
//
// Revision 1.3  2013/11/13 00:29:59  tw
// Servicefunktionen fuer Berechtigungskonzept implementiert.
//
// Revision 1.2  2013/11/08 11:44:00  tw
// Compiler nochmals utf-8 verklickert.
//
// Revision 1.1  2013/11/05 19:31:31  tw
// db-schnittstellen aufgeraeumt.
//
// Revision 1.8  2013/11/05 03:38:33  tw
// .
//
// Revision 1.7  2013/10/25 00:20:38  tw
// Anpassung an Rollout.
//
// Revision 1.6  2013/09/17 02:05:43  tw
// Login-Logout-Mechanismus implementiert.
//
// Revision 1.5  2013/09/16 14:35:40  tw
// Backup, Einbau: Login-Mechanismus
//
// Revision 1.4  2013/08/12 14:16:55  tw
// Tests für Nebenläufige Performance-Tests Erstellt.
//
// Revision 1.3  2013/08/12 10:49:50  tw
// Neues Attribut: anlagedatum erstellt.
//
// Revision 1.2  2013/08/07 13:27:26  tw
// Standart-DB Schnittstellen inclusive Tests für User-Bearbeitung implementiert.
//
// Revision 1.1  2013/08/07 01:46:51  tw
// AEP LoginModul als simpelst-keine-Zusatzbibliotheken-Variante. Erster Wurf.
//
//

package de.decodetron;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;
import de.decodetron.bo.FilterItem;
import de.decodetron.bo.FilterItemList;
import de.decodetron.bo.Group;
import de.decodetron.bo.LimitInfo;
import de.decodetron.bo.SortInfo;
import de.decodetron.bo.User;
import de.decodetron.dao.user.UserDAOI;
import de.decodetron.fac.DAOFactoryJDBC;
import de.decodetron.util.Const;
import de.decodetron.util.CryptoTools;
import de.decodetron.util.SystemUtil;

/**
 * Anlegen, Ändern, Anmelden, Löschen Tests.
 * 
 * @author Thomas Winter
 * @since 07.08.2013
 */
public class UserTest extends TestCase {

    final String TABLENAME = "user";
    final String SCHEMANAME = "junitUser";
    final String TESTPWD_CLEAR = "dct1415";
    final String TESTLOGIN = "hbambel@decodetron.de";

    // final String SCHEMANAME = "user.db";

    /**
     * Prüfung generell: Lassen sich .properties lesen?
     */
    public void testLoadProperties() {
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String value = p.getProperty("file.db.clubbestand");
        assertTrue(!value.isEmpty());
    }

    public void testCreateTestDB() throws Exception {

        DAOFactoryJDBC javabase = DAOFactoryJDBC.getInstance(SCHEMANAME);
        Connection con = javabase.getConnection();
        Statement stat = con.createStatement();
        stat.executeUpdate("drop table if exists " + TABLENAME + ";");
        stat.executeUpdate("create table " + TABLENAME + "(" + Const.TBL_USER_ID + " integer PRIMARY KEY, "
                + Const.TBL_USER_VORNAME + ", " + Const.TBL_USER_NACHNAME + ", " + Const.TBL_USER_LOGIN + ", "
                + Const.TBL_USER_PASSWD + ", " + Const.TBL_USER_ANLAGEDATUM + ", " + Const.TBL_USER_FILTER + ", "
                + Const.TBL_USER_GROUPID + ", " + Const.TBL_USER_VLEITUNG + ", " + Const.TBL_USER_GEBIET + ");");
        stat.close();

        stat = con.createStatement();
        stat.executeUpdate("drop table if exists " + "groups" + ";");
        stat.executeUpdate("create table " + "groups" + "(" + Const.TBL_USER_ID + " integer PRIMARY KEY, " + "Name"
                + ", " + "KZ" + ", " + "SectionIds" + ");");
        stat.close();

        con.close();
    }

    /**
     * 10 Testuser anlegen. Ohne DAO-Factory - Funkionalität! Dieses Verfahren hier ist um den
     * Faktor 50 schneller als das in den DAO-Factories! Einfügen 1000 Datensätze: 502 [ms] s. 27205
     * [ms] !!!
     * 
     * @throws Exception
     */
    public void testCreateUserRaw() throws Exception {
        DAOFactoryJDBC javabase = DAOFactoryJDBC.getInstance(SCHEMANAME);
        Connection con = javabase.getConnection();
        PreparedStatement prep = con.prepareStatement("INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        for (int i = 0; i < 10; i++) {
            prep.setNull(1, Types.NUMERIC);
            prep.setString(2, Const.TBL_USER_VORNAME + i);
            prep.setString(3, Const.TBL_USER_NACHNAME + "i");
            prep.setString(4, Const.TBL_USER_LOGIN + i);
            prep.setString(5, Const.TBL_USER_PASSWD + i);
            prep.setDate(6, new java.sql.Date(System.currentTimeMillis()));
            prep.addBatch();
        }

        con.setAutoCommit(false);
        prep.executeBatch();
        con.setAutoCommit(true);

        prep.close();
        con.close();
    }

    public void testGetAllUser() {
        DAOFactoryJDBC javabase = DAOFactoryJDBC.getInstance(SCHEMANAME);
        UserDAOI userDAO = javabase.getUserDAO();
        List<User> allUser = userDAO.getAllUser();
        assertTrue(allUser.size() >= 10);
    }

    public void testCreateUser1() {

        DAOFactoryJDBC javabase = DAOFactoryJDBC.getInstance(SCHEMANAME);
        UserDAOI userDAO = javabase.getUserDAO();

        // Create user.
        User user = new User();
        user.setId(null); // ID NULL !
        user.setVorname("DAOVorname");
        user.setNachname("DAONach");
        user.setLogin("DAOLogin");
        user.setAnlagedatum(new Date(System.currentTimeMillis()).toString());
        user.setPasswd(new CryptoTools().encryptOneWaySHA1("hexhex"));
        // user.setAnlagedatum(new java.util.Date());
        userDAO.insert(user);

        assertNotNull(user.getId());
    }

    public void testChangePasswd() {

        DAOFactoryJDBC javabase = DAOFactoryJDBC.getInstance(SCHEMANAME);
        UserDAOI userDAO = javabase.getUserDAO();
        String pwdOld = new CryptoTools().encryptOneWaySHA1("hexhex");
        String pwdNew = new CryptoTools().encryptOneWaySHA1("mexmex");

        // Create user.
        User user = new User();
        user.setId(null); // ID NULL !
        user.setVorname("PWDCHANGE");
        user.setAnlagedatum(new Date(System.currentTimeMillis()).toString());
        user.setPasswd(pwdOld);
        userDAO.insert(user);

        // Change Password
        userDAO.changePassword(user.getId(), "mexmex");

        // Check Change
        User newPwdUser = userDAO.findById(user.getId());
        assertNotSame(newPwdUser.getPasswd(), pwdOld);
    }

    public void testCreateMD5() {
        String md5 = new CryptoTools().encryptOneWaySHA1("12345678");
        assertEquals("7C222FB2927D828AF22F592134E8932480637C0D", md5);

        md5 = new CryptoTools().encryptOneWaySHA1("dct1415");
        assertEquals("CE7A301E85D8938BCBDED73BAB523D8C36900562", md5);
    }

    public void testFindUserById() {

        DAOFactoryJDBC javabase = DAOFactoryJDBC.getInstance(SCHEMANAME);
        UserDAOI userDAO = javabase.getUserDAO();

        // Create user.
        User user = new User();
        user.setId(null); // ID NULL !
        user.setVorname("DAOVornameById");
        user.setNachname("DAONachById");
        user.setLogin("DAOLoginById");
        user.setAnlagedatum(new Date(System.currentTimeMillis()).toString());
        user.setPasswd(new CryptoTools().encryptOneWaySHA1("hexhexById"));
        userDAO.insert(user);

        User u2Find = userDAO.findById(user.getId());
        assertEquals(user.getId(), u2Find.getId());

        // ///////////////////////////////////////////
        // /// Attribübertrag bei Neuankömmlingen checken.
        // /
        assertNotNull(u2Find.getVorname());
        assertNotNull(u2Find.getNachname());
        assertNotNull(u2Find.getLogin());
        assertNotNull(u2Find.getAnlagedatum());
        assertNotNull(u2Find.getPasswd());
    }

    public void testFindUserByLoginAndPwd() {

        DAOFactoryJDBC javabase = DAOFactoryJDBC.getInstance(SCHEMANAME);
        UserDAOI userDAO = javabase.getUserDAO();

        // Create user.
        User user = new User();
        user.setId(null); // ID NULL !
        user.setVorname("DAOVornameByLoginAndPwd");
        user.setNachname("DAONachByLoginAndPwd");
        user.setLogin("DAOLoginByLoginAndPwd");
        user.setAnlagedatum(new Date(System.currentTimeMillis()).toString());
        String pwd = new CryptoTools().encryptOneWaySHA1("hexmexByLoginAndPwd");
        user.setPasswd(pwd);
        userDAO.insert(user);

        User u2Find = userDAO.findByLoginPwd(user.getLogin(), pwd);
        assertEquals(user.getId(), u2Find.getId());
    }

    public void testUpdateUser() {

        DAOFactoryJDBC javabase = DAOFactoryJDBC.getInstance(SCHEMANAME);
        UserDAOI userDAO = javabase.getUserDAO();

        // Create user.
        User user = new User();
        user.setId(null); // ID NULL !
        user.setVorname("DAOVornameUpdate");
        user.setNachname("DAONachUpdate");
        user.setLogin("DAOLoginUpdate");
        SimpleDateFormat sdf = new SimpleDateFormat();
        Date d = new Date(System.currentTimeMillis());
        user.setAnlagedatum(d.toString());
        user.setPasswd(new CryptoTools().encryptOneWaySHA1("hexhexUpdate"));
        userDAO.insert(user);

        User u2Update = userDAO.findById(user.getId());
        // Check, alter Name
        assertEquals(u2Update.getVorname(), "DAOVornameUpdate");

        // Name ändern
        u2Update.setVorname("DAOVornameUpdateNeu");
        userDAO.update(u2Update);

        // Check, neuer Name
        u2Update = userDAO.findById(user.getId());
        assertEquals(u2Update.getVorname(), "DAOVornameUpdateNeu");
    }

    /**
     * Erstmalig Test einer View.
     */
    public void testView() {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileUserPerm = u.getHomeDirectory(userHome, p.getProperty("file.db.user"));
        DAOFactoryJDBC dbBaseUserPermissions = DAOFactoryJDBC.getInstance(dbFileUserPerm);
        UserDAOI userDao = dbBaseUserPermissions.getUserDAO();
        List<User> lViewUser = userDao.getAllUser990erExtraWurst();
        assertEquals(14, lViewUser.size());
    }

    public void testDeleteUser() {

        DAOFactoryJDBC javabase = DAOFactoryJDBC.getInstance(SCHEMANAME);
        UserDAOI userDAO = javabase.getUserDAO();

        // Create user.
        User user = new User();
        user.setId(null); // ID NULL !
        user.setVorname("DAOVornameDelete");
        user.setNachname("DAONachDelete");
        user.setLogin("DAOLoginDelete");
        user.setAnlagedatum(new Date(System.currentTimeMillis()).toString());
        user.setPasswd(new CryptoTools().encryptOneWaySHA1("hexhexDelete"));
        userDAO.insert(user);

        // Get user By Id.
        User u2Delete = userDAO.findById(user.getId());
        assertNotNull(u2Delete);

        userDAO.delete(u2Delete);
        u2Delete = userDAO.findById(user.getId());
        assertNull(u2Delete);
    }

    public void testCheckLoginExists() {

        DAOFactoryJDBC javabase = DAOFactoryJDBC.getInstance(SCHEMANAME);
        UserDAOI userDAO = javabase.getUserDAO();

        // Create user.
        User user = new User();
        user.setId(null); // ID NULL !
        user.setVorname("DAOVornameCheckLogin");
        user.setNachname("DAONachCheckLogin");
        user.setLogin("DAOLoginCheckLogin");
        user.setAnlagedatum(new Date(System.currentTimeMillis()).toString());
        user.setPasswd(new CryptoTools().encryptOneWaySHA1("hexhexDelete"));
        userDAO.insert(user);

        assertTrue(userDAO.existLogin("DAOLoginCheckLogin"));
        assertFalse(userDAO.existLogin("DAOLoginCheckLogin_"));
    }

    public void testFindByLogin() {
        DAOFactoryJDBC javabase = DAOFactoryJDBC.getInstance(SCHEMANAME);
        UserDAOI userDAO = javabase.getUserDAO();
        User u = userDAO.findByLogin("DAOLoginCheckLogin");
        assertEquals("DAOLoginCheckLogin", u.getLogin());
    }

    public void testFindByLogin2() {
        DAOFactoryJDBC javabase = DAOFactoryJDBC.getInstance(SCHEMANAME);
        UserDAOI userDAO = javabase.getUserDAO();
        String pwdE = new CryptoTools().encryptOneWaySHA1("hexhexDelete");
        User u = userDAO.findByLoginPwd(null, pwdE);
        assertNull(u);
    }

    public void testGetPassword1() {
        // Passwörter per Hand erzeugen
        System.out.println("PWD: " + new CryptoTools().encryptOneWaySHA1("decomaster"));
    }

    public void testGetPassword2() {
        DAOFactoryJDBC javabase = DAOFactoryJDBC.getInstance(SCHEMANAME);
        UserDAOI userDAO = javabase.getUserDAO();
        String pwd = userDAO.getPasswdForLogin("DAOLoginById");
        assertEquals(pwd, "14336840A9D13ED43775D7E798F89C03FA7A5500");
    }

    public void testDeleteLocalTestDB() throws Exception {
        DAOFactoryJDBC javabase = DAOFactoryJDBC.getInstance(SCHEMANAME);
        Connection c = javabase.getConnection();
        DatabaseMetaData dbd = c.getMetaData();
        String dbUrl = dbd.getURL();
        c.close();
        int idx = dbUrl.lastIndexOf(":") - 1;
        String dbName = dbUrl.substring(idx);
        File dbFile = new File(dbName);
        dbFile.delete();
    }

    /**
     * Test mit der echten UserDB. Holt die Daten portionsweise ab.
     * 
     * public void testGetAllUserLimited() {
     * 
     * SystemUtil u = new SystemUtil(); String userHome = System.getProperty("user.dir"); Properties
     * p = new SystemUtil().loadSystemProperties("db.properties"); String dbFileUserPerm =
     * u.getHomeDirectory(userHome, p.getProperty("file.db.user")); DAOFactoryJDBC
     * dbBaseUserPermissions = DAOFactoryJDBC.getInstance(dbFileUserPerm); UserDAOI userDao =
     * dbBaseUserPermissions.getUserDAO(); List<User> list = userDao.getAllUserLimited("login",
     * "asc", 5, 0, new FilterItemList()); assertEquals(list.size(), 5); }
     */

    /**
     * Test mit der echten UserDB. Holt die Daten portionsweise, mit Filterinformationen ab.
     * 
     * public void testGetAllUserLimitedFiltered() {
     * 
     * SystemUtil u = new SystemUtil(); String userHome = System.getProperty("user.dir"); Properties
     * p = new SystemUtil().loadSystemProperties("db.properties"); String dbFileUserPerm =
     * u.getHomeDirectory(userHome, p.getProperty("file.db.user")); DAOFactoryJDBC
     * dbBaseUserPermissions = DAOFactoryJDBC.getInstance(dbFileUserPerm); UserDAOI userDao =
     * dbBaseUserPermissions.getUserDAO();
     * 
     * FilterItemList filterList = new FilterItemList(); filterList.add(new FilterItem("GroupIds",
     * "1")); filterList.add(new FilterItem("login", "fler")); filterList.add(new
     * FilterItem("Filter", "*"));
     * 
     * List<User> list = userDao.getAllUserLimited("login", "asc", 5, 0, filterList);
     * assertEquals(list.size(), 1); }
     */

    /**
     * Test mit der echten UserDB. Holt die Daten portionsweise ab. Gibt nicht die Superadmins
     * zurück.
     */
    public void testGetAllUserLimited4Admin() {

        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileUserPerm = u.getHomeDirectory(userHome, p.getProperty("file.db.user"));
        DAOFactoryJDBC dbBaseUserPermissions = DAOFactoryJDBC.getInstance(dbFileUserPerm);
        UserDAOI userDao = dbBaseUserPermissions.getUserDAO();

        // Kein Superadmin OHNE Einschränkung
        SortInfo sortInfo = new SortInfo("asc", "groupids");
        LimitInfo limitInfo = new LimitInfo(1000, 0L);
        User testUser = new User();
        testUser.setIsSuperAdmin(false);
        List<User> list1 = userDao.getUser4Verwaltung(testUser, sortInfo, limitInfo, new FilterItemList());

        for (Iterator<User> iterator = list1.iterator(); iterator.hasNext();) {
            User user = iterator.next();

            // Es darf kein Superadmin in der Liste sein. Admins sind erlaubt!
            if (user.getIsSuperAdmin()) {
                assertTrue(false);
            }
        }

        // Alle andern Fälle auch nochmal ...
        FilterItemList filterList = new FilterItemList();
        filterList.add(new FilterItem("GroupIds", "2"));
        filterList.add(new FilterItem("login", "00"));
        filterList.add(new FilterItem("Filter", "000"));

        // Superadmin OHNE Einschränkung
        testUser.setIsSuperAdmin(true);
        list1 = userDao.getUser4Verwaltung(testUser, sortInfo, limitInfo, new FilterItemList());// 606
        assertTrue(list1.size() > 100);

        // Kein Superadmin MIT Einschränkung
        testUser.setIsSuperAdmin(false);
        list1 = userDao.getUser4Verwaltung(testUser, sortInfo, limitInfo, filterList);// 3
        assertTrue(list1.size() < 100);

        // Superadmin MIT Einschränkung
        testUser.setIsSuperAdmin(true);
        list1 = userDao.getUser4Verwaltung(testUser, sortInfo, limitInfo, filterList);
        assertTrue(list1.size() < 100);
    }

    public void testCountAllUser4Admin() {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileUserPerm = u.getHomeDirectory(userHome, p.getProperty("file.db.user"));
        DAOFactoryJDBC dbBaseUserPermissions = DAOFactoryJDBC.getInstance(dbFileUserPerm);
        UserDAOI userDao = dbBaseUserPermissions.getUserDAO();

        User testUser = new User();

        testUser.setIsSuperAdmin(false);
        Long cnt1 = userDao.countUser4Verwaltung(testUser, new FilterItemList());
        testUser.setIsSuperAdmin(true);
        Long cnt2 = userDao.countUser4Verwaltung(testUser, new FilterItemList());
        assertTrue(cnt1 != cnt2);

        FilterItemList filterList = new FilterItemList();
        filterList.add(new FilterItem("Filter", "*"));
        testUser.setIsSuperAdmin(false);
        cnt1 = userDao.countUser4Verwaltung(testUser, filterList);
        testUser.setIsSuperAdmin(true);
        cnt2 = userDao.countUser4Verwaltung(testUser, filterList);
        assertTrue(cnt1 != cnt2);
    }

    /**
     * Angezeigte Spalten varieren an der Oberfläche, je nach dem ob Superadmin oder nicht. In dem
     * Fall um 2 Spalten.
     * 
     * 19.07.2014, erstmal abgeklemmt. Es gibt, wie immmer, eh keine Spec dazu.
     * 
     * public void testGetColNames() { SystemUtil u = new SystemUtil(); String userHome =
     * System.getProperty("user.dir"); Properties p = new
     * SystemUtil().loadSystemProperties("db.properties"); String dbFileUserPerm =
     * u.getHomeDirectory(userHome, p.getProperty("file.db.user")); DAOFactoryJDBC
     * dbBaseUserPermissions = DAOFactoryJDBC.getInstance(dbFileUserPerm); UserDAOI userDao =
     * dbBaseUserPermissions.getUserDAO();
     * 
     * User testUser = new User(); testUser.setIsSuperAdmin(true); DataRecord drSuperAdmin =
     * userDao.getColumNamesAsDataRecord(testUser, TABLENAME); testUser.setIsSuperAdmin(false);
     * DataRecord drNoSuperAdmin = userDao.getColumNamesAsDataRecord(testUser, TABLENAME);
     * 
     * assertTrue(drSuperAdmin.getSize() - drNoSuperAdmin.getSize() == 2); }
     */

    public void testColGroups() {

        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileUserPerm = u.getHomeDirectory(userHome, p.getProperty("file.db.user"));
        DAOFactoryJDBC dbBaseUserPermissions = DAOFactoryJDBC.getInstance(dbFileUserPerm);
        UserDAOI userDao = dbBaseUserPermissions.getUserDAO();
        List<String> groups = userDao.getDistinctByColName("user", "GroupIds");

        assertTrue(groups.size() > 0);
    }

    public void testGetGroups() {

        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileUserPerm = u.getHomeDirectory(userHome, p.getProperty("file.db.user"));
        DAOFactoryJDBC dbBaseUserPermissions = DAOFactoryJDBC.getInstance(dbFileUserPerm);
        UserDAOI userDao = dbBaseUserPermissions.getUserDAO();

        User testUser = new User();
        testUser.setIsSuperAdmin(true);
        List<Group> groupSuperAdmin = userDao.getAllGroups(testUser);
        testUser.setIsSuperAdmin(false);
        List<Group> groupNOSuperAdmin = userDao.getAllGroups(testUser);

        assertTrue(groupSuperAdmin.size() - groupNOSuperAdmin.size() == 1);
    }
}
