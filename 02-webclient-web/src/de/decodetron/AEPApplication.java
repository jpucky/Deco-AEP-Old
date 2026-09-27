package de.decodetron;

import java.io.File;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.URIResolver;

import org.apache.fop.servlet.ServletContextURIResolver;
import org.apache.log4j.Logger;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.IPackageResourceGuard;
import org.apache.wicket.markup.html.SecurePackageResourceGuard;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.RequestUtils;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.Url.QueryParameter;
import org.apache.wicket.request.component.IRequestableComponent;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceStreamResource;
import org.apache.wicket.request.resource.SharedResourceReference;
import org.apache.wicket.request.resource.UrlResourceReference;
import org.apache.wicket.resource.JQueryPluginResourceReference;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.time.Duration;

import de.decodetron.bo.User;
import de.decodetron.dao.halde.HaldeDAOI;
import de.decodetron.dao.luecken.LueckeSRDAO;
import de.decodetron.dao.luecken.LueckeSRDAOI;
import de.decodetron.dao.recherche.RechercheDAOI;
import de.decodetron.dao.recherche.RechercheScanDAOI;
import de.decodetron.dao.statistik.StatistikDAOI;
import de.decodetron.dao.statistik.clubabv.DAOIClubAb;
import de.decodetron.dao.statistik.clubbes.DAOIClubBes;
import de.decodetron.dao.statistik.defekte.DAODefekte;
import de.decodetron.dao.statistikuser.StatistikUserDAOI;
import de.decodetron.dao.user.UserDAOI;
import de.decodetron.data.Util;
import de.decodetron.fac.DAOFactoryFileIO;
import de.decodetron.fac.DAOFactoryJDBC;
import de.decodetron.fac.DAOFactoryLucene;
import de.decodetron.security.AuthenticatedWebPage;
import de.decodetron.security.LoginPage;
import de.decodetron.security.LoginSession;
import de.decodetron.security.pwd.PasswordChange;

/**
 * Application object for your web application. If you want to run this application without
 * deploying, run the Start class.
 * 
 * @see de.decodetron.Start#main(String[])
 */
public class AEPApplication extends WebApplication {

    protected URIResolver uriResolver;
    private AEPApplicationModel model;
    private static final Integer LUECKEN_GEN_HH = 1; // Nachts um 1:00
    private static final Integer DELETE_PDF_HH = 3; // Nachts um 3:00
    private static Logger log = Logger.getLogger(AEPApplication.class);

    // private HashMap<String, Session> sessionMap = new HashMap<String, Session>();

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends WebPage> getHomePage() {
        return MainPage.class;
    }

    public static AEPApplication get() {
        return (AEPApplication) Application.get();
    }

    public URIResolver getURIResolver() {
        return this.uriResolver;
    }

    /**
     * @see org.apache.wicket.protocol.http.WebApplication#newSession(Request, Response)
     */
    @Override
    public Session newSession(Request request, Response response) {
        return new LoginSession(request);
    }

    /**
     * Beispiel, um ohne ServletgedÃ¶ns z.B. Franks Checkerseite zur VerfÃ¼gung zu stellen.
     */
    private class ExampleResource implements IResource {

        @Override
        public void respond(Attributes attributes) {
            WebResponse resp = (WebResponse) attributes.getResponse();
            resp.setContentType("text/plain");
            resp.write("OK");
        }
    }

    private void allowFileTypeAccess() {
        IPackageResourceGuard packageResourceGuard = getResourceSettings().getPackageResourceGuard();
        if (packageResourceGuard instanceof SecurePackageResourceGuard) {
            SecurePackageResourceGuard guard = (SecurePackageResourceGuard) packageResourceGuard;
            // Allow to access only to pdf files placed in the â€œpublicâ€� directory.
            guard.addPattern("+*.js");
        }
    }

    public static String SCRIPT_ROOT_PATH = "../..";
    
