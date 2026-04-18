// $Log: DAOIDefekte.java,v $
// Revision 1.8  2020/02/26 19:23:08  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.7  2017/06/22 13:36:07  tw
// Mobilmachung der Headrevision.
//
// Revision 1.6  2014/05/21 13:58:44  tw
// Versuchsblase: Neudefinition v. Lieferanten.
//
// Revision 1.5  2014/05/12 20:55:25  tw
// Aufraeumarbeiten, Benutzer anlegen loeschen.
//
// Revision 1.4  2014/05/09 21:00:32  tw
// Change Request: Doppelte Defektenlisten v. 09.05.2014. (2) gilt nur fuer Lieferanten.
//
// Revision 1.3  2014/05/07 17:21:52  tw
// Change Request: Doppelte Defektenlisten.
//
// Revision 1.2  2014/05/04 20:22:48  tw
// Benutzer anlegen: Neue Schnittstelle: getDistinctByColName
//
// Revision 1.1  2014/04/28 11:15:22  tw
// Schnittstellen- umzug/umbenennung Filter=>Statistik
//
// Revision 1.4  2014/04/15 00:15:39  tw
// SQL-Injection sichere Verarbeitung (nur f. Defekentlisten!).
//
// Revision 1.3  2014/03/31 21:45:46  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.2  2014/03/28 23:02:33  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.1  2014/03/28 17:22:33  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
//

package de.decodetron.dao.statistik.defekte;

import java.util.List;

import de.decodetron.bo.DataRecord;
import de.decodetron.bo.FilterItemList;
import de.decodetron.bo.LimitInfo;
import de.decodetron.bo.SortInfo;
import de.decodetron.bo.User;
import de.decodetron.dao.statistik.StatistikDAOI;

/**
 * 21.05.2014, ein Benutzer der der Gruppe "Lieferant" angehört wird nicht mehr durch die Gruppe mit
 * der Kennung: "LIEF" beschrieben, sondern durch die zugehörigkeit der Sektionen: "clubbestand",
 * "clubabver", "ververl". Besitzt ein Benutzer diese drei Sektionen gemeinsam, ist er ein
 * Lieferant.<br>
 * <br>
 * - Lieferanten dürfen die Statistiken: "AEP Plus Bestand", "AEP Plus Abverkauf" sowie die
 * Defektenlisten (L) sehen.<br>
 * - Für Lieferanten die sich die Statistik: "Defektenliste (L)" betrachten, gilt:<br>
 * - Die Spalten: „Kundennummer“, „Auftragsnummer“, "LieferantNr" und „Betrag“ werden ausgeblendet.<br>
 * - Mehrfachbelege werden nicht angezeigt.
 * 
 * @author Thomas Winter
 * @since 28.03.2014
 */
public interface DAOIDefekte extends StatistikDAOI {

    /**
     * Liefert Spaltenbezeichner für Defektenlisten. Auch hier gilt: <br>
     * 1.) Lieferanten dürfen die Spalten „Kundennummer“, „Auftragsnummer“ und „Betrag“ nicht sehen,
     * 20.03.2014, Hr. Kastner per Mail. <br>
     * 2.) Den Filteridentifier seiner Sektion sollte der Kunde auch nicht sehen, da die Nummer
     * ohnehin immer die selbe ist.
     * 
     * @param User
     *            user
     * @return String
     */
    public DataRecord getDefekteColNames(User user);

    /**
     * Zeit aufzuräumen. Das soll ein Prototyp mit zusammengefassten Schnittstellen werden. Die
     * Besonderheit bei den Defektenlisten ist:<br>
     * 1.) Lieferanten dürfen die Spalten „Kundennummer“, „Auftragsnummer“ und „Betrag“ nicht sehen,
     * 20.03.2014, Hr. Kastner per Mail.<br>
     * 2.) Den Filteridentifier seiner Sektion sollte der Kunde auch nicht sehen, da die Nummer
     * ohnehin immer die selbe ist.
     */
    public List<DataRecord> getDefekteData(//
            User user,//
            String colName,//
            SortInfo sortInfo,//
            LimitInfo limitInfo,//
            FilterItemList listTxtFields);

    /**
     * 09.05.2014: Die eingebaute Änderung soll nur für Lieferenten gelten!<br>
     * <br>
     * Nachtrag, 07.05.2014: <br>
     * Da die Idioten von AEP nicht in der Lage sind, ihre Dopplungen erst gar nicht zu senden,
     * müssen wir den Scheiss jetzt rausfiltern. Fürs Count ist diese geschachtelte Konstruktion
     * nötig:
     * 
     * <pre>
     * select count(*) from (
     *        select * from defekte d
     *        where d.PZN like '1097875' and d.AuftragsNr like '2477' and d.KundenNr like '1010101'
     *        group by d.AuftragDatum, d.KundenNr, d.AuftragsNr, d.PZN, d.Preis
     *        order by d.pzn
     * );
     * </pre>
     * 
     * @param User
     *            user
     * @param String
     *            colName
     * @param FilterItemList
     *            listTxtFields
     * @return long
     * @see #getDefekteData(User, String, FilterItemList)
     */
    public long countDefekteData(//
            User user,//
            String colName,//
            FilterItemList listTxtFields);

    /**
     * Test f. prepared Statements ...
     */
    public void createStatementWithoutValue();

    /**
     * Test f. Prepared Statements ...
     * 
     * @return
     */
    public long countDefekteDataTest();
}
