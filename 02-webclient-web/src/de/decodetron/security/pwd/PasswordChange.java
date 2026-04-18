// $Log: PasswordChange.java,v $
// Revision 1.10  2020/02/09 15:31:10  tw
// Argh. Umlaute!
//
// Revision 1.9  2020/02/09 15:06:30  tw
// CR: Passort-aendern Dialog: Das Standartpasswort muss geaendert werden, ist keine Empfehlung mehr.
//
// Revision 1.8  2014/10/23 21:03:20  tw
// Header-Security Anpassung.
//
// Revision 1.7  2014/05/27 11:56:29  tw
// Login-Begrenzer f. fehlerhafte Anmeldung implementiert.
//
// Revision 1.6  2014/02/21 01:31:31  tw
// Skriptfehler Passwort zuruecksezten beseitigt.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.5  2014/02/11 20:33:27  tw
// Umlaute.
//
// Revision 1.4  2014/02/11 20:29:37  tw
// Bugfix Return-Consumption fuer alle Inputfelder.
//
// Revision 1.3  2014/02/11 16:30:03  tw
// Bestaetigungsdialog f. Passwortaenderung.
//
// Revision 1.2  2014/02/11 02:20:08  tw
// Zwangsteuerung f. Standartpasswortaenderung implementiert - entschaerft.
//
// Revision 1.1  2014/02/11 02:10:23  tw
// Zwangsteuerung f. Standartpasswortaenderung implementiert.
//
// Revision 1.6  2014/01/29 09:49:30  tw
// Vorbereitung: Belegart: EK / Benutzerverwaltung.
//
// Revision 1.5  2013/11/15 21:48:03  tw
// Tabellenspalten werden per seperater DB-Funktion abgerufen.
//
// Revision 1.4  2013/11/15 12:40:48  tw
// Passwort aendern Dialog erstellt.
//
// Revision 1.3  2013/11/15 09:27:52  tw
// Reimporte u. Chargen ans Berechtigungssystem angeflanscht.
//
// Revision 1.2  2013/11/14 23:20:28  tw
// .
//
// Revision 1.1  2013/11/14 23:06:18  tw
// Passwort-andern reingerotzt
//
//

package de.decodetron.security.pwd;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.resource.JQueryPluginResourceReference;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;

import de.decodetron.AEPApplication;
import de.decodetron.Const;
import de.decodetron.MainPage;
import de.decodetron.SecureBasePage;
import de.decodetron.bo.User;
import de.decodetron.dao.user.UserDAOI;
import de.decodetron.security.AuthenticatedWebPage;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.statistik.TabStatistik;
import de.decodetron.util.CryptoTools;

/**
 * @author Thomas Winter
 * @since 14.11.2013
 */
public class PasswordChange extends SecureBasePage implements AuthenticatedWebPage {

    private final String PASSWORD_PATTERN = ".*";    
    
    public PasswordChange(final PageParameters parameters) {
        
        final String pageType = parameters.get("param").toString();
        
        final PasswordTextField password1 = new PasswordTextField("Passwort1", Model.of(""));
        final PasswordTextField password2 = new PasswordTextField("Passwort2", Model.of(""));
        
        password1.setResetPassword(true);
        password2.setResetPassword(true);        

        password1.add(new PatternValidator(PASSWORD_PATTERN));
        password1.add(StringValidator.minimumLength(6));
        password2.add(StringValidator.minimumLength(6));
        
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        add(feedback);

        final Form<Void> fform = new Form<Void>("userForm");        
        fform.add(password1);
        fform.add(password2);
        fform.add(new EqualPasswordInputValidator(password1, password2));
        fform.add(new DefaultPasswordChangeValidator(password1, password2));
        fform.add(AttributeModifier.replace("autocomplete", "off"));

        // ////////////////////////////////////////////////////////////////
        // /// Problem: Der Button löst VOR onSubmit aus und dient daher nicht als Bestätigung.
        // /
        // btnOK.add(AttributeModifier.append("onclick",
        // "if(!confirm('Do you really want to perform this action?')) return false;"));
        fform.add(new AjaxButton("btnConfirm", fform) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                changePwd(target, password1);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedback);
            }
        });

        Button cancel = new Button("cancelbutton") {
            public void onSubmit() {
                
                if(pageType != null && pageType.equals(Const.PWD_CHANGE_SOFT)){
                    setResponsePage(MainPage.class);   
                }else if(pageType != null && pageType.equals(Const.PWD_CHANGE_FORCE)){
                    LoginSession.get().invalidate();
                }else{
                    // Default ?
                    LoginSession.get().invalidate();
                }
            }
        };
        cancel.setDefaultFormProcessing(false);
        fform.add(cancel);

        add(fform);
    }

    private void changePwd(AjaxRequestTarget target, PasswordTextField password1){
        String newPassword = password1.getModelObject();
        User u = LoginSession.get().getUser(); 
        UserDAOI dao = AEPApplication.get().getDBUser();
        dao.changePassword(u.getId(), newPassword);
        u.setPasswd(new CryptoTools().encryptOneWaySHA1(newPassword));
        target.appendJavaScript(
        	    "alert('Passwort erfolgreich geändert');" +
        	    "setTimeout(function() {" +
        	    "  window.location.href = '" + urlFor(MainPage.class, null) + "';" +
        	    "}, 200);"
        	);
    }

}
