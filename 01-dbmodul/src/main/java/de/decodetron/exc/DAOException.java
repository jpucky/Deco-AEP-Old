// $Log: DAOException.java,v $
// Revision 1.1  2013/11/21 17:40:02  tw
// Neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.1  2013/11/02 21:46:42  tw
// ClubBestand-DB-Schicht. 1. Schritte zur Veralgemeinerung.
//
// Revision 1.1  2013/08/07 01:46:51  tw
// AEP LoginModul als simpelst-keine-Zusatzbibliotheken-Variante. Erster Wurf.
//
//

package de.decodetron.exc;

/**
 * @author Thomas Winter
 * @since 06.08.2013
 */
public class DAOException extends RuntimeException {

    /**
     * Constructs a DAOException with the given detail message.
     * 
     * @param message
     *            The detail message of the DAOException.
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructs a DAOException with the given root cause.
     * 
     * @param cause
     *            The root cause of the DAOException.
     */
    public DAOException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a DAOException with the given detail message and root cause.
     * 
     * @param message
     *            The detail message of the DAOException.
     * @param cause
     *            The root cause of the DAOException.
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
