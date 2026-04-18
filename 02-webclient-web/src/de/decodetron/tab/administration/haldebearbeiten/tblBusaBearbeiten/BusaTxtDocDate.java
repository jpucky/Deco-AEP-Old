// $Log: BusaTxtDocDate.java,v $
// Revision 1.5  2015/04/14 09:48:05  tw
// CR Feng-ID: 3950#5
//
// Revision 1.4  2015/04/13 12:13:18  tw
// CR Feng-ID: 3950#2
//
// Revision 1.3  2015/03/23 22:47:16  tw
// CR Feng-ID: 3947#6
//
// Revision 1.2  2015/03/19 13:06:21  tw
// CR Feng-ID: 3947#4
//
// Revision 1.1  2015/02/27 09:39:30  tw
// Haldenbearbeitung: Datum-Musterpruefung implementiert.
//
//

package de.decodetron.tab.administration.haldebearbeiten.tblBusaBearbeiten;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;


import de.decodetron.Const;
import de.decodetron.bo.HaldeBuchungssatzScan;
import de.decodetron.event.ChangeEvent;

/**
 * @author Thomas Winter
 * @since 27.02.2015
 */
public class BusaTxtDocDate extends TextField {

    private HaldeBuchungssatzScan hbs = null;

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(OnDomReadyHeaderItem.forScript("$.fn.initDatePickerHalde()"));
    }

    public BusaTxtDocDate(String id, final HaldeBuchungssatzScan busa) {

        super(id, new PropertyModel<String>(busa, id) {
            @Override
            public String getObject() {
                String inDate = busa.getDokDatum();
                // System.out.println("DateCheck: " + inDate);
                // busa.setDokDatum(Util.inOutDateParser(inDate, "yyyyMMdd", "dd.MM.yyyy"));
                return busa.getDokDatum();
            }
        });
        this.hbs = busa;
        add(new AjaxFormComponentUpdatingBehavior("onChange") {

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                BusaTxtDocDate.this.add(AttributeModifier.replace("class", Model.of("calendar datePickerHalde")));
                target.add(BusaTxtDocDate.this);

                new ChangeEvent(BusaTxtDocDate.this, target, busa, Const.KEY_HALDE_BUSA_MODIFIED).fire();
            }

            // @Override
            // protected void onError(AjaxRequestTarget target, RuntimeException e) {
            //
            // BusaTxtDocDate.this.add(AttributeModifier.replace("class",
            // Model.of("redalert calendar datePickerHalde")));
            // target.add(BusaTxtDocDate.this);
            // //
            // // // System.out.println("Error-Objekt: \"" + getModelObject() + "\"");
            // // new ChangeEvent(BusaTxtDocDate.this, target, null,
            // Const.KEY_HALDE_BUSA_ERROROCCURED).fire();
            // // super.onError(target, e);
            // }
        });

        add(new DatePatternValidator<HaldeBuchungssatzScan>());
        setRequired(true);
    }

    private class DatePatternValidator<T> implements IValidator<T> {
        public void validate(IValidatable<T> validatable) {
            String txtValue = (String) validatable.getValue();

            if (!isDatePatternOK(txtValue, "dd.MM.yyyy")) {
                ValidationError err = new ValidationError();
                err.addKey("error.datepattern");
                validatable.error(err);
            }
        }
    }

    // @Override
    // public boolean isEnabled() {
    // return this.hbs.getDokDatum().length() == 0;
    // }
    /**
     * Prüft, ob das Datum dem angegebenen Muster entspricht.
     * 
     * @param String
     *            inDate
     * @param String
     *            inFormat
     * @return boolean
     */
    public static boolean isDatePatternOK(String inDate, String inFormat) {
        boolean isDateOK = true;
        try {
            if (inDate == null || inDate.length() == 0) {
                isDateOK = false;
                return isDateOK;
            }

            // Das Muster: dd.MM.yyyy verarbeitet auch 2-stellige Jahre!
            Pattern patternDate = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}", Pattern.CASE_INSENSITIVE);
            Matcher matcher = patternDate.matcher(inDate);
            if (!matcher.find()) {
                isDateOK = false;
                return isDateOK;
            }
            
            SimpleDateFormat fourDigitYear = new SimpleDateFormat(inFormat);
            fourDigitYear.setLenient(false);
            fourDigitYear.parse(inDate);
        } catch (ParseException e) {
            isDateOK = false;
        }
        return isDateOK;
    }
}
