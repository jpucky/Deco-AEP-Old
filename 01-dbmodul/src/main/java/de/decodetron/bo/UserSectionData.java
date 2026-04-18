// $Log: UserSectionData.java,v $
// Revision 1.11  2014/11/03 16:57:06  tw
// Testanbindung: History-DB Benutzerrechte.
//
// Revision 1.10  2014/10/28 22:44:41  tw
// Testanbindung Xml-Objektparsing, UserSectionData.
//
// Revision 1.9  2014/10/10 12:05:07  tw
// Implementierung: Benutzerrechte Vertriebsleiter.
//
// Revision 1.8  2014/09/12 15:03:49  tw
// Aep-Benutzerr-Rechteverwaltung: Setzten des Defaultpasswortes.
//
// Revision 1.7  2014/08/16 14:16:56  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung: Anbindung Textfelder.
//
// Revision 1.6  2014/08/15 14:39:36  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.5  2014/08/15 10:47:40  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.4  2014/08/14 16:04:52  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.3  2014/08/14 01:15:24  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.2  2014/08/12 01:03:12  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.1  2014/08/11 12:31:51  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
//

package de.decodetron.bo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Die Klasse wurde erstellt um die Benutzerrechte-GUI zu bedienen. Da die Modelle in Wicket
 * überwiegend Resource-Modelle sind, ist das der einfachste Weg. Vorsicht beim Ändern von hashSet
 * und equals !!!. In der Benutzerrechteverwaltung sollen nur die Benutzer zur DB geschickt werden
 * die auch geändert wurden. Das zu erkennen, erfordert sorgfältiges überschreiben der Methoden und
 * Auswahl der richtigen Felder.<br>
 * <br>
 * Die Felder werden per Reflektion befüllt und erkannt. Sollte eine neue Sektion dazukommen, muss
 * geprüft werden, was mit ihr passieren soll. Siehe: CheckBoxColumnSection#newPropertyModel(T
 * rowObject).
 * 
 * @author Thomas Winter
 * @since 07.08.2014
 */
@XmlRootElement
public class UserSectionData extends User {

    // TODO: Reflektion. Hier mal weiterbohren
    // Wie man die Methoden zur Laufzeit erzeugt !!!
    // private Boolean verver = Boolean.FALSE;
    // private Boolean btm = Boolean.FALSE;
    // private Boolean reimp = Boolean.FALSE;
    // private Boolean tfg = Boolean.FALSE;
    // private Boolean tierarz = Boolean.FALSE;
    //
    // private Boolean hochpr = Boolean.FALSE;
    // private Boolean valuta = Boolean.FALSE;
    // private Boolean clubbestand = Boolean.FALSE;
    // private Boolean clubabver = Boolean.FALSE;
    // private Boolean chargen = Boolean.FALSE;
    //
    // private Boolean uebe = Boolean.FALSE;
    // private Boolean ls = Boolean.FALSE;
    // private Boolean nb = Boolean.FALSE;
    // private Boolean gs = Boolean.FALSE;
    // private Boolean ek = Boolean.FALSE;
    //
    // private Boolean sr = Boolean.FALSE;
    // private Boolean sclf = Boolean.FALSE;
    // private Boolean scrg = Boolean.FALSE;
    // private Boolean scrt = Boolean.FALSE;
    // private Boolean scsd = Boolean.FALSE;
    //
    // private Boolean pwz = Boolean.FALSE;
    // private Boolean ververl = Boolean.FALSE;
    // private Boolean bea = Boolean.FALSE;
    // private Boolean brv = Boolean.FALSE;

    private SectionInfo ververl = new SectionInfo(-1, Boolean.FALSE);
    private SectionInfo verver = new SectionInfo(-1, Boolean.FALSE);
    private SectionInfo btm = new SectionInfo(-1, Boolean.FALSE);
    private SectionInfo reimp = new SectionInfo(-1, Boolean.FALSE);
    private SectionInfo tfg = new SectionInfo(-1, Boolean.FALSE);
    private SectionInfo tierarz = new SectionInfo(-1, Boolean.FALSE);

    private SectionInfo hochpr = new SectionInfo(-1, Boolean.FALSE);
    private SectionInfo valuta = new SectionInfo(-1, Boolean.FALSE);
    private SectionInfo clubbestand = new SectionInfo(-1, Boolean.FALSE);
    private SectionInfo clubabver = new SectionInfo(-1, Boolean.FALSE);
    private SectionInfo chargen = new SectionInfo(-1, Boolean.FALSE);

    private SectionInfo uebe = new SectionInfo(-1, Boolean.FALSE);
    private SectionInfo ls = new SectionInfo(-1, Boolean.FALSE);
    private SectionInfo nb = new SectionInfo(-1, Boolean.FALSE);
    private SectionInfo gs = new SectionInfo(-1, Boolean.FALSE);
    private SectionInfo ek = new SectionInfo(-1, Boolean.FALSE);

