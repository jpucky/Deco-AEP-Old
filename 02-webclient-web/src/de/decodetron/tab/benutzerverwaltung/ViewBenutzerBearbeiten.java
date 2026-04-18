// $Log: ViewBenutzerBearbeiten.java,v $
// Revision 1.4  2015/12/03 16:38:41  tw
// Bugfix: AEP - Bug Benutzerverwaltung (Filter/Gebiet).
//
// Revision 1.3  2015/02/12 00:58:08  tw
// Haldenbearbeitung: Grossflaechiger Skriptumbau. Zwangsumstellung auf JQuery 1.7.1.
//
// Revision 1.2  2014/08/25 15:14:24  tw
// Aep-Benutzerr-Rechteverwaltung: Bugfix, Model auraeumen.
//
// Revision 1.1  2014/07/17 15:03:25  tw
// Bugfix f. Filter anlegen/aendern.
//
// Revision 1.9  2014/07/17 14:10:26  tw
// Bugfix: Combobox ff, Benutzerverwaltung.
//
// Revision 1.8  2014/07/16 14:27:58  tw
// Bugfix Benutzer aendern.
//
// Revision 1.7  2014/06/28 16:00:38  tw
// Benutzer bearbeiten finalisiert. Busy-Indicator f. Statistik u. Benutzer Bearbeiten.
//
// Revision 1.6  2014/06/12 15:36:23  tw
// Benutzer bearbeiten: Vorbereitung f. korrekte Gruppenverarbeitung.
//
// Revision 1.5  2014/06/12 12:29:20  tw
// Benutzer bearbeiten: Vorbereitung f. korrekte Gruppenverarbeitung.
//
// Revision 1.4  2014/06/05 15:35:29  tw
// Backup: Benutzer anlegen, aendern, loeschen.
//
// Revision 1.3  2014/06/01 12:50:26  tw
// Benutzer aendern, Grundstruktur.
//
// Revision 1.2  2014/05/30 22:21:44  tw
// Backup: Benutzer aendern.
//
// Revision 1.1  2014/05/12 16:53:15  tw
// Aufraeumarbeiten, Benutzer anlegen loeschen.
//
// Revision 1.6  2014/05/08 20:59:13  tw
// Bugfix: Fehlerhafte Treffermenge wenn Suchergebnis leer.
//
// Revision 1.5  2014/05/06 00:43:57  tw
// Backup: Benutzer anlegen.
//
// Revision 1.4  2014/05/03 00:05:57  tw
// Backup: Benutzer anlegen.
//
// Revision 1.3  2014/05/02 09:51:50  tw
// Backup: Benutzer anlegen.
//
// Revision 1.2  2014/04/28 21:57:52  tw
// Backup: Benutzer anlegen.
//
// Revision 1.1  2014/04/28 12:38:56  tw
// Backup: Benutzer anlegen.
//
//

package de.decodetron.tab.benutzerverwaltung;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.resource.JQueryPluginResourceReference;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPApplication;
import de.decodetron.Const;
import de.decodetron.bo.DataRecord;
import de.decodetron.bo.Group;
import de.decodetron.bo.User;
import de.decodetron.dao.user.UserDAOI;
import de.decodetron.event.ChangeEvent;
import de.decodetron.security.LoginSession;
import de.decodetron.security.pwd.DefaultPasswordChangeValidator;
import de.decodetron.tab.benutzerverwaltung.anlegen.DlgBenutzerAnlegen;
import de.decodetron.tab.benutzerverwaltung.anlegen.LoginCheckValidator;
import de.decodetron.tab.benutzerverwaltung.loeschen.DlgBenutzerLoeschen;
import de.decodetron.tab.statistik.TxtFilter;
import de.decodetron.util.CryptoTools;
import de.decodetron.util.Util;

/**
 * Gesamtkomponente für Benutzer Anlegen, Ändern, Löschen, Suchfelder.
 * 
 * @author Thomas Winter
 * @since 28.04.2014
 */
public class ViewBenutzerBearbeiten extends Panel {

