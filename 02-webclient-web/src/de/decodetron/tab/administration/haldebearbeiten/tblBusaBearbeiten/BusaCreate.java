// $Log: BusaCreate.java,v $
// Revision 1.6  2015/03/24 23:20:44  tw
// CR Feng-ID: 3947#7
//
// Revision 1.5  2015/03/19 13:06:21  tw
// CR Feng-ID: 3947#4
//
// Revision 1.4  2015/03/17 22:10:09  tw
// CR Feng-ID: 3947
//
// Revision 1.3  2015/02/19 14:33:27  tw
// Haldenbearbeitung: Aenderung des Speicherverzeichnisses.
//
// Revision 1.2  2015/02/13 03:18:43  tw
// Haldenbearbeitung: Eventhandling, css.
//
// Revision 1.1  2015/02/09 12:44:28  tw
// Haldenbearbeitung: Bugfix. Aufraeumarbeiten.
//
// Revision 1.3  2015/02/08 17:12:18  tw
// Haldenbearbeitung: Datensatz-bearbeiten Aenderungserkennung impl.
//
// Revision 1.2  2015/02/08 00:21:43  tw
// Haldenbearbeitung: Datensatz-bearbeiten Aenderungserkennung impl.
//
// Revision 1.1  2015/02/07 17:05:18  tw
// Haldenbearbeitung: Datensatz-Loeschen-Schnittstelle implementiert.
//
//

package de.decodetron.tab.administration.haldebearbeiten.tblBusaBearbeiten;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.Model;

import de.decodetron.AEPApplication;
import de.decodetron.Const;
import de.decodetron.bo.HaldeBuchungssatzScan;
import de.decodetron.dao.halde.HaldeDAOI;
import de.decodetron.data.Util;
import de.decodetron.event.AbstractEvent;
import de.decodetron.event.ChangeEvent;
import de.decodetron.event.EventListenerInterface;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.administration.haldebearbeiten.HaldeBearbeitenModel;

/**
 * 
 * Der '+' Button "Buchungsdatensatz erstellen".
 * 
 * @author Thomas Winter
 * @since 07.02.2015
 */
public class BusaCreate extends AjaxLink<HaldeBearbeitenModel> implements EventListenerInterface {

    public BusaCreate(String id, HaldeBearbeitenModel model) {
        super(id, Model.of(model));
        setOutputMarkupPlaceholderTag(true);
    }

    private HaldeDAOI getContactsDB() {
        return AEPApplication.get().getDBHalde(BusaCreate.this.getModelObject().getSelectedBelegart());
    }

    @Override
    public boolean isVisible() {
        return getModelObject().getClickedFundstelle() != null ? true : false;
    }

    @Override
    public void notifyAjaxEvent(AbstractEvent event) {
        if (event instanceof ChangeEvent) {
            ChangeEvent ce = ((ChangeEvent) event);
            String ident = ce.getIdentifier();

            if (Const.KEY_HALDE_HALDEFILE_KLICK.equals(ident)) {
                ce.update(BusaCreate.this);
            }
        }
    }

    @Override
    public void onClick(AjaxRequestTarget target) {

        List<HaldeBuchungssatzScan> newList = new ArrayList<HaldeBuchungssatzScan>();
        newList.addAll(getModelObject().getHaldeBuchungssaetze());
        newList.add(createEmptyHaldeBuchungssatz());
        getContactsDB().insertBuchungsSatz(newList);

        new ChangeEvent(BusaCreate.this, target, null, Const.KEY_HALDE_BUSA_ADDED).fire();
    }

    /**
     * Erzeugt einen leeren Buchungssatz, nur mit dem Doktype, gebildet aus dem Dateinamen.
     * 
     * @return HaldeBuchungssatz
     */
    private HaldeBuchungssatzScan createEmptyHaldeBuchungssatz() {
        HaldeBearbeitenModel m = BusaCreate.this.getModelObject();
        HaldeBuchungssatzScan hbs = new HaldeBuchungssatzScan();
        hbs.setUserLogin(LoginSession.get().getUser().getLogin());
        hbs.setDokumentType(Util.getNameCutSuffix(m.getClickedFundstelle().getFileName()));
        return hbs;
    }

}
