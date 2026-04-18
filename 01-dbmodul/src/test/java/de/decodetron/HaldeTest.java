// $Log: HaldeTest.java,v $
// Revision 1.12  2015/07/21 17:59:19  tw
// Korrektur d. Haldetests.
//
// Revision 1.11  2015/03/23 22:47:02  tw
// CR Feng-ID: 3947#6
//
// Revision 1.10  2015/03/19 22:39:27  tw
// CR Feng-ID: 3947#4
//
// Revision 1.9  2015/03/19 13:04:54  tw
// CR Feng-ID: 3947#4
//
// Revision 1.8  2015/02/19 15:13:03  tw
// Haldenbearbeitung: Aenderung des Speicherverzeichnisses.
//
// Revision 1.7  2015/02/19 14:32:41  tw
// Haldenbearbeitung: Aenderung des Speicherverzeichnisses.
//
// Revision 1.6  2015/02/15 01:31:26  tw
// Haldenbearbeitung: Autocomplete die 1.
//
// Revision 1.5  2015/02/07 12:02:42  tw
// Haldenbearbeitung: Datensatz-Loeschen-Schnittstelle implementiert.
//
// Revision 1.4  2015/02/04 16:23:09  tw
// Haldenbearbeitung: Speichern v. Buchungsdatensaetzen implementiert.
//
// Revision 1.3  2015/02/03 00:51:19  tw
// Haldenbearbeitung: Einlesen v. Archivierungsdatensaetzen ermoeglicht.
//
// Revision 1.2  2015/02/02 11:05:11  tw
// Halde-Testanbindung aufgeraeumt.
//
// Revision 1.1  2015/01/30 02:43:37  tw
// Haldenbearbeitung 1. Wurf.
//
//

package de.decodetron;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import de.decodetron.bo.HaldeBuchungssatzScan;
import de.decodetron.bo.HaldeFile;
import de.decodetron.dao.halde.HaldeDAOI;
import de.decodetron.fac.DAOFactoryFileIO;
import de.decodetron.util.Const;
import de.decodetron.util.SystemUtil;

/**
 * @author Thomas Winter
 * @since 28.01.2015
 */
public class HaldeTest extends TestCase {

    private HaldeDAOI haldeDAOI = null;

    public HaldeTest() {
        //DAOFactoryFileIO factoryIO = DAOFactoryFileIO.getInstance("path.halde.pdf.ju",
        //    new SystemUtil().getValue4DBProperties("path.halde.bsatz.tmp.save.ju"), "path.halde.bsatz.save.ju");
        DAOFactoryFileIO factoryIO = DAOFactoryFileIO.getInstance(Const.HALDE_JUNIT);
        haldeDAOI = factoryIO.getHaldeDAO();
    }

    public void testGetMinOneHaldePDF() {
        // Kann auch leer sein!
        List<HaldeFile> hf = haldeDAOI.getAllHaldeFiles(null);
        assertTrue(hf.size() >= 1);
    }

    public void testSaveOndHaldeBuchungssatz() {

        HaldeBuchungssatzScan hb = new HaldeBuchungssatzScan();
        hb.setDokumentType("AEPL00017632");
        hb.setLieferantNr("12270");
        hb.setBestellNr("80021454");
        hb.setDokDatum("26.01.2015");
        // haldeDAOI.insertBuchungsSatz(hb);
        haldeDAOI.insertBuchungsSatz(Arrays.asList(hb));

        // //////////////////////////////////////////////////////
        // // Gucken, ob das File existiert. Dazu den Filekonstuktionsmechanismus wiederholen
        String FS = System.getProperty("file.separator");
        String LS = System.getProperty("line.separator");

        // DAOFactoryFileIO factoryIO = DAOFactoryFileIO.getInstance("path.halde.pdf.ju",
        // "path.halde.bsatz.save.ju");
        // String haldeDir = factoryIO.getDirSave();
        // String newFilePath = haldeDir + FS + HaldeBuchungssatz.SAVE_FILENAME_ROOT +
        // hb.getDokumentType() + ".csv";
        // File file = new File(newFilePath);
        File file = haldeDAOI.getBuchungssatzDestFile4Tmp(hb);
        assertTrue(file.exists());
        file.delete();
    }

