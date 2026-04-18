// $Log: StatistikDAO.java,v $
// Revision 1.9  2020/03/09 22:31:04  tw
// Version 1.32-H
// -----------------------------
// CR: Umstellung der Statements um Einschraenkung per Datum vornehmen zu koennen.
// BUGFIX: Datums-Suche AEP-Plus (CLUBBESTAND) / Valuta korrigiert.
//
// Revision 1.8  2020/02/28 20:30:09  tw
// Alte Funktionen entfernt. System.out. f. Debugzwecke erstellt.
//
// Revision 1.7  2020/02/26 19:23:08  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.6  2015/07/21 00:32:09  tw
// CR Feng-ID: 3952. Grundfunktionalitaet erstellt.
//
// Revision 1.5  2014/07/23 14:14:24  tw
// Ueberlegungen zur User - Statistikabfrage.
//
// Revision 1.4  2014/04/28 11:15:22  tw
// Schnittstellen- umzug/umbenennung Filter=>Statistik
//
// Revision 1.13  2014/04/15 15:33:20  tw
// Schnittstellensaeuberung: AEP-Plus-Abverkauf.
//
// Revision 1.12  2014/04/15 00:15:39  tw
// SQL-Injection sichere Verarbeitung (nur f. Defekentlisten!).
//
// Revision 1.11  2014/03/30 10:39:36  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.10  2014/03/28 23:02:33  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.9  2014/03/28 17:22:33  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.8  2014/03/27 01:20:43  tw
// Vorbereitung, CR-Defektenlisten f. Lieferanten: Markup-freie Tabellengestaltung.
//
// Revision 1.7  2014/02/28 15:39:49  tw
// Anpassung an neue DB-Struktur: Gebietssleiter.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.6  2014/01/17 21:40:13  tw
// Kommentare ergaenzt.
//
// Revision 1.5  2014/01/14 16:37:50  tw
// Bufix: Beseitigung aller KZs.
//
// Revision 1.4  2013/12/09 09:39:21  tw
// Neue Schnittstelle: Von-bis Datum Statistik
//
// Revision 1.3  2013/12/08 11:30:46  tw
// Neue Schnittstelle: Von-bis Datum Statistik
//
// Revision 1.2  2013/12/07 13:20:05  tw
// Neue Schnittstelle: Von-bis Datum Statistik
//
// Revision 1.1  2013/12/02 12:31:08  tw
// Neue Schnittstelle um unnoetige Spalten auszublenden.
//
//

package de.decodetron.dao.statistik;

import static de.decodetron.util.DAOUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import de.decodetron.bo.DataRecord;
import de.decodetron.bo.FilterItem;
import de.decodetron.bo.FilterItemList;
import de.decodetron.bo.LimitInfo;
import de.decodetron.bo.PreparedStatementO;
import de.decodetron.bo.SortInfo;
import de.decodetron.dao.DAO;
import de.decodetron.exc.DAOException;
import de.decodetron.fac.DAOFactoryJDBC;
import de.decodetron.util.Util;

/**
 * Allgemeine Schnittstelle, um die Daten ohne die Spalten DokumentTyp u. PositionsTyp zu liefern.
 * 
 * @author Thomas Winter
 * @since 02.12.2013
 */
public class StatistikDAO extends DAO implements StatistikDAOI {

    private DAOFactoryJDBC daoFactory;
    private static Logger log = Logger.getLogger(DAO.class);

    public static final String COL_DOKUMENTTYP = "DokumentTyp";
    public static final String COL_POSITIONSTYP = "PositionsTyp";
    public static final String COL_CLUBKZ = "ClubKz";

    public StatistikDAO(DAOFactoryJDBC df) {
        super(df);
        daoFactory = df;
    }

    /**
     * Überschriebene Methode aus DAOFilter. Spalten:<br>
     * 1. DokumentTyp<br>
     * 2. PositionsTyp<br>
     * 3. ClubKz<br>
     * entfernt, wenn vorhanden.
     */
    public List<String> getColumNames(String tableName) {
        List<String> columNames = super.getColumNames(tableName);
        List<String> removeCol = new ArrayList<String>();
        removeCol.add(COL_DOKUMENTTYP);
        removeCol.add(COL_POSITIONSTYP);
        removeCol.add(COL_CLUBKZ);
        columNames.removeAll(removeCol);
        return columNames;
    }

