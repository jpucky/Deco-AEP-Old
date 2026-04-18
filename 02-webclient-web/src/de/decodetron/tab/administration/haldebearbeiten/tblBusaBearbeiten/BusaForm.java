// $Log: BusaForm.java,v $
// Revision 1.15  2015/04/13 14:50:17  tw
// CR Feng-ID: 3950#3
//
// Revision 1.14  2015/04/10 14:21:53  tw
// CR Feng-ID: 3947#9
//
// Revision 1.13  2015/03/24 23:20:44  tw
// CR Feng-ID: 3947#7
//
// Revision 1.12  2015/03/19 13:06:21  tw
// CR Feng-ID: 3947#4
//
// Revision 1.11  2015/03/18 22:40:25  tw
// CR Feng-ID: 3947#4
//
// Revision 1.10  2015/03/17 22:10:09  tw
// CR Feng-ID: 3947
//
// Revision 1.9  2015/02/27 18:16:42  tw
// Haldenbearbeitung: Datepicker Bugfix.
//
// Revision 1.8  2015/02/26 12:46:45  tw
// Haldenbearbeitung: Fehlerpruefung, Bugfixing.
//
// Revision 1.7  2015/02/19 21:23:25  tw
// Haldenbearbeitung: Inititialisierung Lieferanten.ini. Bugix Tabellenscrollbar.
//
// Revision 1.6  2015/02/19 14:33:27  tw
// Haldenbearbeitung: Aenderung des Speicherverzeichnisses.
//
// Revision 1.5  2015/02/17 21:46:26  tw
// Haldenbearbeitung: Erste Implementierung d. Eingabeueberpruefungen.
//
// Revision 1.4  2015/02/13 10:47:50  tw
// Haldenbearbeitung: LiefName hinzugefuegt.
//
// Revision 1.3  2015/02/13 03:18:43  tw
// Haldenbearbeitung: Eventhandling, css.
//
// Revision 1.2  2015/02/11 14:08:12  tw
// Haldenbearbeitung: Auskommentieren der Ansicht fuer Vorab-Realease.
//
// Revision 1.1  2015/02/09 12:44:28  tw
// Haldenbearbeitung: Bugfix. Aufraeumarbeiten.
//
//

package de.decodetron.tab.administration.haldebearbeiten.tblBusaBearbeiten;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import de.decodetron.AEPApplication;
import de.decodetron.Const;
import de.decodetron.bo.HaldeBuchungssatzScan;
import de.decodetron.dao.halde.HaldeDAOI;
import de.decodetron.event.AbstractEvent;
import de.decodetron.event.ChangeEvent;
import de.decodetron.event.EventListenerInterface;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.administration.haldebearbeiten.HaldeBearbeitenModel;

/**
 * 
 * Das Formular "Buchungsdatensatz erstellen" mit seinen Unterkomponenten.<br>
 * <br>
 * siehe auch: http://wicket.apache.org/guide/guide/forms2.html
 * 
 * @author Thomas Winter
 * @since 09.02.2015
 */
public class BusaForm extends Form<HaldeBearbeitenModel> implements EventListenerInterface {

    // FeedbackPanel feedback = null;
    private static Logger log = Logger.getLogger(BusaForm.class);
    MyFeedbackPanel feebackPanel;

    public BusaForm(String id, HaldeBearbeitenModel model) {
        super(id, Model.of(model));
        add(new BusaTable<HaldeBearbeitenModel>("tblAjaxContainer2", model));
        // add(new BusaDelete("deleteAllBusa", model));
        add(new SaveButton("savebutton"));
        // add(new BusaCreate("createBusa", model));
        add(new BusaCreateContainer("createBusaContainer", model));
        add(new BusaDeleteContainer("deleteAllBusaContainer", model));

        // feedback = new FeedbackPanel("feedback");
        // feedback.setEscapeModelStrings(false);
        // feedback.setOutputMarkupId(true);
        // add(feedback);
        add(feebackPanel = new MyFeedbackPanel("feedback"));

        // TextField txt;
        // add(txt = new TextField<String>("txttest"));
        // txt.add(new CustomTestValidator());
        // txt.setRequired(true);
        // // txt.add(new ErrorDecorationBehavior());
        // txt.setType(String.class);
        //
        // add(new ErrorHighlightBehavior());
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(OnDomReadyHeaderItem.forScript("$.fn.initFixedTableArcNew()"));
        response.render(OnDomReadyHeaderItem.forScript("$.fn.initDatePickerHalde()"));
    }

