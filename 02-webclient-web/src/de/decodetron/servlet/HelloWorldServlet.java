// $Log: HelloWorldServlet.java,v $
// Revision 1.2  2015/07/20 14:55:44  tw
// Versuche f. Statusrueckmeldungen.
//
// Revision 1.1  2014/01/17 21:48:54  tw
// Starten einiger Testblasen.
//
//

package de.decodetron.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.Session;

import de.decodetron.security.LoginSession;

/**
 * Wicket laesst sich mit Servletkomponenten mischen. Ein paar Tests.
 * 
 * @author Thomas Winter
 * @since 17.01.2014
 */
public class HelloWorldServlet extends HttpServlet {

    // Method to handle GET method request.
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set refresh, autoload time as 5 seconds
        response.setIntHeader("Refresh", 5);
        //response.addHeader("Refresh", "5"); // <= Geht auch !

        // Set response content type
        response.setContentType("text/html");

        // Get current time
        Calendar calendar = new GregorianCalendar();
        String am_pm;
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        if (calendar.get(Calendar.AM_PM) == 0)
            am_pm = "AM";
        else
            am_pm = "PM";

        String CT = hour + ":" + minute + ":" + second + " " + am_pm;

        PrintWriter out = response.getWriter();
        String title = "Auto Page Refresh using Servlet";
        String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
        out.println(docType + "<html>\n" + "<head><title>" + title + "</title></head>\n"
                + "<body bgcolor=\"#f0f0f0\">\n" + "<h1 align=\"center\">" + title + "</h1>\n" + "<p>Current Time is: "
                + CT + "</p><p> Remote Address: "+request.getRemoteAddr()+"</p>\n");
    }

    // Method to handle POST method request.
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    // public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException,
    // IOException {
    // res.setContentType("text/html");
    // PrintWriter out = res.getWriter();
    // String message = "Hi. "
    // + (Session.exists() ? " I know Wicket session " + ((LoginSession)
    // Session.get()).getUser().getVorname()
    // + "." : " I can't find a Wicket session.");
    // out.println(message);
    // out.close();
    // }
}
