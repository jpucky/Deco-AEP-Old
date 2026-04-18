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

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;

/**
 * Zu Dokzwecken mal aufgehoben, falls den Herrschaften die normale Checkbox nicht genügt.
 */
public class StyledCheckboxColumn<T, S> extends CheckboxColumn<T, S> {
    public StyledCheckboxColumn(IModel<String> title, String expression) {
        super(title, expression);
    }

    public StyledCheckboxColumn(IModel<String> title, String expression, IModel<?> m) {
        super(title, expression, m);
    }

    @Override
    protected FormComponent<Boolean> newCheckBox(String id, IModel<Boolean> model, T rowObject) {
        // return new StyledCheckbox(id, model).setTitle("");
        return new CheckBox(id, model);
    }
}