// $Log: Const.java,v $
// Revision 1.90  2020/02/09 15:31:09  tw
// Argh. Umlaute!
//
// Revision 1.89  2020/02/09 15:06:29  tw
// CR: Passort-aendern Dialog: Das Standartpasswort muss geaendert werden, ist keine Empfehlung mehr.
//
// Revision 1.88  2017/07/05 21:40:14  tw
// Version 1.19-H. PDF-Bereiniger deaktiviert.
//
// Revision 1.87  2017/07/04 01:07:31  tw
// Mobilmachung der Headrevision. Beseitigung des PDF-Speicherlecks.
//
// Revision 1.86  2017/06/26 10:11:54  tw
// Lucene-Offsetfeld: Trennzeichen von # auf @ umgestellt.
//
// Revision 1.85  2017/06/23 11:57:57  tw
// Mobilmachung der Headrevision.
//
// Revision 1.84  2016/02/05 15:47:50  tw
// CR 3956: Interner Umbau: Events separiert.
//
// Revision 1.83  2016/01/31 17:00:14  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.82  2016/01/19 23:15:06  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.81  2016/01/12 21:26:22  tw
// CR 3956: Interner Umbau: Lucene-Felder. Rueckbau d. Textfeld-Id Verlaengerung.
//
// Revision 1.80  2015/12/16 20:41:39  tw
// CR 3953: Archivierung von Retourenbelegen.
//
// Revision 1.79  2015/12/03 16:59:23  tw
// CR 3953: Archivierung von Retourenbelegen, Aufraeumarbeiten, Vorbereitung.
//
// Revision 1.78  2015/05/19 19:58:46  tw
// CR Feng-ID: 3947#11
//
// Revision 1.77  2015/03/19 22:41:04  tw
// CR Feng-ID: 3947#4
//
// Revision 1.76  2015/03/17 22:10:52  tw
// CR Feng-ID: 3947
//
// Revision 1.75  2015/02/26 12:46:45  tw
// Haldenbearbeitung: Fehlerpruefung, Bugfixing.
//
// Revision 1.74  2015/02/19 14:33:26  tw
// Haldenbearbeitung: Aenderung des Speicherverzeichnisses.
//
// Revision 1.73  2015/02/11 11:47:48  tw
// Haldenbearbeitung: Aktivieren der Autocomplete-Funktion. Verlagern der Skripte.
//
// Revision 1.72  2015/02/09 12:47:13  tw
// Haldenbearbeitung: Bugfix. Aufraeumarbeiten.
//
// Revision 1.71  2015/02/08 17:12:18  tw
// Haldenbearbeitung: Datensatz-bearbeiten Aenderungserkennung impl.
//
// Revision 1.70  2015/01/30 02:44:51  tw
// Haldenbearbeitung 1. Wurf.
//
// Revision 1.69  2015/01/25 14:34:34  tw
// Vorabentwurf f. Lueckenprotokoll implementiert.
//
// Revision 1.68  2015/01/23 17:19:38  tw
// Doppelklickversuche implementiert.
//
// Revision 1.67  2014/10/03 15:06:33  tw
// Implementierung: Haldenstatus.
//
// Revision 1.66  2014/10/01 22:02:53  tw
// Lieferantenname Textfeldanbindung.
//
// Revision 1.65  2014/09/12 15:04:16  tw
// Aep-Benutzerr-Rechteverwaltung: Setzten des Defaultpasswortes.
//
// Revision 1.64  2014/09/10 16:15:41  tw
// Aep-Benutzerr-Rechteverwaltung: Aufraeumarbeiten, Klassen entdroeselt.
//
// Revision 1.63  2014/08/21 22:04:51  tw
// Aep-Benutzerr-Rechteverwaltung: Layout.
//
// Revision 1.62  2014/08/14 21:52:05  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.61  2014/08/12 01:03:24  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.60  2014/08/11 12:32:07  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.59  2014/08/09 00:16:19  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.58  2014/08/08 16:05:02  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.57  2014/08/01 14:21:42  tw
// Scanbelege, Oberflaechenapassungen, Fundstellensuche.
//
// Revision 1.56  2014/07/31 14:10:16  tw
// Scanbelege, Oberflaechenapassungen.
//
// Revision 1.55  2014/07/30 16:08:37  tw
// Scanbelege, Lucene Objektmapper erstellt, Aufraeumarbeiten.
//
// Revision 1.54  2014/07/17 22:05:20  tw
// Extrawurst-Label f. Scanbelege erstellt.
//
// Revision 1.53  2014/07/16 14:27:58  tw
// Bugfix Benutzer aendern.
//
// Revision 1.52  2014/06/28 16:00:37  tw
// Benutzer bearbeiten finalisiert. Busy-Indicator f. Statistik u. Benutzer Bearbeiten.
//
// Revision 1.51  2014/06/17 11:51:33  tw
// Benutzer bearbeiten,  Passwortaenderung, Bugfix.
//
// Revision 1.50  2014/06/12 12:29:20  tw
// Benutzer bearbeiten: Vorbereitung f. korrekte Gruppenverarbeitung.
//
// Revision 1.49  2014/05/12 20:45:09  tw
// Aufraeumarbeiten, Benutzer anlegen loeschen.
//
// Revision 1.48  2014/05/11 10:59:56  tw
// Backup: Benutzer loeschen.
//
// Revision 1.47  2014/04/24 13:36:05  tw
// Aufraeumarbeiten: Listeneintraege 'Statistik' werden jetzt sauber als Sections verwaltet.
//
// Revision 1.46  2014/04/02 14:28:33  tw
// pdf, aufraeumarbeiten, neue xsl-struktur.
//
// Revision 1.45  2014/03/20 02:22:22  tw
// Recherche: Paginierung, Funktionstest. Event-Konstanten zusammengefasst.
//
// Revision 1.44  2014/03/06 00:49:13  tw
// Recherche: Paginierung, Oberflaechenanbindung (auskommentiert).
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.43  2014/03/03 19:29:51  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Recherche: Paginierung.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.42  2014/02/15 11:58:48  tw
// Implementierung: Passwort zuruecksetzen.
//
// Revision 1.41  2014/02/14 17:03:23  tw
// Implementierung: Passwort zuruecksetzen.
//
// Revision 1.40  2014/02/13 02:03:18  tw
// Benutzerverwaltung, dynamischer Suchfilteraufbau.
//
// Revision 1.39  2014/02/11 02:10:22  tw
// Zwangsteuerung f. Standartpasswortaenderung implementiert.
//
// Revision 1.38  2014/02/07 02:07:05  tw
// Restzeitaktualisierung bei Aktivitaeten implementiert.
//
// Revision 1.37  2014/02/06 03:03:47  tw
// Scanbelege, Anpassung an neue DB-Struktur
//
// Revision 1.36  2014/02/03 22:45:36  tw
// Implementierung Benutzerverwaltung
//
// Revision 1.35  2014/01/30 23:19:16  tw
// Belegart: EK. Spaltenbezeichner Anpassung.
//
// Revision 1.34  2014/01/30 17:39:11  tw
// Umbau: Belegart-Auswahl. Backup.
//
// Revision 1.33  2014/01/29 19:52:54  tw
// Umbau: Belegart-Auswahl.
//
// Revision 1.32  2014/01/16 12:34:51  tw
// Anzeige Treffermenge. Pdfgenerierung.
//
// Revision 1.31  2014/01/15 21:44:32  tw
// Bufix: Beseitigung aller KZs. Verbergen alle bisherigen Aenderungen.
//
// Revision 1.30  2014/01/10 15:03:40  tw
// Statistik: Listenausgabe als pdf. Erster Durchstich d. Defektenliste.
//
// Revision 1.29  2013/12/05 20:10:52  tw
// Bugfix Suche. Performancesteigerung: Defektenliste.
//
// Revision 1.28  2013/12/04 14:17:19  tw
// csv-liste fuer btm implementiert.
//
// Revision 1.27  2013/11/29 12:32:37  tw
// Neue Liste: Valuta.
//
// Revision 1.26  2013/11/28 17:18:11  tw
// Neue Liste: Ueberweiser.
//
// Revision 1.25  2013/11/28 14:35:02  tw
// Neue Liste: Transfusion.
//
// Revision 1.24  2013/11/28 13:42:27  tw
// Anbindung Liste:Tierarznei.
//
// Revision 1.23  2013/11/27 13:45:19  tw
// TODO: https://team.decodetron.de:10443/index.php?c=task&a=view_task&id=3929
// Bugfix: https://team.decodetron.de:10443/index.php?c=task&a=view_task&id=3933 1a.
//
// Revision 1.22  2013/11/25 12:22:15  tw
// Chargendoku: limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.21  2013/11/25 11:02:06  tw
// Limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.20  2013/11/21 17:40:46  tw
// Vorbereitung: neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.19  2013/11/17 16:18:44  tw
// Recherche an den Multifilter angebunden. Modulumstellung auf KZ.
//
// Revision 1.18  2013/11/17 02:11:19  tw
// Mapping zurueckgenommen
//
// Revision 1.17  2013/11/17 01:54:41  tw
// Mapping der Statistik-Daten.
//
// Revision 1.16  2013/11/13 01:17:39  tw
// Statistik - Listen ans Berechtigungskonzept angebunden.
//
// Revision 1.15  2013/11/10 20:26:10  tw
// Statistik: Reimporte
//
// Revision 1.14  2013/11/09 19:01:12  tw
// Defektenliste, Zusammenfassungsarbeiten, Nullponter-Exception Sortierung behoben.
//
// Revision 1.13  2013/11/08 12:50:25  tw
// Club-Abverkauf implementiert.
//
// Revision 1.12  2013/11/08 09:48:53  tw
// chargenliste implementiert.
//
// Revision 1.11  2013/11/08 00:28:16  tw
// BTM-Liste implementiert.
//
// Revision 1.10  2013/11/07 22:42:09  tw
// Clubliste geradegezogen.
//
// Revision 1.9  2013/11/07 22:03:46  tw
// Clubliste geradegezogen.
//
// Revision 1.8  2013/11/05 23:03:23  tw
// Experimente, um CVS-Listen generischer Lesen zu koennen.
//
// Revision 1.7  2013/11/05 03:20:16  tw
// Paginierung begonnen.
//
// Revision 1.6  2013/10/31 00:14:26  tw
// Konfiguration der Quelldatenverzeichnisse von aussen moeglich.
//
// Revision 1.5  2013/10/30 12:23:00  tw
// Anbindung eines Tab-Reiters.
//
// Revision 1.4  2013/10/27 20:28:38  tw
// Umlaute
//
// Revision 1.3  2013/10/27 17:42:28  tw
// PDF-Ablage geaendert. Layoutanpassung: PDF-Ansicht als Overflow:hidden.
//
// Revision 1.2  2013/10/27 17:25:40  tw
// PDF-Ablage geaendert. Layoutanpassung: PDF-Ansicht als Overflow:hidden.
//
// Revision 1.1  2013/10/25 01:59:00  tw
// Anpassung an Rollout.
//
//

