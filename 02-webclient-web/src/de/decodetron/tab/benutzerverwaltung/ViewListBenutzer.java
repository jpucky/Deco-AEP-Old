// $Log: ViewListBenutzer.java,v $
// Revision 1.24  2016/06/28 19:36:23  tw
// BUFIX 3963: Benutzerverwaltung/Benutzer-DB =>  zusaetzliche Felder.
//
// Revision 1.23  2014/08/08 16:05:02  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.22  2014/07/29 21:19:58  tw
// Schnittstellenanpassung Scanbelege.
//
// Revision 1.21  2014/06/28 16:00:38  tw
// Benutzer bearbeiten finalisiert. Busy-Indicator f. Statistik u. Benutzer Bearbeiten.
//
// Revision 1.20  2014/06/12 12:29:20  tw
// Benutzer bearbeiten: Vorbereitung f. korrekte Gruppenverarbeitung.
//
// Revision 1.19  2014/06/05 15:35:29  tw
// Backup: Benutzer anlegen, aendern, loeschen.
//
// Revision 1.18  2014/06/01 12:50:26  tw
// Benutzer aendern, Grundstruktur.
//
// Revision 1.17  2014/05/30 22:21:44  tw
// Backup: Benutzer aendern.
//
// Revision 1.16  2014/05/30 12:22:11  tw
// Pflegemassnahmen.
//
// Revision 1.15  2014/05/13 01:19:43  tw
// Dataprovider Cache - Optimierungen.
//
// Revision 1.14  2014/05/12 16:24:44  tw
// Aufraeumarbeiten, Benutzer anlegen loeschen.
//
// Revision 1.13  2014/05/11 10:59:56  tw
// Backup: Benutzer loeschen.
//
// Revision 1.12  2014/05/09 13:50:57  tw
// Backup: Benutzer anlegen.
//
// Revision 1.11  2014/05/08 20:59:13  tw
// Bugfix: Fehlerhafte Treffermenge wenn Suchergebnis leer.
//
// Revision 1.10  2014/04/28 11:11:15  tw
// Schnittstellen- umzug/umbenennung Filter=>Statistik
//
// Revision 1.9  2014/03/27 12:08:20  tw
// Vorbereitung, CR-Defektenlisten f. Lieferanten: Markup-freie Tabellengestaltung.
//
// Revision 1.8  2014/03/27 01:20:43  tw
// Vorbereitung, CR-Defektenlisten f. Lieferanten: Markup-freie Tabellengestaltung.
//
// Revision 1.7  2014/03/26 21:03:10  tw
// Vorbereitung, CR-Defektenlisten f. Lieferanten: Markup-freie Tabellengestaltung.
//
// Revision 1.6  2014/02/21 01:31:32  tw
// Skriptfehler Passwort zuruecksezten beseitigt.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.5  2014/02/18 02:23:51  tw
// Individualisierung: Modaler Dialog.
//
// Revision 1.4  2014/02/15 11:58:48  tw
// Implementierung: Passwort zuruecksetzen.
//
// Revision 1.3  2014/02/14 17:03:24  tw
// Implementierung: Passwort zuruecksetzen.
//
// Revision 1.2  2014/02/13 13:06:24  tw
// Benutzerverwaltung, dynamischer Suchfilteraufbau, Schnittstellenanpassung.
//
// Revision 1.1  2014/02/13 02:03:19  tw
// Benutzerverwaltung, dynamischer Suchfilteraufbau.
//
//

package de.decodetron.tab.benutzerverwaltung;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPApplication;
import de.decodetron.Const;
import de.decodetron.bo.DataRecord;
import de.decodetron.bo.User;
import de.decodetron.data.Util;
import de.decodetron.dlgcomponents.ModalWindowUser;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.MyMultilineLabel;
import de.decodetron.tab.benutzerverwaltung.aendern.BenutzerAendernLink;
import de.decodetron.tab.benutzerverwaltung.loeschen.DeleteUserCheckBoxPanel;
import de.decodetron.tab.benutzerverwaltung.pwdreset.CheckBoxPanel;
import de.decodetron.tab.statistik.TrefferLabel;

