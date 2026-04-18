// $Log: DAOFactoryJDBC.java,v $
// Revision 1.17  2015/07/21 00:32:09  tw
// CR Feng-ID: 3952. Grundfunktionalitaet erstellt.
//
// Revision 1.16  2015/07/10 15:09:21  tw
// Implementierung: Lucken-SR. DB-Anbindung.
//
// Revision 1.15  2015/02/20 20:22:25  tw
// Vorbereitung f. spaetere User-On-Time.db Absplittung aus decoUser.db.
//
// Revision 1.14  2014/11/03 16:57:06  tw
// Testanbindung: History-DB Benutzerrechte.
//
// Revision 1.13  2014/04/28 11:12:49  tw
// Schnittstellen- umzug/umbenennung Filter=>Statistik
//
// Revision 1.12  2014/04/15 15:33:20  tw
// Schnittstellensaeuberung: AEP-Plus-Abverkauf.
//
// Revision 1.11  2014/03/28 17:22:33  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.10  2014/03/01 23:58:12  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.9  2014/02/20 03:52:16  tw
// Statistik-Schnittstellen implementiert.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.8  2014/01/24 13:39:06  tw
// Schnittstellen aufgeraeumt. Neue limitierte Benutzerschnittstelle.
//
// Revision 1.7  2013/12/02 12:33:30  tw
// Neue Schnittstelle um unnoetige Spalten auszublenden.
//
// Revision 1.6  2013/11/29 12:32:20  tw
// Neue Liste: Valuta.
//
// Revision 1.5  2013/11/28 17:17:59  tw
// Neue Liste: Ueberweiser.
//
// Revision 1.4  2013/11/28 14:34:41  tw
// Neue Liste: Transfusion.
//
// Revision 1.3  2013/11/28 13:40:10  tw
// Schnittstelle Tierarznei.
//
// Revision 1.2  2013/11/27 17:05:44  tw
// Spaltenmapping, Vorbereitung.
//
// Revision 1.1  2013/11/21 17:40:02  tw
// Neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.5  2013/11/06 22:39:25  tw
// Neue Schnittstellentests f. flexiblere Abfragen.
//
// Revision 1.4  2013/11/05 23:02:09  tw
// db-schnittstellen aufgerumt.
//
// Revision 1.3  2013/11/05 19:31:31  tw
// db-schnittstellen aufgeraeumt.
//
// Revision 1.1  2013/11/02 23:17:00  tw
// Bugfix.
//
// Revision 1.1  2013/11/02 21:46:42  tw
// ClubBestand-DB-Schicht. 1. Schritte zur Veralgemeinerung.
//
// Revision 1.9  2013/10/31 00:14:26  tw
// Konfiguration der Quelldatenverzeichnisse von aussen moeglich.
//
// Revision 1.8  2013/10/27 17:25:00  tw
// Kommentar entfernt.
//
// Revision 1.7  2013/10/25 02:43:49  tw
// System.out. f�r den Durchblick
//
// Revision 1.6  2013/10/25 00:20:38  tw
// Anpassung an Rollout.
//
// Revision 1.5  2013/09/17 02:05:43  tw
// Login-Logout-Mechanismus implementiert.
//
// Revision 1.4  2013/09/16 14:35:39  tw
// Backup, Einbau: Login-Mechanismus
//
// Revision 1.3  2013/09/15 19:59:04  tw
// Tests f. Importmodul fertig.
//
// Revision 1.2  2013/09/13 02:17:14  tw
// neues build-konzept.
//
// Revision 1.1  2013/08/07 01:46:51  tw
// AEP LoginModul als simpelst-keine-Zusatzbibliotheken-Variante. Erster Wurf.
//
//

package de.decodetron.fac;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import de.decodetron.dao.DAO;
import de.decodetron.dao.DAOI;
import de.decodetron.dao.history.HistoryBrDAO;
import de.decodetron.dao.history.HistoryBrDAOI;
import de.decodetron.dao.statistik.StatistikDAO;
import de.decodetron.dao.statistik.StatistikDAOI;
import de.decodetron.dao.statistik.club.DAOClub;
import de.decodetron.dao.statistik.club.DAOIClub;
import de.decodetron.dao.statistik.defekte.DAODefekte;
import de.decodetron.dao.statistikuser.StatistikUserDAO;
import de.decodetron.dao.statistikuser.StatistikUserDAOI;
import de.decodetron.dao.user.UserDAOI;
import de.decodetron.dao.user.UserDAOJDBC;
import de.decodetron.exc.DAOConfigurationException;

/**
 * @author Thomas Winter
 * @since 06.08.2013
 */
public abstract class DAOFactoryJDBC {

    /**
     * 
     * Verlangt die komplette schemaFileName - Adressierung. Der Teil hinter:
     * "jdbc:sqlite:<schemaFile>".
     * 
     * 
     * @param String
     *            schemaName
     * @return DAOFactoryJDBC
     * @throws DAOConfigurationException
     */
    public static DAOFactoryJDBC getInstance(final String schemaFileName) throws DAOConfigurationException {
        DAOFactoryJDBC instance;
        instance = new DAOFactoryJDBC() {
            @Override
            public Connection getConnection() throws SQLException {
                String driverClassName = "org.sqlite.JDBC";
                try {
                    Class.forName(driverClassName);
                } catch (ClassNotFoundException e) {
                    throw new DAOConfigurationException("Driver class '" + driverClassName
                            + "' is missing in classpath.", e);
                }
                String url = "jdbc:sqlite:" + schemaFileName;
                String username = "";
                String password = "";
                return DriverManager.getConnection(url, username, password);
            }
        };
        return instance;
    }

    /**
     * Returns a connection to the database. Package private so that it can be used inside the DAO
     * package only.
     * 
     * @return A connection to the database.
     * @throws SQLException
     *             If acquiring the connection fails.
     */
    public abstract Connection getConnection() throws SQLException;

    // DAO implementation getters -----------------------------------------------------------------
    public UserDAOI getUserDAO() {
        return new UserDAOJDBC(this);
    }

    public StatistikUserDAOI getStatistikUserDAO() {
        return new StatistikUserDAO(this);
    }

    public DAOI getDAO() {
        return new DAO(this);
    }

    public StatistikDAOI getDAOFilter() {
        return new StatistikDAO(this);
    }

    public DAOIClub getDAOClub() {
        return new DAOClub(this);
    }

    public DAODefekte getDAODefekte() {
        return new DAODefekte(this);
    }

    public HistoryBrDAOI getDAOHistoryBr() {
        return new HistoryBrDAO(this);
    }

    // public LueckeSRDAOI getLueckeSRDAO(){
    // SystemUtil u = new SystemUtil();
    // String userHome = System.getProperty("user.dir");
    // Properties p = new SystemUtil().loadSystemProperties("db.properties");
    // String dbFileUserPerm = u.getHomeDirectory(userHome, p.getProperty("file.db.sr"));
    // return new LueckeSRDAO(DAOFactoryJDBC.getInstance(dbFileUserPerm));
    // }
    // You can add more DAO implementation getters here.
}
