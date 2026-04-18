// $Log: ChangedUserListNew.java,v $
// Revision 1.2  2014/10/13 10:16:38  tw
// Beim Blaettern werden geaenderte Benutzer angezeigt.
//
// Revision 1.1  2014/10/10 12:06:41  tw
// Implementierung: Benutzerrechte Vertriebsleiter.
//
//

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;

import de.decodetron.AEPModel;
import de.decodetron.bo.UserSectionData;

/**
 * Ausgelagerte Liste der veränderten Benutzer.
 * 
 * @author Thomas Winter
 * @since 07.10.2014
 */
public class ChangedUserListNew extends ListView<UserSectionData> {

    private AEPModel am;

    public ChangedUserListNew(String id, final AEPModel model) {
        super(id, new LoadableDetachableModel<List<UserSectionData>>() {

            @Override
            @SuppressWarnings("unchecked")
            protected List<UserSectionData> load() {
                List<UserSectionData> list = new ArrayList<UserSectionData>();
                // AEPModel model = (AEPModel) getDefaultModelObject();
                HashMap<Long, UserSectionData> hm = model.getBenVerwaltungModel().getUserList2Change();
                Set<Long> keyset = hm.keySet();
                for (Iterator<Long> iterator = keyset.iterator(); iterator.hasNext();) {
                    Long key = iterator.next();
                    list.add(hm.get(key));
                }
                return list;
            }

        });
        am = model;
    }

    @Override
    public boolean isVisible() {
        List<UserSectionData> list = new ArrayList<UserSectionData>();
        HashMap<Long, UserSectionData> hm = am.getBenVerwaltungModel().getUserList2Change();
        //System.out.println("List2ChangeNeu: " + hm.size());
        return hm.size() <= 0 ? false : true;
    }

    // @Override
    // protected IModel<UserSectionData> getListItemModel(IModel<? extends List<UserSectionData>>
    // listViewModel,
    // int index) {
    // if(listViewModel.getObject().size() == 0){
    // setVisible(false);
    // }
    // return super.getListItemModel(listViewModel, index);
    // }

    @Override
    protected void populateItem(ListItem<UserSectionData> item) {
        UserSectionData user = item.getModelObject();
        item.add(new Label("login", user.getLogin()));
        item.add(new Label("groupname", user.getTmpgroupname()));
        // item.add(new Label("changeditems", user.getChangedItems().toString()));
    }

    /**
     * "Überführt" die Benutzer aus der HashMap in eine "Liste-aller-geänderten-Benutzer".
     * 
     * @return List<UserSectionData>
     */
    private List<UserSectionData> getUserSectionDatas() {

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
}
