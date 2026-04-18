// $Log: LueckenSRTest.java,v $
// Revision 1.4  2015/07/21 08:36:04  tw
// .
//
// Revision 1.3  2015/07/20 14:07:14  tw
// Implementierung: Luecken-SR.
//
// Revision 1.2  2015/07/14 13:04:47  tw
// Implementierung: Lucken-SR. DB-Anbindung.
//
// Revision 1.1  2015/07/10 15:09:22  tw
// Implementierung: Lucken-SR. DB-Anbindung.
//
//

package de.decodetron;

import java.io.File;

import de.decodetron.dao.luecken.LueckeSRDAO;
import junit.framework.TestCase;

/**
 * Hat vorerst nur zur Entwicklung gedient.
 * 
 * @author Thomas Winter
 * @since 10.07.2015
 */
public class LueckenSRTest extends TestCase {

    public void testDummy(){
        
    }
    
//     public void testTriggerListGen() {
//     new LueckeSRDAO().generateLueckenProtokollSR();
//     }
    
    // public void testCount() {
    // Integer cnt = new LueckeSRDAO().countSR();
    // assertTrue(cnt > 0);
    // }
    //
//     public void testOutFile() {
//     File file = new LueckeSRDAO().getLueckenProtokollSR();
//     assertTrue(file.exists());
//     }

}
