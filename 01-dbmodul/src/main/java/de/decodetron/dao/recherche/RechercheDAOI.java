// $Log: RechercheDAOI.java,v $
// Revision 1.8  2014/11/23 21:53:43  tw
// https://team.decodetron.de:10443/index.php?c=task&a=view_task&id=3939
//
// Revision 1.7  2014/10/27 14:42:16  tw
// Bugfix in der Recherche Gesamtanzahl / Aufraeumarbeiten.
//
// Revision 1.6  2014/07/30 16:08:55  tw
// Scanbelege, Lucene Objektmapper erstellt, Aufraeumarbeiten.
//
// Revision 1.5  2014/07/29 21:19:12  tw
// Schnittstellenanpassung Scanbelege.
//
// Revision 1.4  2014/06/26 14:15:55  tw
// Serverguard-kram fuer Frank.
//
// Revision 1.3  2014/03/04 16:21:13  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Recherche: Paginierung.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.2  2014/03/03 19:34:55  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Recherche: Paginierung.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.1  2014/03/01 23:58:12  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
//

package de.decodetron.dao.recherche;

import java.util.List;

import de.decodetron.bo.FilterItemList;
import de.decodetron.bo.FundstellePage;
import de.decodetron.bo.Section;
import de.decodetron.bo.User;

/**
 * @author Thomas Winter
 * @since 01.03.2014
 */
public interface RechercheDAOI {

    /**
     * Liefert die Gesamtanzahl aller Datensätze.
     * 
     * @return int
     */
    int getTotalCount();

    FundstellePage getFundstellenPage(User user, Integer hitsPerPage, Long offset, FilterItemList listTxtFields,
            List<String> keyblgart, List<Section> sections, List<String> filterList);

    long countFundstellenPage(User user, Integer hitsPerPage, Long offset, FilterItemList listTxtFields,
            List<String> keyblgart, List<Section> sections, List<String> filterList);

    /**
     * Checkfunktion für Franks überlebens-Abfragetools.
     * 
     * @return boolean
     */
    boolean dbAlive();
}
