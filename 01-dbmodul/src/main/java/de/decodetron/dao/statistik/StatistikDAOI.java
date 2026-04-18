// $Log: StatistikDAOI.java,v $
// Revision 1.6  2020/02/28 22:07:35  tw
// Neuer Treiber
//
// Revision 1.5  2020/02/26 19:23:08  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.4  2014/07/23 14:14:24  tw
// Ueberlegungen zur User - Statistikabfrage.
//
// Revision 1.3  2014/04/28 11:15:22  tw
// Schnittstellen- umzug/umbenennung Filter=>Statistik
//
// Revision 1.4  2014/04/15 15:33:20  tw
// Schnittstellensaeuberung: AEP-Plus-Abverkauf.
//
// Revision 1.3  2013/12/09 09:39:21  tw
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

import java.util.List;

import de.decodetron.bo.DataRecord;
import de.decodetron.bo.FilterItemList;
import de.decodetron.bo.LimitInfo;
import de.decodetron.bo.SortInfo;
import de.decodetron.dao.DAOI;

/**
 * @author Thomas Winter
 * @since 02.12.2013
 */
public interface StatistikDAOI extends DAOI {
    
    public List<DataRecord> getStatistikData(String tableName, String filterIdent, List<String> userFilter,
        SortInfo sortInfo, LimitInfo limitInfo, FilterItemList fValues);

    public Long countStatistikData(String tableName, String colName, List<String> userFilter, FilterItemList fValues);


    /**
     * Eine Abfrage könnte so aussehen:<br>
     * <br>
     * 
     * <pre>
     * select
     *       count(*) as cntlogin,      
     *       u.[id_user] as id,
     *       (select uu.[login] from user uu where uu.id = u.[id_user]) as name
     * from
     *     userstatistik u group by u.id_user order by cntlogin desc;
     * </pre>
     * 
     * Zeigt, wer sich am fleissigsten Eingeloggt hat.
     * 
     * @return List<DataRecord>
     */
    public List<DataRecord> showAllUser();
}
