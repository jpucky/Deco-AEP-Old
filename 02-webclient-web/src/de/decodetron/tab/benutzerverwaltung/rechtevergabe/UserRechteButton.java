// $Log: UserRechteButton.java,v $
// Revision 1.3  2014/10/10 12:05:22  tw
// Implementierung: Benutzerrechte Vertriebsleiter.
//
// Revision 1.2  2014/09/13 15:22:54  tw
// Rechteverwaltung: Bugfix: Textfeldaenderungen werden richtig verarbeitet f. d. Liste d. geaenderten Benutzer.
//
// Revision 1.1  2014/09/10 16:15:42  tw
// Aep-Benutzerr-Rechteverwaltung: Aufraeumarbeiten, Klassen entdroeselt.
//
//

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.IModel;

import de.decodetron.AEPModel;
import de.decodetron.Const;
import de.decodetron.bo.UserSectionData;
import de.decodetron.event.AbstractEvent;
import de.decodetron.event.ChangeEvent;
import de.decodetron.event.EventListenerInterface;

/**
 * Ein Basisbutton für Speichern und Abbrechen der sich zeigt, wenn ein Benutzerattribut geändert
 * wurde.
 * 
 * @author Thomas Winter
 * @since 10.09.2014
 */
public class UserRechteButton extends Button implements EventListenerInterface {

    /**
     * @param String
     *            id
     * @param AEPModel
     *            model
     */
    @SuppressWarnings("unchecked")
    public UserRechteButton(String id, IModel<?> iModel) {
        super(id, (IModel<String>) iModel);
        setOutputMarkupPlaceholderTag(true);
        
    }

    @Override
    public boolean isVisible() {
        AEPModel vm = (AEPModel) getDefaultModelObject();
        HashMap<Long, UserSectionData> hm = vm.getBenVerwaltungModel().getUserList2Change();
        return (hm.size() >= 1);
    }

    private void updateButton(ChangeEvent ce) {
        ce.update(UserRechteButton.this);
    }

    @Override
    public void notifyAjaxEvent(AbstractEvent event) {

        if (event instanceof ChangeEvent) {
            ChangeEvent ce = ((ChangeEvent) event);
            String ident = ce.getIdentifier();
            if (Const.KEY_USER_ATTRIB_2CHANGE_SELECTED.equals(ident)) {
                updateButton(ce);
            }
        }
    }

    /**
     * "Überführt" die Benutzer aus der HashMap in eine "Liste-aller-geänderten-Benutzer".
     * 
     * @return List<UserSectionData>
     */
    public List<UserSectionData> getUserSectionDatas() {
        List<UserSectionData> list = new ArrayList<UserSectionData>();
        AEPModel model = (AEPModel) getDefaultModelObject();
        HashMap<Long, UserSectionData> hm = model.getBenVerwaltungModel().getUserList2Change();
        Set<Long> keyset = hm.keySet();
        for (Iterator<Long> iterator = keyset.iterator(); iterator.hasNext();) {
            Long key = iterator.next();
            list.add(hm.get(key));
        }
        return list;
    }

    /**
     * Datentonne für die geänderten Benutzer
     */
    public void initUserSectionChangeModel() {
        AEPModel model = (AEPModel) getDefaultModelObject();
        model.getBenVerwaltungModel().initUserList2Change();
    }

    /**
     * Datentonne für alle geladenen Benutzer in unverändertem Zustand, um sich den DB-Zugriff zu
     * sparen.
     */
    public void initUserSectionOrignialModel() {
        AEPModel model = (AEPModel) getDefaultModelObject();
        model.getBenVerwaltungModel().initUserListOriginal();
    }

    public void initErrorList() {
        AEPModel model = (AEPModel) getDefaultModelObject();
        model.getBenVerwaltungModel().setErrorList(new ArrayList<String>());
    }
}
