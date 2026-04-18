package de.decodetron;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;

import de.decodetron.data.Util;

// $Log: AEPClassLoader.java,v $
// Revision 1.12  2019/07/21 11:23:23  tw
// Umlaute entfernt argh.
//
// Revision 1.11  2019/07/21 11:11:16  tw
// Transfusion, Button fuer leere Abfrage eingebaut.
//
// Revision 1.10  2016/03/10 11:18:36  tw
// CR 3961: Namensaenderung => "direkt" faellt weg.
//
// Revision 1.9  2016/01/16 22:43:24  tw
// error-pdf zu den resourcen hinzugefuegt.
//
// Revision 1.8  2014/04/02 14:28:33  tw
// pdf, aufraeumarbeiten, neue xsl-struktur.
//
// Revision 1.7  2014/04/01 01:00:07  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.6  2014/01/22 15:34:16  tw
// Formatierung Logausgabe.
//
// Revision 1.5  2014/01/20 14:17:59  tw
// PDF Footerkorrektur.
//
// Revision 1.4  2014/01/17 10:35:47  tw
// Listenausgabe als pdf.
//
// Revision 1.3  2014/01/16 17:01:52  tw
// Listenausgabe als pdf. Abverkauf-Reimporte
//
// Revision 1.2  2014/01/15 21:44:32  tw
// Bufix: Beseitigung aller KZs. Verbergen alle bisherigen Aenderungen.
//
// Revision 1.1  2014/01/10 15:04:37  tw
// Statistik: Listenausgabe als pdf. Erster Durchstich d. Defektenliste.
//
// 
//

/**
 * @author Thomas Winter
 * @since 09.09.2010
 * @deprecated, vorerst kalter Kaffee, bis sich die neue Schemastruktur etabliert hat.
 */
public class AEPClassLoader {

    final public static String XSL_TEMPLATE_AEP_TEMPLATES = "pdf-aep-templates.xsl";
    final public static String XSL_TEMPLATE_AEP_HOCH = "pdf-aep-hf.xsl";
    final public static String XSL_TEMPLATE_AEP_QUER = "pdf-aep-qf.xsl";
    final public static String XSL_TEMPLATE_AEP_QUER_13 = "pdf-aep-qf-13col.xsl";
    final public static String XSL_TEMPLATE_AEP_QUER_14 = "pdf-aep-qf-14col.xsl";
    final public static String XSL_TEMPLATE_AEP_HOCH_DEFEKT = "pdf-aep-hf-defekt.xsl";
    final public static String XSL_TEMPLATE_AEP_HOCH_DEFEKT_LIEF = "pdf-aep-hf-defekt-lief.xsl";
    final public static String XSL_TEMPLATE_AEP_QUER_CHARGEN = "pdf-aep-qf-chargendoku.xsl";
    final public static String XSL_TEMPLATE_AEP_EMPTY_TRANSFUSION = "pdf-aep-empty-transfusion.xsl";
    final public static String PDF_TEMPLATE_AEP_DOKNOTFOUND = "error.pdf";

    // final static String SVG_AEP_LOGO = "login-logo-aep.png";

    public static void loadClasses() throws Exception {

        String[] libs = new String[] { XSL_TEMPLATE_AEP_TEMPLATES, XSL_TEMPLATE_AEP_HOCH, XSL_TEMPLATE_AEP_QUER,
                XSL_TEMPLATE_AEP_HOCH_DEFEKT, XSL_TEMPLATE_AEP_HOCH_DEFEKT_LIEF, XSL_TEMPLATE_AEP_QUER_CHARGEN,
                XSL_TEMPLATE_AEP_QUER_13, XSL_TEMPLATE_AEP_QUER_14, PDF_TEMPLATE_AEP_DOKNOTFOUND,
                XSL_TEMPLATE_AEP_EMPTY_TRANSFUSION };

        // String TMP_DIR = System.getProperty("java.io.tmpdir");

        for (int i = 0; i < libs.length; i++) {

            // ClassLoader cl = AEPClassLoader.class.getClassLoader();
            // InputStream is = cl.getResourceAsStream(libs[i]);

            InputStream in = AEPClassLoader.class.getResourceAsStream("../../xsl/" + libs[i]);
            Util.streamToFile(in, Const.AEP_TMP_PDF_DIRHOME + libs[i]);
            // File tmpLib = Util.streamToFile(in, Const.AEP_TMP_PDF_DIRHOME + libs[i]);
            // File tmpLib = getExtractedLib(is, Const.APPLICATIONHOME + "/" + libs[i]);
            // System.out.println(tmpLib);

            // Wir fügen das extrahierte Jar File zum Classpath hinzu...
            // Method m = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] {
            // URL.class});
            // m.setAccessible(true);
            // m.invoke(cl, new Object[] { tmpLib.toURL() });

            in.close();
        }
    }

}
