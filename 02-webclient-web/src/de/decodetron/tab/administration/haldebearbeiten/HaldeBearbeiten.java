// $Log: HaldeBearbeiten.java,v $
// Revision 1.28  2015/07/21 17:59:52  tw
// Korrektur d. Haldetests.
//
// Revision 1.27  2015/04/11 11:31:12  tw
// .
//
// Revision 1.26  2015/03/24 23:20:44  tw
// CR Feng-ID: 3947#7
//
// Revision 1.25  2015/03/19 22:41:04  tw
// CR Feng-ID: 3947#4
//
// Revision 1.24  2015/03/19 13:06:21  tw
// CR Feng-ID: 3947#4
//
// Revision 1.23  2015/03/18 15:23:58  tw
// CR Feng-ID: 3947
//
// Revision 1.22  2015/03/17 22:10:08  tw
// CR Feng-ID: 3947
//
// Revision 1.21  2015/03/12 13:07:02  tw
// Ticket: Haldenbearbeitung erweitern, Vorbereitungen.
//
// Revision 1.20  2015/02/27 17:23:48  tw
// Haldenbearbeitung: Datepicker Bugfix.
//
// Revision 1.19  2015/02/26 23:08:05  tw
// Haldenbearbeitung: Datepicker implementiert.
//
// Revision 1.18  2015/02/19 21:41:23  tw
// Haldenbearbeitung: Inititialisierung Lieferanten.ini. Bugix Tabellenscrollbar.
//
// Revision 1.17  2015/02/19 21:23:25  tw
// Haldenbearbeitung: Inititialisierung Lieferanten.ini. Bugix Tabellenscrollbar.
//
// Revision 1.16  2015/02/17 21:46:26  tw
// Haldenbearbeitung: Erste Implementierung d. Eingabeueberpruefungen.
//
// Revision 1.15  2015/02/13 03:18:43  tw
// Haldenbearbeitung: Eventhandling, css.
//
// Revision 1.14  2015/02/12 00:58:08  tw
// Haldenbearbeitung: Grossflaechiger Skriptumbau. Zwangsumstellung auf JQuery 1.7.1.
//
// Revision 1.13  2015/02/11 14:08:12  tw
// Haldenbearbeitung: Auskommentieren der Ansicht fuer Vorab-Realease.
//
// Revision 1.12  2015/02/09 12:46:32  tw
// Haldenbearbeitung: Bugfix. Aufraeumarbeiten.
//
// Revision 1.11  2015/02/08 17:12:18  tw
// Haldenbearbeitung: Datensatz-bearbeiten Aenderungserkennung impl.
//
// Revision 1.10  2015/02/08 00:21:43  tw
// Haldenbearbeitung: Datensatz-bearbeiten Aenderungserkennung impl.
//
// Revision 1.9  2015/02/07 17:07:54  tw
// Haldenbearbeitung: Datensatz-Loeschen-Schnittstelle angebunden.
//
// Revision 1.8  2015/02/05 02:32:34  tw
// Haldenbearbeitung: Speichern v. Buchungsdatensaetzen implementiert.
//
// Revision 1.7  2015/02/04 20:45:29  tw
// Haldenbearbeitung: Speichern v. Buchungsdatensaetzen implementiert.
//
// Revision 1.6  2015/02/04 16:23:27  tw
// Haldenbearbeitung: Speichern v. Buchungsdatensaetzen implementiert.
//
// Revision 1.5  2015/02/03 00:52:22  tw
// Haldenbearbeitung: Einlesen v. Archivierungsdatensaetzen ermoeglicht.
//
// Revision 1.4  2015/02/01 11:08:42  tw
// Haldenbearbeitung Korrektur. f. Produktionseinsatz.
//
// Revision 1.3  2015/01/31 17:53:03  tw
// Haldenbearbeitung Korrektur. f. Produktionseinsatz.
//
// Revision 1.2  2015/01/30 21:24:31  tw
// Haldenbearbeitung 1. Wurf.
//
// Revision 1.1  2015/01/30 02:44:52  tw
// Haldenbearbeitung 1. Wurf.
//
//

package de.decodetron.tab.administration.haldebearbeiten;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.resource.JQueryPluginResourceReference;

