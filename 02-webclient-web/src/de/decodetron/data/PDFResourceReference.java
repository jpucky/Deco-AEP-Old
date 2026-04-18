// $Log: PDFResourceReference.java,v $
// Revision 1.1  2014/01/07 13:51:05  tw
// Resource-Beispiele.
//
//

package de.decodetron.data;

import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * @author Thomas Winter
 * @since 24.12.2013
 */
public class PDFResourceReference extends ResourceReference {

    public PDFResourceReference(Class<?> scope, String name) {
        super(scope, "pdfdemo");
    }

    @Override
    public IResource getResource() {
        // 
        return null;
    }

}
