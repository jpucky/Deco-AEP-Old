// $Log: DAODefekte.java,v $
// Revision 1.11  2020/03/09 22:31:04  tw
// Version 1.32-H
// -----------------------------
// CR: Umstellung der Statements um Einschraenkung per Datum vornehmen zu koennen.
// BUGFIX: Datums-Suche AEP-Plus (CLUBBESTAND) / Valuta korrigiert.
//
// Revision 1.10  2020/02/26 19:23:08  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.9  2015/07/21 00:32:09  tw
// CR Feng-ID: 3952. Grundfunktionalitaet erstellt.
//
// Revision 1.8  2014/05/12 20:55:25  tw
// Aufraeumarbeiten, Benutzer anlegen loeschen.
//
// Revision 1.7  2014/05/09 21:00:32  tw
// Change Request: Doppelte Defektenlisten v. 09.05.2014. (2) gilt nur fuer Lieferanten.
//
// Revision 1.6  2014/05/09 20:49:27  tw
// Change Request: Doppelte Defektenlisten v. 09.05.2014. (2) gilt nur f�r Lieferanten.
//
// Revision 1.5  2014/05/09 11:12:57  tw
// Change Request: Doppelte Defektenlisten v. 09.05.2014.
//
// Revision 1.4  2014/05/07 21:25:09  tw
// Change Request: Doppelte Defektenlisten.
//
// Revision 1.3  2014/05/07 17:21:52  tw
// Change Request: Doppelte Defektenlisten.
//
// Revision 1.2  2014/05/04 20:22:48  tw
// Benutzer anlegen: Neue Schnittstelle: getDistinctByColName
//
// Revision 1.1  2014/04/28 11:15:22  tw
// Schnittstellen- umzug/umbenennung Filter=>Statistik
//
// Revision 1.8  2014/04/15 19:57:04  tw
// Schnittstellensaeuberung: AEP-Plus-Abverkauf.
//
// Revision 1.7  2014/04/15 15:33:20  tw
// Schnittstellensaeuberung: AEP-Plus-Abverkauf.
//
// Revision 1.6  2014/04/15 00:15:39  tw
// SQL-Injection sichere Verarbeitung (nur f. Defekentlisten!).
//
// Revision 1.5  2014/04/01 00:59:38  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.4  2014/03/31 21:45:46  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.3  2014/03/30 10:39:36  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.2  2014/03/28 23:02:33  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.1  2014/03/28 17:22:33  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
//

package de.decodetron.dao.statistik.defekte;

import static de.decodetron.util.DAOUtil.close;
import static de.decodetron.util.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import de.decodetron.bo.DataRecord;
import de.decodetron.bo.FilterItem;
import de.decodetron.bo.FilterItemList;
import de.decodetron.bo.LimitInfo;
import de.decodetron.bo.PreparedStatementO;
import de.decodetron.bo.SortInfo;
import de.decodetron.bo.User;
import de.decodetron.dao.statistik.StatistikDAO;
import de.decodetron.exc.DAOException;
import de.decodetron.fac.DAOFactoryJDBC;
import de.decodetron.util.Util;

/**
 * 
 * @author Thomas Winter
 * @since 28.03.2014
 */
public class DAODefekte extends StatistikDAO implements DAOIDefekte {

    public final static String TABLENAME_DEFEKTE = "DEFEKTE";
    public final static String COL_KUNDENNR = "KundenNr";
    public final static String COL_AUFTRAGSNR = "AuftragsNr";
    public final static String COL_BETRAG = "Betrag";
    public final static String COL_LIEFERANT = "LieferantNr";
    private final static String IGNORE_DUPLIKATES_EXTRAWURST = " group by c.AuftragDatum, c.KundenNr, c.PZN ";

    private DAOFactoryJDBC daoFactory;

    public DAODefekte(DAOFactoryJDBC df) {
        super(df);
        this.daoFactory = df;
    }

