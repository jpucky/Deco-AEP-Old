// $Log: FundstellePage.java,v $
// Revision 1.2  2014/03/04 16:21:12  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Recherche: Paginierung.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.1  2014/03/03 19:34:55  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Recherche: Paginierung.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
//

package de.decodetron.bo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Winter
 * @since 03.03.2014
 */
public class FundstellePage extends BusinessObject {

    private int currentPageNo;
    private int start;
    private int end;
    private int totalCount;
    private int hitsCount;
    private int pageSize;
    private int pageCount;
    private List<Fundstelle> fsList;

    // default size 10
    public FundstellePage(int currentPageNo) {
        this(currentPageNo, 10);
    }

    public FundstellePage(int currentPageNo, int defaultPageSize) {
        this.currentPageNo = currentPageNo;
        this.pageSize = defaultPageSize;
        fsList = new ArrayList<Fundstelle>();
    }

    public int getPageCount() {
        pageCount = hitsCount / this.pageSize;
        if (hitsCount % this.pageSize == 0)
            return pageCount;
        else
            return pageCount + 1;

    }

    public int getStart() {
        start = (currentPageNo - 1) * this.pageSize;
        return start;
    }

    public int getEnd() {
        end = getStart() + getPageSize();
        if (this.hitsCount > 0 && end > this.hitsCount) {
            end = hitsCount;
        }
        return end;
    }

    public int getItemsShown(){
        return getEnd() - getStart();
    }

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public long getHitsCount() {
        return hitsCount;
    }

    public void setHitsCount(int totalCount) {
        this.hitsCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    @Override
    public Long getId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setId(Long id) {
        // TODO Auto-generated method stub

    }

    public void addFundstelle(Fundstelle fs){
        fsList.add(fs);
    }
    
    /**
     * @return the fsList
     */
    public List<Fundstelle> getFsList() {
        return fsList;
    }

    /**
     * @param fsList
     *            the fsList to set
     */
    public void setFsList(List<Fundstelle> fsList) {
        this.fsList = fsList;
    }

    /**
     * @return the totalCount
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount the totalCount to set
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
