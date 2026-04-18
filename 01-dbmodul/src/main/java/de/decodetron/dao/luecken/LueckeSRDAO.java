// $Log: LueckeSRDAO.java,v $
// Revision 1.5  2016/03/22 11:41:33  tw
// CR 3962: Spezifizierung der Ueberschrift
//
// Revision 1.4  2015/07/20 14:07:14  tw
// Implementierung: Luecken-SR.
//
// Revision 1.3  2015/07/16 22:15:28  tw
// Implementierung: Luecken-SR. Performance-Optimierung.
//
// Revision 1.2  2015/07/14 13:04:46  tw
// Implementierung: Lucken-SR. DB-Anbindung.
//
// Revision 1.1  2015/07/10 15:09:22  tw
// Implementierung: Lucken-SR. DB-Anbindung.
//
//

package de.decodetron.dao.luecken;

import static de.decodetron.util.DAOUtil.close;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import de.decodetron.bo.SammelrechnungL;
import de.decodetron.bo.SollListe;
import de.decodetron.exc.DAOException;
import de.decodetron.fac.DAOFactoryJDBC;
import de.decodetron.util.SystemUtil;
import de.decodetron.util.Util;

/**
 * DB-Anbindung hier zum ersten mal in diesen Teil verlagert, da der Client damit nichts zu tun
 * haben sollte.
 * 
 * @author Thomas Winter
 * @since 10.07.2015
 */
public class LueckeSRDAO implements LueckeSRDAOI {

    private DAOFactoryJDBC daoFactory;
    private static final String SQL_DATACHUNK = "5000";
    private static final String SQL_COUNT = "select count(*) AS ROWCOUNT from sr;";

    private static Logger log = Logger.getLogger(LueckeSRDAO.class);

    // Um ein paar globale Variablen kommen wir nicht herum.
    Long highestLuecke = -1L;
    Long lowestLuecke = Long.MAX_VALUE;

    public LueckeSRDAO() {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileUserPerm = u.getHomeDirectory(userHome, p.getProperty("file.db.sr.lue.in"));
        this.daoFactory = DAOFactoryJDBC.getInstance(dbFileUserPerm);
    }

    @Override
    public File getLueckenProtokollSR() {
        SystemUtil u = new SystemUtil();
        String userHome = System.getProperty("user.dir");
        Properties p = new SystemUtil().loadSystemProperties("db.properties");
        String dbFileUserPerm = u.getHomeDirectory(userHome, p.getProperty("file.db.sr.lue.out"));
        return new File(dbFileUserPerm);
    }

