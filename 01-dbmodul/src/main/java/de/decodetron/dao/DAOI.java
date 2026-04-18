// $Log: DAOI.java,v $
// Revision 1.8  2020/02/28 20:30:09  tw
// Alte Funktionen entfernt. System.out. f. Debugzwecke erstellt.
//
// Revision 1.7  2014/05/06 00:43:40  tw
// Backup: Benutzer anlegen.
//
// Revision 1.6  2014/03/28 17:22:33  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.5  2014/02/20 03:52:16  tw
// Statistik-Schnittstellen implementiert.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.4  2013/11/25 16:51:48  tw
// Waehrungsformatierung.
//
// Revision 1.3  2013/11/25 11:01:36  tw
// Limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.2  2013/11/21 22:11:35  tw
// Vorbereitung: neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.1  2013/11/21 17:40:02  tw
// Neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.12  2013/11/18 10:10:59  tw
// Bugfix: Multiuserfilter.
//
// Revision 1.11  2013/11/17 13:41:59  tw
// Neue Schnittstelle f. User mit mehrfachfiltern implementiert.
//
// Revision 1.10  2013/11/15 21:47:41  tw
// Tabellenspalten werden per seperater DB-Funktion abgerufen.
//
// Revision 1.9  2013/11/14 15:49:20  tw
// Schnittstellennormalisierung.
//
// Revision 1.8  2013/11/14 01:34:42  tw
// aaaaaaaaaaaaaaargh umlaute.
//
// Revision 1.7  2013/11/13 23:13:20  tw
// Vorbereitung: Schnittstellen fuer Datenfilter.
//
// Revision 1.6  2013/11/13 00:29:59  tw
// Servicefunktionen fuer Berechtigungskonzept implementiert.
//
// Revision 1.5  2013/11/10 23:31:56  tw
// .
//
// Revision 1.4  2013/11/08 11:00:34  tw
// Umlaute an vorerst ersetzt f. Fehlereingrenzung.
//
// Revision 1.3  2013/11/06 22:15:28  tw
// Neue Schnittstellentests f. flexiblere Abfragen.
//
// Revision 1.2  2013/11/05 23:02:09  tw
// db-schnittstellen aufger�umt.
//
// Revision 1.1  2013/11/02 21:46:42  tw
// ClubBestand-DB-Schicht. 1. Schritte zur Veralgemeinerung.
//
//

package de.decodetron.dao;

import java.util.List;

import de.decodetron.bo.DataRecord;
import de.decodetron.exc.DAOException;

/**
 * @author Thomas Winter
 * @since 02.11.2013
 */
public interface DAOI {

    /**
     * Allgemeine Funktion, um einer Tabelle alle Daten zu entlocken. Der erste DataRecord enthält
     * die Spaltenbezeichnungen!
     * 
     * @param String
     *            tableName
     * @return List<DataRecord>
     * @throws DAOException
     */
    public List<DataRecord> getAllRecords(String tableName) throws DAOException;

    /**
     * Zählt die Datensätze für eine bestimmte Tabelle.
     * 
     * @param String
     *            tableName
     * @return Long
     * @throws DAOException
     */
    public Long countAllRecords(String tableName) throws DAOException;


    /**
     * Gibt eine limitierte Datenmenge zurück. Baut ein sql-String zusammen a la:<br>
     * 
     * <pre>
     * select * from defekte d order by d.menge desc limit 4 offset 4;
     * </pre>
     * 
     * @param String
     *            tableName
     * @param String
     *            colName2Sort
     * @param String
     *            sortOrder
     * @param String
     *            limit
     * @param String
     *            offset
     * @return List<DataRecord>
     * @throws DAOException
     */
    public List<DataRecord> getAllRecordsLimited(String tableName, String colIndex2Sort, String sortOrder, int limit,
            long offset) throws DAOException;

    /**
     * Filtert alle Treffer einer Tabelle nach den angegeben Werten. Die Reihenfolge der Werte muss
     * der Reihenfolge der Tabellenspalten entsprechen! Alle Filterwerte werden mit UND verknüpft.<br>
     * <br>
     * 
     * Baut folgenden sql-String dynamisch zusammen:
     * 
     * <pre>
     * select * from [tablename] c
     * where c.{col1} like '%[filterValues]%'
     * and c.{col2} like '%[filterValues]%'
     * </pre>
     * 
     * @param String
     *            tableName
     * @param String
     *            ... values
     * @return List<DataRecord>
     * @throws DAOException
     */
    public List<DataRecord> getAllRecordsFilterBy(String tableName, String... filterValues) throws DAOException;

    /**
     * Löscht alle Einträge einer Tabelle die dem Filter entsprechen. VORSICHT: Wenn keine Filter
     * mitgegeben werden, werden ALLE Einträge einer Tabelle glöscht !!!
     * 
     * @param String
     *            tableName
     * @param String
     *            ... filterValues
     * @throws DAOException
     */
    public void deleteAllRecordsBy(String tableName, String... filterValues) throws DAOException;


    /**
     * Liefert die Spaltennamen einer Tabelle.
     * 
     * @param String
     *            tableName
     * @return List<String>
     */
    public List<String> getColumNames(String tableName);

    /**
     * Liefert die Spaltentypen einer Tabelle.
     * 
     * @param String
     *            tableName
     * @return List<String>
     */
    public List<String> getColumTypes(String tableName);

    /**
     * Liefert die Spaltennamen einer Tabelle als DataRecord.
     * 
     * @param String
     *            tableName
     * @return DataRecord
     */
    public DataRecord getColumNamesAsDataRecord(String tableName);
    

    /**
     * Gibt den Spalteninhalt aus der Benutzertabelle ohne Dopplungen (distinct).
     * 
     * @param String
     *            tblName
     * @param String
     *            colName
     * @return List<String>
     */
    public List<String> getDistinctByColName(String tblName, String colName);
}
