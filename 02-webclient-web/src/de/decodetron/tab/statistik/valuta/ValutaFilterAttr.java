// $Log: ValutaFilterAttr.java,v $
// Revision 1.2  2013/12/09 14:39:20  tw
// Von-Bis Suchfeld, Statistik.
//
// Revision 1.1  2013/11/29 12:32:37  tw
// Neue Liste: Valuta.
//
//

package de.decodetron.tab.statistik.valuta;

import java.io.Serializable;

/**
 * @author Thomas Winter
 * @since 29.11.2013
 */
public class ValutaFilterAttr implements Serializable {

    private String tagesDatumVon;
    private String tagesDatumBis;
    private String kundenNr = "";
    private String auftragsNr = "";
    private String lieferscheinNr = "";

    private String faelligkeit = "";
    private String betrag = "";

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
     * @return the faelligkeit
     */
    public String getFaelligkeit() {
        return faelligkeit == null ? "" : faelligkeit;
    }

    /**
     * @param faelligkeit
     *            the faelligkeit to set
     */
    public void setFaelligkeit(String faelligkeit) {
        this.faelligkeit = faelligkeit;
    }

    public boolean fieldsEmpty() {
        return
        getTagesDatumVon().isEmpty()
        && getTagesDatumBis().isEmpty()
        && getKundenNr().isEmpty()
        && getAuftragsNr().isEmpty()
        && getLieferscheinNr().isEmpty()
        && getFaelligkeit().isEmpty()
        && getBetrag().isEmpty()
        ;
    }
}
