// $Log: TxtFieldScan.java,v $
// Revision 1.6  2016/02/05 15:47:51  tw
// CR 3956: Interner Umbau: Events separiert.
//
// Revision 1.5  2016/01/19 23:15:07  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.4  2016/01/12 21:26:23  tw
// CR 3956: Interner Umbau: Lucene-Felder. Rueckbau d. Textfeld-Id Verlaengerung.
//
// Revision 1.3  2015/12/16 20:41:39  tw
// CR 3953: Archivierung von Retourenbelegen.
//
// Revision 1.2  2014/10/02 12:36:52  tw
// Lieferantenname Textfeldanbindung.
//
// Revision 1.1  2014/10/01 22:03:22  tw
// Lieferantenname Textfeldanbindung.
//
//

package de.decodetron.tab.recherche;

import org.apache.wicket.util.value.ValueMap;

import de.decodetron.data.Util;
import de.decodetron.tab.statistik.TxtFilter;

/**
 * Textfeldkomponente für Scanbelege (Label: Dokument). Bei allen andern Belegtypen blendet sie sich
 * aus.
 * 
 * @author Thomas Winter
 * @since 01.10.2014
 */
public class TxtFieldScan extends TxtFilter {

    private ValueMap model;

    public TxtFieldScan(String id, ValueMap m) {
        super(id);
        model = m;
        setOutputMarkupPlaceholderTag(true);
    }

    @Override
    public boolean isVisible() {
        // TODO: !Util.isScanRetourenBeleg => soll das so sein???
        return Util.isScanBeleg(model) && !Util.isScanRetourenBeleg(model);
    }
}
