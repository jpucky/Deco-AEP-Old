// $Log: DBTest.java,v $
// Revision 1.16  2020/03/09 22:31:05  tw
// Version 1.32-H
// -----------------------------
// CR: Umstellung der Statements um Einschraenkung per Datum vornehmen zu koennen.
// BUGFIX: Datums-Suche AEP-Plus (CLUBBESTAND) / Valuta korrigiert.
//
// Revision 1.15  2020/02/28 20:30:09  tw
// Alte Funktionen entfernt. System.out. f. Debugzwecke erstellt.
//
// Revision 1.14  2020/02/26 19:23:08  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.13  2014/04/28 11:12:49  tw
// Schnittstellen- umzug/umbenennung Filter=>Statistik
//
// Revision 1.12  2014/04/15 15:33:20  tw
// Schnittstellensaeuberung: AEP-Plus-Abverkauf.
//
// Revision 1.11  2014/01/24 13:39:07  tw
// Schnittstellen aufgeraeumt. Neue limitierte Benutzerschnittstelle.
//
// Revision 1.10  2013/12/09 14:50:17  tw
// Von-Bis Suchfeld, Statistik.
//
// Revision 1.9  2013/12/09 09:39:22  tw
// Neue Schnittstelle: Von-bis Datum Statistik
//
// Revision 1.8  2013/12/08 11:30:46  tw
// Neue Schnittstelle: Von-bis Datum Statistik
//
// Revision 1.7  2013/12/07 13:20:05  tw
// Neue Schnittstelle: Von-bis Datum Statistik
//
// Revision 1.6  2013/11/25 16:51:48  tw
// Waehrungsformatierung.
//
// Revision 1.5  2013/11/25 15:36:03  tw
// Defektenliste: limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.4  2013/11/25 11:01:36  tw
// Limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.3  2013/11/22 03:09:59  tw
// anpassung an sv-entwicklung
//
// Revision 1.2  2013/11/21 22:11:35  tw
// Vorbereitung: neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.1  2013/11/21 17:40:02  tw
// Neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.9  2013/11/18 10:11:00  tw
// Bugfix: Multiuserfilter.
//
// Revision 1.8  2013/11/17 13:42:00  tw
// Neue Schnittstelle f. User mit mehrfachfiltern implementiert.
//
// Revision 1.7  2013/11/15 21:47:41  tw
// Tabellenspalten werden per seperater DB-Funktion abgerufen.
//
// Revision 1.6  2013/11/14 15:49:20  tw
// Schnittstellennormalisierung.
//
// Revision 1.5  2013/11/14 14:08:47  tw
// Schnittstellen Datenfilterung implementiert.
//
// Revision 1.4  2013/11/14 01:34:42  tw
// aaaaaaaaaaaaaaargh umlaute.
//
// Revision 1.3  2013/11/13 23:13:20  tw
// Vorbereitung: Schnittstellen fuer Datenfilter.
//
// Revision 1.2  2013/11/13 01:56:07  tw
// Tests an aktuelle Tabellensituation angepasst.
//
// Revision 1.1  2013/11/10 23:31:56  tw
// .
//
// Revision 1.10  2013/11/08 11:44:00  tw
// Compiler nochmals utf-8 verklickert.
//
// Revision 1.9  2013/11/08 11:00:34  tw
// Umlaute an vorerst ersetzt f. Fehlereingrenzung.
//
// Revision 1.8  2013/11/07 22:05:26  tw
// Clubliste geradegezogen.
//
// Revision 1.7  2013/11/07 17:43:52  tw
// Bugfix: getAllColumns
//
// Revision 1.6  2013/11/06 22:15:28  tw
// Neue Schnittstellentests f. flexiblere Abfragen.
//
// Revision 1.5  2013/11/05 19:31:31  tw
// db-schnittstellen aufger�umt.
//
// Revision 1.4  2013/11/04 14:52:39  tw
// Anbindung der Filterfelder. Fehlende Spalten nachgetragen.
//
// Revision 1.3  2013/11/03 23:33:57  tw
// Junittests f. Clubbrause ausgebaut.
//
// Revision 1.2  2013/11/02 23:16:39  tw
// Bugfix.
//
// Revision 1.1  2013/11/02 21:46:43  tw
// ClubBestand-DB-Schicht. 1. Schritte zur Veralgemeinerung.
//
//

