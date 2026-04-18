// $Log: User.java,v $
// Revision 1.25  2016/06/28 19:33:11  tw
// BUFIX 3963: Benutzerverwaltung/Benutzer-DB =>  zusaetzliche Felder.
//
// Revision 1.24  2015/02/11 14:22:59  tw
// Bugfix: Feng-ID: 3945. Apotheker duerfen ihr Passwort nicht aendern.
//
// Revision 1.23  2014/10/28 22:44:41  tw
// Testanbindung Xml-Objektparsing, UserSectionData.
//
// Revision 1.22  2014/10/10 12:05:07  tw
// Implementierung: Benutzerrechte Vertriebsleiter.
//
// Revision 1.21  2014/09/13 15:22:39  tw
// Rechteverwaltung: Bugfix: Textfeldaenderungen werden richtig verarbeitet f. d. Liste d. geaenderten Benutzer.
//
// Revision 1.20  2014/09/12 15:03:49  tw
// Aep-Benutzerr-Rechteverwaltung: Setzten des Defaultpasswortes.
//
// Revision 1.19  2014/08/16 14:16:56  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung: Anbindung Textfelder.
//
// Revision 1.18  2014/08/08 16:04:42  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
// Revision 1.17  2014/05/09 21:00:32  tw
// Change Request: Doppelte Defektenlisten v. 09.05.2014. (2) gilt nur fuer Lieferanten.
//
// Revision 1.16  2014/05/09 20:49:27  tw
// Change Request: Doppelte Defektenlisten v. 09.05.2014. (2) gilt nur für Lieferanten.
//
// Revision 1.15  2014/04/03 14:44:50  tw
// Bean-Property-Bug behoben.
//
// Revision 1.14  2014/03/28 17:22:32  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Eingabefelder. / Neue DB-Schnittstellen.
//
// Revision 1.13  2014/03/27 01:20:43  tw
// Vorbereitung, CR-Defektenlisten f. Lieferanten: Markup-freie Tabellengestaltung.
//
// Revision 1.12  2014/02/28 15:39:49  tw
// Anpassung an neue DB-Struktur: Gebietssleiter.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.11  2014/02/26 15:00:16  tw
// Anpassung an neue DB-Struktur: Gebietssleiter.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.10  2014/02/14 17:04:14  tw
// Implementierung: Passwort zuruecksetzen.
//
// Revision 1.9  2014/02/13 13:05:46  tw
// Benutzerverwaltung, dynamischer Suchfilteraufbau, Schnittstellenanpassung.
//
// Revision 1.8  2014/02/05 13:44:04  tw
// Anpassung an neue DB-Struktur
//
// Revision 1.7  2014/01/24 16:40:12  tw
// Schnittstellen aufgeraeumt. Neue limitierte Benutzerschnittstelle.
//
// Revision 1.6  2014/01/24 13:39:06  tw
// Schnittstellen aufgeraeumt. Neue limitierte Benutzerschnittstelle.
//
// Revision 1.5  2014/01/17 21:40:13  tw
// Kommentare ergaenzt.
//
// Revision 1.4  2013/12/18 13:09:42  tw
// Altes Feld rausgeworfen.
//
// Revision 1.3  2013/12/02 21:58:18  tw
// Spalten: KundenNr, LieferscheinNr werden fuer alle Lieferanten in allen Listen gefiltert.
//
// Revision 1.2  2013/12/02 17:33:45  tw
// Vorbereitungen fuer gezieltes Spaltenfiltern.
//
// Revision 1.1  2013/11/21 17:40:01  tw
// Neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.5  2013/11/17 13:41:59  tw
// Neue Schnittstelle f. User mit mehrfachfiltern implementiert.
//
// Revision 1.4  2013/11/14 23:11:31  tw
// .
//
// Revision 1.3  2013/11/14 23:05:38  tw
// Passwort-ndern Schnittstelle
//
// Revision 1.2  2013/11/13 00:29:58  tw
// Servicefunktionen fuer Berechtigungskonzept implementiert.
//
// Revision 1.1  2013/11/02 21:46:42  tw
// ClubBestand-DB-Schicht. 1. Schritte zur Veralgemeinerung.
//
// Revision 1.2  2013/08/12 10:49:50  tw
// Neues Attribut: anlagedatum erstellt.
//
// Revision 1.1  2013/08/07 01:46:51  tw
// AEP LoginModul als simpelst-keine-Zusatzbibliotheken-Variante. Erster Wurf.
//
//

