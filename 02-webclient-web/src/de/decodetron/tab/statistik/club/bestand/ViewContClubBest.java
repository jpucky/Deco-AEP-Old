// $Log: ViewContClubBest.java,v $
// Revision 1.10  2020/03/09 22:33:41  tw
// Version 1.32-H
// -----------------------------
// CR: Umstellung der Statements um Einschraenkung per Datum vornehmen zu koennen.
// BUGFIX: Datums-Suche AEP-Plus (CLUBBESTAND) / Valuta korrigiert.
//
// Revision 1.9  2020/02/26 19:24:11  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.8  2015/02/27 16:39:40  tw
// Skriptumbau: Bugfix f. Statistik.
//
// Revision 1.7  2014/02/07 02:07:05  tw
// Restzeitaktualisierung bei Aktivitaeten implementiert.
//
// Revision 1.6  2014/01/17 21:39:27  tw
// Bufix: ClubKz auch aus dem Suchbereich entfernt.
//
// Revision 1.5  2013/12/15 22:28:13  tw
// Einbau: Fixed Tableheader.
//
// Revision 1.4  2013/12/09 14:09:18  tw
// Von-Bis Suchfeld, Statistik.
//
// Revision 1.3  2013/12/02 12:33:54  tw
// Neue Schnittstelle um unnoetige Spalten auszublenden.
//
// Revision 1.2  2013/11/25 14:55:22  tw
// Defektenliste: limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.1  2013/11/25 13:16:59  tw
// Clubabverkauf: limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.8  2013/11/18 10:45:56  tw
// Multifilter an die Statistik angebunden.
//
// Revision 1.7  2013/11/17 16:18:44  tw
// Recherche an den Multifilter angebunden. Modulumstellung auf KZ.
//
// Revision 1.6  2013/11/15 21:48:03  tw
// Tabellenspalten werden per seperater DB-Funktion abgerufen.
//
// Revision 1.5  2013/11/14 15:49:40  tw
// Filter - Schnittstellennormalisierung.
//
// Revision 1.4  2013/11/14 14:09:04  tw
// Schnittstellen Datenfilterung implementiert.
//
// Revision 1.3  2013/11/09 19:01:12  tw
// Defektenliste, Zusammenfassungsarbeiten, Nullponter-Exception Sortierung behoben.
//
// Revision 1.2  2013/11/08 12:50:25  tw
// Club-Abverkauf implementiert.
//
// Revision 1.1  2013/11/08 12:00:26  tw
// Umbennenung.
//
// Revision 1.1  2013/11/07 22:43:04  tw
// Hinweis Listentyp eingebaut.
//
// Revision 1.1  2013/11/07 21:59:39  tw
// Clubliste geradegezogen.
//
//

package de.decodetron.tab.statistik.club.bestand;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPApplication;
import de.decodetron.Const;
import de.decodetron.bo.DataRecord;
import de.decodetron.dao.statistik.StatistikDAOI;
import de.decodetron.data.Util;
import de.decodetron.event.ChangeEvent;
import de.decodetron.tab.statistik.TxtFilter;

/**
 * Beinhaltet die Komponenten: Filter und Listenansicht.
 * 
 * @author Thomas Winter
 * @since 07.11.2013
 */
public class ViewContClubBest extends Panel {

    public static String MODULNAME = "clubbestand"; // Fix in der DB vergeben!
    
    public ViewContClubBest(String id, IModel<?> model) {
        super(id, model);
        add(new ViewFilter("viewFilter", model));
    }

    public class ViewFilter extends Form {

        public ViewFilter(String id, IModel model) {
            super(id, model);
            clearInput();
            
            StatistikDAOI statistikDao = AEPApplication.get().getDBClubBestand();
            DataRecord tblHeader = statistikDao.getColumNamesAsDataRecord(Const.TABLENAME_BESTAND);

            int colNr = 0;
            ValueMap map = (ValueMap) model.getObject();
            map.put(Const.KEY_LIST_FILTERSUCHE, new ArrayList<TxtFilter>());
            final List<TxtFilter> list = (List<TxtFilter>) map.get(Const.KEY_LIST_FILTERSUCHE);

            // ///////////////////////////////////////////////////////////////////////////
            // /// Statische Extrawurst f. Datum- Von Bis.
            // /
            final TxtFilter auftragDatumVon;
            final  TxtFilter auftragDatumBis;
            add(auftragDatumVon = new TxtFilter("TagesDatumVon", this));
            add(auftragDatumBis = new TxtFilter("TagesDatumBis", this));
            
            list.add(auftragDatumVon);
            list.add(auftragDatumBis);

            RepeatingView rv = new RepeatingView("repeating");
            while (colNr++ < tblHeader.getSize() - 1) {

                TxtFilter txt = null;
                String tblName = tblHeader.getColItem(colNr);
                WebMarkupContainer c1 = new WebMarkupContainer(rv.newChildId());

                if (!tblName.startsWith("TagesDatum")) {
                    rv.add(c1);
                    c1.add(new Label("label", new ResourceModel("label." + tblName, tblName)));
                    c1.add(txt = new TxtFilter("dummyItem", tblName));
                }
                list.add(txt);
            }
            add(rv);   

            final ViewListClubBest viewListClub;
            add(viewListClub = new ViewListClubBest("viewList", model));
            
            add(new AjaxButton("start-search", this) {
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    Util.parseCurrency(list);
                    target.add(viewListClub);                    
                    new ChangeEvent(this, target, null, Const.ACTION_STAT_SUCHESTARTED).fire();
                }

                protected void onError(AjaxRequestTarget target, Form<?> form) {}
            });

        }
    }
}
