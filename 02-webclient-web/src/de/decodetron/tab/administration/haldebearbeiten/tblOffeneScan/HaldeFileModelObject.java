// $Log: HaldeFileModelObject.java,v $
// Revision 1.1  2015/03/17 22:10:36  tw
// CR Feng-ID: 3947
//
//

package de.decodetron.tab.administration.haldebearbeiten.tblOffeneScan;

import org.apache.wicket.model.LoadableDetachableModel;

import de.decodetron.bo.HaldeFile;

/**
 * @author Thomas Winter
 * @since 16.03.2015
 */
public class HaldeFileModelObject extends LoadableDetachableModel<HaldeFile> {

    private String id;
    private HaldeFile haldeFile;

    public HaldeFileModelObject(HaldeFile f) {
        this.id = f.toString();
        this.haldeFile = f;
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
        } else if (obj instanceof HaldeFileModelObject) {
            HaldeFileModelObject other = (HaldeFileModelObject) obj;
            return other.id.equals(id);
        }
        return false;
    }

    @Override
    protected HaldeFile load() {
        return this.haldeFile;
    }
}
