// $Log: DAOClub.java,v $
// Revision 1.3  2020/03/09 22:31:04  tw
// Version 1.32-H
// -----------------------------
// CR: Umstellung der Statements um Einschraenkung per Datum vornehmen zu koennen.
// BUGFIX: Datums-Suche AEP-Plus (CLUBBESTAND) / Valuta korrigiert.
//
// Revision 1.2  2020/02/26 19:23:08  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.1  2015/07/21 00:33:18  tw
// CR Feng-ID: 3952. Grundfunktionalitaet erstellt.
//
//

package de.decodetron.dao.statistik.club;

import java.util.ArrayList;
import java.util.List;

import de.decodetron.bo.LimitInfo;
import de.decodetron.bo.PreparedStatementO;
import de.decodetron.bo.SortInfo;
import de.decodetron.dao.statistik.StatistikDAO;
import de.decodetron.exc.DAOException;
import de.decodetron.fac.DAOFactoryJDBC;
import de.decodetron.util.Util;

/**
 * @author Thomas Winter
 * @since 20.07.2015
 */
public class DAOClub extends StatistikDAO implements DAOIClub {

    private final static String AEP_BETRAG = "Orig_AEP_BET";
    private DAOFactoryJDBC daoFactory;

    public DAOClub(DAOFactoryJDBC df) {
        super(df);
        this.daoFactory = df;
    }

    @Override
    public List<String> getColumNames(String tableName) {
        List<String> colName = super.getColumNames(tableName);
        // colName.add(AEP_BETRAG);
        return colName;
    }

    @Override
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
        sb.append(", ROUND(Menge*Original_AEP, 2) AS ").append(AEP_BETRAG);
        sb.append(" from ");
        sb.append(tableName);
        sb.append(" c ");
        return sb.toString();
    }

//    /**
//     * <pre>
//     * select count(*), ROUND(Menge*Original_AEP, 2) AS Original_AEP_BET
//     * from ABVERKAUF c
//     * where (c.LieferantNr like '%' or c.LieferantNr is null)
//     * and (Original_AEP_BET like '%1.9%')
//     * order by c.AuftragDatum desc limit 250 offset 0;
//     * </pre>
//     */
//    public Long countStatistikData(String tableName, String colName, List<String> userFilter, String... filterValues)
//            throws DAOException {
//
//        List<String> colNames = getColumNames(tableName);
//        // colNames.removeAll(removeCol);
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("select count(*)");
//        sb.append(", ROUND(Menge*Original_AEP, 2) AS ").append(AEP_BETRAG);
//        sb.append(" from ").append(tableName).append(" c ");
//
//        // ///////////////////////////////////////////////////////////////////////
//        // TEXTFELDEINGABEN
//        //
//        PreparedStatementO ps1 = createSQLFilterValues4PS(colNames, filterValues);
//        sb.append(ps1.getSql());
//
//        // ///////////////////////////////////////////////////////////////////////
//        // USERFILTER
//        //
//        PreparedStatementO ps2 = createFilterStatement4PS(userFilter, colName);
//        sb.append(ps2.getSql());
//        sb.append(";");
//        
//        PreparedStatementO all = new PreparedStatementO();
//        all.setSql(sb.toString().replaceFirst(" and", " where"));
//        all.setValues(Util.concatAllArrays(ps1.getValues(), ps2.getValues()));
//
//        return executeSQLCountStatemet4PS(all, daoFactory);
//    }

    @Override
    public PreparedStatementO createSQLFilterValues4PS(List<String> colNames, String... filterValues) {
        colNames.add(AEP_BETRAG);

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
                    sb.append(" and (").append(colNName).append(" like ");
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

    
    
    @Override
    public String createLimitAndSortSQL(SortInfo sortI, LimitInfo limitI, List<String> colNames) {
        int DEFAULTOFFSET = 0;
        int DEFAULTCOLINDEX = 0;
        int DEFAULTLIMIT = 1000;
        String DEFAULTSORTORDER = "asc";
        StringBuilder sb = new StringBuilder();

        colNames.add(AEP_BETRAG);

        if ((sortI.getColIndex2Sort() != null) && (sortI.getSortOrder() != null) && (limitI.getLimit() != null)) {
            sb.append("order by ");
            sb.append(colNames.get(Integer.valueOf(sortI.getColIndex2Sort()))).append(" ");
            sb.append(sortI.getSortOrder()).append(" ");
            sb.append("limit").append(" ");
            sb.append(limitI.getLimit()).append(" ");
            sb.append("offset").append(" ");
            sb.append(limitI.getOffset()).append(" ");
        } else {
            sb.append("order by ");
            sb.append(colNames.get(DEFAULTCOLINDEX)).append(" ");
            sb.append(DEFAULTSORTORDER).append(" ");
            sb.append("limit").append(" ");
            sb.append(DEFAULTLIMIT).append(" ");
            sb.append("offset").append(" ");
            sb.append(DEFAULTOFFSET).append(" ");
        }

        return sb.toString();
    }

}
