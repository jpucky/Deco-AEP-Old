// $Log: BusaTxtLieferantName.java,v $
// Revision 1.8  2015/04/13 12:13:18  tw
// CR Feng-ID: 3950#2
//
// Revision 1.7  2015/03/23 22:47:16  tw
// CR Feng-ID: 3947#6
//
// Revision 1.6  2015/03/19 13:06:21  tw
// CR Feng-ID: 3947#4
//
// Revision 1.5  2015/03/18 22:40:25  tw
// CR Feng-ID: 3947#4
//
// Revision 1.4  2015/03/17 22:10:09  tw
// CR Feng-ID: 3947
//
// Revision 1.3  2015/02/26 12:46:46  tw
// Haldenbearbeitung: Fehlerpruefung, Bugfixing.
//
// Revision 1.2  2015/02/17 21:46:26  tw
// Haldenbearbeitung: Erste Implementierung d. Eingabeueberpruefungen.
//
// Revision 1.1  2015/02/17 21:33:59  tw
// Haldenbearbeitung: Erste Implementierung d. Eingabeueberpruefungen.
//
//

package de.decodetron.tab.administration.haldebearbeiten.tblBusaBearbeiten;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
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
 * @since 17.02.2015
 */
public class BusaTxtLieferantName extends AutoCompleteTextField<HaldeBuchungssatzScan> implements
        EventListenerInterface {

    private HaldeBuchungssatzScan hbs = null;
    private static int LIEF_NR_COMPONENT = 1; // <=!!! 1. Textfeldkomponente

    public BusaTxtLieferantName(String id, final HaldeBuchungssatzScan busa, final ListItem<HaldeBuchungssatzScan> li) {
        super(id, new PropertyModel<HaldeBuchungssatzScan>(busa, id),
              new AbstractAutoCompleteRenderer<HaldeBuchungssatzScan>() {

                  @Override
                  protected void renderChoice(HaldeBuchungssatzScan object, Response response, String criteria) {
                      // + "-" + object.getLieferantNr()
                      response.write(object.getLieferantenname());
                  }

                  @Override
                  protected String getTextValue(HaldeBuchungssatzScan object) {
                      return object.getLieferantenname();
                  }

                  @Override
                  protected CharSequence getOnSelectJavaScriptExpression(HaldeBuchungssatzScan item) {
                      StringBuilder js = new StringBuilder();
                      Component c = li.get(LIEF_NR_COMPONENT);
                      js.append("document.getElementById(\'");
                      js.append(c != null ? c.getMarkupId() : "");
                      js.append("\').value ='");
                      js.append(item.getLieferantNr());
                      js.append("';");
                      js.append("input");
                      return js.toString();
                  }

              });
        this.hbs = busa;
        AjaxFormComponentUpdatingBehavior test;
        add(test = new AjaxFormComponentUpdatingBehavior("onChange") {

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                BusaTxtLieferantName.this.add(AttributeModifier.replace("class", Model.of("search")));
                target.add(BusaTxtLieferantName.this);

                Object lfrname = getModelObject();
                busa.setLieferantenname((String) lfrname);
                String lNr = AEPApplication.get().getDBHaldeScan().getLieferantenNr(busa.getLieferantenname());
                busa.setLieferantNr(lNr.isEmpty() ? "" : lNr);
                new ChangeEvent(BusaTxtLieferantName.this, target, busa, Const.KEY_HALDE_BUSA_MODIFIED).fire();
                //
                // target.add(BusaTxtLieferantName.this);
//                 Component c = li.get(LIEF_NR_COMPONENT);
//                 if(c != null){
//                 target.add(c);
//                 }
            }

//            @Override
//            protected void onError(AjaxRequestTarget target, RuntimeException e) {
//
//                BusaTxtLieferantName.this.add(AttributeModifier.replace("class", Model.of("search redalert")));
//                target.add(BusaTxtLieferantName.this);
//
//                new ChangeEvent(BusaTxtLieferantName.this, target, null, Const.KEY_HALDE_BUSA_ERROROCCURED).fire();
//                super.onError(target, e);
//            }
            
//            protected boolean getUpdateModel() {
//                return false;
//            };
        });
        
//        add(new AjaxEventBehavior("onclick") {
//            
//            protected void onEvent(AjaxRequestTarget target) {
//                System.out.println("onClick");
//                //setDefaultModel(Model.of("a"));
//                BusaTxtLieferantName.this.getChoices("a");
//                target.add(BusaTxtLieferantName.this);
//            }
//        });
        
        add(new LieferantNameValidator<HaldeBuchungssatzScan>());
        add(AttributeModifier.append("title", getDefaultModel()));
        setRequired(true);
    }

    private class LieferantNameValidator<T> implements IValidator<T> {
        public void validate(IValidatable<T> validatable) {
            String txtValue = (String) validatable.getValue();
            if (!AEPApplication.get().getDBHaldeScan().checkIfNrOrNameExist(txtValue)) {
                validatable.error(new ValidationError().addKey("error.liefname.exist"));
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

    public List<HaldeBuchungssatzScan> getList(String name) {
        List<HaldeBuchungssatzScan> choices = null;
        if ((name != null) && (name.length() > 0)) {
            choices = new ArrayList<HaldeBuchungssatzScan>(10);
            List<LiefNrName> l = AEPApplication.get().getDBHaldeScan().getLieferantenInfo();

            for (LiefNrName po : l) {
                if (po.getLieferantenname().toLowerCase().startsWith(name.toLowerCase())) {

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
                //ce.update(BusaTxtLieferantName.this);
            }
        }
    }
}
