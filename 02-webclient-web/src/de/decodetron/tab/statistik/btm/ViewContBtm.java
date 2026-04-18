// $Log: ViewContBtm.java,v $
// Revision 1.25  2020/02/26 19:24:11  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.24  2015/02/27 16:39:40  tw
// Skriptumbau: Bugfix f. Statistik.
//
// Revision 1.23  2014/06/28 16:00:38  tw
// Benutzer bearbeiten finalisiert. Busy-Indicator f. Statistik u. Benutzer Bearbeiten.
//
// Revision 1.22  2014/02/07 02:07:05  tw
// Restzeitaktualisierung bei Aktivitaeten implementiert.
//
// Revision 1.21  2013/12/15 22:28:12  tw
// Einbau: Fixed Tableheader.
//
// Revision 1.20  2013/12/09 09:39:43  tw
// Neue Schnittstelle: Von-bis Datum Statistik
//
// Revision 1.19  2013/12/08 17:32:19  tw
// Datepicker-Von, Statistik.
//
// Revision 1.18  2013/12/02 12:33:53  tw
// Neue Schnittstelle um unnoetige Spalten auszublenden.
//
// Revision 1.17  2013/11/28 14:41:43  tw
// Bugfix Waehrungssuche.
//
// Revision 1.16  2013/11/25 12:22:15  tw
// Chargendoku: limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.15  2013/11/25 11:02:07  tw
// Limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.14  2013/11/22 00:49:20  tw
// Limitierter Datenausgabe: Anbindung Btm-Liste erster Wurf.
//
// Revision 1.13  2013/11/21 22:10:45  tw
// Limitierter Datenausgabe: Anbindung Btm-Liste erster Wurf.
//
// Revision 1.12  2013/11/21 17:40:46  tw
// Vorbereitung: neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.11  2013/11/18 10:45:55  tw
// Multifilter an die Statistik angebunden.
//
// Revision 1.10  2013/11/17 16:18:44  tw
// Recherche an den Multifilter angebunden. Modulumstellung auf KZ.
//
// Revision 1.9  2013/11/15 21:48:03  tw
// Tabellenspalten werden per seperater DB-Funktion abgerufen.
//
// Revision 1.8  2013/11/14 15:49:40  tw
// Filter - Schnittstellennormalisierung.
//
// Revision 1.7  2013/11/14 14:09:04  tw
// Schnittstellen Datenfilterung implementiert.
//
// Revision 1.6  2013/11/13 15:59:02  tw
// Vorbereitung: Datenfilterung.
//
// Revision 1.5  2013/11/13 01:31:34  tw
// Statistik - Listen Berechtigungskonzept, Bugfix.
//
// Revision 1.4  2013/11/13 01:17:39  tw
// Statistik - Listen ans Berechtigungskonzept angebunden.
//
// Revision 1.3  2013/11/09 19:01:12  tw
// Defektenliste, Zusammenfassungsarbeiten, Nullponter-Exception Sortierung behoben.
//
// Revision 1.2  2013/11/08 09:48:53  tw
// chargenliste implementiert.
//
// Revision 1.1  2013/11/08 00:27:02  tw
// BTM-Liste implementiert.
//
//

package de.decodetron.tab.statistik.btm;

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
 * @author Thomas Winter
 * @since 08.11.2013
 */
public class ViewContBtm extends Panel {

    public static String MODULNAME = "btm"; // Fix in der DB vergeben!

    public ViewContBtm(String id, IModel<?> model) {
        super(id, model);
        setOutputMarkupId(true);
        add(new ViewFilter("viewFilter", model));
    }

    public class ViewFilter extends Form {

        public ViewFilter(String id, IModel model) {

            super(id, model);

            clearInput();
            StatistikDAOI statistikDao = AEPApplication.get().getDBBtmFilter();
            DataRecord tblHeader = statistikDao.getColumNamesAsDataRecord(Const.TABLENAME_BTM);

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
            
            final ViewListBtm viewListBtm;
            add(viewListBtm = new ViewListBtm("viewList", model));

            add(new AjaxButton("start-search", this) {
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    Util.parseCurrency(list);
                    target.add(viewListBtm);
                    new ChangeEvent(this, target, null, Const.ACTION_STAT_SUCHESTARTED).fire();
                }
                protected void onError(AjaxRequestTarget target, Form<?> form) {}
            });

        }
    }
}
