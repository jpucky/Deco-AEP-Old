// $Log: RechercheDAO.java,v $
// Revision 1.16  2016/01/28 13:12:09  tw
// Optische Retusche.
//
// Revision 1.15  2016/01/11 22:50:19  tw
// CR 3956: Interner Umbau: Lucene-Felder.
//
// Revision 1.14  2014/11/23 21:53:43  tw
// https://team.decodetron.de:10443/index.php?c=task&a=view_task&id=3939
//
// Revision 1.13  2014/10/27 14:42:15  tw
// Bugfix in der Recherche Gesamtanzahl / Aufraeumarbeiten.
//
// Revision 1.12  2014/10/02 12:36:33  tw
// Lieferantenname Textfeldanbindung.
//
// Revision 1.11  2014/10/01 11:24:31  tw
// Absplittung, Scanindex vom restlichen Index. Testanbindung.
//
// Revision 1.10  2014/08/01 12:59:35  tw
// Scanbelege, Suchtext->Uppercase.
//
// Revision 1.9  2014/07/31 14:09:43  tw
// Letzter Stand Aufraeumarbeiten eingefroren.
//
// Revision 1.8  2014/07/30 16:08:54  tw
// Scanbelege, Lucene Objektmapper erstellt, Aufraeumarbeiten.
//
// Revision 1.7  2014/07/30 11:56:36  tw
// Scanbelege, Felbezeichner geaendert.
//
// Revision 1.6  2014/07/29 21:19:12  tw
// Schnittstellenanpassung Scanbelege.
//
// Revision 1.5  2014/07/18 22:16:29  tw
// Bugfix: Schnittstelle Benutzerverwaltung, Berechtigung Superadmin / Admin.
//
// Revision 1.4  2014/06/26 14:15:55  tw
// Serverguard-kram fuer Frank.
//
// Revision 1.3  2014/03/04 16:21:13  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Recherche: Paginierung.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.2  2014/03/03 19:34:54  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Recherche: Paginierung.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.1  2014/03/01 23:58:12  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
//

package de.decodetron.dao.recherche;

import static de.decodetron.util.DAOUtil.close;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;

import de.decodetron.bo.FilterItemList;
import de.decodetron.bo.Fundstelle;
import de.decodetron.bo.FundstellePage;
import de.decodetron.bo.Section;
import de.decodetron.bo.User;
import de.decodetron.fac.DAOFactoryLucene;
import de.decodetron.util.Const;

/**
 * Rechercheschnittstelle. Basierend auf Lucene-DB.
 * 
 * @author Thomas Winter
 * @since 01.03.2014
 */
public class RechercheDAO extends Recherche implements RechercheDAOI{

    private DAOFactoryLucene daoFactory;

    public RechercheDAO(DAOFactoryLucene daoFactory) {
        this.daoFactory = daoFactory;
    }

    public int getTotalCount() {
        try {
            return daoFactory.getReader().numDocs();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean dbAlive() {
        boolean alive = false;
        IndexReader reader = null;
        try {
            reader = daoFactory.getReader();
            alive = reader != null ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(reader);
        }
        return alive;
    }

    @Override
    public FundstellePage getFundstellenPage(User user, Integer hitsPerPage, Long offset, FilterItemList listTxtFields,
            List<String> keyblgart, List<Section> sections, List<String> filterList) {

        BooleanQuery booleanQuery = getQueryNew(user, keyblgart, sections, filterList, listTxtFields);

        IndexReader reader = null;
        FundstellePage page = null;

        try {

            reader = daoFactory.getReader();
            IndexSearcher searcher = new IndexSearcher(reader);

            int pageNr = (int) (offset / hitsPerPage) + 1;
            page = new FundstellePage(pageNr, hitsPerPage); // Letzte Seite anzeigen
            TopDocs hits = searcher.search(//
                booleanQuery,//
                page.getEnd(),//
                new Sort(new SortField(Const.LUC_IMPDATE, SortField.Type.STRING, true))//
                    );

            page.setTotalCount(reader.numDocs());
            page.setHitsCount(hits.totalHits);
            ScoreDoc[] scoreDoc = hits.scoreDocs;

            for (int i = page.getStart(); i < page.getEnd() && scoreDoc.length > 0; i++) {

                int index = scoreDoc[i].doc;
                Document document = reader.document(index);
                Fundstelle fs = (Fundstelle) mapObject(Fundstelle.class, document);
                page.addFundstelle(fs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(reader);
        }

        return page;
    }

    @Override
    public long countFundstellenPage(User user, Integer hitsPerPage, Long offset, FilterItemList listTxtFields,
            List<String> keyblgart, List<Section> sections, List<String> filterList) {

        BooleanQuery booleanQuery = getQueryNew(user, keyblgart, sections, filterList, listTxtFields);

        IndexReader reader = null;
        TopDocs hits = null;

        try {

            reader = daoFactory.getReader();
            IndexSearcher searcher = new IndexSearcher(reader);

            int pageNr = (int) (offset / hitsPerPage) + 1;
            FundstellePage page = new FundstellePage(pageNr, hitsPerPage); // Letzte Seite anzeigen
            hits = searcher.search(booleanQuery, page.getEnd());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(reader);
        }

        return hits.totalHits;
    }

}