    public static String MODULNAME = "bea"; // Fix in der DB vergeben!

    public ViewBenutzerBearbeiten(String id, IModel<?> model) {
        super(id, model);
        add(new ViewFilterBenutzer("viewFilter", model));
    }

    @Override
    public void renderHead(IHeaderResponse response) {

        // ///////////////////////
        // /// Fixierter Tabellenkopf ...
        // /
        response.render(CssHeaderItem.forReference(new CssResourceReference(ViewBenutzerBearbeiten.class,
                "../../../../css/tableDefaultTheme.css")));
        response.render(JavaScriptHeaderItem.forReference(new JQueryPluginResourceReference(
                ViewBenutzerBearbeiten.class, "../../../../js/jquery.fixedheadertable.js")));
        response.render(OnDomReadyHeaderItem.forScript("$.fn.initFixedTableHeader()"));
    }

    /**
     * Beim schnellen Klicken nach dem schliessen der Bearbeiten-Dialoges stellt man schnell fest,
     * dass die Buttons noch nicht fertig gerendert sind. Um dem Benutzer zu zeigen, dass sich die
     * Oberfläche neu Aufbaut, wird ein Busy-Indicator eingeblendet.
     */
    // public void renderHead(IHeaderResponse response) {
    // response.render(OnDomReadyHeaderItem.forScript("$.fn.hideViewBusy();"));
    // }

    /**
     * Überschriebenes Benutzerpanel
     */
    private BenutzerPanel benAendernPnl = new BenutzerPanel("ben-aendern-dlg", getDefaultModel()) {

        public void setTitle(String title) {
            super.setTitle("Benutzer ändern");
        };

        @Override
        public void onConfirmP(AjaxRequestTarget target) {
            target.add(ViewBenutzerBearbeiten.this);
        }

        @Override
        public void onCancelP(AjaxRequestTarget target) {
            // target.add(ViewBenAnlegenLoeschen.this);
            // target.appendJavaScript("$.fn.initFixedTableHeader()");
        }

        public void createFormValidationChecks(Form<?> formular, TextField<String> txtLogin,
                MyPasswordContainer txtPwd1, MyPasswordContainer txtPwd2) {
            txtPwd1.setVisible(false);
            txtPwd2.setVisible(false);
            // txtPwd1.setRequired(true);
            // txtPwd2.setRequired(true);
            ValueMap map = (ValueMap) formular.getDefaultModelObject();
            // User user = (User) map.get(Const.KEY_USER_2CHANGE);
            formular.add(new LoginCheckValidator(txtLogin));
            formular.add(new EqualPasswordInputValidator(txtPwd1.getTxtField(), txtPwd2.getTxtField()));
            formular.add(new DefaultPasswordChangeValidator(txtPwd1.getTxtField(), txtPwd2.getTxtField()));
        }

        @Override
        public void performDBAction(AjaxRequestTarget target, Form form) {

            ValueMap map = (ValueMap) form.getDefaultModelObject();

            User user = (User) map.get(Const.KEY_USER_2CHANGE);
            // String sID = map.getString("userID");
            String sVorname = map.getString("Vorname");
            String sNachname = map.getString("Nachname");
            String sLogin = map.getString("Login");
            String sPwdClear = map.getString("Passwort 1");
            String sFilter = map.getString("Filter");
            List<Group> groupList = (List<Group>) map.get("Gruppen");

            // user.setId(null); // ID NULL !
            user.setVorname(sVorname);
            user.setNachname(sNachname);
            user.setLogin(sLogin);
            String pwdNew = new CryptoTools().encryptOneWaySHA1(sPwdClear);
            user.setPasswd(!"".equals(sPwdClear) && !pwdNew.equals(user.getPasswd()) ? pwdNew : user.getPasswd());
            user.setFilter(getFilterItems(sFilter));
            user.setAnlagedatum(user.getAnlagedatum());
            user.setGroupIds(Util.getIdsFromGroup(groupList));
            // user.setVertriebsleitung("");
            // user.setGebiet("");

            AEPApplication.get().getDBUser().update(user);
            if (user.getId() != null) {
                target.appendJavaScript("$.fn.showUserChanged();");
            }
        }
    };

