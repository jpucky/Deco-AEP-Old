// $Log: BusaTable.java,v $
// Revision 1.21  2015/04/13 12:13:18  tw
// CR Feng-ID: 3950#2
//
// Revision 1.20  2015/03/24 23:20:44  tw
// CR Feng-ID: 3947#7
//
// Revision 1.19  2015/03/23 22:47:16  tw
// CR Feng-ID: 3947#6
//
// Revision 1.18  2015/03/19 22:41:05  tw
// CR Feng-ID: 3947#4
//
// Revision 1.17  2015/03/19 13:06:21  tw
// CR Feng-ID: 3947#4
//
// Revision 1.16  2015/03/18 15:23:58  tw
// CR Feng-ID: 3947
//
// Revision 1.15  2015/03/17 22:10:09  tw
// CR Feng-ID: 3947
//
// Revision 1.14  2015/02/27 18:16:42  tw
// Haldenbearbeitung: Datepicker Bugfix.
//
// Revision 1.13  2015/02/27 17:23:48  tw
// Haldenbearbeitung: Datepicker Bugfix.
//
// Revision 1.12  2015/02/27 09:35:36  tw
// Haldenbearbeitung: Datum-Musterpruefung implementiert.
//
// Revision 1.11  2015/02/26 23:08:05  tw
// Haldenbearbeitung: Datepicker implementiert.
//
// Revision 1.10  2015/02/26 12:46:45  tw
// Haldenbearbeitung: Fehlerpruefung, Bugfixing.
//
// Revision 1.9  2015/02/19 14:33:27  tw
// Haldenbearbeitung: Aenderung des Speicherverzeichnisses.
//
// Revision 1.8  2015/02/17 21:46:26  tw
// Haldenbearbeitung: Erste Implementierung d. Eingabeueberpruefungen.
//
// Revision 1.7  2015/02/16 14:10:01  tw
// Haldenbearbeitung: Autocomplete Aufraeumarbeiten.
//
// Revision 1.6  2015/02/15 01:31:57  tw
// Haldenbearbeitung: Autocomplete die 1.
//
// Revision 1.5  2015/02/13 10:47:50  tw
// Haldenbearbeitung: LiefName hinzugefuegt.
//
// Revision 1.4  2015/02/13 03:18:43  tw
// Haldenbearbeitung: Eventhandling, css.
//
// Revision 1.3  2015/02/12 00:58:08  tw
// Haldenbearbeitung: Grossflaechiger Skriptumbau. Zwangsumstellung auf JQuery 1.7.1.
//
// Revision 1.2  2015/02/11 14:08:12  tw
// Haldenbearbeitung: Auskommentieren der Ansicht fuer Vorab-Realease.
//
// Revision 1.1  2015/02/09 12:44:28  tw
// Haldenbearbeitung: Bugfix. Aufraeumarbeiten.
//
// Revision 1.7  2015/02/08 17:12:18  tw
// Haldenbearbeitung: Datensatz-bearbeiten Aenderungserkennung impl.
//
// Revision 1.6  2015/02/08 00:21:43  tw
// Haldenbearbeitung: Datensatz-bearbeiten Aenderungserkennung impl.
//
// Revision 1.5  2015/02/07 17:07:54  tw
// Haldenbearbeitung: Datensatz-Loeschen-Schnittstelle angebunden.
//
// Revision 1.4  2015/02/05 02:32:34  tw
// Haldenbearbeitung: Speichern v. Buchungsdatensaetzen implementiert.
//
// Revision 1.3  2015/02/04 16:23:27  tw
// Haldenbearbeitung: Speichern v. Buchungsdatensaetzen implementiert.
//
// Revision 1.2  2015/02/03 21:45:31  tw
// Haldenbearbeitung: Diverse Layout-Korrekturen an Tabelle "offene Scandateien".
//
// Revision 1.1  2015/02/03 00:52:57  tw
// Haldenbearbeitung: Einlesen v. Archivierungsdatensaetzen ermoeglicht.
//
//

package de.decodetron.tab.administration.haldebearbeiten.tblBusaBearbeiten;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.resource.JQueryPluginResourceReference;

