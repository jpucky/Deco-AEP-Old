// $Log: Util.java,v $
// Revision 1.77  2020/06/19 22:56:59  tw
// *** empty log message ***
//
// Revision 1.76  2020/02/26 19:24:10  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.75  2019/07/23 20:38:08  tw
// CR: Statistik, Transfusion Bis-Datums-Check.
//
// Revision 1.74  2019/07/23 20:19:42  tw
// CR: Statistik, Transfusion Bis-Datums-Check.
//
// Revision 1.73  2019/07/21 11:23:23  tw
// Umlaute entfernt argh.
//
// Revision 1.72  2019/07/21 11:11:16  tw
// Transfusion, Button fuer leere Abfrage eingebaut.
//
// Revision 1.71  2019/07/17 20:19:41  tw
// CR: PDF-Tabellenkoepfe: Ansicht wie auf der Webseite.  CSV - Timer 20 Sek Limit beseitigt (CreateCsvLink.setCacheDuration)
//
// Revision 1.70  2017/07/20 15:59:33  tw
// Version 1.21-H: BUGIFX: Fixe Reihenfolge der Belegtypen zeigt Belegtypen an, die nicht der Gruppe zugeordnet sind.
//
// Revision 1.69  2017/07/18 14:16:02  tw
// Version 1.20-H. Neuer Belegtyp: Monatsberichte.
//
// Revision 1.68  2017/06/26 10:11:55  tw
// Lucene-Offsetfeld: Trennzeichen von # auf @ umgestellt.
//
// Revision 1.67  2017/06/23 17:39:49  tw
// Mobilmachung der Headrevision.
//
// Revision 1.66  2016/06/28 19:36:22  tw
// BUFIX 3963: Benutzerverwaltung/Benutzer-DB =>  zusaetzliche Felder.
//
// Revision 1.65  2016/01/31 17:00:14  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.64  2016/01/19 23:15:06  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.63  2016/01/11 22:50:34  tw
// CR 3956: Interner Umbau: Lucene-Felder.
//
// Revision 1.62  2015/12/16 20:41:39  tw
// CR 3953: Archivierung von Retourenbelegen.
//
// Revision 1.61  2015/12/03 16:59:23  tw
// CR 3953: Archivierung von Retourenbelegen, Aufraeumarbeiten, Vorbereitung.
//
// Revision 1.60  2015/02/03 00:52:22  tw
// Haldenbearbeitung: Einlesen v. Archivierungsdatensaetzen ermoeglicht.
//
// Revision 1.59  2015/02/01 11:08:42  tw
// Haldenbearbeitung Korrektur. f. Produktionseinsatz.
//
// Revision 1.58  2015/01/31 17:53:03  tw
// Haldenbearbeitung Korrektur. f. Produktionseinsatz.
//
// Revision 1.57  2015/01/30 02:44:52  tw
// Haldenbearbeitung 1. Wurf.
//
// Revision 1.56  2015/01/25 20:11:31  tw
// Vorabentwurf f. Lueckenprotokoll implementiert.
//
// Revision 1.55  2015/01/25 14:34:34  tw
// Vorabentwurf f. Lueckenprotokoll implementiert.
//
// Revision 1.54  2014/12/17 22:07:29  tw
// Recherche: aktuelles Jahr verwenden.
//
// Revision 1.53  2014/10/27 14:46:54  tw
// Bugfix in der Recherche Gesamtanzahl / Aufraeumarbeiten.
//
// Revision 1.52  2014/10/24 20:17:52  tw
// Haldenstatus: Anzeige Datensaetze, Datum.
//
// Revision 1.51  2014/10/23 21:02:43  tw
// Haldenstatus fuer  Dateien m. Datum im Filenamen erweitert.
//
// Revision 1.50  2014/10/14 16:32:02  tw
// Haldenstatus, Anpassung an csv.
//
// Revision 1.49  2014/10/03 21:52:11  tw
// Implementierung: Haldenstatus.
//
// Revision 1.48  2014/10/03 12:01:37  tw
// Lieferantenname Textfeldanbindung.
//
// Revision 1.47  2014/07/30 16:08:37  tw
// Scanbelege, Lucene Objektmapper erstellt, Aufraeumarbeiten.
//
// Revision 1.46  2014/04/03 12:02:30  tw
// pdf, aufraeumarbeiten, neue xsl-struktur.
//
// Revision 1.45  2014/04/02 14:28:33  tw
// pdf, aufraeumarbeiten, neue xsl-struktur.
//
// Revision 1.44  2014/03/28 17:23:44  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.43  2014/03/03 19:29:51  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Recherche: Paginierung.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.42  2014/03/01 23:59:48  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Recherche: Paginierung.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.41  2014/02/13 13:06:24  tw
// Benutzerverwaltung, dynamischer Suchfilteraufbau, Schnittstellenanpassung.
//
// Revision 1.40  2014/02/05 13:47:58  tw
// Anpassung an neue DB-Struktur
//
// Revision 1.39  2014/01/30 23:19:16  tw
// Belegart: EK. Spaltenbezeichner Anpassung.
//
// Revision 1.38  2014/01/30 17:39:11  tw
// Umbau: Belegart-Auswahl. Backup.
//
// Revision 1.37  2014/01/29 19:52:54  tw
// Umbau: Belegart-Auswahl.
//
// Revision 1.36  2014/01/23 11:38:56  tw
// Bugifx classcastexception f. groupid 2.
//
// Revision 1.35  2014/01/22 15:34:16  tw
// Formatierung Logausgabe.
//
// Revision 1.34  2014/01/16 17:01:52  tw
// Listenausgabe als pdf. Abverkauf-Reimporte
//
// Revision 1.33  2014/01/15 21:44:32  tw
// Bufix: Beseitigung aller KZs. Verbergen alle bisherigen Aenderungen.
//
// Revision 1.32  2014/01/10 15:03:40  tw
// Statistik: Listenausgabe als pdf. Erster Durchstich d. Defektenliste.
//
// Revision 1.31  2014/01/10 01:16:06  tw
// Statistik: Listenausgabe als pdf. Erster Durchstich d. Defektenliste.
//
// Revision 1.30  2013/12/24 00:21:35  tw
// Fehlerausgabe verfeinert.
//
// Revision 1.29  2013/12/09 14:40:22  tw
// codierungsaendrung: cp1252
//
// Revision 1.28  2013/12/05 20:38:58  tw
// Bugfix: Abbruch der .cvs - Generierung.
//
// Revision 1.27  2013/12/05 20:10:52  tw
// Bugfix Suche. Performancesteigerung: Defektenliste.
//
// Revision 1.26  2013/12/05 14:37:07  tw
// ImplementierungTokenanmeldung.
//
// Revision 1.25  2013/12/04 15:56:06  tw
// csv-liste fuer btm implementiert.
//
// Revision 1.24  2013/12/04 14:17:19  tw
// csv-liste fuer btm implementiert.
//
// Revision 1.23  2013/11/28 13:42:27  tw
// Anbindung Liste:Tierarznei.
//
// Revision 1.22  2013/11/12 01:30:16  tw
// Umlaute bringen mich um!
//
// Revision 1.21  2013/11/12 00:02:51  tw
// Belegart vorerst hinzugefuegt.
//
// Revision 1.20  2013/11/08 09:48:53  tw
// chargenliste implementiert.
//
// Revision 1.19  2013/10/31 00:14:26  tw
// Konfiguration der Quelldatenverzeichnisse von aussen moeglich.
//
// Revision 1.18  2013/10/30 12:23:00  tw
// Anbindung eines Tab-Reiters.
//
// Revision 1.17  2013/10/30 10:04:48  tw
// Default-Sortierung nach Datum absteigend.
//
// Revision 1.16  2013/10/27 22:28:38  tw
// Einfuehrung einer Buildnumber.
//
// Revision 1.15  2013/10/27 20:28:38  tw
// Umlaute
//
// Revision 1.14  2013/10/27 17:42:28  tw
// PDF-Ablage geandert. Layoutanpassung: PDF-Ansicht als Overflow:hidden.
//
// Revision 1.13  2013/10/27 17:25:40  tw
// PDF-Ablage geändert. Layoutanpassung: PDF-Ansicht als Overflow:hidden.
//
// Revision 1.12  2013/10/25 11:17:10  tw
// Bugfixing f. Rollout.
//
// Revision 1.11  2013/10/25 01:50:01  tw
// Anpassung an Rollout.
//
// Revision 1.10  2013/10/25 00:28:37  tw
// Anpassung an Rollout.
//
// Revision 1.9  2013/10/24 16:44:06  tw
// Anpassung an Helmut's archiv.t
//
// Revision 1.8  2013/10/13 10:40:22  tw
// Debug-Logausgabe f. Dokumentbetrachter angefragt.
//
// Revision 1.7  2013/10/07 11:57:40  tw
// codierung ge�ndert.
//
// Revision 1.6  2013/10/06 23:50:13  tw
// Aufräumarbeiten. Beseitigen globaler Variablen.
//
// Revision 1.5  2013/09/25 23:45:06  tw
// Wurzelverzeichnis auf user.dir gelegt.
//
// Revision 1.4  2013/09/25 23:25:41  tw
// Suche fÃ¼r kdnr, belegnr, datum angebunden
//
// Revision 1.3  2013/09/24 14:02:49  tw
// Kleine Testsuche Kundenr implementiert.
//
// Revision 1.2  2013/09/24 01:54:43  tw
// Fundstellenliste / Ansicht ajaxifiziert.
//
// Revision 1.1  2013/09/20 23:41:41  tw
// PDF-Ansicht implementiert.
//
//

