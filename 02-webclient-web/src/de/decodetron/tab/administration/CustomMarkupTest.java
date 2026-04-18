// $Log: CustomMarkupTest.java,v $
// Revision 1.1  2014/01/30 08:08:19  tw
// Umbau: Belegart-Auswahl. Backup.
//
//

package de.decodetron.tab.administration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.MarkupType;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;

/**
 * So könnte ich den Kram a la Jo erzeugen ...
 * 
 * @author Thomas Winter
 * @since 29.01.2014
 */
public class CustomMarkupTest extends Panel implements IMarkupResourceStreamProvider {

    public CustomMarkupTest(String id, IModel<?> model) {
        super(id, model);
        Form form = new Form("form");
        form.add(new TextField("myinput"));
        add(form);
    }

    public String getHtml() {
        // final Response origResponse = getRequestCycle().getResponse();
        // try {
        // final StringResponse stringResponse = new StringResponse();
        // getRequestCycle().setResponse(stringResponse);
        // renderAssociatedMarkup("panel", "dddddddddddd");
        // return stringResponse.toString();
        // } catch (Exception e) {
        // e.printStackTrace();
        // } finally {
        // getRequestCycle().setResponse(origResponse);
        // }
        // return "";

        String html = "<wicket:panel><form wicket:id=\"form\">" + "my text"
                + "<input wicket:id=\"myinput\" type=\"text\" />" + "<input type=\"submit\" value=\"submit\"/>"
                + "</form></wicket:panel>";
        return html;
    }

    @Override
    public MarkupType getMarkupType() {
        return MarkupType.HTML_MARKUP_TYPE;
    }

    @Override
    protected boolean getStatelessHint() {
        return true;
    }

    public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
        System.out.println("calling getMarkupResourceStream()");

        return new AbstractResourceStream() {

            public InputStream getInputStream() throws ResourceStreamNotFoundException {
                return new ByteArrayInputStream(getHtml().getBytes());
            }

            public void close() throws IOException {}
        };
    }
}
