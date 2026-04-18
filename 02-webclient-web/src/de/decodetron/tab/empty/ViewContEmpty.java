// $Log: ViewContEmpty.java,v $
// Revision 1.2  2020/02/26 19:24:10  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.1  2015/02/01 10:43:17  tw
// Umzug der Defaultanzeige.
//
// Revision 1.3  2015/01/25 14:34:35  tw
// Vorabentwurf f. Lueckenprotokoll implementiert.
//
// Revision 1.2  2013/12/08 17:32:19  tw
// Datepicker-Von, Statistik.
//
// Revision 1.1  2013/11/08 08:33:43  tw
// .
//
// Revision 1.1  2013/11/07 22:43:04  tw
// Hinweis Listentyp eingebaut.
//
//

package de.decodetron.tab.empty;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;


/** 
 * @author Thomas Winter
 * @since 07.11.2013
 */
public class ViewContEmpty extends Panel {

    public ViewContEmpty(String id, IModel<?> model) {
        super(id, model);
        setOutputMarkupId(true);
        add(new ViewEmpty("viewFilter", model));
    }

    public void renderHead(IHeaderResponse response) {
        response.render(OnDomReadyHeaderItem.forScript("$.fn.hideViewBusy();"));
    }
    
    public class ViewEmpty extends Form {

        public ViewEmpty(String id, IModel model) {
            super(id, model);
            // add(new Label("viewHint", Model.of("Bitte wählen Sie eine Liste aus.")));
            add(new ViewListEmpty("viewHint", model));
        }

    }
}
