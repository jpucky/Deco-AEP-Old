// $Log: BasePanelRecherche.java,v $
// Revision 1.4  2017/06/21 19:44:17  tw
// Mobilmachung der Headrevision.
//
// Revision 1.3  2016/02/05 15:24:32  tw
// CR 3956: Interner Umbau: Events separiert.
//
// Revision 1.2  2016/01/31 17:00:15  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.1  2016/01/19 23:16:09  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
//

package de.decodetron.tab.recherche.panel;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.decodetron.AEPModel;
import de.decodetron.tab.recherche.LblShow;
import de.decodetron.tab.recherche.LblTotal;
import de.decodetron.tab.recherche.RechercheModel;
import de.decodetron.tab.recherche.event.RecOnSearchEvent;

/**
 * @author Thomas Winter
 * @since 14.01.2016
 */
public class BasePanelRecherche extends Panel {

    private Component lblShow;
    private Component lblTotal;

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(OnDomReadyHeaderItem.forScript("$.fn.initFixedTableHeaderRec()"));
        response.render(OnDomReadyHeaderItem.forScript("$.fn.initDateSearchRecherche()"));
    }

    public BasePanelRecherche(String id, final IModel<AEPModel> model) {
        super(id, model);
        setOutputMarkupId(true);

        add(new AjaxButton("start-search") {
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                // log.debug("belegart: " + belegart.getModelObject());
                target.appendJavaScript("$.fn.switchEmptySearch();");
                // // target.appendJavaScript("$.fn.initDateSearchBtn();");

                // new ChangeEvent(BasePanelRecherche.this, target, null,
                // Const.KEYSTARTSEARCH).fire();
                new RecOnSearchEvent(BasePanelRecherche.this, target, null).fire();

                target.add(lblShow);
                target.add(lblTotal);
                // target.add(labelC);
            }

            protected void onError(AjaxRequestTarget target, Form<?> form) {}
        });

        add(new DelteSearch("deletesearch", model.getObject().getRechercheModel()));
        add(lblShow = new LblShow("show", getDefaultModel())); // Tabelle Fuss
        add(lblTotal = new LblTotal("totalegal", getDefaultModel())); // Tabelle Fuss
    }

    public RechercheModel getModelRm() {
        return ((AEPModel) BasePanelRecherche.this.getDefaultModelObject()).getRechercheModel();
    }
}
