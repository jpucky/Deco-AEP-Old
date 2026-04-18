// $Log: SecureBasePage.java,v $
// Revision 1.1  2014/10/23 21:03:54  tw
// Header-Security Anpassung.
//
//

package de.decodetron;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Basepage mit sicherheitsrelevanten Headereinstellungen.<br>
 * <br>
 * Check: http://cyh.herokuapp.com/cyh <br>
 * 
 * @author Thomas Winter
 * @since 22.10.2014
 */
public class SecureBasePage extends WebPage {

    public SecureBasePage() {
        super();
    }

    public SecureBasePage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void setHeaders(WebResponse response) {
        
        StringBuffer sbCSB = new StringBuffer();
        sbCSB.append("default-src 'self';");
        sbCSB.append("script-src 'self' 'https://localhost:8082/js/jquery-1.7.1.min.js';");
        sbCSB.append("connect-src 'self';");
        sbCSB.append("img-src 'self';");
        sbCSB.append("style-src 'self';");
        
        response.setHeader("X-Frame-Options", "sameorigin");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
        response.setHeader("X-XSS-Protection", "1; mode=block");
        //response.setHeader("Content-Security-Policy", sbCSB.toString());
        super.setHeaders(response);
    }
}
