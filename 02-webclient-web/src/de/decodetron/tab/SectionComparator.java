// $Log: SectionComparator.java,v $
// Revision 1.2  2014/05/02 09:51:50  tw
// Backup: Benutzer anlegen.
//
// Revision 1.1  2014/02/07 15:44:08  tw
// Menueitems werden aus db gelesen.
//
//

package de.decodetron.tab;

import java.util.Comparator;

import de.decodetron.bo.Section;

/**
 * Sortiert Sections nach MenueitemId oder Menuename. Defaulteinstellung ist: Menuename
 * 
 * @author Thomas Winter
 * @since 07.02.2014
 */
public class SectionComparator implements Comparator<Section> {

    private int sortstate;
    private int sortierreihenfolge;
    public static final int ABSTEIGEND = -1;
    public static final int AUFSTEIGEND = 1;

    public static final int SORTBY_SECTIONNAME = 1;
    public static final int SORTBY_MENUEITEMID = 2;

    public SectionComparator() {
        this(true, SORTBY_SECTIONNAME);
    }

    public SectionComparator(int st) {
        this(true, st);
    }

    public SectionComparator(boolean ascending, int st) {
        sortstate = st;
        this.sortierreihenfolge = ascending ? AUFSTEIGEND : ABSTEIGEND;
    }

    public int compare(String o1, String o2) {
        int com = o1.compareTo(o2);
        return this.sortierreihenfolge * com;
    }

    public int compare(Integer o1, Integer o2) {
        int com = o1.compareTo(o2);
        return this.sortierreihenfolge * com;
    }

    @Override
    public int compare(Section o1, Section o2) {
        switch (sortstate) {
            case SORTBY_SECTIONNAME: {
                return compare(o1.getSection(), o2.getSection());
            }
            case SORTBY_MENUEITEMID: {
                return compare(Integer.valueOf(o1.getMenuitemid()), Integer.valueOf(o2.getMenuitemid()));
            }
            default: {
                return compare(o1.getSection(), o2.getSection());
            }
        }
    }
}