    public void testSaveMultipleHaldeBuchungssatz() {

        HaldeBuchungssatzScan hb1 = new HaldeBuchungssatzScan();
        hb1.setDokumentType("AEPL00017632");
        hb1.setLieferantNr("12270");
        hb1.setBestellNr("80021454");
        hb1.setDokDatum("26.01.2015");

        HaldeBuchungssatzScan hb2 = new HaldeBuchungssatzScan();
        hb2.setDokumentType("AEPL00017632");
        hb2.setLieferantNr("12270");
        hb2.setBestellNr("12345678");
        hb2.setDokDatum("27.01.2015");

        List<HaldeBuchungssatzScan> list = Arrays.asList(hb1, hb2);
        haldeDAOI.insertBuchungsSatz(list);

        // Hier zeigt sich das Problem, dass der Dokumenttype bei allen Buchunbsdatensätzen
        // die in einem File abgelegt werden, nicht ändern dürfen!
        File file = haldeDAOI.getBuchungssatzDestFile4Tmp(hb1);
        assertTrue(file.exists());

        List<HaldeBuchungssatzScan> hbl = haldeDAOI.getBuchungsSaetze4PDF(hb1.getDokumentType(), null);
        assertTrue(hbl.size() >= 2);
    }

    /**
     * Test der Methode: getScanDokBusa()
     */
    public void testGetScanDokBuchungssatz() {
        List<HaldeBuchungssatzScan> list = haldeDAOI.getBuchungsSaetze4PDF("AEPL00000036", null);
        assertTrue(list.size() == 3);
    }

    public void testDeleteDatensatz() {

        // /////////////////////////////////////////////
        // /// Datensätze zum Löschen erzeugen ...
        // //
        HaldeBuchungssatzScan hb1 = new HaldeBuchungssatzScan();
        hb1.setDokumentType("AEPL00017632");
        hb1.setLieferantNr("1");
        hb1.setBestellNr("80021454");
        hb1.setDokDatum("26.01.2015");
        hb1.setUserLogin("joehumbl");

        HaldeBuchungssatzScan hb2 = new HaldeBuchungssatzScan();
        hb2.setDokumentType("AEPL00017632");
        hb2.setLieferantNr("2");
        hb2.setBestellNr("12345678");
        hb2.setDokDatum("27.01.2015");
        hb2.setUserLogin("joehumbl");

        HaldeBuchungssatzScan hb3 = new HaldeBuchungssatzScan();
        hb3.setDokumentType("AEPL00017632");
        hb3.setLieferantNr("3");
        hb3.setBestellNr("12345678");
        hb3.setDokDatum("27.01.2015");
        hb3.setUserLogin("joehumbl");

        List<HaldeBuchungssatzScan> list = Arrays.asList(hb1, hb2, hb3);
        haldeDAOI.insertBuchungsSatz(list);
        File file = haldeDAOI.getBuchungssatzDestFile4Tmp(hb1);
        assertTrue(file.exists());

        long lenghtBefore = file.length();

        // /////////////////////////////////////////////
        // /// Lösche Datensatz Nr 1 ...
        // //
        haldeDAOI.removeDatensatzByIndex(hb1, 1);
        file = haldeDAOI.getBuchungssatzDestFile4Save(hb1);
        long lengthAfter = file.length();
        List<HaldeBuchungssatzScan> newList = haldeDAOI.getBuchungsSaetze4PDF("AEPL00017632", null);

        // /////////////////////////////////////////////
        // /// ... übrig bleibt 1 + 3 ...
        // //
        assertTrue(newList.get(0).getLieferantNr().equals("1"));
        assertTrue(newList.get(1).getLieferantNr().equals("3"));
    }

    public void testgetLieferantenName() {
        haldeDAOI.initLieferantenListe();
        String name = haldeDAOI.getLieferantenName("999036");
        assertEquals(name, "ADIX Pharma GmbH");
    }

    public void testgetLieferantenNr() {
        haldeDAOI.initLieferantenListe();
        String nr = haldeDAOI.getLieferantenNr("Apotheke im Kaiserhof");
        assertEquals(nr, "999025");
    }
}
