// $Log: StatistikUserDAOI.java,v $
// Revision 1.1  2014/04/15 15:33:20  tw
// Schnittstellensaeuberung: AEP-Plus-Abverkauf.
//
// Revision 1.1  2014/02/20 03:52:16  tw
// Statistik-Schnittstellen implementiert.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
//

package de.decodetron.dao.statistikuser;

import de.decodetron.bo.UserStatistik;
import de.decodetron.dao.DAOI;
import de.decodetron.exc.DAOException;

/**
 * @author Thomas Winter
 * @since 19.02.2014
 */
public interface StatistikUserDAOI extends DAOI {

    public void insertUserStatistik(UserStatistik uStat) throws IllegalArgumentException, DAOException;

    public void updateUserStatistik(UserStatistik uStat) throws IllegalArgumentException, DAOException;

    public void deleteUserStatistik(UserStatistik uStat) throws IllegalArgumentException, DAOException;

    public UserStatistik getStatistik4UserId(Long userId) throws IllegalArgumentException, DAOException;
}
