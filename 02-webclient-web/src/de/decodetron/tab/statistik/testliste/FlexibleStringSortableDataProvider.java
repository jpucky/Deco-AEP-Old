// $Log: FlexibleStringSortableDataProvider.java,v $
// Revision 1.3  2013/11/07 21:58:52  tw
// aufraumen 1. welle.
//
// Revision 1.2  2013/11/07 03:59:29  tw
// Bugfix listiterator.
//
// Revision 1.1  2013/11/06 23:38:18  tw
// Generischer Sortierer.
//
//

package de.decodetron.tab.statistik.testliste;

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

import de.decodetron.bo.DataRecord;

/**
 * Generischer Sortierer, der nur Strings kennt und nach Spaltennummern sortiert.
 * 
 * @author Thomas Winter
 * @since 06.11.2013
 */
public class FlexibleStringSortableDataProvider extends SortableDataProvider {

    private List<DataRecord> list;

    public FlexibleStringSortableDataProvider(List<DataRecord> l) {
        list = l;
        setSort("1", SortOrder.DESCENDING);
    }

    @Override
    public Iterator<DataRecord> iterator(long first, long count) {
        final SortParam<String> sort = getSort();
        sortColNumber(sort, Integer.valueOf(sort.getProperty()));
        List<DataRecord> subList = list.subList((int) first, (int) first + (int) count);
        return subList.iterator();
    }

    private void sortColNumber(final SortParam<String> sort, final int colNr) {
        Collections.sort(list, new Comparator<DataRecord>() {
            public int compare(DataRecord arg0, DataRecord arg1) {
                return (sort.isAscending() ? arg0 : arg1).getColItem(colNr).compareTo(
                    (sort.isAscending() ? arg1 : arg0).getColItem(colNr));
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