package de.decodetron.bo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.decodetron.util.Util;

/**
 * @author Thomas Winter
 * @since 06.08.2013
 */
public class User extends BusinessObject {

    private Long id;
    private String groupIds;
    private String vorname;
    private String nachname;
    private String login;
    private String passwd;
    private String anlagedatum;
    private String filter;
    private String gebiet;
    private String vertriebsleitung;

    private Boolean isLieferant = Boolean.FALSE;
    private Boolean isApotheker = Boolean.FALSE;
    private Boolean isAdministrator = Boolean.FALSE;
    private Boolean isSuperAdmin = Boolean.FALSE;
    private Boolean isVertriebsleiter = Boolean.FALSE;
    private Boolean selected = Boolean.FALSE;

    public User(Long id, String groupIds, String vorname, String nachname, String login, String passwd,
            String anlagedatum, String filter, String gebiet, String vertriebsleitung, Boolean isLieferant,
            Boolean isApotheker, Boolean isAdministrator, Boolean isSuperAdmin, Boolean isVertriebsleiter,
            Boolean selected) {
        super();
        this.id = id;
        this.groupIds = groupIds;
        this.vorname = vorname;
        this.nachname = nachname;
        this.login = login;
        this.passwd = passwd;
        this.anlagedatum = anlagedatum;
        this.filter = filter;
        this.gebiet = gebiet;
        this.vertriebsleitung = vertriebsleitung;
        this.isLieferant = isLieferant;
        this.isApotheker = isApotheker;
        this.isAdministrator = isAdministrator;
        this.isSuperAdmin = isSuperAdmin;
        this.isVertriebsleiter = isVertriebsleiter;
        this.selected = selected;
    }

    public User(User u) {
        this(u.getId(), u.getGroupIds(), u.getVorname(), u.getNachname(), u.getLogin(), u.getPasswd(), u
                .getAnlagedatum(), u.getFilter(), u.getGebiet(), u.getVertriebsleitung(), u.getIsLieferant(), u
                .getIsApotheker(), u.getIsAdministrator(), u.getIsSuperAdmin(), u.getIsVertriebsleiter(), u
                .getSelected());
    }

    public User() {
        // Für Tests und Xml Erzeugung!
    }

    /**
     * @return the anlagedatum
     */
    public String getAnlagedatum() {
        return anlagedatum;
    }

