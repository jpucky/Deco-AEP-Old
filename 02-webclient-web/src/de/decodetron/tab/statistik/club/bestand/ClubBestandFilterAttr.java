// $Log: ClubBestandFilterAttr.java,v $
// Revision 1.3  2020/02/26 19:24:11  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.2  2013/12/09 14:09:18  tw
// Von-Bis Suchfeld, Statistik.
//
// Revision 1.1  2013/11/25 14:55:22  tw
// Defektenliste: limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
//

package de.decodetron.tab.statistik.club.bestand;

import java.io.Serializable;

/**
 * @author Thomas Winter
 * @since 25.11.2013
 * @deprecated
 */
public class ClubBestandFilterAttr implements Serializable {

    String tagesDatumVon;
    String tagesDatumBis;
    String dokumentenTyp;
    String positionsTyp;
    String lieferantNr;

    String lieferantName;
    String pzn;
    String artikelbezeichnung;
    String clubKz;

    String bestand;

    /**
     * @return the tagesDatumVon
     */
    public String getTagesDatumVon() {
        return tagesDatumVon == null ? "" : tagesDatumVon;
    }

    /**
     * @param tagesDatumVon the tagesDatumVon to set
     */
    public void setTagesDatumVon(String tagesDatumVon) {
        this.tagesDatumVon = tagesDatumVon;
    }

    /**
     * @return the tagesDatumBis
     */
    public String getTagesDatumBis() {
        return tagesDatumBis == null ? "" : tagesDatumBis;
    }

    /**
     * @param tagesDatumBis the tagesDatumBis to set
     */
    public void setTagesDatumBis(String tagesDatumBis) {
        this.tagesDatumBis = tagesDatumBis;
    }
    /**
     * @return the dokumentenTyp
     */
    public String getDokumentenTyp() {
        return dokumentenTyp == null ? "" : dokumentenTyp;
    }

    /**
     * @param dokumentenTyp
     *            the dokumentenTyp to set
     */
    public void setDokumentenTyp(String dokumentenTyp) {
        this.dokumentenTyp = dokumentenTyp;
    }

    /**
     * @return the positionsTyp
     */
    public String getPositionsTyp() {
        return positionsTyp == null ? "" : positionsTyp;
    }

    /**
     * @param positionsTyp
     *            the positionsTyp to set
     */
    public void setPositionsTyp(String positionsTyp) {
        this.positionsTyp = positionsTyp;
    }

    /**
     * @return the lieferantNr
     */
    public String getLieferantNr() {
        return lieferantNr == null ? "" : lieferantNr;
    }

    /**
     * @param lieferantNr
     *            the lieferantNr to set
     */
    public void setLieferantNr(String lieferantNr) {
        this.lieferantNr = lieferantNr;
    }

    /**
     * @return the lieferantName
     */
    public String getLieferantName() {
        return lieferantName == null ? "" : lieferantName;
    }

    /**
     * @param lieferantName
     *            the lieferantName to set
     */
    public void setLieferantName(String lieferantName) {
        this.lieferantName = lieferantName;
    }

    /**
     * @return the pzn
     */
    public String getPzn() {
        return pzn == null ? "" : pzn;
    }

    /**
     * @param pzn
     *            the pzn to set
     */
    public void setPzn(String pzn) {
        this.pzn = pzn;
    }

    /**
     * @return the artikelbezeichnung
     */
    public String getArtikelbezeichnung() {
        return artikelbezeichnung == null ? "" : artikelbezeichnung;
    }

    /**
     * @param artikelbezeichnung
     *            the artikelbezeichnung to set
     */
    public void setArtikelbezeichnung(String artikelbezeichnung) {
        this.artikelbezeichnung = artikelbezeichnung;
    }

    /**
     * @return the clubKz
     */
    public String getClubKz() {
        return clubKz == null ? "" : clubKz;
    }

    /**
     * @param clubKz
     *            the clubKz to set
     */
    public void setClubKz(String clubKz) {
        this.clubKz = clubKz;
    }

    /**
     * @return the bestand
     */
    public String getBestand() {
        return bestand == null ? "" : bestand;
    }

    /**
     * @param bestand
     *            the bestand to set
     */
    public void setBestand(String bestand) {
        this.bestand = bestand;
    }

    public boolean fieldsEmpty() {
        return

        getTagesDatumVon().isEmpty()
        && getTagesDatumBis().isEmpty()
        && getDokumentenTyp().isEmpty()
        && getPositionsTyp().isEmpty()
        && getLieferantNr().isEmpty()

        && getLieferantName().isEmpty()
        && getPzn().isEmpty()
        && getArtikelbezeichnung().isEmpty()
        && getClubKz().isEmpty()
        
        && getBestand().isEmpty()
        ;
    }

}