    private SectionInfo sr = new SectionInfo(-1, Boolean.FALSE);
    private SectionInfo sclf = new SectionInfo(-1, Boolean.FALSE);
    private SectionInfo scrg = new SectionInfo(-1, Boolean.FALSE);
    private SectionInfo scrt = new SectionInfo(-1, Boolean.FALSE);
    private SectionInfo scsd = new SectionInfo(-1, Boolean.FALSE);

    private SectionInfo pwz = new SectionInfo(-1, Boolean.FALSE);
    private SectionInfo bea = new SectionInfo(-1, Boolean.FALSE);
    private SectionInfo brv = new SectionInfo(-1, Boolean.FALSE);
    private SectionInfo has = new SectionInfo(-1, Boolean.FALSE);
    private SectionInfo bes = new SectionInfo(-1, Boolean.FALSE);

    private String tmpgroupname = null;
    private Boolean isDefaultPasswd = Boolean.TRUE;

    public UserSectionData() {
        this(new User());
    }

    public UserSectionData(User u) {
        super(u);
    }

    /**
     * @return the verver
     */
    public SectionInfo getVerver() {
        return verver;
    }

    /**
     * @param verver
     *            the verver to set
     */
    public void setVerver(SectionInfo verver) {
        this.verver = verver;
    }

    /**
     * @return the btm
     */
    public SectionInfo getBtm() {
        return btm;
    }

    /**
     * @param btm
     *            the btm to set
     */
    public void setBtm(SectionInfo btm) {
        this.btm = btm;
    }

    /**
     * @return the reimp
     */
    public SectionInfo getReimp() {
        return reimp;
    }

    /**
     * @param reimp
     *            the reimp to set
     */
    public void setReimp(SectionInfo reimp) {
        this.reimp = reimp;
    }

    /**
     * @return the tfg
     */
    public SectionInfo getTfg() {
        return tfg;
    }

    /**
     * @param tfg
     *            the tfg to set
     */
    public void setTfg(SectionInfo tfg) {
        this.tfg = tfg;
    }

    /**
     * @return the tierarz
     */
    public SectionInfo getTierarz() {
        return tierarz;
    }

    /**
     * @param tierarz
     *            the tierarz to set
     */
    public void setTierarz(SectionInfo tierarz) {
        this.tierarz = tierarz;
    }

    /**
     * @return the hochpr
     */
    public SectionInfo getHochpr() {
        return hochpr;
    }

    /**
     * @param hochpr
     *            the hochpr to set
     */
    public void setHochpr(SectionInfo hochpr) {
        this.hochpr = hochpr;
    }

    /**
     * @return the valuta
     */
    public SectionInfo getValuta() {
        return valuta;
    }

    /**
     * @param valuta
     *            the valuta to set
     */
    public void setValuta(SectionInfo valuta) {
        this.valuta = valuta;
    }

    /**
     * @return the clubbestand
     */
    public SectionInfo getClubbestand() {
        return clubbestand;
    }

    /**
     * @param clubbestand
     *            the clubbestand to set
     */
    public void setClubbestand(SectionInfo clubbestand) {
        this.clubbestand = clubbestand;
    }

    /**
     * @return the clubabver
     */
    public SectionInfo getClubabver() {
        return clubabver;
    }

    /**
     * @param clubabver
     *            the clubabver to set
     */
    public void setClubabver(SectionInfo clubabver) {
        this.clubabver = clubabver;
    }

    /**
     * @return the chargen
     */
    public SectionInfo getChargen() {
        return chargen;
    }

    /**
     * @param chargen
     *            the chargen to set
     */
    public void setChargen(SectionInfo chargen) {
        this.chargen = chargen;
    }

    /**
     * @return the uebe
     */
    public SectionInfo getUebe() {
        return uebe;
    }

    /**
     * @param uebe
     *            the uebe to set
     */
    public void setUebe(SectionInfo uebe) {
        this.uebe = uebe;
    }

    /**
     * @return the ls
     */
    public SectionInfo getLs() {
        return ls;
    }

    /**
     * @param ls
     *            the ls to set
     */
    public void setLs(SectionInfo ls) {
        this.ls = ls;
    }

    /**
     * @return the nb
     */
    public SectionInfo getNb() {
        return nb;
    }

    /**
     * @param nb
     *            the nb to set
     */
    public void setNb(SectionInfo nb) {
        this.nb = nb;
    }

    /**
     * @return the gs
     */
    public SectionInfo getGs() {
        return gs;
    }

    /**
     * @param gs
     *            the gs to set
     */
    public void setGs(SectionInfo gs) {
        this.gs = gs;
    }

    /**
     * @return the ek
     */
    public SectionInfo getEk() {
        return ek;
    }

