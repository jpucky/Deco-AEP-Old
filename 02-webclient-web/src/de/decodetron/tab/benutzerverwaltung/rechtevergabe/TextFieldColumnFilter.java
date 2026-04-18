// $Log: TextFieldColumnFilter.java,v $
// Revision 1.4  2018/07/01 11:57:16  tw
// Benutzer anlegen/bearbeiten. Im Filter werden Zeichen der Wortgruppe zugelassen (\w Regulaere Ausdruecke).
//
// Revision 1.3  2014/10/10 12:05:22  tw
// Implementierung: Benutzerrechte Vertriebsleiter.
//
// Revision 1.2  2014/09/12 20:40:58  tw
// Aep-Benutzerr-Rechteverwaltung: Setzten des Filters.
//
// Revision 1.1  2014/09/12 15:04:41  tw
// Aep-Benutzerr-Rechteverwaltung: Setzten des Defaultpasswortes.
//
//

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;

import de.decodetron.AEPModel;
import de.decodetron.bo.UserSectionData;

/**
 * Textfeld für das Datenbankfeld 'Filter' in der Tabelle User. Erlaubt die Eingabe von * ODER
 * Zahl(en) per Komma separiert.
 * 
 * @author Thomas Winter
 * @since 11.09.2014
 */
public class TextFieldColumnFilter<T, S> extends TextFieldColumnBase<T, S> {

    private String PATTERN_FILTER = "(\\*?)|(\\,?\\w+\\,?\\s?)+";
    private Pattern pattern = Pattern.compile(PATTERN_FILTER);
    private String errMsg = "Das Feld 'Filter' entspricht nicht dem erwarteten Muster: <br\\> [Wort], [Wort], [*], ...";

    public TextFieldColumnFilter(String title, String expression, IModel<?> m) {
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

//        textField.add(new IValidator<String>() {
//            @Override
//            public void validate(IValidatable<String> validatable) {
//
//                AEPModel vm = (AEPModel) getModel().getObject();
//                StringBuffer errMsg = new StringBuffer();
//                if (!pattern.matcher(validatable.getValue()).matches()) {
//                    errMsg.append("Das Feld 'Filter' entspricht nicht dem erwarteten Muster: <br\\> [Zahl2], [Zahl], [*], ...");
//                    vm.getBenVerwaltungModel().getErrorList().add(errMsg.toString());
//                }
//            }
//        });

        if(((UserSectionData)rowObject).getIsVertriebsleiter()){
            textField.setEnabled(false); // Muss leer bleiben!
            textField.add(AttributeModifier.append("title", textField.getModelObject()));
            textField.setModelObject("");   
        }else{
            textField.setModelObject(getFilterItems(textField.getModelObject()));   
        }
        
        textField.add(AttributeAppender.append("class", "filter"));
    }

    /**
     * 'Reinigt' die Benutzereingaben von unerwünschtem Kram und übernimmt nur die Zahlenwerte als
     * Filter.
     * 
     * @param String
     *            filterInput
     * @return String
     */
    public String getFilterItems(String filterInput) {
        int cnt = 0;
        StringBuilder sb = new StringBuilder();
        Pattern patternDate = Pattern.compile("(\\d+)|(\\*)");// Nur die Zahlen oder * rausfischen
        Matcher matcher = patternDate.matcher(filterInput);
        while (matcher.find()) {
            if (cnt > 0) {
                sb.append(",");
            }
            sb.append(matcher.group());
            cnt++;
        }
        return sb.toString();
    }

    protected PropertyModel<String> newPropertyModel(T rowObject) {
        return new PropertyModel<String>(rowObject, getExpression());
    }
}
