package de.decodetron;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import de.decodetron.fac.DAOFactoryJDBC;
import de.decodetron.util.SystemUtil;
import junit.framework.TestCase;

public class BestandIndexTest extends TestCase {

	ResultSet resultSet = null;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	private DAOFactoryJDBC dbBaseClubBestand = null;

	protected void setUp() throws Exception {
		SystemUtil u = new SystemUtil();
		String userHome = System.getProperty("user.dir");
		Properties p = new SystemUtil().loadSystemProperties("db.properties");
		String dbFileC = u.getHomeDirectory(userHome, p.getProperty("file.db.clubbestand"));
		dbBaseClubBestand = DAOFactoryJDBC.getInstance(dbFileC);
	}

	public void testIndexTagesDatumExisting() {

		String indexName = "idxTagesDatum";
		String sql = "SELECT 1 FROM sqlite_master WHERE type = 'index' AND name = 'idxTagesdatum' LIMIT 1";
		
		try {
			SystemUtil u = new SystemUtil();
			String userHome = System.getProperty("user.dir");
			Properties p = new SystemUtil().loadSystemProperties("db.properties");
			String dbFileC = u.getHomeDirectory(userHome, p.getProperty("file.db.clubbestand"));
			dbBaseClubBestand = DAOFactoryJDBC.getInstance(dbFileC);

			connection = dbBaseClubBestand.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			boolean indexExists = false;
			
			if (resultSet.next()) {
				indexExists = true;
			}
			assertTrue(indexExists);
			resultSet.close();
			connection.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testIndexLieferantNrExisting() {

		String sql = "SELECT 1 FROM sqlite_master WHERE type = 'index' AND name = 'idxLieferantNr' LIMIT 1";
		try {
			SystemUtil u = new SystemUtil();
			String userHome = System.getProperty("user.dir");
			Properties p = new SystemUtil().loadSystemProperties("db.properties");
			String dbFileC = u.getHomeDirectory(userHome, p.getProperty("file.db.clubbestand"));
			dbBaseClubBestand = DAOFactoryJDBC.getInstance(dbFileC);

			connection = dbBaseClubBestand.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			// preparedStatement = connection.prepareStatement(sql);
			// preparedStatement.setString(1, "idxTagesDatum");
			// resultSet = preparedStatement.executeQuery();

			boolean indexExists = false;

			if (resultSet.next()) {
				indexExists = true;
			}
			// connection.setAutoCommit(true);
			assertTrue(indexExists);
			resultSet.close();
			connection.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