package de.decodetron;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;

/**
 * @author Thomas Winter
 * @since 25.10.2013
 */
public class Const {

    public static int ITEMS_PER_PAGE = 250;
    public static int ITEMS_PER_PAGE_SMALL = 10;
    public static int ITEMS_PER_DOWNLOAD = 5000;

    public static String KEYDOCTOTAL = "total";
    public static String KEYHITSPERPAGE = "";
    public static String KEYSHOWPERPAGE = "show";

    public static String STANDARTPWD = "archiv";

    public static String KEYURLPDF = "urlpdf"; // Die URL-Adressierung des PDFs.
    public static String KEYKDNR = "kundenNr";

    public static String KEYFUNDSTELLE = "fundstelle";
    //public static String KEYFUNDSTELLEKLICK = "fundstelleclicked";
    public static String KEYFUNDSTELLEDBLKLICK = "fundstelledblclicked";
    public static String KEYPAGINGKLICK = "pagingclicked";
    public static String KEYSTARTSEARCH = "startsearch";
    public static String KEYTOGGLEDITEM = "toggledItem";

    public static String KEYSEARCHBUSY = "searchBusy";
    public static String STARTTIME = "startTime";
    public static String DEFAULTPWDSET = "defaultPwdSet";

    // Konstante, um dem Offset eine Archivdatum-Zusatzinformation mitzuliefern
    public static String KEY_OFFSET_DIVIDER = "@";