package de.decodetron.data;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.CharacterIterator;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javanet.staxutils.IndentingXMLStreamWriter;

import javax.xml.bind.DatatypeConverter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPApplication;
import de.decodetron.Const;
import de.decodetron.bo.DataRecord;
import de.decodetron.bo.FilterItem;
import de.decodetron.bo.FilterItemList;
import de.decodetron.bo.Fundstelle;
import de.decodetron.bo.Section;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.statistik.TxtFilter;
import de.decodetron.tab.statistik.transfusion.TransfusionFilterAttr;

/**
 * @author Thomas Winter
 * @since 20.09.2013
 */
public class Util {

    private static Logger log = Logger.getLogger(Util.class);
    private static final String LS = System.getProperty("line.separator");

    /**
     * Extrahiert aus einer archiv.t - Datei ein Dokument.
     * 
     * @param String
     *            inArchivT, der Pfad/Name der archiv.t.
     * @param File
     *            outFile, der Pfad/Name des Ausgabefiles.
     * @param long position, die Positionsangabe des Dokumentes. Falls keine bekannt ist: mit
     *        Position 0 wird Begonnen.
     * @return Pfad/Name des Ausgabefiles. Die Endung wird dieser Funktion erst hinzugefï¿½gt, da
     *         das Ausgebeformat nicht konstant ist.
     * 
     * @throws Exception
     */
    public String extractDocument(String inArchivT, File outFile, long position) throws Exception {

        StringBuilder sb = new StringBuilder();
        LoginSession s = LoginSession.get();
        sb.append("Dokumentanfrage: ");
        sb.append(s.getUser().getVorname()).append(" ");
        sb.append(s.getUser().getNachname()).append(", ");
        sb.append(new File(inArchivT).getName());
        sb.append(", Position: ").append(position);

        try {
            byte[] bytePos = new byte[] { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff };
            byte[] byteDocTyp = new byte[4];
            byte[] byteDocLen = new byte[4];
            byte[] byteDocBuffer = null;

            RandomAccessFile fileIn = new RandomAccessFile(inArchivT, "r");
            long positionDocType = position + bytePos.length;
            fileIn.seek(positionDocType); // hinter yyyy
            fileIn.read(byteDocTyp); // hinter FLAT
            fileIn.read(byteDocLen);

            // Output-File
            Map<String, String> suffixMap = new HashMap<String, String>();
            suffixMap.put(new String(new char[] { 'P', 'D', 'F', ' ' }), ".pdf"); // AEP
            suffixMap.put(new String(new char[] { 'P', 'D', 'F', 'X' }), ".pdf");
            suffixMap.put(new String(new char[] { 'F', 'L', 'A', 'T' }), ".txt");
            String suffix = suffixMap.get(new String(byteDocTyp));

            if (suffix == null) {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(LS);
                sb1.append("********************************").append(LS);
                sb1.append("Die Sprungposition stimmt nicht.").append(LS);
                sb1.append("Dokument-Typ: \"").append(new String(byteDocTyp, "UTF-8")).append("\"").append(LS);
                sb1.append("Position : " + position).append(LS);
                sb1.append("Datei    : " + inArchivT).append(LS);
                sb1.append("********************************");
                throw new Exception(sb1.toString());
            }

            /**
             * 18.07.2017: Vorsicht! Ab Java 1.9 wird diese Konstruktion nicht mehr unterstützt:
             * DatatypeConverter.printHexBinary. Siehe:
             * https://stackoverflow.com/questions/140131/convert
             * -a-string-representation-of-a-hex-dump-to-a-byte-array-using-java
             */
            // ///////////////////////////////////////////////////////////////////////////
            // /// Bei fehlerhafter Sprungposition gibts hier u.u. schon Probleme. Pruefung vorher!
            // /
            Long docSize = getValueFromHex(DatatypeConverter.printHexBinary(byteDocLen));
            byteDocBuffer = new byte[docSize.intValue()];
            fileIn.read(byteDocBuffer);

            File oFile = new File(outFile + suffix);
            RandomAccessFile fileOut = new RandomAccessFile(oFile, "rw");
            fileOut.write(byteDocBuffer);

            sb.append(", Bekommt: ").append(oFile.getName());
            log.debug(sb.toString());

            close(fileIn);
            close(fileOut);

            return oFile.getPath();
        } catch (Exception e) {
            log.error(sb.toString(), e);
            e.printStackTrace();
            throw e;
        }
    }

