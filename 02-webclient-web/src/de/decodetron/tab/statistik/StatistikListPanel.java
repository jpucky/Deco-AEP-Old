// $Log: StatistikListPanel.java,v $
// Revision 1.4  2020/02/26 19:24:10  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.3  2015/02/27 16:39:40  tw
// Skriptumbau: Bugfix f. Statistik.
//
// Revision 1.2  2015/02/19 22:24:04  tw
// Haldenbearbeitung: Grossflaechiger Skriptumbau. Bugfix Statistik.
//
// Revision 1.1  2014/06/28 16:08:51  tw
// Benutzer bearbeiten finalisiert. Busy-Indicator f. Statistik u. Benutzer Bearbeiten.
//
//

package de.decodetron.tab.statistik;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.resource.JQueryPluginResourceReference;

import de.decodetron.AEPApplication;

/**
 * @author Thomas Winter
 * @since 28.06.2014
 */
public class StatistikListPanel extends Panel {

    public StatistikListPanel(String id, IModel<?> model) {
        super(id, model);
    }

    public StatistikListPanel(String id) {
        super(id);
    }
    
    public void renderHead(IHeaderResponse response) {

        // Datepicker
        AEPApplication.initDatePicker(response);

        response.render(OnDomReadyHeaderItem.forScript("$.fn.initSearchButtonSelector();"));
        response.render(OnDomReadyHeaderItem.forScript("$.fn.hideViewBusy();"));

        // //////////////////////////////////
        // /// Fixedtable Header
        // /
        response.render(CssHeaderItem.forReference(new CssResourceReference(TabStatistik.class,
                "../../../../css/tableDefaultTheme.css")));
        response.render(JavaScriptHeaderItem.forReference(new JQueryPluginResourceReference(TabStatistik.class,
                "../../../../js/jquery.fixedheadertable.js")));
        response.render(OnDomReadyHeaderItem.forScript("$.fn.initFixedTableHeader();"));
    }
}
