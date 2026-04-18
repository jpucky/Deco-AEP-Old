// $Log: LimitInfo.java,v $
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
 * Zusammenfassen von Sql - LimitierungsInformationen.
 * 
 * @author Thomas Winter
 * @since 28.02.2014
 */
public class LimitInfo implements Serializable {

    private Integer limit;
    private Long offset;

    public LimitInfo(Integer limit, Long offset) {
        super();
        this.limit = limit;
        this.offset = offset;
    }

    /**
     * @return the limit
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * @param limit
     *            the limit to set
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * @return the offset
     */
    public Long getOffset() {
        return offset;
    }

    /**
     * @param offset
     *            the offset to set
     */
    public void setOffset(Long offset) {
        this.offset = offset;
    }
}
