// $Log: HaldeDAO.java,v $
// Revision 1.30  2015/07/21 17:59:19  tw
// Korrektur d. Haldetests.
//
// Revision 1.29  2015/04/17 10:42:33  tw
// Leerzeichen in Buchungssatz-Steuerdatei-Ueberschrift entfernt.
//
// Revision 1.28  2015/04/13 14:50:04  tw
// CR Feng-ID: 3950#3
//
// Revision 1.27  2015/04/10 14:33:52  tw
// CR Feng-ID: 3947#9
//
// Revision 1.26  2015/04/10 14:21:31  tw
// CR Feng-ID: 3947#9
//
// Revision 1.25  2015/04/09 20:32:29  tw
// CR Feng-ID: 3947 Bugfix: Benutzer sieht die Datensaetze anderer Benutzer.
//
// Revision 1.24  2015/03/26 10:46:56  tw
// CR Feng-ID: 3947#7 Bugfix automatisierter Test.
//
// Revision 1.23  2015/03/24 23:20:02  tw
// CR Feng-ID: 3947#7
//
// Revision 1.22  2015/03/23 22:47:02  tw
// CR Feng-ID: 3947#6
//
// Revision 1.21  2015/03/19 22:39:26  tw
// CR Feng-ID: 3947#4
//
// Revision 1.20  2015/03/19 13:04:54  tw
// CR Feng-ID: 3947#4
//
// Revision 1.19  2015/03/18 22:40:01  tw
// CR Feng-ID: 3947#4
//
// Revision 1.18  2015/03/18 15:23:28  tw
// CR Feng-ID: 3947
//
// Revision 1.17  2015/02/19 21:22:58  tw
// Haldenbearbeitung: Inititialisierung Lieferanten.ini. Bugix Tabellenscrollbar.
//
// Revision 1.16  2015/02/19 15:13:03  tw
// Haldenbearbeitung: Aenderung des Speicherverzeichnisses.
//
// Revision 1.15  2015/02/19 14:48:48  tw
// Haldenbearbeitung: Aenderung des Speicherverzeichnisses.
//
// Revision 1.14  2015/02/19 14:32:41  tw
// Haldenbearbeitung: Aenderung des Speicherverzeichnisses.
//
// Revision 1.13  2015/02/16 14:12:04  tw
// Haldenbearbeitung: Umlaute ANSI.
//
// Revision 1.12  2015/02/15 01:31:26  tw
// Haldenbearbeitung: Autocomplete die 1.
//
// Revision 1.11  2015/02/13 10:47:17  tw
// Haldenbearbeitung: LiefName hinzugefuegt.
//
// Revision 1.10  2015/02/11 10:32:47  tw
// Haldenbearbeitung: Loeschen wenn letzter Ds geloescht.
//
// Revision 1.9  2015/02/08 00:21:28  tw
// Haldenbearbeitung: Datensatz-bearbeiten Aenderungserkennung impl.
//
// Revision 1.8  2015/02/07 17:09:08  tw
// Haldenbearbeitung: Datensatz-Loeschen-Schnittstelle angebunden.
//
// Revision 1.7  2015/02/07 12:02:41  tw
// Haldenbearbeitung: Datensatz-Loeschen-Schnittstelle implementiert.
//
// Revision 1.6  2015/02/04 20:45:16  tw
// Haldenbearbeitung: Speichern v. Buchungsdatensaetzen implementiert.
//
// Revision 1.5  2015/02/04 16:23:09  tw
// Haldenbearbeitung: Speichern v. Buchungsdatensaetzen implementiert.
//
// Revision 1.4  2015/02/03 00:51:19  tw
// Haldenbearbeitung: Einlesen v. Archivierungsdatensaetzen ermoeglicht.
//
// Revision 1.3  2015/02/02 11:05:11  tw
// Halde-Testanbindung aufgeraeumt.
//
// Revision 1.2  2015/02/01 11:28:48  tw
// Haldenbearbeitung Bugfix Nummerierung.
//
// Revision 1.1  2015/01/30 02:43:37  tw
// Haldenbearbeitung 1. Wurf.
//
//

package de.decodetron.dao.halde;

import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;

