// $Log: LiefNrName.java,v $
// Revision 1.1  2015/02/19 14:33:04  tw
// Haldenbearbeitung: Aenderung des Speicherverzeichnisses.
//
//

package de.decodetron.bo;

import java.io.Serializable;

/**
 * Ablagetonne für Lieferanten.ini Lieferanten / Nr<->Name Kombinationen.
 * 
 * @author Thomas Winter
 * @since 13.02.2015
 */
public class LiefNrName implements Serializable {

    private String lieferantNr;
    private String lieferantenname;

    public LiefNrName(String lieferantNr, String lieferantenname) {
        super();
        this.lieferantNr = lieferantNr;
        this.lieferantenname = lieferantenname;
    }

    public LiefNrName() {

    }

    /**
     * @return the lieferantNr
     */
    public String getLieferantNr() {
        return lieferantNr;
    }

    /**
     * @param lieferantNr
     *            the lieferantNr to set
     */
    public void setLieferantNr(String lieferantNr) {
        this.lieferantNr = lieferantNr;
    }

    /**
     * @return the lieferantenname
     */
    public String getLieferantenname() {
        return lieferantenname;
    }

    /**
     * @param lieferantenname
     *            the lieferantenname to set
     */
    public void setLieferantenname(String lieferantenname) {
        this.lieferantenname = lieferantenname;
    }

}
