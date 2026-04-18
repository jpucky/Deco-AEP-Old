// $Log: ButtonColumnCopyUser.java,v $
// Revision 1.4  2014/11/06 13:13:03  tw
// Anbindung: History-DB Benutzerrechte.
//
// Revision 1.3  2014/10/29 19:06:16  tw
// Implementierung: Benutzer kopieren.
//
// Revision 1.2  2014/10/29 17:47:39  tw
// Implementierung: Benutzer kopieren.
//
// Revision 1.1  2014/10/29 14:58:59  tw
// Bugfix: setReuseitems f. sinnvollerer Errorhandling. Vorbereitung: Benutzer kopieren.
//
//

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import java.sql.Timestamp;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.Model;

import de.decodetron.AEPApplication;
import de.decodetron.bo.User;
import de.decodetron.bo.UserSectionData;
import de.decodetron.data.Util;
import de.decodetron.security.LoginSession;

/**
 * @author Thomas Winter
 * @since 29.10.2014
 */
public class ButtonColumnCopyUser<T, S> extends ButtonColumn<T, S> {

    private BenListe benListe;
    
    public ButtonColumnCopyUser(String title, BenListe bl) {
        super(Model.of(title), "");
        benListe = bl;
    }

    @Override
    public String getCssClass() {
        return "rotate copyuser";
    }

    @Override
    public Component getHeader(String componentId) {
        return new DivMultiLineLabel(componentId, getDisplayModel());
    }

    protected org.apache.wicket.markup.html.form.FormComponent<String> newButton(String id, final T rowObject) {
        final FormComponent<String> button = super.newButton(id, rowObject);

        button.add(new AjaxFormComponentUpdatingBehavior("onclick") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {

                UserSectionData userCopy = (UserSectionData) rowObject;
                //System.out.println("Checked: " + userCopy.getLogin());
                //System.out.println("Checked: " + userCopy.getFilter());
                userCopy.setId(null);
                userCopy.setLogin(Util.getCurrentTimeStamp());
                userCopy.setPasswd(de.decodetron.util.Const.STANDARTPWD_VER);
                userCopy.setAnlagedatum(new Timestamp(System.currentTimeMillis()).toString());
                AEPApplication.get().getDBUser().insertH(LoginSession.get().getUser(), userCopy);
                
                target.add(benListe);
            }

            // @Override
            // protected IAjaxCallDecorator getAjaxCallDecorator() {
            // return AjaxCheckboxColumn.this.getAjaxCallDecorator();
            // }
        });

        return button;

    };

    protected void onUpdate(FormComponent<String> checkbox, T rowObject, boolean value, AjaxRequestTarget target) {
        System.out.println("Checked: " + ((UserSectionData) rowObject).getLogin());
    }
}
