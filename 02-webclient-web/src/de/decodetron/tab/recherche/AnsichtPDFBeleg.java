// $Log: AnsichtPDFBeleg.java,v $
// Revision 1.10  2017/07/20 15:59:33  tw
// Version 1.21-H: BUGIFX: Fixe Reihenfolge der Belegtypen zeigt Belegtypen an, die nicht der Gruppe zugeordnet sind.
//
// Revision 1.9  2017/07/04 01:07:32  tw
// Mobilmachung der Headrevision. Beseitigung des PDF-Speicherlecks.
//
// Revision 1.8  2017/06/23 17:39:49  tw
// Mobilmachung der Headrevision.
//
// Revision 1.7  2017/06/23 11:57:58  tw
// Mobilmachung der Headrevision.
//
// Revision 1.6  2017/06/21 19:44:16  tw
// Mobilmachung der Headrevision.
//
// Revision 1.5  2016/02/05 15:47:51  tw
// CR 3956: Interner Umbau: Events separiert.
//
// Revision 1.4  2016/02/05 15:24:32  tw
// CR 3956: Interner Umbau: Events separiert.
//
// Revision 1.3  2016/01/31 17:00:14  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.2  2016/01/19 23:15:06  tw
// CR 3956: Interner Umbau: Panelkomponenten erstellt.
//
// Revision 1.1  2016/01/13 12:16:08  tw
// CR 3956: Interner Umbau: Vorbereitung eigener Panelkomponenten.
//
//

package de.decodetron.tab.recherche;

import java.io.File;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.decodetron.AEPApplication;
import de.decodetron.AEPModel;
import de.decodetron.Const;
import de.decodetron.bo.Fundstelle;
import de.decodetron.data.Util;
import de.decodetron.security.LoginSession;
import de.decodetron.tab.recherche.event.IRecOnBelegartSelektiert;
import de.decodetron.tab.recherche.event.IRecOnFundstelleClickEvent;
import de.decodetron.tab.recherche.event.RecOnBelegartSelektiert;
import de.decodetron.tab.recherche.event.RecOnFundstelleClickEvent;

/**
 * @author Thomas Winter
 * @since 12.01.2016
 */
public class AnsichtPDFBeleg extends WebMarkupContainer implements IRecOnFundstelleClickEvent, IRecOnBelegartSelektiert {

    public static String FS = System.getProperty("file.separator");

    public RechercheModel getModelRm() {
        return ((AEPModel) AnsichtPDFBeleg.this.getDefaultModelObject()).getRechercheModel();
    }

