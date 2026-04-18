// $Log: ViewContHochpreis.java,v $
// Revision 1.12  2020/02/26 19:24:12  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.11  2015/02/27 16:39:40  tw
// Skriptumbau: Bugfix f. Statistik.
//
// Revision 1.10  2014/02/07 02:07:06  tw
// Restzeitaktualisierung bei Aktivitaeten implementiert.
//
// Revision 1.9  2013/12/15 22:28:13  tw
// Einbau: Fixed Tableheader.
//
// Revision 1.8  2013/12/09 14:09:19  tw
// Von-Bis Suchfeld, Statistik.
//
// Revision 1.7  2013/11/28 13:42:28  tw
// Anbindung Liste:Tierarznei.
//
// Revision 1.6  2013/11/27 21:45:55  tw
// Bugfix Waehrungsumwanlder, Hochpreiser.
//
// Revision 1.5  2013/11/27 21:42:38  tw
// Bugfix Waehrungsumwanlder, Hochpreiser.
//
// Revision 1.4  2013/11/27 21:36:20  tw
// Bugfix Waehrungsumwanlder, Hochpreiser.
//
// Revision 1.3  2013/11/27 21:33:02  tw
// Bugfix Waehrungsumwanlder, Hochpreiser.
//
// Revision 1.2  2013/11/27 20:09:53  tw
// Implementierung: Hochpreiserliste.
//
// Revision 1.1  2013/11/27 13:45:19  tw
// TODO: https://team.decodetron.de:10443/index.php?c=task&a=view_task&id=3929
// Bugfix: https://team.decodetron.de:10443/index.php?c=task&a=view_task&id=3933 1a.
//
//

package de.decodetron.tab.statistik.hochpreiser;

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
 * @since 27.11.2013
 */
public class ViewContHochpreis extends Panel {

    public static String MODULNAME = "hochpr"; // Fix in der DB vergeben!

    public ViewContHochpreis(String id, IModel<?> model) {
        super(id, model);
        add(new ViewFilter("viewFilter", model));
    }

    public class ViewFilter extends Form {

        public ViewFilter(String id, IModel model) {

            super(id, model);

            clearInput();
            StatistikDAOI statistikDao = AEPApplication.get().getDBHochpreisFilter();
            DataRecord tblHeader = statistikDao.getColumNamesAsDataRecord(Const.TABLENAME_HOCHPREISER);

            int colNr = 0;
            ValueMap map = (ValueMap) model.getObject();
            map.put(Const.KEY_LIST_FILTERSUCHE, new ArrayList<TxtFilter>());
            final List<TxtFilter> list = (List<TxtFilter>) map.get(Const.KEY_LIST_FILTERSUCHE);

            // ///////////////////////////////////////////////////////////////////////////
            // /// Statische Extrawurst f. Datum- Von Bis.
            // /
            final TxtFilter auftragDatumVon;
            final TxtFilter auftragDatumBis;
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

            final ViewListHochpreis viewListHochpreis;
            add(viewListHochpreis = new ViewListHochpreis("viewList", model));

            add(new AjaxButton("start-search", this) {
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    Util.parseCurrency(list);
                    target.add(viewListHochpreis);
                    new ChangeEvent(this, target, null, Const.ACTION_STAT_SUCHESTARTED).fire();
                }

                protected void onError(AjaxRequestTarget target, Form<?> form) {}
            });

        }
    }
}
