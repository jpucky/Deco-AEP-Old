// $Log: ClubBestandPanel.java,v $
// Revision 1.6  2020/02/26 19:24:12  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.5  2014/01/29 09:49:30  tw
// Vorbereitung: Belegart: EK / Benutzerverwaltung.
//
// Revision 1.4  2013/12/02 12:33:54  tw
// Neue Schnittstelle um unnoetige Spalten auszublenden.
//
// Revision 1.3  2013/11/15 21:48:03  tw
// Tabellenspalten werden per seperater DB-Funktion abgerufen.
//
// Revision 1.2  2013/11/08 12:50:25  tw
// Club-Abverkauf implementiert.
//
// Revision 1.1  2013/11/07 21:58:52  tw
// aufraumen 1. welle.
//
// Revision 1.1  2013/11/05 23:04:05  tw
// Experimente, um CVS-Listen generischer Lesen zu koennen.
//
// Revision 1.2  2013/11/05 12:37:21  tw
// Spaltensortierung f. Clubverkauf implementiert.
//
// Revision 1.1  2013/11/05 03:38:09  tw
// Paginierung begonnen.
//
//

package de.decodetron.tab.statistik.testliste;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.bo.ClubBestand;
import de.decodetron.bo.DataRecord;

/**
 * @author Thomas Winter
 * @since 04.11.2013
 */
public class ClubBestandPanel extends Panel {

    private DataRecord tblHeader;
    
    @SuppressWarnings("unchecked")
    public ClubBestandPanel(String id, IModel<?> model) {
        
        super(id, model);
        setOutputMarkupId(true);
        //initClubVerkaufModel();

        final DataView<DataRecord> dataView;
        ValueMap map = (ValueMap) getDefaultModelObject();
        // List<ClubBestand> cblist = (List<ClubBestand>) map.get(Const.KEY_LIST_CLUBVERKAUF);
        //List<DataRecord> cblist = (List<DataRecord>) map.get(Const.KEY_LISTEN_DATA);
        //tblHeader = (DataRecord) cblist.remove(0);
        tblHeader = new DataRecord();
        tblHeader.addColItem("Datum");
        tblHeader.addColItem("PosTyp");

        
        DataRecord dataRec1 = new DataRecord();
        dataRec1.addColItem("14.05.1968");
        dataRec1.addColItem("Blah");
        DataRecord dataRec2 = new DataRecord();
        dataRec2.addColItem("15.05.1968");
        dataRec2.addColItem("Blub");        
        List<DataRecord> ltest = new ArrayList<DataRecord>();
        ltest.add(dataRec1);
        ltest.add(dataRec2);
        // //////////////////////////////////////////////////////////////
        // /// Tabellen nicht sortierbar
        // /
        // add(new ListClubVerkauf("clubverkauf", l));

        // //////////////////////////////////////////////////////////////
        // /// Tabellen sortierbar
        // /
        // ClubBestandSortableDataProvider dp = new ClubBestandSortableDataProvider(cblist);
        FlexibleStringSortableDataProvider dp = new FlexibleStringSortableDataProvider(ltest);
        add(dataView = new ListClubBestandSortable("clubverkauf", dp));
        add(new PagingNavigator("navigator", dataView));

        int colNr = 0;
        // -1 wegen id-row!
        while (colNr < tblHeader.getSize()) {
            MyOrderByBorder obo;
            add(obo = new MyOrderByBorder(String.valueOf(colNr), String.valueOf(colNr), dp) {
                protected void onSortChanged() {
                    dataView.setCurrentPage(dataView.getCurrentPage());
                }
            }); 
            final int c = colNr;
            obo.add(AttributeModifier.append("class", new Model<String>(){
                public String getObject() {
                    return (c % 2 == 1) ? "invisible" : "odd";
                } 
            }));
            colNr++;
        }

        // add(new OrderByBorder("0", ClubBestandSortableDataProvider.SORT_DATUM, dp) {
        // protected void onSortChanged() {
        // dataView.setCurrentPage(dataView.getCurrentPage());
        // }
        // });
        // add(new OrderByBorder("1", ClubBestandSortableDataProvider.SORT_POSTYP, dp) {
        // protected void onSortChanged() {
        // dataView.setCurrentPage(dataView.getCurrentPage());
        // }
        // });
    }

    private class MyOrderByBorder extends OrderByBorder{

        public MyOrderByBorder(String id, Object property, ISortStateLocator stateLocator) {
            super(id, property, stateLocator);
            
//            add(AttributeModifier.append("class", new Model<String>(){
//                public String getObject() {
//                    return "invisible";
//                } 
//            }));
        }
        
    }

    // private DataRecord tblHeader;
    private class ListClubBestandSortable extends DataView<DataRecord> {

        public ListClubBestandSortable(String id, final IDataProvider<DataRecord> dp) {
            super(id, dp);
            setItemsPerPage(2);
        }

        @Override
        protected void populateItem(final Item<DataRecord> item) {
            DataRecord u = item.getModelObject();

            int colCnt = 0;
            while (colCnt < tblHeader.getSize()) {
                Label lbl = new Label(String.valueOf(colCnt), u.getColItem(colCnt));
                final int c = colCnt;
                lbl.add(AttributeModifier.append("class", new Model<String>(){
                    public String getObject() {
                        return (c % 2 == 1) ? "invisible" : "odd";
                    } 
                }));
                item.add(lbl);
                colCnt++;
            }

            item.add(AttributeModifier.replace("class", new Model<String>() {
                public String getObject() {
                    return (item.getIndex() % 2 == 1) ? "even" : "odd";
                }
            }));
        }

    }

    /**
     * Nicht sortierbare Liste
     */
    private class ListClubVerkauf extends ListView<ClubBestand> {

        public ListClubVerkauf(String id, final List<ClubBestand> l) {
            super(id, l);
        }

        protected void populateItem(final ListItem<ClubBestand> item) {
            ClubBestand u = item.getModelObject();
            item.add(new Label("datum", u.getDatum()));
            item.add(new Label("postyp", u.getPosTyp()));
            item.add(new Label("lfrnr", u.getLieferantNr()));
            item.add(new Label("lfrnm", u.getLieferantName()));
            item.add(new Label("pzn", u.getPzn()));
            item.add(new Label("artikelbez", u.getArtikelBezeichnung()));
            item.add(new Label("clubkz", u.getClubKz()));
            item.add(new Label("bestand", u.getBestand()));
            item.add(AttributeModifier.replace("class", new Model<String>() {
                public String getObject() {
                    return (item.getIndex() % 2 == 1) ? "even" : "odd";
                }
            }));
        }
    }
}