    public AnsichtPDFBeleg(String id, IModel<?> model) {
        super(id, model);
        setOutputMarkupId(true);

        add(AttributeModifier.replace("src", new Model<String>() {
            @Override
            public String getObject() {

                final Fundstelle fsklicked = getModelRm().getFundstelle();

                PdfFileLocationData pdfFileLocationData = getPdfFileLocation(fsklicked);
                String pdfError = pdfFileLocationData != null ? pdfFileLocationData.getErrorMsg() : null;
                String pdfLocation = pdfFileLocationData != null ? pdfFileLocationData.getFileLocation() : null;

                if (pdfError != null) {
                    return Const.PDF_HOME_ALIAS + "/" + "error.pdf";
                } else if (pdfLocation != null) {
                    File file = new File(pdfLocation);

                    /**
                     * In dieser Version würde jedes erzeugt PDF in den Resource-Store kommen.
                     * Vorteil wäre, dass nach abgelaufener Session kein PDF mehr aufrufbar ist.
                     * Nachteil: Erst wenn die Resource wieder entfernt wurde, ist das PDF auch aus
                     * dem Speicher entfernt. Diese Variante kam mir subjektiv nicht so performant
                     * vor, daher die einfache Lösung: Ein freigegebenes Verzeichnis in
                     * AEPApplication. In diesem werden die PDFs erzeugt und können angezeigt
                     * werden. Die PDFs werden zyklisch aus diesem Vezeichnis entfernt.
                     * 
                     * <pre>
                     * AEPApplication
                     *         .get()
                     *         .getSharedResources()
                     *         .add(pdfFileLocationData.getTimeStamp(),
                     *             new FileContentResource(new File(pdfFileLocationData.getFileLocation())));
                     * AEPApplication.get().mountResource(pdfFileLocationData.getTimeStamp(),
                     *     new SharedResourceReference(Application.class, pdfFileLocationData.getTimeStamp()));
                     * 
                     * final ResourceReference rr = AEPApplication.get().getSharedResources()
                     *         .get(Application.class, pdfFileLocationData.getTimeStamp(), null, null, null, true);
                     * // return urlFor(rr, null).toString();
                     * </pre>
                     * 
                     */

                    /**
                     * Hier nochmal der Hinweis: Die unten aufgeführte Implementierung führt zu
                     * einem Speicherleck! Jedes angeklickte PDF ist im Speicher verblieben!
                     * 
                     * <pre>
                     * final File file = new File(pdfLocation);
                     * PageParameters pdfParameters = new PageParameters();
                     * ResourceReference rr = new ResourceReference(file.getName()) {
                     *     &#064;Override
                     *     public IResource getResource() {
                     *         return new FileContentResource(file, getRequestCycle());
                     *     }
                     * };
                     * if (rr.canBeRegistered()) {
                     *     getApplication().getResourceReferenceRegistry().registerResourceReference(rr);
                     * }
                     * return urlFor(rr, null).toString();
                     * </pre>
                     */

                    return Const.PDF_HOME_ALIAS + "/" + file.getName();
                } else {
                    return "about:blank";
                }
            }

        }));

    }

    private PdfFileLocationData getPdfFileLocation(Fundstelle fs) {

        if (fs == null) {
            return null;
        }

        PdfFileLocationData pdfFileLocationData = new PdfFileLocationData();
        StringBuilder pathIn = new StringBuilder();
        pathIn.append(AEPApplication.get().getModel().getPathArchivT()).append(FS);
        pathIn.append("archiv_");
        pathIn.append(Util.getDate4PDFLocation(fs));
        // pathIn.append(fs.getDatum());
        // pathIn.append(Util.getCorrectDate(fs));
        pathIn.append(".t");

        /**
         * Der Filename soll als eindeutiger Schlüssel dienen, um das PDF als Resource abzulegen. Er
         * hat das Muster: <login>-<timestamp>
         */
        StringBuilder fileOutNoSuffix = new StringBuilder(Const.AEP_TMP_PDF_DIRHOME);
        // fileOutNoSuffix.append(fs.getKundenNr());
        fileOutNoSuffix.append(LoginSession.get().getUser().getLogin());
        fileOutNoSuffix.append("-");

        String tmpTimestamp = Util.getTimeStamp2(System.currentTimeMillis());
        pdfFileLocationData.setTimeStamp(tmpTimestamp);
        fileOutNoSuffix.append(tmpTimestamp);
        File fout = new File(fileOutNoSuffix.toString());

        Util u = new Util();
        String pagePos = Util.getCorrectOffsetPosition(fs.getOffset());
        String outFile = null;

        try {
            outFile = u.extractDocument(pathIn.toString(), fout, Long.valueOf(pagePos));
            pdfFileLocationData.setFileLocation(outFile);
            // return outFile;
        } catch (Exception e) {
            e.printStackTrace();
            pdfFileLocationData.setErrorMsg(Const.ERROR);
        }
        return pdfFileLocationData;
    }

    private class PdfFileLocationData {

        private String fileLocation;
        private String timeStamp;
        private String errorMsg;

        public String getFileLocation() {
            return fileLocation;
        }

        public void setFileLocation(String fileLocation) {
            this.fileLocation = fileLocation;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }
    }

    @Override
    public void onBelegartSelektiert(RecOnBelegartSelektiert b) {
        b.update(AnsichtPDFBeleg.this);
    }

    @Override
    public void onFundstelleClick(RecOnFundstelleClickEvent ev) {
        ev.update(AnsichtPDFBeleg.this);
    }
}