    public static void initDatePicker(IHeaderResponse response) {
//        response.render(JavaScriptHeaderItem.forReference(new JQueryPluginResourceReference(AEPApplication.class,
//            SCRIPT_ROOT_PATH + "ui/jquery.ui.core.js")));
//        response.render(JavaScriptHeaderItem.forReference(new JQueryPluginResourceReference(AEPApplication.class,
//                SCRIPT_ROOT_PATH + "ui/jquery.ui.widget.js")));
//        response.render(JavaScriptHeaderItem.forReference(new JQueryPluginResourceReference(AEPApplication.class,
//                SCRIPT_ROOT_PATH + "ui/jquery.ui.datepicker.js")));
//        response.render(JavaScriptHeaderItem.forReference(new JQueryPluginResourceReference(AEPApplication.class,
//                SCRIPT_ROOT_PATH + "ui/i18n/jquery.ui.datepicker-de.js")));
//        response.render(CssHeaderItem.forReference(new CssResourceReference(AEPApplication.class,
//                SCRIPT_ROOT_PATH + "themes/base/jquery.ui.all.css")));
//        response.render(CssHeaderItem.forReference(new CssResourceReference(AEPApplication.class,
//                SCRIPT_ROOT_PATH + "demos/demos.css")));
        response.render(OnDomReadyHeaderItem.forScript("$.fn.initDateSearchRecherche()"));
        response.render(OnDomReadyHeaderItem.forScript("$.fn.initDateSearchStatistik()"));
    }

    private void startFilePoller() {

    }

