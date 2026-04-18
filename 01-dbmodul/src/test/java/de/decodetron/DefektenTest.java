// $Log: DefektenTest.java,v $
// Revision 1.8  2020/02/26 19:23:08  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.7  2014/05/21 13:58:44  tw
// Versuchsblase: Neudefinition v. Lieferanten.
//
// Revision 1.6  2014/04/28 11:12:49  tw
// Schnittstellen- umzug/umbenennung Filter=>Statistik
//
// Revision 1.5  2014/04/15 00:15:39  tw
// SQL-Injection sichere Verarbeitung (nur f. Defekentlisten!).
//
// Revision 1.4  2014/04/01 00:59:38  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.3  2014/03/31 21:45:46  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.2  2014/03/28 23:02:33  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.1  2014/03/28 17:22:33  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
//

package de.decodetron;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import junit.framework.TestCase;
import de.decodetron.bo.DataRecord;
import de.decodetron.bo.User;
import de.decodetron.dao.statistik.defekte.DAOIDefekte;
import de.decodetron.fac.DAOFactoryJDBC;
import de.decodetron.util.SystemUtil;

/**
 * @author Thomas Winter
 * @since 28.03.2014
 */
public class DefektenTest extends TestCase {

    DAOIDefekte daoDefekten = null;

    public DefektenTest() {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileDefekte = u.getHomeDirectory(userHome, p.getProperty("file.db.defekte"));
        DAOFactoryJDBC dbDefekten = DAOFactoryJDBC.getInstance(dbFileDefekte);
        daoDefekten = dbDefekten.getDAODefekte();
    }

    /**
     * Angezeigte Spalten varieren an der Oberfläche, je nach dem ob Lieferant oder nicht. In dem
     * Fall um 3 Spalten.
     */
    public void testGetColNames() {
        User testUser = new User();
        testUser.setIsLieferant(false);
        DataRecord dr1Lieferant = daoDefekten.getDefekteColNames(testUser);
        testUser.setIsLieferant(true);
        DataRecord dr2NoLieferant = daoDefekten.getDefekteColNames(testUser);        
        assertTrue(dr1Lieferant.getSize() - dr2NoLieferant.getSize() == 4);
    }
    
    public void testSQLWithoutValue(){
        daoDefekten.createStatementWithoutValue();
    }
    
    public void testPreparedStatement(){
        long val = daoDefekten.countDefekteDataTest();
        System.out.println("Count: " + val);
    }
}
