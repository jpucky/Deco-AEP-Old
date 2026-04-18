// $Log: DetachableUserSectionModel.java,v $
// Revision 1.1  2014/08/08 16:05:21  tw
// Vorbereitung: Aep-Benutzerr-Rechteverwaltung.
//
//

package de.decodetron.tab.benutzerverwaltung;

import org.apache.wicket.model.LoadableDetachableModel;

import de.decodetron.bo.UserSectionData;

/**
 * @author Thomas Winter
 * @since 08.08.2014
 */
public class DetachableUserSectionModel extends LoadableDetachableModel<UserSectionData> {

    private Long id = null;
    private UserSectionData user = null;

    public DetachableUserSectionModel(UserSectionData f) {
        this(f.getId());
        this.user = f;
    }

    public DetachableUserSectionModel(Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        this.id = id;
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
        } else if (obj instanceof DetachableUserSectionModel) {
            DetachableUserSectionModel other = (DetachableUserSectionModel) obj;
            return other.id.equals(id);
        }
        return false;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    protected UserSectionData load() {
        return this.user;
    }

}