    // /////////////////////////////////////////////////////////////
    // /// Benutzerverwaltung
    // /
    public static String KEY_BENW_DUMMY = "**************";
    public static String KEY_BENW_RESETPWD = "resetPwd";
    public static String KEY_BENW_RESETPWD2 = "resetPwd2";
    public static String KEY_BENW_RESETPWD_PANEL = "pwz";
    public static String KEY_BENW_BENANLEGEN = "bea";
    public static String KEY_USER_2CHANGE = "geaendertebenutzer";
    public static String KEY_USER_SECTION_2CHANGE = "userSection2Change";
    public static String KEY_USER_ATTRIB_2CHANGE_SELECTED = "userAttrib2ChangeSelected";
    public static String KEY_USER_SECTION_ORIGINAL = "userSectionOriginal";
    public static String KEY_BENDELETE_SELEKTED = "BenutzerLoeschenSelektiert";
    public static String KEY_USER_SECTION_ERROR = "userSectionErrorList";

    // /////////////////////////////////////////////////////////////
    // /// Administration
    // /
    public static String KEY_ADMIN_BENSTAT = "bes";
    public static String KEY_ADMIN_HALDENSTAT = "has";
    public static String KEY_ADMIN_LUECKENPROT = "lue";
    public static String KEY_ADMIN_LUECKENPROT_SR = "luesr";
    public static String KEY_ADMIN_HALDEBEARBEITEN = "hbe";
    public static String REG_EX_ISNUMBER = "[0-9]+";

