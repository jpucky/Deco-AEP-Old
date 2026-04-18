/*
 * Artifactory is a binaries repository manager.
 * Copyright (C) 2012 JFrog Ltd.
 *
 * Artifactory is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Artifactory is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Artifactory.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import static de.decodetron.tab.benutzerverwaltung.rechtevergabe.CheckBoxPanel.CHECKBOX_ID;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

/**
 * CheckboxColmn für eine nicht-Ajax-Ansicht.
 * 
 * @see http
 *      ://subversion.jfrog.org/artifactory/public/trunk/web/common/src/main/java/org/artifactory
 *      /common/wicket/component/table/columns/
 * @author Yoav Aharoni
 * @param <S>
 */
public class CheckboxColumn<T, S> extends AbstractColumn<T, S> {

    private IModel<?> parentModel;
    private String expression;
    private T rowObject;

    public CheckboxColumn(IModel<String> title, String expression) {
        super(title);
        this.expression = expression;
    }

    public CheckboxColumn(IModel<String> title, String expression, IModel<?> m) {
        super(title);
        this.expression = expression;
        this.parentModel = m;
    }

    @Override
    public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
        rowObject = rowModel.getObject();

        CheckBoxPanel panel = new CheckBoxPanel(componentId, rowModel);
        // cellItem.add(new CssClass("CheckboxColumn"));
        cellItem.add(panel);

        IModel<Boolean> model = newPropertyModel(rowObject);
        FormComponent<?> checkBox = newCheckBox(CHECKBOX_ID, model, rowObject);
        panel.add(checkBox);

        boolean enabled = isEnabled(rowObject);
        checkBox.setEnabled(enabled);
    }

    protected FormComponent<Boolean> newCheckBox(String id, IModel<Boolean> model, T rowObject) {
        return new CheckBox(id, model);
    }

    protected FormComponent<Boolean> newAjaxCheckBox(String id, IModel<Boolean> model, T rowObject) {
        FormComponent<Boolean> fc = new AjaxCheckBox(id, model) {

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                // TODO Auto-generated method stub

            }
        };
        return fc;
    }

    /**
     * getExpression() entspricht einem Feld von rowObject.
     * 
     * @param T
     *            rowObject
     * @return IModel<Boolean>
     */
    protected IModel<Boolean> newPropertyModel(T rowObject) {
        try {
            return new PropertyModel<Boolean>(rowObject, getExpression());
        } catch (Exception e) {
            return Model.of(Boolean.FALSE);
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