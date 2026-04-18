// $Log: HistoryBrDAOI.java,v $
// Revision 1.3  2014/11/06 13:12:26  tw
// Testanbindung: History-DB Benutzerrechte.
//
// Revision 1.2  2014/11/04 16:40:00  tw
// Testanbindung: History-DB Benutzerrechte.
//
// Revision 1.1  2014/11/03 16:57:07  tw
// Testanbindung: History-DB Benutzerrechte.
//
//

package de.decodetron.dao.history;

import java.sql.SQLException;

import de.decodetron.bo.HistoryBr;

/**
 * @author Thomas Winter
 * @since 31.10.2014
 */
public interface HistoryBrDAOI {
    public void insert(HistoryBr h);

    public void update(HistoryBr h);

    public void delete(HistoryBr h);

    public HistoryBr findById(Long id);
}