    @Override
    public DataRecord getColumNamesAsDataRecord(String tableName) {

        DataRecord columNames = new DataRecord();
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(createSelectStart(tableName));
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int colNr = rsmd.getColumnCount();
            // HochPreiserFieldMapper map = (HochPreiserFieldMapper) colMap.get(tableName);
            // List<String> colNames4View = map.getColumnNames4View();
            // int colNr = colNames4View.size(); // <= !!!
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

    public Long countStatistikData(String tableName, String colName, List<String> userFilter, FilterItemList fValues) {

        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from ").append(tableName).append(" c ");

        // ///////////////////////////////////////////////////////////////////////
        // TEXTFELDEINGABEN
        //
        List<String> colNames = getColumNames(tableName);
        String[] filterValues = toArray(colNames, fValues.getFilterItems());
        PreparedStatementO ps1 = createSQLFilterValues4PS(colNames, filterValues);
        sb.append(ps1.getSql());

        // ///////////////////////////////////////////////////////////////////////
        // USERFILTER
        //
        PreparedStatementO ps2 = createFilterStatement4PS(userFilter, colName);
        sb.append(ps2.getSql());
        sb.append(";");

        // ///////////////////////////////////////////////////////////////////////
        // Alle Statements zusammensetzen
        //
        PreparedStatementO all = new PreparedStatementO();
        all.setSql(sb.toString().replaceFirst(" and", " where"));
        all.setValues(Util.concatAllArrays(ps1.getValues(), ps2.getValues()));

        return executeSQLCountStatemet4PS(all, daoFactory);
    }

    @Override
    public List<DataRecord> getStatistikData(String tableName, String filterIdent, List<String> userFilter,
            SortInfo sortInfo, LimitInfo limitInfo, FilterItemList fValues) {
        
        // ///////////////////////////////////////////////////////////////////////
        // select [colname] from [tablename]
        //
        StringBuilder sb = new StringBuilder();
        sb.append(createSelectStart(tableName));

        // ///////////////////////////////////////////////////////////////////////
        // TEXTFELDEINGABEN
        //
        List<String> colNames = getColumNames(tableName);
        String[] filterValues = toArray(colNames, fValues.getFilterItems());
        PreparedStatementO ps1 = createSQLFilterValues4PS(colNames, filterValues);
        sb.append(ps1.getSql());        

        // ///////////////////////////////////////////////////////////////////////
        // USERFILTER
        //
        PreparedStatementO ps2 = createFilterStatement4PS(userFilter, filterIdent);
        sb.append(ps2.getSql());

        // ///////////////////////////////////////////////////////////
        // /// Limitierung und Sortierung
        sb.append(" ").append(createLimitAndSortSQL(sortInfo, limitInfo, colNames));
        sb.append(";");

        PreparedStatementO all = new PreparedStatementO();
        all.setSql(sb.toString().replaceFirst(" and", " where"));
        all.setValues(Util.concatAllArrays(ps1.getValues(), ps2.getValues()));

        return (executeSQLStatement4PS(all, daoFactory));
    }

    /**
     * Erzeugt das Limitierende Sql-Statement und die Sortierung.
     * 
     * @return String
     */
    public String createLimitAndSortSQL(SortInfo sortI, LimitInfo limitI, List<String> colNames) {

        int DEFAULTOFFSET = 0;
        int DEFAULTCOLINDEX = 0;
        int DEFAULTLIMIT = 1000;
        String DEFAULTSORTORDER = "asc";
        StringBuilder sb = new StringBuilder();

        if ((sortI.getColIndex2Sort() != null) && (sortI.getSortOrder() != null) && (limitI.getLimit() != null)) {
            sb.append("order by c.");
            sb.append(colNames.get(Integer.valueOf(sortI.getColIndex2Sort()))).append(" ");
            sb.append(sortI.getSortOrder()).append(" ");
            sb.append("limit").append(" ");
            sb.append(limitI.getLimit()).append(" ");
            sb.append("offset").append(" ");
            sb.append(limitI.getOffset()).append(" ");
        } else {
            sb.append("order by c.");
            sb.append(colNames.get(DEFAULTCOLINDEX)).append(" ");
            sb.append(DEFAULTSORTORDER).append(" ");
            sb.append("limit").append(" ");
            sb.append(DEFAULTLIMIT).append(" ");
            sb.append("offset").append(" ");
            sb.append(DEFAULTOFFSET).append(" ");
        }

        return sb.toString();
    }

    /**
     * Erstellt das Sql-Statement:
     * 
     * <pre>
     *   select [colname] from [tablename] c.
     * </pre>
     * 
     * Es werden die existierenden Spalten der Tabelle berücksichtigt.
     * 
     * @param String
     *            tableName
     * @return String
     */
    public String createSelectStart(String tableName) {

        List<String> tblName = getColumNames(tableName);
        // tblName.removeAll(removeCol);

        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        // sb.append("*"); // <=
        for (int i = 0; i < tblName.size(); i++) {
            String colNameTmp = tblName.get(i);
            sb.append(colNameTmp);
            sb.append((i >= 0) && (i < tblName.size() - 1) ? ", " : "");
        }
        sb.append(" from ");
        sb.append(tableName);
        sb.append(" c ");
        return sb.toString();
    }


    /**
     * Bläst die FilterItem-Liste wieder auf eine String-Liste auf, damit die alte
     * createSQl-Funktion noch benutzt werden kann. Die Filteritems müssen dabei der Reihenfolge der
     * Texteingabefeldern entsprechen.
     * 
     * @param List
     *            <String> colNames
     * @param List
     *            <FilterItem> fi
     * @return String[]
     */
    public String[] toArray(List<String> colNames, List<FilterItem> fi) {

        int offset = 0;
        HashMap<Integer, String> hm = new HashMap<Integer, String>();
        hm.put(0, "von");
        hm.put(1, "bis");
        String[] s = new String[colNames.size() + 1]; // datum von bis!

        for (int i = 0; i < colNames.size() + 1; i++) {
            if (i >= colNames.size()) {
                continue;
            }
            String colName = colNames.get(i);
            if (colName.toLowerCase().endsWith("datum")) {
                s[i + offset] = getFilterText4Ident(fi, colName + hm.get(offset++)); // von
                s[i + offset] = getFilterText4Ident(fi, colName + hm.get(offset)); // bis
            } else {
                s[i + offset] = getFilterText4Ident(fi, colName);
            }
        }
        return s;
    }

    private String getFilterText4Ident(List<FilterItem> fi, String ident) {
        String s = "";
        for (Iterator<FilterItem> iterator = fi.iterator(); iterator.hasNext();) {
            FilterItem filterItem = iterator.next();
            if (filterItem.getFilterIdent().toLowerCase().startsWith(ident.toLowerCase())) {
                s = filterItem.getFilterText();
                break;
            }
        }
        return s;
    }
    
    @Override
    public List<DataRecord> showAllUser() {
        // TODO Auto-generated method stub
        return null;
    }

}
