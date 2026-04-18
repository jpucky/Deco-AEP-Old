// $Log: BusaTxtLieferantNr.java,v $
// Revision 1.9  2015/04/13 20:02:19  tw
// CR Feng-ID: 3950#3
//
// Revision 1.8  2015/04/13 12:13:18  tw
// CR Feng-ID: 3950#2
//
// Revision 1.7  2015/03/23 22:47:16  tw
// CR Feng-ID: 3947#6
//
// Revision 1.6  2015/03/19 13:06:21  tw
// CR Feng-ID: 3947#4
//
// Revision 1.5  2015/03/17 22:10:09  tw
// CR Feng-ID: 3947
//
// Revision 1.4  2015/02/26 12:46:46  tw
// Haldenbearbeitung: Fehlerpruefung, Bugfixing.
//
// Revision 1.3  2015/02/19 14:33:27  tw
// Haldenbearbeitung: Aenderung des Speicherverzeichnisses.
//
// Revision 1.2  2015/02/17 21:46:26  tw
// Haldenbearbeitung: Erste Implementierung d. Eingabeueberpruefungen.
//
// Revision 1.1  2015/02/17 21:33:59  tw
// Haldenbearbeitung: Erste Implementierung d. Eingabeueberpruefungen.
//
// Revision 1.1  2015/02/16 14:10:01  tw
// Haldenbearbeitung: Autocomplete Aufraeumarbeiten.
//
// Revision 1.1  2015/02/15 01:31:57  tw
// Haldenbearbeitung: Autocomplete die 1.
//
//

package de.decodetron.tab.administration.haldebearbeiten.tblBusaBearbeiten;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteRenderer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.Response;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import de.decodetron.AEPApplication;
import de.decodetron.Const;
import de.decodetron.bo.HaldeBuchungssatzScan;
import de.decodetron.bo.LiefNrName;
import de.decodetron.event.AbstractEvent;
import de.decodetron.event.ChangeEvent;
import de.decodetron.event.EventListenerInterface;

/**
 * @author Thomas Winter
 * @since 13.02.2015
 */
public class BusaTxtLieferantNr extends AutoCompleteTextField<HaldeBuchungssatzScan> implements EventListenerInterface{

    private HaldeBuchungssatzScan hbs;
    private static int LIEF_NAME_COMPONENT = 2; // <=!!! 2. Textfeldkomponente

    public BusaTxtLieferantNr(String id, final HaldeBuchungssatzScan busa, final ListItem<HaldeBuchungssatzScan> li) {
        super(id, new PropertyModel<HaldeBuchungssatzScan>(busa, id),
              new AbstractAutoCompleteRenderer<HaldeBuchungssatzScan>() {

                  @Override
                  protected void renderChoice(HaldeBuchungssatzScan object, Response response, String criteria) {
                      // + "-" + object.getLieferantenname()
                      response.write(object.getLieferantNr());
                  }

                  @Override
                  protected String getTextValue(HaldeBuchungssatzScan object) {
                      return object.getLieferantNr();
                  }

                  @Override
                  protected CharSequence getOnSelectJavaScriptExpression(HaldeBuchungssatzScan item) {
                      StringBuilder js = new StringBuilder();
                      Component c = li.get(LIEF_NAME_COMPONENT);
                      js.append("document.getElementById(\'");
                      js.append(c != null ? c.getMarkupId() : "");
                      js.append("\').value ='");
                      js.append(item.getLieferantenname());
                      js.append("';");
                      js.append("input;");
                      return js.toString();
                  }
              });
        this.hbs = busa;
        add(new AjaxFormComponentUpdatingBehavior("onChange") {

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                BusaTxtLieferantNr.this.add(AttributeModifier.replace("class", Model.of("search")));

                /**
                 * Wichtig! Die Information muss vor allem dann übertragen werden, wenn das
                 * Autocomplete-Auswahlfenster nicht benutzt wird!
                 */
                Object liefNr = getValue();
                busa.setLieferantNr((String) liefNr);
                String lname = AEPApplication.get().getDBHaldeScan().getLieferantenName(busa.getLieferantNr());
                busa.setLieferantenname(lname.isEmpty() ? "" : lname);
                new ChangeEvent(BusaTxtLieferantNr.this, target, busa, Const.KEY_HALDE_BUSA_MODIFIED).fire();

//                target.add(BusaTxtLieferantNr.this);
//                Component c = li.get(LIEF_NAME_COMPONENT);
//                if (c != null) {
//                    target.add(c);
//                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, RuntimeException e) {
                
//                /**
//                 * Wichtig! Die Information muss vor allem dann übertragen werden, wenn das
//                 * Autocomplete-Auswahlfenster nicht benutzt wird!
//                 */
//                //System.out.println("getValidatorKeyPrefix: " + getValidatorKeyPrefix());
//                //System.out.println("getValue: " + getValue());
//                //System.out.println("getDefaultModelObjectAsString: " + getDefaultModelObjectAsString());
//                
//                Object liefNr = getValue();
//                busa.setLieferantNr((String) liefNr);
//                String lname = AEPApplication.get().getDBHalde().getLieferantenName(busa.getLieferantNr());
//                busa.setLieferantenname(lname.isEmpty() ? "" : lname);
//                //new ChangeEvent(BusaTxtLieferantNr.this, target, busa, Const.KEY_HALDE_BUSA_MODIFIED).fire();
//
//                target.add(BusaTxtLieferantNr.this);
//                Component c = li.get(LIEF_NAME_COMPONENT);
//                if (c != null) {
//                    target.add(c);
//                }

//                BusaTxtLieferantNr.this.add(AttributeModifier.replace("class", Model.of("search redalert")));
//                target.add(BusaTxtLieferantNr.this);

//                new ChangeEvent(BusaTxtLieferantNr.this, target, null, Const.KEY_HALDE_BUSA_ERROROCCURED).fire();
//                super.onError(target, e);
            }

        });


