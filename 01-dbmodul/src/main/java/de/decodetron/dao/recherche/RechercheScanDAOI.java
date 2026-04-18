// $Log: RechercheScanDAOI.java,v $
// Revision 1.3  2014/11/23 21:53:43  tw
// https://team.decodetron.de:10443/index.php?c=task&a=view_task&id=3939
//
// Revision 1.2  2014/10/27 14:42:16  tw
// Bugfix in der Recherche Gesamtanzahl / Aufraeumarbeiten.
//
// Revision 1.1  2014/10/01 11:24:31  tw
// Absplittung, Scanindex vom restlichen Index. Testanbindung.
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
 * @since 01.10.2014
 */
public interface RechercheScanDAOI {

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
