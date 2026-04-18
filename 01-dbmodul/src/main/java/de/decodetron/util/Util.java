// $Log: Util.java,v $
// Revision 1.12  2015/07/20 14:07:14  tw
// Implementierung: Luecken-SR.
//
// Revision 1.11  2015/07/16 22:15:28  tw
// Implementierung: Luecken-SR. Performance-Optimierung.
//
// Revision 1.10  2015/03/24 23:20:02  tw
// CR Feng-ID: 3947#7
//
// Revision 1.9  2015/03/18 22:40:01  tw
// CR Feng-ID: 3947#4
//
// Revision 1.8  2015/02/03 00:51:19  tw
// Haldenbearbeitung: Einlesen v. Archivierungsdatensaetzen ermoeglicht.
//
// Revision 1.7  2015/01/30 21:36:42  tw
// import entfernt.
//
// Revision 1.6  2015/01/30 21:22:31  tw
// UTF-8 Umstellung.
//
// Revision 1.5  2015/01/30 02:43:37  tw
// Haldenbearbeitung 1. Wurf.
//
// Revision 1.4  2014/11/03 16:57:06  tw
// Testanbindung: History-DB Benutzerrechte.
//
// Revision 1.3  2014/06/12 15:37:11  tw
// Benutzer bearbeiten: Vorbereitung f. korrekte Gruppenverarbeitung.
//
// Revision 1.2  2014/04/15 00:15:39  tw
// SQL-Injection sichere Verarbeitung (nur f. Defekentlisten!).
//
// Revision 1.1  2014/02/26 15:00:16  tw
// Anpassung an neue DB-Struktur: Gebietssleiter.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
//

package de.decodetron.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import de.decodetron.bo.Group;

/**
 * @author Thomas Winter
 * @since 26.02.2014
 */
public class Util {

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

    /**
     * Splittet mit Komma oder Punkt separierte Strings in eine Liste auf.
     * 
     * @param String
     *            s2parse
     * @return List<String>
     */
    public static List<String> toList(String s2parse) {
        if (s2parse != null) {
            return Arrays.asList(s2parse.split("[\\,\\.]"));
        } else {
            return Arrays.asList("");
        }
    }

    /**
     * Liefert die Ids einer Gruppe in einer Kommaseparierten Liste zurück.
     * 
     * @param List
     *            <Group> groupList
     * @return String
     */
    public static String getIdsFromGroup(List<Group> groupList) {

        int cnt = 0;
        StringBuffer sbKommaSepList = new StringBuffer();

        for (Iterator<Group> iterator = groupList.iterator(); iterator.hasNext();) {
            Group group = iterator.next();
            if (group != null) {
                sbKommaSepList.append((cnt > 0 && cnt < groupList.size()) ? "," : "");
                sbKommaSepList.append(String.valueOf(group.getId()));
                cnt++;
            }
        }
        return sbKommaSepList.toString();
    }

    /**
     * Merged 2 Arrays.
     * 
     * @param <T>
     * @param T
     *            [] first
     * @param T
     *            [] second
     * @return <T> T[]
     */
    public static <T> T[] concatArray(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    /**
     * Merged beliebig viele Arrays.
     * 
     * @param <T>
     * @param T
     *            [] first
     * @param T
     *            []... rest
     * @return <T> T[]
     */
    public static <T> T[] concatAllArrays(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    /**
     * Erzeugt aus dem Objekt ein .xml File.
     * 
     * @param File
     *            file
     * @param Object
     *            o, hier ist das komplette Objekt nötig, da ansonsten die @XmlRootElement -
     *            Anotation nicht interpretiert wird.
     * @return File
     */
    public static File object2XmlFile(File file, Object o) {

        JAXBContext jaxbCtx;

        try {
            jaxbCtx = JAXBContext.newInstance(o.getClass());
            Marshaller jaxbMarshaller = jaxbCtx.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // jaxbMarshaller.marshal(user, System.out);
            if (file.exists()) {
                // olles File löschen
                file.delete();
            }
            jaxbMarshaller.marshal(o, file);
        } catch (JAXBException jbe) {
            jbe.printStackTrace();
        }

        return file;
    }

    public static String object2XmlString(Object o) {

        JAXBContext jaxbCtx;
        StringWriter sw = new StringWriter();

        try {
            jaxbCtx = JAXBContext.newInstance(o.getClass());
            Marshaller jaxbMarshaller = jaxbCtx.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(o, sw);
        } catch (JAXBException jbe) {
            jbe.printStackTrace();
        }

        return sw.toString();
    }

    public static Object file2Object(File file, Class<? extends Object> o) {

        JAXBContext jaxbCtx;
        try {
            jaxbCtx = JAXBContext.newInstance(o);
            Unmarshaller um = jaxbCtx.createUnmarshaller();
            Object oTmp = um.unmarshal(new FileReader(file));
            return oTmp;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JAXBException jbe) {
            jbe.printStackTrace();
        }
        return o;
    }

    public static List<File> sortFilesLastModifiedDesc(List<File> files) {
        File[] fsortDesc = files.toArray(new File[files.size()]);
        Arrays.sort(fsortDesc, new Comparator<File>() {
            public int compare(File f1, File f2) {
                return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());
            }
        });
        return Arrays.asList(fsortDesc);
    }

    /**
     * Sucht rekursiv in dem angegebenen Verzeichnis nach dem Muster.
     * 
     * @param String
     *            sourceDir
     * @param String
     *            pattern, Regulärer Ausdruck erforderlich, z.B. ".*\\.pdf$"
     * @return ArrayList<File>
     */
    public static List<File> getFiles(String sourceDir, String pattern) {
        return getFiles(sourceDir, pattern, true);
    }

    /**
     * Eine File-Suchfunktion.
     * 
     * @param String
     *            sourceDir
     * @param String
     *            pattern, Regulärer Ausdruck erforderlich, z.B. ".*\\.pdf$"
     * @param boolean rekursiv
     * @return ArrayList<File>
     */
    public static List<File> getFiles(String sourceDir, String pattern, boolean rekursiv) {

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

    /**
     * Schnippelt die Endung eines Files ab.
     * 
     * @param File
     *            f
     * @return String
     */
    public static String cutSuffix(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        if ((lastDot >= 0) && ((lastDot + 1) < fileName.length())) {
            return fileName.substring(0, lastDot);
        }
        return "";
    }

    /**
     * Fügt einer Datei den Inhalt "text" am Ende der Datei hinzu. Die Datei wird, falls nicht
     * vorhanden, neu erzeugt. Die Codierung erfolgt in UTF-8!
     * 
     * @param File
     *            fileName
     * @param String
     *            text (UTF-8)
     */
    public static void appendToFile(File file, String text) {
        RandomAccessFile raf = null;

        if (text == null || text.length() == 0) {
            return;
        }

        try {
            raf = new RandomAccessFile(file, "rw");
            raf.seek(raf.length());
            raf.write(text.getBytes("UTF-8"));

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
}
