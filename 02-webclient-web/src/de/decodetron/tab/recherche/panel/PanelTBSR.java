// $Log: PanelTBSR.java,v $
// Revision 1.2  2020/03/09 22:33:40  tw
// Version 1.32-H
// -----------------------------
// CR: Umstellung der Statements um Einschraenkung per Datum vornehmen zu koennen.
// BUGFIX: Datums-Suche AEP-Plus (CLUBBESTAND) / Valuta korrigiert.
//
// Revision 1.1  2017/07/04 01:04:15  tw
// Panel genauer spezifiziert.
//
// Revision 1.5  2017/06/23 17:39:50  tw
// Mobilmachung der Headrevision.
//
// Revision 1.4  2017/06/23 12:43:43  tw
// Mobilmachung der Headrevision.
//
// Revision 1.3  2017/06/21 19:44:17  tw
// Mobilmachung der Headrevision.
//
// Revision 1.2  2016/01/31 17:00:15  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.1  2016/01/19 23:16:10  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
//

package de.decodetron.tab.recherche.panel;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPModel;
import de.decodetron.Const;
import de.decodetron.bo.Fundstelle;
import de.decodetron.data.HighlitableDataItemSortable;
import de.decodetron.tab.recherche.BootstrapPagingNavigator;
import de.decodetron.tab.recherche.FundstellenDataProvider;
import de.decodetron.tab.recherche.LblTblHeaderBelegart;
import de.decodetron.tab.statistik.TxtFilter;

/**
 * Panel für die Belgtypen: TB,SR [TB=LS,NB,GS]
 * 
 * @author Thomas Winter
 * @since 14.01.2016
 */
public class PanelTBSR extends BasePanelRecherche {

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        // Funzt hier nicht
        //response.render(OnDomReadyHeaderItem.forScript("$.fn.hideViewBusy();"));
    }
    
    public PanelTBSR(String id, IModel<AEPModel> model) {

        super(id, model);

        TxtFilter txtKdNr = new TxtFilter("txt-kdnr", "");
        TxtFilter txtLfsNr = new TxtFilter("txt-lfsnr", "");

        // //////////////////////////////////////////////////////
        // /// Suchrelevante Textfelder der Komponente hinzufügen.
        // /
        add(txtKdNr);
        add(txtLfsNr);

        // //////////////////////////////////////////////////////
        // /// Suchrelevante Textfelder in eine Liste legen.
        // /
        getModelRm().addSuchItem(txtKdNr);
        getModelRm().addSuchItem(txtLfsNr);

        // //////////////////////////////////////////////////////
        // /// Die Suche liegt in der Basisklasse, Markup wird jedoch hier bedient :-(
        // //////////////////////////////////////////////////////

        add(new LblTblHeaderBelegart("labelcol4")); // Kopf
        DataViewWrapper wmc = new DataViewWrapper("wmcFsViewAjaxContainer", (IModel<AEPModel>) getDefaultModel(),
                new FundstellenViewSortable("fundstellen", (ValueMap) getDefaultModelObject()), this);
        add(new BootstrapPagingNavigator("navigator", wmc.getView()));
        add(wmc);

        // ValueMap vm = (ValueMap) getDefaultModelObject();
        // String check = (String)vm.get("txt-kdnr");
        // vm.put("txt-kdnr", "123");

    }

    private class FundstellenViewSortable extends DataView<Fundstelle> {

        public FundstellenViewSortable(String id, ValueMap v) {
            super(id, new FundstellenDataProvider(v));
            setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
            setItemsPerPage(Const.ITEMS_PER_PAGE);
        }

        @Override
        protected void populateItem(final Item<Fundstelle> item) {
            final Fundstelle u = item.getModelObject();
            // item.add(new ActionPanel("actions", (IModel)item.getModel()));
            item.add(new Label("kdnr", u.getKundennummer()));
            item.add(new Label("lfsr", u.getBelegnummer()));
            // item.add(new Label("datum", Util.getCorrectDate(u)));
            item.add(new Label("datum", u.getDatum()));
            item.add(new LblSuperAdmin("belegart", u.getBelegart()));
            item.add(new Label("seitenanz", u.getSeitenzahl()));
            item.add(AttributeModifier.replace("class", new Model<String>() {
                public String getObject() {
                    return (item.getIndex() % 2 == 1) ? "" : "dk";
                }
            }));
        }

        // @Override
        protected Item<Fundstelle> newItem(String id, int index, final IModel<Fundstelle> model) {
            return new HighlitableDataItemSortable<Fundstelle>(id, index, model, getModelRm());
        }
    }

}
