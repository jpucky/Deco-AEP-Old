// $Log: ViewContBenutzerStatus.java,v $
// Revision 1.3  2016/11/10 17:44:58  tw
// Version 1.17-H, CR ohne Auftrag: Einblendung der IP-Adresse unter Administration/Benutzerstatus
//
// Revision 1.2  2015/02/12 00:58:08  tw
// Haldenbearbeitung: Grossflaechiger Skriptumbau. Zwangsumstellung auf JQuery 1.7.1.
//
// Revision 1.1  2014/10/14 16:28:11  tw
// umzug
//
// Revision 1.1  2014/10/03 15:07:05  tw
// Implementierung: Haldenstatus.
//
//

package de.decodetron.tab.administration.benutzerstatus;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.resource.JQueryPluginResourceReference;

import de.decodetron.security.SessionController;
import de.decodetron.security.UserInfoBundle;

/**
 * @author Thomas Winter
 * @since 03.10.2014
 */
public class ViewContBenutzerStatus extends Panel {
    
    @Override
    public void renderHead(IHeaderResponse response) {

        // ///////////////////////
        // /// Fixierter Tabellenkopf ...
        // /
        response.render(CssHeaderItem.forReference(new CssResourceReference(ViewContBenutzerStatus.class,
                "../../../../../css/tableDefaultTheme.css")));
        response.render(JavaScriptHeaderItem.forReference(new JQueryPluginResourceReference(ViewContBenutzerStatus.class,
                "../../../../../js/jquery.fixedheadertable.js")));
        response.render(OnDomReadyHeaderItem.forScript("$.fn.initFixedTableArcNew()"));
    }
    
    public ViewContBenutzerStatus(String id, IModel<?> model) {
        super(id, model);

        UserDataProvider dp = new UserDataProvider();
        final UserListe dataView;
        add(dataView = new UserListe("userliste", dp));
        add(new OrderByBorder("obylogin", UserDataProvider.SORT_LOGIN, dp) {
            protected void onSortChanged() {
                dataView.setCurrentPage(dataView.getCurrentPage());
            }
        });
        add(new OrderByBorder("obysession", UserDataProvider.SORT_SESSIONID, dp) {
            protected void onSortChanged() {
                dataView.setCurrentPage(dataView.getCurrentPage());
            }
        });
        add(new OrderByBorder("obydatesince", UserDataProvider.SORT_LOGINSINCE, dp) {
            protected void onSortChanged() {
                dataView.setCurrentPage(dataView.getCurrentPage());
            }
        });
        add(new OrderByBorder("obyvorname", UserDataProvider.SORT_VORNAME, dp) {
            protected void onSortChanged() {
                dataView.setCurrentPage(dataView.getCurrentPage());
            }
        });
        add(new OrderByBorder("obynachname", UserDataProvider.SORT_NACHNAME, dp) {
            protected void onSortChanged() {
                dataView.setCurrentPage(dataView.getCurrentPage());
            }
        });
        add(new OrderByBorder("obyipadress", UserDataProvider.SORT_IPADRESS, dp) {
            protected void onSortChanged() {
                dataView.setCurrentPage(dataView.getCurrentPage());
            }
        });

        add(new LadeBenutzer("refresh", getDefaultModel()));
    }

    private class UserListe extends DataView<UserInfoBundle> {

        protected UserListe(String id, IDataProvider<UserInfoBundle> dataProvider) {
            super(id, dataProvider);
        }

        @Override
        protected void populateItem(final Item<UserInfoBundle> item) {
            UserInfoBundle info = item.getModelObject();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            item.add(new Label("login", info.getUser().getLogin()));
            item.add(new Label("vorname", info.getUser().getVorname()));
            item.add(new Label("nachname", info.getUser().getNachname()));
            item.add(new Label("sessionid", info.getSessionID()));
            item.add(new Label("loginsince", sdf.format(new Date(info.getLoginSince()))));
            item.add(new Label("ipadress", info.getIpAdress()));
            item.add(AttributeModifier.replace("class", new Model<String>() {
                public String getObject() {
                    return (item.getIndex() % 2 == 1) ? "even" : "odd";
                }
            }));
        }

    }

