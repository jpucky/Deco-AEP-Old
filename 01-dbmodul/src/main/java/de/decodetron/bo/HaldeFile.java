// $Log: HaldeFile.java,v $
// Revision 1.5  2015/04/13 14:50:04  tw
// CR Feng-ID: 3950#3
//
// Revision 1.4  2015/02/04 16:23:09  tw
// Haldenbearbeitung: Speichern v. Buchungsdatensaetzen implementiert.
//
// Revision 1.3  2015/02/03 00:51:19  tw
// Haldenbearbeitung: Einlesen v. Archivierungsdatensaetzen ermoeglicht.
//
// Revision 1.2  2015/02/01 11:28:48  tw
// Haldenbearbeitung Bugfix Nummerierung.
//
// Revision 1.1  2015/01/30 02:43:36  tw
// Haldenbearbeitung 1. Wurf.
//
//

package de.decodetron.bo;

/**
 * @author Thomas Winter
 * @since 28.01.2015
 */
public class HaldeFile extends BusinessObject {

    private Long id;
    private String rootPath;
    private String fileName;
    private String fileChangeDate;
    private String fileSize;
    private Boolean buchungssatzTmpExist = Boolean.FALSE;
    private Boolean buchungssatzSaveExist= Boolean.FALSE;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName
     *            the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the fileChangeDate
     */
    public String getFileChangeDate() {
        return fileChangeDate;
    }

    /**
     * @param fileChangeDate
     *            the fileChangeDate to set
     */
    public void setFileChangeDate(String fileChangeDate) {
        this.fileChangeDate = fileChangeDate;
    }

    /**
     * @return the fileSize
     */
    public String getFileSize() {
        return fileSize;
    }

    /**
     * @param fileSize
     *            the fileSize to set
     */
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * @return the rootPath
     */
    public String getRootPath() {
        return rootPath;
    }

    /**
     * @param rootPath
     *            the rootPath to set
     */
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    /**
     * @return the buchungssatzExist
     */
    public Boolean getBuchungssatzTmpExist() {
        return buchungssatzTmpExist;
    }

    /**
     * @param buchungssatzExist
     *            the buchungssatzExist to set
     */
    public void setBuchungssatzTmpExist(Boolean buchungssatzExist) {
        this.buchungssatzTmpExist = buchungssatzExist;
    }

    /**
     * @return the buchungssatzSaveExist
     */
    public Boolean getBuchungssatzSaveExist() {
        return buchungssatzSaveExist;
    }

    /**
     * @param buchungssatzSaveExist
     *            the buchungssatzSaveExist to set
     */
    public void setBuchungssatzSaveExist(Boolean buchungssatzSaveExist) {
        this.buchungssatzSaveExist = buchungssatzSaveExist;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((buchungssatzTmpExist == null) ? 0 : buchungssatzTmpExist.hashCode());
        result = prime * result + ((buchungssatzSaveExist == null) ? 0 : buchungssatzSaveExist.hashCode());
        result = prime * result + ((fileChangeDate == null) ? 0 : fileChangeDate.hashCode());
        result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
        result = prime * result + ((fileSize == null) ? 0 : fileSize.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((rootPath == null) ? 0 : rootPath.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        HaldeFile other = (HaldeFile) obj;
        if (buchungssatzTmpExist == null) {
            if (other.buchungssatzTmpExist != null)
                return false;
        } else if (!buchungssatzTmpExist.equals(other.buchungssatzTmpExist))
            return false;
        if (buchungssatzSaveExist == null) {
            if (other.buchungssatzSaveExist != null)
                return false;
        } else if (!buchungssatzSaveExist.equals(other.buchungssatzSaveExist))
            return false;
        if (fileChangeDate == null) {
            if (other.fileChangeDate != null)
                return false;
        } else if (!fileChangeDate.equals(other.fileChangeDate))
            return false;
        if (fileName == null) {
            if (other.fileName != null)
                return false;
        } else if (!fileName.equals(other.fileName))
            return false;
        if (fileSize == null) {
            if (other.fileSize != null)
                return false;
        } else if (!fileSize.equals(other.fileSize))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (rootPath == null) {
            if (other.rootPath != null)
                return false;
        } else if (!rootPath.equals(other.rootPath))
            return false;
        return true;
    }

    /**
     * Auf dieser toString() - Methode basiert eine eigene Model-Equals Erkennung im Client. NICHT
     * ÄNDERN !!! Siehe: #DetachableHaldeBearbeitenModel
     */
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "HaldeFile [id=" + id + ", rootPath=" + rootPath + ", fileName=" + fileName + ", fileChangeDate="
                + fileChangeDate + ", fileSize=" + fileSize + ", buchungssatzTmpExist=" + buchungssatzTmpExist
                + ", buchungssatzSaveExist=" + buchungssatzSaveExist + "]";
    }

}
