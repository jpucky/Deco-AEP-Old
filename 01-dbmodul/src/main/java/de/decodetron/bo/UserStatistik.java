// $Log: UserStatistik.java,v $
// Revision 1.1  2014/02/20 03:52:16  tw
// Statistik-Schnittstellen implementiert.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
//

package de.decodetron.bo;

import java.text.SimpleDateFormat;

/**
 * @author Thomas Winter
 * @since 18.02.2014
 */
public class UserStatistik extends BusinessObject {

    private Long id;
    private Long id_user;
    private String ip;
    private String logintime;
    private String logouttime;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Long getId() {
        return this.id;
    }

    public void setId(Long i) {
        this.id = i;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     *            the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the loginTime
     */
    public String getLogintime() {
        return logintime;
    }

    /**
     * Überschriebene "Servicefunktion" die Millis frisst.
     * 
     * @param loginTime
     *            the loginTime to set
     */
    public void setLogintime(Long loginTime) {
        this.logintime = sdf.format(loginTime);
    }

    /**
     * @param loginTime
     *            the loginTime to set
     */
    public void setLogintime(String loginTime) {
        this.logintime = loginTime;
    }

    /**
     * @return the logoutTime
     */
    public String getLogouttime() {
        return logouttime;
    }

    /**
     * @param logoutTime
     *            the logoutTime to set
     */
    public void setLogoutTime(Long logoutTime) {
        this.logouttime = sdf.format(logoutTime);
    }

    /**
     * @param logoutTime
     *            the logoutTime to set
     */
    public void setLogoutTime(String logoutTime) {
        this.logouttime = logoutTime;
    }

    /**
     * @return the id_User
     */
    public Long getId_user() {
        return id_user;
    }

    /**
     * @param id_User the id_User to set
     */
    public void setId_user(Long id_User) {
        this.id_user = id_User;
    }

}
