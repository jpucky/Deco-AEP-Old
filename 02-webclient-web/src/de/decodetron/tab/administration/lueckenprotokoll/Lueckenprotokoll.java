// $Log: Lueckenprotokoll.java,v $
// Revision 1.7  2015/10/19 12:59:45  tw
// CR intern: Erweiterung der DB-Schnittstelle "Fundstelle" f. Archivierungsdatum.
//
// Revision 1.6  2015/07/22 09:56:23  tw
// Implementierung: Luecken-SR. Bugfix.
//
// Revision 1.5  2015/07/20 14:54:05  tw
// Implementierung: Luecken-SR.
//
// Revision 1.4  2015/05/19 19:58:46  tw
// CR Feng-ID: 3947#11
//
// Revision 1.3  2015/01/30 02:44:52  tw
// Haldenbearbeitung 1. Wurf.
//
// Revision 1.2  2015/01/25 20:11:32  tw
// Vorabentwurf f. Lueckenprotokoll implementiert.
//
// Revision 1.1  2015/01/25 14:34:35  tw
// Vorabentwurf f. Lueckenprotokoll implementiert.
//
//

package de.decodetron.tab.administration.lueckenprotokoll;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.encoding.UrlEncoder;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.time.Duration;

import de.decodetron.AEPApplication;
import de.decodetron.data.FileContentResource;
import de.decodetron.data.Util;
import de.decodetron.security.LoginSession;

/**
 * @author Thomas Winter
 * @since 23.01.2015
 */
public class Lueckenprotokoll extends Panel {

    public Lueckenprotokoll(String id, IModel<?> model) {
        super(id, model);
        add(new LueckeView("lueckenProtokll", model));
        add(new LueckeDownload("lueckeDownload"));
        add(new Refresh("refreshLueckenprotokoll"));
        add(new DateLabel("lueckeDate"));
        add(new ChefButton("chefbutton"));
    }

    /**
     * Erzeugt ein Datumslabel: "Stand dd.MM.yyyy hh:mm" aus dem Dateinamen.
     */
    private class DateLabel extends Label {

        /**
         * @param String
         *            id
         */
        public DateLabel(String id) {
            super(id);
            setDefaultModel(new Model<String>() {
                @Override
                public String getObject() {
                    StringBuilder sb = new StringBuilder();
                    try {

                        File fileLuecke = null;                        
                        if("luesr".equals(LoginSession.get().getCurrentSection().getKz())){
                            fileLuecke = new File(AEPApplication.get().getModel().getFileLueckenProtokollSR());
                        }else{
                            fileLuecke = new File(AEPApplication.get().getModel().getFileLueckenProtokoll());
                        }
                        
                        if (fileLuecke != null && fileLuecke.exists()) {
                            SimpleDateFormat fin = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                            String date = fin.format(new Date(fileLuecke.lastModified()));
                            sb.append("(Stand ");
                            sb.append(date);
                            sb.append(")");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return sb.toString();
                }
            });
        }
    }

    private class Refresh extends Link<String> {

        public Refresh(String id) {
            super(id);
        }

        @Override
        public void onClick() {
            // nix. der request-zyklus reicht zum erneuern. ajax schenken wir uns.
        }
    }

    private class LueckeDownload extends DownloadLink {

        @Override
        public boolean isVisible() {
            File fileLuecke = new File(AEPApplication.get().getModel().getFileLueckenProtokoll());
            return fileLuecke != null ? fileLuecke.exists() : false;
        }

        public LueckeDownload(String id) {
            super(id, new AbstractReadOnlyModel<File>() {
                public File getObject() {
                    
                    if("luesr".equals(LoginSession.get().getCurrentSection().getKz())){
                        return new File(AEPApplication.get().getModel().getFileLueckenProtokollSR());
                    }else{
                        return new File(AEPApplication.get().getModel().getFileLueckenProtokoll());
                    }
                }
            });
        }

        /**
         * Zwecks Umlautersetztung überschrieben!
         */
        @Override
        public void onClick() {
            final File file = getModelObject();
            if (file == null) {
                throw new IllegalStateException(getClass().getName() + " failed to retrieve a File object from model");
            }

            String fileName = Util.replaceUmlaute(file.getName());
            fileName = UrlEncoder.QUERY_INSTANCE.encode(fileName, getRequest().getCharset());

            IResourceStream resourceStream = new FileResourceStream(new org.apache.wicket.util.file.File(file));
            getRequestCycle().scheduleRequestHandlerAfterCurrent(
                new ResourceStreamRequestHandler(resourceStream) {
                    @Override
                    public void respond(IRequestCycle requestCycle) {
                        super.respond(requestCycle);
                    }
                }.setFileName(fileName).setContentDisposition(ContentDisposition.ATTACHMENT)
                        .setCacheDuration(Duration.NONE));
            
            
        }
    }

    private class LueckeView extends WebMarkupContainer {
        
        public LueckeView(String id, IModel<?> model) {
            super(id, model);

            add(AttributeModifier.replace("src", new Model<String>() {
                @Override
                public String getObject() {

                    PrintWriter pw = null;

                    try {
                        
                        File fileLuecke = null;                        
                        if("luesr".equals(LoginSession.get().getCurrentSection().getKz())){
                            fileLuecke = new File(AEPApplication.get().getModel().getFileLueckenProtokollSR());
                        }else{
                            fileLuecke = new File(AEPApplication.get().getModel().getFileLueckenProtokoll());
                        }

                        if ((fileLuecke != null) && (!fileLuecke.exists())) {
                            fileLuecke = File.createTempFile("info", ".txt");
                            pw = new PrintWriter(new FileWriter(fileLuecke));
                            pw.println(Util.getTimeStamp1(System.currentTimeMillis()) + ": Keine Einträge vorhanden!");
                        }
                        
                        // ENDLICH! So einach ist es, die blöde Cache-Problematik zu umgehen!
                        PageParameters pdfParameters = new PageParameters();
                        pdfParameters.add("ts", System.currentTimeMillis());
                       return (String) urlFor(getResourceReference(fileLuecke), pdfParameters);

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
    }

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
    
    private class ChefButton extends Link<String>{

        public ChefButton(String id) {
            super(id);
        }

        @Override
        public void onClick() {
            AEPApplication.get().getDBLueckeSR().generateLueckenProtokollSR();
            
        }
        
        @Override
        public boolean isVisible() {
            return "twinter@decodetron.de".equals(LoginSession.get().getUser().getLogin());
        }
    }
}
