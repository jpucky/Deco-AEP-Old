// $Log: BenutzerPanel.java,v $
// Revision 1.18  2018/07/03 20:52:07  tw
// UPDATE: Die Textfilterlaenge angepasst auf alten Wert angepasst.
//
// Revision 1.17  2018/07/01 11:57:16  tw
// Benutzer anlegen/bearbeiten. Im Filter werden Zeichen der Wortgruppe zugelassen (\w Regulaere Ausdruecke).
//
// Revision 1.16  2015/06/21 11:43:32  tw
// BF Feng-ID: 3951
//
// Revision 1.15  2014/09/12 20:40:58  tw
// Aep-Benutzerr-Rechteverwaltung: Setzten des Filters.
//
// Revision 1.14  2014/08/21 22:04:51  tw
// Aep-Benutzerr-Rechteverwaltung: Layout.
//
// Revision 1.13  2014/07/17 15:03:25  tw
// Bugfix f. Filter anlegen/aendern.
//
// Revision 1.12  2014/07/17 14:19:40  tw
// Bugfix: Combobox ff, Benutzerverwaltung.
//
// Revision 1.11  2014/07/17 14:10:26  tw
// Bugfix: Combobox ff, Benutzerverwaltung.
//
// Revision 1.10  2014/07/16 14:27:58  tw
// Bugfix Benutzer aendern.
//
// Revision 1.9  2014/06/28 16:00:38  tw
// Benutzer bearbeiten finalisiert. Busy-Indicator f. Statistik u. Benutzer Bearbeiten.
//
// Revision 1.8  2014/06/17 11:55:36  tw
// Benutzer bearbeiten,  Passwortaenderung, Bugfix.
//
// Revision 1.7  2014/06/17 11:51:33  tw
// Benutzer bearbeiten,  Passwortaenderung, Bugfix.
//
// Revision 1.6  2014/06/14 12:52:32  tw
// Anbindung d. Checkbox f. Standartpasswort.
//
// Revision 1.5  2014/06/13 21:10:13  tw
// Layout: Checkbox f. Standartpasswort.
//
// Revision 1.4  2014/06/12 15:36:23  tw
// Benutzer bearbeiten: Vorbereitung f. korrekte Gruppenverarbeitung.
//
// Revision 1.3  2014/06/12 12:29:20  tw
// Benutzer bearbeiten: Vorbereitung f. korrekte Gruppenverarbeitung.
//
// Revision 1.2  2014/06/05 15:35:28  tw
// Backup: Benutzer anlegen, aendern, loeschen.
//
// Revision 1.1  2014/06/01 12:50:26  tw
// Benutzer aendern, Grundstruktur.
//
//

package de.decodetron.tab.benutzerverwaltung;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.value.ValueMap;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;

import de.decodetron.AEPApplication;
import de.decodetron.Const;
import de.decodetron.bo.Group;
import de.decodetron.bo.User;
import de.decodetron.dlgcomponents.ConfirmationAnswer;
import de.decodetron.dlgcomponents.ModalWindowUser;
import de.decodetron.dlgcomponents.SuperAdminLabeledDropDown;
import de.decodetron.event.AbstractEvent;
import de.decodetron.event.ChangeEvent;
import de.decodetron.event.EventListenerInterface;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.benutzerverwaltung.anlegen.LoginCheckValidator;
import de.decodetron.util.CryptoTools;
import de.decodetron.util.Util;

/**
 * Panel für Benutzer anlegen, ändern - Aktionen.
 * 
 * @author Thomas Winter
 * @since 31.05.2014
 */
public class BenutzerPanel extends Panel {

    private ModalWindowUser modalWindow;
    private ConfirmationAnswer answer_;
    private String PATTERN_PASSWORD = ".*";
    private String PATTERN_FILTER = "(\\*?)|(\\,?\\w+\\,?\\s?)+";

