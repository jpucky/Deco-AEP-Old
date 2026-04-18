// $Log: ViewContResetPwd.java,v $
// Revision 1.6  2016/06/28 19:36:23  tw
// BUFIX 3963: Benutzerverwaltung/Benutzer-DB =>  zusaetzliche Felder.
//
// Revision 1.5  2015/02/12 00:58:08  tw
// Haldenbearbeitung: Grossflaechiger Skriptumbau. Zwangsumstellung auf JQuery 1.7.1.
//
// Revision 1.4  2014/08/07 00:15:10  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.3  2014/06/12 12:29:20  tw
// Benutzer bearbeiten: Vorbereitung f. korrekte Gruppenverarbeitung.
//
// Revision 1.2  2014/06/05 15:35:29  tw
// Backup: Benutzer anlegen, aendern, loeschen.
//
// Revision 1.1  2014/05/12 16:34:41  tw
// Aufraeumarbeiten, Benutzer anlegen loeschen.
//
// Revision 1.9  2014/05/08 20:59:13  tw
// Bugfix: Fehlerhafte Treffermenge wenn Suchergebnis leer.
//
// Revision 1.8  2014/04/29 20:33:19  tw
// Backup: Benutzer anlegen.
//
// Revision 1.7  2014/04/28 21:57:52  tw
// Backup: Benutzer anlegen.
//
// Revision 1.6  2014/03/01 23:57:13  tw
// Listencache ueberarbeitet.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.5  2014/02/25 16:55:43  tw
// Bugfix zuruecksetzten der Eingabefelder.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.4  2014/02/25 16:21:41  tw
// Pwd-Feld disabled.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.3  2014/02/15 11:58:48  tw
// Implementierung: Passwort zuruecksetzen.
//
// Revision 1.2  2014/02/14 17:03:24  tw
// Implementierung: Passwort zuruecksetzen.
//
// Revision 1.1  2014/02/13 02:03:19  tw
// Benutzerverwaltung, dynamischer Suchfilteraufbau.
//
//

package de.decodetron.tab.benutzerverwaltung.pwdreset;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.resource.JQueryPluginResourceReference;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.AEPApplication;
import de.decodetron.AMarkupProvider;
import de.decodetron.Const;
import de.decodetron.bo.DataRecord;
import de.decodetron.bo.User;
import de.decodetron.data.Util;
import de.decodetron.dlgcomponents.ModalWindowUser;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.benutzerverwaltung.BenutzerPanel;
import de.decodetron.tab.benutzerverwaltung.ViewListBenutzer;
import de.decodetron.tab.statistik.TxtFilter;

/**
 * Benutzerpasswort - Ändern - Ansicht.
 * 
 * @author Thomas Winter
 * @since 12.02.2014
 */
public class ViewContResetPwd extends AMarkupProvider {

    public void renderHead(IHeaderResponse response) {

        // ///////////////////////
        // /// Fixierter Tabellenkopf ...
        // /
        response.render(CssHeaderItem.forReference(new CssResourceReference(ViewContResetPwd.class,
                "../../../../../css/tableDefaultTheme.css")));
        response.render(JavaScriptHeaderItem.forReference(new JQueryPluginResourceReference(ViewContResetPwd.class,
                "../../../../../js/jquery.fixedheadertable.js")));
        response.render(OnDomReadyHeaderItem.forScript("$.fn.initFixedTableHeader()"));

        // Damit der Suchbutton die Korrekten ids ermittelt, siehe, init.js.
        response.render(OnDomReadyHeaderItem.forScript("$.fn.initSearchButtonSelector()"));
    }

