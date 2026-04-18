// $Log: TextFieldColumn.java,v $
// Revision 1.10  2014/09/09 15:43:00  tw
// Aep-Benutzerr-Rechteverwaltung: Benutzer Anlegen, Backup.
//
// Revision 1.9  2014/08/28 20:37:30  tw
// Zusammenfassung TextfieldColumn.
//
// Revision 1.8  2014/08/24 22:18:54  tw
// Aep-Benutzerr-Rechteverwaltung: Bugfix, Model auraeumen.
//
// Revision 1.7  2014/08/20 11:46:42  tw
// Aep-Benutzerr-Rechteverwaltung: Layout.
//
// Revision 1.6  2014/08/19 10:41:17  tw
// Aep-Benutzerr-Rechteverwaltung: Layout, Textfeld-tests.
//
// Revision 1.5  2014/08/18 22:02:32  tw
// Tests: Klappmechanismus f. Tabelle.
//
// Revision 1.4  2014/08/18 12:34:34  tw
// Bugfix: Aep-Benutzer-Rechteverwaltung.
//
// Revision 1.3  2014/08/16 14:37:08  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung: Anbindung Textfelder.
//
// Revision 1.2  2014/08/16 14:17:16  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung: Anbindung Textfelder.
//
// Revision 1.1  2014/08/15 14:40:12  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
//

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

/**
 * Simples Textfeld, ohne Prüfungen. Momentan für Name, Vorname.
 * 
 * @author Thomas Winter
 * @since 14.08.2014
 */
public class TextFieldColumn<T, S> extends TextFieldColumnBase<T, S> {

    /**
     * Standardlänge überschrieben!
     */
    private final static int TEXTFIELD_SIZE = 12;

    /**
     * Construct new text field column with the given property expression for property access and
     * sorting.
     * 
     * @param title
     *            The columns title
     * @param expression
     *            Property expression which will be used to extract value from the raw object, both
     *            for display and as sorting property
     */
    public TextFieldColumn(String title, String expression, IModel<?> m) {
        super(Model.of(title), expression, m);
    }

    @Override
    public String getCssClass() {
        return "static";
    }

    @Override
    public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
        MarkupContainer panel = new TextFieldPanel(componentId, rowModel);
        cellItem.add(panel);

        final T rowObject = rowModel.getObject();
        PropertyModel<String> model = newPropertyModel(rowObject);
        final FormComponent<String> textField = super.newTextField(TextFieldPanel.TEXTFIELD_ID, model, rowObject);
        textField.add(AttributeAppender.replace("size", TEXTFIELD_SIZE));
        panel.add(textField);
    }

    protected PropertyModel<String> newPropertyModel(T rowObject) {
        return new PropertyModel<String>(rowObject, getExpression());
    }
}