    private class UserDataProvider extends SortableDataProvider {

        public static final String SORT_LOGIN = "login";
        public static final String SORT_VORNAME = "vorname";
        public static final String SORT_NACHNAME = "nachname";
        public static final String SORT_SESSIONID = "sessionID";
        public static final String SORT_LOGINSINCE = "loginSince";
        public static final String SORT_IPADRESS = "ipadress";

        public UserDataProvider() {
            setSort("sortLogin", SortOrder.DESCENDING);
        }

        @Override
        public Iterator<UserInfoBundle> iterator(long first, long count) {
            SortParam<String> sort = getSort();
            List<UserInfoBundle> subList = getSortedList(sort, SessionController.getUserInfoList()).subList(
                (int) first, (int) first + (int) count);
            return subList.iterator();
        }

        @Override
        public long size() {
            return SessionController.getKeyList().size();
        }

        private List<UserInfoBundle> getSortedList(final SortParam<String> sort, List<UserInfoBundle> l) {

            if (SORT_LOGIN.equals(sort.getProperty())) {
                Collections.sort(l, new Comparator<UserInfoBundle>() {
                    public int compare(UserInfoBundle arg0, UserInfoBundle arg1) {
                        return (sort.isAscending() ? arg0 : arg1).getUser().getLogin()
                                .compareTo((sort.isAscending() ? arg1 : arg0).getUser().getLogin());
                    }
                });
            } else if (SORT_VORNAME.equals(sort.getProperty())) {
                Collections.sort(l, new Comparator<UserInfoBundle>() {
                    public int compare(UserInfoBundle arg0, UserInfoBundle arg1) {
                        return (sort.isAscending() ? arg0 : arg1).getUser().getVorname()
                                .compareTo((sort.isAscending() ? arg1 : arg0).getUser().getVorname());
                    }
                });
            } else if (SORT_NACHNAME.equals(sort.getProperty())) {
                Collections.sort(l, new Comparator<UserInfoBundle>() {
                    public int compare(UserInfoBundle arg0, UserInfoBundle arg1) {
                        return (sort.isAscending() ? arg0 : arg1).getUser().getNachname()
                                .compareTo((sort.isAscending() ? arg1 : arg0).getUser().getNachname());
                    }
                });
            } else if (SORT_SESSIONID.equals(sort.getProperty())) {
                Collections.sort(l, new Comparator<UserInfoBundle>() {
                    public int compare(UserInfoBundle arg0, UserInfoBundle arg1) {
                        return (sort.isAscending() ? arg0 : arg1).getSessionID().compareTo(
                            (sort.isAscending() ? arg1 : arg0).getSessionID());
                    }
                });
            } else if (SORT_LOGINSINCE.equals(sort.getProperty())) {
                Collections.sort(l, new Comparator<UserInfoBundle>() {
                    public int compare(UserInfoBundle arg0, UserInfoBundle arg1) {
                        return (sort.isAscending() ? arg0 : arg1).getLoginSince().compareTo(
                            (sort.isAscending() ? arg1 : arg0).getLoginSince());
                    }
                });
            }else if (SORT_IPADRESS.equals(sort.getProperty())) {
                Collections.sort(l, new Comparator<UserInfoBundle>() {
                    public int compare(UserInfoBundle arg0, UserInfoBundle arg1) {
                        return (sort.isAscending() ? arg0 : arg1).getIpAdress().compareTo(
                            (sort.isAscending() ? arg1 : arg0).getIpAdress());
                    }
                });
            }

            return l;
        }

        @Override
        @SuppressWarnings({ "unchecked" })
        public IModel<Serializable> model(Object object) {
            return new Model<Serializable>((Serializable) object);
        }
    }

    private class LadeBenutzer extends Link<String> {

        public LadeBenutzer(String id, IModel model) {
            super(id, model);
        }

        @Override
        public void onClick() {
            // nix. der request-zyklus reicht zum erneuern. ajax schenken wir uns.
        }
    }
}
