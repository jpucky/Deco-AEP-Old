package de.decodetron.dao.statistik.clubbes;

import java.util.List;

import de.decodetron.bo.DataRecord;
import de.decodetron.bo.FilterItemList;
import de.decodetron.bo.LimitInfo;
import de.decodetron.bo.SortInfo;
import de.decodetron.dao.statistik.StatistikDAOI;

public interface DAOIClubBes extends StatistikDAOI {

    public List<DataRecord> getClubBestandData(String tableName, String filterIdent, List<String> userFilter,
            SortInfo sortInfo, LimitInfo limitInfo, FilterItemList fValues);
    
    public Long countClubBestandData(String tableName, String colName, List<String> userFilter, FilterItemList fValues);
}