    /**
     * @see org.apache.wicket.Application#init()
     */
    @Override
    public void init() {
        super.init();

        startFilePoller();

        // getJavaScriptLibrarySettings().setJQueryReference(yourJquery183ResourceReference);
        this.getMarkupSettings().setStripWicketTags(true);
        getResourceSettings().getPropertiesFactory().clearCache();
        // getRequestCycleListeners().add(new CleanPDFDir());
        // getRequestCycleListeners().add(new SessionGuard());
        // getRequestCycleListeners().add(new SessionExpiredListener());
        // getRequestCycleListeners().add(new PageRequestHandlerTracker());

        
        /**
         * VORSICHT!!! DIE FIXED-TABLE-HEADER LAUFEN NUR MIT DIESER JQUERY-VARIANTE !!!
         */
        List<File> f = Util.searchFileByRegEx(new File("."), "jquery-1.7.1.js");
        
//        getJavaScriptLibrarySettings().setJQueryReference(
//            new UrlResourceReference(Url.parse(SCRIPT_ROOT_PATH + "js/jquery-1.7.1.js")));
        // allowFileTypeAccess();
        
        this.uriResolver = new ServletContextURIResolver(getServletContext());
        // getRequestCycleSettings().addResponseFilter(new ServerAndClientTimeFilter());

        // Einstellung f. CD-Start?
        getStoreSettings().setFileStoreFolder(new File(Const.USERNHOME + Const.FS + Const.AEP_TMP_DIRHOME));

        initSecuritySettings();
        model = new AEPApplicationModel();

        mountPage("/login", LoginPage.class);
        mountPage("/archiv", MainPage.class);
        mountPage("/pwdchange", PasswordChange.class);

        /**
         * PDFs sind unter AEP_TMP_PDF_DIRHOME abrufbar! Hier lokal, sieht das so aus:
         * C:\Users\gustav\.aep\pdf<br>
         * <br>
         * Die Implementierungen, so wie sie in den Dokus zu finden sind, mit FileResourceStream
         * usw. verusachen ein Speicherleck !!!!!<br>
         * <br>
         * Um zu dieser, nicht Speicherfressenden Lösung zu gelangen, habe ich 4 Jahre den Kopf in
         * den Sand gesteckt und bin jetzt erst (04.07.2017) zu einer Lösung gekommen. Die in diesem
         * Verzeichnis entstehenden PDFs werden zyklisch gelöscht, da sie nach Ablauf der Session
         * aufrufbar sind! @see #CleanPDFDirectories.
         */
        getSharedResources().add(Const.PDF_HOME_ALIAS, new FolderContentResource(new File(Const.AEP_TMP_PDF_DIRHOME)));
        mountResource(Const.PDF_HOME_ALIAS, new SharedResourceReference(Application.class, Const.PDF_HOME_ALIAS));

        // Ein Beispiel, um den Eintrag wieder zu entfernen
        // ResourceReference rr = getSharedResources().get(Application.class, Const.PDF_HOME_ALIAS,
        // null, null, null,
        // true);
        // getSharedResources().remove(new Key(rr));

        /**
         * Beispiel, um ohne ServletgedÃ¶ns z.B. Franks Checkerseite zur VerfÃ¼gung zu stellen.
         */
        // getSharedResources().add("confirm", new ExampleResource());
        // mountResource("confirm", new SharedResourceReference("confirm"));

        getPageSettings().setRecreateMountedPagesAfterExpiry(false);
        // getApplicationSettings().setPageExpiredErrorPage(PageExpired.class);
        getApplicationSettings().setPageExpiredErrorPage(LoginPage.class);
        //getResourceSettings().setDefaultCacheDuration(Duration.ONE_SECOND);

        Util u = new Util();
        getModel().setBuildNumber(u.getVersionPropertieValue("version"));
        getModel().setFileUserDB(u.getHomeDirectory(Const.APPLICATIONHOME, u.getConfPropertieValue("file.userdb")));
        getModel().setFileUserOnTimeDB(
            u.getHomeDirectory(Const.APPLICATIONHOME, u.getConfPropertieValue("file.userontime")));

        getModel().setFileDefekte(u.getHomeDirectory(Const.APPLICATIONHOME, u.getConfPropertieValue("file.defekte")));
        getModel().setFileBtmDB(u.getHomeDirectory(Const.APPLICATIONHOME, u.getConfPropertieValue("file.btmdb")));
        getModel().setFileReimportDB(
            u.getHomeDirectory(Const.APPLICATIONHOME, u.getConfPropertieValue("file.reimporte")));
        // getModel().setFileClubTestDB(u.getHomeDirectory(Const.APPLICATIONHOME,
        // u.getConfPropertieValue("file.clubdb")));
        getModel().setFileChargenDB(
            u.getHomeDirectory(Const.APPLICATIONHOME, u.getConfPropertieValue("file.chargendb")));
        getModel().setFileClubDBAbverkauf(
            u.getHomeDirectory(Const.APPLICATIONHOME, u.getConfPropertieValue("file.clubdbaberkauf")));
        getModel().setFileClubDBBestand(
            u.getHomeDirectory(Const.APPLICATIONHOME, u.getConfPropertieValue("file.clubdbbestand")));
        getModel().setFileHochpreisDB(
            u.getHomeDirectory(Const.APPLICATIONHOME, u.getConfPropertieValue("file.hochpreis")));
        getModel().setFileTierarzneiDB(
            u.getHomeDirectory(Const.APPLICATIONHOME, u.getConfPropertieValue("file.tierarznei")));
        getModel().setFileTransfusionDB(
            u.getHomeDirectory(Const.APPLICATIONHOME, u.getConfPropertieValue("file.transfusion")));
        getModel().setFileUeberweiserDB(
            u.getHomeDirectory(Const.APPLICATIONHOME, u.getConfPropertieValue("file.ueberweiser")));
        getModel().setFileValutaDB(u.getHomeDirectory(Const.APPLICATIONHOME, u.getConfPropertieValue("file.valuta")));

        getModel().setDirRootScan(u.getHomeDirectory(Const.APPLICATIONHOME, u.getConfPropertieValue("dir.root.scan")));
        getModel().setFileOffeneScan(u.getConfPropertieValue("file.offenescan", false));
        getModel().setFileOffeneArchivierung(u.getConfPropertieValue("file.offenearch", false));
        getModel().setFileLueckenProtokoll(u.getConfPropertieValue("file.lueckenprot", true));
        getModel().setFileLueckenProtokollSR(u.getConfPropertieValue("file.lueckenprot.sr", true));

        getModel().setPathArchivT(u.getHomeDirectory(Const.APPLICATIONHOME, u.getConfPropertieValue("path.archivt")));
        getModel().setPathLucy(u.getHomeDirectory(Const.APPLICATIONHOME, u.getConfPropertieValue("path.lucyidx")));
        getModel().setPathLucyScan(
            u.getHomeDirectory(Const.APPLICATIONHOME, u.getConfPropertieValue("path.lucyidx.scan")));

        log.info("####################################################");
        log.info("# AEP - VERSION       : " + getModel().getBuildNumber());
        log.info("# Home-Verzeichnis    : " + Const.APPLICATIONHOME);
        log.info("# UserDB-Verzeichnis  : " + getModel().getFileUserDB());
        log.info("# ArchivT-Verzeichnis : " + getModel().getPathArchivT());
        log.info("# Lucy-Verzeichnis    : " + getModel().getPathLucy());
        log.info("####################################################");

        initLueckenPolling();
        initPDFDiretoryCleaner();
    }

