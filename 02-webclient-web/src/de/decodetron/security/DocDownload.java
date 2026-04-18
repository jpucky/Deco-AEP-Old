// $Log: DocDownload.java,v $
// Revision 1.1  2014/11/24 11:44:04  tw
// Vorgeplaenkel f. Belegabruf.
//
//

package de.decodetron.security;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.protocol.http.WebApplication;

/**
 * Test, um einen Direktabruf zu ermöglichen.
 * 
 * @author Thomas Winter
 * @since 20.11.2014
 */
public class DocDownload extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

    private String filePath;
    private static final int BUFSIZE = 4096;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String p1 = request.getParameter("p1");
        String p2 = request.getParameter("p2");
        System.out.println("Parameter-Check: " + p1 + ", " + p2);

        if (p1 == null) {
            filePath = WebApplication.get().getServletContext().getRealPath("") + File.separator + "test.txt";
        } else {
            filePath = p1;
        }
        File file = new File(filePath);

        System.out.println("Suche File: " + filePath);

        int length = 0;

        ServletOutputStream outStream = response.getOutputStream();
        ServletContext context = getServletConfig().getServletContext();
        String mimetype = context.getMimeType(filePath);

        // sets response content type
        if (mimetype == null) {
            mimetype = "application/octet-stream";
        }
        response.setContentType(mimetype);
        response.setContentLength((int) file.length());
        String fileName = file.getName();

        // sets HTTP header
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        byte[] byteBuffer = new byte[BUFSIZE];
        DataInputStream in = new DataInputStream(new FileInputStream(file));

        // reads the file's bytes and writes them to the response stream
        while ((in != null) && ((length = in.read(byteBuffer)) != -1)) {
            outStream.write(byteBuffer, 0, length);
        }

        in.close();
        outStream.close();
    }
}
