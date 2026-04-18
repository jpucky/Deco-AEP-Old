// $Log: Const.java,v $
// Revision 1.13  2017/06/22 13:36:08  tw
// Mobilmachung der Headrevision.
//
// Revision 1.12  2016/01/11 22:50:19  tw
// CR 3956: Interner Umbau: Lucene-Felder.
//
// Revision 1.11  2015/07/21 17:59:19  tw
// Korrektur d. Haldetests.
//
// Revision 1.10  2015/03/19 22:39:26  tw
// CR Feng-ID: 3947#4
//
// Revision 1.9  2015/02/19 14:32:41  tw
// Haldenbearbeitung: Aenderung des Speicherverzeichnisses.
//
// Revision 1.8  2014/11/23 21:53:44  tw
// https://team.decodetron.de:10443/index.php?c=task&a=view_task&id=3939
//
// Revision 1.7  2014/10/02 12:36:33  tw
// Lieferantenname Textfeldanbindung.
//
// Revision 1.6  2014/09/12 15:03:50  tw
// Aep-Benutzerr-Rechteverwaltung: Setzten des Defaultpasswortes.
//
// Revision 1.5  2014/07/30 16:08:55  tw
// Scanbelege, Lucene Objektmapper erstellt, Aufraeumarbeiten.
//
// Revision 1.4  2014/02/28 15:39:49  tw
// Anpassung an neue DB-Struktur: Gebietssleiter.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.3  2014/02/05 13:44:04  tw
// Anpassung an neue DB-Struktur
//
// Revision 1.2  2013/12/18 14:48:11  tw
// AEP-Token-Anmeldung implementiert.
//
// Revision 1.1  2013/11/21 17:40:02  tw
// Neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.4  2013/11/14 01:34:42  tw
// aaaaaaaaaaaaaaargh umlaute.
//
// Revision 1.3  2013/11/13 23:13:20  tw
// Vorbereitung: Schnittstellen fuer Datenfilter.
//
// Revision 1.2  2013/11/13 00:29:59  tw
// Servicefunktionen fuer Berechtigungskonzept implementiert.
//
// Revision 1.1  2013/11/02 21:46:42  tw
// ClubBestand-DB-Schicht. 1. Schritte zur Veralgemeinerung.
//
// Revision 1.1  2013/08/12 10:50:13  tw
// Neues Attribut: anlagedatum erstellt.
//
// Revision 1.1  2013/08/07 01:46:51  tw
// AEP LoginModul als simpelst-keine-Zusatzbibliotheken-Variante. Erster Wurf.
//
// Revision 1.1  2012/10/31 15:23:26  tw
//
//

package de.decodetron.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import de.decodetron.exc.DAOConfigurationException;

/**
 * @author Thomas Winter
 * @since 31.10.2012
 */
public class Const {

    public static String TBL_USER_ID = "id";
    public static String TBL_USER_VORNAME = "vorname";
    public static String TBL_USER_NACHNAME = "nachname";
    public static String TBL_USER_LOGIN = "login";
    public static String TBL_USER_PASSWD = "passwd";
    public static String TBL_USER_ANLAGEDATUM = "anlagedatum";
    public static String TBL_USER_FILTER = "filter";
    public static String TBL_USER_GROUPID = "groupIds";
    public static String TBL_USER_VLEITUNG = "Vertriebsleitung";
    public static String TBL_USER_GEBIET = "Gebiet";

    public static String TBL_GROUP_ID = "id";
    public static String TBL_GROUP_NAME = "name";
    public static String TBL_GROUP_KZ = "kz";
    public static String TBL_GROUP_SECTIONS = "sections";

    public static String TBL_SECTIONS_ID = "id";
    public static String TBL_SECTIONS_MENUITEMID = "MenuItemId";
    public static String TBL_SECTIONS_MENUITEM = "MenuItem";
    public static String TBL_SECTIONS_NAME = "name";
    public static String TBL_SECTIONS_KZ = "kz";
    public static String TBL_SECTIONS_FILTEIDENTIFIER = "filteridentifier";
    public static String TBL_SECTIONS_LOCACTION = "location";
    public static String TBL_SECTIONS_DATABASE = "database";
    public static String TBL_SECTIONS_TABLE = "table";

    public static String TBL_IPALLOWED_ID = "id";
    public static String TBL_IPALLOWED_IP = "ip";

    public static String HALDE_JUNIT = "halde_junit";
    public static String HALDE_BELEGART_SC = "Scanbelege";
    public static String HALDE_BELEGART_SR = "Sammelrechungen";
    public static String HALDE_BELEGART_TB = "Tagesbelege";
    public static String HALDE_BELEGART_EK = "Einkaufsaufträge";

    private static final String PROPERTIES_FILE = "db.properties";
    private static final Properties PROP = new Properties();
    public static String STANDARTPWD_VER = "AB8D2CB0550CCA77D5C9A0831EC5825386E7AB17";