    // @Deprecated
    // public List<Fundstelle> getFundstellen(ValueMap vm, BooleanQuery booleanQuery) {
    //
    // // System.out.println("start Search ...");
    // vm.put(Const.KEYSEARCHBUSY, Boolean.TRUE);
    // // ValueMap vm = (ValueMap) getDefaultModelObject();
    // long start = System.currentTimeMillis();
    //
    // Integer colNr = new Integer(0);
    // HashMap<String, Integer> colOrder;
    // colOrder = new HashMap<String, Integer>();
    // colOrder.put("id", ++colNr);
    // colOrder.put("kundennummer", ++colNr);
    // colOrder.put("belegnummer", ++colNr);
    // colOrder.put("datum", ++colNr);
    // colOrder.put("offset", ++colNr);
    // colOrder.put("seitenzahl", ++colNr);
    // colOrder.put("belegart", ++colNr);
    //
    // IndexReader reader = null;
    // List<Fundstelle> localList = new ArrayList<Fundstelle>();
    //
    // try {
    //
    // String FS = System.getProperty("file.separator");
    // StringBuilder sb = new StringBuilder();
    // // sb.append(Const.APPLICATIONHOME).append(FS);
    // // sb.append(Const.FUNDSTELLEN_HOME).append(FS);
    // sb.append(AEPApplication.get().getModel().getPathLucy()).append(FS);
    // sb.append("data").append(FS);
    // sb.append("index");
    //
    // // http://lucene.apache.org/core/3_6_0/api/core/org/apache/lucene/store/FSDirectory.html
    // // - SimpleFSDirectory
    // // - MMapDirectory, Mag ja sein, dass das unter Unix funktioniert, hier machts
    // // jedenfalls Ärger!
    // reader = DirectoryReader.open(FSDirectory.open(new File(sb.toString())));
    //
    // IndexSearcher searcher = new IndexSearcher(reader);
    // // //////////////////////////////////////////
    // // /// Suche ohne Sortiertung
    // // /
    // // TopScoreDocCollector collector = TopScoreDocCollector.create(MainPage.HITSPERPAGE,
    // // true);
    // // searcher.search(booleanQuery, collector);
    // // TopDocs hits = collector.topDocs();
    //
    // // //////////////////////////////////////////
    // // /// Suche mit Default-Sortiertung
    // // /
    // TopDocs hits = searcher.search(booleanQuery, Const.ITEMS_PER_PAGE, new Sort(new
    // SortField("datum",
    // SortField.Type.STRING, true)));
    //
    // ScoreDoc[] scoreDoc = hits.scoreDocs;
    //
    // // displayHits(collector.topDocs(), reader);
    // vm.put(Const.KEYDOCTOTAL, Long.valueOf(reader.numDocs())); // alle Dokumente
    // vm.put(Const.KEYHITSPERPAGE, Long.valueOf(hits.totalHits)); // HITSPERPAGE, alle Treffer
    // vm.put(Const.KEYSHOWPERPAGE, Long.valueOf(hits.scoreDocs.length)); // SHOWPERPAGE
    //
    // // System.out.println("Total :" + hits.totalHits + " hits.");
    // // System.out.println("Show  :" + hits.scoreDocs.length + " hits.");
    // // displayHits(hits, reader);
    //
    // for (int i = 0; i < scoreDoc.length; ++i) {
    //
    // // Thread.sleep(2 * 5);
    // // System.out.print(".");
    //
    // int index = scoreDoc[i].doc;
    // Document document = reader.document(index);
    // List<?> restFields = document.getFields();
    // Fundstelle fs = new Fundstelle();
    //
    // for (Iterator<?> iterator = restFields.iterator(); iterator.hasNext();) {
    //
    // Field field = (Field) iterator.next();
    // if (field == null) {
    // continue;
    // }
    //
    // // String fieldName = field.name();
    // // System.out.print("FeldName: " + fieldName + "-");
    // Integer cnt = colOrder.get(field.name());
    //
    // if (cnt == null) {
    // break;
    // }
    //
    // // System.out.print(cnt + ", ");
    // switch (cnt) {
    // case 1: {
    // fs.setId(document.get(field.name()));
    // break;
    // }
    // case 2: {
    // fs.setKundennummer(document.get(field.name()));
    // break;
    // }
    // case 3: {
    // fs.setBelegnummer(document.get(field.name()));
    // break;
    // }
    // case 4: {
    // fs.setDatum(document.get(field.name()));
    // break;
    // }
    // case 5: {
    // fs.setOffset(document.get(field.name()));
    // break;
    // }
    // case 6: {
    // fs.setSeitenzahl(document.get(field.name()));
    // break;
    // }
    // case 7: {
    // fs.setBelegart(document.get(field.name()));
    // break;
    // }
    // default: {
    // break;
    // }
    // }
    // }
    //
    // localList.add(fs);
    // }
    //
    // } catch (org.apache.lucene.store.NoSuchDirectoryException nse) {
    //
    // for (int i = 0; i < 10; i++) {
    // // Fundstelle fs = new Fundstelle();
    // // fs.setBelgDate(" ");
    // // fs.setKundenNr(".");
    // // fs.setFiliale(".");
    // // list.add(fs);
    // }
    // nse.printStackTrace();
    // } catch (Exception e) {
    // e.printStackTrace();
    // } finally {
    // if (reader != null) {
    // try {
    // reader.close();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }
    // }
    //
    // StringBuilder sb = new StringBuilder();
    // sb.append("Fundstellensuche: ");
    // sb.append(LoginSession.get().getUser().getLogin()).append(", ");
    // sb.append((System.currentTimeMillis() - start));
    // sb.append(" [ms], Size: ");
    // sb.append(localList.size());
    // sb.append(" Datum: \"").append(Util.getNotNull(vm.get("txt-blgdate"))).append("\"");
    // sb.append(", Lfsnr: \"").append(Util.getNotNull(vm.get("txt-lfsnr"))).append("\"");
    // sb.append(", Kdnr : \"").append(Util.getNotNull(vm.get("txt-kdnr"))).append("\"");
    // sb.append(" Listengroesse: ").append(localList.size());
    // // System.out.println(sb.toString());
    // logAEP.debug(sb.toString());
    //
    // vm.put(Const.KEYSEARCHBUSY, Boolean.FALSE);
    // vm.put(Const.KEYFUNDLISTE, localList);
    // // return list;
    // return localList;
    // }

    /**
     * 
     * @param String
     *            sourceDir
     * @param String
     *            pattern
     * @return ArrayList<File>
     */
    public ArrayList<File> getFiles(String sourceDir, String pattern) {
        return getFiles(sourceDir, pattern, true);
    }

    /**
     * Eine File-Suchfunktion.
     * 
     * @param String
     *            sourceDir
     * @param String
     *            pattern
     * @param boolean rekursiv
     * @return ArrayList<File>
     */
    public ArrayList<File> getFiles(String sourceDir, String pattern, boolean rekursiv) {

        ArrayList<File> list = new ArrayList<File>();

        if (sourceDir == null || sourceDir.length() == 0) {
            return list;
        }

        if (pattern == null || pattern.length() == 0) {
            return list;
        }

        File tmpDir = new File(sourceDir);
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);

        Stack<File> dirs = new Stack<File>();
        if (tmpDir.isDirectory()) {
            dirs.push(tmpDir);
        }

