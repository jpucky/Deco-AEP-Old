// $Log: SessionController.java,v $
// Revision 1.7  2017/06/21 19:44:16  tw
// Mobilmachung der Headrevision.
//
// Revision 1.6  2015/02/20 20:22:54  tw
// Vorbereitung f. spaetere User-On-Time.db Absplittung aus decoUser.db.
//
// Revision 1.5  2015/01/23 17:19:11  tw
// Einstellung f. CD-Produktion.
//
// Revision 1.4  2014/02/21 00:44:01  tw
// Statistik-Schnittstellen implementiert.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.3  2014/02/20 03:52:27  tw
// Statistik-Schnittstellen implementiert.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.2  2014/02/04 02:09:21  tw
// Implementierung Administrationsbereich.
//
// Revision 1.1  2014/02/02 23:22:09  tw
// Implementierung einer Uebersicht aller angemeldeten Benutzer.
//
//

package de.decodetron.security;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.wicket.Session;

import de.decodetron.AEPApplication;
import de.decodetron.bo.User;
import de.decodetron.bo.UserStatistik;

/**
 * Ursprünglich als Listener geplant. LoginSession bringt jedoch von Hause aus eine onInvalidate -
 * Methode mit sich, was den Listener erübrigt. Die Session-Id dient als Key.
 * 
 * @author Thomas Winter
 * @since 02.02.2014
 */
public class SessionController { // implements HttpSessionListener {

    private static Logger log = Logger.getLogger(SessionController.class);
    private static Map<String, UserInfoBundle> loggedUser = (HashMap<String, UserInfoBundle>) new HashMap<String, UserInfoBundle>();

    public static void putUserInfo(String key, UserInfoBundle value) {
        loggedUser.put(key, value);
        showSessionStore();
    }

    public static void removeUserInfo(Object key) {

        if (loggedUser.containsKey(key)) {
            UserInfoBundle uib = loggedUser.get(key);
            loggedUser.remove(key);

            // DB-Eintrag ...
            if (Session.exists()) {
                Long uid = LoginSession.get().getUser().getId();
                UserStatistik uStat = AEPApplication.get().getDBUserOnTime().getStatistik4UserId(uid);
                if (uStat != null) {
                    uStat.setLogoutTime(new Date().getTime());
                    // TODO: CD-PROD
                    AEPApplication.get().getDBUserOnTime().updateUserStatistik(uStat);
                }
            } else {
                // Dann darf ich auch kein DB-Aufruf mehr starten !!! TODO: ???
            }
        }

        showSessionStore();
    }

    public static List<String> getKeyList() {
        return new ArrayList<String>(loggedUser.keySet());
    }

    public static List<UserInfoBundle> getUserInfoList() {
        Set<String> keyset = loggedUser.keySet();
        List<UserInfoBundle> infoList = new ArrayList<UserInfoBundle>();
        for (Iterator<String> iterator = keyset.iterator(); iterator.hasNext();) {
            infoList.add(loggedUser.get(iterator.next()));
        }
        return infoList;
    }

    public static void showSessionStore() {
        Set keyset = loggedUser.keySet();
        log.debug("####################################################");
        if (keyset.isEmpty()) {
            log.debug("# Alle Benutzer abgemeldet!                        #");
            log.debug("####################################################");
            return;
        }

        for (Iterator<String> iterator = keyset.iterator(); iterator.hasNext();) {
            String key = iterator.next();
            StringBuffer b = new StringBuffer();
            UserInfoBundle info = loggedUser.get(key);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
            b.append("User eingeloggt: ").append(info.getUser().getLogin());
            b.append(", Session-Id: ").append(key);
            b.append(", Seit      : ").append(sdf.format(new Date(info.getLoginSince())));
            b.append(", IP-Adresse: ").append(info.getIpAdress());
            log.debug(b.toString());
        }
        log.debug("####################################################");
    }
}
