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

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.core.util.lang.PropertyResolver;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.string.AppendingStringBuffer;

import de.decodetron.AEPApplication;
import de.decodetron.AEPModel;
import de.decodetron.Const;
import de.decodetron.bo.UserSectionData;
import de.decodetron.event.ChangeEvent;

/**
 * Die Ajax Variante einer Checkbox mit UpdatingBehavior.
 * 
 * @author Thomas Winter
 * @param <T, S>
 */
public class CheckBoxColumnSection<T, S> extends CheckboxColumn<T, S> {

    public CheckBoxColumnSection(IModel<String> title, String expression, IModel<?> m) {
        super(title, expression, m);
    }

    @Override
    public String getCssClass() {
        StringBuilder css = new StringBuilder();
        try {
            if (getRowObject() instanceof UserSectionData) {
                UserSectionData ud = (UserSectionData) getRowObject();
                css.append("level" + BeanUtils.getProperty(ud, getExpression() + ".level"));
                css.append(" rotate");
            }
        } catch (Exception e) {
            // e.printStackTrace();
            css.append("level" + "unknown");
        }
        return css.toString();
    }

    @Override
    public Component getHeader(String componentId) {
        return new DivMultiLineLabel(componentId, getDisplayModel());
    }

    @Override
    protected FormComponent<Boolean> newCheckBox(String id, final IModel<Boolean> model, final T rowObject) {
        final FormComponent<Boolean> checkbox = super.newCheckBox(id, model, rowObject);

        checkbox.add(new AjaxFormComponentUpdatingBehavior("onclick") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                Boolean checked = checkbox.getModelObject();
                CheckBoxColumnSection.this.onUpdate(checkbox, rowObject, checked, target);
            }

            // @Override
            // protected IAjaxCallDecorator getAjaxCallDecorator() {
            // return AjaxCheckboxColumn.this.getAjaxCallDecorator();
            // }
        });

        return checkbox;
    }

    // protected IAjaxCallDecorator getAjaxCallDecorator() {
    // return null;
    // }

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
    
    /**
     * Called when the checkbox is updated (checked/unchecked).
     * 
     * @param checkbox
     *            The updated checkbox.
     * @param rowObject
     *            The affected row model.
     * @param value
     *            True if the checkbox is checked.
     * @param target
     *            The ajax target (the table container is added by default).
     */

    protected void onUpdate(FormComponent checkbox, T rowObject, boolean value, AjaxRequestTarget target) {
        // System.out.println("Checked: " + ((UserSectionData) rowObject).getLogin() + ", " +
        // rowObject.toString());
        // System.out.println("Checked: " + ((User) rowObject).getNachname() + ", " +
        // rowObject.toString());
        UserSectionData user = (UserSectionData) rowObject;
        user.setTmpgroupname(AEPApplication.get().getDBUser().generateGroupName4User(user));
        refreshUserSectionModel(user);
        //showMap();
        new ChangeEvent(checkbox, target, null, Const.KEY_USER_ATTRIB_2CHANGE_SELECTED).fire();
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
        AEPModel vm = (AEPModel) getModel().getObject();
        HashMap<Long, UserSectionData> hm = vm.getBenVerwaltungModel().getUserList2Change();
        LinkedHashSet<UserSectionData> origUserSet = vm.getBenVerwaltungModel().getUserListOriginal();

        // System.out.println("User2Change: " + hm.size());
        // System.out.print("origUserSet: ");
        // showOriginalMap(origUserSet);
        // System.out.println("\n|");
        // System.out.println("v");

         //System.out.println("klicked user ek : " + user.toString());
         //System.out.println("klicked user sr : " + user.getSr().getIsSet());

//        StringWriter sw = new StringWriter();
//        try {
//            JAXBContext jaxbCtx = JAXBContext.newInstance(UserSectionData.class);
//            Marshaller jaxbMarshaller = jaxbCtx.createMarshaller();
//            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
//            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            //jaxbMarshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "LIEFCTRL.xsd");
//            jaxbMarshaller.marshal(user, new File("d:\\tmp\\file.xml"));
//            jaxbMarshaller.marshal(user, sw);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("--" + e.getMessage());
//        }
//        
//        System.out.println(sw.toString());
//        try {
//            sw.close();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        
        Long listKey = user.getId();
        if (!hm.containsKey(listKey)) {
            hm.put(listKey, user);
        } else {
            UserSectionData uTmp = hm.get(listKey);
            if (origUserSet.contains(uTmp)) {
                hm.remove(listKey);
                // keine Änderung, heisst Benutzer muss nicht gespeichert werden.
            } else {
                hm.put(listKey, user);
            }
        }

         //System.out.println("User2Change: " + hm.size());
         //System.out.println("origUserSet: " + origUserSet.size());
    }

    private void showOriginalMap(LinkedHashSet<UserSectionData> origUserSet) {
        for (Iterator<UserSectionData> iterator = origUserSet.iterator(); iterator.hasNext();) {
            UserSectionData userSectionData = iterator.next();
            System.out.print(userSectionData.getEk().getIsSet() + ", " + userSectionData.getSr().getIsSet());
        }
    }

    public void showMap() {
        AEPModel vm = (AEPModel) getModel().getObject();
        HashMap<Long, UserSectionData> hm = vm.getBenVerwaltungModel().getUserList2Change();
        Set keyset = hm.keySet();

        if (keyset.isEmpty()) {
            System.out.println("User: " + "---");
        }

        for (Iterator<Long> iterator = keyset.iterator(); iterator.hasNext();) {
            Long key = iterator.next();
            UserSectionData user = hm.get(key);
            System.out.println("User: " + user.toString());
        }
    }
}