    public class ViewFilterBenutzer extends Form {

        private ViewListBenutzer viewListBenutzer = null;

        public ViewFilterBenutzer(String id, final IModel model) {

            super(id, model);
            add(createTxtSearchFields(model));
            add(viewListBenutzer = new ViewListBenutzer("viewList", model, ViewListBenutzer.CTX_BEN_ANLEGEN_AENDERN,
                    benAendernPnl.getModalWindow()));

            // ////////////////////////////////////////////////////
            // /// Anlegen über ein Hilfspanel
            // /
            DlgBenutzerAnlegen benAnlegenDlg = new DlgBenutzerAnlegen("ben-anlegen-dlg", model) {

                @Override
                public void onConfirmA(AjaxRequestTarget target) {
                    System.out.println("Ok");
                    moveLogin2SearchField(model);
                    target.add(ViewBenutzerBearbeiten.this);
                }

                @Override
                public void onCancelA(AjaxRequestTarget target) {
                    // target.add(ViewBenAnlegenLoeschen.this);
                    // target.appendJavaScript("$.fn.initFixedTableHeader()");
                    System.out.println("Cancel");
                }
            };
            add(benAnlegenDlg);

            // ////////////////////////////////////////////////////
            // /// Aendern ohne Hilfspanel
            // /
            add(benAendernPnl.getModalWindow());

            // ////////////////////////////////////////////////////
            // /// Löschen
            // /
            add(new DlgBenutzerLoeschen("ben-delete-dlg", model));
            add(new AjaxButton("start-search", this) {

                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

                    target.add(viewListBenutzer);
                    viewListBenutzer.initUserDeleteModel();
                    target.appendJavaScript("$.fn.initFixedTableHeader()");
                    new ChangeEvent(this, target, null, Const.ACTION_STAT_SUCHESTARTED).fire();
                }

                protected void onError(AjaxRequestTarget target, Form<?> form) {}
            });
        }

        /**
         * Überträgt das Login aus dem Anlegen-Fenster-Textfeld in die Suche.
         * 
         * @param model
         */
        private void moveLogin2SearchField(IModel model) {
            ValueMap map = (ValueMap) model.getObject();
            List<TxtFilter> list = (List<TxtFilter>) map.get(Const.KEY_LIST_FILTERSUCHE);
            String login = map.getString("Login");

            for (Iterator<TxtFilter> iterator = list.iterator(); iterator.hasNext();) {
                TxtFilter txtFilter = iterator.next();
                if ("dummyItem".equals(txtFilter.getId()) && "login".equalsIgnoreCase(txtFilter.getTblIdent())) {
                    txtFilter.setText(login);
                }
            }
        }

        private RepeatingView createTxtSearchFields(IModel model) {

            int colNr = 0;
            UserDAOI daoUser = AEPApplication.get().getDBUser();
            DataRecord tblHeader = daoUser
                    .getColumNamesAsDataRecord(LoginSession.get().getUser(), Const.TABLENAME_USER);

            ValueMap map = (ValueMap) model.getObject();
            map.put(Const.KEY_LIST_FILTERSUCHE, new ArrayList<TxtFilter>());
            List<TxtFilter> list = (List<TxtFilter>) map.get(Const.KEY_LIST_FILTERSUCHE);

            RepeatingView rv = new RepeatingView("repeating");
            while (colNr++ < tblHeader.getSize() - 1) {

                String tblName = tblHeader.getColItem(colNr);
                WebMarkupContainer c1 = new WebMarkupContainer(rv.newChildId());
                c1.add(new Label("label", new ResourceModel("label." + tblName, tblName)));
                TxtFilter txt = new TxtFilter("dummyItem", tblName);
                txt.setEnabled("passwd".equals(tblName) ? false : true);
                c1.add(txt);
                rv.add(c1);
                list.add(txt);
            }

            return rv;
        }
    }

}
