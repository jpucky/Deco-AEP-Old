// $Log: FileContentResource.java,v $
// Revision 1.10  2017/07/04 01:07:31  tw
// Mobilmachung der Headrevision. Beseitigung des PDF-Speicherlecks.
//
// Revision 1.9  2017/06/21 19:44:16  tw
// Mobilmachung der Headrevision.
//
// Revision 1.8  2015/07/20 14:56:12  tw
// Forschung: Memory Leak.
//
// Revision 1.7  2015/02/04 16:23:27  tw
// Haldenbearbeitung: Speichern v. Buchungsdatensaetzen implementiert.
//
// Revision 1.6  2015/01/30 02:44:51  tw
// Haldenbearbeitung 1. Wurf.
//
// Revision 1.5  2014/10/03 21:52:11  tw
// Implementierung: Haldenstatus.
//
// Revision 1.4  2014/10/03 15:06:33  tw
// Implementierung: Haldenstatus.
//
// Revision 1.3  2013/12/24 00:24:27  tw
// Speicherleck PDF-Anzeige beseitigt.
//
// Revision 1.2  2013/10/29 16:06:57  tw
// - aep user im import.sql integriert.
// - billig-baumdarstellung implementiert.
//
// Revision 1.1  2013/10/25 01:59:00  tw
// Anpassung an Rollout.
//
//

package de.decodetron.data;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceStreamResource;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.time.Duration;

/**
 * Hilfsklasse, um Files als Resource in Wicket einzubinden.
 * 
 * @author Thomas Winter
 * @since 15.10.2013
 */
public class FileContentResource implements IResource, Serializable {

    private final File file;
    private RequestCycle requestCycle;
    private boolean deleteFile = true; // default!

    public FileContentResource(File rootFolder, RequestCycle rc, boolean df) {
        this(rootFolder, rc);
        deleteFile = df;
    }

    public FileContentResource(File rootFolder, RequestCycle rc) {
        this.file = rootFolder;
        this.requestCycle = rc;
    }

    public void respond(Attributes attributes) {

        final FileResourceStream fileResourceStream = new FileResourceStream(file);

        // IRequestHandler target = new ResourceStreamRequestHandler(fileResourceStream)
        // .setCacheDuration(org.apache.wicket.util.time.Duration.MAXIMUM);
        // this.requestCycle.scheduleRequestHandlerAfterCurrent(target);

        ResourceStreamResource resource = new ResourceStreamResource(fileResourceStream);
        resource.setCacheDuration(Duration.NONE);
        resource.setContentDisposition(ContentDisposition.INLINE);
        resource.respond(attributes);

        try {
            fileResourceStream.close();
            if (deleteFile) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValidImagePath(List<String> indexedParams) {
        String fileName = indexedParams.get(indexedParams.size() - 1);
        return !FilenameUtils.getExtension(fileName).isEmpty();
    }

    private List<String> getAllIndexedParameters(PageParameters parameters) {
        int indexedparamCount = parameters.getIndexedCount();
        List<String> indexedParameters = new ArrayList<String>();
        for (int i = 0; i < indexedparamCount; i++) {
            indexedParameters.add(parameters.get(i).toString());
        }
        return indexedParameters;
    }

    /**
     * Eine Responsealternative.
     * 
     * @param Attributes
     *            attributes
     */
    private void responseAlternative(Attributes attributes) {

        WebResponse response = (WebResponse) attributes.getResponse();
        OutputStream outStream = response.getOutputStream();
        // response.setContentType("audio/x-wav");
        response.setContentLength((int) file.length());

        // sets HTTP header
        // response.setContentType("text/plain");
        // response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() +
        // "\"");

        byte[] byteBuffer = new byte[1024];
        DataInputStream in = null;

        try {

            in = new DataInputStream(new FileInputStream(file));

            int length = 0;
            // reads the file's bytes and writes them to the response stream
            while ((in != null) && ((length = in.read(byteBuffer)) != -1)) {
                outStream.write(byteBuffer, 0, length);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {}
        }
    }

    // So würde das bei einem Servlet gehen ...
    // http://www.javabeat.net/pdf-file-content-type-servlet/#sthash.W7JSaJsI.dpuf
    /**
     * <pre>
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/pdf");
        String filepath = "/home/jsp.pdf";
        response.setHeader("Content-Disposition", "inline; filename=’jsp.pdf'");
        FileOutputStream fileout = new FileOutputStream(filepath);
        fileout.close();
        out.close();
    }
    * </pre>
    */

}
