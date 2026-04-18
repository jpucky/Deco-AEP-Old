// $Log: SectionInfo.java,v $
// Revision 1.2  2014/10/28 22:44:41  tw
// Testanbindung Xml-Objektparsing, UserSectionData.
//
// Revision 1.1  2014/08/12 01:03:47  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
//

package de.decodetron.bo;

import java.io.Serializable;

/**
 * Speichert, ob eine Sektion gesetzt ist und deren Gruppierung/Level.
 * 
 * @author Thomas Winter
 * @since 11.08.2014
 */
public class SectionInfo implements Serializable {

    private Integer level = -1;
    private Boolean isSet = Boolean.FALSE;

    public SectionInfo(){
        // Test
    }
    
    public SectionInfo(Integer level, Boolean isSet) {
        super();
        this.level = level;
        this.isSet = isSet;
    }

    /**
     * @return the level
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * @param level
     *            the level to set
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * @return the isSet
     */
    public Boolean getIsSet() {
        return isSet;
    }

    /**
     * @param isSet
     *            the isSet to set
     */
    public void setIsSet(Boolean isSet) {
        this.isSet = isSet;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SectionInfo [level=" + level + ", isSet=" + isSet + "]";
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((isSet == null) ? 0 : isSet.hashCode());
        result = prime * result + ((level == null) ? 0 : level.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SectionInfo other = (SectionInfo) obj;
        if (isSet == null) {
            if (other.isSet != null)
                return false;
        } else if (!isSet.equals(other.isSet))
            return false;
        if (level == null) {
            if (other.level != null)
                return false;
        } else if (!level.equals(other.level))
            return false;
        return true;
    }

}