import de.decodetron.AEPApplication;
import de.decodetron.AEPModel;
import de.decodetron.Const;
import de.decodetron.bo.HaldeBuchungssatzScan;
import de.decodetron.bo.HaldeFile;
import de.decodetron.dao.halde.HaldeDAOI;
import de.decodetron.event.ChangeEvent;
import de.decodetron.tab.administration.haldebearbeiten.tblBusaBearbeiten.BusaForm;
import de.decodetron.tab.administration.haldebearbeiten.tblOffeneScan.HaldeFileTable;
import de.decodetron.tab.recherche.LblTotal;

/**
 * Dateimuster, Scan-Buchungssätze: 05_07_Keys_AEPL-AEPR_vom_20150410.csv <br>
 * 
 * @author Thomas Winter
 * @since 29.01.2015
 */
public class HaldeBearbeiten extends Panel {

    private StringBuilder values = new StringBuilder();
    private static Logger log = Logger.getLogger(HaldeBearbeiten.class);

    // public static Map<String, IHaldeConfig> hmDBConfig = new HashMap<String, IHaldeConfig>();

    @Override
    public void renderHead(IHeaderResponse response) {

//        // ///////////////////////
//        // /// Fixierter Tabellenkopf ...
//        // /
//        //response.render(CssHeaderItem.forReference(new CssResourceReference(HaldeBearbeiten.class,
//        //        "/../../../../../css/tableDefaultTheme.css")));
//		response.render(CssHeaderItem.forReference(new CssResourceReference(AEPApplication.class,
//				AEPApplication.SCRIPT_ROOT_PATH + "/css/tableDefaultTheme.css")));
//        //response.render(JavaScriptHeaderItem.forReference(new JQueryPluginResourceReference(HaldeBearbeiten.class,
//        //        "/../../../../../js/jquery.fixedheadertable.js")));
//		response.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(AEPApplication.class,
//				AEPApplication.SCRIPT_ROOT_PATH + "/js/jquery.fixedheadertable.js")));
//        // response.render(OnDomReadyHeaderItem.forScript("$.fn.initFixedTableArcNew()"));
//        response.render(OnDomReadyHeaderItem.forScript("$.fn.initFixedTableHeaderRec()"));
//        
//        initDatePicker(response);
    }

