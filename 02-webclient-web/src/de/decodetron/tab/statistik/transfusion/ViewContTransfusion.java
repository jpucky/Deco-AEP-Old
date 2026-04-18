// $Log: ViewContTransfusion.java,v $
// Revision 1.7  2020/02/26 19:24:13  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.6  2019/07/23 20:19:42  tw
// CR: Statistik, Transfusion Bis-Datums-Check.
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
// Revision 1.1  2013/11/28 14:35:02  tw
// Neue Liste: Transfusion.
//
//

package de.decodetron.tab.statistik.transfusion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 * @since 28.11.2013
 */
public class ViewContTransfusion extends Panel {

    public static String MODULNAME = "tfg"; // Fix in der DB vergeben!
    
    public ViewContTransfusion(String id, IModel<?> model) {
        super(id, model);
        add(new ViewFilter("viewFilter", model));
    }
    
    public class ViewFilter extends Form {

        public ViewFilter(String id, final IModel model) {

            super(id, model);

            clearInput();
            StatistikDAOI statistikDao = AEPApplication.get().getDBTransfusionFilter();
            DataRecord tblHeader = statistikDao.getColumNamesAsDataRecord(Const.TABLENAME_TRANSFUSION);

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

            final ViewListTransfusion viewListTransfusion;
            add(viewListTransfusion = new ViewListTransfusion("viewList", model));

            add(new AjaxButton("start-search", this) {
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    Util.parseCurrency(list);
                    target.add(viewListTransfusion);                    
                    new ChangeEvent(this, target, null, Const.ACTION_STAT_SUCHESTARTED).fire();
                }

                protected void onError(AjaxRequestTarget target, Form<?> form) {}
            });

        }
    }
}
