// $Log: HaldeDAOI.java,v $
// Revision 1.12  2015/07/21 17:59:19  tw
// Korrektur d. Haldetests.
//
// Revision 1.11  2015/04/13 14:50:04  tw
// CR Feng-ID: 3950#3
//
// Revision 1.10  2015/03/24 23:20:02  tw
// CR Feng-ID: 3947#7
//
// Revision 1.9  2015/03/23 22:47:02  tw
// CR Feng-ID: 3947#6
//
// Revision 1.8  2015/03/19 13:04:54  tw
// CR Feng-ID: 3947#4
//
// Revision 1.7  2015/02/19 15:13:03  tw
// Haldenbearbeitung: Aenderung des Speicherverzeichnisses.
//
// Revision 1.6  2015/02/19 14:32:41  tw
// Haldenbearbeitung: Aenderung des Speicherverzeichnisses.
//
// Revision 1.5  2015/02/15 01:31:26  tw
// Haldenbearbeitung: Autocomplete die 1.
//
// Revision 1.4  2015/02/07 12:02:42  tw
// Haldenbearbeitung: Datensatz-Loeschen-Schnittstelle implementiert.
//
// Revision 1.3  2015/02/04 16:23:09  tw
// Haldenbearbeitung: Speichern v. Buchungsdatensaetzen implementiert.
//
// Revision 1.2  2015/02/03 00:51:19  tw
// Haldenbearbeitung: Einlesen v. Archivierungsdatensaetzen ermoeglicht.
//
// Revision 1.1  2015/01/30 02:43:37  tw
// Haldenbearbeitung 1. Wurf.
//
//

package de.decodetron.dao.halde;

import java.io.File;
import java.util.List;

import de.decodetron.bo.HaldeBuchungssatzScan;
import de.decodetron.bo.HaldeFile;
import de.decodetron.bo.LiefNrName;

/**
 * @author Thomas Winter
 * @since 28.01.2015
 */
public interface HaldeDAOI {

    /**
     * Gibt in erster Linie die PDFs der Halde, verpackt im Objekt "HaldeFile". "HaldeFile" enthält
     * u.a. Informationen, ob es z.B. zu dem PDF schon einen Buchungssatz gibt.
     * 
     * @return List<HaldeFile>
     */
    public List<HaldeFile> getAllHaldeFiles(String login);

    public void insertBuchungsSatz(List<HaldeBuchungssatzScan> hb);

    /**
     * Im Prinzip die gleiche Funktionalität wie {@link #insertBuchungsSatz(List)}, nur mit anderem
     * Zielverzeichnis. Weiterhin wird das Quellfile gelöscht.
     * 
     * @param List
     *            <HaldeBuchungssatz> hb
     */
    public void exportBuchungsSatz(List<HaldeBuchungssatzScan> hb);

    /**
     * Liefert den Dateinamen für das Zielverzeichnis.
     * 
     * @param HaldeBuchungssatzScan
     *            hb
     * @return File
     */
    public File getBuchungssatzDestFile4Save(HaldeBuchungssatzScan hb);

    /**
     * Liefert den Dateinamen für das temporäre Verzeichnis.
     * 
     * @param HaldeBuchungssatzScan
     *            hb
     * @return File
     */
    public File getBuchungssatzDestFile4Tmp(HaldeBuchungssatzScan hb);

    /**
     * Liefert die Haldenbuchungssätze für ein PDF-Dokument. Es wird das aktuellste File beginnend
     * mit der fileId im Dateinamen gesucht.
     * 
     * @param fileId
     *            , Entspricht bisher dem Fileprefix, in diesem Fall: getDokumentType()
     * @return List<HaldeBuchungssatz>
     */
    public List<HaldeBuchungssatzScan> getBuchungsSaetze4PDF(String fileId, String userLogin);

    /**
     * Entfernt den entsprechenden Datensatz aus der .csv - Datei. Es wird erwartet, dass eine
     * Überschrift existiert. D.h., index == 0 löscht den ersten Datensatz nach der Überschrift.
     * 
     * @param HaldeBuchungssatzScan
     *            hb
     * @param Integer
     *            index
     */
    public void removeDatensatzByIndex(HaldeBuchungssatzScan hb, Integer index);

    /**
     * Löscht alle Buchungsdatensätze des entsprechenden HaldeBuchungssatzes.
     * 
     * @param HaldeFile
     *            haldeFile
     * @param String
     *            userLogin
     */
    public void removeAllDatensaetze(HaldeFile haldeFile, String userLogin);

    /**
     * Erzwingt das erneute Einlesen der Lieferanten.ini Liste.
     */
    public void initLieferantenListe();

    /**
     * Liefert num Lieferantennamen die dazugehörige Lieferanten-Nr.
     * 
     * @param String
     *            lieferantenName
     * @return String
     */
    public String getLieferantenNr(String lieferantenName);

    /**
     * Liefert zur Lieferenten-Nr den dazugehörigen Lieferantennamen.
     * 
     * @param String
     *            lieferantenNr
     * @return String
     */
    public String getLieferantenName(String lieferantenNr);

    /**
     * Liefert eine Liste des LiefNrName - Objektes.
     * 
     * @return List<LiefNrName>
     */
    public List<LiefNrName> getLieferantenInfo();

    /**
     * Prüft, ob es den Lieferantennamen oder die Lieferantennummer gibt.
     * 
     * @param String
     *            nrName
     * @return boolean
     */
    public boolean checkIfNrOrNameExist(String nrName);

    /**
     * Prüft, ob es zu diesem Buchungssatz schon einen im Zielverzeichnis gibt. (U.u. eines anderen
     * Benuzters !)
     * 
     * @param HaldeBuchungssatzScan
     *            hb
     * @return boolean
     */
    public boolean checkIfBuchungssatzExist(HaldeBuchungssatzScan hb);
}
