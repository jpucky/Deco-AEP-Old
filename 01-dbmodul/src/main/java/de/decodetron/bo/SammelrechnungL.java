// $Log: SammelrechnungL.java,v $
// Revision 1.3  2015/07/16 22:15:27  tw
// Implementierung: Luecken-SR. Performance-Optimierung.
//
// Revision 1.2  2015/07/14 13:04:46  tw
// Implementierung: Lucken-SR. DB-Anbindung.
//
// Revision 1.1  2015/07/10 15:09:22  tw
// Implementierung: Lucken-SR. DB-Anbindung.
//
//

package de.decodetron.bo;

/**
 * Spezielles Sammelrechnungsobjekt, nur für Lückenverarbeitung gedacht.
 * 
 * @author Thomas Winter
 * @since 10.07.2015
 */
public class SammelrechnungL extends BusinessObject{

    private Long id = -1L;
    private String belegdatum = "";
    private String belegnr = "";

//    private SammelrechnungL vor;
//    private SammelrechnungL nach;

    public SammelrechnungL() {

    }

    /**
     * Nur diese 3 Attribute! Ansonsten gib das ein nicht erwünschten Deep-Clone Effekt.
     */
    public SammelrechnungL(SammelrechnungL sr) {
        this(sr.getId(), sr.getBelegdatum(), sr.getBelegnr());
    }

    public SammelrechnungL(Long id, String belegdatum, String belegnr) {
        super();
        this.id = id;
        this.belegdatum = belegdatum;
        this.belegnr = belegnr;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long i) {
        this.id = i;
    }

    /**
     * @return the belegDatum
     */
    public String getBelegdatum() {
        return belegdatum;
    }

    /**
     * @param belegDatum
     *            the belegDatum to set
     */
    public void setBelegdatum(String belegDatum) {
        this.belegdatum = belegDatum;
    }

    /**
     * @return the belegNr
     */
    public String getBelegnr() {
        return belegnr;
    }

    /**
     * @param belegNr
     *            the belegNr to set
     */
    public void setBelegnr(String belegNr) {
        this.belegnr = belegNr;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        SammelrechnungL other = (SammelrechnungL) obj;
        if (belegnr == null) {
            if (other.belegnr != null)
                return false;
        } else if (!belegnr.equals(other.belegnr))
            return false;
        return true;
    }


//    /**
//     * Die Sammelrechnung - Vorgänger.
//     * 
//     * @return the vor
//     */
//    public SammelrechnungL getVor() {
//        return vor;
//    }
//
//    /**
//     * Die Sammelrechnung - Vorgänger.
//     * 
//     * @param vor
//     *            the vor to set
//     */
//    public void setVor(SammelrechnungL vor) {
//        this.vor = vor;
//    }
//
//    /**
//     * Die Sammelrechnung - Nachfolger.
//     * 
//     * @return the nach
//     */
//    public SammelrechnungL getNach() {
//        return nach;
//    }
//
//    /**
//     * Die Sammelrechnung - Nachfolger.
//     * 
//     * @param nach
//     *            the nach to set
//     */
//    public void setNach(SammelrechnungL nach) {
//        this.nach = nach;
//    }

}