/**
 * DefaultDataTable<User, String> liefert mehr als benötigt. Hier wird DataTable verwendet und mit
 * eigenen Komonenten angereichert. Prototyp zum Testen.
 * 
 * @author Thomas Winter
 * @since 12.02.2014
 */
public class ViewListBenutzer extends Panel {

    public static String CTX_BEN_ANLEGEN_AENDERN = "BENANLEGENAENDERN";
    public static String CTX_RESET_PWD = "RESETPWD";

    public void renderHead(IHeaderResponse response) {
        response.render(OnDomReadyHeaderItem.forScript("$.fn.initSearchButtonSelector()"));
        response.render(OnDomReadyHeaderItem.forScript("$.fn.hideViewBusy();"));
    }

    @SuppressWarnings("unchecked")
    public ViewListBenutzer(String id, final IModel<?> mm, String context, final ModalWindowUser benAendernMdlDlg) {
        super(id, mm);
        setOutputMarkupId(true);
        initUserDeleteModel();

        final DataTable<User, String> dataTable;
        UserDataProvider dataProvider = new UserDataProvider((IModel<ValueMap>) mm);
        List<IColumn<User, String>> columns = new ArrayList<IColumn<User, String>>();

        // final ModalWindowUser benAendernMdlDlg = createConfirmModal("modalBenAendern");

        // Für die runden Ecken
        columns.add(new AbstractColumn<User, String>(new Model<String>("dummy")) {
            @Override
            public void populateItem(Item<ICellPopulator<User>> cellItem, String componentId, IModel<User> model) {
                cellItem.add(new Label(componentId, Model.of("dummy")));
            }

            @Override
            public String getCssClass() {
                return "invisible";
            }
        });

        if (CTX_RESET_PWD.equals(context)) {

            // Passwort zurücksetzen
            columns.add(new AbstractColumn<User, String>(new ResourceModel("label.pwdreset")) {
                @Override
                public Component getHeader(final String componentId) {
                    return new MyMultilineLabel(componentId, getDisplayModel());
                }

                @Override
                public void populateItem(Item<ICellPopulator<User>> cellItem, String componentId, IModel<User> model) {
                    cellItem.add(new CheckBoxPanel(componentId, model.getObject(), ViewListBenutzer.this
                            .getDefaultModel()));
                }
            });
        } else if (CTX_BEN_ANLEGEN_AENDERN.equals(context)) {

            columns.add(new AbstractColumn<User, String>(new ResourceModel("label.changeUser")) {
                @Override
                public Component getHeader(final String componentId) {
                    return new MyMultilineLabel(componentId, getDisplayModel());
                }

                @Override
                public void populateItem(Item<ICellPopulator<User>> cellItem, String componentId, IModel<User> model) {
                    cellItem.add(new BenutzerAendernLink(componentId, model, benAendernMdlDlg));
                }
            });

            // Benutzer löschen
            columns.add(new AbstractColumn<User, String>(new ResourceModel("label.deleteUser")) {
                @Override
                public Component getHeader(final String componentId) {
                    return new MyMultilineLabel(componentId, getDisplayModel());
                }

                @Override
                public void populateItem(Item<ICellPopulator<User>> cellItem, String componentId, IModel<User> model) {
                    cellItem.add(new DeleteUserCheckBoxPanel(componentId, model.getObject(), getDefaultModel()));
                }

                @Override
                public String getCssClass() {
                    return "checkbox";
                }
            });

        }

        // Benutzerinformationen
        int colNr = 0;
        DataRecord tblHeader = AEPApplication.get().getDBUser()
                .getColumNamesAsDataRecord(LoginSession.get().getUser(), Const.TABLENAME_USER);
        while (colNr++ < tblHeader.getSize() - 1) {
            final String colName = tblHeader.getColItem(colNr);
            final ResourceModel colLabel = new ResourceModel("label." + colName, colName);
            // columns.add(new PropertyColumn<User, String>(srm, colName, colName));

            // Ganz sauber ist das nicht. Hier wird nur beim angemeldeten Benutzer geguckt, ob das
            // Feld existiert ..
            if (Util.fieldExist(LoginSession.get().getUser().getClass(), colName)) {
                columns.add(new AbstractColumn<User, String>(colLabel, colName) {

                    @Override
                    public void populateItem(Item<ICellPopulator<User>> cellItem, String componentId,
                            IModel<User> rowModel) {
                        // Für unbekannte Spaltenbezeichner ein Dummy einblenden ...
                        // if (Util.fieldExist(rowModel.getObject().getClass(), colName)) {
                        cellItem.add(new Label(componentId, new PropertyModel<User>(rowModel, colName)));
                        // } else {
                        // cellItem.add(new Label(componentId, Model.of("unbekannt")));
                        // }

                        // cellItem.add(new Label(componentId, new MyPropertyModel<User>(rowModel,
                        // colName)));
                        // cellItem.add(new Label(componentId, Model.of("dummy")));
                    }
                });
            }
        }

        // Even Odd
        add(dataTable = new DataTable<User, String>("table", columns, dataProvider, Const.ITEMS_PER_PAGE) {
            @Override
            protected Item<User> newRowItem(final String id, final int index, final IModel<User> model) {
                OddEvenItem<User> item = new OddEvenItem<User>(id, index, model);
                // item.add(new AjaxEventBehavior("onclick") {
                // @Override
                // protected void onEvent(AjaxRequestTarget target) {
                // // System.out.println("User: " + model.getObject().getLogin());
                // confirmModal.show(target, model.getObject());
                // }
                // });
                return item;
            }
        });

        // dataTable.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
        // Tabellenkopf
        dataTable.addTopToolbar(new HeadersToolbar<String>(dataTable, dataProvider));

        // Tabellennavigation
        add(new PagingNavigator("navigator", dataTable));
        add(new TrefferLabel("treffer", getDefaultModel()));
        // add(benAendernMdlDlg);
        // add(dataView = new ListViewBenutzer("user", dataProvider, Const.ITEMS_PER_PAGE));
        // add(new Label("test", new StringResourceModel("vorname", this, getDefaultModel())));
    }

