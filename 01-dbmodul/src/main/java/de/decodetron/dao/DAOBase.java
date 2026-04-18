// $Log: DAOBase.java,v $
// Revision 1.15  2020/03/09 22:31:04  tw
// Version 1.32-H
// -----------------------------
// CR: Umstellung der Statements um Einschraenkung per Datum vornehmen zu koennen.
// BUGFIX: Datums-Suche AEP-Plus (CLUBBESTAND) / Valuta korrigiert.
//
// Revision 1.14  2020/02/28 22:07:35  tw
// Neuer Treiber
//
// Revision 1.13  2020/02/28 20:30:09  tw
// Alte Funktionen entfernt. System.out. f. Debugzwecke erstellt.
//
// Revision 1.12  2015/07/21 00:32:08  tw
// CR Feng-ID: 3952. Grundfunktionalitaet erstellt.
//
// Revision 1.11  2014/11/06 13:12:26  tw
// Testanbindung: History-DB Benutzerrechte.
//
// Revision 1.10  2014/04/15 19:57:04  tw
// Schnittstellensaeuberung: AEP-Plus-Abverkauf.
//
// Revision 1.9  2014/04/15 00:15:39  tw
// SQL-Injection sichere Verarbeitung (nur f. Defekentlisten!).
//
// Revision 1.8  2014/04/03 14:44:51  tw
// Bean-Property-Bug behoben.
//
// Revision 1.7  2014/04/01 00:59:38  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.6  2014/02/20 03:52:16  tw
// Statistik-Schnittstellen implementiert.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.5  2013/12/04 15:55:50  tw
// csv-liste fuer btm implementiert.
//
// Revision 1.4  2013/11/27 13:43:55  tw
// Bugfix: https://team.decodetron.de:10443/index.php?c=task&a=view_task&id=3930
//
// Revision 1.3  2013/11/25 16:51:48  tw
// Waehrungsformatierung.
//
// Revision 1.2  2013/11/25 11:01:36  tw
// Limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.1  2013/11/21 17:40:01  tw
// Neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.2  2013/11/14 15:49:20  tw
// Schnittstellennormalisierung.
//
// Revision 1.1  2013/11/14 14:08:47  tw
// Schnittstellen Datenfilterung implementiert.
//
//

package de.decodetron.dao;

import static de.decodetron.util.DAOUtil.close;
import static de.decodetron.util.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import de.decodetron.bo.DataRecord;
import de.decodetron.bo.PreparedStatementO;
import de.decodetron.dao.history.HistoryBrDAOI;
import de.decodetron.exc.DAOException;
import de.decodetron.fac.DAOFactoryJDBC;
import de.decodetron.util.SystemUtil;

/**
 * Nur um ein paar gemeinsame Helferlein in den Keller zu verbannen ...
 * 
 * @author Thomas Winter
 * @since 14.11.2013
 */
public class DAOBase {

    private static Logger log = Logger.getLogger(DAOBase.class);
    private static Logger logDB = Logger.getLogger("LOGFILE");
    private static DecimalFormat dc = new DecimalFormat("###,###,###.00");
    public final static NumberFormat NF_MONEY = NumberFormat.getCurrencyInstance();

    /**
     * Mapped ein resultSet in ein Datarecord. Formatiert float/double-Werte in Zahlen mit
     * zweistelliger Kommastelle.
     * 
     * @param resultSet
     * @return
     * @throws SQLException
     */
    public DataRecord map(ResultSet resultSet) throws SQLException {

        DataRecord dr = new DataRecord();
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int colnr = rsmd.getColumnCount();
        for (int i = 1; i <= colnr; ++i) {

            int coltypeI = rsmd.getColumnType(i);
            // if (i == 12) {
            // System.out.println("Preis Spaltentyp: " + coltypeI);
            // }
            switch (coltypeI) {
                // <=> MONEY !?
                case Types.FLOAT: {
                    Double value = Double.valueOf(resultSet.getString(rsmd.getColumnName(i)));
                    // dr.addColItem(dc.format(value));
                    dr.addColItem(NF_MONEY.format(value));
                    break;
                }
                case Types.INTEGER: {
                    String colName = rsmd.getColumnName(i);
                    // ARGH, wenn die Werte keine Kommastelle haben werden sie anscheinend als INT
                    // interpretiert. !!!
                    if ("Preis".equalsIgnoreCase(colName) || "Betrag".equalsIgnoreCase(colName)) {
                        Double value = Double.valueOf(resultSet.getString(rsmd.getColumnName(i)));
                        // dr.addColItem(dc.format(value));
                        dr.addColItem(NF_MONEY.format(value));
                        break;
                    }
                }
                default: {
                    dr.addColItem(resultSet.getString(rsmd.getColumnName(i)));
                }
            }
        }
        return dr;
    }

