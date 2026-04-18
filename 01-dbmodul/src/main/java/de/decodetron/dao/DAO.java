// $Log: DAO.java,v $
// Revision 1.19  2020/03/09 22:31:04  tw
// Version 1.32-H
// -----------------------------
// CR: Umstellung der Statements um Einschraenkung per Datum vornehmen zu koennen.
// BUGFIX: Datums-Suche AEP-Plus (CLUBBESTAND) / Valuta korrigiert.
//
// Revision 1.18  2020/02/28 20:30:09  tw
// Alte Funktionen entfernt. System.out. f. Debugzwecke erstellt.
//
// Revision 1.17  2020/02/26 19:23:08  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.16  2015/07/21 00:32:08  tw
// CR Feng-ID: 3952. Grundfunktionalitaet erstellt.
//
// Revision 1.15  2015/02/05 00:13:17  tw
// Bugfix: Feng-ID: 3944. Unterdrueckung v. Datensaetzen m. null - values.
//
// Revision 1.14  2014/07/30 16:08:54  tw
// Scanbelege, Lucene Objektmapper erstellt, Aufraeumarbeiten.
//
// Revision 1.13  2014/05/06 00:43:40  tw
// Backup: Benutzer anlegen.
//
// Revision 1.12  2014/04/24 21:15:16  tw
// Bugfix f. Nullvalues.
//
// Revision 1.11  2014/04/15 19:57:04  tw
// Schnittstellensaeuberung: AEP-Plus-Abverkauf.
//
// Revision 1.10  2014/04/15 15:33:20  tw
// Schnittstellensaeuberung: AEP-Plus-Abverkauf.
//
// Revision 1.9  2014/03/27 12:06:55  tw
// Vorbereitung, CR-Defektenlisten f. Lieferanten: Markup-freie Tabellengestaltung.
//
// Revision 1.8  2014/02/20 03:52:16  tw
// Statistik-Schnittstellen implementiert.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.7  2013/11/28 13:38:33  tw
// Schnittstelle Tierarznei.
//
// Revision 1.6  2013/11/27 17:05:44  tw
// Spaltenmapping, Vorbereitung.
//
// Revision 1.5  2013/11/25 16:51:48  tw
// Waehrungsformatierung.
//
// Revision 1.4  2013/11/25 15:36:03  tw
// Defektenliste: limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.3  2013/11/25 11:01:36  tw
// Limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.2  2013/11/21 22:11:35  tw
// Vorbereitung: neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.1  2013/11/21 17:40:01  tw
// Neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.19  2013/11/18 10:10:59  tw
// Bugfix: Multiuserfilter.
//
// Revision 1.18  2013/11/17 13:41:59  tw
// Neue Schnittstelle f. User mit mehrfachfiltern implementiert.
//
// Revision 1.17  2013/11/15 21:47:41  tw
// Tabellenspalten werden per seperater DB-Funktion abgerufen.
//
// Revision 1.16  2013/11/14 15:49:20  tw
// Schnittstellennormalisierung.
//
// Revision 1.15  2013/11/14 14:08:47  tw
// Schnittstellen Datenfilterung implementiert.
//
// Revision 1.14  2013/11/14 01:34:42  tw
// aaaaaaaaaaaaaaargh umlaute.
//
// Revision 1.13  2013/11/13 23:13:20  tw
// Vorbereitung: Schnittstellen fuer Datenfilter.
//
// Revision 1.12  2013/11/13 00:29:59  tw
// Servicefunktionen fuer Berechtigungskonzept implementiert.
//
// Revision 1.11  2013/11/10 23:31:56  tw
// .
//
// Revision 1.10  2013/11/10 20:17:18  tw
// Bugfix: select - statement hat nicht korrekt auf null - values reagiert.
//
// Revision 1.9  2013/11/08 11:44:00  tw
// Compiler nochmals utf-8 verklickert.
//
// Revision 1.8  2013/11/08 11:00:34  tw
// Umlaute an vorerst ersetzt f. Fehlereingrenzung.
//
// Revision 1.7  2013/11/07 22:05:26  tw
// Clubliste geradegezogen.
//
// Revision 1.6  2013/11/07 17:43:52  tw
// Bugfix: getAllColumns
//
// Revision 1.5  2013/11/06 22:15:28  tw
// Neue Schnittstellentests f. flexiblere Abfragen.
//
// Revision 1.4  2013/11/05 23:02:09  tw
// db-schnittstellen aufger�umt.
//
// Revision 1.3  2013/11/05 19:31:31  tw
// db-schnittstellen aufgeraeumt.
//
// Revision 1.2  2013/11/02 23:16:39  tw
// Bugfix.
//
// Revision 1.1  2013/11/02 21:46:42  tw
// ClubBestand-DB-Schicht. 1. Schritte zur Veralgemeinerung.
//
//

