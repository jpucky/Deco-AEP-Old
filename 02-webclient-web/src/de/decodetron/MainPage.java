// $Log: MainPage.java,v $
// Revision 1.65  2020/02/09 15:31:09  tw
// Argh. Umlaute!
//
// Revision 1.64  2020/02/09 15:06:29  tw
// CR: Passort-aendern Dialog: Das Standartpasswort muss geaendert werden, ist keine Empfehlung mehr.
//
// Revision 1.63  2017/07/04 01:07:31  tw
// Mobilmachung der Headrevision. Beseitigung des PDF-Speicherlecks.
//
// Revision 1.62  2016/02/05 15:47:50  tw
// CR 3956: Interner Umbau: Events separiert.
//
// Revision 1.61  2016/02/05 15:24:31  tw
// CR 3956: Interner Umbau: Events separiert.
//
// Revision 1.60  2015/04/13 12:10:45  tw
// CR Feng-ID: 3950#1
//
// Revision 1.59  2015/03/22 00:53:02  tw
// Anzeige des Changelogs fuer Superadmins.
//
// Revision 1.58  2015/02/11 14:34:14  tw
// Bugfix: Feng-ID: 3945. Apotheker duerfen ihr Passwort nicht aendern.
//
// Revision 1.57  2015/01/30 02:44:51  tw
// Haldenbearbeitung 1. Wurf.
//
// Revision 1.56  2014/10/23 21:03:19  tw
// Header-Security Anpassung.
//
// Revision 1.55  2014/10/14 16:32:02  tw
// Haldenstatus, Anpassung an csv.
//
// Revision 1.54  2014/10/03 21:52:11  tw
// Implementierung: Haldenstatus.
//
// Revision 1.53  2014/08/18 12:34:34  tw
// Bugfix: Aep-Benutzer-Rechteverwaltung.
//
// Revision 1.52  2014/08/08 16:05:02  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.51  2014/03/20 02:22:22  tw
// Recherche: Paginierung, Funktionstest. Event-Konstanten zusammengefasst.
//
// Revision 1.50  2014/03/18 02:22:55  tw
// Recherche: Paginierung, Styleanbindung, Funktionstest.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.49  2014/02/21 00:44:01  tw
// Statistik-Schnittstellen implementiert.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.48  2014/02/11 16:30:02  tw
// Bestaetigungsdialog f. Passwortaenderung.
//
// Revision 1.47  2014/02/11 02:10:22  tw
// Zwangsteuerung f. Standartpasswortaenderung implementiert.
//
// Revision 1.46  2014/02/07 02:07:05  tw
// Restzeitaktualisierung bei Aktivitaeten implementiert.
//
// Revision 1.45  2014/02/02 23:26:13  tw
// Implementierung einer Uebersicht aller angemeldeten Benutzer.
//
// Revision 1.44  2014/02/02 23:22:08  tw
// Implementierung einer Uebersicht aller angemeldeten Benutzer.
//
// Revision 1.43  2013/11/14 23:20:28  tw
// .
//
// Revision 1.42  2013/11/14 23:05:55  tw
// Passwort-andern reingerotzt
//
// Revision 1.41  2013/11/09 19:18:06  tw
// Versionsnummer wieder rangeschafft.
//
// Revision 1.40  2013/11/02 00:51:32  tw
// Statistik-Modul, erster Wurf implementiert.
//
// Revision 1.39  2013/10/30 12:23:00  tw
// Anbindung eines Tab-Reiters.
//
// Revision 1.38  2013/10/29 16:06:56  tw
// - aep user im import.sql integriert.
// - billig-baumdarstellung implementiert.
//
// Revision 1.37  2013/10/27 22:28:38  tw
// Einfuehrung einer Buildnumber.
//
// Revision 1.36  2013/10/27 20:28:38  tw
// Umlaute
//
// Revision 1.35  2013/10/27 17:42:28  tw
// PDF-Ablage geandert. Layoutanpassung: PDF-Ansicht als Overflow:hidden.
//
// Revision 1.34  2013/10/27 17:25:40  tw
// PDF-Ablage geÃ¤ndert. Layoutanpassung: PDF-Ansicht als Overflow:hidden.
//
// Revision 1.33  2013/10/25 17:43:19  tw
// Zeitmessung eingebaut.
//
// Revision 1.32  2013/10/25 11:34:34  tw
// Logausgaben f. An- und Abmeldung.
//
// Revision 1.31  2013/10/25 11:17:10  tw
// Bugfixing f. Rollout.
//
// Revision 1.30  2013/10/25 00:28:37  tw
// Anpassung an Rollout.
//
// Revision 1.29  2013/10/07 10:12:28  tw
// Logheader ist verlorgengegangen.
//
// Revision n.n  2013/09/16 14:36:55  tw
//

package de.decodetron;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.event.AbstractEvent;
import de.decodetron.event.ChangeEvent;
import de.decodetron.event.EventListenerInterface;
import de.decodetron.help.HelpPage;
import de.decodetron.security.AuthenticatedWebPage;
import de.decodetron.security.LoginPage;
import de.decodetron.security.LoginSession;
import de.decodetron.security.pwd.PasswordChange;
import de.decodetron.tab.TabbedHandler;
import de.decodetron.tab.recherche.event.IRecOnFundstelleClickEvent;
import de.decodetron.tab.recherche.event.IRecOnSearchEvent;
import de.decodetron.tab.recherche.event.RecOnFundstelleClickEvent;
import de.decodetron.tab.recherche.event.RecOnSearchEvent;

