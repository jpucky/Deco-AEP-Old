// Revision 1.41  2014/12/17 22:07:29  tw
// Recherche: aktuelles Jahr verwenden.
//
// Revision 1.40  2014/11/06 13:13:03  tw
// Anbindung: History-DB Benutzerrechte.
//
// Revision 1.39  2014/10/29 17:47:39  tw
// Implementierung: Benutzer kopieren.
//
// Revision 1.38  2014/10/29 14:58:59  tw
// Bugfix: setReuseitems f. sinnvollerer Errorhandling. Vorbereitung: Benutzer kopieren.
//
// Revision 1.37  2014/10/11 16:28:04  tw
// Implementierung: Benutzerrechte Suchfelder.
//
// Revision 1.36  2014/10/10 12:05:21  tw
// Implementierung: Benutzerrechte Vertriebsleiter.
//
// Revision 1.35  2014/09/30 13:01:04  tw
// geaenderte Benutzer nach Suche loeschen.
//
// Revision 1.34  2014/09/13 15:22:54  tw
// Rechteverwaltung: Bugfix: Textfeldaenderungen werden richtig verarbeitet f. d. Liste d. geaenderten Benutzer.
//
// Revision 1.33  2014/09/12 20:40:58  tw
// Aep-Benutzerr-Rechteverwaltung: Setzten des Filters.
//
// Revision 1.32  2014/09/12 15:04:16  tw
// Aep-Benutzerr-Rechteverwaltung: Setzten des Defaultpasswortes.
//
// Revision 1.31  2014/09/10 16:15:41  tw
// Aep-Benutzerr-Rechteverwaltung: Aufraeumarbeiten, Klassen entdroeselt.
//
// Revision 1.30  2014/09/09 21:31:23  tw
// Aep-Benutzerr-Rechteverwaltung: Benutzer Anlegen, Backup.
//
// Revision 1.29  2014/09/09 15:43:00  tw
// Aep-Benutzerr-Rechteverwaltung: Benutzer Anlegen, Backup.
//
// Revision 1.28  2014/08/26 14:37:22  tw
// Aep-Benutzerr-Rechteverwaltung: Benutzer Loeschen implementiert.
//
// Revision 1.27  2014/08/26 01:15:10  tw
// .
//
// Revision 1.26  2014/08/26 01:09:24  tw
// Aep-Benutzerr-Rechteverwaltung: Benutzer Loeschen.
//
// Revision 1.25  2014/08/25 15:14:25  tw
// Aep-Benutzerr-Rechteverwaltung: Bugfix, Model auraeumen.
//
// Revision 1.24  2014/08/24 22:18:54  tw
// Aep-Benutzerr-Rechteverwaltung: Bugfix, Model auraeumen.
//
// Revision 1.23  2014/08/22 22:08:26  tw
// Aep-Benutzerr-Rechteverwaltung: Layout.
//
// Revision 1.22  2014/08/21 22:04:52  tw
// Aep-Benutzerr-Rechteverwaltung: Layout.
//
// Revision 1.21  2014/08/19 21:51:56  tw
// Aep-Benutzerr-Rechteverwaltung: Layout.
//
// Revision 1.20  2014/08/19 10:41:17  tw
// Aep-Benutzerr-Rechteverwaltung: Layout, Textfeld-tests.
//
// Revision 1.19  2014/08/18 22:02:32  tw
// Tests: Klappmechanismus f. Tabelle.
//
// Revision 1.18  2014/08/18 13:10:08  tw
// Bugfix: Aep-Benutzer-Rechteverwaltung.
//
// Revision 1.17  2014/08/18 12:34:34  tw
// Bugfix: Aep-Benutzer-Rechteverwaltung.
//
// Revision 1.16  2014/08/16 14:17:15  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung: Anbindung Textfelder.
//
// Revision 1.15  2014/08/15 15:28:07  tw
// system.out raus.
//
// Revision 1.14  2014/08/15 15:14:59  tw
// .
//
// Revision 1.13  2014/08/15 14:39:48  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.12  2014/08/15 10:47:56  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.11  2014/08/14 21:52:06  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.10  2014/08/14 16:04:41  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.9  2014/08/14 01:15:39  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.8  2014/08/12 01:03:24  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.7  2014/08/11 12:32:08  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.6  2014/08/09 11:47:19  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.5  2014/08/09 00:16:20  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.4  2014/08/08 22:34:05  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.3  2014/08/08 16:05:02  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.2  2014/08/07 21:16:53  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.1  2014/08/07 00:15:42  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
//

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.value.ValueMap;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import de.decodetron.AEPApplication;
import de.decodetron.AEPModel;
import de.decodetron.Const;
import de.decodetron.bo.Section;
import de.decodetron.bo.User;
import de.decodetron.bo.UserSectionData;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.statistik.TrefferLabel;