    /**
     * @param ek
     *            the ek to set
     */
    public void setEk(SectionInfo ek) {
        this.ek = ek;
    }

    /**
     * @return the sr
     */
    public SectionInfo getSr() {
        return sr;
    }

    /**
     * @param sr
     *            the sr to set
     */
    public void setSr(SectionInfo sr) {
        this.sr = sr;
    }

    /**
     * @return the sclf
     */
    public SectionInfo getSclf() {
        return sclf;
    }

    /**
     * @param sclf
     *            the sclf to set
     */
    public void setSclf(SectionInfo sclf) {
        this.sclf = sclf;
    }

    /**
     * @return the scrg
     */
    public SectionInfo getScrg() {
        return scrg;
    }

    /**
     * @param scrg
     *            the scrg to set
     */
    public void setScrg(SectionInfo scrg) {
        this.scrg = scrg;
    }

    /**
     * @return the scrt
     */
    public SectionInfo getScrt() {
        return scrt;
    }

    /**
     * @param scrt
     *            the scrt to set
     */
    public void setScrt(SectionInfo scrt) {
        this.scrt = scrt;
    }

    /**
     * @return the scsd
     */
    public SectionInfo getScsd() {
        return scsd;
    }

    /**
     * @param scsd
     *            the scsd to set
     */
    public void setScsd(SectionInfo scsd) {
        this.scsd = scsd;
    }

    /**
     * @return the pwz
     */
    public SectionInfo getPwz() {
        return pwz;
    }

    /**
     * @param pwz
     *            the pwz to set
     */
    public void setPwz(SectionInfo pwz) {
        this.pwz = pwz;
    }

    /**
     * @return the ververl
     */
    public SectionInfo getVerverl() {
        return ververl;
    }

    /**
     * @param ververl
     *            the ververl to set
     */
    public void setVerverl(SectionInfo ververl) {
        this.ververl = ververl;
    }

    /**
     * @return the bea
     */
    public SectionInfo getBea() {
        return bea;
    }

    /**
     * @param bea
     *            the bea to set
     */
    public void setBea(SectionInfo bea) {
        this.bea = bea;
    }

    /**
     * @return the brv
     */
    public SectionInfo getBrv() {
        return brv;
    }