    @Override
    public DataRecord getDefekteColNames(User user) {

        DataRecord columNames = new DataRecord();
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(createSelect(user));
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
    public List<DataRecord> getDefekteData(//
            User user,//
            String colName,//
            SortInfo sortInfo,//
            LimitInfo limitInfo,//
            FilterItemList listTxtFields) {

        StringBuilder sb = new StringBuilder();
        sb.append(createSelect(user));

        List<String> colNames = getDefekteColNames(user).getList();
        String[] filterValues = toArray(colNames, listTxtFields.getFilterItems());
        
        // ///////////////////////////////////////////////////////////////////////
        // USERFILTER
        //
        PreparedStatementO ps1 = createSQLFilterValues4PS(colNames, filterValues);
        sb.append(ps1.getSql());

        // ///////////////////////////////////////////////////////////////////////
        // / Durchiterieren der Textfelder
        // /
        PreparedStatementO ps2 = createFilterStatement4PS(user.getAllFilter(), colName);
        //createWhereClauseIfNecessary(ps1, ps2);    
        sb.append(ps2.getSql());
        if (user.getIsLieferant()) {
            sb.append(IGNORE_DUPLIKATES_EXTRAWURST); // IGNORE-DUPLICATES
        }

        // ///////////////////////////////////////////////////////////
        // /// Limitierung und Sortierung
        String debug = createLimitAndSortSQL(sortInfo, limitInfo, colNames);
        sb.append(" ").append(debug);
        sb.append(";");

        PreparedStatementO all = new PreparedStatementO();
        String check = sb.toString().replaceFirst(" and", " where");
        all.setSql(check);
        all.setValues(Util.concatAllArrays(ps1.getValues(), ps2.getValues()));
        
        List<DataRecord> dr = executeSQLStatement4PS(all, daoFactory);
        return dr;
    }

    @Override
    public long countDefekteData(//
            User user,//
            String colName,//
            FilterItemList listTxtFields) {

        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from ");
        if (user.getIsLieferant()) {
            sb.append("("); // IGNORE-DUPLICATES
            sb.append("select * from "); // IGNORE-DUPLICATES
        }
        // Die Variante ist nicht schneller !
        // sb.append("select distinct c.AuftragDatum, c.KundenNr, c.AuftragsNr, c.PZN, c.Preis from ");
        sb.append(TABLENAME_DEFEKTE).append(" c ");

        List<String> colNames = getDefekteColNames(user).getList();
        String[] filterValues = toArray(colNames, listTxtFields.getFilterItems());
        
        // ///////////////////////////////////////////////////////////////////////
        // USERFILTER
        //
        PreparedStatementO ps1 = createSQLFilterValues4PS(colNames, filterValues);
        sb.append(ps1.getSql());

        // ///////////////////////////////////////////////////////////////////////
        // / Durchiterieren der Textfelder
        // /

        PreparedStatementO ps2 = createFilterStatement4PS(user.getAllFilter(), colName);
        sb.append(ps2.getSql());
        if (user.getIsLieferant()) {
            sb.append(IGNORE_DUPLIKATES_EXTRAWURST); // IGNORE-DUPLICATES
            sb.append(")");
        }

        PreparedStatementO all = new PreparedStatementO();
        all.setSql(sb.toString().replaceFirst(" and", " where"));
        all.setValues(Util.concatAllArrays(ps1.getValues(), ps2.getValues()));

        long recordCount = executeSQLCountStatemet4PS(all, daoFactory);
        return recordCount;
    }

    /**
     * Erstellt das Sql-Statement:
     * 
     * <pre>
     *   select [colname] from [tablename] u.
     * </pre>
     * 
     * Leute die keine Superadmins sind, dürfen die Spalten: GruppenId und Filter nicht sehen!
     * 
     * @param User
     *            user
     * @param String
     *            tableName
     * @return String
     */
    private String createSelect(User user) {

        List<String> tblName = getColumNames(TABLENAME_DEFEKTE);
        if (user.getIsLieferant()) {
            tblName.remove(COL_BETRAG);
            tblName.remove(COL_KUNDENNR);
            tblName.remove(COL_LIEFERANT);
            tblName.remove(COL_AUFTRAGSNR);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        for (int i = 0; i < tblName.size(); i++) {
            String colNameTmp = tblName.get(i);
            sb.append(colNameTmp);
            sb.append((i >= 0) && (i < tblName.size() - 1) ? ", " : "");
        }
        sb.append(" from ");
        sb.append(TABLENAME_DEFEKTE);
        sb.append(" c ");
        return sb.toString();
    }

    public void createStatementWithoutValue(){
        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from defekte c;");        
        Object[] values = new Object[] {};
        
        Long recordCount = 0L;
        int dataRecordCounter = 0;
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            //preparedStatement = connection.prepareStatement(sb.toString());
            preparedStatement = prepareStatement(connection, sb.toString(), false, values);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                recordCount = Long.valueOf(resultSet.getString(++dataRecordCounter));
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement);
        }
    }
    
    /**
     * Hardcodierte prepared-Statement Testvariante.
     */
    @Override
    public long countDefekteDataTest() {

        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from DEFEKTE c");
        sb.append(" where (");
        sb.append(" c.KundenNr like ? or");
        sb.append(" c.KundenNr like ? or");
        sb.append(" c.KundenNr like ? or");
        sb.append(" c.KundenNr like ? or");
        sb.append(" c.KundenNr like ? or");
        sb.append(" c.KundenNr like ? ) and ");
        sb.append(" c.AuftragDatum >= ? and ");
        sb.append(" c.AuftragDatum <= ? and ");
        sb.append(" ( c.AuftragsNr like ? or c.AuftragsNr is ?");
        sb.append(")");
        String[] values = new String[] { "%%", "00574", "11950", "05074", "31005", "123", "2014-01-01", "2014-01-30",
                "%890%", null };
        // String[] values = new String[]{};

        Long recordCount = 0L;
        int dataRecordCounter = 0;
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sb.toString(), false, (Object[]) values);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                recordCount = Long.valueOf(resultSet.getString(++dataRecordCounter));
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement);
        }

        return recordCount;
    }
}
