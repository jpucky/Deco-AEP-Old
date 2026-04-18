// $Log: UserInfoBundle.java,v $
// Revision 1.2  2014/02/04 02:09:21  tw
// Implementierung Administrationsbereich.
//
// Revision 1.1  2014/02/02 23:22:09  tw
// Implementierung einer Uebersicht aller angemeldeten Benutzer.
//
//

package de.decodetron.security;

import java.io.Serializable;

import de.decodetron.bo.User;

/**
 * Datentonne für Userinformationen.
 * 
 * @author Thomas Winter
 * @since 02.02.2014
 */
public class UserInfoBundle implements Serializable {

    public UserInfoBundle(User user, String ipAdress, Long loginSince, String sessionID) {
        super();
        this.user = user;
        this.ipAdress = ipAdress;
        this.loginSince = loginSince;
        this.sessionID = sessionID;
    }

    private User user;
    private String ipAdress;
    private Long loginSince;
    private String sessionID;

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the ipAdress
     */
    public String getIpAdress() {
        return ipAdress;
    }

    /**
     * @param ipAdress
     *            the ipAdress to set
     */
    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }

    /**
     * @return the loginSince
     */
    public Long getLoginSince() {
        return loginSince;
    }

    /**
     * @param loginSince
     *            the loginSince to set
     */
    public void setLoginSince(Long loginSince) {
        this.loginSince = loginSince;
    }

    /**
     * @return the sessionID
     */
    public String getSessionID() {
        return sessionID;
    }

    /**
     * @param sessionID
     *            the sessionID to set
     */
    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
}