    /**
     * @param anlagedatum
     *            the anlagedatum to set
     */
    public void setAnlagedatum(String anlagedatum) {
        this.anlagedatum = anlagedatum;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long i) {
        this.id = i;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    // public void setAllFilter(List<String> allFilter) {
    // this.allFilter = allFilter;
    // }

    /**
     * Lieferanten sind jene, deren Gruppe 'LIEF' in der Kurzbeschreibung haben.
     * 
     * @return the isLieferant
     */
    public Boolean getIsLieferant() {
        return isLieferant;
    }

    /**
     * 
     * Lieferanten sind jene, deren Gruppe 'LIEF' in der Kurzbeschreibung haben.
     * 
     * @param isLieferant
     *            the isLieferant to set
     */
    public void setIsLieferant(Boolean isLieferant) {
        this.isLieferant = isLieferant;
    }

    /**
     * Benutzer der Gruppen: Admin und Superuser.
     * 
     * @return the isAdministrator
     */
    public Boolean getIsAdministrator() {
        return isAdministrator;
    }

    /**
     * Benutzer der Gruppen: Admin und Superuser.
     * 
     * @param isAdministrator
     *            the isAdministrator to set
     */
    public void setIsAdministrator(Boolean isAdministrator) {
        this.isAdministrator = isAdministrator;
    }

    /**
     * Superadmin beinhaltet Admin!
     * 
     * @return the isSuperAdmin
     */
    public Boolean getIsSuperAdmin() {
        return isSuperAdmin;
    }

    /**
     * Superadmin beinhaltet Admin!
     * 
     * @param isSuperAdmin
     *            the isSuperAdmin to set
     */
    public void setIsSuperAdmin(Boolean isSuperAdmin) {
        this.isSuperAdmin = isSuperAdmin;
    }

    /**
     * ARGH. Spaltenbezeichner und Bean-Properties passen nicht zusammen! Bean-Properties kommen mit
     * Grossbuchstaben im Funktionsnamen nicht klar! TODO: Das muss gehen!
     * 
     * @return the groupids
     */
    public String getGroupIds() {
        return groupIds;
    }

    /**
     * @param groupids
     *            the groupids to set
     */
    public void setGroupIds(String groupids) {
        this.groupIds = groupids;
    }

    /**
     * @return the filter
     */
    public String getFilter() {
        return filter;
    }

    /**
     * @see User#getFilter()
     * @return the allFilter
     */
    public List<String> getAllFilter() {
        return Util.toList(filter);
    }

    /**
     * Ermöglicht das Hinzufügen von Filtern
     * 
     * @param List
     *            <String> allFilter
     */
    public void addFilter(List<String> allFilter) {
        List<String> existingFilter = new ArrayList<String>(Util.toList(filter));
        for (Iterator<String> iterator = allFilter.iterator(); iterator.hasNext();) {
            String additionalFilter = iterator.next();
            if (!existingFilter.contains(additionalFilter)) {
                existingFilter.add(additionalFilter);
            }
        }

        StringBuffer sbKommaSepList = new StringBuffer();
        int cnt = 0;
        for (Iterator<String> iterator = existingFilter.iterator(); iterator.hasNext();) {
            String filterItem = iterator.next();
            if (filterItem != null && filterItem.length() > 0) {
                sbKommaSepList.append((cnt > 0 && cnt < existingFilter.size()) ? "," : "");
                sbKommaSepList.append(filterItem);
                cnt++;
            }
        }

        setFilter(sbKommaSepList.toString());
    }

    /**
     * @param filter
     *            the filter to set
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * @see
     * @return
     */
    public Boolean getSelected() {
        return selected;
    }

    /**
     * Dieses Feld dient im Moment dazu festzustellen, ob das Standartpasswort des Benutzers gesetzt
     * ist. TODO: Das ist mal eine Krücke gewesen und gehört hier nicht rein! Wenn das
     * Oberflächenmodul Passwort-Zurücksetzen verschwunden ist, muss auch das Feld verschwinden!
     * 
     * @param selected
     */
    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    /**
     * @return the gebiet
     */
    public String getGebiet() {
        return gebiet;
    }

    /**
     * @see User#getGebiet()
     * @return the allFilter
     */
    public List<String> getAllGebiete() {
        return Util.toList(gebiet);
    }

    /**
     * @param gebiet
     *            the gebiet to set
     */
    public void setGebiet(String gebiet) {
        this.gebiet = gebiet;
    }

    /**
     * @return the vertriebsleitung
     */
    public String getVertriebsleitung() {
        return vertriebsleitung;
    }

    /**
     * @see User#getVertriebsleitung()
     * @return the allFilter
     */
    public List<String> getAllVertriebsleitungen() {
        return Util.toList(vertriebsleitung);
    }

    /**
     * @param vertriebsleitung
     *            the vertriebsleitung to set
     */
    public void setVertriebsleitung(String vertriebsleitung) {
        this.vertriebsleitung = vertriebsleitung;
    }

    /**
     * @return the isVertriebsleiter
     */
    public Boolean getIsVertriebsleiter() {
        return isVertriebsleiter;
    }

    /**
     * @param isVertriebsleiter
     *            the isVertriebsleiter to set
     */
    public void setIsVertriebsleiter(Boolean isVertriebsleiter) {
        this.isVertriebsleiter = isVertriebsleiter;
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
        result = prime * result + ((anlagedatum == null) ? 0 : anlagedatum.hashCode());
        result = prime * result + ((filter == null) ? 0 : filter.hashCode());
        result = prime * result + ((gebiet == null) ? 0 : gebiet.hashCode());
        result = prime * result + ((groupIds == null) ? 0 : groupIds.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((isAdministrator == null) ? 0 : isAdministrator.hashCode());
        result = prime * result + ((isLieferant == null) ? 0 : isLieferant.hashCode());
        result = prime * result + ((isApotheker == null) ? 0 : isApotheker.hashCode());
        result = prime * result + ((isSuperAdmin == null) ? 0 : isSuperAdmin.hashCode());
        result = prime * result + ((isVertriebsleiter == null) ? 0 : isVertriebsleiter.hashCode());
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((nachname == null) ? 0 : nachname.hashCode());
        result = prime * result + ((passwd == null) ? 0 : passwd.hashCode());
        result = prime * result + ((selected == null) ? 0 : selected.hashCode());
        result = prime * result + ((vertriebsleitung == null) ? 0 : vertriebsleitung.hashCode());
        result = prime * result + ((vorname == null) ? 0 : vorname.hashCode());
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
        User other = (User) obj;
        if (anlagedatum == null) {
            if (other.anlagedatum != null)
                return false;
        } else if (!anlagedatum.equals(other.anlagedatum))
            return false;
        if (filter == null) {
            if (other.filter != null)
                return false;
        } else if (!filter.equals(other.filter))
            return false;
        if (gebiet == null) {
            if (other.gebiet != null)
                return false;
        } else if (!gebiet.equals(other.gebiet))
            return false;
        if (groupIds == null) {
            if (other.groupIds != null)
                return false;
        } else if (!groupIds.equals(other.groupIds))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (isAdministrator == null) {
            if (other.isAdministrator != null)
                return false;
        } else if (!isAdministrator.equals(other.isAdministrator))
            return false;
        if (isLieferant == null) {
            if (other.isLieferant != null)
                return false;
        } else if (!isLieferant.equals(other.isLieferant))
            return false;
        if (isApotheker == null) {
            if (other.isApotheker != null)
                return false;
        } else if (!isApotheker.equals(other.isApotheker))
            return false;
        if (isSuperAdmin == null) {
            if (other.isSuperAdmin != null)
                return false;
        } else if (!isSuperAdmin.equals(other.isSuperAdmin))
            return false;
        if (isVertriebsleiter == null) {
            if (other.isVertriebsleiter != null)
                return false;
        } else if (!isVertriebsleiter.equals(other.isVertriebsleiter))
            return false;
        if (login == null) {
            if (other.login != null)
                return false;
        } else if (!login.equals(other.login))
            return false;
        if (nachname == null) {
            if (other.nachname != null)
                return false;
        } else if (!nachname.equals(other.nachname))
            return false;
        if (passwd == null) {
            if (other.passwd != null)
                return false;
        } else if (!passwd.equals(other.passwd))
            return false;
        if (selected == null) {
            if (other.selected != null)
                return false;
        } else if (!selected.equals(other.selected))
            return false;
        if (vertriebsleitung == null) {
            if (other.vertriebsleitung != null)
                return false;
        } else if (!vertriebsleitung.equals(other.vertriebsleitung))
            return false;
        if (vorname == null) {
            if (other.vorname != null)
                return false;
        } else if (!vorname.equals(other.vorname))
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "User [id=" + id + ", groupIds=" + groupIds + ", vorname=" + vorname + ", nachname=" + nachname
                + ", login=" + login + ", passwd=" + passwd + ", anlagedatum=" + anlagedatum + ", filter=" + filter
                + ", gebiet=" + gebiet + ", vertriebsleitung=" + vertriebsleitung + ", isLieferant=" + isLieferant
                + ", isApotheker=" + isApotheker + ", isAdministrator=" + isAdministrator + ", isSuperAdmin="
                + isSuperAdmin + ", isVertriebsleiter=" + isVertriebsleiter + ", selected=" + selected + "]";
    }

    /**
     * @return the isApotheker
     */
    public Boolean getIsApotheker() {
        return isApotheker;
    }

    /**
     * @param isApotheker
     *            the isApotheker to set
     */
    public void setIsApotheker(Boolean isApotheker) {
        this.isApotheker = isApotheker;
    }

}