import de.decodetron.AEPApplication;
import de.decodetron.Const;
import de.decodetron.bo.HaldeBuchungssatzScan;
import de.decodetron.bo.HaldeFile;
import de.decodetron.dao.halde.HaldeDAOI;
import de.decodetron.data.Util;
import de.decodetron.event.AbstractEvent;
import de.decodetron.event.ChangeEvent;
import de.decodetron.event.EventListenerInterface;
import de.decodetron.fac.DAOFactoryFileIO;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.administration.haldebearbeiten.HaldeBearbeiten;
import de.decodetron.tab.administration.haldebearbeiten.HaldeBearbeitenModel;

/**
 * @author Thomas Winter
 * @param <T>
 * @since 02.02.2015
 */
public class BusaTable<T> extends WebMarkupContainer implements EventListenerInterface {

//    @Override
//    public void renderHead(IHeaderResponse response) {
//        response.render(OnDomReadyHeaderItem.forScript("$.fn.initDatePickerHalde()"));
//    }

    public BusaTable(String id, HaldeBearbeitenModel hbm) {
        super(id, Model.of(hbm));
        add(new BusaView("busalist", hbm));
        setOutputMarkupId(true);
    }

    /**
     * Lädt für das angeklickte HaldeFile die zugehörigen Buchungsdatensätze.
     */
    private void reloadBuchungssatzData() {

        // /////////////////////////////////////////
        // /// aktualisieren des Models
        // /// 1. Fundstelle angeklickt ...
        // /// 2. Buchungsdatensätze dafür holen ...
        // /// 3. Dem Model verklickern ...
        // /
        HaldeBearbeitenModel hbm = (HaldeBearbeitenModel) getDefaultModelObject();
        HaldeFile hf = hbm.getClickedFundstelle();

        if (hf != null) {
            String fileName = Util.getNameCutSuffix(hf.getFileName());
            String login = LoginSession.get().getUser().getLogin();
            List<HaldeBuchungssatzScan> list = getContactsDB().getBuchungsSaetze4PDF(fileName, login);
            hbm.addHaldeBuchungssaetze(list != null ? list : new ArrayList<HaldeBuchungssatzScan>());
            hbm.addHaldeBuchungssaetzeOrig(getCopyList(hbm.getHaldeBuchungssaetze()));
        }

        // Löschen der geänderten Datensätze
        hbm.initHaldeBuchungssaetzeChanged();
        // showList(hbm.getBuchungssaetzeChanged());
    }

    private List<HaldeBuchungssatzScan> getCopyList(List<HaldeBuchungssatzScan> src) {
        List<HaldeBuchungssatzScan> list = new ArrayList<HaldeBuchungssatzScan>(src.size());
        for (Iterator<HaldeBuchungssatzScan> iterator = src.iterator(); iterator.hasNext();) {
            HaldeBuchungssatzScan haldeBuchungssatz = iterator.next();
            HaldeBuchungssatzScan htmp = new HaldeBuchungssatzScan(haldeBuchungssatz);
            // htmp.setTs(System.currentTimeMillis());
            list.add(htmp);
        }
        return list;
    }

    private HaldeDAOI getContactsDB() {
        HaldeBearbeitenModel hbm = (HaldeBearbeitenModel) getDefaultModelObject();
        return AEPApplication.get().getDBHalde(hbm.getSelectedBelegart());
    }

    @Override
    public void notifyAjaxEvent(AbstractEvent event) {
        if (event instanceof ChangeEvent) {
            ChangeEvent ce = ((ChangeEvent) event);
            String ident = ce.getIdentifier();
            Object obj = ce.getChange();

            if (Const.KEY_HALDE_HALDEFILE_KLICK.equals(ident) || Const.KEY_HALDE_BUSA_ADDED.equals(ident)
                    || Const.KEY_HALDE_BUSA_DELETED.equals(ident) || Const.KEY_HALDE_BUSA_SAVED.equals(ident)) {
                reloadBuchungssatzData();
                ce.update(BusaTable.this);
            } else if (Const.KEY_HALDE_BUSA_MODIFIED.equals(ident)) {
                HaldeBuchungssatzScan hbs = (HaldeBuchungssatzScan) obj;
                rerefshHaldeBuchungssatzModel(hbs);
            }
        }
    }
    
