// $Log: SortInfo.java,v $
// Revision 1.1  2014/02/28 15:39:49  tw
// Anpassung an neue DB-Struktur: Gebietssleiter.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
//

package de.decodetron.bo;

import java.io.Serializable;

/**
 * Zusammenfassen von Sql - Sortierinformationen. Bietet wahlweise den Spaltenbezeichner (String)
 * oder Spaltenindex (Integer).
 * 
 * @author Thomas Winter
 * @since 28.02.2014
 */
public class SortInfo implements Serializable {

    private String sortOrder;
    private String col2Sort;

    /**
     * Erwartet den Spaltenbezeichner.
     * 
     * @param sortOrder
     * @param colName2Sort
     */
    public SortInfo(String sortOrder, String colName2Sort) {
        super();
        this.sortOrder = sortOrder;
        this.col2Sort = colName2Sort;
    }

    /**
     * Erwartet den Spaltenindex.
     * 
     * @param sortOrder
     * @param colIndex2Sort
     */
    public SortInfo(String sortOrder, Integer colIndex2Sort) {
        super();
        this.sortOrder = sortOrder;
        this.col2Sort = String.valueOf(colIndex2Sort);
    }

    /**
     * @return the colIndex2Sort
     */
    public String getColIndex2Sort() {
        return col2Sort.toString();
    }

    /**
     * @param colIndex2Sort
     *            the colIndex2Sort to set
     */
    public void setColIndex2Sort(String colIndex2Sort) {
        this.col2Sort = colIndex2Sort;
    }

    /**
     * @return the sortOrder
     */
    public String getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder
     *            the sortOrder to set
     */
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
