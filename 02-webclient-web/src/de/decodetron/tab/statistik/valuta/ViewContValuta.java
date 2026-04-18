// $Log: ViewContValuta.java,v $
// Revision 1.7  2020/03/09 22:33:41  tw
// Version 1.32-H
// -----------------------------
// CR: Umstellung der Statements um Einschraenkung per Datum vornehmen zu koennen.
// BUGFIX: Datums-Suche AEP-Plus (CLUBBESTAND) / Valuta korrigiert.
//
// Revision 1.6  2020/02/26 19:24:13  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.5  2015/02/27 16:39:41  tw
// Skriptumbau: Bugfix f. Statistik.
//
// Revision 1.4  2014/02/07 02:07:06  tw
// Restzeitaktualisierung bei Aktivitaeten implementiert.
//
// Revision 1.3  2013/12/15 22:28:13  tw
// Einbau: Fixed Tableheader.
//
// Revision 1.2  2013/12/09 14:39:20  tw
// Von-Bis Suchfeld, Statistik.
//
// Revision 1.1  2013/11/29 12:32:38  tw
// Neue Liste: Valuta.
//
//

package de.decodetron.tab.statistik.valuta;

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
 * @since 29.11.2013
 */
public class ViewContValuta extends Panel {
    
    public static String MODULNAME = "valuta"; // Fix in der DB (section) vergeben!
    
    public ViewContValuta(String id, IModel<?> model) {
        super(id, model);
        add(new ViewFilter("viewFilter", model));
    }
    
    public class ViewFilter extends Form {

        public ViewFilter(String id, IModel model) {

            super(id, model);

            clearInput();
            StatistikDAOI statistikDao = AEPApplication.get().getDBValutaFilter();
            DataRecord tblHeader = statistikDao.getColumNamesAsDataRecord(Const.TABLENAME_VALUTA);

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

                if (!tblName.startsWith("Tagesdatum")) {
                    rv.add(c1);
                    c1.add(new Label("label", new ResourceModel("label." + tblName, tblName)));
                    c1.add(txt = new TxtFilter("dummyItem", tblName));
                }
                list.add(txt);
            }
            add(rv);  
            
            
            final ViewListValuta viewListValuta;
            add(viewListValuta = new ViewListValuta("viewList", model));

            add(new AjaxButton("start-search", this) {
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    Util.parseCurrency(list);
                    target.add(viewListValuta);                    
                    new ChangeEvent(this, target, null, Const.ACTION_STAT_SUCHESTARTED).fire();
                }

                protected void onError(AjaxRequestTarget target, Form<?> form) {}
            });

        }
    }
}
