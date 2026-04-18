// $Log: RechercheScanTest.java,v $
// Revision 1.9  2016/06/28 19:33:11  tw
// BUFIX 3963: Benutzerverwaltung/Benutzer-DB =>  zusaetzliche Felder.
//
// Revision 1.8  2016/01/11 22:50:19  tw
// CR 3956: Interner Umbau: Lucene-Felder.
//
// Revision 1.7  2015/03/26 21:29:43  tw
// CR Feng-ID: 3947#7 Erweiterung automatisierter Test.
//
// Revision 1.6  2015/03/26 10:36:29  tw
// CR Feng-ID: 3949#1
//
// Revision 1.5  2014/10/24 20:46:19  tw
// Test-Bugfix.
//
// Revision 1.4  2014/10/24 20:14:06  tw
// Testanpassung: CSV-Parsing mit Hilfsklasse.
//
// Revision 1.3  2014/10/23 15:12:10  tw
// Testanpassung: CSV-Parsing mit Hilfsklasse.
//
// Revision 1.2  2014/10/22 12:32:17  tw
// Case-Sensivitaet lucene.
//
// Revision 1.1  2014/10/01 11:24:31  tw
// Absplittung, Scanindex vom restlichen Index. Testanbindung.
//
//

package de.decodetron;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import au.com.bytecode.opencsv.CSVReader;

import junit.framework.TestCase;
import de.decodetron.bo.FilterItem;
import de.decodetron.bo.FilterItemList;
import de.decodetron.bo.FundstellePage;
import de.decodetron.bo.Section;
import de.decodetron.bo.User;
import de.decodetron.dao.recherche.RechercheScanDAOI;
import de.decodetron.fac.DAOFactoryJDBC;
import de.decodetron.fac.DAOFactoryLucene;
import de.decodetron.util.SystemUtil;

/**
 * @author Thomas Winter
 * @since 01.10.2014
 */
public class RechercheScanTest extends TestCase {

    private RechercheScanDAOI dbRecherche = null;

    @Override
    protected void setUp() throws Exception {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileRecherche = u.getHomeDirectory(userHome, p.getProperty("file.solr.rechscan"));
        dbRecherche = DAOFactoryLucene.getInstance(dbFileRecherche).getRechercheScanDAO();
    }

    public void testIsAlive() {
        assertTrue(dbRecherche.dbAlive());
    }

    /**
     * getFundstellen ... . TODO: Funktioniert jetzt über Filteritems! Per Hand zusammenstellen.
     */
    public void testCompareCountFundstellen() {

        Integer hitsPerPage = 250;
        Long offset = 0L;
        
//        FilterItemList listTxtFields = null; // Soll mal benutzt werden ... :-(.
//        String txtdateVon = "";
//        String txtdateBis = "";
//        String txtkdnr = "";
//        String txtlfrtName = "";
//        String txtlfnr = "";
//        String txtdoktyp = "";
        
        FilterItemList filterItemList = new FilterItemList();
        //filterItemList.add(new FilterItem("Filter", "*"));
        
        List<String> keyblgart = Arrays.asList("SCLF");// 638
        List<Section> sections = getUserDB().getUserDAO().getAllSections();
        List<String> filterList = Arrays.asList("*");

        User userTest = new User();
        userTest.setId(5L);
        userTest.setFilter("*");
        userTest.setIsSuperAdmin(Boolean.TRUE);

        // TODO: Funkioniert nur mit der alten Branch-Variante!
        // Liste erreicht Limit v. 250
        //FundstellePage fundstellenPage = dbRecherche.getFundstellenPage(userTest, hitsPerPage, offset, filterItemList,
            //keyblgart, sections, filterList);
        //assertEquals(250, fundstellenPage.getFsList().size());

        // Kann das Limit überschreiten!
        //long cnt = dbRecherche.countFundstellenPage(userTest, hitsPerPage, offset, filterItemList, keyblgart, sections,
        //    filterList);
        //assertEquals(fundstellenPage.getHitsCount(), cnt);
    }

    public void testFundstellen03071() {
        
        Integer hitsPerPage = 250;
        Long offset = 0L;

        FilterItemList filterItemList = new FilterItemList();
        filterItemList.add(new FilterItem("KundenNr", "030*"));
        
        
        List<String> keyblgart = Arrays.asList("SCLF");
        List<Section> sections = getUserDB().getUserDAO().getAllSections();
        List<String> filterList = Arrays.asList("*");

        User userTest = new User();
        userTest.setId(5L);
        userTest.setFilter("*");
        userTest.setIsSuperAdmin(Boolean.TRUE);

        // FsList < Limitgrösse daher kann mit getHitsCount verglichen werden.
        FundstellePage fundstellenPage = dbRecherche.getFundstellenPage(userTest, hitsPerPage, offset, filterItemList,
            keyblgart, sections, filterList);
        assertEquals(fundstellenPage.getHitsCount(), fundstellenPage.getFsList().size());

        long cnt = dbRecherche.countFundstellenPage(userTest, hitsPerPage, offset, filterItemList, keyblgart, sections,
            filterList);
        assertEquals(fundstellenPage.getHitsCount(), cnt);
    }
    