    @Override
    public void generateLueckenProtokollSR() {

        long startTime = System.currentTimeMillis();

        int allSR = countSR();

        int chunk = Integer.valueOf(SQL_DATACHUNK);
        File outFile = getLueckenProtokollSR();
        if (outFile.exists()) {
            outFile.delete();
        }

        try {

            // Reset
            int gapCnt = 0;
            String bisValue = "";
            highestLuecke = -1L;
            lowestLuecke = Long.MAX_VALUE;

            Util.appendToFile(outFile, "Vorhandene Lieferschein-Lücken in der Belegfolge:\n");

            for (int offset = 0; offset < allSR; offset += chunk) {
                LinkedHashMap<String, SammelrechnungL> istList = getAllSRH(offset);
                List<SammelrechnungL> lsr = new ArrayList<SammelrechnungL>(istList.values());

                SollListe sollList = null;

                if (offset == 0) {
                    bisValue = lsr.get(istList.values().size() - 1).getBelegnr();
                    sollList = new SollListe(lsr.get(0).getBelegnr(), bisValue);
                } else {
                    int vonAlt = Integer.valueOf(bisValue);
                    int vonNeu = Integer.valueOf(lsr.get(0).getBelegnr());
                    bisValue = lsr.get(istList.values().size() - 1).getBelegnr();

                    if (vonNeu - vonAlt == 1) {
                        // Dann hatten wir KEINE Lücke zwischen den Chunks!
                        sollList = new SollListe(lsr.get(0).getBelegnr(), bisValue);
                    } else {
                        // Dann hatten wir EINE Lücke zwischen den Chunks!
                        int nextVon = vonAlt + 1;
                        sollList = new SollListe(String.valueOf(nextVon), bisValue);
                    }
                }

                StringBuilder sb = new StringBuilder();
                for (long k = sollList.getVonI(); k <= sollList.getBisI(); k++) {

                    SammelrechnungL srSoll = new SammelrechnungL();
                    srSoll.setBelegnr(String.valueOf(k));

                    if (istList.containsKey(srSoll.getBelegnr())) {

                        // Keine Lücke
                        // Letztes Element ist IMMER eine NICHT-LUECKE. D.h., Output ist
                        // sichergestellt!
                        if (gapCnt > 0) {
                            sb.append("-----------------------------------\n");
                            if (gapCnt == 1) {
                                sb.append(getNrVorTxt(istList, String.valueOf(lowestLuecke - 1)));
                                sb.append(getMissingTxt(String.valueOf(lowestLuecke), String.valueOf(highestLuecke),
                                    String.valueOf(gapCnt)));
                                sb.append(getNrNachTxt(istList, String.valueOf(highestLuecke + 1)));
                            } else {
                                sb.append(getNrVorTxt(istList, String.valueOf(lowestLuecke - 1)));
                                sb.append(getMissingTxt(String.valueOf(lowestLuecke), String.valueOf(highestLuecke),
                                    String.valueOf(gapCnt)));
                                sb.append(getNrNachTxt(istList, String.valueOf(highestLuecke + 1)));
                            }
                        }

                        // Reset
                        gapCnt = 0;
                        lowestLuecke = Long.MAX_VALUE;
                        highestLuecke = -1L;

                    } else {
                        // Lücke
                        gapCnt++;

                        Long nrLuecke = Long.valueOf(srSoll.getBelegnr()).longValue();
                        saveLowestValue(nrLuecke);
                        saveHighestLuecke(nrLuecke);
                    }
                }

                Util.appendToFile(outFile, sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        //System.out.println("Generierungszeit: " + (endTime - startTime) + " [ms]");
        log.info("Luecken-Protokollgenerierung beendet : " + (endTime - startTime) + " [ms]");
    }

    private void saveLowestValue(long val) {
        if (val <= lowestLuecke) {
            lowestLuecke = val;
        }
    }

    private void saveHighestLuecke(long val) {
        if (val >= highestLuecke) {
            highestLuecke = val;
        }
    }

    private String getMissingTxt(String lowGap, String highGap, String gapCnt) {
        StringBuilder sb = new StringBuilder();
        sb.append("fehlt : Nr. ");
        sb.append(lowestLuecke);
        if (!"1".equals(gapCnt)) {
            sb.append(" ~ ").append(highestLuecke).append(" (").append(gapCnt).append(")");
        }
        sb.append("\n");
        return sb.toString();
    }

    private String getNrVorTxt(HashMap<String, SammelrechnungL> istList, String nrVor) {
        StringBuilder sb = new StringBuilder();
        if (istList.containsKey(nrVor)) {
            SammelrechnungL srVor = istList.get(nrVor);
            sb.append("Vorlf.: Nr. ");
            sb.append(srVor.getBelegnr());
            sb.append(" / ");
            sb.append(srVor.getBelegdatum());
            sb.append("\n");
        } else {
            // An den Chunk-Übergängen kann es Vorkommen, dass die Vorgänger in der istList nicht
            // mehr vorhanden sind! Dann mache ich in drei Teufelsnamen an dieser Stelle eine
            // DB-Abfrage...
        	if(nrVor == null){
        		System.out.println("stop");
        	}
            SammelrechnungL srVor = getSRForNr(nrVor);
            sb.append("Vorlf.: Nr. ");
            sb.append(srVor.getBelegnr());
            sb.append(" / ");
            sb.append(srVor.getBelegdatum());
            sb.append("\n");
        }
        return sb.toString();
    }

    private String getNrNachTxt(HashMap<String, SammelrechnungL> istList, String nrNach) {
        StringBuilder sb = new StringBuilder();
        if (istList.containsKey(nrNach)) {
            SammelrechnungL srNach = istList.get(nrNach);
            sb.append("Nachf.: Nr. ");
            sb.append(srNach.getBelegnr());
            sb.append(" / ");
            sb.append(srNach.getBelegdatum());
            sb.append("\n");
        }
        return sb.toString();
    }

    public SammelrechnungL getSRForNr(String srNr) {

        SammelrechnungL sr = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection
                    .prepareStatement("select s.[Id], s.[BelegNr], s.[BelegDatum] from sr s where s.[BelegNr] like '"
                            + srNr + "' ;");
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                sr = (SammelrechnungL) mapObject(SammelrechnungL.class, rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, rs);
        }

        return sr;
    }

    @Override
    public LinkedHashMap<String, SammelrechnungL> getAllSRH(Integer offset) {

        ResultSet rs1 = null;
        Connection con = null;
        PreparedStatement pp1 = null;
        LinkedHashMap<String, SammelrechnungL> srList = new LinkedHashMap<String, SammelrechnungL>();

        try {
            con = daoFactory.getConnection();
            pp1 = con.prepareStatement(getSQLStatement(offset));
            rs1 = pp1.executeQuery();
            while (rs1.next()) {
                SammelrechnungL sr = (SammelrechnungL) mapObject(SammelrechnungL.class, rs1);
                srList.put(sr.getBelegnr(), sr);
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(con, pp1, rs1);
        }

        return srList;
    }

    private String getSQLStatement(Integer offset) {
        StringBuilder sb = new StringBuilder();
        sb.append("select s.[Id], s.[BelegDatum], s.[BelegNr] from sr s");
        sb.append(" order by s.[BelegNr] asc");
        sb.append(" limit ").append(SQL_DATACHUNK).append(" offset ").append(String.valueOf(offset));
        sb.append(";");
        return sb.toString();
    }

    @Override
    public Integer countSR() {

        Integer recordCount = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL_COUNT);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                recordCount = rs.getInt("ROWCOUNT");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, rs);
        }

        return recordCount;
    }

    public String getFirstLastBelegNr(String sortOrder) {

        String recordCount = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement("select s.[BelegNr] from sr s order by s.[BelegNr] "
                    + sortOrder + " limit 1 offset 0;");
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                recordCount = rs.getString("BelegNr");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, rs);
        }

        return recordCount;
    }

    /**
     * Allgemeiner DB-Mapper.
     * 
     * @param Class
     *            <?> clazz, z.B. User.class
     * @param resultSet
     * @return Object
     * @throws SQLException
     */
    public Object mapObject(Class<?> clazz, ResultSet resultSet) throws SQLException {

        Object ustat = null;

        try {
            ustat = clazz.newInstance();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int colnr = rsmd.getColumnCount();
            for (int i = 1; i <= colnr; ++i) {
                String debugColName = rsmd.getColumnName(i).toLowerCase();
                BeanUtils.setProperty(ustat, debugColName, resultSet.getString(debugColName));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ustat;
    }

}