    // Halde-Events
    public static String KEY_HALDE_HALDEFILE_KLICK = "fundstelleclicked-halde";
    public static String KEY_HALDE_BUSA_ADDED = "haldebusa_added";
    public static String KEY_HALDE_BUSA_SAVED = "haldebusa_saved";
    public static String KEY_HALDE_BUSA_MODIFIED = "haldebusa_Modified";
    public static String KEY_HALDE_BUSA_DELETED = "haldebusa_deleted";
    public static String KEY_HALDE_BUSA_ERROROCCURED = "error_occured";

    // /////////////////////////////////////////////////////////////
    // /// Recherche
    // /
    public static String KEY_BELART_SELECTED = "belegartselected";
    public static List<String> KEY_PANEL_TB = Arrays.asList("LS", "NB", "GS", "SR");
    public static List<String> KEY_PANEL_EK = Arrays.asList("EK");
    public static List<String> KEY_PANEL_SCAN = Arrays.asList("SCLF", "SCRG", "SCSD");
    public static List<String> KEY_PANEL_SCSNRET = Arrays.asList("SCRT");

    // /////////////////////////////////////////////////////////////
    // /// Statistik
    // /
    public static String KEY_SELECTED_BELEGART = "bbartkz";
    public static String KEY_DD_BELEGTYP = "ddTypSelected";
    public static String KEY_DD_BELEGART = "ddBartSelected";
    public static String KEY_LISTENTYP_KLEIN = "TestListe klein";
    public static String KEY_LISTENTYP_GROSS = "TestListe gross";
    public static String KEY_LISTENTYP_DEFAULTEMPTY = "- - - - - - - -";

