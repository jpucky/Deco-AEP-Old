// $Log: IHaldeConfig.java,v $
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

import java.io.Serializable;

/**
 * Enthält die Schlüssel für die entsprechenden Halde-Verzeichnisse.
 * 
 * @author Thomas Winter
 * @since 17.03.2015
 */
public interface IHaldeConfig extends Serializable {

    public String getDirHaldePDF();

    public void setDirHaldePDF(String dirHaldePDF);

    public String getDirHaldeSave();

    public void setDirHaldeSave(String dirHaldeSave);

    public void setDirHaldeTmp(String dirHaldeTmp);

    public String getDirHaldeTmp();

    public void setHeadLineCSV(String headLineCSV);

    public String getHeadLineCSV();

    public String getFileLieferanten();

}