package de.decodetron;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.sqlite.SQLiteJDBCLoader;

import junit.framework.TestCase;
import de.decodetron.bo.DataRecord;
import de.decodetron.dao.DAOI;
import de.decodetron.dao.statistik.StatistikDAOI;
import de.decodetron.fac.DAOFactoryJDBC;
import de.decodetron.util.DAOUtil;
import de.decodetron.util.SystemUtil;

/**
 * Lesefunktionalitäten-Test für Statistik: ClubBestand.
 * 
 * @author Thomas Winter
 * @since 02.11.2013
 */
public class DBTest extends TestCase {

    final String TABLENAME = "ClubBestand";
    // final String SCHEMANAME = "ClubBestand.sdb";
    private DAOFactoryJDBC dbBaseClubBestand = null;

    @Override
    protected void setUp() throws Exception {
        SystemUtil u = new SystemUtil();
        // Funktioniert nicht !?
        // see: https://bitbucket.org/xerial/sqlite-jdbc/wiki/Usage%20
        //System.setProperty("sqlite.purejava", "true");
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileC = u.getHomeDirectory(userHome, p.getProperty("file.db.clubbestand"));
        dbBaseClubBestand = DAOFactoryJDBC.getInstance(dbFileC);
    }

    /**
     * Test OHNE DAO-Funktionatlität. Nur um warm zu werden.
     */
    public void testGetAllClubBestand1() throws Exception {

        Connection con = dbBaseClubBestand.getConnection();
        PreparedStatement prep = con.prepareStatement("SELECT * FROM ClubBestand");
        ResultSet rs = prep.executeQuery();

        // Auch ne Möglichkeit !
        // Statement stat = con.createStatement();
        // ResultSet rs = stat.executeQuery("SELECT * FROM ClubBestand");

        int cnt = 0;
        while (rs.next()) {
            ++cnt;
            // String datum = rs.getString("Datum");
            // String posTyp = rs.getString("PosTyp");
            // System.out.println("PosTyp: " + posTyp);
        }

        assertTrue(cnt > 0);

        prep.close();
        con.close();
    }

    // /**
    // * Test MIT DAO-Funktionatlität.
    // */
    // public void testGetAllClubBestand2() {
    // ClubBestandDAOI cb = dbBaseClubBestand.getClubBestandDAO();
    // List<ClubBestand> cbl = cb.getAllClubBestand();
    // assertTrue(cbl.size() >= 4);
    // }

    /**
     * Test MIT DAO-Funktionatlität.
     */
    public void testGetAllRecordsFromDbXY() {
        DAOI d = dbBaseClubBestand.getDAO();
        List<DataRecord> dr = d.getAllRecords("ClubBestand");
        assertTrue(dr.size() >= 4);
    }

    // public void testFilterClubBestandCheckNull() {
    // ClubBestandDAOI cb = dbBaseClubBestand.getClubBestandDAO();
    // List<ClubBestand> cbl = cb.getClubBestandFilterBy(null, null, null, null, null, null, null,
    // null);
    // assertTrue(cbl.size() >= 9);
    //
    // cbl = cb.getClubBestandFilterBy(null, null, null, null, null, "20", null, null);
    // assertTrue(cbl.size() == 5);
    // }
    //
    // public void testFilterClubBestandCheckEmptyString() {
    // ClubBestandDAOI cb = dbBaseClubBestand.getClubBestandDAO();
    // List<ClubBestand> cbl = cb.getClubBestandFilterBy("", "", "", "", "", "", "", "");
    // assertTrue(cbl.size() >= 9);
    //
    // cbl = cb.getClubBestandFilterBy("", "", "", "", "", "20", "", "");
    // assertTrue(cbl.size() == 5);
    // }
    //
    // /**
    // * Suche nach pzn %20%. Diese gibt es jedoch nicht.
    // */
    // public void testFilterClubBestandCheckValueNotExisting() {
    // ClubBestandDAOI cb = dbBaseClubBestand.getClubBestandDAO();
    // List<ClubBestand> cbl = cb.getClubBestandFilterBy("", "", "", "", "20", "", "", "");
    // assertTrue(cbl.size() == 0);
    // }

