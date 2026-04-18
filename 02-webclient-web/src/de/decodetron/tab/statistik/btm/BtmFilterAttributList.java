// $Log: BtmFilterAttributList.java,v $
// Revision 1.3  2013/12/08 17:32:19  tw
// Datepicker-Von, Statistik.
//
// Revision 1.2  2013/11/25 11:02:07  tw
// Limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.1  2013/11/22 00:49:20  tw
// Limitierter Datenausgabe: Anbindung Btm-Liste erster Wurf.
//
//

package de.decodetron.tab.statistik.btm;

import java.io.Serializable;

/**
 * Datentonne für die Texteingabefelder. Ein Anfang zum Testen ...
 * 
 * @author Thomas Winter
 * @since 21.11.2013
 */
public class BtmFilterAttributList implements Serializable {

    String auftragDatumVon = "";
    String auftragDatumBis = "";
    String chargennummer = "";
    String dokumentTyp = "";
    String positionsTyp = "";
    String kundenNr = "";
    String auftragsNr = "";
    String lieferscheinNr = "";
    String picklistenNr = "";
    String pzn = "";
    String artikelbezeichnung = "";
    String menge = "";
    String preis = "";
    String betrag = "";
    String lieferantNr = "";
    String lieferantName = "";

    /**
     * @return the auftragDatumVon
     */
    public String getAuftragDatumVon() {
        return auftragDatumVon == null ? "" : auftragDatumVon;
    }

    /**
     * @param auftragDatumVon the auftragDatumVon to set
     */
    public void setAuftragDatumVon(String auftragDatumVon) {
        this.auftragDatumVon = auftragDatumVon;
    }

    /**
     * @return the auftragDatumBis
     */
    public String getAuftragDatumBis() {
        return auftragDatumBis == null ? "" : auftragDatumBis;
    }

    /**
     * @param auftragDatumBis the auftragDatumBis to set
     */
    public void setAuftragDatumBis(String auftragDatumBis) {
        this.auftragDatumBis = auftragDatumBis;
    }
    /**
     * @param dokumentTyp
     *            the dokumentTyp to set
     */
    public void setDokumentTyp(String dokumentTyp) {
        this.dokumentTyp = dokumentTyp;
    }

    /**
     * @return the dokumentTyp
     */
    public String getDokumentTyp() {
        return dokumentTyp == null ? "" : dokumentTyp;
    }

    /**
     * @param positionsTyp
     *            the positionsTyp to set
     */
    public void setPositionsTyp(String positionsTyp) {
        this.positionsTyp = positionsTyp;
    }

    /**
     * @return the positionsTyp
     */
    public String getPositionsTyp() {
        return positionsTyp == null ? "" : positionsTyp;
    }

    /**
     * @return the kundenNr
     */
    public String getKundenNr() {
        return kundenNr == null ? "" : kundenNr;
    }

    /**
     * @param kundenNr
     *            the kundenNr to set
     */
    public void setKundenNr(String kundenNr) {
        this.kundenNr = kundenNr;
    }

    /**
     * @param auftragsNr
     *            the auftragsNr to set
     */
    public void setAuftragsNr(String auftragsNr) {
        this.auftragsNr = auftragsNr;
    }

    /**
     * @return the auftragsNr
     */
    public String getAuftragsNr() {
        return auftragsNr == null ? "" : auftragsNr;
    }

    /**
     * @return the lieferscheinNr
     */
    public String getLieferscheinNr() {
        return lieferscheinNr == null ? "" : lieferscheinNr;
    }

    /**
     * @return the picklistenNr
     */
    public String getPicklistenNr() {
        return picklistenNr == null ? "" : picklistenNr;
    }

    /**
     * @param picklistenNr
     *            the picklistenNr to set
     */
    public void setPicklistenNr(String picklistenNr) {
        this.picklistenNr = picklistenNr;
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
     * @return the menge
     */
    public String getMenge() {
        return menge == null ? "" : menge;
    }

    /**
     * @param menge
     *            the menge to set
     */
    public void setMenge(String menge) {
        this.menge = menge;
    }

    /**
     * @return the preis
     */
    public String getPreis() {
        return preis == null ? "" : preis;
    }

    /**
     * @param preis
     *            the preis to set
     */
    public void setPreis(String preis) {
        this.preis = preis;
    }

    /**
     * @return the betrag
     */
    public String getBetrag() {
        return betrag == null ? "" : betrag;
    }

    /**
     * @param betrag
     *            the betrag to set
     */
    public void setBetrag(String betrag) {
        this.betrag = betrag;
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
     * @return the chargennummer
     */
    public String getChargennummer() {
        return chargennummer == null ? "" : chargennummer;
    }

    /**
     * @param chargennummer
     *            the chargennummer to set
     */
    public void setChargennummer(String chargennummer) {
        this.chargennummer = chargennummer;
    }

    /**
     * @param lieferscheinNr
     *            the lieferscheinNr to set
     */
    public void setLieferscheinNr(String lieferscheinNr) {
        this.lieferscheinNr = lieferscheinNr;
    }

    public boolean fieldsEmpty() {
        return
        getPositionsTyp().isEmpty()
        && getKundenNr().isEmpty()
        && getDokumentTyp().isEmpty()
        && getAuftragDatumVon().isEmpty()
        && getAuftragDatumBis().isEmpty()
        && getAuftragsNr().isEmpty()
        && getLieferscheinNr().isEmpty()
        && getBetrag().isEmpty()
        && getChargennummer().isEmpty()
        && getLieferantName().isEmpty()
        && getLieferantNr().isEmpty()
        && getMenge().isEmpty()
        && getPicklistenNr().isEmpty()
        && getPreis().isEmpty()
        && getPzn().isEmpty()
        && getArtikelbezeichnung().isEmpty()
        ;
    }
}
