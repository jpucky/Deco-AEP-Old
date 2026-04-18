// $Log: MyMultilineLabel.java,v $
// Revision 1.2  2014/03/27 15:36:23  tw
// CR-Defektenlisten f. Lieferanten: Markup-freie Tabellengestaltung.
//
// Revision 1.1  2014/03/26 21:03:10  tw
// Vorbereitung, CR-Defektenlisten f. Lieferanten: Markup-freie Tabellengestaltung.
//
//

package de.decodetron.tab;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.AppendingStringBuffer;

/**
 * Die standart-Multiline-Komponente liefert noch so ein dämliches "&lt;p&gt" Tag mit. Das nervt an
 * dieser Stelle. Außerdem zicken die Label bei "&lt;br&gt;" in den .properties. Daher die eigene
 * Komponente.
 * 
 * @author Thomas Winter
 * @since 26.03.2014
 */
public class MyMultilineLabel extends MultiLineLabel {

    public MyMultilineLabel(String id, IModel<?> model) {
        super(id, model);
    }

    @Override
    public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {
        CharSequence body = toMultilineMarkup(getDefaultModelObjectAsString());
        replaceComponentTagBody(markupStream, openTag, body);
    }

    private CharSequence toMultilineMarkup(final CharSequence s) {
        if (s == null) {
            return null;
        }

        AppendingStringBuffer buffer = new AppendingStringBuffer();
        for (int i = 0; i < s.length(); i++) {
            final char c = s.charAt(i);

            switch (c) {
                case '\n':
                    buffer.append("<br/>");
                    break;

                case '\r':
                    break;

                default:
                    buffer.append(c);
                    break;
            }
        }

        return new AppendingStringBuffer(buffer.toString().replaceAll("&lt;br&gt;", "<br/>"));
    }
}
