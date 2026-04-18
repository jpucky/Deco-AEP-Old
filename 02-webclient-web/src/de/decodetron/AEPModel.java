// $Log: AEPModel.java,v $
// Revision 1.5  2016/01/31 17:00:14  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.4  2015/02/03 00:52:22  tw
// Haldenbearbeitung: Einlesen v. Archivierungsdatensaetzen ermoeglicht.
//
// Revision 1.3  2014/10/24 20:38:59  tw
// Haldenstatus: Anzeige Datensaetze, Datum, Aufraeumarbeiten.
//
// Revision 1.2  2014/10/24 20:17:52  tw
// Haldenstatus: Anzeige Datensaetze, Datum.
//
// Revision 1.1  2014/08/18 12:35:15  tw
// Modelumgestaltung.
//
//

package de.decodetron;

import org.apache.wicket.util.value.ValueMap;

import de.decodetron.tab.administration.haldebearbeiten.HaldeBearbeitenModel;
import de.decodetron.tab.benutzerverwaltung.BenVerwaltungModel;
import de.decodetron.tab.recherche.RechercheModel;

/**
 * Nach diesem Muster müssen Models in Zukunft angefertigt werden.
 * 
 * @author Thomas Winter
 * @since 18.08.2014
 */
public class AEPModel extends ValueMap {

    private RechercheModel rechercheModel;
    private BenVerwaltungModel benVerwaltungModel;
    private HaldeBearbeitenModel haldeBearbeitenModel;

    public AEPModel() {
        addSubModel(rechercheModel = new RechercheModel());
        addSubModel(benVerwaltungModel = new BenVerwaltungModel());
        addSubModel(haldeBearbeitenModel = new HaldeBearbeitenModel());
    }

    private void addSubModel(ValueMap vm) {
        putAll(vm);
    }

    public RechercheModel getRechercheModel() {
        return rechercheModel;
    }

    public HaldeBearbeitenModel getHaldeBearbeitenModel() {
        return haldeBearbeitenModel;
    }

    public BenVerwaltungModel getBenVerwaltungModel() {
        return benVerwaltungModel;
    }

}
