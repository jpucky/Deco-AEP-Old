// $Log: AMarkupProvider.java,v $
// Revision 1.1  2014/02/13 02:03:18  tw
// Benutzerverwaltung, dynamischer Suchfilteraufbau.
//
//

package de.decodetron;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;

/**
 * Mit dieser Klasse ist es möglich, dynamisch Markup zu generieren. Der Markuptext kann wiederum
 * wicket:ids enthalten.
 * 
 * @author Thomas Winter
 * @since 12.02.2014
 */
public abstract class AMarkupProvider extends Panel implements IMarkupResourceStreamProvider {

    public AMarkupProvider(String id) {
        this(id, null);
    }

    public AMarkupProvider(String id, IModel<?> model) {
        super(id, model);
    }

    /**
     * Ermöglicht neben dem Einfügen von Quelltext auch das Verwenden von wicket:ids,
     * wicket:message, usw. "<label><wicket:message key=\"label.BenLogin\"/></label>";
     * 
     * @return String
     */
    public abstract String getMyMarkup();

    public String getHtml() {
        StringBuffer sb = new StringBuffer();
        sb.append("<wicket:panel>").append("\n");
        sb.append(getMyMarkup());
        sb.append("</wicket:panel>").append("\n");
        return sb.toString();
    }

    @Override
    protected boolean getStatelessHint() {
        return true;
    }

    @Override
    public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
        return new AbstractResourceStream() {

            public InputStream getInputStream() throws ResourceStreamNotFoundException {
                return new ByteArrayInputStream(getHtml().getBytes());
            }

            public void close() throws IOException {}
        };
    }
}
