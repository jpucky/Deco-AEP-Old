// $Log: ViewShowPDF.java,v $
// Revision 1.4  2015/02/09 12:46:32  tw
// Haldenbearbeitung: Bugfix. Aufraeumarbeiten.
//
// Revision 1.3  2015/02/04 16:23:27  tw
// Haldenbearbeitung: Speichern v. Buchungsdatensaetzen implementiert.
//
// Revision 1.2  2015/02/03 00:52:22  tw
// Haldenbearbeitung: Einlesen v. Archivierungsdatensaetzen ermoeglicht.
//
// Revision 1.1  2015/01/30 21:24:31  tw
// Haldenbearbeitung 1. Wurf.
//
//

package de.decodetron.tab.administration.haldebearbeiten;

import java.io.File;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

import de.decodetron.Const;
import de.decodetron.bo.HaldeFile;
import de.decodetron.data.FileContentResource;
import de.decodetron.event.AbstractEvent;
import de.decodetron.event.ChangeEvent;
import de.decodetron.event.EventListenerInterface;

/**
 * @author Thomas Winter
 * @since 30.01.2015
 */
public class ViewShowPDF extends WebMarkupContainer implements EventListenerInterface {

    public ViewShowPDF(String id, final HaldeBearbeitenModel model) {
        super(id, Model.of(model));
        setOutputMarkupId(true);
        add(AttributeModifier.replace("src", new Model<String>() {
            @Override
            public String getObject() {

                // HaldeFile fsklicked = (HaldeFile) vm.get(Const.KEYFUNDSTELLE_HALDE);
                // ValueMap vm = (ValueMap) getDefaultModelObject();
                //HaldeFile oldValue = model.getClickedFundstelleOld();
                HaldeFile fsklicked = model.getClickedFundstelle();

                String pdfLocation = getPdfFileLocation(fsklicked);

                if ("".equals(pdfLocation)) {
                    return "Das angeforderte PDF wurde nicht gefunden!";
                }

                // && (hasValueChanged(oldValue, fsklicked))
                if ((pdfLocation != null)) {

                    final File file = new File(pdfLocation);
                    PageParameters pdfParameters = new PageParameters();
                    ResourceReference rr = new ResourceReference(file.getName()) {
                        @Override
                        public IResource getResource() {
                            return new FileContentResource(file, getRequestCycle(), false);
                        }
                    };

                    return (String) urlFor(rr, pdfParameters);
                } else {
                    return "about:blank";
                }
            }
        }));
    }

    private boolean hasValueChanged(HaldeFile oldValue, HaldeFile newValue) {

        if (oldValue == null && newValue == null) {
            return false;
        }

        if (oldValue == null && newValue != null) {
            return true;
        }

        if (oldValue != null && newValue == null) {
            return true;
        }

        if (oldValue != null && newValue != null) {
            return !oldValue.equals(newValue);
        }

        return true;
    }

    private String getPdfFileLocation(HaldeFile fs) {

        if (fs == null) {
            return null;
        }

        StringBuilder pathIn = new StringBuilder();
        pathIn.append(fs.getRootPath()).append(System.getProperty("file.separator"));
        pathIn.append(fs.getFileName());
        return pathIn.toString();
    }

    private void updateAnsichtPDFBeleg(ChangeEvent ce) {
        HaldeBearbeitenModel model = (HaldeBearbeitenModel)getDefaultModelObject();
        HaldeFile oldValue = model.getClickedFundstelleOld();
        HaldeFile fsklicked = model.getClickedFundstelle();
        if((hasValueChanged(oldValue, fsklicked))){
            ce.update(ViewShowPDF.this);
        }
    }

    @Override
    public void notifyAjaxEvent(AbstractEvent event) {
        if (event instanceof ChangeEvent) {
            String ident = ((ChangeEvent) event).getIdentifier();
            if (Const.KEY_HALDE_HALDEFILE_KLICK.equals(ident)) {
                ChangeEvent ce = ((ChangeEvent) event);
                updateAnsichtPDFBeleg(ce);
            }
        }
    }

}
