// $Log: AllTest.java,v $
// Revision 1.10  2015/07/21 17:59:19  tw
// Korrektur d. Haldetests.
//
// Revision 1.9  2015/07/10 15:09:21  tw
// Implementierung: Lucken-SR. DB-Anbindung.
//
// Revision 1.8  2015/02/03 00:51:19  tw
// Haldenbearbeitung: Einlesen v. Archivierungsdatensaetzen ermoeglicht.
//
// Revision 1.7  2014/11/06 13:12:26  tw
// Testanbindung: History-DB Benutzerrechte.
//
// Revision 1.6  2014/10/28 22:44:41  tw
// Testanbindung Xml-Objektparsing, UserSectionData.
//
// Revision 1.5  2014/10/22 12:32:16  tw
// Case-Sensivitaet lucene.
//
// Revision 1.4  2014/10/01 11:24:31  tw
// Absplittung, Scanindex vom restlichen Index. Testanbindung.
//
// Revision 1.3  2014/04/03 14:44:51  tw
// Bean-Property-Bug behoben.
//
// Revision 1.2  2014/02/20 03:52:16  tw
// Statistik-Schnittstellen implementiert.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.1  2013/11/21 17:40:02  tw
// Neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.4  2013/11/13 00:29:59  tw
// Servicefunktionen fuer Berechtigungskonzept implementiert.
//
// Revision 1.3  2013/11/10 20:17:18  tw
// Bugfix: select - statement hat nicht korrekt auf null - values reagiert.
//
// Revision 1.2  2013/11/08 11:44:00  tw
// Compiler nochmals utf-8 verklickert.
//
// Revision 1.1  2013/11/05 19:31:31  tw
// db-schnittstellen aufgeraeumt.
//
// Revision 1.6  2013/09/17 02:05:43  tw
// Login-Logout-Mechanismus implementiert.
//
// Revision 1.5  2013/09/16 14:35:40  tw
// Backup, Einbau: Login-Mechanismus
//
// Revision 1.4  2013/09/13 02:17:14  tw
// neues build-konzept.
//
// Revision 1.3  2013/09/10 21:39:59  tw
// Vorbereitung f. Login.
//
// Revision 1.2  2013/08/12 14:16:55  tw
// Tests für Nebenläufige Performance-Tests Erstellt.
//
// Revision 1.1  2013/08/07 01:46:51  tw
// AEP LoginModul als simpelst-keine-Zusatzbibliotheken-Variante. Erster Wurf.
//
//

package de.decodetron;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Hier der bewuste Versuch, die Datenbank an ihre Grenze zu bringen und ein SQLITE_BUSY zu
 * provozieren. Junit-Tests werden sequenziel abgearbeitet. Hier treten keinen Probleme auf. Wird
 * jedoch DBParallelTesting.xml per TestNG aufgerufen, wird aller wahrscheinlichkeit nach ein Fehler
 * geworfen, da sqlite nicht mehrbenutzerfähig ist.
 * 
 * @author Thomas Winter
 * @since 07.08.2013
 */
public class AllTest extends TestSuite {

    public static Test suite() {
        TestSuite suite = new TestSuite();
        //suite.addTestSuite(UserTest.class);
        suite.addTestSuite(DBTest.class);   
        suite.addTestSuite(UserTestDBPermissions.class);
        suite.addTestSuite(UserStatistikTest.class);
        suite.addTestSuite(DefektenTest.class);
        suite.addTestSuite(RechercheTest.class);
        suite.addTestSuite(RechercheScanTest.class);
        suite.addTestSuite(XmlParserTest.class);
        suite.addTestSuite(UserHistoryBrTest.class);
        suite.addTestSuite(HaldeTest.class);
        suite.addTestSuite(LueckenSRTest.class);
        return suite;
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}
