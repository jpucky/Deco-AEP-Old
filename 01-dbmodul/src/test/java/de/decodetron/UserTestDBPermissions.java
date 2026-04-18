// $Log: UserTestDBPermissions.java,v $
// Revision 1.16  2014/10/09 09:26:52  tw
// Test-Bugfix.
//
// Revision 1.15  2014/08/25 01:42:58  tw
// .
//
// Revision 1.14  2014/08/15 14:39:36  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.13  2014/03/27 01:20:43  tw
// Vorbereitung, CR-Defektenlisten f. Lieferanten: Markup-freie Tabellengestaltung.
//
// Revision 1.12  2014/02/28 15:39:50  tw
// Anpassung an neue DB-Struktur: Gebietssleiter.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.11  2014/02/26 15:00:16  tw
// Anpassung an neue DB-Struktur: Gebietssleiter.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.10  2014/02/14 00:26:48  tw
// Testanpassung.
//
// Revision 1.9  2014/02/14 00:09:59  tw
// Schnittstellenanpassung: User koennen mehrere Gruppen besitzen.
//
// Revision 1.8  2014/02/13 23:07:13  tw
// Schnittstellenanpassung: User koennen mehrere Gruppen besitzen.
//
// Revision 1.7  2014/02/05 13:44:04  tw
// Anpassung an neue DB-Struktur
//
// Revision 1.6  2014/01/24 13:39:07  tw
// Schnittstellen aufgeraeumt. Neue limitierte Benutzerschnittstelle.
//
// Revision 1.5  2013/12/18 14:48:11  tw
// AEP-Token-Anmeldung implementiert.
//
// Revision 1.4  2013/12/18 13:09:42  tw
// Altes Feld rausgeworfen.
//
// Revision 1.3  2013/12/05 12:16:04  tw
// Tests f. Tokenanmeldung.
//
// Revision 1.2  2013/12/05 11:50:36  tw
// Tests f. Tokenanmeldung.
//
// Revision 1.1  2013/11/21 17:40:02  tw
// Neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.8  2013/11/18 11:16:33  tw
// *** empty log message ***
//
// Revision 1.7  2013/11/18 10:11:00  tw
// Bugfix: Multiuserfilter.
//
// Revision 1.6  2013/11/17 13:42:00  tw
// Neue Schnittstelle f. User mit mehrfachfiltern implementiert.
//
// Revision 1.5  2013/11/14 01:34:42  tw
// aaaaaaaaaaaaaaargh umlaute.
//
// Revision 1.4  2013/11/13 23:13:20  tw
// Vorbereitung: Schnittstellen fuer Datenfilter.
//
// Revision 1.3  2013/11/13 16:13:38  tw
// Sectionsplitting geaendert.
//
// Revision 1.2  2013/11/13 01:50:24  tw
// Tests an aktuelle Tabellensituation angepasst.
//
// Revision 1.1  2013/11/13 00:29:59  tw
// Servicefunktionen fuer Berechtigungskonzept implementiert.
//
//

package de.decodetron;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;
import de.decodetron.bo.Group;
import de.decodetron.bo.Section;
import de.decodetron.bo.User;
import de.decodetron.dao.user.UserDAOI;
import de.decodetron.fac.DAOFactoryJDBC;
import de.decodetron.util.CryptoTools;
import de.decodetron.util.DAOUtil;
import de.decodetron.util.SystemUtil;

/**
 * Ein paar GruppenTests sind auch dabei.
 * 
 * @author Thomas Winter
 * @since 12.11.2013
 */
public class UserTestDBPermissions extends TestCase {

    private String TBL_USER = "user";
    private String TBL_GROUPS = "groups";
    private String TBL_SECTIONS = "sections";
    private DAOFactoryJDBC dbBaseUserPermissions = null;

    @Override
    protected void setUp() throws Exception {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileUserPerm = u.getHomeDirectory(userHome, p.getProperty("file.db.user"));
        dbBaseUserPermissions = DAOFactoryJDBC.getInstance(dbFileUserPerm);
    }

    public void testGetUserById() {
        UserDAOI userDao = dbBaseUserPermissions.getUserDAO();
        User user = userDao.findByLogin("arnold.kastner");
        assertTrue("4".equals(user.getGroupIds()));
        assertTrue("*".equals(user.getAllFilter().get(0)));
    }

    public void testInsertUpdateDeleteGroup() {

        UserDAOI userDao = dbBaseUserPermissions.getUserDAO();

        // INSERT
        Group group = new Group();
        group.setName("TESTNAME");
        group.setKz("TESTKZ");
        group.setSectionids("1,2,3");
        userDao.insert(group);
        assertNotNull(group.getId());

        // UPDATE

        // DELETE
        userDao.delete(group);
        assertNull(group.getId());
    }