package de.decodetron.dao;

import static de.decodetron.util.DAOUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import de.decodetron.bo.DataRecord;
import de.decodetron.bo.PreparedStatementO;
import de.decodetron.exc.DAOException;
import de.decodetron.fac.DAOFactoryJDBC;

/**
 * @author Thomas Winter
 * @since 02.11.2013
 */
public class DAO extends DAOBase implements DAOI {

    private DAOFactoryJDBC daoFactory;

    /**
     * Construct an User DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     * 
     * @param daoFactory
     *            The DAOFactory to construct this User DAO for.
     */
    public DAO(DAOFactoryJDBC daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public List<DataRecord> getAllRecords(String tableName) throws DAOException {

        int dataRecordCounter = 0;
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<DataRecord> cBest = new ArrayList<DataRecord>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("select * from " + tableName + ";");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (++dataRecordCounter == 1) {
                    cBest.add(mapColName(resultSet));
                }
                cBest.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return cBest;
    }

    @Override
    public Long countAllRecords(String tableName) throws DAOException {

        Long recordCount = 0L;
        int dataRecordCounter = 0;
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<DataRecord> cBest = new ArrayList<DataRecord>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("select count(*) from " + tableName + ";");
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

    @Override
    public List<DataRecord> getAllRecordsLimited(String tableName, String colName2Sort, String sortOrder, int limit,
            long offset) throws DAOException {

        int dataRecordCounter = 0;
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<DataRecord> cBest = new ArrayList<DataRecord>();

        try {
            connection = daoFactory.getConnection();

            StringBuilder sb = new StringBuilder();
            sb.append("select * from ");
            sb.append(tableName + " a ");
            sb.append(" order by ");
            sb.append("a.").append(colName2Sort).append(" ");
            sb.append(" ").append(sortOrder).append(" ");
            sb.append(" limit ").append(limit).append(" ");
            sb.append(" offset ").append(offset).append(";");

            preparedStatement = connection.prepareStatement(sb.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (++dataRecordCounter == 1) {
                    cBest.add(mapColName(resultSet));
                }
                cBest.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return cBest;
    }

    public List<String> getColumNames(String tableName) {

        List<String> columNames = new ArrayList<String>();
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(createSQLColumNames(tableName));
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int colNr = rsmd.getColumnCount();
            for (int i = 1; i <= colNr; ++i) {
                columNames.add(rsmd.getColumnName(i));
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return columNames;
    }

    /**
     * Bereitgestellt zu Überschreiben:<br>
     * "select * from " + tableName + ";"
     * 
     * @param tableName
     * @return String
     */
    public String createSQLColumNames(String tableName) {
        return "select * from " + tableName + ";";
    }

    public List<String> getColumTypes(String tableName) {

        List<String> columNames = new ArrayList<String>();
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            String sql = "select * from " + tableName + ";";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();

            int colNr = rsmd.getColumnCount();
            for (int i = 1; i <= colNr; ++i) {
                // int colTypeI = rsmd.getColumnType(i);
                // System.out.println(i + ", Label : " + rsmd.getColumnLabel(i) + " / " +
                // rsmd.getColumnTypeName(i));
                columNames.add(rsmd.getColumnTypeName(i));
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return columNames;
    }

    public DataRecord getColumNamesAsDataRecord(String tableName) {

        DataRecord columNames = new DataRecord();
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("select * from " + tableName + ";");
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

    @Override
    public List<DataRecord> getAllRecordsFilterBy(String tableName, String... filterValues) throws DAOException {

        List<String> colNames = getColumNames(tableName);

        if (filterValues.length > colNames.size()) {
            DataRecord r = new DataRecord();
            r.addColItem("Mehr Filter als Spalten !!!");
            return Arrays.asList(r);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("select * from ").append(tableName).append(" c ");
        for (int i = 0; i < filterValues.length; i++) {
            sb.append(i == 0 ? "where (c." : "and (c.");
            sb.append(colNames.get(i)).append(" like '%");
            sb.append(filterValues[i] == null ? "" : filterValues[i]).append("%'");
            sb.append(" or ");
            sb.append("c.").append(colNames.get(i)).append(" is null) ");
        }
        sb.append(";");

        return executeSQLStatement(sb.toString(), daoFactory);
    }

    /**
     * Erstellt das Sql-Statement aus dem User.Filter und dem Section.FilterIdentifier zusammen.
     * 
     * @param List
     *            <String> userFilter
     * @param String
     *            identifier
     * @return String
     */
    public String createFilterStatement(List<String> userFilter, String identifier) {

        StringBuilder sb = new StringBuilder();
        if (userFilter != null) {
            for (int i = 0; i < userFilter.size(); i++) {
                sb.append(i == 0 ? "where (c." : "or c.");
                sb.append(identifier).append(" like '");
                String tmpFilter = userFilter.get(i);
                tmpFilter = "*".equals(tmpFilter) ? "%%" : tmpFilter;
                sb.append(tmpFilter).append("' ");
            }
            sb.append(")");
        }
        return sb.toString();
    }

    /**
     * Alle Statements werden über ein AND zusammengesetzt. Eine spätere Funktion muss sich darum
     * kümmern an die richtige Stelle das "WHERE" zu setzten.
     * 
     * @param colNames
     * @param filterValues
     * @return
     */
    public PreparedStatementO createSQLFilterValues4PS(List<String> colNames, String... filterValues) {

        List<String> valuesL = new ArrayList<String>();
        PreparedStatementO ps = new PreparedStatementO();
        StringBuilder sb = new StringBuilder();

        int indexOffset = 0;
        for (int i = 0; i < filterValues.length; i++) {

            if (i + indexOffset == filterValues.length) {
                continue;
            }
            String filterItem = filterValues[i + indexOffset];
            String colNName = colNames.get(i);

            if (colNName.endsWith("Datum") || colNName.endsWith("datum")) {

                String fromDate = filterItem;
                indexOffset = 1;
                String toDate = filterValues[i + indexOffset];                
                
                // 1.Fall: Von und Bis - Datum sind leer:
                if((fromDate == null || fromDate.length() == 0) && (toDate == null || toDate.length() == 0)){
                    // nix
                }
                
                // 2.Fall: Von und Bis - Datum gefüllt:
                if ((fromDate != null) && (fromDate.length() > 0) && (toDate != null) && (toDate.length() > 0)) {
                    sb.append(" and c.").append(colNName).append(" BETWEEN ? AND ?");
                    valuesL.add(fromDate);
                    valuesL.add(toDate);
                }
                
                // 3.Fall: Von leer, Bis gefüllt:  BETWEEN '' AND '2019-06-01'
                if ((fromDate == null || fromDate.length() == 0) && (toDate != null) && (toDate.length() > 0)) {
                    sb.append(" and c.").append(colNName).append(" BETWEEN ? AND ?");
                    valuesL.add("");
                    valuesL.add(toDate);
                }
                
                // 4.Fall: Von gefüllt, Bis leer:  BETWEEN '2019-06-01' AND 'NOW'
                if ((fromDate != null) && (fromDate.length() > 0) && (toDate == null || toDate.length() == 0)){
                    sb.append(" and c.").append(colNName).append(" BETWEEN ? AND ?");
                    valuesL.add(fromDate);
                    valuesL.add("NOW");
                }

            } else {

                if (filterItem != null && filterItem.length() > 0) {
                    sb.append(" and (c.").append(colNName).append(" like ");
                    sb.append(filterItem == null ? "" : "?").append("");
                    sb.append(") ");
                    valuesL.add("%" + filterItem + "%");
                }
            }
        }

        ps.setSql(sb.toString());
        ps.setValues(valuesL.toArray(new String[valuesL.size()]));
        return ps;
    }

    /**
     * Erstellt das Sql-Statement für ein Prepared-Statement, d.h, nur Fragezeichen anstatt Werte.
     * Alle Statements werden über ein AND zusammengesetzt. Eine spätere Funktion muss sich darum
     * kümmern, an die richtige Stelle das "WHERE" zu setzten.
     * 
     * @param List
     *            <String> userFilter
     * @param String
     *            identifier
     * @return String
     */
    public PreparedStatementO createFilterStatement4PS(List<String> userFilter, String identifier) {

        if (userFilter == null || userFilter.size() == 0 || "".equals(userFilter.get(0))
                || "*".equals(userFilter.get(0))) {
            PreparedStatementO ps = new PreparedStatementO();
            ps.setSql("");
            ps.setValues(new String[] {});
            return ps;
        }

        String[] values = new String[userFilter.size()];
        PreparedStatementO ps = new PreparedStatementO();

        StringBuilder sb = new StringBuilder();
        if (userFilter != null) {
            sb.append(" and c.").append(identifier).append(" in (");
            for (int i = 0; i < userFilter.size(); i++) {
                // sb.append(i == 0 ? "where (c." : "or c.");
                // sb.append(identifier).append(" like ");
                // sb.append(i == 0 ? "where c." : "");
                // sb.append(identifier).append(" in ");
                String tmpFilter = userFilter.get(i);
                values[i] = "*".equals(tmpFilter) ? "%" : tmpFilter;
                tmpFilter = "?";
                sb.append(i == 0 ? "" : ",").append(tmpFilter);

                // Extrawurst für die Spalten mit den Nullwerten!
                // if ("*".equals(userFilter.get(i))) {
                // sb.append("or c.").append(identifier).append(" is null");
                // }
            }
            sb.append(")");
        }

        ps.setSql(sb.toString());
        ps.setValues(values);
        return ps;
    }

    @Override
    public void deleteAllRecordsBy(String tableName, String... filterValues) throws DAOException {

        List<String> colNames = getColumNames(tableName);
        StringBuilder sb = new StringBuilder();
        sb.append("delete from ").append(tableName).append(" ");
        for (int i = 0; i < filterValues.length; i++) {
            if (filterValues[i] == null || filterValues[i].length() == 0) {
                continue;
            }
            sb.append(i == 0 ? "where " : "and ");
            sb.append(colNames.get(i)).append(" like '%");
            sb.append(filterValues[i] == null ? "" : filterValues[i]).append("%'");
        }
        sb.append(";");

        executeDeleteStatement(sb.toString(), daoFactory);
    }

    /**
     * Allgemeiner DB-Mapper.
     * 
     * @param Class
     *            <?> clazz, z.B. User.class
     * @param resultSet
     * @return Object
     * @throws SQLException
     */
    public Object mapObject(Class<?> clazz, ResultSet resultSet) throws SQLException {

        Object ustat = null;

        try {
            ustat = clazz.newInstance();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int colnr = rsmd.getColumnCount();
            for (int i = 1; i <= colnr; ++i) {
                String debugColName = rsmd.getColumnName(i).toLowerCase();
                BeanUtils.setProperty(ustat, debugColName, resultSet.getString(debugColName));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ustat;
    }

    public List<String> getDistinctByColName(String tblName, String colName) {

        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<String> val = new ArrayList<String>();

        try {
            connection = daoFactory.getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("select DISTINCT u." + colName + " from " + tblName + " u order by u." + colName + " asc;");
            preparedStatement = connection.prepareStatement(sb.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                val.add(resultSet.getString(colName));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return val;
    }

}
