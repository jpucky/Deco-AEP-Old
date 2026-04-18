// $Log: BtmDataProvider.java,v $
// Revision 1.20  2020/02/26 19:24:10  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.19  2018/10/14 14:35:04  tw
// Version 1.26-H, BUGFIX: getSection4MenuItem4ModulName wurde abeloest durch getCurrentSection.
//
// Revision 1.18  2014/05/08 20:59:13  tw
// Bugfix: Fehlerhafte Treffermenge wenn Suchergebnis leer.
//
// Revision 1.17  2014/04/28 11:11:15  tw
// Schnittstellen- umzug/umbenennung Filter=>Statistik
//
// Revision 1.16  2014/04/15 15:36:58  tw
// Schnittstellensaeuberung: AEP-Plus-Abverkauf.
//
// Revision 1.15  2014/02/07 02:07:05  tw
// Restzeitaktualisierung bei Aktivitaeten implementiert.
//
// Revision 1.14  2014/02/05 13:47:58  tw
// Anpassung an neue DB-Struktur
//
// Revision 1.13  2014/02/03 16:41:28  tw
// Implementierung Benutzerverwaltung
//
// Revision 1.12  2014/01/29 09:49:30  tw
// Vorbereitung: Belegart: EK / Benutzerverwaltung.
//
// Revision 1.11  2014/01/16 17:01:52  tw
// Listenausgabe als pdf. Abverkauf-Reimporte
//
// Revision 1.10  2013/12/08 17:32:19  tw
// Datepicker-Von, Statistik.
//
// Revision 1.9  2013/12/05 20:10:53  tw
// Bugfix Suche. Performancesteigerung: Defektenliste.
//
// Revision 1.8  2013/12/04 14:17:19  tw
// csv-liste fuer btm implementiert.
//
// Revision 1.7  2013/12/03 20:45:47  tw
// Vorbereitung: .csv, .pdf - button
//
// Revision 1.6  2013/12/02 12:33:53  tw
// Neue Schnittstelle um unnoetige Spalten auszublenden.
//
// Revision 1.5  2013/11/25 14:55:22  tw
// Defektenliste: limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.4  2013/11/25 12:22:15  tw
// Chargendoku: limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.3  2013/11/25 11:02:07  tw
// Limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.2  2013/11/22 00:49:20  tw
// Limitierter Datenausgabe: Anbindung Btm-Liste erster Wurf.
//
// Revision 1.1  2013/11/21 22:12:25  tw
// Limitierter Datenausgabe: Anbindung Btm-Liste erster Wurf.
//
//

package de.decodetron.tab.statistik.btm;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPApplication;
import de.decodetron.Const;
import de.decodetron.bo.DataRecord;
import de.decodetron.bo.FilterItemList;
import de.decodetron.bo.LimitInfo;
import de.decodetron.bo.SortInfo;
import de.decodetron.dao.statistik.StatistikDAOI;
import de.decodetron.data.Util;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.statistik.TxtFilter;

/**
 * 
 * @author Thomas Winter
 * @since 21.11.2013
 */
public class BtmDataProvider extends SortableDataProvider {

    private IModel<ValueMap> model;

    public BtmDataProvider(IModel m) {
        model = m;
        setSort("1", SortOrder.DESCENDING);
    }

    protected StatistikDAOI getContactsDB() {
        return AEPApplication.get().getDBBtmFilter();
    }

    @Override
    public Iterator<DataRecord> iterator(long offset, long count) {
        final SortParam<String> sort = getSort();
        refreshSortInfo(sort.isAscending(), sort.getProperty(), offset, count);
        ValueMap map = (ValueMap) model.getObject();
        return initBTMListe(map).iterator();
    }

    private void refreshSortInfo(Boolean isAscending, String colIndex, long first, long count) {
        if (model != null) {
            ValueMap map = (ValueMap) model.getObject();
            map.put(Const.KEY_LIST_ISASCENDING, isAscending);
            map.put(Const.KEY_LIST_COLNR2SORT, Integer.valueOf(colIndex));
            map.put(Const.KEY_LIST_OFFSET, first);
            //map.put(Const.KEYHITSPERPAGE, size());
            map.put(Const.KEYSHOWPERPAGE, count);
            map.put(Const.KEYDOCTOTAL, getContactsDB().countAllRecords(Const.TABLENAME_BTM));
            model.setObject(map);
        }
    }

    @Override
    public long size() {

        ValueMap map = (ValueMap) model.getObject();
        List<TxtFilter> listTxtFields = (List<TxtFilter>) map.get(Const.KEY_LIST_FILTERSUCHE);
        FilterItemList fList = Util.mapSearchFields(listTxtFields);

        String colName = LoginSession.get().getCurrentSection().getFilteridentifier();
        List<String> filterList = LoginSession.get().getUser().getAllFilter();

        Long cnt = 0L;
        cnt = getContactsDB().countStatistikData(Const.TABLENAME_BTM, colName, filterList, fList);
        // Ist nötig wenn cnt == 0, da der Iterator nicht aktualisiert!
        map.put(Const.KEYHITSPERPAGE, cnt);
        map.put(Const.KEYSHOWPERPAGE, cnt);
        return cnt;
    }

    @Override
    @SuppressWarnings({ "unchecked" })
    public IModel<Serializable> model(Object object) {
        return new Model<Serializable>((Serializable) object);
    }

    /**
     * Holt eine begrenzte Menge (Const.ITEMS_PER_PAGE) an Datensätzen. Die Daten werden per
     * ValueMap zwischen den Komponenten getauscht.
     * 
     * @param ValueMap
     *            map
     * @return List<DataRecord>
     */
    private List<DataRecord> initBTMListe(ValueMap map) {

        String colName = LoginSession.get().getCurrentSection().getFilteridentifier();
        List<String> filterList = LoginSession.get().getUser().getAllFilter();
        Integer sortColNr = (Integer) map.get(Const.KEY_LIST_COLNR2SORT);
        String sortOrder = (Boolean) map.get(Const.KEY_LIST_ISASCENDING) ? "asc" : "desc";
        Long sortOffset = (Long) map.get(Const.KEY_LIST_OFFSET);
        SortInfo sortInfo = new SortInfo(sortOrder, sortColNr);
        LimitInfo limitInfo = new LimitInfo(Const.ITEMS_PER_PAGE, sortOffset);

        List<TxtFilter> listTxtFields = (List<TxtFilter>) map.get(Const.KEY_LIST_FILTERSUCHE);
        FilterItemList fList = Util.mapSearchFields(listTxtFields);

        List<DataRecord> list = getContactsDB().getStatistikData(//
            Const.TABLENAME_BTM,//
            colName,//
            filterList,//
            sortInfo,//
            limitInfo, fList//
                );

        return list;
    }
}