/**
 * Die AEP-Haupt-Rechercheseite.<br>
 * <br>
 * 1. ... erwartet einen Lucene-Index im ${Benutzerverzeichnis}/nowedatb2013/data/index<br>
 * 2. ... bietet eine Fundstellenliste: ${gibt mir alle Felder blgdate}, davon angezeigt:
 * ${HITSPERPAGE}.
 * 
 * 
 * @author Thomas Winter
 * @since 16.09.2013
 */
public class MainPage extends SecureBasePage implements AuthenticatedWebPage, EventListenerInterface,
        IRecOnSearchEvent, IRecOnFundstelleClickEvent {

    public static URL SERVERHOME = null;
    private static Logger log = Logger.getLogger(MainPage.class);

    public MainPage() {

        try {
            SERVERHOME = WebApplication.get().getServletContext().getResource("/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        setOutputMarkupId(true);
        // setDefaultModel(new CompoundPropertyModel<ValueMap>(new ValueMap()));
        setDefaultModel(new CompoundPropertyModel<ValueMap>(new AEPModel()));

        LoginSession s = LoginSession.get();
        add(new LogoutLink("logoutlink"));
        add(new TimerCountDown("timerscript"));
        add(new Label("timelabel", Model.of("")));
        add(new ChangePwdLink("changepwdlink", getDefaultModel()));
        add(new Label("vorname", Model.of(s.isSignedIn() ? s.getUser().getVorname() : "")));
        add(new Label("name", Model.of(s.isSignedIn() ? s.getUser().getNachname() : "")));
        add(new Label("version", AEPApplication.get().getModel().getBuildNumber()));
        add(new TabbedHandler("tabbedHandler", (IModel<AEPModel>) getDefaultModel()));
        add(new ShowHelpPage("showUeberPage"));
    }
    
    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(OnDomReadyHeaderItem.forScript("$.fn.iedetection()"));
    }

    private void updateTimer(ChangeEvent ce) {
        if (ce != null && ce.getTarget() != null) {
            ce.getTarget().appendJavaScript(resetSessionTimeout());
        }
    }

    @Override
    public void notifyAjaxEvent(AbstractEvent event) {
        if (event instanceof ChangeEvent) {

            ChangeEvent ce = ((ChangeEvent) event);
            Object change = ce.getChange();
            String ident = ce.getIdentifier();

            if (Const.ACTION_STAT_SUCHESTARTED.equals(ident)) {
                updateTimer(ce);
            }
        }
    }

    @Override
    public void onFundstelleClick(RecOnFundstelleClickEvent ce) {
        if (ce != null && ce.getTarget() != null) {
            ce.getTarget().appendJavaScript(resetSessionTimeout());
        }
    }

    @Override
    public void onSearchEvent(RecOnSearchEvent ce) {
        if (ce != null && ce.getTarget() != null) {
            ce.getTarget().appendJavaScript(resetSessionTimeout());
        }
    }

    /**
     * Setzt den Timeout der Session neu und liefert die Javascriptfunktion für die Aktualisierung
     * der Zeitangabe an der Oberfläche. Das eigentliche Session-Intervall bekommt einen Buffer, um
     * die Logout-Time in der DB nachtragen zu können.
     * 
     * @return String
     */
    private String resetSessionTimeout() {
        WebRequest req = (WebRequest) RequestCycle.get().getRequest();
        HttpServletRequest httpReq = (HttpServletRequest) req.getContainerRequest();

        int activeIntervall = httpReq.getSession().getMaxInactiveInterval();
        httpReq.getSession().setMaxInactiveInterval(activeIntervall);

        StringBuffer buf = new StringBuffer();
        buf.append("$.fn.startTimer(");
        buf.append(String.valueOf(activeIntervall));
        buf.append(")");
        return buf.toString();
    }

    private class ShowHelpPage extends Link<String> {
        public ShowHelpPage(String id) {
            super(id);
        }

        @Override
        public boolean isEnabled() {
            return LoginSession.get().getUser().getIsSuperAdmin();
        }

        public void onClick() {
            setResponsePage(HelpPage.class);
        }
    }

    private class TimerCountDown extends WebMarkupContainer {

        public TimerCountDown(String id) {
            super(id);
            // setRenderBodyOnly(true);
            setOutputMarkupId(true);
            add(new Behavior() {
                public void renderHead(Component component, IHeaderResponse response) {
                    response.render(OnDomReadyHeaderItem.forScript(resetSessionTimeout()));
                }
            });
        }
    }

    private class ChangePwdLink extends Link<Void> {

        public ChangePwdLink(String id, IModel model) {
            super(id, model);
        }

        @Override
        public boolean isVisible() {
            return !LoginSession.get().getUser().getIsApotheker();
        }

        public void onClick() {
            PageParameters params = new PageParameters();
            params.add("param", Const.PWD_CHANGE_SOFT);
            setResponsePage(PasswordChange.class, params);
        }
    }

    private class LogoutLink extends Link<Void> {

        public LogoutLink(String id) {
            super(id);
        }

        public void onClick() {
            log.info(LoginSession.get().getUser().getLogin() + ": ABGEMELDET.");
            getSession().invalidate();
            setResponsePage(LoginPage.class);
            System.gc();
        }
    }

}
