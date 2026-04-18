// $Log: UserSectionDataProvider.java,v $
// Revision 1.2  2014/10/13 10:16:39  tw
// Beim Blaettern werden geaenderte Benutzer angezeigt.
//
// Revision 1.1  2014/08/22 09:22:28  tw
// Umzug.
//
// Revision 1.3  2014/08/18 12:34:34  tw
// Bugfix: Aep-Benutzer-Rechteverwaltung.
//
// Revision 1.2  2014/08/11 12:32:07  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.1  2014/08/08 16:05:21  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
//

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPApplication;
import de.decodetron.AEPDataProvider;
import de.decodetron.AEPModel;
import de.decodetron.Const;
import de.decodetron.bo.FilterItemList;
import de.decodetron.bo.LimitInfo;
import de.decodetron.bo.SortInfo;
import de.decodetron.bo.UserSectionData;
import de.decodetron.dao.user.UserDAOI;
import de.decodetron.data.Util;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.benutzerverwaltung.DetachableUserSectionModel;
import de.decodetron.tab.statistik.TxtFilter;

/**
 * Der Dataprovider befüllt neben seiner regulären Aufgabe zusätzlich die Liste: "UserListOriginal"
 * für einen späteren Vergleich.
 * 
 * @author Thomas Winter
 * @since 08.08.2014
 */
public class UserSectionDataProvider extends AEPDataProvider {

    private IModel<AEPModel> model;

    public UserSectionDataProvider(IModel<AEPModel> m) {
        model = m;
        setSort("anlagedatum", SortOrder.DESCENDING);
    }

    protected UserDAOI getContactsDB() {
        return AEPApplication.get().getDBUser();
    }

    @Override
    public Iterator<UserSectionData> iterator(long offset, long count) {

        final SortParam<String> sort = getSort();
        refreshSortInfo(sort.isAscending(), sort.getProperty(), offset, count);

        ValueMap map = ((ValueMap) model.getObject());
        String sortOrder = sort.isAscending() ? "asc" : "desc";

        List<UserSectionData> ulist = initUserListe((ValueMap) model.getObject());
        HashMap<Long, UserSectionData> hm = model.getObject().getBenVerwaltungModel().getUserList2Change();
        replaceChangedUser(ulist, hm);
        // Alle geladenen Benutzer werden gesammelt, für einen späteren Vergleich ob Daten geändert
        // wurden.
        // LinkedHashSet<UserSectionData> userList = (LinkedHashSet<UserSectionData>) map
        // .get(Const.KEY_USER_SECTION_ORIGINAL);
        model.getObject().getBenVerwaltungModel().getUserListOriginal().addAll(ulist);

        return ulist.iterator();
    }

    /**
     * Wenn sich geänderte Benutzer in der Liste befinden müssen diese auch zur Anzeige gebracht
     * werden. Dient vor allem einem komfortableren Blättern.
     * 
     * @param List
     *            <UserSectionData> olist
     * @param HashMap
     *            <Long, UserSectionData> changeList
     */
    private void replaceChangedUser(List<UserSectionData> olist, HashMap<Long, UserSectionData> changeList) {

        Set<Entry<Long, UserSectionData>> set = changeList.entrySet();
        for (Iterator<Entry<Long, UserSectionData>> iterator = set.iterator(); iterator.hasNext();) {
            Entry<Long, UserSectionData> entry = iterator.next();
            Long userKey = entry.getKey();
            UserSectionData cUser = entry.getValue();
            int idx = findUsergetIndex(olist, userKey);
            if (idx != -1) {
                olist.set(idx, cUser);
            }
        }
    }

    /**
     * Die Benutzerobjekte sind underschiedlich. Daher ist die interne indexOf Funkion unbrauchbar.
     * Der Vergleich muss über die userId erfolgen.
     * 
     * @param List
     *            <UserSectionData> olist
     * @param Long
     *            userKey
     * @return int
     */
    private int findUsergetIndex(List<UserSectionData> olist, Long userKey) {
        int index = -1;
        for (Iterator<UserSectionData> iterator = olist.iterator(); iterator.hasNext();) {
            index++;
            UserSectionData user = iterator.next();
            if (user.getId().longValue() == userKey.longValue()) {
                return index;
            }
        }
        return -1;
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
            AEPModel map = (AEPModel) model.getObject();
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

    public List<UserSectionData> initUserListe(ValueMap map) {

        String sortColName = (String) map.get(Const.KEY_LIST_COL_NAME_2SORT);
        String sortOrder = (Boolean) map.get(Const.KEY_LIST_ISASCENDING) ? "asc" : "desc";
        Long sortOffset = (Long) map.get(Const.KEY_LIST_OFFSET);

        List<TxtFilter> listTxtFields = (List<TxtFilter>) map.get(Const.KEY_LIST_FILTERSUCHE);
        FilterItemList fList = Util.mapSearchFields(listTxtFields);

        SortInfo sortInfo = new SortInfo(sortOrder, sortColName);
        LimitInfo limitInfo = new LimitInfo(Const.ITEMS_PER_PAGE_SMALL, sortOffset);

        return getContactsDB().getUserSection4Verwaltung(//
            LoginSession.get().getUser(),//
            sortInfo,//
            limitInfo,//
            fList);
    }

    @Override
    public IModel<UserSectionData> model(Object object) {
        return new DetachableUserSectionModel((UserSectionData) object);
    }
}
