// $Log: AEPDataProvider.java,v $
// Revision 1.2  2014/05/30 12:22:11  tw
// Pflegemassnahmen.
//
// Revision 1.1  2014/05/13 01:19:02  tw
// Dataprovider Cache - Optimierungen.
//
// Revision 1.1  2014/05/08 21:01:15  tw
// Testblasen.
//
//

package de.decodetron;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;

/**
 * Dieser Dataprovider hat einen Cache, damit die Grössenabfrage size() nur einmal durchlaufen wird.
 * 
 * @author Thomas Winter
 * @since 08.05.2014
 */
public abstract class AEPDataProvider extends SortableDataProvider{

    // /////////////////////////////////////////////////////////////////////////
    // ITEM COUNT CACHE
    // https://issues.apache.org/jira/browse/WICKET-1766
    // /////////////////////////////////////////////////////////////////////////

    private long start = 0L;
    private transient long cachedItemCount = -1;

    /**
     * Der gecachte Wert.
     */
    public long size() {
        start = System.currentTimeMillis();
        if (isItemCountCached()) {
            return getCachedItemCount();
        }
        long cnt = aepSize();
        setCachedItemCount(cnt);
        return cnt;
    }

    /**
     * Liefert einen gecachten Wert.
     * 
     * @return long
     */
    public abstract long aepSize();

    @Override
    public void detach() {
        clearCachedItemCount();
        super.detach();
    }

    private void clearCachedItemCount() {
        cachedItemCount = -1;
    }

    private void setCachedItemCount(long itemCount) {
        cachedItemCount = itemCount;
    }

    public long getCachedItemCount() {
        if (cachedItemCount < 0) {
            throw new IllegalStateException("getItemCountCache() called when cache was not set");
        }
        return cachedItemCount;
    }

    public boolean isItemCountCached() {
        return cachedItemCount >= 0;
    }
}
