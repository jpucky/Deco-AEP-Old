// $Log: DataRecord.java,v $
// Revision 1.2  2014/03/28 23:02:33  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.1  2013/11/21 17:40:01  tw
// Neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.4  2013/11/06 22:39:25  tw
// Neue Schnittstellentests f. flexiblere Abfragen.
//
// Revision 1.3  2013/11/06 22:15:28  tw
// Neue Schnittstellentests f. flexiblere Abfragen.
//
// Revision 1.2  2013/11/05 23:02:09  tw
// db-schnittstellen aufger�umt.
//
// Revision 1.1  2013/11/02 21:46:42  tw
// ClubBestand-DB-Schicht. 1. Schritte zur Veralgemeinerung.
//
//

package de.decodetron.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple Datentonne die eine Liste enthält. Im Moment kann ich noch nicht absehen, ob ich noch mehr
 * brauche.
 * 
 * @author Thomas Winter
 * @since 02.11.2013
 */
public class DataRecord implements Serializable {

    private List<String> listcol = null;

    public DataRecord() {
        this(10);
    }

    public List<String> getList(){
        return listcol;
    }
    
    public DataRecord(int colNr) {
        listcol = new ArrayList<String>(colNr);
    }

    public void addColItem(String o) {
        listcol.add(o);
    }

    public String getColItem(int index) {
        return (String) listcol.get(index);
    }
    
    public int getSize(){
        return listcol.size();
    }
}
