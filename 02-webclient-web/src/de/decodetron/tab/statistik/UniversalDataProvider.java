// $Log: UniversalDataProvider.java,v $
// Revision 1.3  2013/11/21 17:40:46  tw
// Vorbereitung: neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.2  2013/11/09 19:01:12  tw
// Defektenliste, Zusammenfassungsarbeiten, Nullponter-Exception Sortierung behoben.
//
// Revision 1.1  2013/11/07 22:00:08  tw
// Clubliste geradegezogen.
//
//

package de.decodetron.tab.statistik;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.Const;
import de.decodetron.bo.DataRecord;

/**
 * Generischer Sortierer, der nur Strings kennt und nach Spaltennummern sortiert.
 * 
 * @author Thomas Winter
 * @since 07.11.2013
 */
public class UniversalDataProvider extends SortableDataProvider {

    private IModel model;
    private List<DataRecord> list;

    public UniversalDataProvider(List<DataRecord> l, IModel m) {
        list = l;
        model = m;
        setSort("1", SortOrder.DESCENDING);
    }

    @Override
    public Iterator<DataRecord> iterator(long first, long count) {
        final SortParam<String> sort = getSort();
        sortColNumber(sort, Integer.valueOf(sort.getProperty()));
        List<DataRecord> subList = list.subList((int) first, (int) first + (int) count);
        if (model != null) {
            ValueMap map = (ValueMap) model.getObject();
            //map.put(Const.KEY_LIST_CURRENTSIZE, subList.size());
            map.put(Const.KEY_LIST_ISASCENDING, sort.isAscending());
            map.put(Const.KEY_LIST_COLNR2SORT, Integer.valueOf(sort.getProperty()));
            map.put(Const.KEY_LIST_OFFSET, first);
            model.setObject(map);
        }
        return subList.iterator();
    }

    private void sortColNumber(final SortParam<String> sort, final int colNr) {
        Collections.sort(list, new Comparator<DataRecord>() {
            public int compare(DataRecord arg0, DataRecord arg1) {
                String a0 = arg0.getColItem(colNr) != null ? arg0.getColItem(colNr) : "";
                String a1 = arg1.getColItem(colNr) != null ? arg1.getColItem(colNr) : "";
                return sort.isAscending() ? a0.compareTo(a1) : a1.compareTo(a0);
            }
        });
    }

    @Override
    public long size() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    @SuppressWarnings({ "unchecked" })
    public IModel<Serializable> model(Object object) {
        return new Model<Serializable>((Serializable) object);
    }
}