    /**
     * Eine Möglichkeit, um auf unbekannte Felder zu reagieren ?!
     * 
     * @param <T>
     */
    private class MyPropertyModel<T> extends PropertyModel<T> {

        public MyPropertyModel(Object modelObject, String expression) {
            super(modelObject, expression);
        }

        /**
         * @see org.apache.wicket.model.IModel#getObject()
         */
        @Override
        @SuppressWarnings("unchecked")
        public T getObject() {
            try {
                return super.getObject();
            } catch (WicketRuntimeException e) {
                if (getInnermostModelOrObject() instanceof User) {
                    // return (T)((User)getInnermostModelOrObject()).getId();
                    return (T) "unbekannt";
                }
                return (T) getInnermostModelOrObject();
            }
        }
    }

    /**
     * Setzt das Benutzer-Löschen Model zurück, bzw. Initialisiert es mit einem leeren HashSet.
     */
    public void initUserDeleteModel() {
        ValueMap map = (ValueMap) getDefaultModelObject();
        map.put(Const.KEY_DELETE_USERLIST, new LinkedHashSet<User>());
    }

    /**
     * Setzt das Benutzer-Löschen Model zurück, bzw. Initialisiert es mit einem leeren HashSet.
     */
    // public void resetUserDeleteModel() {
    // ValueMap map = (ValueMap) getDefaultModelObject();
    // map.put(Const.KEY_DELETE_USERLIST, new LinkedHashSet<User>());
    // }

}