        // From:
        // http://apache-wicket.1842946.n4.nabble.com/Remove-error-message-for-a-specific-component-td1867894.html
        // getSession().getFeedbackMessages().clear();
//        IFeedbackMessageFilter f;
//        getSession().getFeedbackMessages().clear(f = new IFeedbackMessageFilter() {
//
//            @Override
//            public boolean accept(FeedbackMessage message) {
//                System.out.println("message.getReporter() : " + message.getReporter());
//                if (message.getReporter() == li.get(LIEF_NAME_COMPONENT)) {
//                    //System.out.println("LIEF_NAME_COMPONENT");
//                    return true;
//                }
//                return false;
//            }
//        });
        
        add(new LieferantNrValidator<HaldeBuchungssatzScan>());
        setRequired(true);
    }

    private class LieferantNrValidator<T> implements IValidator<T> {
        public void validate(IValidatable<T> validatable) {
            String txtValue = (String) validatable.getValue();
            if (!AEPApplication.get().getDBHaldeScan().checkIfNrOrNameExist(txtValue)) {
                validatable.error(new ValidationError().addKey("error.liefnr.exist"));
            }
        }
    }
    
//    @Override
//    public boolean isEnabled() {
//        return this.hbs.getLieferantenname().length() == 0;
//    }
    
    @Override
    protected Iterator<HaldeBuchungssatzScan> getChoices(String input) {

        List<HaldeBuchungssatzScan> list = getList(input);
        if (list != null)
            return list.iterator();
        return Collections.EMPTY_LIST.iterator();
    }

    public List<HaldeBuchungssatzScan> getList(String nr) {
        List<HaldeBuchungssatzScan> choices = null;
        if ((nr != null) && (nr.length() > 0)) {
            choices = new ArrayList<HaldeBuchungssatzScan>(10);
            List<LiefNrName> l = AEPApplication.get().getDBHaldeScan().getLieferantenInfo();
            for (LiefNrName po : l) {
                if (po.getLieferantNr().toLowerCase().startsWith(nr.toLowerCase())) {

                    HaldeBuchungssatzScan hbs = new HaldeBuchungssatzScan();
                    hbs.setLieferantNr(po.getLieferantNr());
                    hbs.setLieferantenname(po.getLieferantenname());
                    choices.add(hbs);

                    if (choices.size() == 10) {
                        break;
                    }
                }
            }
        }
        return choices;
    }

    @Override
    public void notifyAjaxEvent(AbstractEvent event) {
        if (event instanceof ChangeEvent) {
            ChangeEvent ce = ((ChangeEvent) event);
            String ident = ce.getIdentifier();
            if (Const.KEY_HALDE_BUSA_MODIFIED.equals(ident)) {
                //ce.update(BusaTxtLieferantNr.this);
            }
        }
    }

}
