// $Log: DataViewWrapper.java,v $
// Revision 1.3  2016/02/05 15:47:51  tw
// CR 3956: Interner Umbau: Events separiert.
//
// Revision 1.2  2016/02/05 15:24:32  tw
// CR 3956: Interner Umbau: Events separiert.
//
// Revision 1.1  2016/01/31 17:01:04  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
//

package de.decodetron.tab.recherche.panel;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;

import de.decodetron.AEPModel;
import de.decodetron.bo.Fundstelle;
import de.decodetron.tab.recherche.event.IRecOnBelegartSelektiert;
import de.decodetron.tab.recherche.event.IRecOnFundstelleClickEvent;
import de.decodetron.tab.recherche.event.IRecOnSearchEvent;
import de.decodetron.tab.recherche.event.RecOnBelegartSelektiert;
import de.decodetron.tab.recherche.event.RecOnFundstelleClickEvent;
import de.decodetron.tab.recherche.event.RecOnSearchEvent;

/**
 * Gemeinsamer Wrapper für alle Belegtypen für die Fundstellenansicht.
 * 
 * @author Thomas Winter
 * @since 26.01.2016
 */
public class DataViewWrapper extends WebMarkupContainer implements IRecOnSearchEvent, IRecOnBelegartSelektiert,
        IRecOnFundstelleClickEvent {

    private Panel parentComponent;
    private DataView<Fundstelle> dataView;

    public DataViewWrapper(String id, IModel<AEPModel> model, DataView<Fundstelle> viewPort, Panel pc) {
        super(id, model);
        parentComponent = pc;
        add(dataView = viewPort);
        setOutputMarkupId(true);
    }

    /**
     * Muss für den Paging-Navigator nach draussen gereicht werden.
     * 
     * @return DataView<Fundstelle>
     */
    public DataView<Fundstelle> getView() {
        return dataView;
    }

    @Override
    public void onSearchEvent(RecOnSearchEvent ce) {
        ce.update(DataViewWrapper.this);
    }

    @Override
    public void onBelegartSelektiert(RecOnBelegartSelektiert b) {
        b.update(parentComponent);
    }

    @Override
    public void onFundstelleClick(RecOnFundstelleClickEvent ce) {
        ce.update(DataViewWrapper.this);
    }

}
