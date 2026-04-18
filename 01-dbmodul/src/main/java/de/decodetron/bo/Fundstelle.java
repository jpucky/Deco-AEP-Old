// $Log: Fundstelle.java,v $
// Revision 1.9  2017/06/22 13:36:07  tw
// Mobilmachung der Headrevision.
//
// Revision 1.8  2016/01/11 22:50:18  tw
// CR 3956: Interner Umbau: Lucene-Felder.
//
// Revision 1.7  2015/10/19 12:58:21  tw
// CR intern: Erweiterung der DB-Schnittstelle "Fundstelle" f. Archivierungsdatum.
//
// Revision 1.6  2015/01/30 02:43:36  tw
// Haldenbearbeitung 1. Wurf.
//
// Revision 1.5  2014/10/01 11:24:31  tw
// Absplittung, Scanindex vom restlichen Index. Testanbindung.
//
// Revision 1.4  2014/07/30 16:08:54  tw
// Scanbelege, Lucene Objektmapper erstellt, Aufraeumarbeiten.
//
// Revision 1.3  2014/07/29 21:19:12  tw
// Schnittstellenanpassung Scanbelege.
//
// Revision 1.2  2014/03/27 01:20:43  tw
// Vorbereitung, CR-Defektenlisten f. Lieferanten: Markup-freie Tabellengestaltung.
//
// Revision 1.1  2014/03/01 23:58:12  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.5  2013/11/12 01:30:16  tw
// Umlaute bringen mich um!
//
// Revision 1.4  2013/11/12 00:02:51  tw
// Belegart vorerst hinzugefuegt.
//
// Revision 1.3  2013/10/25 01:50:01  tw
// Anpassung an Rollout.
//
// Revision 1.2  2013/09/24 14:02:49  tw
// Kleine Testsuche Kundenr implementiert.
//
// Revision 1.1  2013/09/18 22:04:07  tw
// Fertigstellung der Fundstellenansicht.
//
//

package de.decodetron.bo;

import java.io.Serializable;

/**
 * Fundstellen Datentonne.
 * 
 * @author Thomas Winter
 * @since 18.09.2013
 */
public class Fundstelle implements Serializable {

    // Alte Felder
    // private String id;
    // private String offset;
    // private String datum;
    // private String arcdatum;
    // private String filiale;
    // private String kundennummer;
    // private String belegnummer;
    // private String belegart;
    // private String seitenzahl;
    // private String scdokument;
    // private String lieferantenname;

    // Neue Felder seit 11.01.2016
    private String id;
    private String offset;
    private String impdate;
    private String belegart;
    private String datum;
    private String belegnummer; // Alt: Dokument bzw. scdokument
    private String kundennummer;
    private String seitenzahl;
    private String scdokument; // Alt: belegnummer
    private String lieferantenname;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the impoffset
     */
    public String getOffset() {
        return offset;
    }

    /**
     * @param impoffset
     *            the impoffset to set
     */
    public void setOffset(String impoffset) {
        this.offset = impoffset;
    }

    /**
     * @return the impdate
     */
    public String getImpdate() {
        return impdate;
    }

    /**
     * @param impdate
     *            the impdate to set
     */
    public void setImpdate(String impdate) {
        this.impdate = impdate;
    }

    /**
     * @return the belegart
     */
    public String getBelegart() {
        return belegart;
    }

    /**
     * @param belegart
     *            the belegart to set
     */
    public void setBelegart(String belegart) {
        this.belegart = belegart;
    }

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
     * @return the belegnr
     */
    public String getBelegnummer() {
        return belegnummer;
    }

    /**
     * @param belegnr
     *            the belegnr to set
     */
    public void setBelegnummer(String belegnr) {
        this.belegnummer = belegnr;
    }

    /**
     * @return the kundennr
     */
    public String getKundennummer() {
        return kundennummer;
    }

    /**
     * @param kundennr
     *            the kundennr to set
     */
    public void setKundennummer(String kundennr) {
        this.kundennummer = kundennr;
    }

    /**
     * @return the seitenzahl
     */
    public String getSeitenzahl() {
        return seitenzahl;
    }

    /**
     * @param seitenzahl
     *            the seitenzahl to set
     */
    public void setSeitenzahl(String seitenzahl) {
        this.seitenzahl = seitenzahl;
    }

    /**
     * @return the bestellung
     */
    public String getScdokument() {
        return scdokument;
    }

    /**
     * @param bestellung
     *            the bestellung to set
     */
    public void setScdokument(String bestellung) {
        this.scdokument = bestellung;
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Fundstelle other = (Fundstelle) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
