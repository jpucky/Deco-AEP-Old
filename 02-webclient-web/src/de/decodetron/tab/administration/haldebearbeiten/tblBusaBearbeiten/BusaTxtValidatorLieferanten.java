// $Log: BusaTxtValidatorLieferanten.java,v $
// Revision 1.2  2015/03/17 22:10:09  tw
// CR Feng-ID: 3947
//
// Revision 1.1  2015/02/17 21:47:20  tw
// Haldenbearbeitung: Erste Implementierung d. Eingabeueberpruefungen.
//
// Revision 1.1  2015/02/17 21:33:59  tw
// Haldenbearbeitung: Erste Implementierung d. Eingabeueberpruefungen.
//
// Revision 1.1  2015/02/15 01:31:57  tw
// Haldenbearbeitung: Autocomplete die 1.
//
//

package de.decodetron.tab.administration.haldebearbeiten.tblBusaBearbeiten;

import java.util.HashMap;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import de.decodetron.AEPApplication;

/**
 * Prüft, ob LieferantenNummer bzw. LieferantenName existieren.
 * 
 * @author Thomas Winter
 * @since 13.02.2015
 * @param <T>
 */
public class BusaTxtValidatorLieferanten<T> implements IValidator<T> {

    @Override
    public void validate(IValidatable<T> validatable) {

        String nrOrname = (String) validatable.getValue();
        if (!AEPApplication.get().getDBHaldeScan().checkIfNrOrNameExist(nrOrname)) {

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("item", nrOrname);

            ValidationError err = new ValidationError();
            err.setVariables(map);
            err.addKey("error.liefnrname.exist");
            validatable.error(err);
        }
    }

}
