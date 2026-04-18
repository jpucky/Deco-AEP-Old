// $Log: AEPApplicationModel.java,v $
// Revision 1.22  2015/05/19 19:58:46  tw
// CR Feng-ID: 3947#11
//
// Revision 1.21  2015/03/12 13:07:02  tw
// Ticket: Haldenbearbeitung erweitern, Vorbereitungen.
//
// Revision 1.20  2015/02/20 20:22:54  tw
// Vorbereitung f. spaetere User-On-Time.db Absplittung aus decoUser.db.
//
// Revision 1.19  2015/01/25 14:34:34  tw
// Vorabentwurf f. Lueckenprotokoll implementiert.
//
// Revision 1.18  2014/10/23 21:02:43  tw
// Haldenstatus fuer  Dateien m. Datum im Filenamen erweitert.
//
// Revision 1.17  2014/10/17 08:39:51  tw
// Haldenstatus fuer 2 Dateien erweitert.
//
// Revision 1.16  2014/10/14 16:32:02  tw
// Haldenstatus, Anpassung an csv.
//
// Revision 1.15  2014/10/01 22:02:53  tw
// Lieferantenname Textfeldanbindung.
//
// Revision 1.14  2013/11/29 12:32:37  tw
// Neue Liste: Valuta.
//
// Revision 1.13  2013/11/28 17:18:11  tw
// Neue Liste: Ueberweiser.
//
// Revision 1.12  2013/11/28 14:35:02  tw
// Neue Liste: Transfusion.
//
// Revision 1.11  2013/11/28 13:42:27  tw
// Anbindung Liste:Tierarznei.
//
// Revision 1.10  2013/11/27 13:45:19  tw
// TODO: https://team.decodetron.de:10443/index.php?c=task&a=view_task&id=3929
// Bugfix: https://team.decodetron.de:10443/index.php?c=task&a=view_task&id=3933 1a.
//
// Revision 1.9  2013/11/18 21:49:47  tw
// auswaehlbarer von- bis suchbereich implementiert. aufraeumarbeiten.
//
// Revision 1.8  2013/11/10 20:26:10  tw
// Statistik: Reimporte
//
// Revision 1.7  2013/11/09 19:01:12  tw
// Defektenliste, Zusammenfassungsarbeiten, Nullponter-Exception Sortierung behoben.
//
// Revision 1.6  2013/11/08 12:50:25  tw
// Club-Abverkauf implementiert.
//
// Revision 1.5  2013/11/08 09:48:53  tw
// chargenliste implementiert.
//
// Revision 1.4  2013/11/08 00:28:16  tw
// BTM-Liste implementiert.
//
// Revision 1.3  2013/11/06 22:40:05  tw
// Neue Schnittstellentests f. flexiblere Abfragen.
//
// Revision 1.2  2013/11/02 23:18:16  tw
// ClubBestand-DB-Schicht. 1. Schritte zur Veralgemeinerung.
//
// Revision 1.1  2013/10/31 00:19:09  tw
// Konfiguration der Quelldatenverzeichnisse von aussen moeglich.
//
//

package de.decodetron;

/**
 * DatenTonne für Appliktionsfinformationen.
 * 
 * @author Thomas Winter
 * @since 30.10.2013
 */
public class AEPApplicationModel {

    private String fileDefekte;
    private String fileBtmDB;
    private String fileReimportDB;
    private String fileUserDB;
    private String fileUserOnTimeDB;
    private String fileClubDBTest;
    private String fileClubDBAbverkauf;
    private String fileChargenDB;
    private String fileClubDBBestand;
    private String fileHochpreisDB;
    private String fileTierarzneiDB;
    private String fileTransfusionDB;
    private String fileUeberweiserDB;
    private String fileValutaDB;

    private String dirRootScan;
    private String fileOffeneScan;
    private String fileOffeneArchivierung;
    private String fileLueckenProtokoll;
    private String fileLueckenProtokollSR;

    private String pathArchivT;
    private String pathLucy;
    private String pathLucyScan;
    private String buildNumber;

    /**
     * @return the fileUserDB
     */
    public String getFileUserDB() {
        return fileUserDB;
    }

