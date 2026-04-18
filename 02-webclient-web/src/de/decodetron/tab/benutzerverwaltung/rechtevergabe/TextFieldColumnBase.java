// $Log: TextFieldColumnBase.java,v $
// Revision 1.5  2014/10/29 14:58:59  tw
// Bugfix: setReuseitems f. sinnvollerer Errorhandling. Vorbereitung: Benutzer kopieren.
//
// Revision 1.4  2014/09/13 15:22:54  tw
// Rechteverwaltung: Bugfix: Textfeldaenderungen werden richtig verarbeitet f. d. Liste d. geaenderten Benutzer.
//
// Revision 1.3  2014/09/10 16:15:42  tw
// Aep-Benutzerr-Rechteverwaltung: Aufraeumarbeiten, Klassen entdroeselt.
//
// Revision 1.2  2014/09/09 15:43:00  tw
// Aep-Benutzerr-Rechteverwaltung: Benutzer Anlegen, Backup.
//
// Revision 1.1  2014/08/28 20:37:30  tw
// Zusammenfassung TextfieldColumn.
//
//

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import java.util.HashMap;
import java.util.LinkedHashSet;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.StringValidator;

import de.decodetron.AEPModel;
import de.decodetron.Const;
import de.decodetron.bo.UserSectionData;
import de.decodetron.event.ChangeEvent;

/**
 * Gemeinsames Textfeld: Maxlänge: 50, setRequired(true).
 * 
 * @author Thomas Winter
 * @since 28.08.2014
 */
public abstract class TextFieldColumnBase<T, S> extends AbstractColumn<T, S> {

    private String expression;
    private IModel<?> parentModel;
    private final static int TEXTFIELD_SIZE = 6;
    private final static int TEXTFIELD_LENGHT = 50;

    public TextFieldColumnBase(IModel<String> displayModel, String expression, IModel<?> m) {
        super(displayModel);
        this.parentModel = m;
        this.expression = expression;
    }

    public TextFieldColumnBase(IModel<String> displayModel, String expression, S sortProperty) {
        super(displayModel, sortProperty);
        this.expression = expression;
    }

    protected TextField<String> newTextField(String id, IModel<String> valueModel, final T rowObject) {
        final TextField<String> txt = new TextField<String>(id, valueModel);
        txt.add(AttributeAppender.append("size", TEXTFIELD_SIZE));
        txt.add(StringValidator.maximumLength(TEXTFIELD_LENGHT));
        txt.setRequired(true);
        txt.add(new AjaxFormComponentUpdatingBehavior("onChange") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                // Object object = txt.getModelObject();
                // System.out.println("Text-Objekt: " + object);
                refreshUserSectionModel((UserSectionData) rowObject);
                new ChangeEvent(txt, target, null, Const.KEY_USER_ATTRIB_2CHANGE_SELECTED).fire();
            }
        });
        return txt;
    }

    /**
     * Diese Funktion guckt, welcher Benutzer gerade angeklickt wurde und überträgt nur geänderte
     * Benutzer in eine Liste. Wird die Änderung zurückgenommen, verschwindet der Benutzer wieder
     * aus der Liste.
     * 
     * @param UserSectionData
     *            user
     */
    private void refreshUserSectionModel(UserSectionData user) {
        AEPModel vm = (AEPModel) this.parentModel.getObject();
        HashMap<Long, UserSectionData> hm = vm.getBenVerwaltungModel().getUserList2Change();
        LinkedHashSet<UserSectionData> origUserSet = vm.getBenVerwaltungModel().getUserListOriginal();

        Long listKey = user.getId();
        if (!hm.containsKey(listKey)) {
            hm.put(listKey, user);
        } else {
            // Bei mehr als einer Änderung ...
            UserSectionData uTmp = hm.get(listKey);
            if (origUserSet.contains(uTmp)) {
                 hm.remove(listKey);
                // keine Änderung, heisst Benutzer muss nicht gespeichert werden.
                // Den Anspruch hab ich hier nicht. Wer im Textfeld rumfummelt, dessen Daten werden
                // gespeichert! Das Originalset ist an dieser Stelle nicht mehr zu gebrauchen da die
                // Modelle die Änderung schon eingepflegt haben. TODO: Gucken, warum sich das bei
                // der Checkbox anders verhält als beim Textfeld.
            } else {
                hm.put(listKey, user);
            }
        }
    }

    protected IModel<?> getModel() {
        return this.parentModel;
    }

    public final String getExpression() {
        return expression;
    }
}