import au.com.bytecode.opencsv.CSVReader;
import de.decodetron.bo.HaldeBuchungssatzScan;
import de.decodetron.bo.HaldeFile;
import de.decodetron.bo.LiefNrName;
import de.decodetron.fac.DAOFactoryFileIO;
import de.decodetron.util.Const;
import de.decodetron.util.SystemUtil;
import de.decodetron.util.Util;

/**
 * @author Thomas Winter
 * @since 28.01.2015
 */
public class HaldeDAO implements HaldeDAOI {

    private DAOFactoryFileIO daoFactory;
    private static List<LiefNrName> lieferantenInfoList = new ArrayList<LiefNrName>();

    public HaldeDAO(DAOFactoryFileIO daoFactory) {
        this.daoFactory = daoFactory;
        if (lieferantenInfoList.size() == 0) {
            initLieferantenListe();
        }
    }

    @Override
    public List<HaldeFile> getAllHaldeFiles(String login) {

        int cnt = 0;
        String lin = login == null ? "" : login;
        // Pattern patternAEPL = Pattern.compile("AEPL\\d{6,15}", Pattern.CASE_INSENSITIVE);

        // String haldeDir = new SystemUtil().getValue4DBProperties("path.halde.pdf");
        List<File> fpdf = Util.getFiles(this.daoFactory.getDirPDFHalde(), ".*\\.pdf$");
        List<File> fcsvTmp = Util.getFiles(this.daoFactory.getDirTmpSave(), ".*" + lin + "\\.csv$");

        // Alle Files. Auch die der andern User!
        List<File> fcsvSave = Util.getFiles(this.daoFactory.getDirSave(), ".*" + "\\.csv$");

        List<HaldeFile> hfList = new ArrayList<HaldeFile>();
        for (Iterator<File> iterator = fpdf.iterator(); iterator.hasNext();) {
            File file = iterator.next();
            List<String> pdfTmp = createPDFBuddy(fcsvTmp);
            List<String> pdfSave = createPDFBuddy(fcsvSave);
            hfList.add(mapHaldeFile(file, ++cnt, pdfTmp, pdfSave));
        }

        // try {
        // // Künstliche Verlängerung zum Experimentieren ...
        // Thread.sleep(1500);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }

        return hfList;
    }

    @Override
    public boolean checkIfBuchungssatzExist(HaldeBuchungssatzScan hb) {

        boolean bsExist = false;
        Pattern patternAEPL = Pattern.compile("AEPL\\d{6,15}", Pattern.CASE_INSENSITIVE);
        List<File> fcsvSave = Util.getFiles(this.daoFactory.getDirSave(), ".*" + "\\.csv$");

        for (Iterator<File> iterator = fcsvSave.iterator(); iterator.hasNext();) {

            File file = iterator.next();
            String nameSaveCSV = file.getName();
            Matcher matcher = patternAEPL.matcher(nameSaveCSV);
            if (matcher.find()) {

                String aeplPattern = matcher.group();
                if (hb.getDokumentType().equals(aeplPattern)) {
                    bsExist = true;
                    break;
                }
            }
        }

        return bsExist;
    }

    /**
     * Hier muss geguckt werden, ob:<br>
     * AEPL00000037_twinter@decodetron.de.csv == AEPL00000037.pdf<br>
     * <br>
     * ODER 18.03.2015 Planänderung:<br>
     * 05_07_AEPL00000037_twinter@decodetron.de.csv == AEPL00000037.pdf <br>
     * <br>
     * Anders gesagt, muss hier aus dem temporären .csv - File der entsprechende .pdf Name erzeugt
     * werden.
     */
    private List<String> createPDFBuddy(List<File> fcsvTmp) {

        List<String> fcsvn = new ArrayList<String>();
        Pattern patternAEPL = Pattern.compile("AEPL\\d{6,15}", Pattern.CASE_INSENSITIVE);

        for (Iterator<File> iterator = fcsvTmp.iterator(); iterator.hasNext();) {

            File file = iterator.next();
            String nameTmpCSV = file.getName();
            Matcher matcher = patternAEPL.matcher(nameTmpCSV);
            if (matcher.find()) {
                nameTmpCSV = matcher.group() + ".pdf";
            }

            // name = name.replaceFirst("\\.csv$", ".pdf");
            // name = name.replaceFirst("(\\_.*\\.csv$)|(\\.csv$)", ".pdf");
            fcsvn.add(nameTmpCSV);
        }
        return fcsvn;
    }

