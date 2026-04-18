// $Log: CheckBoxColumnDeleteUser.java,v $
// Revision 1.3  2014/09/12 15:04:16  tw
// Aep-Benutzerr-Rechteverwaltung: Setzten des Defaultpasswortes.
//
// Revision 1.2  2014/08/26 14:37:22  tw
// Aep-Benutzerr-Rechteverwaltung: Benutzer Loeschen implementiert.
//
// Revision 1.1  2014/08/26 01:18:59  tw
// Aep-Benutzerr-Rechteverwaltung: Benutzer Loeschen.
//
//

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.core.util.lang.PropertyResolver;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import de.decodetron.AEPModel;
import de.decodetron.bo.UserSectionData;

/**
 * Checkbox um die zu löschenden Benutzer zu sammeln und per Nachfrage zu löschen.
 * 
 * @author Thomas Winter
 * @since 26.08.2014
 */
public class CheckBoxColumnDeleteUser<T, S> extends CheckboxColumn<T, S> {

    public CheckBoxColumnDeleteUser(String title, String expression, IModel<AEPModel> m) {
        super(Model.of(title), expression, m);
    }

    @Override
    public Component getHeader(String componentId) {
        return new DivMultiLineLabel<String>(componentId, getDisplayModel());
    }

    @Override
    public String getCssClass() {
        return "rotate";
    }

    @Override
    protected FormComponent<Boolean> newCheckBox(String id, final IModel<Boolean> model, final T rowObject) {
        final FormComponent<Boolean> checkbox = super.newAjaxCheckBox(id, model, rowObject);

        checkbox.add(new AjaxFormComponentUpdatingBehavior("onclick") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                Boolean checked = checkbox.getModelObject();
                CheckBoxColumnDeleteUser.this.onUpdate(checkbox, rowObject, checked, target);
            }
        });

        return checkbox;
    }

    /**
     * Öhem. Eine andere Möglichkeit sehe ich Momentan nicht, um Exceptions bei neuen, unbekannten
     * Sektionen aus dem Weg zu gehen. Siehe auch:
     * http://wicketinaction.com/2009/01/fixing-wicket-property-models-using-salve/
     * 
     * <br>
     * Punkt 1 sieht hier vor: "Do not use property models ...". <br>
     * <br>
     * So lange ich hier nur eine handvoll Benutzer anzeigen muss, lasse ich die
     * Schrottimplementierung mal so wie sie ist, twinter 26.08.2014.
     * 
     * @param T
     *            rowObject
     * @return IModel<Boolean>
     */
    @Override
    protected IModel<Boolean> newPropertyModel(T rowObject) {
        try {
            PropertyResolver.getValue(getExpression() + "." + "isSet", rowObject);
            // String isSet = BeanUtils.getProperty(rowObject, getExpression() + "." + "isSet");
            // return Model.of("true".equals(isSet) ? Boolean.TRUE : Boolean.FALSE);
            return new PropertyModel<Boolean>(rowObject, getExpression() + "." + "isSet");
        } catch (Exception e) {
            return Model.of(Boolean.FALSE);
        }
    }

    protected void onUpdate(FormComponent<Boolean> checkbox, T rowObject, boolean value, AjaxRequestTarget target) {
        UserSectionData user = (UserSectionData) rowObject;
        AEPModel vm = (AEPModel) getModel().getObject();
        vm.getBenVerwaltungModel().addUser2Delete(user);
        target.appendJavaScript("$.fn.showDeleteUser(\"" + user.getLogin() + "\")");
    }
}