    public static void initDatePicker(IHeaderResponse response) {
    	
		response.render(CssHeaderItem.forReference(new CssResourceReference(AEPApplication.class,
				AEPApplication.SCRIPT_ROOT_PATH + "/themes/base/jquery.ui.all.css")));
		response.render(CssHeaderItem.forReference(new CssResourceReference(AEPApplication.class,
				AEPApplication.SCRIPT_ROOT_PATH + "/demos/demos.css")));
		
		response.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(AEPApplication.class,
				AEPApplication.SCRIPT_ROOT_PATH + "/ui/jquery.ui.core.js")));
		response.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(AEPApplication.class,
				AEPApplication.SCRIPT_ROOT_PATH + "/ui/jquery.ui.widget.js")));
		response.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(AEPApplication.class,
				AEPApplication.SCRIPT_ROOT_PATH + "/ui/jquery.ui.datepicker.js")));
		response.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(AEPApplication.class,
				AEPApplication.SCRIPT_ROOT_PATH + "/ui/i18n/jquery.ui.datepicker-de.js")));
    }

    public HaldeBearbeiten(String id, IModel<?> mm) {
        super(id, mm);

        add(new BelegartDDChoice("belegartHalde"));

        // offene Scandateien
        add(new Refresh("refresh", getDefaultModel()));
        add(new HiddenLink("hiddenlink", getDefaultModel()));
        add(new HaldeFileTable("tblAjaxContainer", getDefaultModel(), HaldeBearbeiten.this, getModel()));
        add(new LblTotal("filecnt", getDefaultModel()));

        // pdf-ansicht
        add(new ViewShowHelp("klickhint", getModel()));
        add(new ViewShowPDF("pdfbeleg1", getModel()));

        // Archivierungsdatensatz erstellen.
        add(new BusaForm("createarcdata", getModel()));

        initHaldeModel();
    }

    private HaldeDAOI getContactsDB() {
        return AEPApplication.get().getDBHaldeScan();
    }

    // public static IHaldeConfig getHaldeConfig(String belegTyp) {
    // return hmDBConfig.get(belegTyp);
    // }

    public HaldeBearbeitenModel getModel() {
        AEPModel model = (AEPModel) HaldeBearbeiten.this.getDefaultModelObject();
        HaldeBearbeitenModel modelHBM = model.getHaldeBearbeitenModel();
        return modelHBM;
    }

    public void initHaldeModel() {

        // hmDBConfig.put(Const.HALDE_BELEGART_SC, new HaldeConfig("path.halde.scan.pdf",
        // "path.halde.scan.bsatz.save"));
        // hmDBConfig.put(Const.HALDE_BELEGART_TB, new HaldeConfig("path.halde.tb.pdf",
        // "path.halde.tb.bsatz.save"));
        // hmDBConfig.put(Const.HALDE_BELEGART_SR, new HaldeConfig("path.halde.sr.pdf",
        // "path.halde.sr.bsatz.save"));
        // hmDBConfig.put(Const.HALDE_BELEGART_EK, new HaldeConfig("path.halde.ek.pdf",
        // "path.halde.ek.bsatz.save"));

        // Zurücksetzen des zuvor selektierten Dokumentes, der Buchungsdatensatz-Tabelle.
        getModel().addClickedFundstelle(null);
        getModel().addClickedFundstelleOld(null);
        getModel().addHaldeBuchungssaetze(new ArrayList<HaldeBuchungssatzScan>());
        getModel().addHaldeBuchungssaetzeOrig(new ArrayList<HaldeBuchungssatzScan>());
        getModel().initHaldeBuchungssaetzeChanged();
    }

    /**
     * Der hier dient zum Beenden des Requests und wird per init.js#$.fn.clickHiddenLink()
     * aufgerufen.
     */
    private class HiddenLink extends Link<String> {
        public HiddenLink(String id, IModel model) {
            super(id, model);
        }

        @Override
        // public void onClick(AjaxRequestTarget r) {
        public void onClick() {
            // r.appendJavaScript("$.fn.hideViewBusy();");
        }
    }

    /**
     * Blendet das Aktivitäts-Icon ein und ruft einen HiddenLink zum Beenden des Requestes auf.
     */
    private class Refresh extends AjaxLink<String> {

        public Refresh(String id, IModel model) {
            super(id, model);
        }

        @Override
        public void onClick(AjaxRequestTarget r) {
            // AjaxRequestTarget r = RequestCycle.get().find(AjaxRequestTarget.class);
            if (r != null) {
                r.appendJavaScript("$.fn.showViewBusy();");
                r.appendJavaScript("$.fn.clickHiddenLink()");
                // r.appendJavaScript("$.fn.initFixedTableHeaderRec()");
                // r.prependJavaScript("$.fn.initFixedTableHeaderRec()");

                /**
                 * ACHTUNG: Hier wird die Lieferenten.ini aufgerufen !!!
                 */
                AEPApplication.get().getDBHaldeScan().initLieferantenListe();

                HaldeFile hf = HaldeBearbeiten.this.getModel().getClickedFundstelle();
                new ChangeEvent(Refresh.this, r, hf, Const.KEY_HALDE_HALDEFILE_KLICK).fire();

                // Das PDF soll nur geladen werden, wenn es sich geändert hat.
                HaldeBearbeiten.this.getModel().addClickedFundstelleOld(hf);
            }
        }
    }

    private class BelegartDDChoice extends DropDownChoice<String> {

        public BelegartDDChoice(String id) {
            super(id, Arrays.asList(de.decodetron.util.Const.HALDE_BELEGART_SC,
                de.decodetron.util.Const.HALDE_BELEGART_SR, de.decodetron.util.Const.HALDE_BELEGART_TB,
                de.decodetron.util.Const.HALDE_BELEGART_EK));
            add(new AjaxFormComponentUpdatingBehavior("onchange") {
                protected void onUpdate(AjaxRequestTarget target) {
                    String selected = (String) BelegartDDChoice.this.getDefaultModelObject();
                    HaldeBearbeiten.this.getModel().setSelectedBelegart(selected);

                    HaldeFile hf = HaldeBearbeiten.this.getModel().getClickedFundstelle();
                    new ChangeEvent(BelegartDDChoice.this, target, hf, Const.KEY_HALDE_HALDEFILE_KLICK).fire();
                }
            });

            // Default
            HaldeBearbeiten.this.getModel().setSelectedBelegart(de.decodetron.util.Const.HALDE_BELEGART_SC);
            BelegartDDChoice.this.setDefaultModel(Model.of(de.decodetron.util.Const.HALDE_BELEGART_SC));

        }

        @Override
        public boolean isEnabled() {
            // Baustelle
            return false;
        }

        @Override
        public boolean isVisible() {
            // Baustelle
            return false;
        }
    }

}
