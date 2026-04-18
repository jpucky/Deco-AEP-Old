// $Log: Section.java,v $
// Revision 1.3  2014/02/05 13:47:33  tw
// Anpassung an neue DB-Struktur
//
// Revision 1.2  2014/02/05 13:44:04  tw
// Anpassung an neue DB-Struktur
//
// Revision 1.1  2013/11/21 17:40:01  tw
// Neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.3  2013/11/14 01:34:42  tw
// aaaaaaaaaaaaaaargh umlaute.
//
// Revision 1.2  2013/11/13 23:13:20  tw
// Vorbereitung: Schnittstellen fuer Datenfilter.
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
public class Section extends BusinessObject {

    private Long id;
    private String menuitemid;
    private String menuitem;
    private String section;
    private String subsection;
    private String kz;
    private String filteridentifier;
    private String location;
    private String database;
    private String table;


    /**
     * @return the name
     */
    public String getSection() {
        return section;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setSection(String name) {
        this.section = name;
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
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the dataBase
     */
    public String getDatabase() {
        return database;
    }

    /**
     * @param dataBase
     *            the dataBase to set
     */
    public void setDatabase(String dataBase) {
        this.database = dataBase;
    }

    /**
     * @return the table
     */
    public String getTable() {
        return table;
    }


    /**
     * @param table
     *            the table to set
     */
    public void setTable(String table) {
        this.table = table;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long i) {
        this.id = i;
    }

    /**
     * @return the filteridentifier
     */
    public String getFilteridentifier() {
        return filteridentifier;
    }

    /**
     * @param filteridentifier the filteridentifier to set
     */
    public void setFilteridentifier(String filteridentifier) {
        this.filteridentifier = filteridentifier;
    }

    /**
     * @return the menuitemid
     */
    public String getMenuitemid() {
        return menuitemid;
    }

    /**
     * @param menuitemid the menuitemid to set
     */
    public void setMenuitemid(String menuitemid) {
        this.menuitemid = menuitemid;
    }

    /**
     * @return the menuitem
     */
    public String getMenuitem() {
        return menuitem;
    }

    /**
     * @param menuitem the menuitem to set
     */
    public void setMenuitem(String menuitem) {
        this.menuitem = menuitem;
    }

    /**
     * @return the subsection
     */
    public String getSubsection() {
        return subsection;
    }

    /**
     * @param subsection the subsection to set
     */
    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

}