    public void testGroupExists() {
        UserDAOI userDao = dbBaseUserPermissions.getUserDAO();
        Long id = userDao.getGroupId4Kz("su");
        assertNotNull(id);
        id = userDao.getGroupId4Kz("SU");
        assertNotNull(id);
    }

    public void testGroupNotExists() {
        UserDAOI userDao = dbBaseUserPermissions.getUserDAO();
        Long id = userDao.getGroupId4Kz("BLAH");
        assertEquals(-1L, id.longValue());
    }

    public void testGetGroupForUserId() {
        UserDAOI userDao = dbBaseUserPermissions.getUserDAO();
        List<Group> groups = userDao.getGroups4User(5L);
        assertTrue("*".equals(groups.get(0).getSectionids()));
    }

    public void testGetGroupSSSForUserId1() {
        UserDAOI userDao = dbBaseUserPermissions.getUserDAO();
        List<Group> groups = userDao.getGroups4User(1030L);
        assertTrue(groups.get(0).getSectionids().contains("1,2,3,4,"));
    }

    public void testGetGroupSSSForUserId2() {
        UserDAOI userDao = dbBaseUserPermissions.getUserDAO();
        List<Group> groups = userDao.getGroups4User(5L);
        assertTrue(groups.get(0).getSectionids().contains("*"));
    }

    /**
     * Check, ob getGroups4User() auf die Nase fällt, wenn der Benutzer noch nicht existiert.
     */
    public void testGetGroupForUserThatNotExist() {
        UserDAOI userDao = dbBaseUserPermissions.getUserDAO();
        List<Group> groups = userDao.getGroups4User(-1L);
        assertTrue(groups.size() == 0);
    }

    public void testGetSectionsForGroup() {
        UserDAOI userDao = dbBaseUserPermissions.getUserDAO();
        Section sec1 = userDao.getSectionForId(1L);
        Section sec2 = userDao.getSectionForId(2L);
        assertTrue("Defektenliste".equals(sec1.getSection()));
        assertTrue("BTM".equals(sec2.getSection()));
    }

    public void testGetAllSections() {
        UserDAOI userDao = dbBaseUserPermissions.getUserDAO();
        List<Section> list = userDao.getAllSections();
        assertTrue(list.size() >= 12);
    }

    public void testGetFilterItems4GebietId() {
        UserDAOI userDao = dbBaseUserPermissions.getUserDAO();
        List<String> filter = userDao.getFilter4GebietId("2");
        assertTrue(filter.size() == 5);
    }

    public void testSectionSplit() {
        String split1 = "*";
        String regExSplit1 = "\\s"; // [*]
        String[] split1a = split1.split(regExSplit1);
        assertTrue(split1a.length == 1);

        String split2 = "1 2";
        String regExSplit2 = "\\s";
        String[] split2a = split2.split(regExSplit2);
        assertTrue(split2a.length == 2);
    }

    /**
     * Simulation, wie es am Client ablaufen wird. Am Ende muss das in eine Funktion gepackt werden,
     * die für einen Benutzer alle Sections in einer Liste zurückgibt. Also:<br>
     * List<Sections> getSectionsForUser(userId);
     */
    public void testGetSectionsForUser11950() {

        UserDAOI userDao = dbBaseUserPermissions.getUserDAO();
        User u = userDao.findByLoginPwd("00574", "AB8D2CB0550CCA77D5C9A0831EC5825386E7AB17");
        List<Group> groupL = userDao.getGroups4User(u.getId());
        for (Iterator<Group> iterator = groupL.iterator(); iterator.hasNext();) {
            Group group = iterator.next();

            String[] sectionid = group.getSectionids().split("\\,");

            Long id1 = Long.valueOf(sectionid[0].trim());
            Long id2 = Long.valueOf(sectionid[1].trim());

            Section sec1 = userDao.getSectionForId(id1);
            Section sec2 = userDao.getSectionForId(id2);

            assertTrue("clubbestand".equals(sec1.getKz()));
            assertTrue("clubabver".equals(sec2.getKz()));
        }
    }

    public void testGetSectionsForUser11950CombiFunc() {
        UserDAOI userDao = dbBaseUserPermissions.getUserDAO();
        User u = userDao.findByLoginPwd("00574", "AB8D2CB0550CCA77D5C9A0831EC5825386E7AB17");
        List<Section> s = userDao.getSectionsForUser(u.getId());

        assertTrue(s.size() == 2);
        Section sec1 = s.get(0);
        Section sec2 = s.get(1);

        assertTrue("clubabver".equals(sec1.getKz()));
        assertTrue("clubbestand".equals(sec2.getKz()));
    }