/**
 * @author Thomas Winter
 * @since 07.08.2014
 */
public class BenListe extends Panel{

    private static Logger log = Logger.getLogger(BenListe.class);

    public void renderHead(IHeaderResponse response) {
        initUserDeleteModel();
        // initUser2ChangeModel();
        response.render(OnDomReadyHeaderItem.forScript("$.fn.hideViewBusy();"));
    }

    public BenListe(String id, IModel<AEPModel> model) {
        super(id, model);
        setOutputMarkupId(true);
        initErrorList();
        initUser2ChangeModel();
        initUserSectionOrignialModel();
        // add(new ChangedUserListWrapper("dummycontainer", model.getObject()));
        add(new RechteForm("rechteForm", model));
        add(new UserNewForm("usernew", model));
        add(new DeleteUserLink("deleteuser", model));
        add(new PwdResetLink("pwdreset", model));

        UserSectionDataProvider dp = new UserSectionDataProvider((IModel<AEPModel>) BenListe.this.getDefaultModel());
        DataTable<UserSectionData, String> dataTable = createDataTable(BenListe.this.getDefaultModel(), dp);
        add(dataTable);

        // dataTable.setOutputMarkupId(true);
        // add(new UserNeuTest("test", model, dataTable));

        // Tabellenkopf
        dataTable.addTopToolbar(new HeadersToolbar<String>(dataTable, dp));
        add(new PagingNavigator("navigator", dataTable));
        add(new TrefferLabel("treffer", getDefaultModel()));
    }

    /**
     * Testklasse um die Zickereien rund um das Info- Errorpanel zu testen.
     */
    private class CustomTestValidator implements IValidator<String> {
        @Override
        public void validate(IValidatable<String> validatable) {
            if ("xxx".equals(validatable.getValue())) {
                ValidationError err = new ValidationError();
                err.setMessage("So geht das nicht !");
                // err.addKey("ben.filter.error.adminuser");
                validatable.error(err);
            }
        }
    }

    /**
     * Zeigt die geänderten Nutzer.
     */
    private class RechteForm extends Form {

        private List<UserSectionData> debug = null;

        @SuppressWarnings("rawtypes")
        public RechteForm(String id, IModel<?> model) {
            super(id, model);

            add(new UserSpeichernButton("savebutton", BenListe.this.getDefaultModel()));
            add(new UserAbbrechenButton("cancelbutton", BenListe.this.getDefaultModel()));

            FeedbackPanel feedback = new FeedbackPanel("feedback");
            feedback.setEscapeModelStrings(false);
            feedback.setOutputMarkupId(true);
            add(feedback);

            // TextField txt;
            // add(txt = new TextField<String>("txttest"));
            // txt.add(new CustomTestValidator());
        }

    }

    /**
     * Bietet die Möglichkeit neue Benutzer anzulegen.
     */
    private class UserNewForm extends Form {

        public UserNewForm(String id, IModel model) {
            super(id, model);
        }

        @Override
        protected void onSubmit() {

            UserSectionData u = new UserSectionData(new User());
            // u.setLogin(Util.getCurrentTimeStamp());
            // u.setVorname("...");
            // u.setNachname("...");
            u.setPasswd(de.decodetron.util.Const.STANDARTPWD_VER);
            u.setGroupIds("-1");
            u.setFilter("*");
            u.setAnlagedatum(new Timestamp(System.currentTimeMillis()).toString());
            AEPApplication.get().getDBUser().insertH(LoginSession.get().getUser(), u);

            // AEPModel model = (AEPModel) BenListe.this.getDefaultModelObject();
            // model.getBenVerwaltungModel().getUserListOriginal().add(u);

            super.onSubmit();
        }
    }

