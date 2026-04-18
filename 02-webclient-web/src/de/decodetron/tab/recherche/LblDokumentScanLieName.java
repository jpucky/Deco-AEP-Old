// $Log: LblDokumentScanLieName.java,v $
// Revision 1.1  2015/12/16 20:44:58  tw
// CR 3953: Archivierung von Retourenbelegen.
//
//

package de.decodetron.tab.recherche;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.value.ValueMap;

import de.decodetron.data.Util;

/**
 * @author Thomas Winter
 * @since 16.12.2015
 */
public class LblDokumentScanLieName extends Label {

    private IModel<?> mm;

    public LblDokumentScanLieName(String id, String lblTxt, IModel<?> model) {
        super(id, Model.of(lblTxt));
        setOutputMarkupPlaceholderTag(true);
        mm = model;
    }

    /**
     * Sichtbar, wenn Scanbeleg UND keine Scanretoure!
     */
    @Override
    public boolean isVisible() {
        ValueMap vm = (ValueMap) mm.getObject();
        boolean debug = Util.isScanBeleg(vm) && !Util.isScanRetourenBeleg(vm);
        return debug;
    }
}
