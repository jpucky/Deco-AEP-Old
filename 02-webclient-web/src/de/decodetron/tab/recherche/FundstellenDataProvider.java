// $Log: FundstellenDataProvider.java,v $
// Revision 1.19  2016/01/31 17:00:14  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.18  2016/01/19 23:15:06  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.17  2016/01/12 21:26:22  tw
// CR 3956: Interner Umbau: Lucene-Felder. Rueckbau d. Textfeld-Id Verlaengerung.
//
// Revision 1.16  2016/01/11 22:50:35  tw
// CR 3956: Interner Umbau: Lucene-Felder.
//
// Revision 1.15  2014/11/24 11:41:14  tw
// Schnittstellenanpassung: https://team.decodetron.de:10443/index.php?c=task&a=view_task&id=3939
//
// Revision 1.14  2014/10/27 14:46:54  tw
// Bugfix in der Recherche Gesamtanzahl / Aufraeumarbeiten.
//
// Revision 1.13  2014/10/14 20:25:35  tw
// Scanningbelege werden nur aus dem neuen index genommen, wenn er existiert.
//
// Revision 1.12  2014/10/03 12:01:37  tw
// Lieferantenname Textfeldanbindung.
//
// Revision 1.11  2014/10/02 12:36:52  tw
// Lieferantenname Textfeldanbindung.
//
// Revision 1.10  2014/10/01 22:02:53  tw
// Lieferantenname Textfeldanbindung.
//
// Revision 1.9  2014/07/31 14:10:16  tw
// Scanbelege, Oberflaechenapassungen.
//
// Revision 1.8  2014/07/29 21:19:58  tw
// Schnittstellenanpassung Scanbelege.
//
// Revision 1.7  2014/07/18 14:11:37  tw
// Recherche: Bugfix Fundstellen wenn null.
//
// Revision 1.6  2014/05/12 16:11:14  tw
// Aufraeumarbeiten, Benutzer anlegen loeschen.
//
// Revision 1.5  2014/03/20 02:22:22  tw
// Recherche: Paginierung, Funktionstest. Event-Konstanten zusammengefasst.
//
// Revision 1.4  2014/03/04 16:21:48  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Recherche: Paginierung.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.3  2014/03/03 19:29:51  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Recherche: Paginierung.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.2  2014/03/02 00:10:46  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Recherche: Paginierung.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.1  2014/03/02 00:00:28  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Recherche: Paginierung.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
//

package de.decodetron.tab.recherche;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPApplication;
import de.decodetron.AEPModel;
import de.decodetron.Const;
import de.decodetron.bo.FilterItem;
import de.decodetron.bo.FilterItemList;
import de.decodetron.bo.Fundstelle;
import de.decodetron.bo.FundstellePage;
import de.decodetron.dao.recherche.RechercheDAOI;
import de.decodetron.dao.recherche.RechercheScanDAOI;
import de.decodetron.data.Util;
import de.decodetron.event.ChangeEvent;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.statistik.TxtFilter;

/**
 * @author Thomas Winter
 * @since 01.03.2014
 */
public class FundstellenDataProvider extends SortableDataProvider<Fundstelle, String> {

    private ValueMap vm;

    public FundstellenDataProvider(ValueMap m) {
        vm = m;
    }

    protected RechercheDAOI getContactsDB() {
        return AEPApplication.get().getDBRecherche();
    }

    protected RechercheScanDAOI getContactsDBScan() {
        return AEPApplication.get().getDBRechercheScan();
    }

    @Override
    public Iterator<Fundstelle> iterator(long offset, long count) {

        Object o = vm.get(Const.KEY_SELECTED_BELEGART);
        List<String> keyblgart = o == null ? new ArrayList<String>() : (List<String>) o;
        //List<TxtFilter> ltxt = (List<TxtFilter>) vm.get(Const.KEY_LIST_FILTERSUCHE);
        List<TxtFilter> ltxt = ((AEPModel)vm).getRechercheModel().getSuchItems();
        FilterItemList flist = Util.mapSearchFields(ltxt);

        FundstellePage page = null;
        if ((Util.isScanBeleg(vm)) && (getContactsDBScan() != null)) {
            page = getContactsDBScan().getFundstellenPage(LoginSession.get().getUser(), Const.ITEMS_PER_PAGE, offset,
                flist, keyblgart, LoginSession.get().getSections4MenuItem(TabRecherche.MENUEITEM),
                LoginSession.get().getUser().getAllFilter());
        } else {
            page = getContactsDB().getFundstellenPage(LoginSession.get().getUser(), Const.ITEMS_PER_PAGE, offset,
                flist, keyblgart, LoginSession.get().getSections4MenuItem(TabRecherche.MENUEITEM),
                LoginSession.get().getUser().getAllFilter());

        }

        // VORSICHT HACK !!! Damit Gerds Zahlen wieder stimmen. Es
        // Gesamtanzahl Belege
        int totCnt = getContactsDB().getTotalCount();
        int totCntScan = getContactsDBScan().getTotalCount();
        vm.put(Const.KEYDOCTOTAL, Long.valueOf(totCnt + totCntScan));
        vm.put(Const.KEYHITSPERPAGE, Long.valueOf(page.getHitsCount()));
        vm.put(Const.KEYSHOWPERPAGE, Long.valueOf(page.getItemsShown())); // SHOWPERPAGE
        
        return page.getFsList().iterator();
    }

    @Override
    public long size() {

        Object blgart = vm.get(Const.KEY_SELECTED_BELEGART);
        List<String> keyblgart = blgart == null ? new ArrayList<String>() : (List<String>) blgart;
        //List<TxtFilter> ltxt = (List<TxtFilter>) vm.get(Const.KEY_LIST_FILTERSUCHE);
        List<TxtFilter> ltxt = ((AEPModel)vm).getRechercheModel().getSuchItems();
        FilterItemList flist = Util.mapSearchFields(ltxt);
        
        long cnt = 0;
        if ((Util.isScanBeleg(vm)) && (getContactsDBScan() != null)) {
            cnt = getContactsDBScan().countFundstellenPage(LoginSession.get().getUser(), Const.ITEMS_PER_PAGE, 0L,
                flist, keyblgart, LoginSession.get().getSections4MenuItem(TabRecherche.MENUEITEM),
                LoginSession.get().getUser().getAllFilter());;
        } else {
            cnt = getContactsDB().countFundstellenPage(LoginSession.get().getUser(), Const.ITEMS_PER_PAGE, 0L, flist,
                keyblgart, LoginSession.get().getSections4MenuItem(TabRecherche.MENUEITEM),
                LoginSession.get().getUser().getAllFilter());
        }

        // Ist nötig wenn cnt == 0, da der Iterator nicht aktualisiert!
        vm.put(Const.KEYHITSPERPAGE, cnt);
        vm.put(Const.KEYSHOWPERPAGE, cnt);        
        return cnt;
    }
    
    @Override
    public IModel<Fundstelle> model(Fundstelle object) {
        return new DetachableFundstelleModel(object);
    }
}
