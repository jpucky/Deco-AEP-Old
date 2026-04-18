// $Log: BusaDDLieferantName.java,v $
// Revision 1.1  2015/03/24 23:21:20  tw
// CR Feng-ID: 3947#7
//
//

package de.decodetron.tab.administration.haldebearbeiten.tblBusaBearbeiten;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

import de.decodetron.AEPApplication;
import de.decodetron.bo.HaldeBuchungssatzScan;
import de.decodetron.bo.LiefNrName;

/**
 * @author Thomas Winter
 * @since 23.03.2015
 */
public class BusaDDLieferantName extends DropDownChoice<HaldeBuchungssatzScan> {

    public BusaDDLieferantName(String id) {
        super(id, new AbstractReadOnlyModel<List<? extends HaldeBuchungssatzScan>>() {
            @Override
            public List<HaldeBuchungssatzScan> getObject() {

                List<LiefNrName> l = AEPApplication.get().getDBHaldeScan().getLieferantenInfo();
                List<HaldeBuchungssatzScan> choices = new ArrayList<HaldeBuchungssatzScan>(l.size());
 

                    for (LiefNrName po : l) {
                        if (po.getLieferantenname().toLowerCase().startsWith("a".toLowerCase())) {

                            HaldeBuchungssatzScan hbs = new HaldeBuchungssatzScan();
                            hbs.setLieferantNr(po.getLieferantNr());
                            hbs.setLieferantenname(po.getLieferantenname());
                            choices.add(hbs);

                        }
                    }

                return choices;
            }
        }, new IChoiceRenderer<HaldeBuchungssatzScan>() {

            @Override
            public String getIdValue(HaldeBuchungssatzScan object, int index) {
                return String.valueOf(object.getSid());
            }

            @Override
            public String getDisplayValue(HaldeBuchungssatzScan object) {
                return object.getLieferantenname();
            }
        });

        add(new AjaxFormComponentUpdatingBehavior("onChange") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                System.out.println("gewählt: " + getModelObject());
            }
        });

    }

    IModel<List<? extends HaldeBuchungssatzScan>> model = new AbstractReadOnlyModel<List<? extends HaldeBuchungssatzScan>>() {
        @Override
        public List<HaldeBuchungssatzScan> getObject() {
            return getList("");
        }
    };

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
}