    public void testQuoteCheckable() {
        assertTrue("\"".equals("\""));
    }

    /**
     * Test 1: Vom Komma im Text nicht irritieren lassen!
     * 
     * @throws Exception
     */
    public void testParseCSV1() throws Exception {
        int cnt = 0;
        String[] nextLine;
        CSVReader reader = new CSVReader(new InputStreamReader(getClass()
                .getResourceAsStream("/de/decodetron/test.txt")));
        while ((nextLine = reader.readNext()) != null) {
            cnt++;
            for (int i = 0; i < nextLine.length; i++) {
                if (cnt == 1) {
                    assertTrue(nextLine[8].equals("AXICORP, GmbH"));
                }
            }
        }
    }

    /**
     * Test 2: Vom Hochkomma im Text nicht irritieren lassen!
     * 
     * @throws Exception
     */
    public void testParseCSV2() throws Exception {

        int cnt = 0;
        String[] nextLine;
        CSVReader reader = new CSVReader(new InputStreamReader(getClass()
                .getResourceAsStream("/de/decodetron/test.txt")));
        while ((nextLine = reader.readNext()) != null) {
            cnt++;
            for (int i = 0; i < nextLine.length; i++) {
                if (cnt == 2) {
                    assertTrue(nextLine[8].equals("AXICORP 6\" GmbH"));
                }
            }
        }
    }

    /**
     * Test 3: Separatoren: Semikolon. Vom Komma im Text nicht irritieren lassen!
     * 
     * @throws Exception
     */
    public void testParseCSV3() throws Exception {
        int cnt = 0;
        String[] nextLine;
        CSVReader reader = new CSVReader(new InputStreamReader(getClass().getResourceAsStream(
            "/de/decodetron/test2.txt")), ';', ' ');
        while ((nextLine = reader.readNext()) != null) {
            cnt++;
            if (cnt == 1) {
                for (int i = 0; i < nextLine.length; i++) {
                    assertTrue(nextLine[8].equals("HAEMATO, PHARM AG"));
                }
            }
        }
    }

    /**
     * Test 4: Separatoren: Semikolon. Vom Hochkomma im Text nicht irritieren lassen!
     * 
     * @throws Exception
     */
    public void testParseCSV4() throws Exception {
        int cnt = 0;
        String[] nextLine;
        CSVReader reader = new CSVReader(new InputStreamReader(getClass().getResourceAsStream(
            "/de/decodetron/test2.txt")), ';');
        while ((nextLine = reader.readNext()) != null) {
            cnt++;
            if (cnt == 2) {
                for (int i = 0; i < nextLine.length; i++) {
                    assertTrue(nextLine[8].equals("AXICORP 6\" GmbH"));
                }
            }
        }
    }

    public void testParseCSV5() throws Exception {
        int cnt = 0;
        String[] nextLine;
        CSVReader reader = new CSVReader(new InputStreamReader(getClass().getResourceAsStream(
            "/de/decodetron/test2.txt")), ';');
        while ((nextLine = reader.readNext()) != null) {
            cnt++;
            if (cnt == 4) {
                for (int i = 0; i < nextLine.length; i++) {
                    assertTrue(nextLine[8].equals("BERLIN CHEMIE"));
                }
            }
        }
    }

    // /**
    // * Test: Quotes entfernen. Quotes im Text stehen lassen! => Regulärer Ausdruck.
    // *
    // * @throws Exception
    // */
    // public void testRemoveBlockQuote2() throws Exception {
    //
    // int cnt = 0;
    // String line;
    // BufferedReader br = null;
    // br = new BufferedReader(new
    // InputStreamReader(getClass().getResourceAsStream("/de/decodetron/test.txt")));
    // while ((line = br.readLine()) != null) {
    // cnt++;
    // String[] linesplit = line.split(",");
    // for (int i = 0; i < linesplit.length; i++) {
    // if (cnt == 2) {
    // String debug = linesplit[8].replaceAll("(^\")|(\"$)", "");
    // assertTrue(debug.equals("HAEMATO 5\" PHARM AG"));
    // }
    // }
    // }
    // br.close();
    // }

    private DAOFactoryJDBC getUserDB() {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileUserPerm = u.getHomeDirectory(userHome, p.getProperty("file.db.user"));
        return DAOFactoryJDBC.getInstance(dbFileUserPerm);
    }
}