    // public BenutzerPanel(String id, final ModalWindow modalWindow, IModel model) {
    public BenutzerPanel(String idModal, IModel model) {

        super(ModalWindow.CONTENT_ID, model);

        modalWindow = new ModalWindowUser(idModal, getDefaultModel());
        modalWindow.setContent(BenutzerPanel.this);

        answer_ = new ConfirmationAnswer(false);
        final Form<?> formular = new Form<ValueMap>("yesNoForm", model);
        formular.setOutputMarkupId(true);

        TextField<String> txtVorname = new TextField<String>("Vorname", String.class);
        TextField<String> txtNachname = new TextField<String>("Nachname", String.class);
        TextField<String> txtLogin = new TextField<String>("Login", String.class);
        // final MyPasswordTextField txtPwd1 = new MyPasswordTextField("Passwort 1");
        final MyPasswordContainer txtPwd1 = new MyPasswordContainer("pwd1Container", "Passwort 1");
        // final MyPasswordTextField txtPwd2 = new MyPasswordTextField("Passwort 2");
        final MyPasswordContainer txtPwd2 = new MyPasswordContainer("pwd2Container", "Passwort 2");

        TextField<String> txtFilter = new TextField<String>("Filter", String.class);

        // Label lblVertriebsleitung = new SuperAdminLabel("lblVertriebsleitung",
        // Model.of("Vertriebsleitung"));
        // Label lblGebiet = new SuperAdminLabel("lblGebiet", Model.of("Gebiet"));
        ListMultipleChoice<Group> ddGroups = new ListMultipleChoice<Group>("Gruppen", modelGroups, rendererGroupList);
        // DropDownChoice<Group> ddVertriebsleitung = new
        // SuperAdminDropDown<Group>("Vertriebsleitung");
        // ddVertriebsleitung.setEnabled(false); // TODO: Langfristige Baustelle!
        MarkupContainer lblDDVertrieb = new SuperAdminLabeledDropDown("lblCompDDVertrieb", "Vertriebsleitung");
        MarkupContainer lblDDGebiet = new SuperAdminLabeledDropDown("lblCompDDGebiet", "Gebiet");
        // DropDownChoice<Group> ddGebiet = new SuperAdminDropDown<Group>("Gebiet");
        // ddGebiet.setEnabled(false);// TODO: Langfristige Baustelle!

        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        feedback.setEscapeModelStrings(false);
        feedback.setOutputMarkupId(true);

        txtVorname.setRequired(true);
        txtNachname.setRequired(true);
        txtLogin.setRequired(true);
        // txtPwd1.setRequired(true);
        // txtPwd2.setRequired(true);
        txtFilter.setRequired(true);
        ddGroups.setRequired(true); // bewirkt nix!
        ddGroups.add(new MMChoiceValidator());

        txtVorname.add(StringValidator.maximumLength(50));
        txtNachname.add(StringValidator.maximumLength(50));
        txtLogin.add(StringValidator.maximumLength(50));
        txtPwd1.add(StringValidator.maximumLength(50));
        txtPwd2.add(StringValidator.maximumLength(50));
        // https://www.sqlite.org/limits.html => 3. Maximum Length Of An SQL Statement
        txtFilter.add(StringValidator.maximumLength(100000)); // Durch die Vertriebsleiter kann das sehr lang werden!

        // txtPwd1.add(new PatternValidator(PATTERN_PASSWORD));
        txtFilter.add(new PatternValidator(PATTERN_FILTER) {
            @Override
            protected ValidationError decorate(ValidationError error, IValidatable<String> validatable) {
                return (new ValidationError()
                        .addKey(LoginSession.get().getUser().getIsSuperAdmin() ? "ben.filter.error.superuser"
                                : "ben.filter.error.adminuser"));
            }
        });

        formular.add(txtVorname);
        formular.add(txtNachname);
        formular.add(txtLogin);
        formular.add(txtPwd1);
        formular.add(txtPwd2);
        formular.add(txtFilter);
        formular.add(ddGroups);
        formular.add(lblDDVertrieb);
        formular.add(lblDDGebiet);
        // formular.add(lblVertriebsleitung);
        // formular.add(lblGebiet);
        // formular.add(ddGebiet);
        formular.add(feedback);

        // Ausgelagerte Validator - Prüfungen um ein Überschreiben zu ermöglichen.
        createFormValidationChecks(formular, txtLogin, txtPwd1, txtPwd2);
        formular.add(new MyPasswordResetCheckBox("defaultpwdbx", txtPwd1, txtPwd2));

        // MultiLineLabel messageLabel = new MultiLineLabel("message", "Wollen Sie ...");
        // yesNoForm.add(messageLabel);
        setTitle("Benutzer anlegen");

        AjaxButton yesButton = new AjaxButton("yesButton", formular) {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form form) {
                if (target != null) {
                    performDBAction(target, formular);
                    answer_.setAnswer(true);
                    // onConfirmP(target);
                    modalWindow.close(target);
                    // Hauptfenster aktualisieren ?
                    // target.appendJavaScript("window.top.location=" + urlFor(MainPage.class,
                    // null));
                    target.appendJavaScript("$.fn.showViewBusy();");
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedback);
            }
        };