    class ErrorDecorationBehavior extends AttributeAppender {
        public ErrorDecorationBehavior() {
            // super("style", true,
            // Model.of("border-style:solid; border-color:#f86b5c; border-width: 3px"), ",");
            super("style", true, Model.of("background-color: #df666c"), ",");
        }

        @Override
        public boolean isEnabled(Component component) {
            return super.isEnabled(component) && component.hasErrorMessage();
        }
    }

    public class ErrorHighlightBehavior extends Behavior {
        @Override
        public void onComponentTag(Component c, ComponentTag tag) {
            FormComponent fc = null;
            if (c instanceof Palette) {
                fc = ((Palette) c).getRecorderComponent();
            } else if (c instanceof FormComponent) {
                fc = (FormComponent) c;
            }
            if ((fc != null) && !fc.isValid()) {
                tag.addBehavior(new AttributeAppender("class", new Model<String>("redalert"), " "));
            }
        }
    }

    private class CustomTestValidator implements IValidator<String> {
        @Override
        public void validate(IValidatable<String> validatable) {
            if (validatable.isValid()) {
                ValidationError err = new ValidationError();
                err.setMessage("Es gibt ungültige Elemente !");
                // err.addKey("ben.filter.error.adminuser");
                validatable.error(err);
            }

            if ("".equals(validatable.getValue())) {
                ValidationError err = new ValidationError();
                err.setMessage("So geht das nicht !");
                // err.addKey("ben.filter.error.adminuser");
                validatable.error(err);
            }
        }
    }

    private class MyFeedbackPanel extends FeedbackPanel implements EventListenerInterface {

        public MyFeedbackPanel(String id) {
            super(id);
            setEscapeModelStrings(false);
            setOutputMarkupId(true);
        }

        @Override
        public void notifyAjaxEvent(AbstractEvent event) {
            if (event instanceof ChangeEvent) {
                ChangeEvent ce = ((ChangeEvent) event);
                String ident = ce.getIdentifier();
                if (Const.KEY_HALDE_BUSA_ERROROCCURED.equals(ident)) {
                    ce.update(MyFeedbackPanel.this);
                } else if (Const.KEY_HALDE_BUSA_MODIFIED.equals(ident)) {
                    ce.update(MyFeedbackPanel.this);
                } else if (Const.KEY_HALDE_BUSA_DELETED.equals(ident)) {
                    ce.update(MyFeedbackPanel.this);
                } else if (Const.KEY_HALDE_HALDEFILE_KLICK.equals(ident)) {
                    ce.update(MyFeedbackPanel.this);
                } else if (Const.KEY_HALDE_BUSA_ADDED.equals(ident)) {
                    ce.update(MyFeedbackPanel.this);
                }
            }
        }

    }

    private class SaveButton extends AjaxButton implements EventListenerInterface {

        public SaveButton(String id) {
            super(id);
            setOutputMarkupPlaceholderTag(true);
            add(new IValidator<String>() {
                @Override
                public void validate(IValidatable<String> validatable) {

                    // //////////////////////////
                    // /// Auswertung der Fehlermeldungen;
                    // /// Vorsicht mit dem Löschen der Models! !!! Das hier wird VOR Submit
                    // /// ausgelöst !!!
                    // /

                    /**
                     * TODO: List<String> errList = vm.getBenVerwaltungModel().getErrorList();
                     */

                    // System.out.println("validatable: " + validatable.isValid());
                    // error(validatable.getValue());
                    List<HaldeBuchungssatzScan> l = BusaForm.this.getModelObject().getHaldeBuchungssaetze();
                    for (Iterator<HaldeBuchungssatzScan> iterator = l.iterator(); iterator.hasNext();) {
                        HaldeBuchungssatzScan haldeBuchungssatz = iterator.next();
                        // if (isEmpty(haldeBuchungssatz)) {
                        // error("Ein Buchungssatz darf keine leeren Felder enthalten!");
                        // }

                        String errMsg = "";
                        if (!(errMsg = ruleCheck(haldeBuchungssatz)).isEmpty()) {
                            // error(errMsg);
                        }

                        if (AEPApplication.get().getDBHaldeScan().checkIfBuchungssatzExist(haldeBuchungssatz)) {
                            //error("Der Beleg : " + haldeBuchungssatz.getDokumentType()
                            //        + " befindet sich bereits in der Bearbeitung!");
                        }
                    }

                    // String errMsgDe = "";
                    // if (!(errMsgDe = checkDoubleEntries(l)).isEmpty()) {
                    // error(errMsgDe);
                    // }
                }
            });
        }

