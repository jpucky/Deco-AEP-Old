// $Log: TierarzneiDataProvider.java,v $
// Revision 1.11  2020/02/26 19:24:12  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.10  2018/10/14 14:35:04  tw
// Version 1.26-H, BUGFIX: getSection4MenuItem4ModulName wurde abeloest durch getCurrentSection.
//
// Revision 1.9  2014/05/08 20:59:13  tw
// Bugfix: Fehlerhafte Treffermenge wenn Suchergebnis leer.
//
// Revision 1.8  2014/04/28 11:11:16  tw
// Schnittstellen- umzug/umbenennung Filter=>Statistik
//
// Revision 1.7  2014/04/15 15:36:58  tw
// Schnittstellensaeuberung: AEP-Plus-Abverkauf.
//
// Revision 1.6  2014/02/05 13:47:58  tw
// Anpassung an neue DB-Struktur
//
// Revision 1.5  2014/01/17 10:35:47  tw
// Listenausgabe als pdf.
//
// Revision 1.4  2013/12/09 14:09:19  tw
// Von-Bis Suchfeld, Statistik.
//
// Revision 1.3  2013/12/05 20:10:53  tw
// Bugfix Suche. Performancesteigerung: Defektenliste.
//
// Revision 1.2  2013/12/02 12:33:54  tw
// Neue Schnittstelle um unnoetige Spalten auszublenden.
//
// Revision 1.1  2013/11/28 13:42:28  tw
// Anbindung Liste:Tierarznei.
//
//

package de.decodetron.tab.statistik.tierarznei;

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
import de.decodetron.bo.Section;
import de.decodetron.bo.SortInfo;
import de.decodetron.dao.statistik.StatistikDAOI;
import de.decodetron.data.Util;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.statistik.TabStatistik;
import de.decodetron.tab.statistik.TxtFilter;

/**
 * @author Thomas Winter
 * @since 28.11.2013
 */
public class TierarzneiDataProvider extends SortableDataProvider {

    private IModel<ValueMap> model;
    private List<DataRecord> list;

    public TierarzneiDataProvider(IModel m) {
        model = m;
        setSort("1", SortOrder.DESCENDING);
    }

    protected StatistikDAOI getContactsDB() {
        return AEPApplication.get().getDBTierarzneiFilter();
    }

    public Iterator<DataRecord> iterator(long offset, long count) {
        final SortParam<String> sort = getSort();
        refreshSortInfo(sort.isAscending(), sort.getProperty(), offset, count);
        ValueMap map = (ValueMap) model.getObject();
        return initTierarzneiListe(map).iterator();
    }

    private void refreshSortInfo(Boolean isAscending, String colIndex, long first, long count) {
        if (model != null) {
            ValueMap map = (ValueMap) model.getObject();
            map.put(Const.KEY_LIST_ISASCENDING, isAscending);
            map.put(Const.KEY_LIST_COLNR2SORT, Integer.valueOf(colIndex));
            map.put(Const.KEY_LIST_OFFSET, first);
            //map.put(Const.KEYHITSPERPAGE, size());
            map.put(Const.KEYSHOWPERPAGE, count);
            map.put(Const.KEYDOCTOTAL, getContactsDB().countAllRecords(Const.TABLENAME_TIERARZNEI));
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
        cnt = getContactsDB().countStatistikData(Const.TABLENAME_TIERARZNEI, colName, filterList, fList);
        // Ist nötig wenn cnt == 0, da der Iterator nicht aktualisiert!
        map.put(Const.KEYHITSPERPAGE, cnt);
        map.put(Const.KEYSHOWPERPAGE, cnt);
        return cnt;
    }

    private List<DataRecord> initTierarzneiListe(ValueMap map) {

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
            Const.TABLENAME_TIERARZNEI,//
            colName,//
            filterList,//
            sortInfo,//
            limitInfo, fList//
                );

        return list;
    }

    @Override
    @SuppressWarnings({ "unchecked" })
    public IModel<Serializable> model(Object object) {
        return new Model<Serializable>((Serializable) object);
    }

}
