// $Log: RechercheModel.java,v $
// Revision 1.2  2017/06/21 19:44:17  tw
// Mobilmachung der Headrevision.
//
// Revision 1.1  2016/01/31 17:01:04  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
//

package de.decodetron.tab.recherche;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.util.value.ValueMap;

import de.decodetron.Const;
import de.decodetron.bo.Fundstelle;
import de.decodetron.data.HighlitableDataItemSortable;
import de.decodetron.tab.statistik.TxtFilter;

/**
 * @author Thomas Winter
 * @since 21.01.2016
 */
public class RechercheModel extends ValueMap {

    // ///////////////////////////////////////////////
    // /// KEY_LIST_FILTERSUCHE, Wichtig: LinkedHashSet<String> !!! Jedes Suchattribut darf nur
    // einmal vorkommen.
    // /
    /**
     * Initialisiert, bzw. löscht alle Suchattribute.
     * 
     * @see#KEY_LIST_FILTERSUCHE
     */
    public void initSuchItems() {
        put(Const.KEY_LIST_FILTERSUCHE, new ArrayList<TxtFilter>());
    }

    /**
     * Liefert alle Text-Suchfelder.
     * 
     * @return List<TxtFilter>
     */
    @SuppressWarnings("unchecked")
    public List<TxtFilter> getSuchItems() {
        if (get(Const.KEY_LIST_FILTERSUCHE) == null) {
            initSuchItems();
        }
        return (List<TxtFilter>) get(Const.KEY_LIST_FILTERSUCHE);
    }

    /**
     * Fügt ein Suchitem hinzu. Sollte es schon existieren, wird das alte durch das neue ersetzt. An
     * dieser Stelle kann man den Wert des alten Feldes auf das neue Übertragen.
     * 
     * @param TxtFilter
     *            txt
     */
    public void addSuchItem(TxtFilter txtNew) {
        if (get(Const.KEY_LIST_FILTERSUCHE) == null) {
            initSuchItems();
        }
        List<TxtFilter> set = ((List<TxtFilter>) get(Const.KEY_LIST_FILTERSUCHE));
        int i = indexOfTxtFilter(set, txtNew);
        if (i != -1) {

            // System.out.println("check new: " + txtNew.getText());
            // System.out.println("check old: " + set.get(i).getText());

            txtNew.setText(set.get(i).getText());
            set.remove(i);
            set.add(txtNew);
        } else {
            set.add(txtNew);
        }

        put(Const.KEY_LIST_FILTERSUCHE, set);
        // showSuchattribute();
    }

    /**
     * Ein TxtFilter-Element existiert, wenn die getId() schon existiert.
     * 
     * @param Set
     *            <TxtFilter> set
     * @return boolean
     */
    private int indexOfTxtFilter(List<TxtFilter> set, TxtFilter itm2Check) {
        int exist = -1;
        if ((set != null) && (itm2Check != null) && (itm2Check.getId() != null)) {
            for (int i = 0; i < set.size(); i++) {
                TxtFilter txtFilter = set.get(i);
                if (itm2Check.getId().equals(txtFilter.getId())) {
                    exist = i;
                    break;
                }
            }
        }
        return exist;
    }

    // /**
    // * Fügt mehrere Suchitems hinzu.
    // *
    // * @param List
    // * <TxtFilter> txt
    // */
    // public void addSuchItems(Set<TxtFilter> txt) {
    // if (get(Const.KEY_LIST_FILTERSUCHE) == null) {
    // initSuchItems();
    // }
    // ((Set<TxtFilter>) get(Const.KEY_LIST_FILTERSUCHE)).addAll(txt);
    // showSuchattribute();
    // }

    /**
     * Die PDF-Fundstellen aus der Tabellenansicht Recherche.
     */
    public void addFundstelle(Fundstelle fs) {
        put(Const.KEYFUNDSTELLE, fs);
    }

    /**
     * Die PDF-Fundstellen aus der Tabellenansicht Recherche.
     * 
     * @return Fundstelle
     */
    public Fundstelle getFundstelle() {
        return (Fundstelle) get(Const.KEYFUNDSTELLE);
    }

    /**
     * Speichert das selektierte Item für die Selektionserkennung
     * 
     * @param HighlitableDataItemSortable item
     */
    public void addSelectedItem(HighlitableDataItemSortable<?> item){
        put(Const.KEYTOGGLEDITEM, item);
    }
    
    public HighlitableDataItemSortable<?> getSelectedItem(){
        return (HighlitableDataItemSortable<?>)get(Const.KEYTOGGLEDITEM);
    }
    
    private void showSuchattribute() {
        List<TxtFilter> ltxt = ((List<TxtFilter>) get(Const.KEY_LIST_FILTERSUCHE));
        System.out.println(ltxt.size());
        for (Iterator<TxtFilter> iterator = ltxt.iterator(); iterator.hasNext();) {
            TxtFilter txtFilter = iterator.next();
            System.out.println(txtFilter.getTblIdent() + " : " + txtFilter.getText());
        }
    }
}
