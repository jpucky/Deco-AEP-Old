// $Log: HelpPage.java,v $
// Revision 1.5  2019/07/17 20:19:41  tw
// CR: PDF-Tabellenkoepfe: Ansicht wie auf der Webseite.  CSV - Timer 20 Sek Limit beseitigt (CreateCsvLink.setCacheDuration)
//
// Revision 1.4  2017/07/20 15:59:33  tw
// Version 1.21-H: BUGIFX: Fixe Reihenfolge der Belegtypen zeigt Belegtypen an, die nicht der Gruppe zugeordnet sind.
//
// Revision 1.3  2015/03/22 00:53:03  tw
// Anzeige des Changelogs fuer Superadmins.
//
// Revision 1.2  2015/03/18 15:23:58  tw
// CR Feng-ID: 3947
//
// Revision 1.1  2015/01/30 02:44:52  tw
// Haldenbearbeitung 1. Wurf.
//
//

package de.decodetron.help;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.URL;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

import de.decodetron.AEPApplication;
import de.decodetron.Const;
import de.decodetron.SecureBasePage;
import de.decodetron.data.FileContentResource;
import de.decodetron.data.Util;
import de.decodetron.security.AuthenticatedWebPage;
import de.decodetron.tab.recherche.AnsichtPDFBeleg;

/**
 * @author Thomas Winter
 * @since 27.01.2015
 */
public class HelpPage extends SecureBasePage implements AuthenticatedWebPage {

    public HelpPage() {
        add(new ChangeLog("offeneScan", getDefaultModel()));
        add(new Label("version", Model.of(AEPApplication.get().getFrameworkSettings().getVersion())));
    }

    private class ChangeLog extends WebMarkupContainer {

        public ChangeLog(String id, IModel<?> model) {
            super(id, model);
            add(AttributeModifier.replace("src", new Model<String>() {

                public String getObject() {

                    PrintWriter pw = null;

                    try {

                        File fileChangeLog = new File(getClass().getResource("changeLog.txt").toURI());

                        if ((fileChangeLog != null) && (!fileChangeLog.exists())) {
                            fileChangeLog = File.createTempFile("info", ".txt");
                            pw = new PrintWriter(new FileWriter(fileChangeLog));
                            pw.println(Util.getTimeStamp1(System.currentTimeMillis()) + ": Keine Einträge vorhanden!");
                        }

                        // ENDLICH! So einach ist es, die blöde Cache-Problematik zu umgehen!
                        PageParameters pdfParameters = new PageParameters();
                        pdfParameters.add("ts", System.currentTimeMillis());
                        return (String) urlFor(getResourceReference(fileChangeLog), pdfParameters);

                    } catch (Exception e) {
                        e.printStackTrace();
                        return "about:blank";
                    } finally {
                        if (pw != null) {
                            pw.close();
                        }
                    }
                }
            }));
        }

        /**
         * 20.07.2017: Auch hier haben wir wieder ein Speicherleck! TODO: Diese Konstruktion muss
         * ersetzt werden, siehe: {@link AnsichtPDFBeleg}
         * 
         * @param file
         * @return
         */
        private ResourceReference getResourceReference(final File file) {
            ResourceReference rr = new ResourceReference(file.getName()) {
                @Override
                public IResource getResource() {
                    RequestCycle rc = getRequestCycle();
                    WebResponse wrc = (WebResponse) rc.getOriginalResponse();
                    wrc.setContentType("application/" + Util.getSuffix(file));
                    return new FileContentResource(file, getRequestCycle(), false);
                }
            };
            if (rr.canBeRegistered()) {
                getApplication().getResourceReferenceRegistry().registerResourceReference(rr);
            }
            return rr;
        }
    }
}
