// $Log: ListSortable.java,v $
// Revision 1.7  2014/02/14 17:03:24  tw
// Implementierung: Passwort zuruecksetzen.
//
// Revision 1.6  2014/02/05 13:47:58  tw
// Anpassung an neue DB-Struktur
//
// Revision 1.5  2013/12/05 15:45:39  tw
// .csv - Listen fuer alle Listen
//
// Revision 1.4  2013/12/02 21:54:12  tw
// Spalten: KundenNr, LieferscheinNr werden fuer alle Lieferanten in allen Listen gefiltert.
//
// Revision 1.3  2013/12/02 17:33:58  tw
// Vorbereitungen f�r gezieltes Spaltenfiltern.
//
// Revision 1.2  2013/11/27 13:45:19  tw
// TODO: https://team.decodetron.de:10443/index.php?c=task&a=view_task&id=3929
// Bugfix: https://team.decodetron.de:10443/index.php?c=task&a=view_task&id=3933 1a.
//
// Revision 1.1  2013/11/09 19:01:12  tw
// Defektenliste, Zusammenfassungsarbeiten, Nullponter-Exception Sortierung behoben.
//
//

package de.decodetron.tab.statistik;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.Model;

import de.decodetron.Const;
import de.decodetron.bo.DataRecord;
import de.decodetron.security.LoginSession;

/**
 * Sortierbare Tabellenansicht die für Lieferanten bestimmte Tabellenspalten ausblendet.
 * 
 * @author Thomas Winter
 * @since 09.11.2013
 */
public class ListSortable extends DataView {

    private DataRecord tblHeader;

    public ListSortable(String id, final IDataProvider<DataRecord> dp, DataRecord dr) {
        super(id, dp);
        tblHeader = dr;
        setItemsPerPage(Const.ITEMS_PER_PAGE);
    }

    @Override
    protected void populateItem(final Item item) {
        DataRecord u = (DataRecord) item.getModelObject();

        int colCnt = 0;
        while (colCnt < tblHeader.getSize()) {

            String colName = tblHeader.getColItem(colCnt);
            String colCont = u.getColItem(colCnt);

            // item.add(new Label(colName, colCont));
            item.add(new ColContent(colName, colCont));
            colCnt++;
        }

        item.add(AttributeModifier.replace("class", new Model<String>() {
            public String getObject() {
                return (item.getIndex() % 2 == 1) ? "even" : "odd";
            }
        }));
    }

    private class ColContent extends Label {

        String colName = "";

        public ColContent(String id, String label) {

            super(id, label);
            colName = id;

            if (LoginSession.get().getUser().getIsSuperAdmin()) {
                return;
            }
            
            if (LoginSession.get().getUser().getIsLieferant()) {

                if ("LieferantNr".equals(colName)
                        && "LieferantNr".equals(LoginSession.get().getCurrentSection().getFilteridentifier())) {
                    setVisible(false);
                }

                if ("KundenNr".equals(colName) && "KundenNr".equals(LoginSession.get().getCurrentSection().getFilteridentifier())) {
                    setVisible(false);
                }
            }
        }
    }
}
