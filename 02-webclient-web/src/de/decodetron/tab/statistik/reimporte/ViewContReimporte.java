// $Log: ViewContReimporte.java,v $
// Revision 1.13  2020/02/26 19:24:12  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.12  2015/02/27 16:39:40  tw
// Skriptumbau: Bugfix f. Statistik.
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
// Revision 1.6  2013/11/25 15:33:52  tw
// Reimporte: limitierter Datenausgabe, DB-seitige Tabellensortierung.
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
// Revision 1.2  2013/11/15 09:27:52  tw
// Reimporte u. Chargen ans Berechtigungssystem angeflanscht.
//
// Revision 1.1  2013/11/10 20:26:10  tw
// Statistik: Reimporte
//
//

package de.decodetron.tab.statistik.reimporte;

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
 * @since 10.11.2013
 */
public class ViewContReimporte extends Panel {

    public static String MODULNAME = "reimp";  // Fix in der DB vergeben!
    
    public ViewContReimporte(String id, IModel<?> model) {
        super(id, model);
        add(new ViewFilter("viewFilter", model));
    }

    public class ViewFilter extends Form {

        public ViewFilter(String id, IModel model) {

            super(id, model);

            clearInput();
            StatistikDAOI statistikDao = AEPApplication.get().getDBReimporteFilter();
            DataRecord tblHeader = statistikDao.getColumNamesAsDataRecord(Const.TABLENAME_REIMPORTE);

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

            final ViewListReimporte viewListReimporte;
            add(viewListReimporte = new ViewListReimporte("viewList", model));

            add(new AjaxButton("start-search", this) {
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    Util.parseCurrency(list);
                    target.add(viewListReimporte);                    
                    new ChangeEvent(this, target, null, Const.ACTION_STAT_SUCHESTARTED).fire();
                }
                protected void onError(AjaxRequestTarget target, Form<?> form) {}
            });

        }
    }
}
