// $Log: RSSProducerResource.java,v $
// Revision 1.1  2014/01/07 13:51:05  tw
// Resource-Beispiele.
//
//

package de.decodetron.data;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.request.resource.AbstractResource;

/**
 * @author Thomas Winter
 * @since 24.12.2013
 */
public class RSSProducerResource extends AbstractResource {

    @Override
    protected ResourceResponse newResourceResponse(Attributes attributes) {
        ResourceResponse resourceResponse = new ResourceResponse();
        resourceResponse.setContentType("text/xml");
        resourceResponse.setTextEncoding("utf-8");

        resourceResponse.setWriteCallback(new WriteCallback() {
            @Override
            public void writeData(Attributes attributes) throws IOException {
                OutputStream outputStream = attributes.getResponse().getOutputStream();
                Writer writer = new OutputStreamWriter(outputStream);
//                SyndFeedOutput output = new SyndFeedOutput();
//                try {
//                    output.output(getFeed(), writer);
//                } catch (FeedException e) {
//                    throw new WicketRuntimeException("Problems writing feed to response...");
//                }
            }
        });

        return resourceResponse;
    }

}
