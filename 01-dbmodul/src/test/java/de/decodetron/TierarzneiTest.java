// $Log: TierarzneiTest.java,v $
// Revision 1.4  2020/02/28 20:30:10  tw
// Alte Funktionen entfernt. System.out. f. Debugzwecke erstellt.
//
// Revision 1.3  2014/04/28 11:12:49  tw
// Schnittstellen- umzug/umbenennung Filter=>Statistik
//
// Revision 1.2  2013/12/02 12:33:30  tw
// Neue Schnittstelle um unnoetige Spalten auszublenden.
//
// Revision 1.1  2013/11/28 14:34:41  tw
// Neue Liste: Transfusion.
//
//

package de.decodetron;

import java.util.List;
import java.util.Properties;

import de.decodetron.bo.DataRecord;
import de.decodetron.dao.DAOI;
import de.decodetron.dao.statistik.StatistikDAOI;

import de.decodetron.fac.DAOFactoryJDBC;
import de.decodetron.util.SystemUtil;
import junit.framework.TestCase;

/**
 * @author Thomas Winter
 * @since 28.11.2013
 */
public class TierarzneiTest extends TestCase {
    
    private DAOFactoryJDBC dbTierarznei = null;

    @Override
    protected void setUp() throws Exception {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileTierarznei = u.getHomeDirectory(userHome, p.getProperty("file.db.tierarznei"));
        dbTierarznei = DAOFactoryJDBC.getInstance(dbFileTierarznei);
    }

    /**
     * Diese Hochpreiser-Abfrage enthält nicht die Spalten: DokumentTyp u. PositionsTyp.
     */
    public void testGetTierarzneiColumnName() {

        // /////////////////////////////////////////////////////////////////////////////////////////
        // /// klassiche Abfrage
        // /
        DAOI dao = dbTierarznei.getDAO();
        DataRecord list = dao.getColumNamesAsDataRecord("TIERARZ");
        assertTrue(list.getSize()  == 16); // 16 Spalten

        // /////////////////////////////////////////////////////////////////////////////////////////
        // /// überschriebene Abfrage
        // /
        StatistikDAOI daot = dbTierarznei.getDAOFilter();
        list = daot.getColumNamesAsDataRecord("TIERARZ");
        assertTrue(list.getSize() == 14); // 14 Spalten
    }
    
//    /**
//     * Diese Hochpreiser-Abfrage enthält nicht die Spalten: DokumentTyp u. PositionsTyp.
//     */
//    public void testGetTierarzneiColumnContent() {
//
//        int limit = 1;
//        // /////////////////////////////////////////////////////////////////////////////////////////
//        // /// klassiche Abfrage
//        // /
//        DAOI dao = dbTierarznei.getDAO();
//        List<DataRecord> list = dao.getAllRecordsByColNameByColItemLimit("TIERARZ", null, null, new Integer(1), "asc",
//            new Integer(limit), new Long(0));
//        assertTrue(list.size() == limit);
//        DataRecord record2Check = list.get(0);
//        assertTrue(record2Check.getSize() == 16); // 16 Spalten
//
//        // /////////////////////////////////////////////////////////////////////////////////////////
//        // /// überschriebene Abfrage
//        // /
//        StatistikDAOI daot = dbTierarznei.getDAOFilter();
//        list = daot.getAllRecordsByColNameByColItemLimit("TIERARZ", null, null, new Integer(1), "asc",
//            new Integer(limit), new Long(0));
//        assertTrue(list.size() == limit);
//        record2Check = list.get(0);
//        assertTrue(record2Check.getSize() == 14); // 14 Spalten
//    }
}