    private static class FolderContentResource implements IResource {
        private final File rootFolder;

        public FolderContentResource(File rootFolder) {
            this.rootFolder = rootFolder;
        }

        public void respond(Attributes attributes) {
            PageParameters parameters = attributes.getParameters();
            String fileName = parameters.get(0).toString();
            File file = new File(rootFolder, fileName);
            FileResourceStream fileResourceStream = new FileResourceStream(file);
            ResourceStreamResource resource = new ResourceStreamResource(fileResourceStream);
            resource.respond(attributes);
        }
    }

    /**
     * Alte Default-Funktion.
     * 
     * @deprecated
     * @return HaldeDAOI
     */
    public HaldeDAOI getDBHaldeScan() {
        return getDBHalde(de.decodetron.util.Const.HALDE_BELEGART_SC);
    }

    /**
     * @param String
     *            selectedBelegart
     * @return HaldeDAOI
     */
    public HaldeDAOI getDBHalde(String selBelegart) {
        HaldeDAOI haldeDB = DAOFactoryFileIO.getInstance(selBelegart).getHaldeDAO();
        return haldeDB;
    }

    public List<String> getAllowedIPAdresses() {
        String userdbLocation = getModel().getFileUserDB();
        UserDAOI userDB = DAOFactoryJDBC.getInstance(userdbLocation).getUserDAO();
        return userDB.getIPAdressesAllowed();
    }

    public UserDAOI getDBUser() {
        String userdbLocation = getModel().getFileUserDB();
        UserDAOI userDB = DAOFactoryJDBC.getInstance(userdbLocation).getUserDAO();
        return userDB;
    }

    public RechercheDAOI getDBRecherche() {
        String luceneDB = getModel().getPathLucy();
        RechercheDAOI lDB = DAOFactoryLucene.getInstance(luceneDB).getRechercheDAO();
        return lDB;
    }

    public RechercheScanDAOI getDBRechercheScan() {
        String luceneDB = getModel().getPathLucyScan();
        if (new File(luceneDB).exists()) {
            RechercheScanDAOI lDB = DAOFactoryLucene.getInstance(luceneDB).getRechercheScanDAO();
            return lDB;
        } else {
            // Extrawurst fÃ¼r Helmut, da er nicht in die Puschen kommt.
            return null;
        }
    }

    /**
     * TODO: Die Funktion muss zu einem geeigneten Zeitpunkt auf eine seperate DB umgestellt werden.
     * 
     * @return StatistikUserDAOI
     */
    public StatistikUserDAOI getDBUserOnTime() {
        String userdbLocation = getModel().getFileUserDB(); // <= getFileUserOnTimeDB
        StatistikUserDAOI statDB = DAOFactoryJDBC.getInstance(userdbLocation).getStatistikUserDAO();
        return statDB;
    }

    // Recherche-Club-Extrawurst
    public DAOIClubAb getDBClubAbverkauf() {
        String clubdbLocation = getModel().getFileClubDBAbverkauf();
        DAOIClubAb clubDB = DAOFactoryJDBC.getInstance(clubdbLocation).getDAOClub();
        return clubDB;
    }

    // Recherche-Defekte-Extrawurst
    public DAODefekte getDBDefekteFilter() {
        String chargendbLocation = AEPApplication.get().getModel().getFileDefekte();
        DAODefekte defekteDBFilter = DAOFactoryJDBC.getInstance(chargendbLocation).getDAODefekte();
        return defekteDBFilter;
    }

    // Recherche-ClubBestand-Extrawurst
    public DAOIClubBes getDBClubBestand() {
        String clubdbBestand = getModel().getFileClubDBBestand();
        DAOIClubBes clubDBBest = DAOFactoryJDBC.getInstance(clubdbBestand).getDAOClubBes();
        return clubDBBest;
    }

