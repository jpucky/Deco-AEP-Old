// $Log: HaldeBuchungssatzScan.java,v $
// Revision 1.4  2015/04/14 09:47:47  tw
// CR Feng-ID: 3950#4
//
// Revision 1.3  2015/04/10 14:21:31  tw
// CR Feng-ID: 3947#9
//
// Revision 1.2  2015/03/19 22:39:26  tw
// CR Feng-ID: 3947#4
//
// Revision 1.1  2015/03/19 13:05:23  tw
// CR Feng-ID: 3947#4
//
// Revision 1.7  2015/03/18 15:23:28  tw
// CR Feng-ID: 3947
//
// Revision 1.6  2015/02/26 12:46:28  tw
// Haldenbearbeitung: Fehlerpruefung, Bugfixing.
//
// Revision 1.5  2015/02/19 14:32:41  tw
// Haldenbearbeitung: Aenderung des Speicherverzeichnisses.
//
// Revision 1.4  2015/02/11 10:32:47  tw
// Haldenbearbeitung: Loeschen wenn letzter Ds geloescht.
//
// Revision 1.3  2015/02/08 00:21:27  tw
// Haldenbearbeitung: Datensatz-bearbeiten Aenderungserkennung impl.
//
// Revision 1.2  2015/02/05 02:32:13  tw
// Haldenbearbeitung: Speichern v. Buchungsdatensaetzen implementiert.
//
// Revision 1.1  2015/01/30 02:43:36  tw
// Haldenbearbeitung 1. Wurf.
//
//

package de.decodetron.bo;

import java.io.Serializable;

/**
 * @author Thomas Winter
 * @since 28.01.2015
 */
public class HaldeBuchungssatzScan implements Serializable {

    private String sid = "";

    // Scanfelder
    private String dokumentType = "";
    private String lieferantNr = "";
    private String lieferantenname = "";
    private String bestellNr = "";
    private String dokDatum = "";

    private String userLogin = "";
    private Long ts;

    // Dateimuster Buchungssätze Scanbelege
    public static String SAVE_FILENAME_ROOT = "05_07_Keys_";

    public HaldeBuchungssatzScan() {

    }

    public HaldeBuchungssatzScan(String sid, String dokumentType, String lieferantNr, String bestellNr,
            String dokDatum, String lieferantenname, String userLogin, Long ts) {
        super();
        this.sid = sid;
        this.dokumentType = dokumentType;
        this.lieferantNr = lieferantNr;
        this.bestellNr = bestellNr;
        this.dokDatum = dokDatum;
        this.lieferantenname = lieferantenname;
        this.userLogin = userLogin;
        this.ts = ts;
    }

    public HaldeBuchungssatzScan(HaldeBuchungssatzScan h) {
        this(h.getSid(), h.getDokumentType(), h.getLieferantNr(), h.getBestellNr(), h.getDokDatum(), h
                .getLieferantenname(), h.getUserLogin(), h.getTs());
    }

    /**
     * @return the sid
     */
    public String getSid() {
        return sid;
    }

    /**
     * @param sid
     *            the sid to set
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * Sollte dem Dokument-Filenamen entsprechen.
     * 
     * @return the dokumentType
     */
    public String getDokumentType() {
        return dokumentType;
    }

    /**
     * @param dokumentType
     *            the dokumentType to set
     */
    public void setDokumentType(String dokumentType) {
        this.dokumentType = dokumentType;
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
     * @return the bestellNr
     */
    public String getBestellNr() {
        return bestellNr;
    }

    /**
     * @param bestellNr
     *            the bestellNr to set
     */
    public void setBestellNr(String bestellNr) {
        this.bestellNr = bestellNr;
    }

    /**
     * @return the dokDatum
     */
    public String getDokDatum() {
        return dokDatum;
    }

    /**
     * Erwartet das Datum in folgendem Format: dd.MM.yyyy.
     * 
     * @param dokDatum
     *            the dokDatum to set
     */
    public void setDokDatum(String dokDatum) {
        this.dokDatum = dokDatum;
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

    /**
     * @return the ts
     */
    public Long getTs() {
        return ts;
    }

    /**
     * @param ts
     *            the ts to set
     */
    public void setTs(Long ts) {
        this.ts = ts;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "HaldeBuchungssatz [sid=" + sid + ", dokumentType=" + dokumentType + ", lieferantNr=" + lieferantNr
                + ", bestellNr=" + bestellNr + ", dokDatum=" + dokDatum + ", lieferantenname=" + lieferantenname
                + ", userLogin=" + userLogin + ", ts=" + ts + "]";
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((bestellNr == null) ? 0 : bestellNr.hashCode());
        result = prime * result + ((dokDatum == null) ? 0 : dokDatum.hashCode());
        result = prime * result + ((dokumentType == null) ? 0 : dokumentType.hashCode());
        result = prime * result + ((lieferantNr == null) ? 0 : lieferantNr.hashCode());
        result = prime * result + ((lieferantenname == null) ? 0 : lieferantenname.hashCode());
        result = prime * result + ((sid == null) ? 0 : sid.hashCode());
        result = prime * result + ((userLogin == null) ? 0 : userLogin.hashCode());
        result = prime * result + ((ts == null) ? 0 : ts.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        // Führt zu fehlerhaftem Verhalten !!!
        // if (!super.equals(obj))
        // return false;
        if (getClass() != obj.getClass())
            return false;
        HaldeBuchungssatzScan other = (HaldeBuchungssatzScan) obj;
        if (bestellNr == null) {
            if (other.bestellNr != null)
                return false;
        } else if (!bestellNr.equals(other.bestellNr))
            return false;
        if (dokDatum == null) {
            if (other.dokDatum != null)
                return false;
        } else if (!dokDatum.equals(other.dokDatum))
            return false;
        if (dokumentType == null) {
            if (other.dokumentType != null)
                return false;
        } else if (!dokumentType.equals(other.dokumentType))
            return false;
        if (lieferantNr == null) {
            if (other.lieferantNr != null)
                return false;
        } else if (!lieferantNr.equals(other.lieferantNr))
            return false;
        if (lieferantenname == null) {
            if (other.lieferantenname != null)
                return false;
        } else if (!lieferantenname.equals(other.lieferantenname))
            return false;

        // Die wird Hochgezählt !
        // if (sid == null) {
        // if (other.sid != null)
        // return false;
        // } else if (!sid.equals(other.sid))
        // return false;

        if (userLogin == null) {
            if (other.userLogin != null)
                return false;
        } else if (!userLogin.equals(other.userLogin))
            return false;

        if (ts == null) {
            if (other.ts != null)
                return false;
        } else if (!ts.equals(other.ts))
            return false;
        return true;
    }

    /**
     * @return the userLogin
     */
    public String getUserLogin() {
        return userLogin;
    }

    /**
     * @param userLogin
     *            the userLogin to set
     */
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

}
