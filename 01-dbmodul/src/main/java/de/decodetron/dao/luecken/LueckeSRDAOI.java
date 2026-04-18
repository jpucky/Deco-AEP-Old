// $Log: LueckeSRDAOI.java,v $
// Revision 1.4  2015/07/20 14:07:14  tw
// Implementierung: Luecken-SR.
//
// Revision 1.3  2015/07/16 22:15:28  tw
// Implementierung: Luecken-SR. Performance-Optimierung.
//
// Revision 1.2  2015/07/14 13:04:46  tw
// Implementierung: Lucken-SR. DB-Anbindung.
//
// Revision 1.1  2015/07/10 15:09:22  tw
// Implementierung: Lucken-SR. DB-Anbindung.
//
//

package de.decodetron.dao.luecken;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import de.decodetron.bo.SammelrechnungL;

/**
 * @author Thomas Winter
 * @since 10.07.2015
 */
public interface LueckeSRDAOI {

    /**
     * Liefert eine limitierte Menge von Datensätzen. Die Hashmap hat sich performanter als eine
     * Liste herausgestellt.
     * 
     * @param Integer
     *            offset
     * @return HashMap<String, SammelrechnungL>
     */
    public HashMap<String, SammelrechnungL> getAllSRH(Integer offset);

    /**
     * Gibt die Anzahl aller SR - Datensätze.
     * 
     * @return Integer
     */
    public Integer countSR();

    /**
     * Liefert das Lueckenprotokoll als File.
     * 
     * @return File
     */
    public File getLueckenProtokollSR();

    /**
     * Generiert im Moment des Aufrufes das Protokoll.
     */
    public void generateLueckenProtokollSR();

}