    public static String KEY_LIST_ISASCENDING = "isAscending";
    public static String KEY_LIST_COLNR2SORT = "colNrToSort";
    public static String KEY_LIST_OFFSET = "offset";
    public static String KEY_LIST_COL_NAME_2SORT = "colname2Sort";
    public static String KEY_BTN_FILTERSUCHE = "btnFilterSuche";
    public static String KEY_DELETE_USERLIST = "user2Delete";

    public static String TABLENAME_USER = "USER";
    public static String TABLENAME_BTM = "BTM";
    public static String TABLENAME_CHARGEN = "CHARGEN";
    public static String TABLENAME_BESTAND = "BESTAND";
    public static String TABLENAME_ABVERKAUF = "ABVERKAUF";
    public static String TABLENAME_DEFEKTE = "DEFEKTE";
    public static String TABLENAME_REIMPORTE = "REIMP";
    public static String TABLENAME_HOCHPREISER = "HOCHPR";
    public static String TABLENAME_TIERARZNEI = "TIERARZ";
    public static String TABLENAME_TRANSFUSION = "TFG";
    public static String TABLENAME_UEBERWEISER = "UEBE";
    public static String TABLENAME_VALUTA = "VALUTA";

    // public static String HALDE_BELEGART_SC = "Scanbelege";
    // public static String HALDE_BELEGART_SR = "Sammelrechungen";
    // public static String HALDE_BELEGART_TB = "Tagesbelege";
    // public static String HALDE_BELEGART_EK = "Einkaufsaufträge";

    public static String KEY_NAVI_COMBOACTION = "navi-comboaction";
    public static String KEY_LIST_RECHERCHE_BELEGART = "recherche-belegarten";
    public static String ACTION_STAT_SUCHESTARTED = "statistiksuche";
    public static String KEY_LIST_FILTERSUCHE = "statistiksuche-list-textfields";
    /**
     * Der Schlüssel hat die Besonderheit, dass er dynamisch aus Sektionsnamen und einem konstanten
     * Alles-Schlüssel in der Funstellensuche#initDropdownBelegarten() zusammengebaut wird. Wenn er
     * geändert wird, muss die Implementierung in initDropdownBelegarten() geändert werden.
     */
    public static String KEY_ALLE_SCANBELEGE = "Scanbelege-Alles";
    // Extrawurst: Scan-Retouren: Stellen ein Spezialfall/Unterkategorie der Scanbelege dar.
    public static String KEY_SCANBELEGE_RETOURE = "SCRT";

    // TODO: Durch eine DB-Funktion getAllScanSections() ersetzen ?
    // public static List<String> SCANBELEGE = Arrays.asList("SCLF", "SCRG", "SCRT", "SRSD");
    public static String LBL1_DEFAULT = "Kunden-Nr.";
    public static String LBL2_DEFAULT = "Beleg-Nr.";

    public static HashMap<String, String> LBL_KDLIEF_NR = new HashMap<String, String>();
    public static HashMap<String, String> LBL_BELBES_NR = new HashMap<String, String>();

    public static String ERROR = "error";
    public static String PDF_HOME_ALIAS = "pdf";
    public static String AEP_TMP_DIRHOME = de.decodetron.util.Const.AEP_TMP_DIRHOME;
    public static String AEP_TMP_PDF_DIRHOME = de.decodetron.util.Const.AEP_TMP_PDF_DIRHOME;
    public static String AEP_TMP_CSV_DIRHOME = de.decodetron.util.Const.AEP_TMP_CSV_DIRHOME;

    //public static String AEP_XSL_HOME = "xsl";

    public static String LS = System.getProperty("line.separator");
    public static String FS = System.getProperty("file.separator");
    public static String USERNHOME = System.getProperty("user.home");
    public static String APPLICATIONHOME = System.getProperty("user.dir");
    
    public static String PWD_CHANGE_SOFT = "soft";
    public static String PWD_CHANGE_FORCE = "force";

