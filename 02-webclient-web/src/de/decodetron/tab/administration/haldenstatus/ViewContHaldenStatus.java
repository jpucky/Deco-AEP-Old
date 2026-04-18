// $Log: ViewContHaldenStatus.java,v $
// Revision 1.15  2015/01/25 14:34:34  tw
// Vorabentwurf f. Lueckenprotokoll implementiert.
//
// Revision 1.14  2014/11/06 10:43:35  tw
// Uhreitkorrektur auf deutsch.
//
// Revision 1.13  2014/10/27 14:46:54  tw
// Bugfix in der Recherche Gesamtanzahl / Aufraeumarbeiten.
//
// Revision 1.12  2014/10/24 20:38:59  tw
// Haldenstatus: Anzeige Datensaetze, Datum, Aufraeumarbeiten.
//
// Revision 1.11  2014/10/24 20:17:52  tw
// Haldenstatus: Anzeige Datensaetze, Datum.
//
// Revision 1.10  2014/10/23 21:02:44  tw
// Haldenstatus fuer  Dateien m. Datum im Filenamen erweitert.
//
// Revision 1.9  2014/10/18 16:09:39  tw
// Fallback f. nichvorhandene Dateien erweitert.
//
// Revision 1.8  2014/10/18 10:57:04  tw
// Hilfetext in Panel verlagert wg. besserer Lesbarkeit.
//
// Revision 1.7  2014/10/18 10:45:03  tw
// Hilfetext in .properties ausgelagert.
//
// Revision 1.6  2014/10/18 10:20:37  tw
// Hilfe-Dialog Text hinzugefuegt.
//
// Revision 1.5  2014/10/17 08:39:52  tw
// Haldenstatus fuer 2 Dateien erweitert.
//
// Revision 1.4  2014/10/16 11:17:37  tw
// Hilfe-Dialog fuer Halde implementiert.
//
// Revision 1.3  2014/10/16 10:51:44  tw
// Hilfe-Dialog fuer Halde implementiert.
//
// Revision 1.2  2014/10/14 20:25:35  tw
// Scanningbelege werden nur aus dem neuen index genommen, wenn er existiert.
//
// Revision 1.1  2014/10/14 16:29:00  tw
// umzug
//
// Revision 1.4  2014/10/03 23:31:56  tw
// falsche imports entfernt.
//
// Revision 1.3  2014/10/03 22:23:02  tw
// Implementierung: Haldenstatus.
//
// Revision 1.2  2014/10/03 21:52:11  tw
// Implementierung: Haldenstatus.
//
// Revision 1.1  2014/10/03 15:07:05  tw
// Implementierung: Haldenstatus.
//
//

package de.decodetron.tab.administration.haldenstatus;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

import de.decodetron.AEPApplication;
import de.decodetron.AEPModel;
import de.decodetron.data.FileContentResource;
import de.decodetron.data.Util;

/**
 * Für Anzeige und Download gibt es immer ZWEI Listen.<br>
 * <br>
 * - Halde_DB.csv = Liste aller offenen Archivierungsdatensätze (stammt aus HALDE.sdb)<br>
 * - Halde_DIR.csv = Liste aller offenen (SCAN-)Belegedateien (DIR aus Verzeichnis HALDE)
 * 
 * @author Thomas Winter
 * @since 03.10.2014
 */
public class ViewContHaldenStatus extends Panel {

    private static String PDATE = "_\\d+_";
    private static String PANZAHL = "_\\d+\\.";
    private static String PCREATEDATE = "dd.MM.yyyy HH:mm";

    private static String OFFENESCANDATEIEN = "os";
    private static String OFFENEARCHIVIERUNG = "oa";

    public void renderHead(IHeaderResponse response) {
        response.render(OnDomReadyHeaderItem.forScript("$.fn.closeHelpHalde()"));
    }

    public ViewContHaldenStatus(String id, IModel<AEPModel> model) {
        super(id, model);

        // //////////////////////////////////////////////////////////
        // /// offene Scandateien
        // /
        add(new ScanDownload("downloadOffeneScan"));
        add(new Refresh("refreshOffeneScan"));
        add(new ScanCountLabel("scancnt", OFFENESCANDATEIEN));
        add(new DateLabel("scandate", OFFENESCANDATEIEN));
        add(new ScanView("offeneScan", model));

        // //////////////////////////////////////////////////////////
        // /// offene Archivierungsdatensätze
        // /
        add(new OffeneArchivierungDownload("downloadOffeneArchivierung"));
        add(new Refresh("refreshOffeneArchivierung"));
        add(new ScanCountLabel("oacnt", OFFENEARCHIVIERUNG));
        add(new DateLabel("oadate", OFFENEARCHIVIERUNG));
        add(new OffeneArchivierungView("offeneArchivierung", model));

        add(new HelpText("helptext"));
    }

