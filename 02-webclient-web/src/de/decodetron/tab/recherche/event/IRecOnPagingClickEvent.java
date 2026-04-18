// $Log: IRecOnPagingClickEvent.java,v $
// Revision 1.2  2016/02/05 15:24:32  tw
// CR 3956: Interner Umbau: Events separiert.
//
// Revision 1.1  2016/01/31 17:01:04  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
//

package de.decodetron.tab.recherche.event;

/**
 * @author Thomas Winter
 * @since 29.01.2016
 */
public interface IRecOnPagingClickEvent {
    public void onPagingClickEvent(RecOnPagingClickEvent ce);
}