    public ViewContResetPwd(String id, final IModel<?> model) {

        super(id, model);
        setOutputMarkupId(true);

        Form<List<User>> form = new Form<List<User>>("viewFilter");

        Button brnPwdReset = new Button("btnpwdreset") {

            @Override
            public void onSubmit() {

                ValueMap map = (ValueMap) model.getObject();
                // List<User> userList = (List<User>) map.get(Const.KEYUSERLIST);
                @SuppressWarnings("unchecked")
                Set<User> changedUserList = (Set<User>) map.get(Const.KEY_USER_2CHANGE);

                for (Iterator<User> iterator = changedUserList.iterator(); iterator.hasNext();) {
                    User user = iterator.next();
                    AEPApplication.get().getDBUser().update(user);
                }

                changedUserList.clear();
            }
        };
        brnPwdReset.add(AttributeModifier.append("class", "pwdreset invisible"));
        brnPwdReset.setDefaultFormProcessing(false);
        form.add(brnPwdReset);

        form.add(new ViewListBenutzer("viewList", model, ViewListBenutzer.CTX_RESET_PWD, null));
        form.add(new AjaxButton("start-search", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                target.add(ViewContResetPwd.this);
                target.appendJavaScript("$.fn.initFixedTableHeader()");
            }
        });

        int colNr = 0;
        ValueMap map = (ValueMap) model.getObject();
        map.put(Const.KEY_LIST_FILTERSUCHE, new ArrayList<TxtFilter>());

        /**
         * Suchfelder ...
         */
        DataRecord tblHeader = AEPApplication.get().getDBUser().getColumNamesAsDataRecord(Const.TABLENAME_USER);
        while (colNr++ < tblHeader.getSize() - 1) {
            String tblName = tblHeader.getColItem(colNr);
            if (Util.fieldExist(LoginSession.get().getUser().getClass(), tblName)) {
                TxtFilter txt = new TxtFilter(tblName, form);
                List<TxtFilter> list = (List<TxtFilter>) map.get(Const.KEY_LIST_FILTERSUCHE);
                list.add(txt);
                form.add(txt);
            }
        }

        add(form);
    }

    @Override
    public String getMyMarkup() {
        StringBuffer sb = new StringBuffer();
        // sb.append("<wicket:panel>");

        sb.append("<script type=\"text/javascript\">");
        sb.append("        $(document).ready(function() {");
        sb.append("$(\".deletesearch a\").click(function(){");
        sb.append("$(\".switchpanel input[type=text]\").val('');");
        sb.append("});  ");
        sb.append("}); ");
        sb.append("</script>");

        sb.append("<form action=\"forms.html\" method=\"post\" class=\"search searchForm1\" wicket:id=\"viewFilter\">");
        sb.append("<fieldset>");
        sb.append("<div class=\"switchpanel\">");

        sb.append("<div class=\"headline1\">");
        sb.append("<div>Suchbegriffe</div>");
        sb.append("<a href=\"#\"></a>");
        sb.append("</div>");

        DataRecord tblHeader = AEPApplication.get().getDBUser().getColumNamesAsDataRecord(Const.TABLENAME_USER);
        int colNr = 0;
        while (colNr < tblHeader.getSize() - 1) {
            colNr++;
            String tblName = tblHeader.getColItem(colNr);
            if (Util.fieldExist(LoginSession.get().getUser().getClass(), tblName)) {
                sb.append("<label><wicket:message key=\"label.").append(tblName).append("\"/></label>").append("\n");
                sb.append("passwd".equals(tblName) ? "<input disabled=\"disabled\"" : "<input ");
                sb.append("type=\"text\" value=\"\" wicket:id=\"").append(tblName).append("\"/>");
                sb.append("\n");
            }
        }

        sb.append("</div>");// switchpanel
        sb.append("<input type=\"submit\" value=\"SUCHE STARTEN\" class=\"search\" wicket:id=\"start-search\"/>");
        sb.append("<input type=\"submit\" wicket:id=\"btnpwdreset\" value=\"Passwort Reset!\"/>");
        sb.append("<div class=\"deletesearch fontheader\"><a href=\"#\">Suchbegriffe löschen</a></div>");
        sb.append("</fieldset>");

        sb.append("<div class=\"view\">");
        sb.append("    <div class=\"viewbusy\"></div>");
        sb.append("    <span wicket:id=\"viewList\"/>");
        sb.append("</div>");

        sb.append("</form>");
        // sb.append("</wicket:panel>");

        return sb.toString();
    }

}