    // Recherche-generisch
    public StatistikDAOI getDBBtmFilter() {
        String dbBTMLocation = AEPApplication.get().getModel().getFileBtmDB();
        StatistikDAOI btmDBFilter = DAOFactoryJDBC.getInstance(dbBTMLocation).getDAOFilter();
        return btmDBFilter;
    }

    // Recherche-generisch
    public StatistikDAOI getDBReimporteFilter() {
        String reimpdbLocation = AEPApplication.get().getModel().getFileReimportDB();
        StatistikDAOI reimpDB = DAOFactoryJDBC.getInstance(reimpdbLocation).getDAOFilter();
        return reimpDB;
    }

    // Recherche-generisch
    public StatistikDAOI getDBChargenFilter() {
        String chargendbLocation = AEPApplication.get().getModel().getFileChargenDB();
        StatistikDAOI chargenDBFilter = DAOFactoryJDBC.getInstance(chargendbLocation).getDAOFilter();
        return chargenDBFilter;
    }

    // Recherche-generisch
    public StatistikDAOI getDBHochpreisFilter() {
        String chargendbLocation = AEPApplication.get().getModel().getFileHochpreisDB();
        StatistikDAOI hochpreisDB = DAOFactoryJDBC.getInstance(chargendbLocation).getDAOFilter();
        return hochpreisDB;
    }

    // Recherche-generisch
    public StatistikDAOI getDBTierarzneiFilter() {
        String tierarzdbLocation = AEPApplication.get().getModel().getFileTierarzneiDB();
        StatistikDAOI hochpreisDBFilter = DAOFactoryJDBC.getInstance(tierarzdbLocation).getDAOFilter();
        return hochpreisDBFilter;
    }

    // Recherche-generisch
    public StatistikDAOI getDBTransfusionFilter() {
        String transfusiondbLocation = AEPApplication.get().getModel().getFileTransfusionDB();
        StatistikDAOI transfusionDBFilter = DAOFactoryJDBC.getInstance(transfusiondbLocation).getDAOFilter();
        return transfusionDBFilter;
    }

    // Recherche-generisch
    public StatistikDAOI getDBUeberweiserFilter() {
        String ueberweiserdbLocation = AEPApplication.get().getModel().getFileUeberweiserDB();
        StatistikDAOI ueberweiserDBFilter = DAOFactoryJDBC.getInstance(ueberweiserdbLocation).getDAOFilter();
        return ueberweiserDBFilter;
    }

    // Recherche-generisch
    public StatistikDAOI getDBValutaFilter() {
        String valutadbLocation = AEPApplication.get().getModel().getFileValutaDB();
        StatistikDAOI valutaDBFilter = DAOFactoryJDBC.getInstance(valutadbLocation).getDAOFilter();
        return valutaDBFilter;
    }

    public LueckeSRDAOI getDBLueckeSR() {
        return new LueckeSRDAO();
    }

    public boolean statistikAliveCheck() {
        boolean dbBtmExists = new File(AEPApplication.get().getModel().getFileBtmDB()).exists();
        boolean dbChargenExists = new File(AEPApplication.get().getModel().getFileChargenDB()).exists();
        boolean dbAbverkaufExists = new File(AEPApplication.get().getModel().getFileClubDBAbverkauf()).exists();
        boolean dbBestandExists = new File(AEPApplication.get().getModel().getFileClubDBBestand()).exists();
        boolean dbDefekteExists = new File(AEPApplication.get().getModel().getFileDefekte()).exists();
        boolean dbHochpreisExists = new File(AEPApplication.get().getModel().getFileHochpreisDB()).exists();
        boolean dbReimportExists = new File(AEPApplication.get().getModel().getFileReimportDB()).exists();
        boolean dbTierarznei = new File(AEPApplication.get().getModel().getFileTierarzneiDB()).exists();
        boolean dbTransfusion = new File(AEPApplication.get().getModel().getFileTransfusionDB()).exists();
        boolean dbUeberweiser = new File(AEPApplication.get().getModel().getFileUeberweiserDB()).exists();
        boolean dbValuta = new File(AEPApplication.get().getModel().getFileValutaDB()).exists();
        return dbBtmExists && dbChargenExists && dbAbverkaufExists && dbBestandExists && dbDefekteExists
                && dbHochpreisExists && dbReimportExists && dbTierarznei && dbTransfusion && dbUeberweiser && dbValuta;
    }

