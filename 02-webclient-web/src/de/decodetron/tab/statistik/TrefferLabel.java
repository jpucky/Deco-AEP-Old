// $Log: TrefferLabel.java,v $
// Revision 1.5  2014/05/08 20:59:13  tw
// Bugfix: Fehlerhafte Treffermenge wenn Suchergebnis leer.
//
// Revision 1.4  2014/01/22 15:32:49  tw
// Bugfix Nullpointerexception
//
// Revision 1.3  2014/01/17 11:08:48  tw
// Treffermengenanzeige korrigiert.
//
// Revision 1.2  2014/01/17 10:35:47  tw
// Listenausgabe als pdf.
//
// Revision 1.1  2014/01/16 12:34:51  tw
// Anzeige Treffermenge. Pdfgenerierung.
//
//

package de.decodetron.tab.statistik;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.Const;
import de.decodetron.security.LoginSession;

/**
 * Statistik-Infos. Nur die Admins/Nicht-Lieferanten bekommen die Gesamtanzahl Datensätze zu sehen.
 * 
 * @author Thomas Winter
 * @since 16.01.2014
 */
public class TrefferLabel extends Label {

    public TrefferLabel(String id, final IModel<?> mm) {
        super(id, mm);
        setOutputMarkupId(true);
        setDefaultModel(new Model<String>() {
            public String getObject() {
                ValueMap vm = (ValueMap) mm.getObject();
                Long show = (Long) vm.get(Const.KEYSHOWPERPAGE);
                Long hits = (Long) vm.get(Const.KEYHITSPERPAGE);
                Long counttotal = (Long) vm.get(Const.KEYDOCTOTAL);
                
                show = show == null ? 0 : show;
                hits = hits == null ? 0 : hits;
                counttotal = counttotal == null ? 0 : counttotal;

                StringBuffer buf = new StringBuffer();

                if (show == hits) {
                    buf.append(String.valueOf(show));
                } else {
                    buf.append(String.valueOf(show)).append(" / ").append(String.valueOf(hits));
                }

                if (!LoginSession.get().getUser().getIsLieferant()) {
                    // Für die Wortfetischisten. Da soll es ja einige geben ...
                    buf.append(((show == hits) && (show == 1)) ? " Suchergebnis aus " : " Suchergebnisse aus ");
                    buf.append(counttotal);
                    buf.append((counttotal >= 1) ? " Datensätzen" : " Datensatz");
                } else {
                    buf.append(((show == hits) && (show == 1)) ? " Datensatz" : " Datensätzen");
                }
                return buf.toString();
            }
        });
    }
}
