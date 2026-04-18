// $Log: ViewContDefekte.java,v $
// Revision 1.22  2020/03/09 22:33:41  tw
// Version 1.32-H
// -----------------------------
// CR: Umstellung der Statements um Einschraenkung per Datum vornehmen zu koennen.
// BUGFIX: Datums-Suche AEP-Plus (CLUBBESTAND) / Valuta korrigiert.
//
// Revision 1.21  2020/02/26 19:24:12  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.20  2015/02/27 16:39:40  tw
// Skriptumbau: Bugfix f. Statistik.
//
// Revision 1.19  2014/05/21 14:12:04  tw
// Versuchsblase: Neudefinition v. Lieferanten.
//
// Revision 1.18  2014/04/28 21:57:52  tw
// Backup: Benutzer anlegen.
//
// Revision 1.17  2014/04/28 11:11:15  tw
// Schnittstellen- umzug/umbenennung Filter=>Statistik
//
// Revision 1.16  2014/04/14 13:41:00  tw
// Fix. f. Recherche-Textfelder.
//
// Revision 1.15  2014/04/01 01:00:07  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.14  2014/03/28 23:58:14  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.13  2014/03/28 23:02:49  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.12  2014/03/28 17:23:44  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.11  2014/02/07 02:07:06  tw
// Restzeitaktualisierung bei Aktivitaeten implementiert.
//
// Revision 1.10  2013/12/15 22:28:13  tw
// Einbau: Fixed Tableheader.
//
// Revision 1.9  2013/12/09 14:09:19  tw
// Von-Bis Suchfeld, Statistik.
//
// Revision 1.8  2013/12/02 12:33:54  tw
// Neue Schnittstelle um unnoetige Spalten auszublenden.
//
// Revision 1.7  2013/11/28 14:41:43  tw
// Bugfix Waehrungssuche.
//
// Revision 1.6  2013/11/25 14:55:22  tw
// Defektenliste: limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.5  2013/11/18 10:45:56  tw
// Multifilter an die Statistik angebunden.
//
// Revision 1.4  2013/11/17 16:18:44  tw
// Recherche an den Multifilter angebunden. Modulumstellung auf KZ.
//
// Revision 1.3  2013/11/15 21:48:03  tw
// Tabellenspalten werden per seperater DB-Funktion abgerufen.
//
// Revision 1.2  2013/11/14 16:00:06  tw
// Defektenliste ans Berechtigungssystem angebunden.
//
// Revision 1.1  2013/11/09 19:01:12  tw
// Defektenliste, Zusammenfassungsarbeiten, Nullponter-Exception Sortierung behoben.
//
//

package de.decodetron.tab.statistik.defekte;

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
import de.decodetron.dao.statistik.defekte.DAODefekte;
import de.decodetron.data.Util;
import de.decodetron.event.ChangeEvent;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.statistik.TxtFilter;

/**
 * @author Thomas Winter
 * @since 09.11.2013
 */
public class ViewContDefekte extends Panel {

    public static String MODULNAME = "verver"; // Fix in der DB vergeben!
    public static String MODULNAMEL = "ververl"; // Fix in der DB vergeben!

    public ViewContDefekte(String id, IModel<?> model) {
        super(id, model);
        add(new ViewFilter("viewFilter", model));
    }

    public class ViewFilter extends Form {

        public ViewFilter(String id, IModel model) {

            super(id, model);
            
            clearInput();
            DAODefekte daoDefekte = AEPApplication.get().getDBDefekteFilter();
            DataRecord tblHeader = daoDefekte.getDefekteColNames(LoginSession.get().getUser());

            int colNr = 0;
            ValueMap map = (ValueMap) model.getObject();
            map.put(Const.KEY_LIST_FILTERSUCHE, new ArrayList<TxtFilter>());
            final List<TxtFilter> list = (List<TxtFilter>) map.get(Const.KEY_LIST_FILTERSUCHE);

            // ///////////////////////////////////////////////////////////////////////////
            // /// Statische Extrawurst f. Datum- Von Bis.
            // /
            final TxtFilter auftragDatumVon;
            final  TxtFilter auftragDatumBis;
            add(auftragDatumVon = new TxtFilter("AuftragDatumVon", this));
            add(auftragDatumBis = new TxtFilter("AuftragDatumBis", this));
            
            // TODO: Falls die Datenmenge zu groß wird hier eine Einschränkung vorgeben.
            // auftragDatumVon.setText("2020-01-01");
            
            list.add(auftragDatumVon);
            list.add(auftragDatumBis);

            RepeatingView rv = new RepeatingView("repeating");
            while (colNr++ < tblHeader.getSize() - 1) {

                TxtFilter txt = null;
                String tblName = tblHeader.getColItem(colNr);
                WebMarkupContainer c1 = new WebMarkupContainer(rv.newChildId());

                if (!tblName.startsWith("AuftragDatum")) {
                    rv.add(c1);
                    c1.add(new Label("label", new ResourceModel("label." + tblName, tblName)));
                    c1.add(txt = new TxtFilter("dummyItem", tblName));
                }
                list.add(txt);
            }
            add(rv);

            final ViewListDefekte viewListDefekte;
            add(viewListDefekte = new ViewListDefekte("viewList", model));
            add(new AjaxButton("start-search", this) {
                
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    Util.parseCurrency(list);
                    Util.checkBisDateNotAfterNow(auftragDatumBis);
                    target.add(viewListDefekte);
                    new ChangeEvent(this, target, null, Const.ACTION_STAT_SUCHESTARTED).fire();
                }
                protected void onError(AjaxRequestTarget target, Form<?> form) {}
            });

        }
    }
}