        AjaxButton noButton = new AjaxButton("noButton", formular) {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form form) {
                if (target != null) {
                    formular.clearInput();
                    answer_.setAnswer(false);
                    // onCancelP(target);
                    modalWindow.close(target);
                }
            }
        };

        /**
         * Das Auslösen der confirm- u. cancel Buttons innherhalb der Ajax-Buttons führt,
         * insbesondere beim Cancel zu einer seltsamen Verzögerung die nicht nur nervig ist, sondern
         * auch sporadische Fehlermeldungen verursacht hat. Nur so funktioniert das korrekt ...
         */
        modalWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {

            @Override
            public void onClose(AjaxRequestTarget target) {
                if (getAnswer().isAnswer()) {
                    onConfirmP(target);
                } else {
                    onCancelP(target);
                }
            }

        });

        noButton.setDefaultFormProcessing(false);
        formular.add(yesButton);
        formular.add(noButton);

        add(formular);
    }

    /**
     * Wahrscheinlich bedingt durch den Modalen Dialog, merkt sich diese dämliche Checkbox den
     * zuletzt gewählten Zustand, auch bei einem Abbruch des Dialoges. Dieser Effekt ist nicht
     * erwünscht und ich hab es nur durch das Lauschen auf das Event geschafft, diesem Ding den
     * richtigen Initialwert zu verpassen.
     */
    private class MyPasswordResetCheckBox extends AjaxCheckBox implements EventListenerInterface {

        private MyPasswordContainer txtPwd1;
        private MyPasswordContainer txtPwd2;

        public MyPasswordResetCheckBox(String id, MyPasswordContainer p1, MyPasswordContainer p2) {
            super(id, Model.of(Boolean.TRUE));
            txtPwd1 = p1;
            txtPwd2 = p2;
        }

        protected void onUpdate(AjaxRequestTarget target) {
            Boolean useDefaultPwd = getModelObject();
            new ChangeEvent(this, target, useDefaultPwd, Const.DEFAULTPWDSET).fire();

            // txtPwd1.setEnabled(true);
            // txtPwd2.setEnabled(true);

            // Beim Rückbau des MarkupContainers wieder aktivieren!
            if (txtPwd1.isVisible()) {
                target.add(txtPwd1);
            }

            if (txtPwd2.isVisible()) {
                target.add(txtPwd2);
            }
        }

        @Override
        public void notifyAjaxEvent(AbstractEvent event) {
            if (event instanceof ChangeEvent) {
                ChangeEvent ce = ((ChangeEvent) event);
                if (Const.DEFAULTPWDSET.equals(ce.getIdentifier())) {
                    ValueMap map = (ValueMap) BenutzerPanel.this.getDefaultModelObject();
                    Boolean b = (Boolean) map.get(Const.KEY_BENW_RESETPWD);
                    MyPasswordResetCheckBox.this.setModelObject(b);
                }
            }
        }
    }

    public class MyPasswordContainer extends MarkupContainer {

        private MyPasswordTextField pwd;

        public MyPasswordContainer(String id, String idpwdField) {
            super(id);
            setOutputMarkupId(true);
            add(pwd = new MyPasswordTextField(idpwdField));
            pwd.add(new PatternValidator(PATTERN_PASSWORD));
            pwd.setRequired(true);
        }

        public PasswordTextField getTxtField() {
            return pwd;
        }
    }

    /**
     * Das Passworttextfeld lauscht auf das Setzten des Events.
     */
    private class MyPasswordTextField extends PasswordTextField implements EventListenerInterface {

        public MyPasswordTextField(String id) {
            super(id);
            MyPasswordTextField.this.setOutputMarkupId(true);
            setResetPassword(false);

            ValueMap map = (ValueMap) BenutzerPanel.this.getDefaultModelObject();
            map.put("Passwort 1", Const.STANDARTPWD);
            map.put("Passwort 2", Const.STANDARTPWD);
            // setDefaultModel(Model.of(Const.STANDARTPWD)); //
            // setModel(Model.of(Const.STANDARTPWD)); //
        }

        private void updateDefaultPwd(ChangeEvent ce) {
            Boolean defaultPwdSet = (Boolean) ce.getChange();
            // setDefaultModel(defaultPwdSet ? Model.of(Const.STANDARTPWD) : Model.of(""));
            ValueMap map = (ValueMap) BenutzerPanel.this.getDefaultModelObject();
            map.put(Const.KEY_BENW_RESETPWD, defaultPwdSet);
            map.put("Passwort 1", defaultPwdSet ? Const.STANDARTPWD : "");
            map.put("Passwort 2", defaultPwdSet ? Const.STANDARTPWD : "");

            // Führt zu einer Fehlermeldung im Debugger:
            // "... . Make sure you called component.setOutputMarkupId(true) ...", obwohl dies hier
            // geschieht!?
            // ce.update(MyPasswordTextField.this);
        }

        @Override
        public void notifyAjaxEvent(AbstractEvent event) {

            if (event instanceof ChangeEvent) {
                ChangeEvent ce = ((ChangeEvent) event);
                if (Const.DEFAULTPWDSET.equals(ce.getIdentifier())) {
                    updateDefaultPwd(ce);
                }
            }
        }
    }

    /**
     * Prüfung, ob die Gruppe gesetzt ist. Es muss eine Gruppe gewählt werden. Das Feld
     * setRequire(true) funktioniert für diese Komponente nicht.
     */
    @SuppressWarnings("rawtypes")
    private class MMChoiceValidator implements IValidator {
        public void validate(IValidatable validatable) {
            List<Group> l = (List<Group>) validatable.getValue();
            if (l.size() == 0) {
                ValidationError error = new ValidationError();
                error.addKey("ben.mm.group.empty");
                validatable.error(error);
            }
        }
    }

    /**
     * Erzeugt alle Prüfungen, die am Formular direkt kleben.
     * 
     * @param Form
     *            form
     * @return Form
     */
    public void createFormValidationChecks(Form<?> formular, TextField<String> txtLogin, MyPasswordContainer txtPwd1,
            MyPasswordContainer txtPwd2) {
        formular.add(new LoginCheckValidator(txtLogin));
        formular.add(new EqualPasswordInputValidator(txtPwd1.getTxtField(), txtPwd2.getTxtField()));
        // formular.add(new DefaultPasswordChangeValidator(txtPwd1, txtPwd2));
    }

    public void setTitle(String title) {
        modalWindow.setTitle(title);
    }

    public ModalWindowUser getModalWindow() {
        return modalWindow;
    }

    /**
     * Methode wird ausgelöst und muss bei Bedarf überschrieben werden.
     * 
     * @param AjaxRequestTarget
     *            target
     */
    public void onConfirmP(AjaxRequestTarget target) {
        // ...
    }

    /**
     * Methode wird ausgelöst und muss bei Bedarf überschrieben werden.
     * 
     * @param AjaxRequestTarget
     *            target
     */
    public void onCancelP(AjaxRequestTarget target) {
        // ...
    }

    public void performDBAction(AjaxRequestTarget target, Form form) {

        ValueMap map = (ValueMap) form.getDefaultModelObject();
        String sVorname = map.getString("Vorname");
        String sNachname = map.getString("Nachname");
        String sLogin = map.getString("Login");
        String sPwdClear = map.getString("Passwort 1");
        String sFilter = map.getString("Filter");
        List<Group> groupList = (List<Group>) map.get("Gruppen");

        User user = new User();
        user.setId(null); // ID NULL !
        user.setVorname(sVorname);
        user.setNachname(sNachname);
        user.setLogin(sLogin);
        user.setPasswd(new CryptoTools().encryptOneWaySHA1(sPwdClear));
        user.setFilter(getFilterItems(sFilter));
        user.setAnlagedatum(new Timestamp(System.currentTimeMillis()).toString());
        user.setGroupIds(Util.getIdsFromGroup(groupList));
        // user.setVertriebsleitung("");
        // user.setGebiet("");

        AEPApplication.get().getDBUser().insert(user);
        if (user.getId() != null) {
            target.appendJavaScript("$.fn.showUserCreated();");
        }
    }

    /**
     * Intitialisiert den Dialog. Null setzt die Felder zurück.
     * 
     * @param User
     *            u
     */
    public void initInputDlg(User u) {

        ValueMap map = (ValueMap) BenutzerPanel.this.getDefaultModelObject();
        map.put("Vorname", u == null ? null : u.getVorname());
        map.put("Nachname", u == null ? null : u.getNachname());
        map.put("Login", u == null ? null : u.getLogin());
        // map.put("Passwort 1", u == null ? null : u.getPasswd());
        // map.put("Passwort 2", u == null ? null : u.getPasswd());
        map.put("Filter", u == null ? null : u.getFilter());
        map.put("Gruppen", u == null ? null : u.getGroupIds());

        // Die dämliche Checkbox lässt sich nur so überreden ...
        map.put(Const.KEY_BENW_RESETPWD, Boolean.TRUE);
        new ChangeEvent(this, null, map.get(Const.KEY_BENW_RESETPWD), Const.DEFAULTPWDSET).fire();
    }

    public ConfirmationAnswer getAnswer() {
        return answer_;
    }

    /**
     * 'Reinigt' die Benutzereingaben von unerwünschtem Kram und übernimmt nur die Zahlenwerte als
     * Filter.<br>
     * <br>
     * 
     * 01.07.2018, Bugfix: Nachdem AEP sowas: "23745_ESS" als Filter anlegen wollte, war das nicht
     * möglich. Es werden Zeichen der Wortgruppe zugelassen.
     * 
     * @param String
     *            filterInput
     * @return String
     */
    public String getFilterItems(String filterInput) {
        int cnt = 0;
        StringBuilder sb = new StringBuilder();
        // Pattern patternDate = Pattern.compile("(\\d+)|(\\*)");// Nur die Zahlen oder *
        // rausfischen
        Pattern patternDate = Pattern.compile("(\\w+)|(\\*)");// Nur Wörter oder * rausfischen
        Matcher matcher = patternDate.matcher(filterInput);
        while (matcher.find()) {
            if (cnt > 0) {
                sb.append(",");
            }
            sb.append(matcher.group());
            cnt++;
        }
        return sb.toString();
    }

    IModel<List<? extends Group>> modelGroups = new AbstractReadOnlyModel<List<? extends Group>>() {
        @Override
        @SuppressWarnings("unused")
        public List<Group> getObject() {
            User u = LoginSession.get().getUser();
            List<Group> model = AEPApplication.get().getDBUser().getAllGroups(u);
            if (model == null) {
                model = Collections.emptyList();
            }
            return model;
        }
    };

    IChoiceRenderer<Group> rendererGroupList = new IChoiceRenderer<Group>() {

        @Override
        public String getIdValue(Group object, int index) {
            return String.valueOf(object.getId());
        }

        @Override
        public String getDisplayValue(Group object) {
            return object.getName();
        }
    };
}
