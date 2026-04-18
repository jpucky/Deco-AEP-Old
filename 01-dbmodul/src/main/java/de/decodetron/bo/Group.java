// $Log: Group.java,v $
// Revision 1.2  2014/02/05 13:44:04  tw
// Anpassung an neue DB-Struktur
//
// Revision 1.1  2013/11/21 17:40:01  tw
// Neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.1  2013/11/13 00:29:58  tw
// Servicefunktionen fuer Berechtigungskonzept implementiert.
//
//

package de.decodetron.bo;

/**
 * @author Thomas Winter
 * @since 12.11.2013
 */
public class Group extends BusinessObject {

    private Long id;
    private String name;
    private String kz;
    private String sectionids;

    public Long getId() {
        return this.id;
    }

    public void setId(Long i) {
        this.id = i;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the kz
     */
    public String getKz() {
        return kz;
    }

    /**
     * @param kz
     *            the kz to set
     */
    public void setKz(String kz) {
        this.kz = kz;
    }

    /**
     * @return the sectionids
     */
    public String getSectionids() {
        return sectionids;
    }

    /**
     * @param sectionids the sectionids to set
     */
    public void setSectionids(String sectionids) {
        this.sectionids = sectionids;
    }
}