    /**
     * Die HaldeBuchungsdatensatz-Liste der Ansicht "Archivierungsdatensatz erstellen". Sie muss in
     * einem Container gekapselt werden, um auf Aktualisierungen reagieren zu können!
     */
    private class BusaView extends ListView<HaldeBuchungssatzScan> {

        protected BusaView(String id, final HaldeBearbeitenModel hbm) {
            super(id, new LoadableDetachableModel<List<HaldeBuchungssatzScan>>() {
                @Override
                protected List<HaldeBuchungssatzScan> load() {
                    return hbm.getHaldeBuchungssaetze();
                }
            });
        }

        @Override
        protected void populateItem(final ListItem<HaldeBuchungssatzScan> item) {
            HaldeBuchungssatzScan busa = item.getModelObject();
            HaldeBearbeitenModel hbm = (HaldeBearbeitenModel) BusaTable.this.getDefaultModelObject();

            item.add(new BusaTxt("dokumentType", busa).setEnabled(false));
            item.add(new BusaTxtLieferantNr("lieferantNr", busa, item));
            item.add(new BusaTxtLieferantName("lieferantenname", busa, item));
            //item.add(new BusaDDLieferantName("lieferantenname"));

            item.add(new BusaTxtBestellNr("bestellNr", busa));
            item.add(new BusaTxtDocDate("dokDatum", busa));
            //item.add(new DeleteData("datdelete", item.getIndex(), busa));
        }

        private class DeleteData extends AjaxLink<Integer> {

            private HaldeBuchungssatzScan hbs = null;

            public DeleteData(String id, Integer index, HaldeBuchungssatzScan h) {
                super(id, Model.of(index));
                this.hbs = h;
            }

            @Override
            public void onClick(AjaxRequestTarget target) {
                getContactsDB().removeDatensatzByIndex(this.hbs, getModelObject());
                reloadBuchungssatzData();
                target.add(BusaTable.this);
                new ChangeEvent(DeleteData.this, target, null, Const.KEY_HALDE_BUSA_DELETED).fire();
            }
        }

    }

    /**
     * Überträgt den geänderten Haldenbuchungssatz in eine Liste.
     * 
     * @param HaldeBuchungssatzScan
     *            hbs
     */
    private void rerefshHaldeBuchungssatzModel(HaldeBuchungssatzScan hbs) {
        HaldeBearbeitenModel hbm = (HaldeBearbeitenModel) BusaTable.this.getDefaultModelObject();
        HashMap<String, HaldeBuchungssatzScan> listHBSChanged = hbm.getBuchungssaetzeChanged();

        List<HaldeBuchungssatzScan> listHBSOrigina = hbm.getHaldeBuchungssaetzeOrig();
        // HaldeFile hf = hbm.getClickedFundstelle();
        // String fileName = Util.getNameCutSuffix(hf.getFileName());
        // List<HaldeBuchungssatz> listHBSOrigina =
        // getContactsDB().getBuchungsSaetze4PDF(fileName);

        // System.out.println("listHBSOrigina: " + listHBSOrigina);

        String key = hbs.getSid();
        if (!listHBSChanged.containsKey(key)) {
            listHBSChanged.put(key, hbs);
        } else {
            // Bei mehr als einer Änderung ...
            HaldeBuchungssatzScan uTmp = listHBSChanged.get(key);
            if (listHBSOrigina.contains(uTmp)) {
                listHBSChanged.remove(key);
            } else {
                listHBSChanged.put(key, hbs);
            }
        }

        // showMap();
    }

    public void showMap() {
        HaldeBearbeitenModel hbm = (HaldeBearbeitenModel) getDefaultModelObject();
        HashMap<String, HaldeBuchungssatzScan> hm = hbm.getBuchungssaetzeChanged();
        Set keyset = hm.keySet();

        System.out.println("HBS : " + keyset.size());

        for (Iterator<String> iterator = keyset.iterator(); iterator.hasNext();) {
            String key = iterator.next();
            HaldeBuchungssatzScan hbs = hm.get(key);
            System.out.println("HBS : " + hbs.toString());
        }

        System.out.println("--------------------- ENDE ---------------------");
    }
}
