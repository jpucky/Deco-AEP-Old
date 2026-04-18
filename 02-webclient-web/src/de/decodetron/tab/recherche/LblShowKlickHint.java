// $Log: LblShowKlickHint.java,v $
// Revision 1.7  2017/06/23 11:57:58  tw
// Mobilmachung der Headrevision.
//
// Revision 1.6  2017/06/21 19:44:17  tw
// Mobilmachung der Headrevision.
//
// Revision 1.5  2016/02/05 15:47:51  tw
// CR 3956: Interner Umbau: Events separiert.
//
// Revision 1.4  2016/02/05 15:24:32  tw
// CR 3956: Interner Umbau: Events separiert.
//
// Revision 1.3  2016/01/31 17:00:15  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.2  2016/01/19 23:15:07  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.1  2016/01/13 12:16:08  tw
// CR 3956: Interner Umbau: Vorbereitung eigener Panelkomponenten.
//
//

package de.decodetron.tab.recherche;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.decodetron.AEPModel;
import de.decodetron.tab.recherche.event.IRecOnBelegartSelektiert;
import de.decodetron.tab.recherche.event.IRecOnFundstelleClickEvent;
import de.decodetron.tab.recherche.event.RecOnBelegartSelektiert;
import de.decodetron.tab.recherche.event.RecOnFundstelleClickEvent;

/**
 * @author Thomas Winter
 * @since 12.01.2016
 */
public class LblShowKlickHint extends Label implements IRecOnFundstelleClickEvent, IRecOnBelegartSelektiert {

    private IModel<?> modelFs;

    public LblShowKlickHint(String id, final IModel<?> mm) {
        super(id);
        modelFs = mm;
        setOutputMarkupPlaceholderTag(true);
        setEscapeModelStrings(false);

        setDefaultModel(new Model<String>() {
            public String getObject() {

                AEPModel vm = (AEPModel) mm.getObject();
                Object o = vm.getRechercheModel().getFundstelle();
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

    public boolean isVisible() {
        return ((AEPModel) modelFs.getObject()).getRechercheModel().getFundstelle() == null ? true : false;
    }

    @Override
    public void onBelegartSelektiert(RecOnBelegartSelektiert b) {
        b.update(LblShowKlickHint.this);
    }

    @Override
    public void onFundstelleClick(RecOnFundstelleClickEvent ev) {
        ev.update(LblShowKlickHint.this);
    }

}
