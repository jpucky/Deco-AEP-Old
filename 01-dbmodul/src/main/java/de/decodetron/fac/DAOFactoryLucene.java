// $Log: DAOFactoryLucene.java,v $
// Revision 1.3  2014/10/22 12:32:16  tw
// Case-Sensivitaet lucene.
//
// Revision 1.2  2014/10/01 11:24:31  tw
// Absplittung, Scanindex vom restlichen Index. Testanbindung.
//
// Revision 1.1  2014/03/01 23:58:12  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
//

package de.decodetron.fac;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import de.decodetron.dao.recherche.RechercheDAO;
import de.decodetron.dao.recherche.RechercheDAOI;
import de.decodetron.dao.recherche.RechercheScanDAO;
import de.decodetron.dao.recherche.RechercheScanDAOI;
import de.decodetron.exc.DAOConfigurationException;

/**
 * @author Thomas Winter
 * @since 01.03.2014
 */
public abstract class DAOFactoryLucene {

    public static DAOFactoryLucene getInstance(final String schemaFileName) throws DAOConfigurationException {

        DAOFactoryLucene instance = new DAOFactoryLucene() {

            @Override
            public IndexReader getReader() throws IOException {
                String check = "" + schemaFileName;
                return DirectoryReader.open(FSDirectory.open(new File(check)));
            }
        };

        return instance;
    }

    // public abstract String getHomePath() throws IOException;

    public abstract IndexReader getReader() throws IOException;

    public RechercheDAOI getRechercheDAO() {
        return new RechercheDAO(this);
    }

    public RechercheScanDAOI getRechercheScanDAO() {
        return new RechercheScanDAO(this);
    }
}
