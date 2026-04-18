// $Log: DAOUtil.java,v $
// Revision 1.4  2014/03/01 23:58:12  tw
// Verlagerung der Fundstellensuche in DB-Schicht.
// Committed on the Free edition of March Hare Software CVSNT Client.
// Upgrade to CVS Suite for more features and support:
// http://march-hare.com/cvsnt/
//
// Revision 1.3  2013/12/05 14:36:55  tw
// ImplementierungTokenanmeldung.
//
// Revision 1.2  2013/12/05 11:50:36  tw
// Tests f. Tokenanmeldung.
//
// Revision 1.1  2013/11/21 17:40:02  tw
// Neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.2  2013/11/08 11:00:34  tw
// Umlaute an vorerst ersetzt f. Fehlereingrenzung.
//
// Revision 1.1  2013/11/02 21:46:42  tw
// ClubBestand-DB-Schicht. 1. Schritte zur Veralgemeinerung.
//
// Revision 1.1  2013/08/07 01:46:51  tw
// AEP LoginModul als simpelst-keine-Zusatzbibliotheken-Variante. Erster Wurf.
//
//

package de.decodetron.util;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Statische Helferlein.
 * 
 * @author Thomas Winter
 * @since 06.08.2013
 */
public class DAOUtil {
    private DAOUtil() {
        // Utility class, hide constructor.
    }

    // Actions ------------------------------------------------------------------------------------

    /**
     * Returns a PreparedStatement of the given connection, set with the given SQL query and the
     * given parameter values.
     * 
     * @param connection
     *            The Connection to create the PreparedStatement from.
     * @param sql
     *            The SQL query to construct the PreparedStatement with.
     * @param returnGeneratedKeys
     *            Set whether to return generated keys or not.
     * @param values
     *            The parameter values to be set in the created PreparedStatement.
     * @throws SQLException
     *             If something fails during creating the PreparedStatement.
     */
    public static PreparedStatement prepareStatement(Connection connection, String sql, boolean returnGeneratedKeys,
            Object... values) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql,
            returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        setValues(preparedStatement, values);
        return preparedStatement;
    }

    /**
     * Set the given parameter values in the given PreparedStatement.
     * 
     * @param connection
     *            The PreparedStatement to set the given parameter values in.
     * @param values
     *            The parameter values to be set in the created PreparedStatement.
     * @throws SQLException
     *             If something fails during setting the PreparedStatement values.
     */
    public static void setValues(PreparedStatement preparedStatement, Object... values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            preparedStatement.setObject(i + 1, values[i]);
        }
    }

    /**
     * Converts the given java.util.Date to java.sql.Date.
     * 
     * @param date
     *            The java.util.Date to be converted to java.sql.Date.
     * @return The converted java.sql.Date.
     */
    public static Date toSqlDate(java.util.Date date) {
        return (date != null) ? new Date(date.getTime()) : null;
    }

    /**
     * Quietly close the Connection. Any errors will be printed to the stderr.
     * 
     * @param connection
     *            The Connection to be closed quietly.
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Closing Connection failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Quietly close the Statement. Any errors will be printed to the stderr.
     * 
     * @param statement
     *            The Statement to be closed quietly.
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.err.println("Closing Statement failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Quietly close the ResultSet. Any errors will be printed to the stderr.
     * 
     * @param resultSet
     *            The ResultSet to be closed quietly.
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.err.println("Closing ResultSet failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void close(Closeable reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println("Closing reader failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Quietly close the Connection and Statement. Any errors will be printed to the stderr.
     * 
     * @param connection
     *            The Connection to be closed quietly.
     * @param statement
     *            The Statement to be closed quietly.
     */
    public static void close(Connection connection, Statement statement) {
        close(statement);
        close(connection);
    }

    /**
     * Quietly close the Connection, Statement and ResultSet. Any errors will be printed to the
     * stderr.
     * 
     * @param connection
     *            The Connection to be closed quietly.
     * @param statement
     *            The Statement to be closed quietly.
     * @param resultSet
     *            The ResultSet to be closed quietly.
     */
    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        close(resultSet);
        close(statement);
        close(connection);
    }

    /**
     * Wandelt Umlaute in zweistellige UTF-8 konforme Zeichen.
     * 
     * @param String
     *            input
     * @return String output
     */
    public static String replaceUmlaute(String input) {
        StringBuffer sb = new StringBuffer("");

        char[] chAr = input.toCharArray();
        for (int i = 0; i < chAr.length; i++) {
            switch (chAr[i]) {
                case 'ä': {
                    sb.append("ae");
                    break;
                }
                case 'ö': {
                    sb.append("oe");
                    break;
                }
                case 'ü': {
                    sb.append("ue");
                    break;
                }
                case 'Ä': {
                    sb.append("Ae");
                    break;
                }
                case 'Ö': {
                    sb.append("Oe");
                    break;
                }
                case 'Ü': {
                    sb.append("Ue");
                    break;
                }
                case 'ß': {
                    sb.append("ss");
                    break;
                }
                default: {
                    sb.append(chAr[i]);
                    break;
                }
            }
        }
        return sb.toString();
    }

    public static String string2XORHex(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            // sb.append(Integer.toHexString((int) (s.charAt(i) ^ 0xff)));
            sb.append(Integer.toHexString(char2XORInt(s.charAt(i))));
        }
        return sb.toString();
    }

    public static int char2XORInt(char s) {
        return (int) (s ^ 0xff);
    }

    /**
     * Converts an array of bytes into an array of characters representing the hexidecimal values of
     * each byte in order. The returned array will be double the length of the passed array, as it
     * takes two characters to represent any given byte.
     * 
     * @param data
     *            a byte[] to convert to Hex characters
     * @return A char[] containing hexidecimal characters
     */
    public static char[] encodeHex2Char(byte[] data) {

        int l = data.length;
        char[] out = new char[l << 1];
        char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = digits[(0xF0 & data[i]) >>> 4];
            out[j++] = digits[0x0F & data[i]];
        }

        return out;
    }

    public static String convertStringToHex(String str) {
        char[] chars = str.toCharArray();
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }
        return hex.toString();
    }

    public static String convertHexToString(String hex) {

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        // 49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {

            // grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            // convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            // convert the decimal to character
            sb.append((char) decimal);

            temp.append(decimal);
        }
        // System.out.println("Decimal : " + temp.toString());

        return sb.toString();
    }
    
    public static String XORHex2String(String hex) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hex.length() - 1; i += 2) {
            String output = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(output, 16);
            decimal = DAOUtil.char2XORInt((char) decimal);
            char c = (char) decimal;
            sb.append(c);
        }

        return sb.toString();
    }

    /**
     * Konvertiert Hex in Long. Falls der Hexwert keiner Zahl entpricht, R�ckgabewert: null. Auf
     * Long muss hier gesteigerten Wert gelegt werden, da Noweda bereits die 2GB - Grenze sprengt!
     * 
     * @param String
     *            hex
     * @return Long
     */
    public static Long getValueFromHex(String hex) {

        Long tmpInt = null;
        try {
            tmpInt = Long.parseLong(hexReverser(hex), 16);
        } catch (NumberFormatException nf) {

        }
        return tmpInt;
    }

    /**
     * Liest Hexwerte von Hinten nach Vorne. Macht aus: "224B00" => "004B22"
     * 
     * @param String
     *            hex
     * @return String
     */
    public static String hexReverser(String hex) {

        StringBuilder sb = new StringBuilder();
        for (int i = hex.length(); i > 0; i = i - 2) {
            String output = hex.substring((i - 2), i);
            sb.append(output);
        }
        return sb.toString();
    }
}
