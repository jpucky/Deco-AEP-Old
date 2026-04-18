// $Log: DetachableFundstelleModel.java,v $
// Revision 1.1  2014/03/20 02:25:48  tw
// Recherche: Paginierung, Funktionstest. Event-Konstanten zusammengefasst.
//
//

package de.decodetron.tab.recherche;

import org.apache.wicket.model.LoadableDetachableModel;

import de.decodetron.bo.Fundstelle;

/**
 * Das wichtigste an dieser Komponente ist die überschriebene Hash- und Equals Methode, damit die
 * Item-Reuse-Strategie funktioniert!
 * 
 * @author Thomas Winter
 * @since 20.03.2014
 */
public class DetachableFundstelleModel extends LoadableDetachableModel<Fundstelle> {

    private final String id;
    private Fundstelle fundstelle;

    public DetachableFundstelleModel(Fundstelle f) {
        this(f.getId());
        this.fundstelle = f;
    }

    public DetachableFundstelleModel(String id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        this.id = id;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * used for dataview with ReuseIfModelsEqualStrategy item reuse strategy
     * 
     * @see org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (obj instanceof DetachableFundstelleModel) {
            DetachableFundstelleModel other = (DetachableFundstelleModel) obj;
            return other.id.equals(id);
        }
        return false;
    }

    @Override
    protected Fundstelle load() {
        return this.fundstelle;
    }
}
