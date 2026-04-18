// $Log: RechercheTest.java,v $
// Revision 1.3  2014/10/22 12:32:17  tw
// Case-Sensivitaet lucene.
//
// Revision 1.2  2014/10/03 23:20:19  tw
// .
//
// Revision 1.1  2014/10/01 11:24:31  tw
// Absplittung, Scanindex vom restlichen Index. Testanbindung.
//
//

package de.decodetron;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

import junit.framework.TestCase;
import au.com.bytecode.opencsv.CSVReader;
import de.decodetron.dao.recherche.RechercheDAOI;
import de.decodetron.fac.DAOFactoryJDBC;
import de.decodetron.fac.DAOFactoryLucene;
import de.decodetron.util.SystemUtil;

/**
 * Kleine Lucene-Solr-Tests.
 * 
 * @author Thomas Winter
 * @since 01.10.2014
 */
public class RechercheTest extends TestCase {

    private RechercheDAOI dbRecherche = null;

    @Override
    protected void setUp() throws Exception {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileRecherche = u.getHomeDirectory(userHome, p.getProperty("file.solr.rech"));
        dbRecherche = DAOFactoryLucene.getInstance(dbFileRecherche).getRechercheDAO();
    }

    public void testIsAlive() {
        assertTrue(dbRecherche.dbAlive());
    }

    
    /**
     * Testet, ob die count-Schnittstelle die gleiche Anzahl Liefert wie Anzahl Datensätze in
     * getFundstellen ... .
     * 
     * public void testCompareCountFundstellen() {
     * 
     * Integer hitsPerPage = 250; Long offset = 0L; FilterItemList listTxtFields = null; // Wird
     * jetzt benutzt... :-). String txtdateVon = ""; String txtdateBis = ""; String txtkdnr = "";
     * String txtlfnr = ""; String txtdoktyp = ""; List<String> keyblgart = Arrays.asList("GS",
     * "LS", "NB"); List<Section> sections = getUserDB().getUserDAO().getAllSections(); List<String>
     * filterList = Arrays.asList("*");
     * 
     * int value = 6551;
     * 
     * FundstellePage fundstellenPage = dbRecherche.getFundstellenPage(hitsPerPage, offset,
     * listTxtFields, txtdateVon, txtdateBis, txtkdnr, txtlfnr, txtdoktyp, keyblgart, sections,
     * filterList); assertEquals(value, fundstellenPage.getHitsCount());
     * 
     * long cnt = dbRecherche.countFundstellenPage(hitsPerPage, offset, listTxtFields, txtdateVon,
     * txtdateBis, txtkdnr, txtlfnr, txtdoktyp, keyblgart, sections, filterList);
     * assertEquals(value, cnt); }
     */

    private DAOFactoryJDBC getUserDB() {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileUserPerm = u.getHomeDirectory(userHome, p.getProperty("file.db.user"));
        return DAOFactoryJDBC.getInstance(dbFileUserPerm);
    }
}
