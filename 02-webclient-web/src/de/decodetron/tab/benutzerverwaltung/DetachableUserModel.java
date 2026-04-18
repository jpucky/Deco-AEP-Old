// $Log: DetachableUserModel.java,v $
// Revision 1.1  2014/06/01 12:50:27  tw
// Benutzer aendern, Grundstruktur.
//
//

package de.decodetron.tab.benutzerverwaltung;

import org.apache.wicket.model.LoadableDetachableModel;

import de.decodetron.bo.User;

/**
 * @author Thomas Winter
 * @since 30.05.2014
 */
public class DetachableUserModel extends LoadableDetachableModel<User> {

    private Long id = null;
    private User user = null;

    public DetachableUserModel(User f) {
        this(f.getId());
        this.user = f;
    }

    public DetachableUserModel(Long id) {
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
        } else if (obj instanceof DetachableUserModel) {
            DetachableUserModel other = (DetachableUserModel) obj;
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
    protected User load() {
        return this.user;
    }
}
