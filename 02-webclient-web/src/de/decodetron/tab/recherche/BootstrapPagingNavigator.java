// $Log: BootstrapPagingNavigator.java,v $
// Revision 1.6  2016/02/05 15:24:32  tw
// CR 3956: Interner Umbau: Events separiert.
//
// Revision 1.5  2016/01/31 17:00:14  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.4  2016/01/19 23:15:06  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.3  2014/03/20 02:22:22  tw
// Recherche: Paginierung, Funktionstest. Event-Konstanten zusammengefasst.
//
// Revision 1.2  2014/03/18 14:05:53  tw
// Recherche: Paginierung, Styleanbindung, Funktionstest.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.1  2014/03/18 02:22:56  tw
// Recherche: Paginierung, Styleanbindung, Funktionstest.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
//

package de.decodetron.tab.recherche;

import java.io.Serializable;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigation;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigationIncrementLink;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigationLink;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.model.IModel;

import de.decodetron.tab.recherche.event.IRecOnSearchEvent;
import de.decodetron.tab.recherche.event.RecOnPagingClickEvent;
import de.decodetron.tab.recherche.event.RecOnSearchEvent;

/**
 * Funktioniert wenn die dazugehörige Listview auf einem MarkupContainer sitzt. Die Vorarbeit wurde
 * dankenswerter weise von dem Laden hier übernommen:
 * http://blog.comsysto.com/2013/11/07/wicket-6-paging-navigator-for-bootstrap-3/ <br>
 * <br>
 * 
 * Die Aufwand mit der Paging-Komponente wird betrieben um eine eigene [ul][li] Markupstrukur zu
 * generieren die sich leichter formatieren lässt.
 * 
 * @author Thomas Winter
 * @since 17.03.2014
 */
public class BootstrapPagingNavigator extends AjaxPagingNavigator implements IRecOnSearchEvent {

    public BootstrapPagingNavigator(String id, IPageable pageable) {
        super(id, pageable);
        setOutputMarkupId(true);
    }

    @Override
    protected void onAjaxEvent(AjaxRequestTarget target) {
        super.onAjaxEvent(target); // 1!
        new RecOnPagingClickEvent(BootstrapPagingNavigator.this, target, null).fire(); // 2!
    }

    @Override
    public void onSearchEvent(RecOnSearchEvent ce) {
        ce.update(BootstrapPagingNavigator.this);
    }

    // Link for: "1 | 2 | 3 | 4"
    @Override
    protected PagingNavigation newNavigation(String id, IPageable pageable, IPagingLabelProvider labelProvider) {

        AjaxPagingNavigation pn = new AjaxPagingNavigation(id, pageable) {

            @Override
            protected LoopItem newItem(int iteration) {
                LoopItem item = super.newItem(iteration);

                // add css for enable/disable link
                long pageIndex = getStartIndex() + iteration;
                PageLinkCssModel pm = new PageLinkCssModel(pageable, pageIndex, "active");
                item.add(new AttributeModifier("class", pm));
                return item;
            }
        };

        // pn.setViewSize(5);

        return pn;
    }

    // Link for: first,last
    @Override
    protected AbstractLink newPagingNavigationLink(String id, IPageable pageable, int pageNumber) {
        ExternalLink navCont = new ExternalLink(id + "Cont", (String) null);

        // add css for enable/disable link
        long pageIndex = pageable.getCurrentPage() + pageNumber;
        navCont.add(new AttributeModifier("class", new PageLinkCssModel(pageable, pageIndex, "disabled")));

        // change original wicket-link, so that it always generates href
        navCont.add(new AjaxPagingNavigationLink(id, pageable, pageNumber) {
            @Override
            protected void disableLink(ComponentTag tag) {}
        });
        return navCont;
    }

    // Link for: prev,next
    @Override
    protected AbstractLink newPagingNavigationIncrementLink(String id, IPageable pageable, int increment) {
        ExternalLink navCont = new ExternalLink(id + "Cont", (String) null);

        // add css for enable/disable link
        long pageIndex = pageable.getCurrentPage() + increment;
        navCont.add(new AttributeModifier("class", new PageLinkIncrementCssModel(pageable, pageIndex)));

        // change original wicket-link, so that it always generates href
        navCont.add(new AjaxPagingNavigationIncrementLink(id, pageable, increment) {
            @Override
            protected void disableLink(ComponentTag tag) {}
        });

        navCont.setVisible(false);
        return navCont;
    }

    private class PageLinkIncrementCssModel implements IModel<String>, Serializable {
        protected final IPageable pageable;

        private final long pageNumber;

        public PageLinkIncrementCssModel(IPageable pageable, long pageNumber) {
            this.pageable = pageable;
            this.pageNumber = pageNumber;
        }

        @Override
        public String getObject() {
            return isEnabled() ? "" : "disabled";
        }

        @Override
        public void setObject(String object) {}

        @Override
        public void detach() {}

        public boolean isEnabled() {
            if (pageNumber < 0) {
                return !isFirst();
            } else {
                return !isLast();
            }
        }

        public boolean isFirst() {
            return pageable.getCurrentPage() <= 0;
        }

        public boolean isLast() {
            return pageable.getCurrentPage() >= (pageable.getPageCount() - 1);
        }
    }

    private class PageLinkCssModel implements IModel<String>, Serializable {
        private final long pageNumber;
        protected final IPageable pageable;
        private final String css;

        public PageLinkCssModel(IPageable pageable, long pageNumber, String css) {
            this.pageNumber = pageNumber;
            this.pageable = pageable;
            this.css = css;
        }

        @Override
        public String getObject() {
            return isSelected() ? css : "";
        }

        @Override
        public void setObject(String object) {}

        @Override
        public void detach() {}

        public boolean isSelected() {
            return getPageNumber() == pageable.getCurrentPage();
        }

        private long getPageNumber() {
            long idx = pageNumber;
            if (idx < 0) {
                idx = pageable.getPageCount() + idx;
            }

            if (idx > (pageable.getPageCount() - 1)) {
                idx = pageable.getPageCount() - 1;
            }

            if (idx < 0) {
                idx = 0;
            }

            return idx;
        }
    }

}
