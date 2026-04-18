// $Log: HochpreiserTest.java,v $
// Revision 1.6  2020/02/28 20:30:10  tw
// Alte Funktionen entfernt. System.out. f. Debugzwecke erstellt.
//
// Revision 1.5  2014/04/28 11:12:49  tw
// Schnittstellen- umzug/umbenennung Filter=>Statistik
//
// Revision 1.4  2013/12/02 12:33:30  tw
// Neue Schnittstelle um unnoetige Spalten auszublenden.
//
// Revision 1.3  2013/11/28 13:40:43  tw
// Schnittstelle Tierarznei.
//
// Revision 1.2  2013/11/27 20:09:23  tw
// Implementierung: Hochpreiserliste.
//
// Revision 1.1  2013/11/27 17:05:44  tw
// Spaltenmapping, Vorbereitung.
//
//

package de.decodetron;

import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;
import de.decodetron.bo.DataRecord;
import de.decodetron.dao.DAOI;
import de.decodetron.dao.statistik.StatistikDAOI;
import de.decodetron.fac.DAOFactoryJDBC;
import de.decodetron.util.SystemUtil;

/**
 * @author Thomas Winter
 * @since 27.11.2013
 */
public class HochpreiserTest extends TestCase {

    private DAOFactoryJDBC dbHochpreis = null;

    @Override
    protected void setUp() throws Exception {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileHochpreis = u.getHomeDirectory(userHome, p.getProperty("file.db.hochpreiser"));
        dbHochpreis = DAOFactoryJDBC.getInstance(dbFileHochpreis);
    }
//
//    /**
//     * Diese Hochpreiser-Abfrage enthält nicht die Spalten: DokumentTyp u. PositionsTyp.
//     */
//    public void testGetHochpreiserColumnContent() {
//
//        int limit = 1;
//        // /////////////////////////////////////////////////////////////////////////////////////////
//        // /// klassiche Abfrage
//        // /
//        DAOI dao = dbHochpreis.getDAO();
//        List<DataRecord> list = dao.getAllRecordsByColNameByColItemLimit("HOCHPR", null, null, new Integer(1), "asc",
//            new Integer(limit), new Long(0));
//        assertTrue(list.size() == limit);
//        DataRecord record2Check = list.get(0);
//        assertTrue(record2Check.getSize() == 16); // 16 Spalten
//
//        // /////////////////////////////////////////////////////////////////////////////////////////
//        // /// überschriebene Abfrage
//        // /
//        StatistikDAOI daoh = dbHochpreis.getDAOFilter();
//        list = daoh.getAllRecordsByColNameByColItemLimit("HOCHPR", null, null, new Integer(1), "asc",
//            new Integer(limit), new Long(0));
//        assertTrue(list.size() == limit);
//        record2Check = list.get(0);
//        assertTrue(record2Check.getSize() == 14); // 14 Spalten
//    }
    
    /**
     * Diese Hochpreiser-Abfrage enthält nicht die Spalten: DokumentTyp u. PositionsTyp.
     */
    public void testGetHochpreiserColumnName() {

        // /////////////////////////////////////////////////////////////////////////////////////////
        // /// klassiche Abfrage
        // /
        DAOI dao = dbHochpreis.getDAO();
        DataRecord list = dao.getColumNamesAsDataRecord("HOCHPR");
        assertTrue(list.getSize()  == 16); // 16 Spalten

        // /////////////////////////////////////////////////////////////////////////////////////////
        // /// überschriebene Abfrage
        // /
        StatistikDAOI daoh = dbHochpreis.getDAOFilter();
        list = daoh.getColumNamesAsDataRecord("HOCHPR");
        assertTrue(list.getSize() == 14); // 14 Spalten
    }
}