    public void testMultiFilterFunction() {
        DAOI daoi = dbBaseClubBestand.getDAO();
        List<DataRecord> l = daoi.getAllRecordsFilterBy("ClubBestand", "", "", "", "", "", "20", "", "");
        assertTrue(l.size() == 5);
    }

    public void testMultiFilterFunctionNullValues() {
        DAOI daoi = dbBaseClubBestand.getDAO();
        List<DataRecord> l = daoi.getAllRecordsFilterBy("ClubBestand", null, null, null, null, null, null, null, null);
        // Es darf nicht 0 sein. Der Rest ist egal.
        assertTrue(l.size() > 5);
    }

    /**
     * Filter können sein: '*', '123', null?
     */
    public void testSectionSplit() {
        String toSplit = "*";
        String regExSplit1 = "\\,"; // [*]
        String[] split1a = toSplit.split(regExSplit1);
        assertTrue(split1a.length == 1);

        toSplit = "1, 2";
        split1a = toSplit.split(regExSplit1);
        assertTrue(split1a.length == 2);

        toSplit = "1,2";
        split1a = toSplit.split(regExSplit1);
        assertTrue(split1a.length == 2);
    }

    /**
     * Es werden mehr Filterbezeichner mitgeliefert, als die Tabelle Spalten hat:<br>
     * filterValues.length > colNames.size()<br>
     * Tabelle ClubBestand: 8 Spalten.
     */
    public void testMOREFilterThanColumns() {
        DAOI daoi = dbBaseClubBestand.getDAO();
        List<DataRecord> l = daoi.getAllRecordsFilterBy("ClubBestand", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        DataRecord r = l.get(0);
        // Vorerst! Weiss noch nicht wie ich drauf Reagieren soll
        assertTrue(r.getColItem(0).startsWith("Mehr Filter als "));
    }

    /**
     * Es werden weniger Filterbezeichner mitgeliefert, als die Tabelle Spalten hat:<br>
     * colNames.size() > filterValues.length
     */
    public void testLESSFilterThanColumns() {
        DAOI daoi = dbBaseClubBestand.getDAO();
        List<DataRecord> l = daoi.getAllRecordsFilterBy("ClubBestand", "1", "2");
        // Nix. Es tritt keine Exception auf. Die Abfrage bezieht sich einfach nicht auf alle
        // Filter!
    }

    public void testClub_ABVERKAUF_20131106() {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileC = u.getHomeDirectory(userHome, p.getProperty("file.db.club20131106"));
        DAOFactoryJDBC dbClub20131106 = DAOFactoryJDBC.getInstance(dbFileC);
        DAOI dao = dbClub20131106.getDAO();
        List<DataRecord> dr = dao.getAllRecords("ABVERKAUF");

        // Nix. Einfach nur mal gucken, ob überhaupt was rauskommt.
        assertTrue(dr.size() > 0);
    }

    public void testClub_BESTAND_20131106() {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileC = u.getHomeDirectory(userHome, p.getProperty("file.db.club20131106"));
        DAOFactoryJDBC dbClub20131106 = DAOFactoryJDBC.getInstance(dbFileC);
        DAOI dao = dbClub20131106.getDAO();
        List<DataRecord> dr = dao.getAllRecords("BESTAND");

        // Nix. Einfach nur mal gucken ob überhaupt was rauskommt.
        assertTrue(dr.size() > 5000);
    }

    public void testClub_BESTAND_20131106_MultiFilterFunction() {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileC = u.getHomeDirectory(userHome, p.getProperty("file.db.club20131106"));
        DAOFactoryJDBC dbClub20131106 = DAOFactoryJDBC.getInstance(dbFileC);
        DAOI dao = dbClub20131106.getDAO();
        List<DataRecord> dr = dao.getAllRecordsFilterBy("BESTAND", "", "", "", "", "", "", "", "", "");

        // Nix. Einfach nur mal gucken ob überhaupt was rauskommt.
        assertTrue(dr.size() > 5000);
    }

    public void testClub_BESTAND_20131106_MultiFilterFunction1() {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileC = u.getHomeDirectory(userHome, p.getProperty("file.db.club20131106"));
        DAOFactoryJDBC dbClub20131106 = DAOFactoryJDBC.getInstance(dbFileC);
        DAOI dao = dbClub20131106.getDAO();
        List<DataRecord> dr = dao.getAllRecordsFilterBy("BESTAND", "", "", "", "", "", "", "", "TOLTERODIN", "", "");

        assertTrue(dr.size() == 1);
    }

    public void test_Reimport_MultiFilterFunction1() {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileR = u.getHomeDirectory(userHome, p.getProperty("file.db.reimporte"));
        DAOFactoryJDBC dbReimport = DAOFactoryJDBC.getInstance(dbFileR);
        DAOI dao = dbReimport.getDAO();
        List<DataRecord> dr = dao.getAllRecordsFilterBy("REIMP", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "");

        assertTrue(dr.size() == 789);
    }

    public void test_Reimport_MultiFilterFunction2() {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileR = u.getHomeDirectory(userHome, p.getProperty("file.db.reimporte"));
        DAOFactoryJDBC dbReimport = DAOFactoryJDBC.getInstance(dbFileR);
        DAOI dao = dbReimport.getDAO();
        List<DataRecord> dr = dao.getAllRecordsFilterBy("REIMP", "", "", "", "", "0061303", "", "", "", "", "", "", "",
            "", "", "", "");

        assertTrue(dr.size() == 2);
    }

    public void testLimit1() {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileR = u.getHomeDirectory(userHome, p.getProperty("file.db.reimporte"));
        DAOFactoryJDBC dbReimport = DAOFactoryJDBC.getInstance(dbFileR);
        DAOI dao = dbReimport.getDAO();
        List<DataRecord> dr = dao.getAllRecordsLimited("REIMP", "Menge", "desc", 5, 5);

        assertEquals(dr.size(), 5 + 1); // +1 wg. spaltenüberschrift!
        assertTrue(((DataRecord) dr.get(3)).getColItem(10).equals("6"));
        assertTrue(((DataRecord) dr.get(4)).getColItem(10).equals("5"));
    }

    public void testChargenUmlautReplacement1() {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileChargen = u.getHomeDirectory(userHome, p.getProperty("file.db.chargen"));
        DAOFactoryJDBC dbChargen = DAOFactoryJDBC.getInstance(dbFileChargen);
        DAOI dao = dbChargen.getDAO();
        List<String> colNames = dao.getColumNames("CHARGEN");

        assertEquals("Strasse", DAOUtil.replaceUmlaute(colNames.get(15)));
    }

    public void testChargenGetColumnTypes() {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileChargen = u.getHomeDirectory(userHome, p.getProperty("file.db.chargen"));
        DAOFactoryJDBC dbChargen = DAOFactoryJDBC.getInstance(dbFileChargen);
        DAOI dao = dbChargen.getDAO();
        List<String> columnTypes = dao.getColumTypes("CHARGEN");

        System.out.println(columnTypes);
    }

    public void testGetColumNamesDefekte() {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileChargen = u.getHomeDirectory(userHome, p.getProperty("file.db.defekte"));
        DAOFactoryJDBC dbChargen = DAOFactoryJDBC.getInstance(dbFileChargen);
        DAOI dao = dbChargen.getDAO();
        List<String> colNames = dao.getColumNames("DEFEKTE");
        assertTrue(colNames.size() == 14);
    }

    public void testChargenUmlautReplacement2() {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileChargen = u.getHomeDirectory(userHome, p.getProperty("file.db.chargen"));
        DAOFactoryJDBC dbChargen = DAOFactoryJDBC.getInstance(dbFileChargen);
        DAOI dao = dbChargen.getDAO();
        List<DataRecord> colNames = dao.getAllRecords("CHARGEN");
        DataRecord colName = colNames.get(0);

        assertEquals("Strasse", DAOUtil.replaceUmlaute(colName.getColItem(15)));
    }

    // /**
    // * Simuliert das Laden z.B der CLUB-Abverkaufliste mit eingeschränkten Rechten.
    // */
    // @Deprecated
    // public void testGetAllRecordsByColNameByColItemLieferantNr() {
    // SystemUtil u = new SystemUtil();
    // String userHome = System.getProperty("user.dir");
    // Properties p = new SystemUtil().loadSystemProperties("db.properties");
    // String dbFileC = u.getHomeDirectory(userHome, p.getProperty("file.db.club20131106"));
    // DAOFactoryJDBC dbClub20131106 = DAOFactoryJDBC.getInstance(dbFileC);
    // DAOI dao = dbClub20131106.getDAO();
    // List<DataRecord> list = dao.getAllRecordsByColNameByColItem("ABVERKAUF", "LieferantNr",
    // "11950");
    // // assertTrue(list.size() == 2);
    // assertTrue(list.size() == 1); // spaltenueberschrift faellt weg.
    // }

    // @Deprecated
    // public void testGetAllRecordsByColNameByColItemWildcard() {
    // SystemUtil u = new SystemUtil();
    // String userHome = System.getProperty("user.dir");
    // Properties p = new SystemUtil().loadSystemProperties("db.properties");
    // String dbFileC = u.getHomeDirectory(userHome, p.getProperty("file.db.club20131106"));
    // DAOFactoryJDBC dbClub20131106 = DAOFactoryJDBC.getInstance(dbFileC);
    // DAOI dao = dbClub20131106.getDAO();
    // List<DataRecord> list = dao.getAllRecordsByColNameByColItem("ABVERKAUF", "LieferantNr", "*");
    // assertTrue(list.size() == 20);
    // }

    // /**
    // * Simuliert das Laden z.B der CLUB-Abverkaufliste mit eingeschränkten Rechten.
    // */
    // @Deprecated
    // public void testGetAllRecordsByColNameByColItem2() {
    // SystemUtil u = new SystemUtil();
    // String userHome = System.getProperty("user.dir");
    // Properties p = new SystemUtil().loadSystemProperties("db.properties");
    // String dbFileC = u.getHomeDirectory(userHome, p.getProperty("file.db.club20131106"));
    // DAOFactoryJDBC dbClub20131106 = DAOFactoryJDBC.getInstance(dbFileC);
    // DAOI dao = dbClub20131106.getDAO();
    // List<DataRecord> list = dao.getAllRecordsByColNameByColItem("ABVERKAUF", "LieferantNr",
    // "20792", "", "", "");
    // assertTrue(list.size() == 7);
    // }
    //
    // /**
    // * Simuliert das Laden z.B der CLUB-Abverkaufliste mit eingeschränkten Rechten.
    // */
    // @Deprecated
    // public void testGetAllRecordsByColNameByColItem3() {
    // SystemUtil u = new SystemUtil();
    // String userHome = System.getProperty("user.dir");
    // Properties p = new SystemUtil().loadSystemProperties("db.properties");
    // String dbFileC = u.getHomeDirectory(userHome, p.getProperty("file.db.club20131106"));
    // DAOFactoryJDBC dbClub20131106 = DAOFactoryJDBC.getInstance(dbFileC);
    // DAOI dao = dbClub20131106.getDAO();
    // // Test: zusatzfilteratribute weglassen.
    // List<DataRecord> list = dao.getAllRecordsByColNameByColItem("ABVERKAUF", "LieferantNr",
    // "20792");
    // assertTrue(list.size() == 7);
    // }

    // /**
    // * Testet das Abholen der Daten von Nutzern mit mehrfach-Filtern. Konkret wird diese Abfrage
    // * getestet. Sie liefert 2 Datensätze:
    // *
    // * <pre>
    // * select * from btm a
    // * where(a.KundenNr like '3154778'
    // * or a.KundenNr like '4425050'
    // * or a.KundenNr like '0069687')
    // * and (a.PicklistenNr like '%1000000807%' or c.PicklistenNr is null )
    // * and (a.LieferantNr like '%25137%' or c.LieferantNr is null);
    // * </pre>
    // */
    // public void testGetAllRecordsMultiFilterUser() {
    // SystemUtil u = new SystemUtil();
    // String userHome = System.getProperty("user.dir");
    // Properties p = new SystemUtil().loadSystemProperties("db.properties");
    // String dbFileBtm = u.getHomeDirectory(userHome, p.getProperty("file.db.btm"));
    // DAOFactoryJDBC dbBtm = DAOFactoryJDBC.getInstance(dbFileBtm);
    // DAOI dao = dbBtm.getDAO();
    // List<DataRecord> list = dao.getAllRecordsByColNameByColItem("btm", "KundenNr",
    // Arrays.asList("3154778", "4425050", "0069687"), "", "", "", "", "", "", "", "1000000807", "",
    // "", "", "",
    // "", "25137");
    // assertTrue(list.size() == 2);
    // }

    // /**
    // * Testet das Abholen der Daten mit Wildcards. Wildcard wird ersetzt durch '%'<br>
    // *
    // * <pre>
    // * select * from btm c
    // * where (
    // * c.KundenNr like '%%'
    // * )
    // * and (c.PicklistenNr like '%1000000807%' or c.RecId is null)
    // * and (c.LieferantNr like '%25137%' or c.AuftragDatum is null);
    // * </pre>
    // */
    // public void testGetAllRecordsMultiFilterUserWildcard() {
    // SystemUtil u = new SystemUtil();
    // String userHome = System.getProperty("user.dir");
    // Properties p = new SystemUtil().loadSystemProperties("db.properties");
    // String dbFileBtm = u.getHomeDirectory(userHome, p.getProperty("file.db.btm"));
    // DAOFactoryJDBC dbBtm = DAOFactoryJDBC.getInstance(dbFileBtm);
    // DAOI dao = dbBtm.getDAO();
    // List<DataRecord> list = dao.getAllRecordsByColNameByColItem("btm", "KundenNr",
    // Arrays.asList("*"), "", "", "",
    // "", "", "", "", "1000000807", "", "", "", "", "", "25137");
    // assertTrue(list.size() == 2);
    // }

    public void testCountAllBtmRecords() {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileBtm = u.getHomeDirectory(userHome, p.getProperty("file.db.btm"));
        DAOFactoryJDBC dbBtm = DAOFactoryJDBC.getInstance(dbFileBtm);
        DAOI dao = dbBtm.getDAO();
        Long rec = dao.countAllRecords("btm");
        assertTrue(rec >= 572);
    }

    public void testRunningMode() {
        try {
            System.out.println(String.format("running in %s mode", SQLiteJDBCLoader.isNativeMode() ? "native"
                    : "pure-java"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // /**
    // * <pre>
    // * select * from btm a
    // * where(a.KundenNr like '3054172'
    // * or a.KundenNr like '4425050'
    // * or a.KundenNr like '0069687')
    // * --and a.PicklistenNr like '%1000000807%'
    // * --and a.LieferantNr like '%25137%'
    // * order by a.Artikelbezeichnung asc limit 4 offset 0;
    // * </pre>
    // */
    // public void testGetAllRecordsMultiFilterLimit1() {
    //
    // SystemUtil u = new SystemUtil();
    // String userHome = System.getProperty("user.dir");
    // Properties p = new SystemUtil().loadSystemProperties("db.properties");
    // String dbFileBtm = u.getHomeDirectory(userHome, p.getProperty("file.db.btm"));
    // DAOFactoryJDBC dbBtm = DAOFactoryJDBC.getInstance(dbFileBtm);
    // DAOI dao = dbBtm.getDAO();
    // // "btm", "KundenNr", Arrays.asList("3154778", "4425050", "0069687"), 9, "asc", 4, 0
    // List<DataRecord> list = dao.getAllRecordsByColNameByColItemLimit("btm", "KundenNr",
    // Arrays.asList("3054172", "4425050", "0069687"), new Integer(9), "asc", new Integer(4), new
    // Long(0));
    // assertTrue(list.size() == 4);
    // }
    //
    // /**
    // * Sollte einer der Limitierungsparameter null sein, wird eine Defaultmenge von 1000
    // datensätzen
    // * ausgegeben.
    // */
    // public void testGetAllRecordsMultiFilterLimitLimitNull() {
    //
    // SystemUtil u = new SystemUtil();
    // String userHome = System.getProperty("user.dir");
    // Properties p = new SystemUtil().loadSystemProperties("db.properties");
    // String dbFileBtm = u.getHomeDirectory(userHome, p.getProperty("file.db.btm"));
    // DAOFactoryJDBC dbBtm = DAOFactoryJDBC.getInstance(dbFileBtm);
    // DAOI dao = dbBtm.getDAO();
    // // "btm", "KundenNr", Arrays.asList("3154778", "4425050", "0069687"), 9, "asc", 4, 0
    // List<DataRecord> list = dao.getAllRecordsByColNameByColItemLimit("btm", "KundenNr",
    // Arrays.asList("3054172", "4425050", "0069687"), null, "asc", new Integer(4), new Long(0));
    // assertTrue(list.size() >= 15);
    // }

    // public void testGetAllRecordsMultiFilterLimitPicListNr7() {
    //
    // SystemUtil u = new SystemUtil();
    // String userHome = System.getProperty("user.dir");
    // Properties p = new SystemUtil().loadSystemProperties("db.properties");
    // String dbFileBtm = u.getHomeDirectory(userHome, p.getProperty("file.db.btm"));
    // DAOFactoryJDBC dbBtm = DAOFactoryJDBC.getInstance(dbFileBtm);
    // DAOI dao = dbBtm.getDAO();
    // // "btm", "KundenNr", Arrays.asList("3154778", "4425050", "0069687"), 9, "asc", 4, 0
    // List<DataRecord> list = dao.getAllRecordsByColNameByColItemLimit("btm", "KundenNr",
    // Arrays.asList("3054172", "4425050", "0069687"), new Integer(9), "asc", new Integer(4), new
    // Long(0), "", "",
    // "", "", "", "", "", "1000000807");
    // assertTrue(list.size() == 3);
    // }

    // TODO: Diese Tests erneuern !!!

    // public void testGetAllRecordsMultiFilterLimitFromToDate() {
    // SystemUtil u = new SystemUtil();
    // String userHome = System.getProperty("user.dir");
    // Properties p = new SystemUtil().loadSystemProperties("db.properties");
    // String dbFileBtm = u.getHomeDirectory(userHome, p.getProperty("file.db.btm"));
    // DAOFactoryJDBC dbBtm = DAOFactoryJDBC.getInstance(dbFileBtm);
    // StatistikDAOI dao = dbBtm.getDAOFilter();
    // // "btm", "KundenNr", Arrays.asList("3154778", "4425050", "0069687"), 9, "asc", 4, 0
    // List<DataRecord> list = dao.getStatistikData("btm", "KundenNr",
    // Arrays.asList("*"), new Integer(9), "asc", new Integer(100), new Long(0), "", "2013-11-05",
    // "2013-11-07",
    // "44", // KundenNr
    // "", "", "", "", "TILI", "", "", "", "", "", "" // Chargennummer
    // );
    // assertTrue(list.size() == 3);
    // }
    //
    // public void testGetAllRecordsMultiFilterLimitFromToDateNOTODATE() {
    // SystemUtil u = new SystemUtil();
    // String userHome = System.getProperty("user.dir");
    // Properties p = new SystemUtil().loadSystemProperties("db.properties");
    // String dbFileBtm = u.getHomeDirectory(userHome, p.getProperty("file.db.btm"));
    // DAOFactoryJDBC dbBtm = DAOFactoryJDBC.getInstance(dbFileBtm);
    // StatistikDAOI dao = dbBtm.getDAOFilter();
    // // "btm", "KundenNr", Arrays.asList("3154778", "4425050", "0069687"), 9, "asc", 4, 0
    // List<DataRecord> list = dao.getStatistikData("btm", "KundenNr",
    // Arrays.asList("*"), new Integer(9), "asc", new Integer(100), new Long(0), "", "2013-11-06",
    // // VON
    // "", // BIS
    // "44", // KundenNr
    // "", "", "", "", "", "", "", "", "", "", "" // Chargennummer
    // );
    // assertTrue(list.size() == 76);
    // }
    //
    // public void testGetAllRecordsMultiFilterLimitFromToDateNOFROMDATE() {
    // SystemUtil u = new SystemUtil();
    // String userHome = System.getProperty("user.dir");
    // Properties p = new SystemUtil().loadSystemProperties("db.properties");
    // String dbFileBtm = u.getHomeDirectory(userHome, p.getProperty("file.db.btm"));
    // DAOFactoryJDBC dbBtm = DAOFactoryJDBC.getInstance(dbFileBtm);
    // StatistikDAOI dao = dbBtm.getDAOFilter();
    // // "btm", "KundenNr", Arrays.asList("3154778", "4425050", "0069687"), 9, "asc", 4, 0
    // List<DataRecord> list = dao.getStatistikData("btm", "KundenNr",
    // Arrays.asList("*"), new Integer(9), "asc", new Integer(100), new Long(0), "", "", // VON
    // "2013-10-04", // BIS
    // "44", // KundenNr
    // "", "", "", "", "", "", "", "", "", "", "" // Chargennummer
    // );
    // assertTrue(list.size() == 16);
    // }
    //
    // public void testGetAllRecordsMultiFilterLimitFromToDateNOFROMNOTODATE() {
    // SystemUtil u = new SystemUtil();
    // String userHome = System.getProperty("user.dir");
    // Properties p = new SystemUtil().loadSystemProperties("db.properties");
    // String dbFileBtm = u.getHomeDirectory(userHome, p.getProperty("file.db.btm"));
    // DAOFactoryJDBC dbBtm = DAOFactoryJDBC.getInstance(dbFileBtm);
    // StatistikDAOI dao = dbBtm.getDAOFilter();
    // // "btm", "KundenNr", Arrays.asList("3154778", "4425050", "0069687"), 9, "asc", 4, 0
    // List<DataRecord> list = dao.getStatistikData("btm",// TABELLENNAME
    // "KundenNr",// FILTERIDENTIFIER
    // Arrays.asList("*"),// FILTERITEM
    // new Integer(9),// COL2SORT
    // "asc",// SORTDIRECTION
    // new Integer(100),// LIMIT
    // new Long(0),// OFFSET
    // "",// RECID
    // "", // VON
    // "", // BIS
    // "449", // KundenNr
    // "",//
    // "",//
    // "",//
    // "",//
    // "",//
    // "",//
    // "",//
    // "",//
    // "",//
    // "",//
    // "" // Chargennummer
    // );
    // assertTrue(list.size() == 48);
    // }
    //
    // public void testCOUNTAllRecordsMultiFilterLimitFromToDateNOFROMNOTODATE() {
    // SystemUtil u = new SystemUtil();
    // String userHome = System.getProperty("user.dir");
    // Properties p = new SystemUtil().loadSystemProperties("db.properties");
    // String dbFileBtm = u.getHomeDirectory(userHome, p.getProperty("file.db.btm"));
    // DAOFactoryJDBC dbBtm = DAOFactoryJDBC.getInstance(dbFileBtm);
    // StatistikDAOI dao = dbBtm.getDAOFilter();
    // // "btm", "KundenNr", Arrays.asList("3154778", "4425050", "0069687"), 9, "asc", 4, 0
    //
    // long cnt = dao.countStatistikData("btm",// TABELLENNAME
    // "KundenNr",// FILTERIDENTIFIER
    // Arrays.asList("*"),// FILTERITEM
    // // new Integer(9),// COL2SORT
    // // "asc",// SORTDIRECTION
    // // new Integer(100),// LIMIT
    // // new Long(0),// OFFSET
    // "",// RECID
    // "", // VON
    // "", // BIS
    // "449", // KundenNr
    // "",//
    // "",//
    // "",//
    // "",//
    // "",//
    // "",//
    // "",//
    // "",//
    // "",//
    // "",//
    // "" // Chargennummer
    // );
    // assertTrue(cnt == 48);
    // }
}