    static {
        try {
            AEPClassLoader.loadClasses();
            LBL_KDLIEF_NR.put("EK", "Lieferanten-Nr.");
            LBL_KDLIEF_NR.put("SCLF", "Lieferanten-Nr.");
            LBL_KDLIEF_NR.put("SCRG", "Lieferanten-Nr.");
            LBL_KDLIEF_NR.put("SCRT", "Lieferanten-Nr.");
            LBL_KDLIEF_NR.put("SRSD", "Lieferanten-Nr.");
            LBL_KDLIEF_NR.put(KEY_SCANBELEGE_RETOURE, "Kunden-Nr. (BGA)");

            LBL_BELBES_NR.put("SCLF", "Beleg-Nr.");
            LBL_BELBES_NR.put("SCRG", "Beleg-Nr.");
            LBL_BELBES_NR.put("SCRT", "Beleg-Nr.");
            LBL_BELBES_NR.put("SRSD", "Beleg-Nr.");
            LBL_BELBES_NR.put(KEY_SCANBELEGE_RETOURE, "Beleg-Nr. (GPE)");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Deprecated
    public static String getLbl_BELBES_Nr(List<String> key) {
        if ((key != null) && (key.size() == 1)) {
            return getLbl_BELBES_Nr(key.get(0));
        } else if ((key != null) && (key.size() > 1)) {
            if (CollectionUtils.containsAny(key, LBL_BELBES_NR.keySet())) {
                return "Bestell-Nr.";
            } else {
                return LBL2_DEFAULT;
            }
        } else {
            return LBL2_DEFAULT;
        }
    }

    /**
     * LABEL 1. Holt für einen Satz an Keys das passende Label. Ist momentan nur für Scanbelege
     * implementiert, muss u.u erweitert werden!
     * 
     * @param List
     *            <String> key
     * @return String
     */
    @Deprecated
    public static String getLbl_KDLIEF_Nr(List<String> key) {

        if ((key != null) && (key.size() == 1)) {
            return getLbl_KDLIEF_Nr(key.get(0));
        } else if ((key != null) && (key.size() > 1)) {
            if (CollectionUtils.containsAny(key, LBL_KDLIEF_NR.keySet())) {
                return "Lieferanten-Nr.";
            } else {
                return LBL1_DEFAULT;
            }
        } else {
            return LBL1_DEFAULT;
        }
    }

    /**
     * LABEL 1. Holt für ein Key den passenden Labelbezeichner.
     * 
     * @param String
     *            key
     * @return String
     * 
     */
    @Deprecated
    public static String getLbl_KDLIEF_Nr(String key) {
        if (LBL_KDLIEF_NR.containsKey(key)) {
            return LBL_KDLIEF_NR.get(key);
        } else {
            return LBL1_DEFAULT;
        }
    }

    /**
     * LABEL 2. Holt für ein Key den passenden Labelbezeichner.
     * 
     * @param String
     *            key
     * @return String
     * 
     */
    @Deprecated
    public static String getLbl_BELBES_Nr(String key) {
        if (LBL_BELBES_NR.containsKey(key)) {
            return LBL_BELBES_NR.get(key);
        } else {
            return LBL2_DEFAULT;
        }
    }

    /**
     * Liefert die vollstände Base-Url inclusive Protokoll
     * 
     * @return
     */
    public static String getFullBaseUrl() {

        StringBuilder url = new StringBuilder();

        WebRequest req = (WebRequest) RequestCycle.get().getRequest();
        HttpServletRequest httpReq = (HttpServletRequest) req.getContainerRequest();
        Integer port = RequestCycle.get().getUrlRenderer().getBaseUrl().getPort();

        url.append(httpReq.isSecure() ? "https://" : "http://");
        // url.append("http://");
        url.append(RequestCycle.get().getUrlRenderer().getBaseUrl().getHost());
        url.append(port != null ? ":" + port : "");
        // url.append(":8080");
        url.append(WebApplication.get().getServletContext().getContextPath() + "/");

        return url.toString();
    }
}
