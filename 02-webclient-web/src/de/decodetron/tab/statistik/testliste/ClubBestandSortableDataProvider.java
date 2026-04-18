// $Log: ClubBestandSortableDataProvider.java,v $
// Revision 1.1  2013/11/07 21:58:52  tw
// aufraumen 1. welle.
//
// Revision 1.1  2013/11/05 23:04:05  tw
// Experimente, um CVS-Listen generischer Lesen zu koennen.
//
// Revision 1.2  2013/11/05 12:37:21  tw
// Spaltensortierung f. Clubverkauf implementiert.
//
// Revision 1.1  2013/11/05 03:38:09  tw
// Paginierung begonnen.
//
//

package de.decodetron.tab.statistik.testliste;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.decodetron.bo.ClubBestand;

/** 
 * @author Thomas Winter
 * @since 05.11.2013
 */
@SuppressWarnings("rawtypes")
public class ClubBestandSortableDataProvider extends SortableDataProvider {

    private List<ClubBestand> listClubBestand = null;
    public static final String SORT_DATUM = "Datum";
    public static final String SORT_POSTYP = "PosTyp";
    public static final String SORT_LIEFERANTNR = "LieferantNr";
    public static final String SORT_LIEFERANTNAME = "LieferantName";
    public static final String SORT_PZN = "pzn";
    public static final String SORT_ARTIKELBEZEICHNUNG = "artikelbezeichnung";
    public static final String SORT_CLUBKZ = "clubKz";
    public static final String SORT_BESTAND = "bestand";

    @SuppressWarnings("unchecked")
    public ClubBestandSortableDataProvider(List<ClubBestand> l) {
        listClubBestand = l;
        setSort(SORT_DATUM, SortOrder.DESCENDING);
    }

    @Override
    public Iterator<ClubBestand> iterator(long first, long count) {
        @SuppressWarnings("unchecked")
        SortParam<String> sort = getSort();
        List<ClubBestand> subList = getSortedList(sort).subList((int) first, (int) first + (int) count);
        return subList.iterator();
    }

    private List<ClubBestand> getSortedList(final SortParam<String> sort) {
        List<ClubBestand> l = listClubBestand;
        if (SORT_DATUM.equals(sort.getProperty())) {
            Collections.sort(l, new ClubBestandDatumComparator(sort.isAscending()));
        } else if (SORT_POSTYP.equals(sort.getProperty())) {
            Collections.sort(l, new Comparator<ClubBestand>() {
                public int compare(ClubBestand arg0, ClubBestand arg1) {
                    return (sort.isAscending() ? arg0 : arg1).getPosTyp().compareTo(
                        (sort.isAscending() ? arg1 : arg0).getPosTyp());
                }
            });
        } else if (SORT_LIEFERANTNR.equals(sort.getProperty())) {
            Collections.sort(l, new Comparator<ClubBestand>() {
                public int compare(ClubBestand arg0, ClubBestand arg1) {
                    return (sort.isAscending() ? arg0 : arg1).getLieferantNr().compareTo(
                        (sort.isAscending() ? arg1 : arg0).getLieferantNr());
                }
            });
        } else if (SORT_LIEFERANTNAME.equals(sort.getProperty())) {
            Collections.sort(l, new Comparator<ClubBestand>() {
                public int compare(ClubBestand arg0, ClubBestand arg1) {
                    return (sort.isAscending() ? arg0 : arg1).getLieferantName().compareTo(
                        (sort.isAscending() ? arg1 : arg0).getLieferantName());
                }
            });
        } else if (SORT_PZN.equals(sort.getProperty())) {
            Collections.sort(l, new Comparator<ClubBestand>() {
                public int compare(ClubBestand arg0, ClubBestand arg1) {
                    return (sort.isAscending() ? arg0 : arg1).getPzn().compareTo(
                        (sort.isAscending() ? arg1 : arg0).getPzn());
                }
            });
        } else if (SORT_ARTIKELBEZEICHNUNG.equals(sort.getProperty())) {
            Collections.sort(l, new Comparator<ClubBestand>() {
                public int compare(ClubBestand arg0, ClubBestand arg1) {
                    return (sort.isAscending() ? arg0 : arg1).getArtikelBezeichnung().compareTo(
                        (sort.isAscending() ? arg1 : arg0).getArtikelBezeichnung());
                }
            });
        } else if (SORT_CLUBKZ.equals(sort.getProperty())) {
            Collections.sort(l, new Comparator<ClubBestand>() {
                public int compare(ClubBestand arg0, ClubBestand arg1) {
                    return (sort.isAscending() ? arg0 : arg1).getClubKz().compareTo(
                        (sort.isAscending() ? arg1 : arg0).getClubKz());
                }
            });
        } else if (SORT_BESTAND.equals(sort.getProperty())) {
            Collections.sort(l, new Comparator<ClubBestand>() {
                public int compare(ClubBestand arg0, ClubBestand arg1) {
                    return (sort.isAscending() ? arg0 : arg1).getBestand().compareTo(
                        (sort.isAscending() ? arg1 : arg0).getBestand());
                }
            });
        }

        return l;
    }

    @Override
    public long size() {
        if (listClubBestand != null) {
            return listClubBestand.size();
        } else {
            return 0;
        }
    }

    @Override
    @SuppressWarnings({ "unchecked" })
    public IModel<Serializable> model(Object object) {
        return new Model((Serializable) object);
    }
}
