package de.decodetron.dao.statistik.clubbes;

import java.util.List;

import org.apache.log4j.Logger;

import de.decodetron.bo.DataRecord;
import de.decodetron.bo.FilterItemList;
import de.decodetron.bo.LimitInfo;
import de.decodetron.bo.PreparedStatementO;
import de.decodetron.bo.SortInfo;
import de.decodetron.dao.statistik.StatistikDAO;
import de.decodetron.fac.DAOFactoryJDBC;
import de.decodetron.util.Util;

/**
 * 
 * 
 * 09.2026: U.a. die Bestands-Tabelle fängt in der Produktion an zu lahmen. Caching I/O Prozesse scheinen u.a. dafür
 * Verantwortlich zu sein. Der Zusatz: "INDEXED BY idxTagesDatum" brachte in ersten Versuchen Besserung. Diese Klasse
 * planzt diesen Zusatz in das SQL mit ein.
 */
public class DAOClubBes extends StatistikDAO implements DAOIClubBes {

	private DAOFactoryJDBC daoFactory;
    private static Logger logDB = Logger.getLogger("LOGFILE");
    private final static String TAGESDATUM = "idxTagesdatum";
    private final static String LIEFERANTENNR = "idxLieferantNr";
    private final static String INDEXED_BY_TAGESDATUM = "INDEXED BY " + TAGESDATUM;
    private final static String INDEXED_BY_LIEFERANTENNR = "INDEXED BY " + LIEFERANTENNR;

	public DAOClubBes(DAOFactoryJDBC df) {
		super(df);
		this.daoFactory = df;
	}
	
    @Override
    public List<DataRecord> getClubBestandData(String tableName, String filterIdent, List<String> userFilter,
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
        
        if(indexExists(daoFactory, TAGESDATUM)){
        	sb.append(INDEXED_BY_TAGESDATUM);
        }

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

        long startTime = System.currentTimeMillis(); 
        List<DataRecord> ldr = executeSQLStatement4PS(all, daoFactory);
		logDB.debug("stm, tbl : " + tableName + " : " + (int) ((System.currentTimeMillis() - startTime) / 1000)
				+ " [s], filter: " + userFilter + " filter2: " + getStringArrayItems(filterValues));
        logDB.debug("--------------------------------------------------------------------------");
        
        return ldr;
    }
    
    @Override
    public Long countClubBestandData(String tableName, String colName, List<String> userFilter, FilterItemList fValues) {

        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from ").append(tableName).append(" c ");

        // ///////////////////////////////////////////////////////////////////////
        // TEXTFELDEINGABEN
        //
        List<String> colNames = getColumNames(tableName);
        String[] filterValues = toArray(colNames, fValues.getFilterItems());
        PreparedStatementO ps1 = createSQLFilterValues4PS(colNames, filterValues);
        sb.append(ps1.getSql());
        
        if(indexExists(daoFactory, LIEFERANTENNR)){
            sb.append(INDEXED_BY_LIEFERANTENNR);	
        }

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
        
        long startTime = System.currentTimeMillis();
        Long value = executeSQLCountStatemet4PS(all, daoFactory);
		logDB.debug("cnt, tbl : " + tableName + " : " + (int) ((System.currentTimeMillis() - startTime) / 1000)
				+ " [s], filter: " + userFilter + " filter2: " + getStringArrayItems(filterValues));
        return value;
    }

}