    /**
     * @param fileUserDB
     *            the fileUserDB to set
     */
    public void setFileUserDB(String fileUserDB) {
        this.fileUserDB = fileUserDB;
    }

    /**
     * @return the defekte
     */
    public String getFileDefekte() {
        return fileDefekte;
    }

    /**
     * @param defekte
     *            the defekte to set
     */
    public void setFileDefekte(String defekte) {
        this.fileDefekte = defekte;
    }

    // /**
    // * @return the fileClubDB
    // */
    // public String getFileClubTestDB() {
    // return fileClubDBTest;
    // }
    //
    // /**
    // * @param fileClubDB
    // * the fileClubDB to set
    // */
    // public void setFileClubTestDB(String fileClubDB) {
    // this.fileClubDBTest = fileClubDB;
    // }

    /**
     * @return the fileClubDBAbverkauf
     */
    public String getFileClubDBAbverkauf() {
        return fileClubDBAbverkauf;
    }

    /**
     * @param fileClubDBAbverkauf
     *            the fileClubDBAbverkauf to set
     */
    public void setFileClubDBAbverkauf(String fileClubDBAbverkauf) {
        this.fileClubDBAbverkauf = fileClubDBAbverkauf;
    }

    /**
     * @return the pathArchivT
     */
    public String getPathArchivT() {
        return pathArchivT;
    }

    /**
     * @param pathArchivT
     *            the pathArchivT to set
     */
    public void setPathArchivT(String pathArchivT) {
        this.pathArchivT = pathArchivT;
    }

    /**
     * @return the pathLucy
     */
    public String getPathLucy() {
        return pathLucy;
    }

    /**
     * @param pathLucy
     *            the pathLucy to set
     */
    public void setPathLucy(String pathLucy) {
        this.pathLucy = pathLucy;
    }

    /**
     * @return the buildNumber
     */
    public String getBuildNumber() {
        return buildNumber;
    }

    /**
     * @param buildNumber
     *            the buildNumber to set
     */
    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    /**
     * @return the fileClubDB20131106
     */
    public String getFileClubDBBestand() {
        return fileClubDBBestand;
    }

    /**
     * @param fileClubDB20131106
     *            the fileClubDB20131106 to set
     */
    public void setFileClubDBBestand(String d) {
        this.fileClubDBBestand = d;
    }

    /**
     * @return the fileBtmDB
     */
    public String getFileBtmDB() {
        return fileBtmDB;
    }

    /**
     * @param fileBtmDB
     *            the fileBtmDB to set
     */
    public void setFileBtmDB(String fileBtmDB) {
        this.fileBtmDB = fileBtmDB;
    }

    /**
     * @return the fileChargenDB
     */
    public String getFileChargenDB() {
        return fileChargenDB;
    }

    /**
     * @param fileChargenDB
     *            the fileChargenDB to set
     */
    public void setFileChargenDB(String fileChargenDB) {
        this.fileChargenDB = fileChargenDB;
    }

    /**
     * @return the fileReimportDB
     */
    public String getFileReimportDB() {
        return fileReimportDB;
    }

    /**
     * @param fileReimportDB
     *            the fileReimportDB to set
     */
    public void setFileReimportDB(String fileReimportDB) {
        this.fileReimportDB = fileReimportDB;
    }

    /**
     * @return the fileClubDBTest
     */
    public String getFileClubDBTest() {
        return fileClubDBTest;
    }

    /**
     * @param fileClubDBTest
     *            the fileClubDBTest to set
     */
    public void setFileClubDBTest(String fileClubDBTest) {
        this.fileClubDBTest = fileClubDBTest;
    }

    /**
     * @return the fileHochpreisDB
     */
    public String getFileHochpreisDB() {
        return fileHochpreisDB;
    }

    /**
     * @param fileHochpreisDB
     *            the fileHochpreisDB to set
     */
    public void setFileHochpreisDB(String fileHochpreisDB) {
        this.fileHochpreisDB = fileHochpreisDB;
    }

