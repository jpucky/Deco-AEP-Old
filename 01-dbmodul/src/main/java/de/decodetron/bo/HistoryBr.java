// $Log: HistoryBr.java,v $
// Revision 1.2  2014/11/04 16:40:00  tw
// Testanbindung: History-DB Benutzerrechte.
//
// Revision 1.1  2014/11/03 16:57:06  tw
// Testanbindung: History-DB Benutzerrechte.
//
//

package de.decodetron.bo;

import java.text.SimpleDateFormat;

/**
 * @author Thomas Winter
 * @since 31.10.2014
 */
public class HistoryBr extends BusinessObject {

    private Long id;
    private Long id_user;
    private String aktion;
    private String timestamp;
    private String xmlUserData;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public final static String AKTION_ANLEGEN = "I";
    public final static String AKTION_AENDERN = "U";
    public final static String AKTION_LOESCHEN = "D";

    public Long getId() {
        return this.id;
    }

    public void setId(Long i) {
        this.id = i;
    }

    /**
     * @return the loginTime
     */
    public String getTimeStamp() {
        return timestamp;
    }

    /**
     * Überschriebene "Servicefunktion" die Millis frisst.
     * 
     * @param loginTime
     *            the loginTime to set
     */
    public void setTimeStamp(Long loginTime) {
        this.timestamp = sdf.format(loginTime);
    }

    public void setTimeStamp(String loginTime) {
        this.timestamp = loginTime;
    }

    /**
     * Die User-Id des angemeldeten Benutzers. Des Benutzers der die Änderungen durchführt.
     * 
     * @return the id_User
     */
    public Long getId_user() {
        return id_user;
    }

    /**
     * Die User-Id des angemeldeten Benutzers. Des Benutzers der die Änderungen durchführt.
     * 
     * @param id_User
     *            the id_User to set
     */
    public void setId_user(Long id_User) {
        this.id_user = id_User;
    }

    /**
     * @return the xmlUserData
     */
    public String getXmlUserData() {
        return xmlUserData;
    }

    /**
     * @param xmlUserData
     *            the xmlUserData to set
     */
    public void setXmlUserData(String xmlUserData) {
        this.xmlUserData = xmlUserData;
    }

    /**
     * @return the aktion
     */
    public String getAktion() {
        return aktion;
    }

    /**
     * @param aktion
     *            the aktion to set
     */
    public void setAktion(String aktion) {
        this.aktion = aktion;
    }

}
