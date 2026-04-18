// $Log: BusinessObject.java,v $
// Revision 1.1  2013/11/21 17:40:01  tw
// Neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.1  2013/11/02 21:46:42  tw
// ClubBestand-DB-Schicht. 1. Schritte zur Veralgemeinerung.
//
// Revision 1.1  2013/08/07 01:46:51  tw
// AEP LoginModul als simpelst-keine-Zusatzbibliotheken-Variante. Erster Wurf.
//
//

package de.decodetron.bo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Siehe auch: http://www.ibm.com/developerworks/java/tutorials/j-lessismore/section3.html.
 * 
 * @author Thomas Winter
 * @since 14.02.2012
 */
public abstract class BusinessObject extends Object implements Serializable {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BusinessObject) {
            BusinessObject businessObject = (BusinessObject) obj;
            if (businessObject.getId() != null && businessObject.getId() > 0) {
                return new EqualsBuilder().append(businessObject.getId(), this.getId()).isEquals();
            } else {
                super.equals(obj);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (getId() != null && getId() > 0) {
            return new HashCodeBuilder().append(getId()).toHashCode();
        } else {
            return super.hashCode();
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public abstract Long getId();

    public abstract void setId(Long id);

    public Date getTimestamp() {return null;}

    public void setTimestamp(Date timestamp) {}
}
