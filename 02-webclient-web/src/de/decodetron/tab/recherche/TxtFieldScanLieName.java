// $Log: TxtFieldScanLieName.java,v $
// Revision 1.1  2015/12/16 20:44:58  tw
// CR 3953: Archivierung von Retourenbelegen.
//
//

package de.decodetron.tab.recherche;

import org.apache.wicket.util.value.ValueMap;

import de.decodetron.data.Util;
import de.decodetron.tab.statistik.TxtFilter;

/**
 * Textfeldkomponente für Scanbelege (Label: Lieferantenname). Ist nur sichtbar f. Scan-
 * Lieferscheine/Rechnungen.
 * 
 * @author Thomas Winter
 * @since 16.12.2015
 */
public class TxtFieldScanLieName extends TxtFilter {

    private ValueMap model;

    public TxtFieldScanLieName(String id, ValueMap m) {
        super(id);
        model = m;
        setOutputMarkupPlaceholderTag(true);
    }

    /**
     * Sichtbar, wenn Scanbeleg UND keine Scanretoure!
     */
    @Override
    public boolean isVisible() {
        return Util.isScanBeleg(model) && !Util.isScanRetourenBeleg(model);
    }

}
