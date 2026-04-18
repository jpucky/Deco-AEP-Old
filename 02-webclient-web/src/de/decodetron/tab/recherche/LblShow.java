// $Log: LblShow.java,v $
// Revision 1.4  2016/02/05 15:24:32  tw
// CR 3956: Interner Umbau: Events separiert.
//
// Revision 1.3  2016/01/31 17:00:15  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.2  2016/01/19 23:15:07  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.1  2014/07/18 14:14:11  tw
// Labelanpassung Scannbelege.
//
// Revision 1.4  2014/03/20 02:22:22  tw
// Recherche: Paginierung, Funktionstest. Event-Konstanten zusammengefasst.
//
// Revision 1.3  2014/01/31 13:32:11  tw
// Umlaute!
//
// Revision 1.2  2014/01/31 13:28:24  tw
// Belegart nur fuer Superadmins sichtbar.
//
// Revision 1.1  2014/01/30 17:39:40  tw
// Umbau: Belegart-Auswahl. Aufraeumarbeiten.
//
//

package de.decodetron.tab.recherche;

import java.text.NumberFormat;
import java.util.Locale;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.Const;
import de.decodetron.tab.recherche.event.IRecOnPagingClickEvent;
import de.decodetron.tab.recherche.event.RecOnPagingClickEvent;

/**
 * Ausgelagerte Trefferanzeige. Treffermenge, gesamt.
 * 
 * @author Thomas Winter
 * @since 30.01.2014
 */
public class LblShow extends Label implements IRecOnPagingClickEvent {

    public LblShow(String id, final IModel<?> mm) {
        super(id);
        setOutputMarkupId(true);
        setDefaultModel(new Model<String>() {
            public String getObject() {
                ValueMap vm = (ValueMap) mm.getObject();
                Long hitpp = vm.get(Const.KEYHITSPERPAGE) == null ? 0 : (Long) vm.get(Const.KEYHITSPERPAGE);
                Long showpp = vm.get(Const.KEYSHOWPERPAGE) == null ? 0 : (Long) vm.get(Const.KEYSHOWPERPAGE);
                NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);

                StringBuilder sb = new StringBuilder();
                sb.append(nf.format(showpp));
                if (hitpp > showpp) {
                    sb.append(" / ");
                    sb.append(nf.format(hitpp));
                }

                return sb.toString();
            }
        });
    }

//    private void updateView(ChangeEvent ce) {
//        //ce.update(LblShow.this);
//    }
//
//
//    public void notifyAjaxEvent(AbstractEvent event) {
//        if (event instanceof ChangeEvent) {
//            ChangeEvent ce = ((ChangeEvent) event);
//            Object change = ce.getChange();
//            String ident = ce.getIdentifier();
//            if (Const.KEYSTARTSEARCH.equals(ident)) {
//                //updateView(ce);
//            }else if(Const.KEYPAGINGKLICK.endsWith(ident)){
//                updateView(ce);
//            }else if(Const.KEY_HALDE_HALDEFILE_KLICK.equals(ident)){
//                //updateView(ce);
//            }
//        }
//    }
    
    @Override
    public void onPagingClickEvent(RecOnPagingClickEvent ce) {
        ce.update(LblShow.this);        
    }

}