        while (dirs.size() > 0) {

            File f = dirs.pop();
            File[] files = f.listFiles();
            if (files == null) {
                continue;
            }

            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    if (rekursiv) {
                        dirs.push(files[i]);
                    }
                } else if (p.matcher(files[i].getName()).matches()) {
                    list.add(files[i]);
                }
            }
        }

        return list;
    }

    public Long getValueFromHex(String hex) {

        Long tmpInt = null;
        try {
            tmpInt = Long.parseLong(hexReverser(hex), 16);
        } catch (NumberFormatException nf) {

        }
        return tmpInt;
    }

    /**
     * Liest Hexwerte (String-2er-Pï¿½rchen) von Hinten nach Vorne. Macht aus: "224B00" => "004B22"
     * 
     * @param String
     *            hex
     * @return String
     */
    private static String hexReverser(String hex) {

        StringBuilder sb = new StringBuilder();
        for (int i = hex.length(); i > 0; i = i - 2) {
            String output = hex.substring((i - 2), i);
            sb.append(output);
        }
        return sb.toString();
    }

    private static void close(RandomAccessFile cl) {

        if (cl == null) {
            return;
        }
        try {
            cl.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static String getNotNull(Object o) {
        String s = "";
        if (o instanceof String) {
            s = (String) o;
            s = s == null ? "" : s.trim();
        }
        return s;
    }

    public static boolean isListEmpty(Collection coll) {
        return (coll == null || coll.isEmpty());
    }

    public static void displayHits(TopDocs hits, IndexReader reader) throws Exception {

        System.out.println("all Docs : " + reader.numDocs());
        System.out.println("max Docs : " + reader.maxDoc());

        // 4. display results
        System.out.println("Total :" + hits.totalHits + " hits.");
        System.out.println("Show  :" + hits.scoreDocs.length + " hits.");

        // System.out.println("Number of hits: " + hits.totalHits);

        StringBuilder sb = new StringBuilder();
        ScoreDoc[] scoreDoc = hits.scoreDocs;
        for (int i = 0; i < scoreDoc.length; ++i) {
            int index = scoreDoc[i].doc;
            Document document = reader.document(index);
            List<?> restFields = document.getFields();
            sb.delete(0, sb.length());
            sb.append((i + 1) + ". ");
            for (Iterator<?> iterator = restFields.iterator(); iterator.hasNext();) {
                Field field = (Field) iterator.next();
                sb.append(field.name()).append(":\"").append(document.get(field.name())).append("\"\t");
            }
            System.out.println(sb.toString());
        }
    }

    /**
     * Liefert einen aktuellen Zeitstempel nach dem Muster: yyyy-MM-dd-HH:mm:ss.
     * 
     * @return String
     */
    public static String getCurrentTimeStamp() {
        return getTimeStamp1(System.currentTimeMillis());
    }

    /**
     * Liefert einen Zeitstempel nach dem Muster: yyyy-MM-dd-HH:mm:ss.
     * 
     * @param Long
     *            date
     * @return String
     */
    public static String getTimeStamp1(Long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        return sdf.format(new Date(date));
    }

    /**
     * Erwartet das Datum in dem Format: yyyy-MM-dd. Wenn das nicht kommt, wird ein Leerstring
     * zurückgegeben.
     * 
     * Falls das - bis Datum in der Zukunft liegt, wird es auf das Tagesdatum zurückgesetzt.
     * 
     * @param ds
     * @return
     */
    public static void checkBisDateNotAfterNow(TxtFilter dateTextField) {

        String dateInp = dateTextField.getText();
        String dateRet = "";
        if (dateInp == null || dateInp.isEmpty()) {
            dateTextField.setText(dateRet);
        } else {

            try {
                Date nowDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date bisDate = sdf.parse(dateInp);
                if (bisDate.after(nowDate)) {
                    bisDate = nowDate;
                }
                dateRet = sdf.format(bisDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            dateTextField.setText(dateRet);
        }
    }

    /**
     * Liefert einen Zeitstempel nach dem Muster: yyyyMMdd-HHmmssSSS.
     * 
     * @param Long
     *            date
     * @return String
     */
    public static String getTimeStamp2(Long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmssSSS");
        return sdf.format(new Date(date));
    }

    /**
     * Liefert einen Zeitstempel nach dem Muster: yyyyMMdd.
     * 
     * @param Long
     *            date
     * @return String
     */
    public static String getTimeStamp3(Long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date(date));
    }

    /**
     * Liefert einen Zeitstempel nach dem Muster: ddMMyyyy.
     * 
     * @param Long
     *            date
     * @return String
     */
    public static String getTimeStamp4(Long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(date));
    }

    /**
     * Schnippelt die Endung eines Filenamens ab und gibt den Namen ohne .Endung zurück.
     * 
     * @param String
     *            fileName
     * @return String
     */
    public static String getNameCutSuffix(String fileName) {
        String justName = "";
        int indexSuffix = fileName.lastIndexOf('.');
        if (indexSuffix != -1) {
            justName = fileName.substring(0, indexSuffix);
        }
        return justName;
    }

    /**
     * Liefert das aktuelle Jahr.
     * 
     * @return String
     */
    public static String getCurrentYear() {
        GregorianCalendar gc = new GregorianCalendar();
        return String.valueOf(gc.get(Calendar.YEAR));
    }

    public File urlToFile(String url) {
        File file = null;
        try {
            file = urlToFile(new URL(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return file;
    }

    public File urlToFile(URL url) {
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        }
        return file;
    }

    /**
     * Liefert zu dem Uebergebenen Key den zugehoerigen Value der entsprechenden .properties-Datei.
     * 
     * @param String
     *            key
     * @param String
     *            properties
     * @return
     * @throws Exception
     */
    public String getVersionPropertieValue(String key) {

        String msg = "";
        Properties prop = null;

        try {
            prop = getInitialisedProperties("version.properties", false, true);
            msg = prop.getProperty(key);
        } catch (Exception e) {
            if (prop == null) {
                System.out.println("###################################################################");
                System.out.println("# Bitte die \"" + "version.properties" + "\"-Datei in den Classpath kopieren! #");
                System.out.println("###################################################################");
            } else {
                e.printStackTrace();
            }
        }

        return msg;
    }

    /**
     * Liefert die Values aus der conf.properties.
     * 
     * @param String
     *            key
     * @param String
     *            replaceBackslash
     * @return String
     */
    public String getConfPropertieValue(String key, boolean replaceBackslash) {
        String msg = "";
        Properties prop = null;

        try {
            prop = getInitialisedProperties("conf.properties", false, replaceBackslash);
            msg = prop.getProperty(key);
        } catch (Exception e) {
            System.out.println("Bitte die \"" + "conf.properties" + "\"-Datei in den Classpath kopieren!");
            e.printStackTrace();
        }

        if (msg == null) {
            log.error("###########################################################");
            log.error("# Zu folgendem Key wurde kein Wert gefunden : " + key + " !");
            log.error("###########################################################");
        }

        return msg;
    }

    /**
     * Liefert die Values aus der conf.properties. Backslashes werden per default ersetzt.
     * 
     * @param String
     *            key
     * @return String
     */
    public String getConfPropertieValue(String key) {
        return getConfPropertieValue(key, true);
    }

    /**
     * Initialisiert absolut oder relativ die übergebene Propertie-Datei.
     * 
     * @param String
     *            pathTopropertie
     * @param boolean pathAbsolute
     * @return Properties
     * @throws Exception
     */
    public Properties getInitialisedProperties(String pathTopropertie, boolean pathAbsolute, boolean replaceBackslah)
            throws Exception {

        InputStream isr = null;
        Properties prop = new Properties();

        if (pathAbsolute) {
            isr = new FileInputStream(new File(pathTopropertie));
        } else {
            String path = "../../../" + pathTopropertie;
            isr = getClass().getResourceAsStream(path);
        }

        if (isr != null) {
            String propertyFileContents = getStringFromInputStream(isr);
            if (replaceBackslah) {
                prop.load(new StringReader(propertyFileContents.replace("\\", "/")));
            } else {
                prop.load(new StringReader(propertyFileContents));
            }
        }
        return prop;
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
     * Setzt aus dem Root-Projektverzeichnis und dem relativen Pfad ein Directory zusammen.
     * 
     * @param String
     *            relativePath
     * @return String
     */
    public String getHomeDirectory(String rootPath, String relativePath) {

        if (rootPath == null || relativePath == null) {
            log.error("###################################");
            log.error("# conf.properties ueberpruefen !!!#");
            log.error("###################################");
        }

        File rootPathF = new File(rootPath);

        try {
            String[] pathElements = relativePath.split("/");

            for (int i = 0; i < pathElements.length; i++) {
                if ("..".equals(pathElements[i])) {
                    rootPathF = new File(rootPathF.getParent());
                } else {
                    rootPathF = new File(rootPathF.getPath() + "/" + pathElements[i]);
                }
            }
        } catch (Exception ex) {
            log.error("###################################");
            log.error("# FEHLER, PARAMETERCHECK rootPath    : " + rootPath);
            log.error("# FEHLER, PARAMETERCHECK relativePath: " + relativePath);
            log.error("###################################");
            log.error(ex.getMessage(), ex);
            return "";
        }

        return rootPathF.getPath();
    }

    /**
     * Macht aus einer Währungsdarstellung wieder ein Wert.
     * 
     * @param String
     *            v
     * @return String
     */
    public static String parseCurrency(String v) {

        if (v == null || v.length() == 0) {
            return "";
        }

        NumberFormat nf = NumberFormat.getNumberInstance();
        try {
            return nf.parse(v).toString();
        } catch (ParseException e) {
            // e.printStackTrace();
        }
        return "";
    }

    public File createEmptyTransfusionPDF(TransfusionFilterAttr tf, DataRecord tableHeader, Section section,
            String xslTemplate) {

        String dateVon = tf.getAuftragDatumVon();
        String dateBis = tf.getAuftragDatumBis();

        dateVon = dateVon.isEmpty() ? "2013-10-15" : dateVon;
        dateBis = dateBis.isEmpty() ? getTimeStamp4(new Date().getTime()) : dateBis;

        dateVon = inOutDateParser(dateVon, "yyyy-MM-dd", "dd.MM.yyyy");
        dateBis = inOutDateParser(dateBis, "yyyy-MM-dd", "dd.MM.yyyy");

        StringBuilder fileNameXML = new StringBuilder();
        fileNameXML.append(Const.AEP_TMP_PDF_DIRHOME);
        fileNameXML.append(section.getKz());
        fileNameXML.append("_");
        fileNameXML.append(getTimeStamp3(System.currentTimeMillis()));
        fileNameXML.append("_");
        fileNameXML.append(LoginSession.get().getUser().getLogin());
        fileNameXML.append(".xml");
        File destXMLFile = new File(fileNameXML.toString());
        if (destXMLFile.exists()) {
            destXMLFile.delete();
        }

        OutputStream out = null;
        OutputStreamWriter osw = null;
        XMLStreamWriter writer = null;

        // //////////////////////////////////////////////////////////////////////
        // //// Daten zusammenstellen
        // /
        try {
            out = new FileOutputStream(destXMLFile);
            osw = new OutputStreamWriter(out);
            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            writer = new IndentingXMLStreamWriter(factory.createXMLStreamWriter(new BufferedOutputStream(out), "UTF-8"));

            writer.writeStartDocument("1.0");
            writer.writeStartElement("statistik");

            writer.writeStartElement("base-path");
            writer.writeCharacters("http://localhost:8082");
            writer.writeEndElement();

            writer.writeStartElement("listentyp");
            writer.writeCharacters(section.getSection());
            writer.writeEndElement();// listentyp

            createDataHeader(writer, tableHeader);
            createAdresseKunde(writer);

            writer.writeStartElement("datevon");
            writer.writeCharacters(dateVon);
            writer.writeEndElement();

            writer.writeStartElement("datebis");
            writer.writeCharacters(dateBis);
            writer.writeEndElement();

            // //////////////////////////////////////////////
            // /// 10 leere - Dummyzeilen
            // /
            writer.writeStartElement("data");
            writer.writeCharacters("\n");
            writer.flush();

            for (int i = 1; i < 10; i++) {
                String xmlRI = getEmptyXmlRowItem();
                osw.write("     " + xmlRI);
            }

            osw.flush();
            writer.writeEndElement(); // data

            writer.writeEndElement(); // statistik

        } catch (Exception e) {
            log.error(e.getCause(), e);
        } finally {

            if (writer != null) {
                try {
                    writer.writeCharacters("\n");
                    writer.writeEndDocument();
                    writer.close();

                    out.close();
                } catch (Exception e) {
                    log.error(e.getCause(), e);
                }
            }
        }

        // //////////////////////////////////////////////////////////////////////
        // //// PDF erzeugen
        // /
        StringBuilder fileNamPDF = new StringBuilder();
        fileNamPDF.append(Const.AEP_TMP_PDF_DIRHOME);
        fileNamPDF.append(section.getKz());
        fileNamPDF.append("_");
        fileNamPDF.append(getTimeStamp3(System.currentTimeMillis()));
        fileNamPDF.append("_");
        fileNamPDF.append(LoginSession.get().getUser().getLogin());
        fileNamPDF.append(".pdf");
        File destPDFFile = new File(fileNamPDF.toString());
        if (destPDFFile.exists()) {
            destPDFFile.delete();
        }

        try {

            out = new BufferedOutputStream(new FileOutputStream(destPDFFile));

            FopFactory fopFactory = FopFactory.newInstance();
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
            // fopFactory.setBaseURL("http://localhost:8082"); keine wirkung!
            // Wichtig für die Angabe von relativen image-Pfaden!
            fopFactory.setURIResolver(AEPApplication.get().getURIResolver());
            fop.getUserAgent().setBaseURL(Const.getFullBaseUrl());

            Source srcXml = new StreamSource(destXMLFile);
            Result resOut = new SAXResult(fop.getDefaultHandler());
            StreamSource source = new StreamSource(new File(Const.AEP_TMP_PDF_DIRHOME + xslTemplate));
            Transformer transformer = TransformerFactory.newInstance().newTransformer(source);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.transform(srcXml, resOut);
            transformer.setURIResolver(AEPApplication.get().getURIResolver());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            destXMLFile.delete();
        }

        return destPDFFile;
    }

    public File createPDF(SortableDataProvider<Serializable, String> dp, DataRecord tableHeader, Section section,
            int itmPP, String xslTemplate) {

        StringBuilder fileNameXML = new StringBuilder();
        fileNameXML.append(Const.AEP_TMP_PDF_DIRHOME);
        fileNameXML.append(section.getKz());
        fileNameXML.append("_");
        fileNameXML.append(getTimeStamp3(System.currentTimeMillis()));
        fileNameXML.append("_");
        fileNameXML.append(LoginSession.get().getUser().getLogin());
        fileNameXML.append(".xml");
        File destXMLFile = new File(fileNameXML.toString());
        if (destXMLFile.exists()) {
            destXMLFile.delete();
        }

        OutputStream out = null;
        OutputStreamWriter osw = null;
        XMLStreamWriter writer = null;

        // //////////////////////////////////////////////////////////////////////
        // //// Daten zusammenstellen
        // /
        try {
            out = new FileOutputStream(destXMLFile);
            osw = new OutputStreamWriter(out);
            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            writer = new IndentingXMLStreamWriter(factory.createXMLStreamWriter(new BufferedOutputStream(out), "UTF-8"));

            writer.writeStartDocument("1.0");
            writer.writeStartElement("statistik");

            writer.writeStartElement("base-path");
            writer.writeCharacters("http://localhost:8082");
            writer.writeEndElement();

            writer.writeStartElement("listentyp");
            writer.writeCharacters(section.getSection());
            writer.writeEndElement();// listentyp

            createDataHeader(writer, tableHeader);
            createAdresseKunde(writer);

            int step = itmPP;
            long max = dp.size();

            for (int offset = 0; offset < max; offset = offset + step) {
                // System.out.println("offset: " + i);

                writer.writeStartElement("data");
                writer.writeCharacters("\n");
                writer.flush();

                Iterator<DataRecord> iter = (Iterator<DataRecord>) dp.iterator(offset, -1);
                while (iter.hasNext()) {
                    DataRecord dr = iter.next();
                    String xmlRI = getXmlRowItem(dr);
                    osw.write("     " + xmlRI);
                }

                osw.flush();
                writer.writeEndElement(); // data
            }

            writer.writeEndElement(); // statistik

        } catch (Exception e) {
            log.error(e.getCause(), e);
        } finally {

            if (writer != null) {
                try {
                    writer.writeCharacters("\n");
                    writer.writeEndDocument();
                    writer.close();

                    out.close();
                } catch (Exception e) {
                    log.error(e.getCause(), e);
                }
            }
        }

        // //////////////////////////////////////////////////////////////////////
        // //// PDF erzeugen
        // /

        StringBuilder fileNamPDF = new StringBuilder();
        fileNamPDF.append(Const.AEP_TMP_PDF_DIRHOME);
        fileNamPDF.append(section.getKz());
        fileNamPDF.append("_");
        fileNamPDF.append(getTimeStamp3(System.currentTimeMillis()));
        fileNamPDF.append("_");
        fileNamPDF.append(LoginSession.get().getUser().getLogin());
        fileNamPDF.append(".pdf");
        File destPDFFile = new File(fileNamPDF.toString());
        if (destPDFFile.exists()) {
            destPDFFile.delete();
        }

        try {

            out = new BufferedOutputStream(new FileOutputStream(destPDFFile));

            FopFactory fopFactory = FopFactory.newInstance();
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
            // fopFactory.setBaseURL("http://localhost:8082"); keine wirkung!
            // Wichtig für die Angabe von relativen image-Pfaden!
            fopFactory.setURIResolver(AEPApplication.get().getURIResolver());
            fop.getUserAgent().setBaseURL(Const.getFullBaseUrl());
            // System.out.println("URL-Check: " + fop.getUserAgent().getBaseURL());

            Source srcXml = new StreamSource(destXMLFile);
            Result resOut = new SAXResult(fop.getDefaultHandler());
            // InputStream in = getClass().getResourceAsStream("../../../" + xslTemplate);

            StreamSource source = new StreamSource(new File(Const.AEP_TMP_PDF_DIRHOME + xslTemplate));

            // Das hier ist eleganter, hierfür muss ich dem Transformer allerdings das
            // ssl-Zertifikat verklickern! TODO: Gucken, wie das geht!
            //
            // StringBuilder xslFile = new StringBuilder();
            // xslFile.append(Const.getFullBaseUrl());//.append("/");
            // xslFile.append("/").append(Const.AEP_XSL_HOME).append("/");
            // xslFile.append(xslTemplate);
            // StreamSource source = new StreamSource(xslFile.toString());

            Transformer transformer = TransformerFactory.newInstance().newTransformer(source);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.transform(srcXml, resOut);
            transformer.setURIResolver(AEPApplication.get().getURIResolver());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            destXMLFile.delete();
        }

        return destPDFFile;
    }

    /**
     * 
     * @param InputStream
     *            is, Quellstream
     * @param String
     *            fileName, targetFile
     * @return File
     */
    public static File streamToFile(InputStream is, String fileName) {

        FileOutputStream fos = null;
        File tmpLib = new File(fileName);

        try {
            new File(tmpLib.getParent()).mkdirs();
            tmpLib.createNewFile();

            fos = new FileOutputStream(tmpLib);

            int len = 0;
            byte[] buffer = new byte[0xFFFF];

            while ((len = is.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Wir löschen das extrahierte jar File beim beenden...
        // tmpLib.deleteOnExit();

        return tmpLib;
    }

    /**
     * Beginnt bei 1 um die ID zu ueberspringen.
     * 
     * @param XMLStreamWriter
     *            writer
     * @param DataRecord
     *            dr
     */
    private void createDataHeader(XMLStreamWriter writer, DataRecord dr) {

        try {
            writer.writeStartElement("dataheader");
            writer.writeStartElement("rowh");

            int drSize = dr.getSize();

            for (int i = 1; i < drSize; i++) {
                String colItem = dr.getColItem(i);
                writer.writeStartElement("col");

                try {
                    Util u = new Util();
                    Properties p = u.getInitialisedProperties("de/decodetron/tab/statistik/TabStatistik.properties",
                        false, false);
                    // System.out.println(p.getProperty("labeltable." + colItem));
                    String item = p.getProperty("labeltablepdf." + colItem);
                    writer.writeCharacters(item != null ? item : colItem);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // writer.writeCharacters(colItem != null ? colItem : " ");
                // writer.writeCharacters("" + i);
                writer.writeEndElement();
            }
            writer.writeEndElement();// rowh
            writer.writeEndElement();// dataheader

        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private void createAdresseKunde(XMLStreamWriter writer) {

        try {
            writer.writeStartElement("adressekunde");
            writer.writeStartElement("zeile");

            String vorname = LoginSession.get().getUser().getVorname();
            String nachname = LoginSession.get().getUser().getNachname();
            writer.writeCharacters(vorname + " " + nachname);
            writer.writeEndElement();// row
            writer.writeEndElement();// adressekunde

        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    /**
     * Erzeugt eine Semikolon-separierte .csv Liste.
     * 
     * @param SortableDataProvider
     *            <Serializable, String> dp
     * @param DataRecord
     *            tableHeader
     * @param String
     *            listId
     * @return File
     */
    public File createCSV(SortableDataProvider<Serializable, String> dp, DataRecord tableHeader, String listId,
            int itmPP) {

        StringBuilder fileName = new StringBuilder();
        fileName.append(Const.AEP_TMP_CSV_DIRHOME);
        fileName.append(listId);
        fileName.append("_");
        fileName.append(getTimeStamp3(System.currentTimeMillis()));
        fileName.append("_");
        fileName.append(LoginSession.get().getUser().getLogin());
        fileName.append(".csv");
        File dest = new File(fileName.toString());
        if (dest.exists()) {
            dest.delete();
        }

        int step = itmPP;
        long max = dp.size();

        StringBuilder sb = new StringBuilder();
        appendDataRecord(sb, tableHeader);
        appendToFile(dest, sb.toString());

        for (int offset = 0; offset < max; offset = offset + step) {
            // System.out.println("offset: " + i);
            Iterator<DataRecord> iter = (Iterator<DataRecord>) dp.iterator(offset, -1);
            // List test = Arrays.asList(iter);
            appendToFile(iter, tableHeader, dest);
            // System.out.println(i);
        }

        return dest;
    }

    /**
     * Bekommt eine list mit DataRecords und hängt diese an ein bestehendes File an. Ab hier werden
     * alle Daten im Arbeitsspeicher gehalten. Der Aufrufer muss darauf achten, dass die Datenmenge
     * nicht zu gross wird!
     * 
     * @param Iterator
     *            <DataRecord> iter
     * @param DataRecord
     *            tableHeader
     * @param File
     *            file
     */
    private void appendToFile(Iterator<DataRecord> iter, DataRecord tableHeader, File file) {

        if (iter == null || file == null) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        while (iter.hasNext()) {
            DataRecord dr = iter.next();
            appendDataRecord(sb, dr);
        }
        appendToFile(file, sb.toString());
    }

    private String getEmptyXmlRowItem() {
        StringBuilder sb = new StringBuilder();
        sb.append("<row>");
        for (int i = 1; i < 10; i++) {
            sb.append("<col>");
            sb.append("&#160;");
            sb.append("</col>");
        }
        sb.append("</row>");
        sb.append(Const.LS);
        return sb.toString();
    }

    /**
     * Verpackt den DataRecord in ein xml - Datensatz. Beginnt bei 1 um die ID zu ueberspringen!
     * 
     * @param DataRecord
     *            dr
     * @return String
     */
    private String getXmlRowItem(DataRecord dr) {
        StringBuilder sb = new StringBuilder();
        int drSize = dr.getSize();
        sb.append("<row>");
        for (int i = 1; i < drSize; i++) {
            String colItem = dr.getColItem(i);
            sb.append("<col>");
            sb.append(colItem != null ? prepareForXML(colItem) : " ");
            sb.append("</col>");
        }
        sb.append("</row>");
        sb.append(Const.LS);
        return sb.toString();
    }

    /**
     * Dröselte den DataRecord in seine Spaltenbestandteile und separiert sie mit Semikolon.
     * 
     * @param StringBuilder
     *            sb
     * @param DataRecord
     *            dr
     */
    private void appendDataRecord(StringBuilder sb, DataRecord dr) {
        int drSize = dr.getSize();
        for (int i = 0; i < drSize; i++) {
            String colItem = dr.getColItem(i);
            sb.append(colItem != null ? colItem : " ").append(";");
        }
        sb.append(Const.LS);
    }

    /**
     * Fügt einer Datei den Inhalt "text" am Ende der Datei hinzu. Die Datei wird, falls nicht
     * vorhanden, neu erzeugt.
     * 
     * @param File
     *            fileName
     * @param String
     *            text (CP1252)
     */
    public void appendToFile(File file, String text) {

        RandomAccessFile raf = null;

        try {

            // //////////////////////////////////////////////////////////////////////////
            // Normale Random-Access-Schreibvariante
            //
            raf = new RandomAccessFile(file, "rw");
            raf.seek(raf.length());
            raf.write(text.getBytes("CP1252"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static FilterItemList parseCurrency(List<TxtFilter> filterList) {
        FilterItemList fList = new FilterItemList();
        if (filterList != null) {
            for (Iterator<TxtFilter> iterator = filterList.iterator(); iterator.hasNext();) {
                TxtFilter txtFilter = iterator.next();
                if (txtFilter != null && txtFilter.getText() != null && !txtFilter.getText().isEmpty()) {
                    if ("dummyItem".equals(txtFilter.getId())) {
                        if ("preis".startsWith(txtFilter.getTblIdent().toLowerCase())) {
                            if (!txtFilter.getText().contains(".")) {
                                txtFilter.setText(parseCurrency(txtFilter.getText()));
                            }
                        } else if ("betrag".startsWith(txtFilter.getTblIdent().toLowerCase())) {
                            if (!txtFilter.getText().contains(".")) {
                                txtFilter.setText(parseCurrency(txtFilter.getText()));
                            }
                        }
                        fList.add(new FilterItem(txtFilter.getTblIdent(), txtFilter.getText()));
                    }
                }
            }
        }
        return fList;
    }

    /**
     * Gibt den Text der Suchfelder als Liste wieder.
     * 
     * @param List
     *            <TxtFilter> filterList
     * @return FilterItemList
     */
    public static FilterItemList mapSearchFields(List<TxtFilter> filterList) {

        FilterItemList fList = new FilterItemList();
        if (filterList != null) {
            for (Iterator<TxtFilter> iterator = filterList.iterator(); iterator.hasNext();) {
                TxtFilter txtFilter = iterator.next();
                if (txtFilter != null && txtFilter.getText() != null && !txtFilter.getText().isEmpty()) {
                    if ("dummyItem".equals(txtFilter.getId())) {
                        fList.add(new FilterItem(txtFilter.getTblIdent(), txtFilter.getText()));
                    } else {
                        fList.add(new FilterItem(txtFilter.getId(), txtFilter.getText()));
                    }
                }
            }
        }
        return fList;
    }

    // public static FilterItemList mapSearchFields(Set<TxtFilter> filterList) {
    //
    // FilterItemList fList = new FilterItemList();
    // if (filterList != null) {
    // for (Iterator<TxtFilter> iterator = filterList.iterator(); iterator.hasNext();) {
    // TxtFilter txtFilter = iterator.next();
    // if (txtFilter != null && txtFilter.getText() != null && !txtFilter.getText().isEmpty()) {
    // if ("dummyItem".equals(txtFilter.getId())) {
    // fList.add(new FilterItem(txtFilter.getTblIdent(), txtFilter.getText()));
    // } else {
    // fList.add(new FilterItem(txtFilter.getId(), txtFilter.getText()));
    // }
    // }
    // }
    // }
    //
    // return fList;
    // }

    /**
     * Das Scan-Text-Suchfeld "txt-lfsnr" [Lieferschennummer=Belegnummer] muss für Scanbelege nicht
     * nach Belegnummern suchen, sondern nach dem Feld "Bestellung". Für die generisch aufgebaute
     * Textfilter-suchliste gilt daher hier eine Ausnahme: Das Feld bekommt die Unterscheidung
     * "txt-lfsnr-scan".
     * 
     * @param List
     *            <TxtFilter> filterList
     * @param boolean isScanbeleg
     * @return FilterItemList
     * @deprecated
     */
    public static FilterItemList mapSearchFieldsScanning(List<TxtFilter> filterList, boolean isScanbeleg) {

        FilterItemList fl = mapSearchFields(filterList);
        List<FilterItem> fil = fl.getFilterItems();
        for (Iterator<FilterItem> iterator = fil.iterator(); iterator.hasNext();) {
            FilterItem filterItem = iterator.next();
            String filterIdent = filterItem.getFilterIdent();
            String filterText = filterItem.getFilterText();
            if (isScanbeleg && "txt-lfsnr".equals(filterIdent)) {
                filterIdent = filterIdent + "-scan";
                filterItem.setFilterIdent(filterIdent);
            }
        }
        return fl;
    }

    /**
     * Wandelt Umlaute in zweistellige UTF-8 konforme Zeichen.
     * 
     * @param String
     *            input
     * @return String output
     */
    public static String replaceUmlaute(String input) {
        StringBuffer sb = new StringBuffer("");

        char[] chAr = input.toCharArray();
        for (int i = 0; i < chAr.length; i++) {
            switch (chAr[i]) {
                case 'ä': {
                    sb.append("ae");
                    break;
                }
                case 'ö': {
                    sb.append("oe");
                    break;
                }
                case 'ü': {
                    sb.append("ue");
                    break;
                }
                case 'Ä': {
                    sb.append("Ae");
                    break;
                }
                case 'Ö': {
                    sb.append("Oe");
                    break;
                }
                case 'Ü': {
                    sb.append("Ue");
                    break;
                }
                case 'ß': {
                    sb.append("ss");
                    break;
                }
                default: {
                    sb.append(chAr[i]);
                    break;
                }
            }
        }
        return sb.toString();
    }

    /**
     * Escape characters for text appearing as XML data, between tags.
     * 
     * <P>
     * The following characters are replaced with corresponding character entities :
     * <table border='1' cellpadding='3' cellspacing='0'>
     * <tr>
     * <th>Character</th>
     * <th>Encoding</th>
     * </tr>
     * <tr>
     * <td><</td>
     * <td>&lt;</td>
     * </tr>
     * <tr>
     * <td>></td>
     * <td>&gt;</td>
     * </tr>
     * <tr>
     * <td>&</td>
     * <td>&amp;</td>
     * </tr>
     * <tr>
     * <td>"</td>
     * <td>&quot;</td>
     * </tr>
     * <tr>
     * <td>'</td>
     * <td>&#039;</td>
     * </tr>
     * </table>
     * 
     * <P>
     * Note that JSTL's {@code <c:out>} escapes the exact same set of characters as this method.
     * <span class='highlight'>That is, {@code <c:out>} is good for escaping to produce valid XML,
     * but not for producing safe HTML.</span>
     */
    private String prepareForXML(String aText) {
        final StringBuilder result = new StringBuilder();
        final StringCharacterIterator iterator = new StringCharacterIterator(aText);
        char character = iterator.current();
        while (character != CharacterIterator.DONE) {
            if (character == '<') {
                result.append("&lt;");
            } else if (character == '>') {
                result.append("&gt;");
            } else if (character == '\"') {
                result.append("&quot;");
            } else if (character == '\'') {
                result.append("&#039;");
            } else if (character == '&') {
                result.append("&amp;");
            } else if ((int) character < 32 || ((int) character > 126 && (int) character < 160)
                    || (int) character > 255) {
                result.append(" ");
            } else {
                // the char is not a special one
                // add it to the result as is
                result.append(character);
            }
            character = iterator.next();
        }
        return result.toString();
    }

    /**
     * Stellt fest, ob der gewählte Beleg ein Scanbeleg ist.
     * 
     * @return boolean
     */
    public static boolean isScanBeleg(ValueMap vm) {

        List<String> selBelArt = (List<String>) vm.get(Const.KEY_SELECTED_BELEGART);
        HashMap<String, List<String>> lucyBelegartMap = (HashMap<String, List<String>>) vm
                .get(Const.KEY_LIST_RECHERCHE_BELEGART);

        boolean isScanBeleg = false;
        List<String> sectionKz = lucyBelegartMap.get(Const.KEY_ALLE_SCANBELEGE);
        if (selBelArt == null || sectionKz == null) {
            isScanBeleg = false;
        } else {
            isScanBeleg = CollectionUtils.containsAny(selBelArt, sectionKz);
        }
        return isScanBeleg;
    }

    public static boolean isScanRetourenBeleg(ValueMap vm) {

        List<String> selBelArt = (List<String>) vm.get(Const.KEY_SELECTED_BELEGART);
        HashMap<String, List<String>> lucyBelegartMap = (HashMap<String, List<String>>) vm
                .get(Const.KEY_LIST_RECHERCHE_BELEGART);

        boolean isScanBeleg = false;
        List<String> sectionKz = lucyBelegartMap.get("Scanbelege-Retouren");
        if (selBelArt == null || sectionKz == null || selBelArt.get(0) == null) {
            isScanBeleg = false;
        } else {
            isScanBeleg = selBelArt.get(0).equals(Const.KEY_SCANBELEGE_RETOURE);
        }
        return isScanBeleg;
    }

    public static String getSuffix(java.io.File f) {
        String fstr = f.getAbsolutePath();
        int lastDot = fstr.lastIndexOf('.');
        if ((lastDot >= 0) && ((lastDot + 1) < fstr.length())) {
            return fstr.substring(lastDot + 1);
        }
        return "";
    }

    public static void copyFile(String src, String dest) {
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dest);

            copy(fis, fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException e) {}
            if (fos != null)
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * Wandelt das Ausgabeformat eines Datums Beispielsweise so:<br>
     * inDate: "1410240633" outDate: "2014.10.24 06:33". inFormat muss dem richtigen inDate - Format
     * entsprechen, d.h, in diesem Beispiel: yyMMddhhmm.
     * 
     * @param String
     *            inDate, Bsp: 1410240633
     * @param String
     *            inFormat, Bsp: yyMMddhhmm
     * @param String
     *            outFormat, Bsp: yyyy.MM.dd hh:mm
     * @return String
     */
    public static String inOutDateParser(String inDate, String inFormat, String outFormat) {
        try {
            if (inDate == null || inDate.length() == 0) {
                return "";
            }
            SimpleDateFormat fin = new SimpleDateFormat(inFormat);
            Date date = fin.parse(inDate);
            SimpleDateFormat fout = new SimpleDateFormat(outFormat);
            return fout.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return inDate;
    }

    public static String inOutParser(String inDate, String outFormat) {
        try {
            if (inDate == null || inDate.length() == 0) {
                return "";
            }
            long date = Long.valueOf(inDate);
            SimpleDateFormat fin = new SimpleDateFormat(outFormat);
            return fin.format(new Date(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inDate;
    }

    /**
     * Schnippelt aus dem übergebenen Text das Muster heraus.
     * 
     * @param String
     *            pattern
     * @param String
     *            inputText
     * @return String
     */
    public static String cutTextPattern(String regEx, String inputText) {
        Pattern patternDate = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = patternDate.matcher(inputText);
        return matcher.find() ? matcher.group() : "";
    }

    /**
     * Ermöglich das Suchen nach Filemustern per regulärem Ausdruck.
     * 
     * @param String
     *            startVerzeichnis
     * @param String
     *            extensionPattern. Beispiel: Wenn alles erlaubt ist, dann: ".*". Nach bestimmten
     *            Typen einschränken: (.*\\.gif$)|(.*\\.jpg$)
     * @param boolean rekursiv
     * @return List<File>
     */
    public List<File> searchFiles(String startVerzeichnis, String extensionPattern, boolean rekursiv) {

        final List<File> files = new ArrayList<File>();
        final Stack<File> dirs = new Stack<File>();
        final File startdir = new File(startVerzeichnis);
        final Pattern p = Pattern.compile(extensionPattern, Pattern.CASE_INSENSITIVE);

        if (startdir.isDirectory()) {
            dirs.push(startdir);
        }

        while (dirs.size() > 0) {
            for (File file : dirs.pop().listFiles()) {
                if (file.isDirectory()) {
                    if (rekursiv) {
                        dirs.push(file);
                    }
                } else if (p.matcher(file.getName()).matches()) {
                    files.add(file);
                }
            }
        }

        return files;
    }

    public static void copy(InputStream in, OutputStream out) throws IOException {
        int len;
        byte[] buffer = new byte[0xFFFF];
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
    }

    /**
     * Hier die korrekte, kompakte Variante leider geklaut und nicht selbst geschrieben. Siehe auch:
     * http://en.wikipedia.org/wiki/Binary_prefix.<br>
     * <br>
     * Hier eine lustige Zahlenreihe die die Abweichung nach oben dokumentiert:<br>
     * 
     * <pre>
     *                               SI     BINARY
     * 
     *                    0:        0 B        0 B
     *                   27:       27 B       27 B
     *                  999:      999 B      999 B
     *                 1000:     1.0 kB     1000 B
     *                 1023:     1.0 kB     1023 B
     *                 1024:     1.0 kB    1.0 KiB
     *                 1728:     1.7 kB    1.7 KiB
     *               110592:   110.6 kB  108.0 KiB
     *              7077888:     7.1 MB    6.8 MiB
     *            452984832:   453.0 MB  432.0 MiB
     *          28991029248:    29.0 GB   27.0 GiB
     *        1855425871872:     1.9 TB    1.7 TiB
     *  9223372036854775807:     9.2 EB    8.0 EiB   (Long.MAX_VALUE)
     * 
     * </pre>
     * 
     * Liefert formatierte Bytewerte, wahlweise im si/binärformat. Die Länge der Kommastelle lässt
     * sich per Argument bestimmen.
     * 
     * @param long bytes
     * @param boolean si true: Dezimalprefix, false: Binärprefix.
     * @return String
     */
    public static String formatBytes(long bytes, boolean si, Integer decpoint) {

        int komma = 0;

        if (decpoint != null) {
            komma = decpoint;
        }

        if (bytes < 0) {
            return 0 + " Byte";
        }

        int unit = si ? 1000 : 1024;
        if (bytes < unit) {
            return bytes + " Byte";
        }

        int exp = (int) (Math.log(bytes) / Math.log(unit));
        StringBuffer pre = new StringBuffer();
        pre.append((si ? "KMGTPE" : "KMGTPE").charAt(exp - 1));
        // pre.append((si ? "" : "i"));
        // String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre.toString()) eine Kommastelle
        return String.format("%." + komma + "f %sB", bytes / Math.pow(unit, exp), pre.toString());
    }

    /**
     * Wie FileUtils#formatBytes(long, boolean, Integer). Liefert den Wert ohne Komma, aber
     * Formatiert.
     * 
     * @param long bytes
     * @param boolean si
     * @return String
     * @see FileUtils#formatBytes(long, boolean, Integer)
     */
    public static String formatBytes(long bytes, boolean si) {
        return formatBytes(bytes, si, null);
    }

    /**
     * Wie FileUtils#formatBytes(long, boolean, Integer). Liefert den Wert unformatiert.
     * 
     * @param long bytes
     * @param boolean si
     * @return double
     * @see FileUtils#formatBytes(long, boolean, Integer)
     */
    public static double formatBytesD(long bytes, boolean si) {

        if (bytes < 0) {
            return 0;
        }

        int unit = si ? 1000 : 1024;
        if (bytes < unit) {
            return bytes;
        }

        int exp = (int) (Math.log(bytes) / Math.log(unit));
        StringBuffer pre = new StringBuffer();
        pre.append((si ? "kMGTPE" : "KMGTPE").charAt(exp - 1));

        // String value = String.format("%.0f", bytes / Math.pow(unit, exp));
        return (double) ((double) bytes / (double) Math.pow(unit, exp));

        // Das String.format("%.0f" bewirkt ein auf/abrunden!
        // String value = String.format("%.0f", bytes / Math.pow(unit, exp));
        // return Long.valueOf(value);
    }

    /**
     * 18.10.2015: Für AEP soll es in Zukunft für jeden Beleg noch ein Archivierungsdatum neben dem
     * Belegdatum geben. Wenn ich Helmut richtig verstanden habe, hat das Archivierungsdatum immer
     * Priorität. Nur wenn es nicht gesetzt ist, nehme ich das Belegdatum. Alle 4 Möglichen Fälle
     * werden hier abgeprüft.<br>
     * <br>
     * 29.03.2017: Nachtrag: Archivierungsdatum ist für den Datencontainer. Belegdatum ist für die
     * Suche. Bald soll es Umgesetzt werden ... .<br>
     * <br>
     * 23.06.2017: Das Archivierungsdatum liegt zusätzlich zur Offset-Position im Offset-Feld.<br>
     * Muster: 123456@20170623<br>
     * <br>
     * Alte Indexeinträge NUR mit Offsetposition müssen natürlich lesbar bleiben. D.h.: Ich gucke im
     * Offset-Feld nach, ob sich dort ein Feld mit Raute? (TODO: Mit Helmut klären) befindet. Wenn
     * ja, wird aus diesem Feld das relevante Datum ermittelt. Wenn nein, nehme ich das Datum aus
     * dem Datumsfeld.
     */
    public static String getDate4PDFLocation(Fundstelle fs) {

        String offset = fs.getOffset();

        if (offset != null && offset.length() > 0) {
            int index = offset.indexOf(Const.KEY_OFFSET_DIVIDER);
            if (index != -1) {
                return offset.substring(index + 1);
            } else {
                return fs.getDatum();
            }
        } else {
            return fs.getDatum();
        }

        // ///////////////////////////////////////////////////////////////////
        // /// Alte Implementierung
        // /
        // // 1. Belegdatum gefüllt, Rest leer. Betrifft alle alten Dokumente
        // if (isEmpty(fs.getImpdate()) && !isEmpty(fs.getDatum())) {
        // return fs.getDatum();
        // }
        //
        // // 2. Beide gefüllt
        // if (!isEmpty(fs.getImpdate()) && !isEmpty(fs.getDatum())) {
        // return fs.getImpdate();
        // }
        //
        // // 3. Archivierungsdatum gefüllt, Rest leer
        // if (!isEmpty(fs.getImpdate()) && isEmpty(fs.getDatum())) {
        // return fs.getImpdate();
        // }
        //
        // // 2. Beide leer ?
        // if (isEmpty(fs.getImpdate()) && isEmpty(fs.getDatum())) {
        // return null;
        // }
        //
        // // default, unreachable.
        // return fs.getImpdate();

    }

    /**
     * Das Offset - Feld kann so:<br>
     * 1234567 oder so: 1234567@20170623 aussehen.<br>
     * 
     * @param offsetField
     * @return
     */
    public static String getCorrectOffsetPosition(String offsetField) {

        if (offsetField != null && offsetField.length() > 0) {
            int index = offsetField.indexOf(Const.KEY_OFFSET_DIVIDER);
            if (index != -1) {
                // Dann haben wir es mit dem "neuen" Archivdatumsfeld zu tun.
                return offsetField.substring(0, index);
            } else {
                return offsetField;
            }
        } else {
            return "";
        }
    }

    /**
     * Guckt nach, ob es sich um ein "neues" Offset-Feld handelt. Neue Felder besitzen eine Raute.
     * 
     * @param offset
     * @return
     */
    private static boolean isNewOffsetField(String offset) {
        return false;
    }

    private static boolean isEmpty(String s) {
        if (s == null) {
            return true;
        } else {
            return s.isEmpty();
        }
    }

    public static String lowerCaseFirstChar(String str) {
        if (str != null && str.length() > 0) {
            StringBuilder sb = new StringBuilder(str.length());
            sb.append(str.substring(0, 1).toLowerCase());
            sb.append(str.substring(1, str.length()));
            return sb.toString();
        } else {
            return str;
        }
    }

    /**
     * Prüft, ob das Feld in der Klasse existiert.
     * 
     * 
     * @param Class
     *            <?> clz
     * @param String
     *            fieldName
     * @return boolean
     */
    public static boolean fieldExist(Class<?> clz, String fieldName) {

        try {
            clz.getDeclaredField(Util.lowerCaseFirstChar(fieldName));
        } catch (NoSuchFieldException ex) {
            // field doesn't exist
            return false;
        } catch (SecurityException ex) {
            // no access to field
            return false;
        }
        return true;
    }
    
    /**
     * Sucht rekursiv nach dem angegebenen Muster.
     * 
     * @param File
     *            dir
     * @param String
     *            find, z.B.: ".*\\.zip$"
     * @return ArrayList<File>
     */
    public static ArrayList<File> searchFileByRegEx(File dir, String find) {

        Stack<File> dirs = new Stack<File>();
        ArrayList<File> matches = new ArrayList<File>();
        Pattern searchPattern = Pattern.compile(find, Pattern.CASE_INSENSITIVE);

        if (dir.isDirectory()) {
            dirs.push(dir);
        }

        if (dir.isFile()) {
            dirs.push(new File(dir.getParent()));
        }

        while (dirs.size() > 0) {

            // Bei Angaben wie: Startverzeichnis: D:\\ oder c:\\Windows befinden sich aktive
            // Dateien in dieser Liste. Das macht die Behandlung (filesS == null) continue
            // nötig.
            File firstFile = dirs.pop();

            // An dieser Stelle NUR MIT String-Arrays arbeiten !!!
            // Bei grossen Verzeichnissen (Noweda Tagesbelege 380.000) treten hier
            // sonst Speicherprobleme auf. Wir erhalten hier nur den Filenamen !!!
            String[] filesS = firstFile.list();

            if (filesS == null) {
                continue;
            }

            for (int i = 0; i < filesS.length; i++) {

                StringBuffer fileNameBuf = new StringBuffer();
                fileNameBuf.append(firstFile.getPath());
                fileNameBuf.append(Const.FS);
                fileNameBuf.append(filesS[i]);
                File fileTmp = new File(fileNameBuf.toString());

                if (fileTmp.isDirectory()) {
                    dirs.push(fileTmp);
                } else if (searchPattern.matcher(fileTmp.getName()).matches()) {
                    matches.add(fileTmp);
                }
            }
        }

        return matches;
    }
}
