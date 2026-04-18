// $Log: TabbedHandler.java,v $
// Revision 1.19  2014/10/14 16:32:02  tw
// Haldenstatus, Anpassung an csv.
//
// Revision 1.18  2014/08/22 22:08:26  tw
// Aep-Benutzerr-Rechteverwaltung: Layout.
//
// Revision 1.17  2014/05/05 21:03:03  tw
// Backup: Benutzer anlegen.
//
// Revision 1.16  2014/03/20 02:25:23  tw
// Recherche: Paginierung, Funktionstest. Event-Konstanten zusammengefasst.
//
// Revision 1.15  2014/03/20 02:22:22  tw
// Recherche: Paginierung, Funktionstest. Event-Konstanten zusammengefasst.
//
// Revision 1.14  2014/02/15 11:58:48  tw
// Implementierung: Passwort zuruecksetzen.
//
// Revision 1.13  2014/02/13 23:07:42  tw
// Schnittstellenanpassung: User koennen mehrere Gruppen besitzen.
//
// Revision 1.12  2014/02/11 16:30:03  tw
// Bestaetigungsdialog f. Passwortaenderung.
//
// Revision 1.11  2014/02/07 15:44:08  tw
// Menueitems werden aus db gelesen.
//
// Revision 1.10  2014/02/07 12:41:05  tw
// bugfix: tab-statistik hatte keine mueitemsteuerung.
//
// Revision 1.9  2014/02/07 02:21:16  tw
// Benutzerverwaltung abgeklemmt.
//
// Revision 1.8  2014/02/03 16:41:28  tw
// Implementierung Benutzerverwaltung
//
// Revision 1.7  2014/01/31 01:44:00  tw
// Variables Label Kundennr/Lieferantnr.
//
// Revision 1.6  2014/01/29 19:52:54  tw
// Umbau: Belegart-Auswahl.
//
// Revision 1.5  2014/01/29 09:49:30  tw
// Vorbereitung: Belegart: EK / Benutzerverwaltung.
//
// Revision 1.4  2013/11/14 14:09:04  tw
// Schnittstellen Datenfilterung implementiert.
//
// Revision 1.3  2013/11/07 22:02:22  tw
// Clubliste geradegezogen.
//
// Revision 1.2  2013/11/02 00:51:32  tw
// Statistik-Modul, erster Wurf implementiert.
//
// Revision 1.1  2013/10/30 12:23:00  tw
// Anbindung eines Tab-Reiters.
//
//

package de.decodetron.tab;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPModel;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.administration.TabAdministration;
import de.decodetron.tab.benutzerverwaltung.TabBenutzerverwaltung;
import de.decodetron.tab.recherche.TabRecherche;
import de.decodetron.tab.statistik.TabStatistik;
import de.decodetron.tab.test.TabTestConfirmModal;

/**
 * @author Thomas Winter
 * @since 30.10.2013
 */
public class TabbedHandler extends Panel {

    public TabbedHandler(String id, final IModel<AEPModel> model) {
        super(id, model);

        int itemId = 0;
        List<MyStatistikTab> tabs = new ArrayList<MyStatistikTab>();
        List<String> itemNames = LoginSession.get().getAllMenuitemNames4User();

        if (itemNames.size() == 0) {
            securityCheck(tabs);
            add(new MyTabbedPanel("tabs", tabs));
            setRenderBodyOnly(true);
            return;
        }

        String itemName = itemNames.get(itemId) != null ? itemNames.get(itemId) : "RECHERCHE";
        tabs.add(new MyStatistikTab(new Model<String>(itemName), itemId) {
            public WebMarkupContainer getPanel(String panelId) {
                return new TabRecherche(panelId, model);
            }
        });
        itemId++;

        itemName = itemId < itemNames.size() ? itemNames.get(itemId) : "STATISTIK";
        tabs.add(new MyStatistikTab(new Model<String>(itemName), itemId) {
            public WebMarkupContainer getPanel(String panelId) {
                return new TabStatistik(panelId, model);
            }
        });
        itemId++;

        itemName = itemId < itemNames.size() ? itemNames.get(itemId) : "BENUTZERVERWALTUNG";
        tabs.add(new MyStatistikTab(new Model<String>(itemName), itemId) {
            public WebMarkupContainer getPanel(String panelId) {
                return new TabBenutzerverwaltung(panelId, model);
            }
        });
        itemId++;

        itemName = itemId < itemNames.size() ? itemNames.get(itemId) : "ADMINISTRATION";
        tabs.add(new MyStatistikTab(new Model<String>(itemName), itemId) {
            public WebMarkupContainer getPanel(String panelId) {
                return new TabAdministration(panelId, model);
            }
        });

        // Meine kleine Testecke ...
        if ("twinter@decodetron.de".equals(LoginSession.get().getUser().getLogin())) {
            tabs.add(new MyStatistikTab(new Model<String>("TESTKRAM"), -1) {
                @Override
                public void setIsVisible(boolean b) {
                    super.setIsVisible(true);
                }

                public WebMarkupContainer getPanel(String panelId) {
                    return new TabTestConfirmModal(panelId, model);
                    // return new TabTestConfirmSimple(panelId, model);
                }
            });

        }

        securityCheck(tabs);
        add(new MyTabbedPanel("tabs", tabs));
        setRenderBodyOnly(true);
    }

    /**
     * Nur Benutzer mit entsprechender MenueItemID darf den Menueeintrag sehen.
     * 
     * @param List
     *            <MyStatistikTab> tabs
     */
    private void securityCheck(List<MyStatistikTab> tabs) {

        Set<Integer> setIds = LoginSession.get().getAllMenuitemIds4User();
        for (Iterator<MyStatistikTab> iterator = tabs.iterator(); iterator.hasNext();) {
            MyStatistikTab tab = iterator.next();
            Integer tabId = tab.getItemId();
            tab.setIsVisible(setIds.contains(tabId));
        }
    }

    private class MyStatistikTab extends AbstractTab {

        private int itemId = -1;
        private boolean isVisible = true;

        public MyStatistikTab(IModel<String> title, int i) {
            super(title);
            itemId = i;
        }

        public int getItemId() {
            return itemId;
        }

        @Override
        public IModel<String> getTitle() {
            // TODO Auto-generated method stub
            return super.getTitle();
        }

        @Override
        public WebMarkupContainer getPanel(String panelId) {
            // TODO Auto-generated method stub
            return null;
        }

        public void setIsVisible(boolean b) {
            isVisible = b;
        }

        @Override
        public boolean isVisible() {
            return isVisible;
        }
    }

    private class MyTabbedPanel extends TabbedPanel<MyStatistikTab> {

        public MyTabbedPanel(String id, List<MyStatistikTab> tabs) {
            super(id, tabs);
            setVersioned(false);
            setOutputMarkupId(true);
        }

        @Override
        public void renderHead(IHeaderResponse response) {
            super.renderHead(response);
        }

        @Override
        protected WebMarkupContainer newLink(final String linkId, final int index) {
            return new Link<Void>(linkId) {
                @Override
                public void onClick() { 
                    setSelectedTab(index);
                    tabChanged();
                }
            };
        }

        protected void tabChanged() {
            // new ChangeEvent(this, AjaxRequestTarget.get(), null, Constants.TAB_CHANGED).fire();
        }
    }
}
