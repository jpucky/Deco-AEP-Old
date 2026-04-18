// $Log: PageExpired.java,v $
// Revision 1.2  2014/10/23 21:03:20  tw
// Header-Security Anpassung.
//
// Revision 1.1  2013/09/22 15:13:34  tw
// Page-Expired, Verdrahtung/Hilfehinweis Hauptseite implementiert
//
//

package de.decodetron.security;

import org.apache.wicket.markup.html.link.Link;

import de.decodetron.SecureBasePage;

/**
 * @author Thomas Winter
 * @since 22.09.2013
 */
public class PageExpired extends SecureBasePage {
    public PageExpired() {
        add(new Link<Void>("loginURL") {
            public void onClick() {
                setResponsePage(LoginPage.class);
            }
        });
    }
}
