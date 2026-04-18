// $Log: UserDateCheck.java,v $
// Revision 1.2  2014/01/24 13:39:07  tw
// Schnittstellen aufgeraeumt. Neue limitierte Benutzerschnittstelle.
//
// Revision 1.1  2013/11/21 17:40:02  tw
// Neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.1  2013/11/17 13:42:00  tw
// Neue Schnittstelle f. User mit mehrfachfiltern implementiert.
//
//

package de.decodetron;

import junit.framework.TestCase;
import de.decodetron.bo.User;
import de.decodetron.dao.user.UserDAOI;
import de.decodetron.fac.DAOFactoryJDBC;

/**
 * @author Thomas Winter
 * @since 14.11.2013
 */
public class UserDateCheck extends TestCase {

    private DAOFactoryJDBC dbBaseUser;
    
    @Override
    protected void setUp() throws Exception {
        String dbLocation;
        dbLocation = "D:/Daten/Dokumente/Entwicklung/Programmierung/Java/03_Decode-Projekte/Deco-AEPDirekt/AEPJUNIT/decoUser.db";
        dbBaseUser = DAOFactoryJDBC.getInstance(dbLocation);
    }
    
    /**
     * Kleine Nebenbaustelle, da das Datum beim Update verlorengeht.OFS
     */
    public void testUserDate(){
        UserDAOI userDAO = dbBaseUser.getUserDAO();
        User u = userDAO.findById(5L);
        System.out.println(u.getAnlagedatum());
    }
}