    // /////////////////////////////////////////////////////////////
    // /// Recherche - Lucene
    // /
    // ALT
    // public static String LUC_IDX_ID = "id";
    // public static String LUC_KUNDENNUMMER = "kundennummer"; // lieferantennr
    // public static String LUC_BELEGNUMMER = "belegnummer";
    // public static String LUC_DATUM = "datum";
    // public static String LUC_OFFSET = "offset";
    // public static String LUC_SEITENZAHL = "seitenzahl";
    // public static String LUC_BELEGART = "belegart";
    // public static String LUC_SCDOKUMENT = "scdokument";
    // public static String LUC_LIEFERANTNAME = "lieferantenname";

    // ALT
    //public static String LUC_IDX_ID = "id";
    public static String LUC_IMPDATE = "datum";
    //public static String LUC_OFFSET = "impoffset";
    public static String LUC_BELEGART = "belegart";
    //public static String LUC_DATUM = "datum";
    public static String LUC_BELEGNUMMER = "belegnummer";
    public static String LUC_KUNDENNUMMER = "kundennummer"; // lieferantennr
    public static String LUC_SEITENZAHL = "seitenzahl";
    public static String LUC_BESTELLUNG = "scdokument";
    public static String LUC_LIEFERANTNAME = "lieferantenname";

    // NEU
    // public static String LUC_IDX_ID = "id";
    // public static String LUC_IMPDATE = "impdate";
    // public static String LUC_OFFSET = "impoffset";
    // public static String LUC_BELEGART = "belegart";
    // public static String LUC_DATUM = "date";
    // public static String LUC_BELEGNUMMER = "belegnr";
    // public static String LUC_KUNDENNUMMER = "kundennr"; // lieferantennr
    // public static String LUC_SEITENZAHL = "seitenzahl";
    // public static String LUC_BESTELLUNG = "bestellung";
    // public static String LUC_LIEFERANTNAME = "lieferantenname";

    public static String AEP_TMP_DIRHOME = ".aep";

    private static String AEP_TMP_PDF_HOME = "pdf";
    public static String AEP_TMP_PDF_DIRHOME = "";

    private static String AEP_TMP_CSV_HOME = "csv";
    public static String AEP_TMP_CSV_DIRHOME = "";

    private static String AEP_TMP_HALDE_HOME = "halde";
    public static String AEP_TMP_HALDE_DIRHOME = "";

    public static String LS = System.getProperty("line.separator");
    public static String FS = System.getProperty("file.separator");
    public static String USERNHOME = System.getProperty("user.home");

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);

        if (propertiesFile == null) {
            throw new DAOConfigurationException("Properties file '" + PROPERTIES_FILE + "' is missing in classpath.");
        }

        try {
            PROP.load(propertiesFile);
        } catch (IOException e) {
            throw new DAOConfigurationException("Cannot load properties file '" + PROPERTIES_FILE + "'.", e);
        }

        // //////////////////////////////////////////
        // /// Lokale Arbeitsverzeichnisse anlegen.
        // /// <user.home>.aep/pdf
        // /
        StringBuilder pathOut = new StringBuilder();
        pathOut.append(USERNHOME).append(FS);
        pathOut.append(AEP_TMP_DIRHOME).append(FS);
        pathOut.append(AEP_TMP_PDF_HOME).append(FS);
        AEP_TMP_PDF_DIRHOME = pathOut.toString();
        File pout = new File(Const.AEP_TMP_PDF_DIRHOME);
        if (!pout.exists()) {
            pout.mkdirs();
        }

        // //////////////////////////////////////////
        // /// Lokale Arbeitsverzeichnisse anlegen.
        // /// <user.home>.aep/csv
        // /
        pathOut = new StringBuilder();
        pathOut.append(USERNHOME).append(FS);
        pathOut.append(AEP_TMP_DIRHOME).append(FS);
        pathOut.append(AEP_TMP_CSV_HOME).append(FS);
        AEP_TMP_CSV_DIRHOME = pathOut.toString();
        pout = new File(Const.AEP_TMP_CSV_DIRHOME);
        if (!pout.exists()) {
            pout.mkdirs();
        }

        // //////////////////////////////////////////
        // /// Lokale Arbeitsverzeichnisse anlegen.
        // /// <user.home>.aep/halde
        // /
        pathOut = new StringBuilder();
        pathOut.append(USERNHOME).append(FS);
        pathOut.append(AEP_TMP_DIRHOME).append(FS);
        pathOut.append(AEP_TMP_HALDE_HOME).append(FS);
        AEP_TMP_HALDE_DIRHOME = pathOut.toString();
        pout = new File(Const.AEP_TMP_HALDE_DIRHOME);
        if (!pout.exists()) {
            pout.mkdirs();
        }

    }

}