    /**
     * Ausgelagerter Tabellengenerator, damits übersichtlicher bleibt.
     * 
     * @param IModel
     *            <?> model
     * @param UserSectionDataProvider
     *            dp
     * @return DataTable<UserSectionData, String>
     */
    private DataTable<UserSectionData, String> createDataTable(IModel<?> model, UserSectionDataProvider dp) {

        DataTable<UserSectionData, String> dataTable = null;
        List<IColumn<UserSectionData, String>> columns = new ArrayList<IColumn<UserSectionData, String>>();

        // /////////////////////////////////////////////////////////////////////
        // /// Benutzerdaten
        // /
        // columns.add(new AbstractColumn<UserSectionData, String>(new Model<String>("Login"),
        // "login") {
        //
        // @Override
        // public void populateItem(Item<ICellPopulator<UserSectionData>> cellItem, String
        // componentId,
        // IModel<UserSectionData> rowModel) {
        // cellItem.add(new Label(componentId, rowModel.getObject().getLogin()));
        // }
        // });
        columns.add(new TextFieldColumnLogin<UserSectionData, String>("Login", "login", BenListe.this.getDefaultModel()));
        columns.add(new TextFieldColumn<UserSectionData, String>("Vorname", "vorname", BenListe.this.getDefaultModel()));
        columns.add(new TextFieldColumn<UserSectionData, String>("Nachname", "nachname", BenListe.this
                .getDefaultModel()));
        columns.add(new TextFieldColumnFilter<UserSectionData, String>("Filter", "Filter", BenListe.this
                .getDefaultModel()));
        columns.add(new TextFieldColumnVLeitung<UserSectionData, String>("Gebiet", "Gebiet", BenListe.this
                .getDefaultModel()));
        columns.add(new TextFieldColumnVLeitung<UserSectionData, String>("Vertriebs-leitung", "Vertriebsleitung",
                BenListe.this.getDefaultModel()));
        columns.add(new ButtonColumnCopyUser<UserSectionData, String>("Benutzer kopieren", BenListe.this));
        columns.add(new CheckBoxColumnResetPwd("Passwort zurückset.", "isDefaultPasswd", BenListe.this
                .getDefaultModel()));
        columns.add(new CheckBoxColumnDeleteUser("Löschen", "delete", BenListe.this.getDefaultModel()));

        // /////////////////////////////////////////////////////////////////////
        // /// Sektionsdaten. Jeder Benutzer bekommt so viele Spalten zu sehen, wie es seiner
        // /// Berechtigung entspricht. Superadmin: Alles, Admin: Nicht Alles.
        // /
        // Beispiel für eine hardcodierte Variante ...
        /**
         * <pre>
         * columns.add(new AbstractColumn&lt;UserSectionData, String&gt;(new Model&lt;String&gt;(&quot;Defenktenliste&quot;)) {
         * 
         *     &#064;Override
         *     public void populateItem(Item&lt;ICellPopulator&lt;UserSectionData&gt;&gt; cellItem, String componentId,
         *             IModel&lt;UserSectionData&gt; rowModel) {
         *         cellItem.add(new Label(componentId, rowModel.getObject().getVerver()));
         *     }
         * });
         * </pre>
         */

        String kzOld = "";
        String kzNew = "";
        List<Section> sections = AEPApplication.get().getDBUser()
                .getSectionsForUser(LoginSession.get().getUser().getId());
        for (Iterator<Section> iterator = sections.iterator(); iterator.hasNext();) {

            final Section section = iterator.next();
            final String colName = section.getKz();
            if (colName == null) {
                continue;
            }

            IModel<String> rm = Model.of(section.getKz());
            kzOld = kzNew;
            kzNew = section.getMenuitemid();
            if (!kzNew.equals(kzOld)) {

                // Hierum kümmern wir uns später ...
                // columns.add(new AbstractColumn<UserSectionData, String>(new
                // Model<String>(colName)) {
                //
                // @Override
                // public void populateItem(Item<ICellPopulator<UserSectionData>> cellItem, String
                // componentId,
                // IModel<UserSectionData> rowModel) {
                // cellItem.add(new Label(componentId, ""));
                // }
                //
                // int cnt = 0;
                // @Override
                // public String getCssClass() {
                // return "switchcol " + section.getMenuitemid();
                // }
                //
                // });
            }

            ResourceModel rcm = new ResourceModel("label." + section.getKz(), section.getKz());
            // columns.add(new SectionColumn(section, section.getKz()));
            // columns.add(new CheckboxColumn(rm, section.getKz()));
            columns.add(new CheckBoxColumnSection<UserSectionData, String>(rcm, section.getKz(), getDefaultModel()));
        }

        dataTable = new DataTable<UserSectionData, String>("table", columns, dp, Const.ITEMS_PER_PAGE_SMALL) {

            @Override
            protected Item<UserSectionData> newRowItem(final String id, final int index,
                    final IModel<UserSectionData> model) {
                OddEvenItem<UserSectionData> item = new OddEvenItem<UserSectionData>(id, index, model);
                return item;
            }

        };
        dataTable.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
        return dataTable;
    }

