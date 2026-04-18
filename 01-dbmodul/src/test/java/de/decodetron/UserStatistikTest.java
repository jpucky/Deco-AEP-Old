// $Log: UserStatistikTest.java,v $
// Revision 1.4  2015/02/20 20:22:26  tw
// Vorbereitung f. spaetere User-On-Time.db Absplittung aus decoUser.db.
//
// Revision 1.3  2014/04/15 15:33:20  tw
// Schnittstellensaeuberung: AEP-Plus-Abverkauf.
//
// Revision 1.2  2014/02/21 00:38:21  tw
// Statistik-Schnittstellen implementiert.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.1  2014/02/20 03:52:16  tw
// Statistik-Schnittstellen implementiert.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
//

package de.decodetron;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;
import de.decodetron.bo.DataRecord;
import de.decodetron.bo.User;
import de.decodetron.bo.UserStatistik;
import de.decodetron.dao.statistikuser.StatistikUserDAOI;
import de.decodetron.dao.user.UserDAOI;
import de.decodetron.fac.DAOFactoryJDBC;
import de.decodetron.util.SystemUtil;

/**
 * @author Thomas Winter
 * @since 18.02.2014
 */
public class UserStatistikTest extends TestCase {

    private Long SEKUNDE = 1000L;
    private Long MINUTE = SEKUNDE * 60;
    private Long STUNDE = MINUTE * 60;

    private StatistikUserDAOI statDao = null;
    //private UserDAOI userDao = null;
    private Long ID_USER1 = 1L;
    private Long ID_USER2 = 3L;

    @Override
    protected void setUp() throws Exception {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileUserPerm = u.getHomeDirectory(userHome, p.getProperty("file.db.userstatistik"));
        DAOFactoryJDBC dbFactory = DAOFactoryJDBC.getInstance(dbFileUserPerm);
        statDao = dbFactory.getStatistikUserDAO();
        //userDao = dbFactory.getUserDAO();
    }

    public void testInsertUserStat2013() {

        GregorianCalendar cal = new GregorianCalendar(2013, 2, 3, 3, 15, 6);

        UserStatistik uStat = new UserStatistik();
        uStat.setId_user(ID_USER1);
        uStat.setIp("1.2.3.4");
        uStat.setLogintime(cal.getTimeInMillis());
        uStat.setLogoutTime(new GregorianCalendar(2013, 2, 3, 3, 15, 6).getTimeInMillis());

        statDao.insertUserStatistik(uStat);
    }

    public void testInsertUserStatNow() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        UserStatistik uStat = new UserStatistik();
        uStat.setId_user(ID_USER2);
        uStat.setIp("4.5.6.7");
        uStat.setLogintime(sdf.format(new Date().getTime()));
        //uStat.setLogoutTime(sdf.format(new Date().getTime() + STUNDE));
        statDao.insertUserStatistik(uStat);        
        uStat = statDao.getStatistik4UserId(ID_USER2);
        assertNotNull(uStat);
        assertNotNull(uStat.getId_user());
        assertNull(uStat.getLogouttime());
        
        uStat.setLogoutTime(sdf.format(new Date().getTime() + STUNDE));
        statDao.updateUserStatistik(uStat);
        assertNotNull(uStat.getLogouttime());
    }
    
    
    public void testDeleteUserStat() {

        List<DataRecord> dr = statDao.getAllRecordsFilterBy("userstatistik", "", String.valueOf(ID_USER1));
        dr.addAll(statDao.getAllRecordsFilterBy("userstatistik", "", String.valueOf(ID_USER2)));
        assertTrue(dr.size() > 0);
        
        for (Iterator<DataRecord> iterator = dr.iterator(); iterator.hasNext();) {
            DataRecord drTmp = iterator.next();
            String id = drTmp.getColItem(0);
            statDao.deleteAllRecordsBy("userstatistik", id);
        }

        dr = statDao.getAllRecordsFilterBy("userstatistik", "", String.valueOf(ID_USER1));
        dr.addAll(statDao.getAllRecordsFilterBy("userstatistik", "", String.valueOf(ID_USER2)));
        assertTrue(dr.size() == 0);
    }
}
