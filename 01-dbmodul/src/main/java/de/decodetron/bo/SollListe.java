// $Log: SollListe.java,v $
// Revision 1.1  2015/07/21 00:33:34  tw
// Implementierung: Luecken-SR.
//
//

package de.decodetron.bo;

import java.io.Serializable;

/**
 * Enthält Von - Bis Werte. Die können sowohl als String als auch als Integer übergeben werden und
 * werden synchron gehalten.
 * 
 * @author Thomas Winter
 * @since 17.07.2015
 */
public class SollListe implements Serializable {

    private String vonS;
    private String bisS;
    private Long vonI;
    private Long bisI;

    public SollListe(String vonS, String bisS) {
        super();
        this.vonS = vonS;
        this.bisS = bisS;
        this.vonI = Long.valueOf(vonS);
        this.bisI = Long.valueOf(bisS);
    }

    public SollListe(Long vonI, Long bisI) {
        super();
        this.vonI = vonI;
        this.bisI = bisI;
        this.vonS = String.valueOf(vonI);
        this.bisS = String.valueOf(bisI);
    }

    /**
     * @return the vonS
     */
    public String getVonS() {
        return vonS;
    }

    /**
     * @param vonS
     *            the vonS to set
     */
    public void setVonS(String vonS) {
        this.vonI = Long.valueOf(vonS);
        this.vonS = vonS;
    }

    /**
     * @return the bisS
     */
    public String getBisS() {
        return bisS;
    }

    /**
     * @param bisS
     *            the bisS to set
     */
    public void setBisS(String bisS) {
        this.bisI = Long.valueOf(bisS);
        this.bisS = bisS;
    }

    /**
     * @return the vonI
     */
    public Long getVonI() {
        return vonI;
    }

    /**
     * @param vonI
     *            the vonI to set
     */
    public void setVonI(Long vonI) {
        this.vonS = String.valueOf(vonI);
        this.vonI = vonI;
    }

    /**
     * @return the bisI
     */
    public Long getBisI() {
        return bisI;
    }

    /**
     * @param bisI
     *            the bisI to set
     */
    public void setBisI(Long bisI) {
        this.bisS = String.valueOf(bisI);
        this.bisI = bisI;
    }
    
    public static void main(String args[]) {
    	//String zahl = "16123218142";
    	//System.out.println(Long.valueOf(zahl));
    }
}
