// $Log: HaldeBearbeitenModel.java,v $
// Revision 1.8  2015/03/19 13:06:21  tw
// CR Feng-ID: 3947#4
//
// Revision 1.7  2015/03/17 22:10:08  tw
// CR Feng-ID: 3947
//
// Revision 1.6  2015/02/13 03:18:43  tw
// Haldenbearbeitung: Eventhandling, css.
//
// Revision 1.5  2015/02/09 12:46:32  tw
// Haldenbearbeitung: Bugfix. Aufraeumarbeiten.
//
// Revision 1.4  2015/02/08 17:12:18  tw
// Haldenbearbeitung: Datensatz-bearbeiten Aenderungserkennung impl.
//
// Revision 1.3  2015/02/08 00:21:43  tw
// Haldenbearbeitung: Datensatz-bearbeiten Aenderungserkennung impl.
//
// Revision 1.2  2015/02/04 16:23:27  tw
// Haldenbearbeitung: Speichern v. Buchungsdatensaetzen implementiert.
//
// Revision 1.1  2015/02/03 00:52:57  tw
// Haldenbearbeitung: Einlesen v. Archivierungsdatensaetzen ermoeglicht.
//
//

package de.decodetron.tab.administration.haldebearbeiten;

import java.util.HashMap;
import java.util.List;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.bo.HaldeBuchungssatzScan;
import de.decodetron.bo.HaldeFile;
import de.decodetron.tab.administration.haldebearbeiten.tblOffeneScan.HaldeFileItem;

/**
 * @author Thomas Winter
 * @since 02.02.2015
 */
public class HaldeBearbeitenModel extends ValueMap {

    public void addHaldeBuchungssaetze(List<HaldeBuchungssatzScan> list) {
        put("haldebuchungssaetze", list);
    }

    public List<HaldeBuchungssatzScan> getHaldeBuchungssaetze() {
        return (List<HaldeBuchungssatzScan>) get("haldebuchungssaetze");
    }

    public void addHaldeBuchungssaetzeOrig(List<HaldeBuchungssatzScan> list) {
        put("haldebuchungssaetzeOrig", list);
    }

    public List<HaldeBuchungssatzScan> getHaldeBuchungssaetzeOrig() {
        return (List<HaldeBuchungssatzScan>) get("haldebuchungssaetzeOrig");
    }

    public void addErrorComponent(TextField<String> c) {
        put("errorComp", c);
    }

    public TextField<String> getErrorComponent() {
        return (TextField<String>) get("errorComp");
    }

    /**
     * Eine Tonne für die geänderten Buchungsdatensätze.
     * 
     * @param list
     */
    public void initHaldeBuchungssaetzeChanged() {
        put("haldebuchungssaetzeGeandert", new HashMap<String, HaldeBuchungssatzScan>());
    }

    /**
     * Eine Tonne für die geänderten Buchungsdatensätze.
     * 
     * @return List<HaldeBuchungssatz>
     */
    public HashMap<String, HaldeBuchungssatzScan> getBuchungssaetzeChanged() {
        return (HashMap<String, HaldeBuchungssatzScan>) get("haldebuchungssaetzeGeandert");
    }

    /**
     * Speicher für das angeklickte DataItem.
     * 
     * @param HaldeFileItem
     *            <HaldeFile> hd
     */
    public void addHaldeFileItemHighlitable(HaldeFileItem<HaldeFile> hd) {
        put("toggledItem-halde", hd);
    }

    /**
     * Speicher für das angeklickte DataItem.
     * 
     * @return HaldeFileItemHighlitable<HaldeFile>
     */
    public HaldeFileItem<HaldeFile> getHaldeFileItemHighlitable() {
        return (HaldeFileItem<HaldeFile>) get("toggledItem-halde");
    }

    /**
     * Hier wird die selektierte "offene Scandatei" gespeichert.
     * 
     * @param HaldeFile
     *            hf
     */
    public void addClickedFundstelle(HaldeFile fs) {
        put("fundstelle-halde", fs);
    }

    /**
     * Liefert die selektierte "offene Scandatei".
     * 
     * @return HaldeFile
     */
    public HaldeFile getClickedFundstelle() {
        return (HaldeFile) get("fundstelle-halde");
    }

    /**
     * Um das Laden der lahmen PDF-Ansicht ein bischen zu Optimieren, wird der alte Wert gesichtert.
     * 
     * @param HaldeFile
     *            hf
     */
    public void addClickedFundstelleOld(HaldeFile fs) {
        put("oldFundstelle", fs);
    }

    /**
     * Um das Laden der lahmen PDF-Ansicht ein bischen zu Optimieren, wird der alte Wert gesichtert.
     * 
     * @return HaldeFile
     */
    public HaldeFile getClickedFundstelleOld() {
        return (HaldeFile) get("oldFundstelle");
    }

    /**
     * Die gewählte Halde-Bearbeiten Belegart.
     * 
     * @param String
     *            ba
     */
    public void setSelectedBelegart(String ba) {
        put("belegart", ba);
    }

    /**
     * Die gewählte Halde-Bearbeiten Belegart.
     * 
     * @return String
     */
    public String getSelectedBelegart() {
        return (String) get("belegart");
    }
}