    public void testGetSectionsForUserMultiGroup() {
        UserDAOI userDao = dbBaseUserPermissions.getUserDAO();
        User u = userDao.findByLogin("kurt.schluss");
        List<Section> s = userDao.getSectionsForUser(u.getId());
        assertTrue(s.size() == 20);
    }

    /**
     * Test, ob Benutzer mit mehrfachfiltern diese auch erhalten.
     */
    public void testUserMultipleFilter() {
        UserDAOI userDAO = dbBaseUserPermissions.getUserDAO();
        // User u = userDAO.findById(62L);
        User u = userDAO.findByLoginPwd("11950", "AB8D2CB0550CCA77D5C9A0831EC5825386E7AB17");
        System.out.println("Filtercheck: " + u.getAllFilter());
        assertTrue(u.getAllFilter().size() == 2);
    }

    public void testString2XOR() {
        String xor = "apo";
        String resHexOriginal = DAOUtil.convertStringToHex(xor);
        assertEquals("61706f", resHexOriginal);

        String resHex = DAOUtil.string2XORHex(xor);
        assertEquals("9e8f90", resHex);
    }

    public void testInt2XOR() {
        // XOR ...
        char c = 97;
        int resHex = DAOUtil.char2XORInt(c);
        assertEquals(158, resHex);

        // ... und wieder zurück
        c = 158;
        resHex = DAOUtil.char2XORInt(c);
        assertEquals(97, resHex);
    }

    public void testChar2XOR() {
        // XOR ...
        char c = 'a';
        int resHex = DAOUtil.char2XORInt(c);
        assertEquals(158, resHex);

        // ... und wieder zurück
        c = 158; // Lässt sich als Char nicht darstellen
        resHex = DAOUtil.char2XORInt(c);
        assertEquals(97, resHex);
    }

    public void testChar2XORHex1() {
        // XOR ...
        char c = 'a';
        int resHex = DAOUtil.char2XORInt(c);
        String hexString = Integer.toHexString(resHex);
        assertEquals("9e", hexString);

        // ... und wieder zurück
        String hex = "9e";
        int decimal = Integer.parseInt(hex, 16);
        resHex = DAOUtil.char2XORInt((char) decimal);
        assertEquals('a', (char) resHex);
    }

    public void testChar2XORHex2() {
        // XOR ...
        char c = 'p';
        int resHex = DAOUtil.char2XORInt(c);
        String hexString = Integer.toHexString(resHex);
        assertEquals("8f", hexString);

        // ... und wieder zurück
        String hex = "8f";
        int decimal = Integer.parseInt(hex, 16);
        resHex = DAOUtil.char2XORInt((char) decimal);
        assertEquals('p', (char) resHex);
    }

    /**
     * Mit einem Zeichen klappt alles. Jetzt muss
     */
    public void testChar2XorAndBack() {

        // Einmal "verschlüsselt"
        assertEquals("9e8f90", DAOUtil.string2XORHex("apo"));

        // Das muss rauskommen ...
        // assertEquals("apo", DAOUtil.convertHexToString("61706f"));

        // Und wieder zurück ...
        assertEquals("apo", DAOUtil.XORHex2String("9e8f90"));
    }

    public void testDirektanmeldungToken() {
        String check;
        check = DAOUtil.XORHex2String("9e8f90db9b9a9c90929e8c8b9a8ddbcfcfcfcfc8cecaa0cedbcb9b9ecbcaccc6cd");
        assertEquals("apo$decomaster$0000715_1$4da45392", check);
    }

    public void testDirktanmeldungTimestamp() {
        String timeStampInSeconds = "4da45392";
        long timeStampL = Long.parseLong(timeStampInSeconds, 16) * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        System.out.println("Zeitcheck: " + sdf.format(new Date(timeStampL)));
    }

    public void testAEPTimestampMD5() {
        CryptoTools c = new CryptoTools();
        String md5 = c.encryptOneWayMD5("20130830");
        assertEquals("2ff0298a134f678702807a901701f708".toLowerCase(), md5.toLowerCase());

        // md5 = c.encryptOneWayMD5("20130829");
        // assertEquals("2ff0298a134f678702807a901701f708".toLowerCase(), md5.toLowerCase());
    }

    public void testGetAllIpAdrAllowed() {
        UserDAOI userDAO = dbBaseUserPermissions.getUserDAO();
        List l = userDAO.getIPAdressesAllowed();
        assertTrue(l.size() > 0);
    }

    public void testTwinter() {
        UserDAOI userDao = dbBaseUserPermissions.getUserDAO();
        User u = userDao.findByLogin("twinter@decodetron.de");
        assertTrue(u.getAllFilter().size() > 0);
    }
}
