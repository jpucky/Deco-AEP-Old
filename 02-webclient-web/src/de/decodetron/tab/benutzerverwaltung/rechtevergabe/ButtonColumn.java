// $Log: ButtonColumn.java,v $
// Revision 1.1  2014/10/29 14:58:59  tw
// Bugfix: setReuseitems f. sinnvollerer Errorhandling. Vorbereitung: Benutzer kopieren.
//
//

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import static de.decodetron.tab.benutzerverwaltung.rechtevergabe.ButtonPanel.BUTTON_ID;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import de.decodetron.bo.UserSectionData;

/**
 * @author Thomas Winter
 * @since 29.10.2014
 */
public class ButtonColumn<T, S> extends AbstractColumn<T, S> {

    private IModel<?> parentModel;
    private String expression;
    private T rowObject;

    public ButtonColumn(IModel<String> title, String expression) {
        super(title);
        this.expression = expression;
    }

    public ButtonColumn(IModel<String> title, String expression, IModel<?> m) {
        super(title);
        this.expression = expression;
        this.parentModel = m;
    }

    @Override
    public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
        rowObject = rowModel.getObject();

        ButtonPanel panel = new ButtonPanel(componentId, rowModel);
        // cellItem.add(new CssClass("CheckboxColumn"));
        cellItem.add(panel);

        // IModel<String> model = newPropertyModel(rowObject);
        FormComponent<String> button = newButton(BUTTON_ID, rowObject);
        panel.add(button);

        boolean enabled = isEnabled(rowObject);
        button.setEnabled(enabled);
    }

    protected FormComponent<String> newButton(String id, T rowObject) {
        return new Button(id);
    }
//
//    protected FormComponent<String> newAjaxButton(String id, final T rowObject) {
//        FormComponent<String> fc = new AjaxButton(id) {
//
//            @Override
//            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
//                // TODO Auto-generated method stub
//                System.out.println("Copy-User: " + ((UserSectionData) rowObject).getLogin());
//            }
//        };
//        return fc;
//    }

    /**
     * getExpression() entspricht einem Feld von rowObject.
     * 
     * @param T
     *            rowObject
     * @return IModel<Boolean>
     */
    protected IModel<String> newPropertyModel(T rowObject) {
        try {
            return new PropertyModel<String>(rowObject, getExpression());
        } catch (Exception e) {
            return Model.of("");
        }
    }

    protected boolean isEnabled(T rowObject) {
        return true;
    }

    protected final String getExpression() {
        return expression;
    }

    protected IModel<?> getModel() {
        return this.parentModel;
    }

    protected T getRowObject() {
        return this.rowObject;
    }
}
