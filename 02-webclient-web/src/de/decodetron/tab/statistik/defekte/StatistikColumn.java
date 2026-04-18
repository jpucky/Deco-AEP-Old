// $Log: StatistikColumn.java,v $
// Revision 1.1  2014/08/12 20:43:15  tw
// Umzug.
//
// Revision 1.2  2014/04/03 15:36:22  tw
// schoenheitskorrektur f. leere tabellen
//
// Revision 1.1  2014/03/27 15:36:23  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Tabellengestaltung.
//
//

package de.decodetron.tab.statistik.defekte;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.decodetron.bo.DataRecord;
import de.decodetron.tab.MyMultilineLabel;

/**
 * Soll mal eine allgemeingültige Spalte für alle Statistiken werden.
 * 
 * @author Thomas Winter
 * @since 27.03.2014
 */
public class StatistikColumn extends AbstractColumn<DataRecord, String> {

    private int cellIndex = 0;
    private String colName = "";

    public StatistikColumn(IModel<String> displayModel, String cn) {
        super(displayModel);
        colName = cn;
    }

    public StatistikColumn(IModel<String> displayModel, String sortProperty, String cn) {
        super(displayModel, sortProperty);
        colName = cn;
    }

    @Override
    public void populateItem(Item<ICellPopulator<DataRecord>> cellItem, String componentId, IModel<DataRecord> rowModel) {
        cellIndex = cellItem.getIndex();
        DataRecord dr = (DataRecord) rowModel.getObject();
        String colCont = cellIndex < dr.getSize() ? dr.getColItem(cellIndex) : "";
        cellItem.add(new Label(componentId, Model.of(colCont)));
    }

    @Override
    public Component getHeader(final String componentId) {
        return new MyMultilineLabel(componentId, getDisplayModel());
    }

    @Override
    public String getCssClass() {

        StringBuilder css = new StringBuilder();
        css.append("Preis".equals(colName) ? "currency " : "");
        css.append("Betrag".equals(colName) ? "currency " : "");
        css.append("RecId".equals(colName) ? "invisible " : "");
        //css.append(cellIndex == 0 ? "invisible" : "");
        return css.toString();
    }

}