    public AEPApplicationModel getModel() {
        return model;
    }

    private class CleanPDFDir extends AbstractRequestCycleListener {

        @Override
        public void onEndRequest(RequestCycle cycle) {
            Util u = new Util();
            LoginSession session = LoginSession.class.cast(Session.get());
            User user = session.getUser();
            if (session.isSignedIn()) {
                List<File> f = u.getFiles(Const.AEP_TMP_PDF_DIRHOME + "/", "^" + user.getLogin() + ".*?\\.pdf$");
                for (Iterator<File> iterator = f.iterator(); iterator.hasNext();) {
                    File pdfFile = iterator.next();
                    pdfFile.delete();
                }
            }
            super.onEndRequest(cycle);
        }
    }

    private class SessionGuard extends AbstractRequestCycleListener {

        @Override
        public void onBeginRequest(RequestCycle cycle) {

            // getRequestCycleSettings().setRenderStrategy(
            // IRequestCycleSettings.RenderStrategy.ONE_PASS_RENDER);

            Duration timout = getRequestCycleSettings().getTimeout();
            WebRequest req = (WebRequest) RequestCycle.get().getRequest();
            HttpServletRequest httpReq = (HttpServletRequest) req.getContainerRequest();
            int maxInteractiveInterval = httpReq.getSession().getMaxInactiveInterval();
            String isSecure = httpReq.isSecure() ? "https://" : "http://";

            System.out.println("1: " + isSecure + RequestCycle.get().getUrlRenderer().getBaseUrl().getHost() + ":"
                    + RequestCycle.get().getUrlRenderer().getBaseUrl().getPort());
            System.out.println("2: " + RequestUtils.toAbsolutePath(httpReq.getRequestURL().toString(), "/"));
            System.out.println("3: " + WebApplication.get().getServletContext().getServerInfo());
            System.out.println("4: " + WebApplication.get().getServletContext().getRealPath(""));
            System.out.println("5: " + getFrameworkSettings().getVersion());

            List<?> p = RequestCycle.get().getUrlRenderer().getBaseUrl().getQueryParameters();
            if (p != null && p.size() > 0) {
                QueryParameter qp = (QueryParameter) p.get(0);
                System.out.println("6: " + qp);
            }

            try {
                System.out.println("7: " + WebApplication.get().getServletContext().getResource("/"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            WebApplication web = WebApplication.get();
            ServletContext ctx = web.getServletContext();
            LoginSession session = LoginSession.class.cast(Session.get());

            if (session.isSignedIn()) {

                // sessionMap.put(session.getUser().getLogin(), session);
                System.out.println("Username     : " + session.getUser().getNachname());
                System.out.println("Session-ID   : " + session.getId());
                System.out.println("Remote-adr   : " + httpReq.getRemoteHost() + "\n");
                System.out.println("MaxInactiveInterval : " + maxInteractiveInterval + " [s]");
                System.out.println("Zeit bis zum Timeout? : " + timout.getMilliseconds() / 1000 + " [s]");
                System.out.println("ServletContext : " + ctx.getContextPath());
                System.out.println(" ");
            } else {
                System.out.println("An diesem Client: " + httpReq.getRemoteHost() + " keiner eingeloggt.");
            }

            super.onBeginRequest(cycle);
        }

        @Override
        public void onRequestHandlerExecuted(RequestCycle cycle, IRequestHandler handler) {
            super.onRequestHandlerExecuted(cycle, handler);
        }

        @Override
        public void onEndRequest(RequestCycle cycle) {
            super.onEndRequest(cycle);
        }
    }

    private void initLueckenPolling() {
        Timer timer = new Timer();
        long SECOND = 1000;
        long MINUTE = SECOND * 60;
        long HOUR = MINUTE * 60;
        timer.schedule(new GenerateGapProtokoll(), 0, HOUR); // Check, 1 mal pro Stunde
    }

    private void initPDFDiretoryCleaner() {
        Timer timer = new Timer();
        long SECOND = 1000;
        timer.schedule(new CleanPDFDirectories(), 0, SECOND * 30); // Check, alle 30 Sekunden
    }

    private void initSecuritySettings() {
        getSecuritySettings().setAuthorizationStrategy(new IAuthorizationStrategy() {

            @Override
            public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> componentClass) {

                if (AuthenticatedWebPage.class.isAssignableFrom(componentClass)) {

                    log.debug("1 Debug : " + componentClass);
                    // Is user signed in?
                    if (((LoginSession) Session.get()).isSignedIn()) {
                        // okay to proceed
                        return true;
                    }
                    // Intercept the request, but remember the target for later.
                    // Invoke Component.continueToOriginalDestination() after successful logon
                    // to
                    // continue with the target remembered.
                    throw new RestartResponseAtInterceptPageException(LoginPage.class);
                } else {
                    log.debug("2 Debug : " + componentClass);
                }

                // okay to proceed
                return true;
            }

            @Override
            public boolean isActionAuthorized(Component arg0, Action arg1) {
                // Hier ist erstmal alles erlaubt (jede Rolle?)
                return true;
            }
        });
    }

    /**
     * LÃ¶st die Generierung zu einer bestimmten Stunde aus. Der Timer muss so eingestellt sein, dass
     * nur 1 mal pro Stunde gecheckt wird, wieviel Uhr gerade ist.
     */
    private class GenerateGapProtokoll extends TimerTask {

        @Override
        public void run() {
            Calendar now = new GregorianCalendar(TimeZone.getDefault());
            if (now.get(Calendar.HOUR_OF_DAY) == LUECKEN_GEN_HH) {
                log.info("Luecken-Protokollgenerierung gestartet ...");
                getDBLueckeSR().generateLueckenProtokollSR();
            }
        }
    }

    /**
     * BÃ¼gelt alle PDF- und CSV - Dateien in dem temporÃ¤ren PDF- und CSV-Verzeichnis auÃŸer
     * error.pdf. Wenn das stÃ¤ndig lÃ¤uft, macht das Probleme im laufenden Betrieb! Es werden nur
     * Files gelÃ¶scht, die Ã¤lter als 30 Sekunden sind. Die Files mÃ¼ssen gelÃ¶scht werden, da sie auch
     * nach Ablauf der Session noch abrufbar sind!
     */
    private class CleanPDFDirectories extends TimerTask {

        public void run() {

            long timeBuffer = 1000 * 30;

            Util u = new Util();
            // Die Reihenfolge in dem RegulÃ¤ren Ausdruck ist entscheident!
            List<File> f = u.getFiles(Const.AEP_TMP_PDF_DIRHOME + "/", "(?!error.pdf)(.*\\.pdf$)");
            List<File> fpdf = u.getFiles(Const.AEP_TMP_CSV_DIRHOME + "/", ".*\\.pdf$");
            List<File> fcsv = u.getFiles(Const.AEP_TMP_CSV_DIRHOME + "/", ".*\\.csv$");
            f.addAll(fpdf);
            f.addAll(fcsv);
            for (Iterator<File> iterator = f.iterator(); iterator.hasNext();) {
                File pdfFile = iterator.next();

                try {
                    Date now = new Date();
                    // Date fileDate = new Date(pdfFile.lastModified());
                    Date fileExpireDate = new Date(pdfFile.lastModified() + timeBuffer);

                    // SimpleDateFormat sdfDebug1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                    // Locale.GERMAN);
                    // System.out.println("Filedate       : " + sdfDebug1.format(fileDate) + " " +
                    // pdfFile.getName());
                    // System.out.println("FileExpireDate : " + sdfDebug1.format(fileExpireDate));
                    // System.out.println("Now            : " + sdfDebug1.format(now));

                    if (now.after(fileExpireDate)) {
                        pdfFile.delete();
                    }
                } catch (Exception e) {
                    log.error("Probleme beim Löschen des Files: " + pdfFile.getName());
                }
            }
        }
    }
}
