// $Log: HistoryBrDAO.java,v $
// Revision 1.3  2014/11/06 13:12:26  tw
// Testanbindung: History-DB Benutzerrechte.
//
// Revision 1.2  2014/11/04 16:40:00  tw
// Testanbindung: History-DB Benutzerrechte.
//
// Revision 1.1  2014/11/03 16:57:07  tw
// Testanbindung: History-DB Benutzerrechte.
//
//

package de.decodetron.dao.history;

import static de.decodetron.util.DAOUtil.close;
import static de.decodetron.util.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.decodetron.bo.HistoryBr;
import de.decodetron.dao.DAO;
import de.decodetron.exc.DAOException;
import de.decodetron.fac.DAOFactoryJDBC;

/**
 * @author Thomas Winter
 * @since 03.11.2014
 */
public class HistoryBrDAO extends DAO implements HistoryBrDAOI {

    private DAOFactoryJDBC daoFactory;

    public HistoryBrDAO(DAOFactoryJDBC daoFactory) {
        super(daoFactory);
        this.daoFactory = daoFactory;
    }

    @Override
    public void insert(HistoryBr h) {

        if (h.getId() != null) {
            throw new IllegalArgumentException("History Benutzerrechte is already created, the history-ID is not null.");
        }

        Object[] values = {//
        null,// 1
                h.getId_user(), // 2
                h.getAktion(), // 3
                (String) h.getTimeStamp(), // 4
                h.getXmlUserData() // 5
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, "INSERT INTO benutzerrechte VALUES (?, ?, ?, ?, ?)", true,
                values);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating benutzerrechte failed, no rows affected.");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                h.setId(generatedKeys.getLong(1));
            } else {
                throw new DAOException("Creating benutzerrechte failed, no generated key obtained.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, generatedKeys);
        }
    }

    @Override
    public void update(HistoryBr h) {
        // Wird wahrscheinlich nicht benötigt. Das Update eines Benutzers sorgt jedesmal für einen
        // neuen Historien-DB-Eintrag. Daher wird eine update(HistoryBr) keinen Sinn machen. Das
        // Flag AKTION_XX muss daher Clientseitig, gesetzt werden.

    }

    private HistoryBr find(String sql, Object... values) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        HistoryBr h = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                h = mapHistoryBr(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return h;
    }

    private HistoryBr mapHistoryBr(ResultSet resultSet) throws SQLException {
        HistoryBr hbr = new HistoryBr();
        hbr.setId(resultSet.getLong("id"));
        hbr.setId_user(resultSet.getLong("id_user"));
        hbr.setAktion(resultSet.getString("aktion"));
        hbr.setTimeStamp(resultSet.getString("timestamp"));
        hbr.setXmlUserData(resultSet.getString("userdata"));
        return hbr;
    }

    @Override
    public void delete(HistoryBr h) {
        Object[] values = { h.getId() };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, "DELETE FROM benutzerrechte WHERE id = ?", false, values);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting benutzerrechte failed, no rows affected.");
            } else {
                h.setId(null);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement);
        }
    }

    @Override
    public HistoryBr findById(Long id) {
        return find("SELECT * FROM benutzerrechte WHERE id = ?", id);
    }

}
