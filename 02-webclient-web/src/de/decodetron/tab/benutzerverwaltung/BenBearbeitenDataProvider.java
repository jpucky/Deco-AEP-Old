// $Log: BenBearbeitenDataProvider.java,v $
// Revision 1.2  2014/07/18 22:17:05  tw
// Layoutkorrektur.
//
// Revision 1.1  2014/06/28 16:08:51  tw
// Benutzer bearbeiten finalisiert. Busy-Indicator f. Statistik u. Benutzer Bearbeiten.
//
// Revision 1.3  2014/05/30 12:22:11  tw
// Pflegemassnahmen.
//
// Revision 1.2  2014/05/13 01:19:43  tw
// Dataprovider Cache - Optimierungen.
//
// Revision 1.1  2014/05/08 21:01:16  tw
// Testblasen.
//
//

package de.decodetron.tab.benutzerverwaltung;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPApplication;
import de.decodetron.AEPDataProvider;
import de.decodetron.Const;
import de.decodetron.bo.FilterItemList;
import de.decodetron.bo.LimitInfo;
import de.decodetron.bo.SortInfo;
import de.decodetron.bo.User;
import de.decodetron.dao.user.UserDAOI;
import de.decodetron.data.Util;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.statistik.TxtFilter;

/**
 * Test für den size-cache.
 * 
 * @author Thomas Winter
 * @since 08.05.2014
 */
public class BenBearbeitenDataProvider extends AEPDataProvider {

    private IModel<ValueMap> model;

    public BenBearbeitenDataProvider(IModel<ValueMap> m) {
        model = m;
        setSort("anlagedatum", SortOrder.DESCENDING);
    }

    protected UserDAOI getContactsDB() {
        return AEPApplication.get().getDBUser();
    }

    @Override
    public Iterator<User> iterator(long offset, long count) {

        final SortParam<String> sort = getSort();
        refreshSortInfo(sort.isAscending(), sort.getProperty(), offset, count);

        ValueMap map = ((ValueMap) model.getObject());
        String sortOrder = sort.isAscending() ? "asc" : "desc";

        List<User> ulist = initUserListe((ValueMap) model.getObject());
        // map.put(Const.KEYUSERLIST, ulist);

        return ulist.iterator();
    }

    /**
     * Setzt die Sortierinformationen Systemweit u.a für Suche-Starten.
     * 
     * @param Boolean
     *            isAscending
     * @param String
     *            colIndex
     * @param long first
     */
    private void refreshSortInfo(Boolean isAscending, String colName, long first, long pp) {
        if (model != null) {
            ValueMap map = (ValueMap) model.getObject();
            map.put(Const.KEY_LIST_ISASCENDING, isAscending);
            // map.put(Const.KEY_LIST_COLNR2SORT, Integer.valueOf(colIndex));
            map.put(Const.KEY_LIST_COL_NAME_2SORT, colName);
            map.put(Const.KEY_LIST_OFFSET, first);
            map.put(Const.KEYHITSPERPAGE, size());
            map.put(Const.KEYSHOWPERPAGE, pp);
            model.setObject(map);
        }
    }

    @Override   
    public long aepSize() {

        ValueMap map = (ValueMap) model.getObject();
        List<TxtFilter> listTxtFields = (List<TxtFilter>) map.get(Const.KEY_LIST_FILTERSUCHE);
        FilterItemList fList = Util.mapSearchFields(listTxtFields);

        long cnt = getContactsDB().countUser4Verwaltung(LoginSession.get().getUser(), fList);
        if (fList.getFilterItems().isEmpty()) {
            // Initialdurchlauf
            map.put(Const.KEYDOCTOTAL, cnt);
        }
        // Ist nötig wenn cnt == 0, da der Iterator nicht aktualisiert!
        map.put(Const.KEYHITSPERPAGE, cnt);
        map.put(Const.KEYSHOWPERPAGE, cnt);

        return cnt;
    }

    private List<User> initUserListe(ValueMap map) {

        String sortColName = (String) map.get(Const.KEY_LIST_COL_NAME_2SORT);
        String sortOrder = (Boolean) map.get(Const.KEY_LIST_ISASCENDING) ? "asc" : "desc";
        Long sortOffset = (Long) map.get(Const.KEY_LIST_OFFSET);

        List<TxtFilter> listTxtFields = (List<TxtFilter>) map.get(Const.KEY_LIST_FILTERSUCHE);
        FilterItemList fList = Util.mapSearchFields(listTxtFields);

        SortInfo sortInfo = new SortInfo(sortOrder, sortColName);
        LimitInfo limitInfo = new LimitInfo(Const.ITEMS_PER_PAGE, sortOffset);

        return getContactsDB().getUser4Verwaltung(//
            LoginSession.get().getUser(),//
            sortInfo,//
            limitInfo,//
            fList);
    }

    @Override
    public IModel<User> model(Object object) {
        return new DetachableUserModel((User) object);
    }
}
