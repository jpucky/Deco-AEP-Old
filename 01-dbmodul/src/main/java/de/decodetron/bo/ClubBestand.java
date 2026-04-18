// $Log: ClubBestand.java,v $
// Revision 1.1  2013/11/21 17:40:01  tw
// Neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.1  2013/11/02 21:46:42  tw
// ClubBestand-DB-Schicht. 1. Schritte zur Veralgemeinerung.
//
//

package de.decodetron.bo;

/**
 * @author Thomas Winter
 * @since 02.11.2013
 */
public class ClubBestand extends BusinessObject {

    private Long id;

    /**
     * @return the datum
     */
    public String getDatum() {
        return datum;
    }

    /**
     * @param datum
     *            the datum to set
     */
    public void setDatum(String datum) {
        this.datum = datum;
    }

    /**
     * @return the posTyp
     */
    public String getPosTyp() {
        return posTyp;
    }

    /**
     * @param posTyp
     *            the posTyp to set
     */
    public void setPosTyp(String posTyp) {
        this.posTyp = posTyp;
    }

    /**
     * @return the lieferantNr
     */
    public String getLieferantNr() {
        return LieferantNr;
    }

    /**
     * @param lieferantNr
     *            the lieferantNr to set
     */
    public void setLieferantNr(String lieferantNr) {
        LieferantNr = lieferantNr;
    }

    /**
     * @return the lieferantName
     */
    public String getLieferantName() {
        return LieferantName;
    }

    /**
     * @param lieferantName
     *            the lieferantName to set
     */
    public void setLieferantName(String lieferantName) {
        LieferantName = lieferantName;
    }

    /**
     * @return the pzn
     */
    public String getPzn() {
        return pzn;
    }

    /**
     * @param pzn
     *            the pzn to set
     */
    public void setPzn(String pzn) {
        this.pzn = pzn;
    }

    /**
     * @return the artikelBezeichnung
     */
    public String getArtikelBezeichnung() {
        return artikelBezeichnung;
    }

    /**
     * @param artikelBezeichnung
     *            the artikelBezeichnung to set
     */
    public void setArtikelBezeichnung(String artikelBezeichnung) {
        this.artikelBezeichnung = artikelBezeichnung;
    }

    /**
     * @return the clubKz
     */
    public String getClubKz() {
        return ClubKz;
    }

    /**
     * @param clubKz
     *            the clubKz to set
     */
    public void setClubKz(String clubKz) {
        ClubKz = clubKz;
    }

    /**
     * @return the bestand
     */
    public String getBestand() {
        return Bestand;
    }

    /**
     * @param bestand
     *            the bestand to set
     */
    public void setBestand(String bestand) {
        Bestand = bestand;
    }

    private String datum;
    private String posTyp;
    private String LieferantNr;
    private String LieferantName;
    private String pzn;
    private String artikelBezeichnung;
    private String ClubKz;
    private String Bestand;

    public Long getId() {
        return this.id;
    }

    public void setId(Long i) {
        this.id = i;
    }
}
