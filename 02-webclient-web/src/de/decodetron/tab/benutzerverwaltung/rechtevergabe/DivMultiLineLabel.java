// $Log: DivMultiLineLabel.java,v $
// Revision 1.3  2014/09/10 16:15:42  tw
// Aep-Benutzerr-Rechteverwaltung: Aufraeumarbeiten, Klassen entdroeselt.
//
// Revision 1.2  2014/08/26 14:37:22  tw
// Aep-Benutzerr-Rechteverwaltung: Benutzer Loeschen implementiert.
//
// Revision 1.1  2014/08/26 01:18:59  tw
// Aep-Benutzerr-Rechteverwaltung: Benutzer Loeschen.
//
//

package de.decodetron.tab.benutzerverwaltung.rechtevergabe;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.AppendingStringBuffer;

/**
 * Eigenes Multilinelabel. Da für die schräggestellten Tabellenköpfe ein zusätzlicher div/span -
 * Container nötig war, ist dieser Handstand nötig, um der vorgegebenen Struktur einen weiteren
 * Container unterzujubeln.
 * 
 * @author Thomas Winter
 * @since 26.08.2014
 */
public class DivMultiLineLabel<T> extends MultiLineLabel {

    /**
     * Dient dazu dem HEADER einer AbstractColumn oder speziellen Column einen zusätzlichen div -
     * Container / class - Attribut in die vorhandene Struktur einzumergen.
     * 
     * @param String
     *            id
     * @param IModel
     *            <?> model
     */
    public DivMultiLineLabel(String id, IModel<T> model) {
        super(id, model);
        setRenderBodyOnly(true);
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
        buffer.append("<div class=\"textH\">");
        buffer.append("<span>");
        for (int i = 0; i < s.length(); i++) {
            final char c = s.charAt(i);
            buffer.append(c);
        }
        buffer.append("</span>");
        buffer.append("</div>");
        return buffer.toString();
    }
}
