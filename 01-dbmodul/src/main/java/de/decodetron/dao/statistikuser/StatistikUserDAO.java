// $Log: StatistikUserDAO.java,v $
// Revision 1.1  2014/04/15 15:33:20  tw
// Schnittstellensaeuberung: AEP-Plus-Abverkauf.
//
// Revision 1.2  2014/02/21 00:38:21  tw
// Statistik-Schnittstellen implementiert.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.1  2014/02/20 03:52:16  tw
// Statistik-Schnittstellen implementiert.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
//

package de.decodetron.dao.statistikuser;

import static de.decodetron.util.DAOUtil.close;
import static de.decodetron.util.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.commons.beanutils.BeanUtils;

import de.decodetron.bo.UserStatistik;
import de.decodetron.dao.DAO;
import de.decodetron.exc.DAOException;
import de.decodetron.fac.DAOFactoryJDBC;

/**
 * @author Thomas Winter
 * @since 19.02.2014
 */
public class StatistikUserDAO extends DAO implements StatistikUserDAOI {

    private DAOFactoryJDBC daoFactory;

    private static final String SQL_INSERT_STAT = "INSERT INTO userstatistik VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_STAT = "UPDATE userstatistik SET " + "id_user" + " = ?, " + "ip" + " = ?, "
            + "logintime" + " = ?, " + "logouttime" + " = ?" + " WHERE " + "id" + " = ?";
    private static final String SQL_DELETE_STAT = "DELETE FROM userstatistik WHERE id = ?";

    public StatistikUserDAO(DAOFactoryJDBC daoFactory) {
        super(daoFactory);
        this.daoFactory = daoFactory;
    }

    public void insertUserStatistik(UserStatistik uStat) throws IllegalArgumentException, DAOException {
        if (uStat.getId() != null) {
            throw new IllegalArgumentException("UserStatistik is already created, the user ID is not null.");
        }

        Object[] values = { uStat.getId(), uStat.getId_user(), uStat.getIp(), uStat.getLogintime(),
                uStat.getLogouttime() };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_INSERT_STAT, true, values);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating Userstatistik failed, no rows affected.");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                uStat.setId(generatedKeys.getLong(1));
            } else {
                throw new DAOException("Creating Userstatistik failed, no generated key obtained.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, generatedKeys);
        }
    }

    public void updateUserStatistik(UserStatistik uStat) throws IllegalArgumentException, DAOException {
        if (uStat.getId() == null) {
            throw new IllegalArgumentException("UserStatistik is not created yet, the user ID is null.");
        }

        Object[] values = { uStat.getId_user(), uStat.getIp(), uStat.getLogintime(),
                uStat.getLogouttime(), uStat.getId()};

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_UPDATE_STAT, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating Userstatistik failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement);
        }
    }

    @Override
    public void deleteUserStatistik(UserStatistik uStat) throws IllegalArgumentException, DAOException {

        Object[] values = { uStat.getId() };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_DELETE_STAT, false, values);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting Userstatistik failed, no rows affected.");
            } else {
                uStat.setId(null);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement);
        }
    }

    @Override
    public UserStatistik getStatistik4UserId(Long userId) throws IllegalArgumentException, DAOException {
        UserStatistik ustat = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT * FROM Userstatistik WHERE id_user = ?");
            sql.append(" AND logouttime is null");
            sql.append(" ORDER BY logintime DESC;");
            preparedStatement = prepareStatement(connection, sql.toString(), false,
                new Object[] { userId });
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ustat = (UserStatistik) mapObject(UserStatistik.class, resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return ustat;
    }

    private UserStatistik mapUserStatistik(ResultSet resultSet) throws SQLException {
        UserStatistik ustat = new UserStatistik();

        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int colnr = rsmd.getColumnCount();
            for (int i = 1; i <= colnr; ++i) {
                String debugColName = rsmd.getColumnName(i).toLowerCase();
                BeanUtils.setProperty(ustat, debugColName, resultSet.getString(debugColName));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ustat;
    }
}
