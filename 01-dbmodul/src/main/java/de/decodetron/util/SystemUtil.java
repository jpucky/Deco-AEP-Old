// $Log: SystemUtil.java,v $
// Revision 1.4  2015/07/14 13:04:46  tw
// Implementierung: Lucken-SR. DB-Anbindung.
//
// Revision 1.3  2015/03/18 15:23:28  tw
// CR Feng-ID: 3947
//
// Revision 1.2  2015/01/30 02:43:36  tw
// Haldenbearbeitung 1. Wurf.
//
// Revision 1.1  2013/11/21 17:40:02  tw
// Neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.1  2013/11/02 21:46:42  tw
// ClubBestand-DB-Schicht. 1. Schritte zur Veralgemeinerung.
//
// Revision 1.5  2013/09/17 02:05:43  tw
// Login-Logout-Mechanismus implementiert.
//
// Revision 1.4  2013/09/16 14:35:39  tw
// Backup, Einbau: Login-Mechanismus
//
// Revision 1.3  2013/09/15 19:59:04  tw
// Tests f. Importmodul fertig.
//
// Revision 1.2  2013/09/11 22:06:22  tw
// Backup.
//
// Revision 1.1  2013/08/07 01:46:51  tw
// AEP LoginModul als simpelst-keine-Zusatzbibliotheken-Variante. Erster Wurf.
//
//

package de.decodetron.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.CharSet;

/**
 * @author Thomas Winter
 * @since 15.06.2013
 */
public class SystemUtil {

    private static final String LS = System.getProperty("line.separator");

    /**
     * Load a properties file from the classpath.
     * 
     * @param String
     *            propsName
     * @return Properties
     * @throws Exception
     */
    public Properties loadSystemProperties(String propsName) {
        Properties props = new Properties();
        // will cause db.properties to be looked for based on the package name of the class
        // URL url = ClassLoader.getSystemResource(propsName);

        // !!! Damit Junit nicht auf die Nase fällt !!!
        // load from the root of the classpath ...
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(propsName);

        try {
            // props.load(url.openStream());
            // props.load(in);
            String propertyFileContents = getStringFromInputStream(in);
            props.load(new StringReader(propertyFileContents.replace("\\", "/")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    private static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line).append(LS); // \n wichtig für den propertie-parser!
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * Setzt aus dem Root-Projektverzeichnis und dem relativen Pfad ein Directory zusammen.<br>
     * 
     * 18.03.2015, Erweiterung:<br>
     * 1.) Fängt eine Pfadangabe mit "\\" an, so wird der rootPath ignoriert und es wird ein
     * UNC-Pfad zusammengesetzt.<br>
     * 2.) Fängt eine Pfadangabe mit "<Buchstabe>:" an, so wird der rootPath ignoriert und es wird
     * ein absolutes Verzeichnis mit Buchstaben erzeugt.
     * 
     * @param String
     *            rootPath
     * @param String
     *            relativePath
     * @return String relativePath
     */
    public String getHomeDirectory(String rootPath, String relativePath) {

        File rootPathF = new File(rootPath);

        // UNC-Pfad
        if (relativePath.startsWith("//")) {
            rootPathF = new File(relativePath);
            return rootPathF.getPath();
        }

        // Absoluter Pfad
        Pattern windowsDriveName = Pattern.compile("\\w:", Pattern.CASE_INSENSITIVE);
        Matcher matcher = windowsDriveName.matcher(relativePath.substring(0, 2));
        if (matcher.find()) {
            rootPathF = new File(relativePath);
            return rootPathF.getPath();
        }

        // Relativer Pfad
        String[] pathElements = relativePath.split("/");
        for (int i = 0; i < pathElements.length; i++) {
            if ("..".equals(pathElements[i])) {
                rootPathF = new File(rootPathF.getParent());
            }else {
                rootPathF = new File(rootPathF.getPath() + "/" + pathElements[i]);
            }
        }

        return rootPathF.getPath();
    }

    public String getValue4DBProperties(String key) {
        Properties p = loadSystemProperties("db.properties");
        return getHomeDirectory(System.getProperty("user.dir"), p.getProperty(key));
    }

    /**
     * Spezielle Methode, um das import.sql zu lesen.
     * 
     * @param relativeAdress
     * @return
     */
    public BufferedReader openResourceFile(String relativeAdress) {

        BufferedReader br = null;

        try {

            // fileContent = new StringBuffer();
            // URL url = ClassLoader.getSystemResource(relativeAdress);
            // br = new BufferedReader(new InputStreamReader(url.openStream()));

            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(relativeAdress);
            br = new BufferedReader(new InputStreamReader(in));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return br;
    }
}