        /**
         * Schaut nach, ob es Dopplungen in den Buchungssätzen gibt und wirft eine Fehlermeldung.
         * 
         * @param list
         * @return
         */
        public String checkDoubleEntries(List<HaldeBuchungssatzScan> list) {

            List<Integer> checkList = new ArrayList<Integer>();
            StringBuilder sb = new StringBuilder();
            for (Iterator<HaldeBuchungssatzScan> iterator = list.iterator(); iterator.hasNext();) {
                HaldeBuchungssatzScan haldeBuchungssatz = iterator.next();
                Integer hashValue = haldeBuchungssatz.hashCode();
                if (!checkList.contains(hashValue)) {
                    checkList.add(hashValue);
                } else {

                }
            }

            return sb.toString();
        }

        private boolean isEmpty(HaldeBuchungssatzScan hbs) {
            if (hbs.getLieferantNr().isEmpty() || hbs.getLieferantenname().isEmpty() || hbs.getBestellNr().isEmpty()
                    || hbs.getDokDatum().isEmpty()) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * Nachgelagerte Prüfung, da die direkt an die Felder angedockte Prüfung, zumindest für die
         * LieferantenName - LieferantenNr zeitweise versagt hat.
         * 
         * Prüft, ob: <br>
         * 1.) die LieferantenNr zum Lieferantennamen passt.<br>
         * 2.) die BestellNr eine Zahl ist.<br>
         * 3.) das Datum dem geforderten Format entspricht.
         * 
         * @param HaldeBuchungssatzScan
         *            hbs
         * @return String
         */
        private String ruleCheck(HaldeBuchungssatzScan hbs) {

            StringBuilder sb = new StringBuilder();

            boolean lfnrExist = AEPApplication.get().getDBHaldeScan().checkIfNrOrNameExist(hbs.getLieferantNr());
            boolean lfnameExist = AEPApplication.get().getDBHaldeScan().checkIfNrOrNameExist(hbs.getLieferantenname());
            boolean bestNrIsNumber = (hbs.getBestellNr() != null && hbs.getBestellNr().length() > 0) ? hbs
                    .getBestellNr().matches(Const.REG_EX_ISNUMBER) : true;
            // boolean isDate = isDate(hbs.getDokDatum()); TODO !!!

            if (!lfnrExist) {
                sb.append("Ungültige Nummer :  '" + hbs.getLieferantNr() + "' !");
            }

            if (!lfnameExist) {
                sb.append("Ungültiger  Name :  '" + hbs.getLieferantenname() + "' !");
            }

            /**
             * Der Kombicheck aus beiden ist nochmal wichtig, da sich die Felder austricksen lassen!
             */
            String lname = AEPApplication.get().getDBHaldeScan().getLieferantenName(hbs.getLieferantNr());
            if (!lname.equals(hbs.getLieferantenname())) {
                sb.append("Die Kombination LieferantenNR/Name ist falsch :  '" + hbs.getLieferantNr() + " / "
                        + hbs.getLieferantenname() + "' !");
            }

            if (!bestNrIsNumber) {
                sb.append("Das Feld \"Bestellnummer\" muss Zahlen enthalten!");
            }

            return sb.toString();
        }

        @Override
        public boolean isVisible() {
            // showMap();
            HashMap<String, HaldeBuchungssatzScan> hm = BusaForm.this.getModelObject().getBuchungssaetzeChanged();
            if (hm != null) {
                Set<String> keyset = hm.keySet();
                // System.out.println("fbm: " + getSession().getFeedbackMessages());
                return !keyset.isEmpty();
            }
            return false;
        }

        protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

            HaldeBearbeitenModel hbm = (HaldeBearbeitenModel) BusaForm.this.getDefaultModelObject();
            Collection<HaldeBuchungssatzScan> chb = hbm.getBuchungssaetzeChanged().values();
            List<HaldeBuchungssatzScan> listChanged = new ArrayList<HaldeBuchungssatzScan>(chb);
            // if (hbm.getHaldeBuchungssaetze().size() > 0) {
            if (listChanged.size() > 0) {

                // Insert
                List<HaldeBuchungssatzScan> l = BusaForm.this.getModelObject().getHaldeBuchungssaetze();
                getContactsDB().exportBuchungsSatz(l);
                log.info(LoginSession.get().getUser().getLogin() + " : " + hbm.getHaldeBuchungssaetze().size()
                        + " gespeichtert!");

                StringBuilder sb = new StringBuilder();
                sb.append(hbm.getHaldeBuchungssaetze().size());
                if (hbm.getHaldeBuchungssaetze().size() > 1) {
                    sb.append(" Buchungssätze gespeichtert! ");
                } else {
                    sb.append(" Buchungssatz gespeichtert! ");
                }

                info(sb.toString());
            }

            // Löschen der geänderten Datensätze im Model
            hbm.initHaldeBuchungssaetzeChanged();

            // Löschen des temporären Files ...
            getContactsDB().getBuchungssatzDestFile4Tmp(hbm.getHaldeBuchungssaetze().get(0)).delete();

            // target.add(busaTable);
            target.add(feebackPanel);
            target.add(SaveButton.this);
            new ChangeEvent(SaveButton.this, target, null, Const.KEY_HALDE_BUSA_SAVED).fire();
        }

        @Override
        protected void onError(AjaxRequestTarget target, Form<?> form) {
            target.add(feebackPanel);
        }

        private void updateButtonComp(ChangeEvent ce) {
            ce.update(SaveButton.this);
        }

        @Override
        public void notifyAjaxEvent(AbstractEvent event) {
            if (event instanceof ChangeEvent) {
                ChangeEvent ce = ((ChangeEvent) event);
                String ident = ce.getIdentifier();

                if (Const.KEY_HALDE_BUSA_MODIFIED.equals(ident)) {
                    updateButtonComp(ce);
                } else if (Const.KEY_HALDE_BUSA_DELETED.equals(ident)) {
                    updateButtonComp(ce);
                } else if (Const.KEY_HALDE_HALDEFILE_KLICK.equals(ident)) {
                    updateButtonComp(ce);
                } else if (Const.KEY_HALDE_BUSA_ADDED.equals(ident)) {
                    updateButtonComp(ce);
                }
            }

        }
    }

    private HaldeDAOI getContactsDB() {
        return AEPApplication.get().getDBHaldeScan();
    }

    public void showMap() {

        HashMap<String, HaldeBuchungssatzScan> hm = BusaForm.this.getModelObject().getBuchungssaetzeChanged();
        Set<String> keyset = hm.keySet();

        System.out.println("HBS : " + keyset.size());

        for (Iterator<String> iterator = keyset.iterator(); iterator.hasNext();) {
            String key = iterator.next();
            HaldeBuchungssatzScan hbs = hm.get(key);
            System.out.println("HBS : " + hbs.toString());
        }

        System.out.println("--------------------- ENDE ---------------------");
    }

    public void notifyAjaxEvent(AbstractEvent event) {
        if (event instanceof ChangeEvent) {
            ChangeEvent ce = ((ChangeEvent) event);
            String ident = ce.getIdentifier();
            if (Const.KEY_HALDE_HALDEFILE_KLICK.equals(ident)) {
                // Für die korrekte Tabellenkopfdarstellung nach selektion eines Datensatzes.
                ce.update(BusaForm.this);
            }
        }
    }
}