    /**
     * @param brv
     *            the brv to set
     */
    public void setBrv(SectionInfo brv) {
        this.brv = brv;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "UserSectionData [ververl=" + ververl + ", verver=" + verver + ", btm=" + btm + ", reimp=" + reimp
                + ", tfg=" + tfg + ", tierarz=" + tierarz + ", hochpr=" + hochpr + ", valuta=" + valuta
                + ", clubbestand=" + clubbestand + ", clubabver=" + clubabver + ", chargen=" + chargen + ", uebe="
                + uebe + ", ls=" + ls + ", nb=" + nb + ", gs=" + gs + ", ek=" + ek + ", sr=" + sr + ", sclf=" + sclf
                + ", scrg=" + scrg + ", scrt=" + scrt + ", scsd=" + scsd + ", pwz=" + pwz + ", bea=" + bea + ", brv="
                + brv + ", tmpgroupname=" + tmpgroupname + "]" + super.toString();
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
        result = prime * result + ((bea == null) ? 0 : bea.hashCode());
        result = prime * result + ((brv == null) ? 0 : brv.hashCode());
        result = prime * result + ((btm == null) ? 0 : btm.hashCode());
        result = prime * result + ((chargen == null) ? 0 : chargen.hashCode());
        result = prime * result + ((clubabver == null) ? 0 : clubabver.hashCode());
        result = prime * result + ((clubbestand == null) ? 0 : clubbestand.hashCode());
        result = prime * result + ((ek == null) ? 0 : ek.hashCode());
        result = prime * result + ((gs == null) ? 0 : gs.hashCode());
        result = prime * result + ((hochpr == null) ? 0 : hochpr.hashCode());
        result = prime * result + ((ls == null) ? 0 : ls.hashCode());
        result = prime * result + ((nb == null) ? 0 : nb.hashCode());
        result = prime * result + ((pwz == null) ? 0 : pwz.hashCode());
        result = prime * result + ((reimp == null) ? 0 : reimp.hashCode());
        result = prime * result + ((sclf == null) ? 0 : sclf.hashCode());
        result = prime * result + ((scrg == null) ? 0 : scrg.hashCode());
        result = prime * result + ((scrt == null) ? 0 : scrt.hashCode());
        result = prime * result + ((scsd == null) ? 0 : scsd.hashCode());
        result = prime * result + ((sr == null) ? 0 : sr.hashCode());
        result = prime * result + ((tfg == null) ? 0 : tfg.hashCode());
        result = prime * result + ((tierarz == null) ? 0 : tierarz.hashCode());
        result = prime * result + ((uebe == null) ? 0 : uebe.hashCode());
        result = prime * result + ((valuta == null) ? 0 : valuta.hashCode());
        result = prime * result + ((verver == null) ? 0 : verver.hashCode());
        result = prime * result + ((ververl == null) ? 0 : ververl.hashCode());
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
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserSectionData other = (UserSectionData) obj;
        if (bea == null) {
            if (other.bea != null)
                return false;
        } else if (!bea.equals(other.bea))
            return false;
        if (brv == null) {
            if (other.brv != null)
                return false;
        } else if (!brv.equals(other.brv))
            return false;
        if (btm == null) {
            if (other.btm != null)
                return false;
        } else if (!btm.equals(other.btm))
            return false;
        if (chargen == null) {
            if (other.chargen != null)
                return false;
        } else if (!chargen.equals(other.chargen))
            return false;
        if (clubabver == null) {
            if (other.clubabver != null)
                return false;
        } else if (!clubabver.equals(other.clubabver))
            return false;
        if (clubbestand == null) {
            if (other.clubbestand != null)
                return false;
        } else if (!clubbestand.equals(other.clubbestand))
            return false;
        if (ek == null) {
            if (other.ek != null)
                return false;
        } else if (!ek.equals(other.ek))
            return false;
        if (gs == null) {
            if (other.gs != null)
                return false;
        } else if (!gs.equals(other.gs))
            return false;
        if (hochpr == null) {
            if (other.hochpr != null)
                return false;
        } else if (!hochpr.equals(other.hochpr))
            return false;
        if (ls == null) {
            if (other.ls != null)
                return false;
        } else if (!ls.equals(other.ls))
            return false;
        if (nb == null) {
            if (other.nb != null)
                return false;
        } else if (!nb.equals(other.nb))
            return false;
        if (pwz == null) {
            if (other.pwz != null)
                return false;
        } else if (!pwz.equals(other.pwz))
            return false;
        if (reimp == null) {
            if (other.reimp != null)
                return false;
        } else if (!reimp.equals(other.reimp))
            return false;
        if (sclf == null) {
            if (other.sclf != null)
                return false;
        } else if (!sclf.equals(other.sclf))
            return false;
        if (scrg == null) {
            if (other.scrg != null)
                return false;
        } else if (!scrg.equals(other.scrg))
            return false;
        if (scrt == null) {
            if (other.scrt != null)
                return false;
        } else if (!scrt.equals(other.scrt))
            return false;
        if (scsd == null) {
            if (other.scsd != null)
                return false;
        } else if (!scsd.equals(other.scsd))
            return false;
        if (sr == null) {
            if (other.sr != null)
                return false;
        } else if (!sr.equals(other.sr))
            return false;
        if (tfg == null) {
            if (other.tfg != null)
                return false;
        } else if (!tfg.equals(other.tfg))
            return false;
        if (tierarz == null) {
            if (other.tierarz != null)
                return false;
        } else if (!tierarz.equals(other.tierarz))
            return false;
        if (uebe == null) {
            if (other.uebe != null)
                return false;
        } else if (!uebe.equals(other.uebe))
            return false;
        if (valuta == null) {
            if (other.valuta != null)
                return false;
        } else if (!valuta.equals(other.valuta))
            return false;
        if (verver == null) {
            if (other.verver != null)
                return false;
        } else if (!verver.equals(other.verver))
            return false;
        if (ververl == null) {
            if (other.ververl != null)
                return false;
        } else if (!ververl.equals(other.ververl))
            return false;
        return true;
    }

    /**
     * @return the testField
     */
    public String getTmpgroupname() {
        return tmpgroupname;
    }

    /**
     * Das Feld dient dazu den zukünftigen Gruppennamen an der Oberfläche anzuzeigen.
     * 
     * @param testField
     *            the testField to set
     */
    public void setTmpgroupname(String testField) {
        this.tmpgroupname = testField;
    }

    /**
     * @return the isDefaultPasswd
     */
    public Boolean getIsDefaultPasswd() {
        return isDefaultPasswd;
    }

    /**
     * Vorsicht, das Feld ist momentan redundant, siehe {@link User#setSelected(Boolean)}.
     * 
     * @param isDefaultPasswd
     *            the isDefaultPasswd to set
     */
    public void setIsDefaultPasswd(Boolean isDefaultPasswd) {
        this.isDefaultPasswd = isDefaultPasswd;
    }

    /**
     * @return the has
     */
    public SectionInfo getHas() {
        return has;
    }

    /**
     * @param has
     *            the has to set
     */
    public void setHas(SectionInfo has) {
        this.has = has;
    }

    /**
     * @return the bes
     */
    public SectionInfo getBes() {
        return bes;
    }

    /**
     * @param bes
     *            the bes to set
     */
    public void setBes(SectionInfo bes) {
        this.bes = bes;
    }

}
