// $Log: ChargenFilterAttr.java,v $
// Revision 1.4  2020/02/26 19:24:11  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.3  2013/12/09 14:09:18  tw
// Von-Bis Suchfeld, Statistik.
//
// Revision 1.2  2013/11/25 14:55:22  tw
// Defektenliste: limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
// Revision 1.1  2013/11/25 12:22:16  tw
// Chargendoku: limitierter Datenausgabe, DB-seitige Tabellensortierung.
//
//

package de.decodetron.tab.statistik.chargen;

import java.io.Serializable;

/**
 * @author Thomas Winter
 * @since 25.11.2013
 * @deprecated
 */
public class ChargenFilterAttr implements Serializable{

    String auftragDatumVon = "";
    String auftragDatumBis = "";
    String dokumentTyp;
    String positionsTyp;
    String kundenNr;
    
    String auftragsNr;
    String lieferscheinNr;
    String picklistenNr;
    String pzn;
    
    String artikelbezeichnung;
    String menge;
    String preis;
    String betrag;
    
    String apothekenname;
    String inhaberName;
    String strasse;
    String plz;
    
    String ort;
    String chargennummer;

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
     * @return the dokumentTyp
     */
    public String getDokumentTyp() {
        return dokumentTyp == null ? "" : dokumentTyp;
    }

    /**
     * @param dokumentTyp
     *            the dokumentTyp to set
     */
    public void setDokumentTyp(String dokumentTyp) {
        this.dokumentTyp = dokumentTyp;
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
     * @return the auftragsNr
     */
    public String getAuftragsNr() {
        return auftragsNr == null ? "" : auftragsNr;
    }

    /**
     * @param auftragsNr
     *            the auftragsNr to set
     */
    public void setAuftragsNr(String auftragsNr) {
        this.auftragsNr = auftragsNr;
    }

    /**
     * @return the lieferscheinNr
     */
    public String getLieferscheinNr() {
        return lieferscheinNr == null ? "" : lieferscheinNr;
    }

    /**
     * @param lieferscheinNr
     *            the lieferscheinNr to set
     */
    public void setLieferscheinNr(String lieferscheinNr) {
        this.lieferscheinNr = lieferscheinNr;
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
     * @return the apothekenname
     */
    public String getApothekenname() {
        return apothekenname == null ? "" : apothekenname;
    }

    /**
     * @param apothekenname
     *            the apothekenname to set
     */
    public void setApothekenname(String apothekenname) {
        this.apothekenname = apothekenname;
    }

    /**
     * @return the inhaberName
     */
    public String getInhaberName() {
        return inhaberName == null ? "" : inhaberName;
    }

    /**
     * @param inhaberName
     *            the inhaberName to set
     */
    public void setInhaberName(String inhaberName) {
        this.inhaberName = inhaberName;
    }

    /**
     * @return the strasse
     */
    public String getStrasse() {
        return strasse == null ? "" : strasse;
    }

    /**
     * @param strasse
     *            the strasse to set
     */
    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    /**
     * @return the plz
     */
    public String getPlz() {
        return plz == null ? "" : plz;
    }

    /**
     * @param plz
     *            the plz to set
     */
    public void setPlz(String plz) {
        this.plz = plz;
    }

    /**
     * @return the ort
     */
    public String getOrt() {
        return ort == null ? "" : ort;
    }

    /**
     * @param ort
     *            the ort to set
     */
    public void setOrt(String ort) {
        this.ort = ort;
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
        && getMenge().isEmpty()
        && getPicklistenNr().isEmpty()
        && getPreis().isEmpty()        
        && getPzn().isEmpty()
        && getArtikelbezeichnung().isEmpty()
        && getApothekenname().isEmpty()
        && getInhaberName().isEmpty()        
        && getStrasse().isEmpty()
        && getPlz().isEmpty()
        && getOrt().isEmpty()
        && getChargennummer().isEmpty()
        ;
    }
}
