// $Log: TierarzneiFilterAttr.java,v $
// Revision 1.3  2020/02/26 19:24:12  tw
// BUGFIX: Mangelnde Statistik-DB-Performance durch geaenderte SQL-Statements behoben: (where c.kundennr in ('xy')).
//
// Revision 1.2  2013/12/09 14:09:19  tw
// Von-Bis Suchfeld, Statistik.
//
// Revision 1.1  2013/11/28 13:42:28  tw
// Anbindung Liste:Tierarznei.
//
//

package de.decodetron.tab.statistik.tierarznei;

import java.io.Serializable;

/**
 * @author Thomas Winter
 * @since 28.11.2013
 * @deprecated
 */
public class TierarzneiFilterAttr implements Serializable{
    
    private String auftragDatumVon = "";
    private String auftragDatumBis = "";
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
    
    String chargennummer = "";

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
    
    public boolean fieldsEmpty() {
        return

        getAuftragDatumVon().isEmpty()
        && getAuftragDatumBis().isEmpty()
        && getKundenNr().isEmpty()

        && getAuftragsNr().isEmpty()
        && getLieferscheinNr().isEmpty()     
        && getPicklistenNr().isEmpty()
        && getPzn().isEmpty()

        && getArtikelbezeichnung().isEmpty()
        && getMenge().isEmpty()
        && getPreis().isEmpty()
        && getBetrag().isEmpty()

        && getLieferantNr().isEmpty()
        && getLieferantName().isEmpty()
        && getChargennummer().isEmpty()
        ;
    }
}
