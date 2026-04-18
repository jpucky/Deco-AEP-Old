// $Log: DDBelegTypContainer.java,v $
// Revision 1.5  2016/02/05 15:24:32  tw
// CR 3956: Interner Umbau: Events separiert.
//
// Revision 1.4  2016/01/19 23:15:06  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.3  2014/07/31 14:10:16  tw
// Scanbelege, Oberflaechenapassungen.
//
// Revision 1.2  2014/01/31 17:44:01  tw
// Anpassung der Defaulteinstellung/DropDownBoxen beim Laden der Seite.
//
// Revision 1.1  2014/01/30 23:19:16  tw
// Belegart: EK. Spaltenbezeichner Anpassung.
//
//

package de.decodetron.tab.recherche;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.decodetron.tab.recherche.event.IRecOnBelegartSelektiert;
import de.decodetron.tab.recherche.event.RecOnBelegartSelektiert;

/**
 * Slave - DDBox. Die Belegtyp-DropDownbox ist nur sichtbar, wenn sie mehr als einen Eintrag
 * besitzt.
 * 
 * @author Thomas Winter
 * @since 30.01.2014
 */
public class DDBelegTypContainer extends WebMarkupContainer implements IRecOnBelegartSelektiert {

    public DDBelegTypContainer(String id, IModel<?> model, final DropDownChoice<String> dd) {
        super(id, model);
        add(dd);
        setOutputMarkupId(true);
        add(AttributeModifier.replace("class", new Model<String>() {
            public String getObject() {
                List<? extends String> l = dd.getChoices();
                return l.size() <= 1 ? "invisible" : "";
            }
        }));
    }

    @Override
    public void onBelegartSelektiert(RecOnBelegartSelektiert b) {
        b.update(DDBelegTypContainer.this);
    }
}
