// $Log: FundstellenTxtComponents.java,v $
// Revision 1.4  2017/07/04 01:07:32  tw
// Mobilmachung der Headrevision. Beseitigung des PDF-Speicherlecks.
//
// Revision 1.3  2017/06/21 19:44:17  tw
// Mobilmachung der Headrevision.
//
// Revision 1.2  2016/02/05 15:24:32  tw
// CR 3956: Interner Umbau: Events separiert.
//
// Revision 1.1  2016/01/31 17:01:04  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.1  2016/01/19 23:16:09  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
//

package de.decodetron.tab.recherche.panel;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPModel;
import de.decodetron.Const;
import de.decodetron.tab.recherche.RechercheModel;
import de.decodetron.tab.recherche.event.IRecOnBelegartSelektiert;
import de.decodetron.tab.recherche.event.RecOnBelegartSelektiert;

/**
 * @author Thomas Winter
 * @since 13.01.2016
 */
public class FundstellenTxtComponents extends Panel implements IRecOnBelegartSelektiert {

    private Panel panel;

    public FundstellenTxtComponents(String id, IModel<AEPModel> model) {
        super(id, model);
        setOutputMarkupId(true);

        ValueMap vm = (ValueMap) getDefaultModel().getObject();
        List<String> belartL = (List<String>) vm.get(Const.KEY_SELECTED_BELEGART);

        panelSwitch(belartL);
    }
    
    private void panelSwitch(Collection<String> belegartSelected) {

        // Initialer Aufruf
        if (panel == null) {
            add(panel = new PanelTBSR("belegSuchpanel", (IModel<AEPModel>) getDefaultModel()));
            // add(panel = new InheritTest("belegSuchpanel", (IModel<AEPModel>) getDefaultModel()));
        } else {
            if (CollectionUtils.containsAny(belegartSelected, Const.KEY_PANEL_TB)) {
                // LS,NB,GS,SR
                panel = (Panel) panel.replaceWith(new PanelTBSR("belegSuchpanel", (IModel<AEPModel>) getDefaultModel()));
            } else if (CollectionUtils.containsAny(belegartSelected, Const.KEY_PANEL_EK)) {
                // EK
                panel = (Panel) panel.replaceWith(new PanelEK("belegSuchpanel", (IModel<AEPModel>) getDefaultModel()));
                // panel = (Panel) panel.replaceWith(new InheritTest("belegSuchpanel",
                // (IModel<AEPModel>) getDefaultModel()));
            } else if (CollectionUtils.containsAny(belegartSelected, Const.KEY_PANEL_SCAN)) {
                // SCLF,SCRG,SCSD
                panel = (Panel) panel.replaceWith(new PanelSC("belegSuchpanel", (IModel<AEPModel>) getDefaultModel()));
            } else if (CollectionUtils.containsAny(belegartSelected, Const.KEY_PANEL_SCSNRET)) {
                // SCRT
                panel = (Panel) panel
                        .replaceWith(new PanelSCRT("belegSuchpanel", (IModel<AEPModel>) getDefaultModel()));
            } else {
                // Default: LS,NB,GS,SR
                panel = (Panel) panel.replaceWith(new PanelTBSR("belegSuchpanel", (IModel<AEPModel>) getDefaultModel()));
            }

        }
    }

    @Override
    public void onBelegartSelektiert(RecOnBelegartSelektiert b) {
        ValueMap vm = (ValueMap) getDefaultModel().getObject();
        panelSwitch((List<String>) vm.get(Const.KEY_SELECTED_BELEGART));
    }

}
