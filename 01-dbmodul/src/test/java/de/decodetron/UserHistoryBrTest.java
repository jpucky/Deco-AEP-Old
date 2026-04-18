// $Log: UserHistoryBrTest.java,v $
// Revision 1.5  2014/11/24 22:35:18  tw
// Bugfix: Junit-DB Aufruf separiert.
//
// Revision 1.4  2014/11/16 21:40:06  tw
// Bugfix: Junit-DB Aufruf separiert.
//
// Revision 1.3  2014/11/06 13:12:26  tw
// Testanbindung: History-DB Benutzerrechte.
//
// Revision 1.2  2014/11/04 16:40:00  tw
// Testanbindung: History-DB Benutzerrechte.
//
// Revision 1.1  2014/11/03 16:57:07  tw
// Testanbindung: History-DB Benutzerrechte.
//
//

package de.decodetron;

import java.util.Arrays;
import java.util.Properties;

import junit.framework.TestCase;
import de.decodetron.bo.HistoryBr;
import de.decodetron.bo.SectionInfo;
import de.decodetron.bo.UserSectionData;
import de.decodetron.dao.DAOBase;
import de.decodetron.dao.history.HistoryBrDAOI;
import de.decodetron.fac.DAOFactoryJDBC;
import de.decodetron.util.SystemUtil;
import de.decodetron.util.Util;

/**
 * @author Thomas Winter
 * @since 31.10.2014
 */
public class UserHistoryBrTest extends TestCase {

    public static HistoryBrDAOI getDBHistoryBrJunit() {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileUserPerm = u.getHomeDirectory(userHome, p.getProperty("file.db.historybr.ju"));
        return DAOFactoryJDBC.getInstance(dbFileUserPerm).getDAOHistoryBr();
    }
    
    /**
     * Simpler Test der Standartschnittstellen.
     */
    public void testDefaultInterfaces() {
        UserSectionData user = new UserSectionData();
        user.setId(null);
        user.setLogin("testLogin");
        user.setClubabver((new SectionInfo(-1, Boolean.TRUE)));
        user.addFilter(Arrays.asList("123", "456", "789"));

        HistoryBr hBenuzterrechte = new HistoryBr();
        // hBenuzterrechte.setId(null);
        hBenuzterrechte.setId_user(-11L);
        hBenuzterrechte.setAktion(HistoryBr.AKTION_ANLEGEN);
        hBenuzterrechte.setTimeStamp(System.currentTimeMillis());
        hBenuzterrechte.setXmlUserData(Util.object2XmlString(user));

        // /////////////////////////////////
        // //// insert
        // /
        getDBHistoryBrJunit().insert(hBenuzterrechte);
        assertNotNull(hBenuzterrechte.getId());

        // /////////////////////////////////
        // //// search
        // /
        HistoryBr h = getDBHistoryBrJunit().findById(hBenuzterrechte.getId());
        assertNotNull(h);
        assertTrue(HistoryBr.AKTION_ANLEGEN.equals(h.getAktion()));
        assertNotNull(h.getTimeStamp());

        // /////////////////////////////////
        // //// delete
        // /
        getDBHistoryBrJunit().delete(h);
        h = getDBHistoryBrJunit().findById(hBenuzterrechte.getId());
        assertNull(h);
    }

}
