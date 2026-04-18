// $Log: XmlParserTest.java,v $
// Revision 1.3  2014/11/03 16:57:06  tw
// Testanbindung: History-DB Benutzerrechte.
//
// Revision 1.2  2014/10/28 22:44:41  tw
// Testanbindung Xml-Objektparsing, UserSectionData.
//
// Revision 1.1  2014/01/24 13:39:07  tw
// Schnittstellen aufgeraeumt. Neue limitierte Benutzerschnittstelle.
//
//

package de.decodetron;

import java.io.File;

import junit.framework.TestCase;
import de.decodetron.bo.SectionInfo;
import de.decodetron.bo.UserSectionData;
import de.decodetron.util.Util;

/**
 * Basistests für Xml - Umwandlungen.
 * 
 * @author Thomas Winter
 * @since 09.01.2014
 */
public class XmlParserTest extends TestCase {

    public void testGenerateXml() {

        String USERDIR = System.getProperty("user.dir");
        String FS = System.getProperty("file.separator");
        String XMLRESPONSEFILE = "file.xml";

        UserSectionData user = new UserSectionData();
        user.setBtm(new SectionInfo(23, false)); // Testitem1
        user.setVorname("xml-vorname"); // Testitem2
        user.setNachname("xml-nachname"); // Testitem3

        // ///////////////////////////////////////////
        // /// ... Als String ausspucken ...
        // /
        String xmlUserData = Util.object2XmlString(user);
        assertTrue(xmlUserData.startsWith("<?xml version"));
        assertTrue(xmlUserData.contains("<userSectionData>"));
        
        // ///////////////////////////////////////////
        // /// Als File ausspucken ...
        // /
        File file = new File(USERDIR + FS + XMLRESPONSEFILE);
        if (file.exists()) {
            // olles File löschen
            file.delete();
        }
        assertFalse(file.exists()); // alles leer ?
        file = Util.object2XmlFile(file, user);
        assertTrue(file.exists());// neues file erzeugt ?

        // ///////////////////////////////////////////
        // /// ... und wieder einlesen ..
        // /
        Object ut = Util.file2Object(file, UserSectionData.class);        
        UserSectionData usd = (UserSectionData)ut; 
        assertEquals("xml-vorname", usd.getVorname()); // Testitem1
        assertEquals("xml-nachname", usd.getNachname()); // Testitem2
        assertTrue(23 == usd.getBtm().getLevel()); // Testitem3
        file.delete();
    }

}
