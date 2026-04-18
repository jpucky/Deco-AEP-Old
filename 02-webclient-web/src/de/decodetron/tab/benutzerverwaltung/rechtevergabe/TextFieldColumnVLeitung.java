// $Log: TextFieldColumnVLeitung.java,v $
// Revision 1.1  2014/10/10 12:06:41  tw
// Implementierung: Benutzerrechte Vertriebsleiter.
//
//

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import java.util.regex.Pattern;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.validator.RangeValidator;

import de.decodetron.AEPModel;
import de.decodetron.bo.UserSectionData;
import de.decodetron.security.LoginSession;

/**
 * Für Admins gilt: Erlaubt nur die Eingabe einer Zahl. Für Superadmins gilt: Es können mehrere
 * Vertriebsbereiche, per Komma separiert angegeben werden.
 * 
 * @author Thomas Winter
 * @since 07.10.2014
 */
public class TextFieldColumnVLeitung<T, S> extends TextFieldColumnBase<T, S> {

    private String PATTERN_FILTER_ADMIN = "\\d+";
    private String PATTERN_FILTER_SUPERADMIN = "(\\,?\\d+\\,?\\s?)+";
    private Pattern pattern = Pattern
            .compile(LoginSession.get().getUser().getIsSuperAdmin() ? PATTERN_FILTER_SUPERADMIN : PATTERN_FILTER_ADMIN);

    public TextFieldColumnVLeitung(String title, String expression, IModel<?> m) {
        super(Model.of(title), expression, m);
    }

    @Override
    public String getCssClass() {
        return "static";
    }

    public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
        final MarkupContainer panel = new TextFieldPanel(componentId, rowModel);
        cellItem.add(panel);

        final T rowObject = rowModel.getObject();
        PropertyModel<String> model = newPropertyModel(rowObject);
        final FormComponent<String> textField = super.newTextField(TextFieldPanel.TEXTFIELD_ID, model, rowObject);
        textField.setRequired(false);
        panel.add(textField);

        final UserSectionData userData = (UserSectionData) rowObject;

        textField.add(new IValidator<String>() {
            @Override
            public void validate(IValidatable<String> validatable) {

                AEPModel vm = (AEPModel) getModel().getObject();
                StringBuffer errMsg = new StringBuffer();
                if (!pattern.matcher(validatable.getValue()).matches()) {
                    if (LoginSession.get().getUser().getIsSuperAdmin()) {
                        errMsg.append("Das Feld '" + getExpression() + "' des Benutzers '" + userData.getLogin()
                                + "' darf nur Komma separierte Zahlen enthalten oder muss leer bleiben!");
                    } else {
                        errMsg.append("Das Feld '" + getExpression() + "' des Benutzers '" + userData.getLogin()
                                + "' darf nur Zahlen enthalten oder muss leer bleiben!");
                    }
                    vm.getBenVerwaltungModel().getErrorList().add(errMsg.toString());
                }
                //System.out.println("validatable.getValue(): " + validatable.getValue());
                //textField.setModelObject(validatable.getValue());
            }
        });
    }

    protected PropertyModel<String> newPropertyModel(T rowObject) {
        return new PropertyModel<String>(rowObject, getExpression());
    }

}