    /**
     * @return the fileTierarzneiDB
     */
    public String getFileTierarzneiDB() {
        return fileTierarzneiDB;
    }

    /**
     * @param fileTierarzneiDB
     *            the fileTierarzneiDB to set
     */
    public void setFileTierarzneiDB(String fileTierarzneiDB) {
        this.fileTierarzneiDB = fileTierarzneiDB;
    }

    /**
     * @return the fileTransfusionDB
     */
    public String getFileTransfusionDB() {
        return fileTransfusionDB;
    }

    /**
     * @param fileTransfusionDB
     *            the fileTransfusionDB to set
     */
    public void setFileTransfusionDB(String fileTransfusionDB) {
        this.fileTransfusionDB = fileTransfusionDB;
    }

    /**
     * @return the fileUeberweiserDB
     */
    public String getFileUeberweiserDB() {
        return fileUeberweiserDB;
    }

    /**
     * @param fileUeberweiserDB
     *            the fileUeberweiserDB to set
     */
    public void setFileUeberweiserDB(String fileUeberweiserDB) {
        this.fileUeberweiserDB = fileUeberweiserDB;
    }

    /**
     * @return the fileValutaDB
     */
    public String getFileValutaDB() {
        return fileValutaDB;
    }

    /**
     * @param fileValutaDB
     *            the fileValutaDB to set
     */
    public void setFileValutaDB(String fileValutaDB) {
        this.fileValutaDB = fileValutaDB;
    }

    /**
     * @return the pathLucyScan
     */
    public String getPathLucyScan() {
        return pathLucyScan;
    }

    /**
     * @param pathLucyScan
     *            the pathLucyScan to set
     */
    public void setPathLucyScan(String pathLucyScan) {
        this.pathLucyScan = pathLucyScan;
    }

    /**
     * @return the fileHaldenStatus
     */
    public String getFileOffeneScan() {
        return fileOffeneScan;
    }

    /**
     * @param fileHaldenStatus
     *            the fileHaldenStatus to set
     */
    public void setFileOffeneScan(String fileHaldenStatus) {
        this.fileOffeneScan = fileHaldenStatus;
    }

    /**
     * @return the fileOffeneArchivierung
     */
    public String getFileOffeneArchivierung() {
        return fileOffeneArchivierung;
    }

    /**
     * @param fileOffeneArchivierung
     *            the fileOffeneArchivierung to set
     */
    public void setFileOffeneArchivierung(String fileOffeneArchivierung) {
        this.fileOffeneArchivierung = fileOffeneArchivierung;
    }

    /**
     * Das Verzeichnis für die Scanhalde zum runterladen der Liste.
     * 
     * @return the dirRootScan
     */
    public String getDirRootScan() {
        return dirRootScan;
    }

    /**
     * Das Verzeichnis für die Scanhalde zum runterladen der Liste.
     * 
     * @param dirRootScan
     *            the dirRootScan to set
     */
    public void setDirRootScan(String dirRootScan) {
        this.dirRootScan = dirRootScan;
    }

    /**
     * @return the lueckenProtokoll
     */
    public String getFileLueckenProtokoll() {
        return fileLueckenProtokoll;
    }

    /**
     * @param lueckenProtokoll
     *            the lueckenProtokoll to set
     */
    public void setFileLueckenProtokoll(String lueckenProtokoll) {
        this.fileLueckenProtokoll = lueckenProtokoll;
    }

    /**
     * @param lueckenProtokoll
     *            the lueckenProtokoll to set
     */
    public void setFileLueckenProtokollSR(String lueckenProtokollsr) {
        this.fileLueckenProtokollSR = lueckenProtokollsr;
    }

    public String getFileLueckenProtokollSR() {
        return fileLueckenProtokollSR;
    }

    /**
     * @return the fileUserOnTimeDB
     */
    public String getFileUserOnTimeDB() {
        return fileUserOnTimeDB;
    }

    /**
     * @param fileUserOnTimeDB
     *            the fileUserOnTimeDB to set
     */
    public void setFileUserOnTimeDB(String fileUserOnTimeDB) {
        this.fileUserOnTimeDB = fileUserOnTimeDB;
    }

}
