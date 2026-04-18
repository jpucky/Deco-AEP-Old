// $Log: ViewShowHelp.java,v $
// Revision 1.3  2015/02/09 12:46:32  tw
// Haldenbearbeitung: Bugfix. Aufraeumarbeiten.
//
// Revision 1.2  2015/02/03 00:52:22  tw
// Haldenbearbeitung: Einlesen v. Archivierungsdatensaetzen ermoeglicht.
//
// Revision 1.1  2015/01/30 21:24:31  tw
// Haldenbearbeitung 1. Wurf.
//
//

package de.decodetron.tab.administration.haldebearbeiten;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

import de.decodetron.Const;
import de.decodetron.event.AbstractEvent;
import de.decodetron.event.ChangeEvent;
import de.decodetron.event.EventListenerInterface;

/**
 * 
 * 
 * Blendet ein Hilfe-Hinweis ein, wenn die PDF-Adresse null ist.
 * 
 * @author Thomas Winter
 * @since 30.01.2015
 */
public class ViewShowHelp extends Label implements EventListenerInterface {

    public ViewShowHelp(String id, final HaldeBearbeitenModel hbm) {
        super(id, hbm);
        // setRenderBodyOnly(true);
        setOutputMarkupId(true);
        setEscapeModelStrings(false);
        setDefaultModel(new Model<String>() {
            public String getObject() {

                Object o = hbm.getClickedFundstelle();
                StringBuffer buf = new StringBuffer();
                if (o == null) {
                    buf.append("<div class=\"viewpdf\">");
                    buf.append("<div>Bitte w\u00E4hlen Sie eine Fundstelle aus,<br>");
                    buf.append("damit ihnen ein Dokument angezeigt wird.</div>");
                    buf.append("</div>");
                }
                return buf.toString();
            }
        });
    }

    private void updateKlickHintView(ChangeEvent ce) {
        ce.update(ViewShowHelp.this);
    }

    @Override
    public void notifyAjaxEvent(AbstractEvent event) {
        if (event instanceof ChangeEvent) {
            String ident = ((ChangeEvent) event).getIdentifier();
            if (Const.KEY_HALDE_HALDEFILE_KLICK.equals(ident)) {
                ChangeEvent ce = ((ChangeEvent) event);
                updateKlickHintView(ce);
            }
        }
    }

}
