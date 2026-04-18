// $Log: DAOFactoryFileIO.java,v $
// Revision 1.6  2015/07/21 17:59:19  tw
// Korrektur d. Haldetests.
//
// Revision 1.5  2015/04/17 10:42:33  tw
// Leerzeichen in Buchungssatz-Steuerdatei-Ueberschrift entfernt.
//
// Revision 1.4  2015/03/19 22:39:26  tw
// CR Feng-ID: 3947#4
//
// Revision 1.3  2015/02/19 14:32:41  tw
// Haldenbearbeitung: Aenderung des Speicherverzeichnisses.
//
// Revision 1.2  2015/02/02 11:05:11  tw
// Halde-Testanbindung aufgeraeumt.
//
// Revision 1.1  2015/01/30 02:43:37  tw
// Haldenbearbeitung 1. Wurf.
//
//

package de.decodetron.fac;

import java.util.HashMap;
import java.util.Map;

import de.decodetron.bo.HaldeConfig;
import de.decodetron.bo.IHaldeConfig;
import de.decodetron.dao.halde.HaldeDAO;
import de.decodetron.dao.halde.HaldeDAOI;
import de.decodetron.exc.DAOConfigurationException;
import de.decodetron.util.Const;
import de.decodetron.util.SystemUtil;

/**
 * Dient nur als Abtrennung vom Client.
 * 
 * @author Thomas Winter
 * @since 28.01.2015
 */
public abstract class DAOFactoryFileIO {

    public static IHaldeConfig getHaldeConfig(String belegTyp) {
        
        SystemUtil u = new SystemUtil();
        Map<String, IHaldeConfig> hmDBConfig = new HashMap<String, IHaldeConfig>();

        // //////////////////////////////////////////////////////
        // /// Junit
        // /
        hmDBConfig.put(Const.HALDE_JUNIT, new HaldeConfig(//
            "path.halde.pdf.ju",//
            "path.halde.bsatz.save.ju",//
            u.getValue4DBProperties("path.halde.bsatz.tmp.save.ju"),//
            "Dokument Type;LieferantenNummer;Bestellnummer;Dokument Datum",//
            "path.halde.lieferini.ju"));
        
        // //////////////////////////////////////////////////////
        // /// Scanning
        // /
        hmDBConfig.put(Const.HALDE_BELEGART_SC, new HaldeConfig(//
                "path.halde.scan.pdf",//
                "path.halde.scan.bsatz.save",//
                de.decodetron.util.Const.AEP_TMP_HALDE_DIRHOME,//
                "Dokument Type;LieferantenNummer;Bestellnummer;Dokument Datum",//
                "path.halde.lieferini"));
        // //////////////////////////////////////////////////////
        // /// Tagesbelege
        // /
        hmDBConfig.put(Const.HALDE_BELEGART_TB, new HaldeConfig(//
                "path.halde.tb.pdf",//
                "path.halde.tb.bsatz.save",//
                de.decodetron.util.Const.AEP_TMP_HALDE_DIRHOME,//
                "Dokument Type;LieferantenNummer;Bestellnummer; KUCKUCK",//
                "path.halde.lieferini"));
        // //////////////////////////////////////////////////////
        // /// Sammelrechnungen
        // /
        hmDBConfig.put(Const.HALDE_BELEGART_SR, new HaldeConfig(//
                "path.halde.sr.pdf",//
                "path.halde.sr.bsatz.save",//
                de.decodetron.util.Const.AEP_TMP_HALDE_DIRHOME,//
                "TODO",//
                "path.halde.lieferini"));
        // //////////////////////////////////////////////////////
        // /// Einkaufsaufträge
        // /
        hmDBConfig.put(Const.HALDE_BELEGART_EK, new HaldeConfig(//
                "path.halde.ek.pdf",//
                "path.halde.ek.bsatz.save",//
                de.decodetron.util.Const.AEP_TMP_HALDE_DIRHOME,//
                "TODO",//
                "path.halde.lieferini"));
        return hmDBConfig.get(belegTyp);
    }

    /**
     * ACHTUNG !!! Nur noch für Junit-Tests benutzen !!!
     * 
     * @param String
     *            haldePdf, Der Speicherort der PDFs ohne Buchungssatz. ACHTUNG: Erwartet wird der
     *            Key aus den db.properties.
     * @param String
     *            tmpSave, Der temporäre Speicherort für Buchungssätze. ACHTUNG: Hier wird das
     *            vollständige Verzeichnis verlangt.
     * @param String
     *            haldeSave, Der finale Speicherort für Buchungssätze für den Import. ACHTUNG:
     *            Erwartet wird der Key aus den db.properties.
     * @return DAOFactoryFileIO
     * @throws DAOConfigurationException
     * 
     */
    // @Deprecated
    // public static DAOFactoryFileIO getInstance(final String haldePdf, final String tmpSave, final
    // String haldeSave)
    // throws DAOConfigurationException {
    //
    // // final SystemUtil u = new SystemUtil();
    // // final String userHome = System.getProperty("user.dir");
    // // final Properties p = new SystemUtil().loadSystemProperties("db.properties");
    //
    // DAOFactoryFileIO instance = new DAOFactoryFileIO() {
    //
    // public String getDirPDFHalde() {
    // // String haldeDir = u.getHomeDirectory(userHome, p.getProperty(haldePdf));
    // return new SystemUtil().getValue4DBProperties(haldePdf);
    // }
    //
    // @Override
    // public String getDirTmpSave() {
    // return tmpSave;
    // }
    //
    // @Override
    // public String getDirSave() {
    // return new SystemUtil().getValue4DBProperties(haldeSave);
    // }
    //
    // /**
    // * Defaultmässig auf Scan-Überschrift eingestellt.
    // */
    // @Override
    // public String getHeadLineCSV() {
    // return getHaldeConfig(Const.HALDE_BELEGART_SC).getHeadLineCSV();
    // }
    //
    // @Override
    // public String getDocumentType() {
    // // TODO Auto-generated method stub
    // return null;
    // }
    // };
    // return instance;
    // }

    public static DAOFactoryFileIO getInstance(final String documentType) throws DAOConfigurationException {

        DAOFactoryFileIO instance = new DAOFactoryFileIO() {

            public String getDirPDFHalde() {
                return new SystemUtil().getValue4DBProperties(getHaldeConfig(documentType).getDirHaldePDF());
            }

            @Override
            public String getDirTmpSave() {
                return getHaldeConfig(documentType).getDirHaldeTmp();
            }

            @Override
            public String getDirSave() {
                return new SystemUtil().getValue4DBProperties(getHaldeConfig(documentType).getDirHaldeSave());
            }

            @Override
            public String getHeadLineCSV() {
                return getHaldeConfig(documentType).getHeadLineCSV();
            }

            @Override
            public String getDocumentType() {
                return documentType;
            }

            @Override
            public String getFileLieferanten() {
                return new SystemUtil().getValue4DBProperties(getHaldeConfig(documentType).getFileLieferanten());
            }
        };
        return instance;
    }

    public abstract String getDocumentType();

    public abstract String getHeadLineCSV();

    public abstract String getDirPDFHalde();

    public abstract String getFileLieferanten();

    /**
     * Bevor der Buchungssatz verarbeitet wird, existiert er in einem Temporären Verzeichnis, damit
     * der Importmechanismus ihn nicht wegschnappt.
     * 
     * @return
     */
    public abstract String getDirTmpSave();

    public abstract String getDirSave();

    public HaldeDAOI getHaldeDAO() {
        return new HaldeDAO(this);
    }
}
