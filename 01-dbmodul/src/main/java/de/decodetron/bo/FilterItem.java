// $Log: FilterItem.java,v $
// Revision 1.1  2014/02/13 13:05:46  tw
// Benutzerverwaltung, dynamischer Suchfilteraufbau, Schnittstellenanpassung.
//
//

package de.decodetron.bo;

import java.io.Serializable;

/**
 * Trägt die Information auf welche Spalte mit welchem Text gefiltert wird.
 * 
 * @author Thomas Winter
 * @since 13.02.2014
 */
public class FilterItem implements Serializable {

    private String filterIdent;
    private String filterText;

    public FilterItem() {
        this("", "");
    }

    public FilterItem(String filterIdent, String filterText) {
        this.filterIdent = filterIdent;
        this.filterText = filterText;
    }

    /**
     * @return the filterIdent
     */
    public String getFilterIdent() {
        return filterIdent;
    }

    /**
     * @param filterIdent
     *            the filterIdent to set
     */
    public void setFilterIdent(String filterIdent) {
        this.filterIdent = filterIdent;
    }

    /**
     * @return the filterText
     */
    public String getFilterText() {
        return filterText;
    }

    /**
     * @param filterText
     *            the filterText to set
     */
    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }
}
