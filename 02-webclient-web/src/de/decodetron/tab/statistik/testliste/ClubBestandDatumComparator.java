// $Log: ClubBestandDatumComparator.java,v $
// Revision 1.1  2013/11/07 21:58:52  tw
// aufraumen 1. welle.
//
// Revision 1.1  2013/11/05 23:04:05  tw
// Experimente, um CVS-Listen generischer Lesen zu koennen.
//
// Revision 1.1  2013/11/05 03:38:09  tw
// Paginierung begonnen.
//
//

package de.decodetron.tab.statistik.testliste;

import java.util.Comparator;

import de.decodetron.bo.ClubBestand;

/**
 * TODO: Vorsicht, das Ding sortiert nur nach Strings! DB-Seitig muss das als Long daherkommen!
 * 
 * @author Thomas Winter
 * @since 05.11.2013
 */
public class ClubBestandDatumComparator implements Comparator<ClubBestand> {

    private int sortierreihenfolge;
    public static final int ABSTEIGEND = -1;
    public static final int AUFSTEIGEND = 1;

    public ClubBestandDatumComparator() {
        this(true);
    }

    public ClubBestandDatumComparator(boolean ascending) {
        this.sortierreihenfolge = ascending ? AUFSTEIGEND : ABSTEIGEND;
    }

    public int compare(String o1, String o2) {
        int com = o1.compareTo(o2);
        return this.sortierreihenfolge * com;
    }

    @Override
    public int compare(ClubBestand o1, ClubBestand o2) {
        return compare(o1.getDatum(), o2.getDatum());
    }
}