    private void appendBuchungsSatz(HaldeBuchungssatzScan hb) {
        Util.appendToFile(getBuchungssatzDestFile4Tmp(hb), getOneBuchungssatz(hb));
    }

    /**
     * Stellt ein HaldeBuchungssatz zu einer String-Datensatzzeile für z.B. eine .csv zusammen.
     * 
     * @param HaldeBuchungssatzScan
     *            hb
     * @return String
     */
    private String getOneBuchungssatz(HaldeBuchungssatzScan hb) {
        StringBuilder sb = new StringBuilder();
        sb.append(hb.getDokumentType()).append(";");
        sb.append(hb.getLieferantNr()).append(";");
        // sb.append(hb.getLieferantenname()).append(";");
        sb.append(hb.getBestellNr() == null ? "" : hb.getBestellNr()).append(";");
        sb.append(hb.getDokDatum() != null ? Util.inOutDateParser(hb.getDokDatum(), "dd.MM.yyyy", "yyyyMMdd") : "");
        sb.append(System.getProperty("line.separator"));
        return sb.toString();
    }

    public File getBuchungssatzDestFile4Save(HaldeBuchungssatzScan hb) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.daoFactory.getDirSave()).append(getFileName(hb));
        return new File(sb.toString());
    }

    public File getBuchungssatzDestFile4Tmp(HaldeBuchungssatzScan hb) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.daoFactory.getDirTmpSave()).append(getFileName(hb));
        return new File(sb.toString());
    }

    public File getBuchungssatzDestFile4Tmp(HaldeFile hf, String userLogin) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.daoFactory.getDirTmpSave()).append(getFileName(hf, userLogin));
        return new File(sb.toString());
    }

    private String getFileName(HaldeBuchungssatzScan hb) {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("file.separator"));
        sb.append(HaldeBuchungssatzScan.SAVE_FILENAME_ROOT);
        sb.append(hb.getDokumentType());
        sb.append(!hb.getUserLogin().isEmpty() ? "." + hb.getUserLogin() : "");
        sb.append(".csv");
        return sb.toString();
    }

    private String getFileName(HaldeFile hf, String userLogin) {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("file.separator"));
        sb.append(HaldeBuchungssatzScan.SAVE_FILENAME_ROOT);
        sb.append(Util.cutSuffix(hf.getFileName()));
        sb.append((userLogin == null || userLogin.isEmpty()) ? "" : "." + userLogin);
        sb.append(".csv");
        return sb.toString();
    }

    @Override
    public void insertBuchungsSatz(List<HaldeBuchungssatzScan> hb) {
        if ((hb != null) && (!hb.isEmpty())) {
            File destFile = getBuchungssatzDestFile4Tmp(hb.get(0));
            if (!destFile.exists()) {
                Util.appendToFile(destFile, daoFactory.getHeadLineCSV() + "\n");
            } else {
                // Löschen und alles aus dem Speicher wieder raushauen.
                destFile.delete();
                Util.appendToFile(destFile, daoFactory.getHeadLineCSV() + "\n");
            }
            for (Iterator<HaldeBuchungssatzScan> iterator = hb.iterator(); iterator.hasNext();) {
                HaldeBuchungssatzScan haldeBuchungssatz = iterator.next();
                appendBuchungsSatz(haldeBuchungssatz);
            }
        }
    }

    @Override
    public void exportBuchungsSatz(List<HaldeBuchungssatzScan> hb) {

        if ((hb != null) && (!hb.isEmpty())) {
            File destFile = getBuchungssatzDestFile4Save(hb.get(0));
            if (!destFile.exists()) {
                Util.appendToFile(destFile, daoFactory.getHeadLineCSV() + "\n");
            } else {
                // Löschen und alles aus dem Speicher wieder raushauen.
                destFile.delete();
                Util.appendToFile(destFile, daoFactory.getHeadLineCSV() + "\n");
            }

            List<HaldeBuchungssatzScan> cleanList = removeDoubleEntries(hb);
            for (Iterator<HaldeBuchungssatzScan> iterator = cleanList.iterator(); iterator.hasNext();) {
                HaldeBuchungssatzScan haldeBuchungssatz = iterator.next();
                Util.appendToFile(destFile, getOneBuchungssatz(haldeBuchungssatz));
            }
        }
    }

    /**
     * Bereiningt doppelte Archivierungsdatensätze.
     * 
     * @param hb
     */
    public List<HaldeBuchungssatzScan> removeDoubleEntries(List<HaldeBuchungssatzScan> hb) {

        List<HaldeBuchungssatzScan> lNew = new ArrayList<HaldeBuchungssatzScan>();

        for (Iterator<HaldeBuchungssatzScan> iterator = hb.iterator(); iterator.hasNext();) {
            HaldeBuchungssatzScan haldeBuchungssatz = iterator.next();
            if (!lNew.contains(haldeBuchungssatz)) {
                lNew.add(haldeBuchungssatz);
            } else {
                // nix
                System.out.println("Debugcheck: ");
            }
        }

        return lNew;
    }

    private HaldeBuchungssatzScan mapHaldeBuchungssatz(String[] busa, int cnt, String userLogin) {

        HaldeBuchungssatzScan hbs = new HaldeBuchungssatzScan();
        PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(hbs);
        String[] items = daoFactory.getHeadLineCSV().split(";");

        if (busa == null || busa.length != items.length) {
            return hbs;
        }
        // for (int i = 0; i < pds.length; i++) {
        // PropertyDescriptor pd = (PropertyDescriptor)pds[i];
        // System.out.println(pd.getName());
        // //BeanUtils.setProperty(hbs, pd.getName(), map.get(pd.getName())); // <= So müsste es
        // gehen!
        // }

        hbs.setSid(busa[0] + "_" + cnt);
        hbs.setDokumentType(busa[0]);
        hbs.setLieferantNr(busa[1]);
        // hbs.setLieferantenname(busa[2]);
        hbs.setBestellNr(busa[2]);
        hbs.setDokDatum(busa[3]);
        hbs.setUserLogin(userLogin);

        return hbs;
    }

    /**
     * Mappt die Fileattribute auf das HaldeFile.
     * 
     * @param File
     *            file
     * @param List
     *            <File> fcsv
     * @return HaldeFile
     */
    private HaldeFile mapHaldeFile(File file, int cnt, List<String> pdfTmp, List<String> pdfSave) {
        HaldeFile hf = new HaldeFile();
        hf.setId((long) cnt);
        hf.setRootPath(file.getParent());
        hf.setFileName(file.getName());
        hf.setFileChangeDate(String.valueOf(file.lastModified()));
        hf.setFileSize(String.valueOf(file.length()));
        hf.setBuchungssatzTmpExist(pdfTmp.contains(file.getName()));
        hf.setBuchungssatzSaveExist(pdfSave.contains(file.getName()));
        return hf;
    }

    @Override
    public List<HaldeBuchungssatzScan> getBuchungsSaetze4PDF(String fileId, String userLogin) {

        String uLogin = userLogin == null ? "" : "\\." + userLogin;
        List<HaldeBuchungssatzScan> listBusa = null;
        String searchPattern = ".*" + fileId + uLogin + ".*";
        List<File> files = Util.getFiles(this.daoFactory.getDirTmpSave(), searchPattern, false);
        if ((files == null) || (files.size() == 0)) {
            return null;
        }

        File lastModFile = Util.sortFilesLastModifiedDesc(files).get(0);
        CSVReader reader = null;
        try {
            int cnt = 0;
            String[] str;
            listBusa = new ArrayList<HaldeBuchungssatzScan>();
            reader = new CSVReader(new FileReader(lastModFile), ';', CSVReader.DEFAULT_QUOTE_CHARACTER, 1);
            while ((str = reader.readNext()) != null) {
                String userLoginCSV = getUserLogin(lastModFile);

                /**
                 * TODO: Für andere Belegtypen muss dieser Blödsin hier rein ...
                 */
                if (Const.HALDE_BELEGART_SC.equals(this.daoFactory.getDocumentType())) {
                    // listBusa.add(mapHaldeBuchungssatz(str, cnt++, userLogin));
                }

                listBusa.add(mapHaldeBuchungssatz(str, cnt++, userLoginCSV));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeReader(reader);
        }

        return listBusa;
    }

    /**
     * Schnippelt das User-Login aus dem File aus. Erwartet wird der Name zwischen dem ersten und
     * dem letzten Punkt.
     * 
     * @param File
     *            file
     * @return String
     */
    private String getUserLogin(File file) {

        String userName = "";

        int indexUnder = file.getName().indexOf('.');
        int indexDot = file.getName().lastIndexOf('.');
        if ((indexUnder != -1) && (indexDot != -1) && (indexDot > indexUnder)) {
            userName = file.getName().substring(indexUnder + 1, indexDot);
        }

        return userName;
    }

    private void closeReader(Closeable r) {
        if (r != null) {
            try {
                r.close();
            } catch (IOException e) {}
        }
    }

    @Override
    public void removeDatensatzByIndex(HaldeBuchungssatzScan hb, Integer index) {

        File targetFile = getBuchungssatzDestFile4Tmp(hb);

        String line;
        int tmpIndex = 0;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        try {
            br = new BufferedReader(new FileReader(targetFile));
            while ((line = br.readLine()) != null) {
                if (tmpIndex++ - 1 != index) {
                    sb.append(line).append(System.getProperty("line.separator"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeReader(br);
        }

        targetFile.delete();
        if (tmpIndex > 2) {
            Util.appendToFile(targetFile, sb.toString());
        }
    }

    @Override
    public void removeAllDatensaetze(HaldeFile hf, String userLogin) {
        File file2Delete = getBuchungssatzDestFile4Tmp(hf, userLogin);
        file2Delete.delete();
    }

    @Override
    public String getLieferantenNr(String lieferantenName) {

        long start = System.currentTimeMillis();
        for (Iterator<LiefNrName> iterator = lieferantenInfoList.iterator(); iterator.hasNext();) {
            LiefNrName liefNrName = iterator.next();
            if (liefNrName.getLieferantenname().equals(lieferantenName)) {
                return liefNrName.getLieferantNr();
            }
        }
        // System.out.println("getLieferantenNr Zeit: " + (System.currentTimeMillis() - start) +
        // "[ms]");
        return "";
    }

    @Override
    public String getLieferantenName(String lieferantenNr) {

        long start = System.currentTimeMillis();
        for (Iterator<LiefNrName> iterator = lieferantenInfoList.iterator(); iterator.hasNext();) {
            LiefNrName liefNrName = iterator.next();
            if (liefNrName.getLieferantNr().equals(lieferantenNr)) {
                return liefNrName.getLieferantenname();
            }
        }
        // System.out.println("getLieferantenName Zeit: " + (System.currentTimeMillis() - start) +
        // "[ms]");
        return "";
    }

    public void initLieferantenListe() {

        long start = System.currentTimeMillis();

        lieferantenInfoList.clear();
        //junitPath = (junitPath != null && !junitPath.isEmpty()) ? junitPath : "path.halde.lieferini";
        //String fileLocation = new SystemUtil().getValue4DBProperties(junitPath);
        String fileLocation = this.daoFactory.getFileLieferanten();
        File file = new File(fileLocation);
        
        if(!file.exists()){
            System.out.println("initLieferantenListe nicht möglich, File existiert nicht: " + file.getPath());
        }

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "cp1252"));
            while ((line = br.readLine()) != null) {
                String[] items = line.split("=");
                LiefNrName liefNrName = new LiefNrName(items[0], items[1]);
                lieferantenInfoList.add(liefNrName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeReader(br);
        }

        // System.out.println("initLieferantenListe Zeit: " + (System.currentTimeMillis() - start) +
        // "[ms]");
    }

    @Override
    public List<LiefNrName> getLieferantenInfo() {
        return lieferantenInfoList;
    }

    @Override
    public boolean checkIfNrOrNameExist(String nrName) {

        for (Iterator<LiefNrName> iterator = lieferantenInfoList.iterator(); iterator.hasNext();) {
            LiefNrName liefNrName = iterator.next();
            if (liefNrName.getLieferantNr().equals(nrName) || liefNrName.getLieferantenname().equals(nrName)) {
                return true;
            }
        }
        return false;
    }

}