    /**
     * Methode fürs Speichern der geänderten Benutzer.
     */
    public void saveUser2Change() {

        AEPModel model = (AEPModel) BenListe.this.getDefaultModelObject();

        try {
            HashMap<Long, UserSectionData> hm = model.getBenVerwaltungModel().getUserList2Change();
            Set<Long> keyset = hm.keySet();
            for (Iterator<Long> iterator = keyset.iterator(); iterator.hasNext();) {
                Long key = iterator.next();
                UserSectionData user = hm.get(key);
                AEPApplication.get().getDBUser().updateUserSection(LoginSession.get().getUser(), user);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            // //////////////////////////
            // /// Löschen aller geänderten Benutzer;
            // /
            model.getBenVerwaltungModel().initUserList2Change();
            model.getBenVerwaltungModel().initUserListOriginal();
        }

    }

    /**
     * Datentonne für die geänderten Benutzer
     */
    private void initUser2ChangeModel() {
        AEPModel model = (AEPModel) BenListe.this.getDefaultModelObject();
        model.getBenVerwaltungModel().initUserList2Change();
    }

    /**
     * Datentonne für die zu löschenden Benutzer.
     */
    private void initUserDeleteModel() {
        AEPModel model = (AEPModel) BenListe.this.getDefaultModelObject();
        model.getBenVerwaltungModel().initUserDeleteModel();
    }

    /**
     * Datentonne für alle geladenen Benutzer in unverändertem Zustand, um sich den DB-Zugriff zu
     * sparen.
     */
    private void initUserSectionOrignialModel() {
        AEPModel model = (AEPModel) BenListe.this.getDefaultModelObject();
        model.getBenVerwaltungModel().initUserListOriginal();
    }

    /**
     * Fehlermeldungen werden hier gesammelt, da es mir nicht gelungen ist, die Fehlermeldung aus
     * der Liste auf die Errormessage-Anzeige zu bekommen.
     */
    private void initErrorList() {
        AEPModel model = (AEPModel) BenListe.this.getDefaultModelObject();
        model.getBenVerwaltungModel().setErrorList(new ArrayList<String>());
    }

    /**
     * "Überführt" die Benutzer aus der HashMap in eine "Liste-aller-geänderten-Benutzer".
     * 
     * @return List<UserSectionData>
     */
    private List<UserSectionData> getUserSectionDatas() {
        ValueMap vm = (ValueMap) BenListe.this.getDefaultModelObject();
        List<UserSectionData> list = new ArrayList<UserSectionData>();
        AEPModel model = (AEPModel) BenListe.this.getDefaultModelObject();
        HashMap<Long, UserSectionData> hm = model.getBenVerwaltungModel().getUserList2Change();
        Set<Long> keyset = hm.keySet();
        for (Iterator<Long> iterator = keyset.iterator(); iterator.hasNext();) {
            Long key = iterator.next();
            list.add(hm.get(key));
        }
        return list;
    }

    public void showMap() {
        AEPModel model = (AEPModel) BenListe.this.getDefaultModelObject();
        HashMap<Long, UserSectionData> hm = model.getBenVerwaltungModel().getUserList2Change();
        Set keyset = hm.keySet();

        if (keyset.isEmpty()) {
            // System.out.println("User: " + "---");
        }

        for (Iterator<String> iterator = keyset.iterator(); iterator.hasNext();) {
            String key = iterator.next();
            UserSectionData user = hm.get(key);
            // System.out.println("User: " + user.toString());
        }
    }

}
