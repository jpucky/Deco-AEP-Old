// $Log: HaldeConfig.java,v $
// Revision 1.2  2015/07/21 17:59:18  tw
// Korrektur d. Haldetests.
//
// Revision 1.1  2015/03/19 22:40:22  tw
// CR Feng-ID: 3947#4
//
// Revision 1.1  2015/03/17 22:10:36  tw
// CR Feng-ID: 3947
//
//

package de.decodetron.bo;

/**
 * @author Thomas Winter
 * @since 17.03.2015
 */
public class HaldeConfig implements IHaldeConfig {

    private String dirHaldePDF = "";
    private String dirHaldeSave = "";
    private String dirHaldeTmp = "";
    private String headLineCSV = "";
    private String fileLieferanten = "";

    public HaldeConfig() {
        this("", "", "", "", "");
    }

    public HaldeConfig(String dirHaldePDF, String dirHaldeSave, String dirHaldeTmp, String headLineCSV,
            String fileLieferanten) {
        super();
        this.dirHaldePDF = dirHaldePDF;
        this.dirHaldeSave = dirHaldeSave;
        this.dirHaldeTmp = dirHaldeTmp;
        this.headLineCSV = headLineCSV;
        this.fileLieferanten = fileLieferanten;
    }

    /**
     * @return the dirHaldePDF
     */
    public String getDirHaldePDF() {
        return dirHaldePDF;
    }

    /**
     * @param dirHaldePDF
     *            the dirHaldePDF to set
     */
    public void setDirHaldePDF(String dirHaldePDF) {
        this.dirHaldePDF = dirHaldePDF;
    }

    /**
     * @return the dirHaldeSave
     */
    public String getDirHaldeSave() {
        return dirHaldeSave;
    }

    /**
     * @param dirHaldeSave
     *            the dirHaldeSave to set
     */
    public void setDirHaldeSave(String dirHaldeSave) {
        this.dirHaldeSave = dirHaldeSave;
    }

    /**
     * @return the headLineCSV
     */
    public String getHeadLineCSV() {
        return headLineCSV;
    }

    /**
     * @param headLineCSV
     *            the headLineCSV to set
     */
    public void setHeadLineCSV(String headLineCSV) {
        this.headLineCSV = headLineCSV;
    }

    @Override
    public String getFileLieferanten() {
        return fileLieferanten;
    }

    /**
     * @param fileLieferanten
     *            the fileLieferanten to set
     */
    public void setFileLieferanten(String fileLieferanten) {
        this.fileLieferanten = fileLieferanten;
    }

    /**
     * @return the dirHaldeTmp
     */
    public String getDirHaldeTmp() {
        return dirHaldeTmp;
    }

    /**
     * @param dirHaldeTmp
     *            the dirHaldeTmp to set
     */
    public void setDirHaldeTmp(String dirHaldeTmp) {
        this.dirHaldeTmp = dirHaldeTmp;
    }

}