    /**
     * Erzeugt ein Datumslabel: "Stand dd.MM.yyyy hh:mm" aus dem Dateinamen.
     */
    private class DateLabel extends Label {

        /**
         * @param String
         *            id
         * @param String
         *            type: offene Scan oder nicht. Elegant ist anders, egal.
         */
        public DateLabel(String id, final String type) {
            super(id);
            setDefaultModel(new Model<String>() {
                @Override
                public String getObject() {
                    StringBuilder sb = new StringBuilder();
                    try {
                        String fname = "";
                        File file = null;
                        if (OFFENESCANDATEIEN.equals(type)) {
                            file = getFileOffeneScanBySuffix(".csv");
                        } else if (OFFENEARCHIVIERUNG.equals(type)) {
                            file = getFileOffeneArchivierungBySuffix(".csv");
                        }
                        fname = (file != null && file.exists()) ? file.getName() : "";
                        fname = Util.cutTextPattern(PDATE, fname);
                        fname = fname.length() > 0 ? fname.substring(1, fname.length() - 1) : "";
                        fname = Util.inOutDateParser(fname, "yyMMddhhmm", PCREATEDATE);
                        if (!fname.isEmpty()) {
                            sb.append("(Stand ");
                            sb.append(fname);
                            sb.append(")");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return sb.toString();
                }
            });
        }
    }

    /**
     * Erzeugt ein Anzahllabel aus dem Dateinamen.
     */
    private class ScanCountLabel extends Label {

        public ScanCountLabel(String id, final String type) {
            super(id);
            setDefaultModel(new Model<String>() {
                @Override
                public String getObject() {
                    String fname = "";
                    try {
                        File file = null;
                        if (OFFENESCANDATEIEN.equals(type)) {
                            file = getFileOffeneScanBySuffix(".csv");
                        } else if (OFFENEARCHIVIERUNG.equals(type)) {
                            file = getFileOffeneArchivierungBySuffix(".csv");
                        }
                        fname = (file != null && file.exists()) ? file.getName() : "";
                        fname = Util.cutTextPattern(PANZAHL, fname);
                        fname = fname.length() > 0 ? fname.substring(1, fname.length() - 1) : "";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return fname;
                }
            });
        }
    }

    /**
     * Eine Liste aller Offenen Scan-Dateien.
     * 
     * @param String
     *            suffix
     * @return File
     */
    private File getFileOffeneScanBySuffix(String suffix) {

        File file = null;
        String rootDir = AEPApplication.get().getModel().getDirRootScan();
        String filePattern = AEPApplication.get().getModel().getFileOffeneScan();
        Util u = new Util();
        List<File> fileList = u.searchFiles(rootDir, filePattern, false);
        for (Iterator<File> iterator = fileList.iterator(); iterator.hasNext();) {
            File file2 = iterator.next();
            if (file2.getName().toLowerCase().endsWith(suffix.toLowerCase())) {
                file = file2;
                break;
            }
        }
        return file;
    }

    /**
     * Eine Liste aller Offenen Archivierungs -Dateien.
     * 
     * @param String
     *            suffix
     * @return File
     */
    private File getFileOffeneArchivierungBySuffix(String suffix) {

        File file = null;
        String rootDir = AEPApplication.get().getModel().getDirRootScan();
        String filePattern = AEPApplication.get().getModel().getFileOffeneArchivierung();
        Util u = new Util();
        List<File> fileList = u.searchFiles(rootDir, filePattern, false);
        for (Iterator<File> iterator = fileList.iterator(); iterator.hasNext();) {
            File file2 = iterator.next();
            if (file2.getName().toLowerCase().endsWith(suffix.toLowerCase())) {
                file = file2;
                break;
            }
        }
        return file;
    }

    private class ScanDownload extends DownloadLink {

        @Override
        public boolean isVisible() {
            return getFileOffeneScanBySuffix(".csv") != null ? getFileOffeneScanBySuffix(".csv").exists() : false;
        }

        public ScanDownload(String id) {
            super(id, new AbstractReadOnlyModel<File>() {
                public File getObject() {
                    return getFileOffeneScanBySuffix(".csv");
                }
            });
        }
    }

    private class OffeneArchivierungDownload extends DownloadLink {

        @Override
        public boolean isVisible() {
            return getFileOffeneArchivierungBySuffix(".csv") != null ? getFileOffeneArchivierungBySuffix(".csv")
                    .exists() : false;
        }

        public OffeneArchivierungDownload(String id) {
            super(id, new AbstractReadOnlyModel<File>() {
                @Override
                public File getObject() {
                    return getFileOffeneArchivierungBySuffix(".csv");
                }
            });
        }
    }

    private class Refresh extends Link<String> {

        public Refresh(String id) {
            super(id);
        }

        @Override
        public void onClick() {
            // nix. der request-zyklus reicht zum erneuern. ajax schenken wir uns.
        }
    }
    /**
     * Problem: Firefox zickt rum bei der Anzeige von .csv Dateien. Er bietet ständig den
     * Auswahldialog an und zeigt das File nicht im internen Fenster. Daher wird das File umkopiert
     * in ein .txt File.
     */
    private class ScanView extends WebMarkupContainer {

        public ScanView(String id, IModel<?> model) {
            super(id, model);

            add(AttributeModifier.replace("src", new Model<String>() {
                @Override
                public String getObject() {

                    PrintWriter pw = null;

                    try {

                        File fileTxt = getFileOffeneScanBySuffix(".txt");
                        File fileCsv = getFileOffeneScanBySuffix(".csv");

                        if ((fileTxt != null) && (fileTxt.exists())) {
                            // Dann können wir das so übernehmen
                            File destFile = File.createTempFile("dest", ".txt");
                            Util.copyFile(fileTxt.getPath(), destFile.getPath());
                            fileTxt = destFile;
                        } else if ((fileCsv != null) && (fileCsv.exists())) {
                            // Dann wird das Ding in ein .txt File umgemodelt
                            File destFile = File.createTempFile("dest", ".txt");
                            Util.copyFile(fileCsv.getPath(), destFile.getPath());
                            fileTxt = destFile;
                        } else {
                            fileTxt = File.createTempFile("info", ".txt");
                            pw = new PrintWriter(new FileWriter(fileTxt));
                            pw.println(Util.getTimeStamp1(System.currentTimeMillis()) + ": Keine Einträge vorhanden!");
                        }

                        // ENDLICH! So einach ist es die blöde Cache-Problematik zu umgehen!
                        PageParameters pdfParameters = new PageParameters();
                        pdfParameters.add("ts", System.currentTimeMillis());
                        return (String) urlFor(getResourceReference(fileTxt), pdfParameters);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "about:blank";
                    } finally {
                        if (pw != null) {
                            pw.close();
                        }
                    }
                }
            }));
        }
    }

    private class OffeneArchivierungView extends WebMarkupContainer {

        public OffeneArchivierungView(String id, IModel<?> model) {
            super(id, model);

            add(AttributeModifier.replace("src", new Model<String>() {
                @Override
                public String getObject() {

                    PrintWriter pw = null;

                    try {

                        File fileTxt = getFileOffeneArchivierungBySuffix(".txt");
                        File fileCsv = getFileOffeneArchivierungBySuffix(".csv");

                        if ((fileTxt != null) && (fileTxt.exists())) {
                            // Dann können wir das so übernehmen
                            File destFile = File.createTempFile("dest", ".txt");
                            Util.copyFile(fileTxt.getPath(), destFile.getPath());
                            fileTxt = destFile;
                        } else if ((fileCsv != null) && (fileCsv.exists())) {
                            // Dann wird das Ding in ein .txt File umgemodelt
                            File destFile = File.createTempFile("dest", ".txt");
                            Util.copyFile(fileCsv.getPath(), destFile.getPath());
                            fileTxt = destFile;
                        } else {
                            fileTxt = File.createTempFile("info", ".txt");
                            pw = new PrintWriter(new FileWriter(fileTxt));
                            pw.println(Util.getTimeStamp1(System.currentTimeMillis()) + ": Keine Einträge vorhanden!");
                        }

                        // ENDLICH! So einach ist es die blöde Cache-Problematik zu umgehen!
                        PageParameters pdfParameters = new PageParameters();
                        pdfParameters.add("ts", System.currentTimeMillis());
                        return (String) urlFor(getResourceReference(fileTxt), pdfParameters);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "about:blank";
                    } finally {
                        if (pw != null) {
                            pw.close();
                        }
                    }
                }
            }));
        }
    }

    private ResourceReference getResourceReference(final File file) {
        ResourceReference rr = new ResourceReference(file.getName()) {
            @Override
            public IResource getResource() {
                RequestCycle rc = getRequestCycle();
                WebResponse wrc = (WebResponse) rc.getOriginalResponse();
                wrc.setContentType("application/" + Util.getSuffix(file));
                return new FileContentResource(file, getRequestCycle(), false);
            }
        };
        return rr;
    }

}
