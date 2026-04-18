// $Log: FilterItemList.java,v $
// Revision 1.2  2014/03/30 10:39:36  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.1  2014/02/13 13:05:46  tw
// Benutzerverwaltung, dynamischer Suchfilteraufbau, Schnittstellenanpassung.
//
//

package de.decodetron.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author Thomas Winter
 * @since 13.02.2014
 */
public class FilterItemList implements Serializable {

    List<FilterItem> list = null;

    public FilterItemList() {
        list = new ArrayList<FilterItem>();
    }

    public void add(FilterItem itm) {
        list.add(itm);
    }
    
    public List<FilterItem> getFilterItems(){
        return list;
    }
    
    public int getSize(){
        return list.size();
    }
}