    public static DataRecord mapColName(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int colNr = rsmd.getColumnCount();
        DataRecord dr = new DataRecord(colNr);
        for (int i = 1; i <= colNr; ++i) {
            dr.addColItem(rsmd.getColumnName(i));
        }
        return dr;
    }

    /**
     * Spricht, glaube ich, für sich. Helferlein um zwischen den Bean-Properties und den
     * Spaltenbezeichnern zu Vermitteln.
     * 
     * @param String
     *            str
     * @return String
     */
    public String lowerCaseFirstChar(String str) {
        if (str != null && str.length() > 0) {
            StringBuilder sb = new StringBuilder(str.length());
            sb.append(str.substring(0, 1).toLowerCase());
            sb.append(str.substring(1, str.length()));
            return sb.toString();
        } else {
            return str;
        }
    }

    /**
     * Hilfsfunktion, für DataRecords. Die Liste enthält keine Tabellenkopfinformationen!
     * 
     * @param String
     *            sql
     * @return List<DataRecord>
     */
    public List<DataRecord> executeSQLStatement(String sql, DAOFactoryJDBC daoFactory) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<DataRecord> cBest = new ArrayList<DataRecord>();

        log.debug(sql);

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cBest.add(map(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement, resultSet);
        }
        return cBest;
    }

    /**
     * Gibt den Long-Wert eines Count-SQL-Statements zurück.
     * 
     * @param String
     *            sql
     * @param DAOFactoryJDBC
     *            daoFactory
     * @return Long
     */
    public Long executeSQLCountStatemet(String sql, DAOFactoryJDBC daoFactory) {

        Long recordCount = 0L;
        int dataRecordCounter = 0;
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                recordCount = Long.valueOf(resultSet.getString(++dataRecordCounter));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return recordCount;
    }

    /**
     * Benutzt ein "richtiges" prepared Statement um SQL - Injection zu verhindern.
     * 
     * @param PreparedStatementO
     *            ps
     * @param DAOFactoryJDBC
     *            daoFactory
     * @return List<DataRecord>
     */
    public List<DataRecord> executeSQLStatement4PS(PreparedStatementO ps, DAOFactoryJDBC daoFactory) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<DataRecord> cBest = new ArrayList<DataRecord>();

        log.debug(ps.getSql());

        try {
            logDB.debug("SQL: " + ps.getSql());
            logDB.debug("VAL: " + ps.toString());
            
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, ps.getSql(), false, (Object[]) ps.getValues());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cBest.add(map(resultSet));
            }
            
            logDB.debug("--------------------------------------------------------------------------");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement, resultSet);
        }
        return cBest;
    }

    /**
     * Benutzt ein "richtiges" prepared Statement um SQL - Injection zu verhindern.
     * 
     * @param PreparedStatementO
     *            ps
     * @param DAOFactoryJDBC
     *            daoFactory
     * @return Long
     */
    public Long executeSQLCountStatemet4PS(PreparedStatementO ps, DAOFactoryJDBC daoFactory) {

        Long recordCount = 0L;
        int dataRecordCounter = 0;
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            logDB.debug("SQL: " + ps.getSql());
            logDB.debug("VAL: " + ps.toString());
            
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, ps.getSql(), false, (Object[]) ps.getValues());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                recordCount = Long.valueOf(resultSet.getString(++dataRecordCounter));
            }

            logDB.debug("--------------------------------------------------------------------------");
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement);
        }

        return recordCount;
    }

    public void executeDeleteStatement(String sql, DAOFactoryJDBC daoFactory) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        log.debug(sql);

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting user failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement, resultSet);
        }
    }

    /**
     * Für den ausserplanmässigen Aufruf aus DAO-fremden Containern.
     * 
     * @return HistoryBrDAOI
     */
    public static HistoryBrDAOI getDBHistoryBr() {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileUserPerm = u.getHomeDirectory(userHome, p.getProperty("file.db.historybr"));
        return DAOFactoryJDBC.getInstance(dbFileUserPerm).getDAOHistoryBr();
    }

}
