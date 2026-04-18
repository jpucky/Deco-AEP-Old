// $Log: CryptoTools.java,v $
// Revision 1.1  2013/11/21 17:40:02  tw
// Neue Schnittstelle mit limitierter Datenausgabe.
//
// Revision 1.3  2013/11/06 22:39:25  tw
// Neue Schnittstellentests f. flexiblere Abfragen.
//
// Revision 1.2  2013/11/05 23:02:09  tw
// db-schnittstellen aufgerumt.
//
// Revision 1.1  2013/11/05 19:31:32  tw
// db-schnittstellen aufgeraeumt.
//
// Revision 1.3  2013/11/05 03:38:33  tw
// .
//
// Revision 1.2  2013/09/16 14:35:39  tw
// Backup, Einbau: Login-Mechanismus
//
// Revision 1.1  2013/08/07 01:46:51  tw
// AEP LoginModul als simpelst-keine-Zusatzbibliotheken-Variante. Erster Wurf.
//
// Revision 1.1  2012/05/22 02:22:59  tw
// Passwortverschlüsselung implementiert.
//
// Revision 1.0  2012/05/18 23:40:26  t
//
//

package de.decodetron.util;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

/**
 * Die Klasse stellt Funktionalitäten zum Ver- und Entschlüsseln von Passwörtern bereit. Darunter
 * befindet sich sowohl eine Einwegverschlüsselung "encryptOneWay(String)" als auch eine Hin- und
 * Rückverschlüsselung.
 * 
 * @author Thomas Winter
 * @since 29.11.2007
 */
public class CryptoTools {

    private static boolean _app = false;
    private static Logger log = Logger.getLogger(CryptoTools.class);

    /**
     * Applikation Startup
     */
    public static void main(String[] args) {
        _app = true;
        new CryptoTools(args);
    }

    /**
     * Konstruktor
     */
    public CryptoTools() {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        if (!_app)
            log = Logger.getLogger(getClass());
    }

    /**
     * Konstruktor für die Verwendung mit main()
     */
    public CryptoTools(String[] args) {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        if (!_app)
            log = Logger.getLogger(getClass());

        if (args.length < 1) {
            usage("Argument erwartet");
            System.exit(1);
        }

        // Die Parameterliste abarbeiten
        //
        boolean decrypt = false;
        String pass = null;

        for (int n = 0; n < args.length; n++) {
            // Alle Parameter müssen mit -- beginnen
            //
            if (!args[n].startsWith("--")) {
                usage("Argument " + args[n] + " in GNU Notation erwartet (--parameter=wert)");
                System.exit(1);
            }

            // Parameter "Passwort"
            //
            if (args[n].startsWith("--s")) {
                int ndx = args[n].indexOf("=");
                if (ndx == -1)
                    usage("Argument " + args[n] + " in GNU Notation erwartet (--parameter=wert)");

                pass = args[n].substring(ndx + 1, args[n].length());
                if (pass.length() == 0) {
                    usage("Passwort ist leer!");
                    System.exit(1);
                }
            }

            // Flag "Entschlüsselung"
            //
            else if (args[n].startsWith("--d")) {
                decrypt = true;
            }
        }

        // Leeres Passwort oder unbekannte Parameter abarbeiten.
        //
        if (pass == null) {
            usage("Unbekannte Parameter angegeben");
            System.exit(1);
        }

        // Je nach Tätigkeit abarbeiten
        //
        String result = null;
        try {
            if (decrypt)
                result = decrypt(pass);
            else
                result = encrypt(pass);
        } catch (Exception x) {
            x.printStackTrace(System.err);
        }

        if (result == null) {
            System.out.println("null");
            System.exit(1);
        } else
            System.out.print(result);

        System.exit(0);
    }

    /**
     * Method zur Vereinfachung der Anwendung. Es wird ein Hex
     * 
     * @param data
     *            Das zu verschlüsselnde Passwort als String.
     * @return Ein String, der einen Hexstring im Format Wert:Wert:Wert enthält.
     */
    public String encrypt(String data) throws InvalidKeyException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException {

        if (data != null)
            data = data.trim();

        byte[] result = encrypt(data.getBytes());
        return (toHexString(result));
    }

    /**
     * Methode zur Vereinfachung der Anwendung. Es wird ein Hex-String im Format Wert:Wert:Wert:
     * usw. erwartet.
     * 
     * @param data
     *            Der Hex-String
     * @return Das Klarschrift Passwort.
     */
    public String decrypt(String data) throws InvalidKeyException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException {

        if (data != null)
            data = data.trim();

        byte[] result = toByteString(data);
        return (new String(decrypt(result)));
    }

    /**
     * Encrypt eines Passworts
     * 
     * @param bytes
     *            Das Klarschrift Passwort
     * @return Das verschlüsselte Passwort.
     */
    public byte[] encrypt(byte[] bytes) throws InvalidKeyException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException {

        // Create the cipher
        Cipher cip = createCipher();
        cip.init(Cipher.ENCRYPT_MODE, getKey());

        byte[] ciphertext = cip.doFinal(bytes);

        if (!_app && log.isDebugEnabled()) {
            log.debug("Original-Pass: [" + new String(bytes) + "]");
            log.debug("Passphrase:    [" + new String(ciphertext) + "]");
        }

        return (ciphertext);
    }

    /**
     * Decrypt eines Passworts.
     * 
     * @param bytes
     *            Das zu entschlüsselnde Passwort
     * @param Das
     *            Klarschrift Passwort
     */
    public byte[] decrypt(byte[] bytes) throws InvalidKeyException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException {

        Cipher cip = createCipher();
        cip.init(Cipher.DECRYPT_MODE, getKey());

        byte[] ciphertext = cip.doFinal(bytes);

        if (!_app && log.isDebugEnabled()) {
            log.debug("Original-Pass: [" + new String(bytes) + "]");
            log.debug("Passphrase:    [" + new String(ciphertext) + "]");
        }

        return (ciphertext);
    }

    /**
     * Leght die Cipher Instanz an.
     */
    private Cipher createCipher() throws NoSuchAlgorithmException, BadPaddingException,
            NoSuchPaddingException {
        return (Cipher.getInstance("DES/ECB/PKCS5Padding"));
    }

    /**
     * Liefert den SALT Wert zurück.
     */
    private SecretKey getKey() {
        byte b[] = { (byte) -101, (byte) 59, (byte) -122, (byte) -9, (byte) -77, (byte) 93,
                (byte) 7, (byte) 69 };

        return (new SecretKeySpec(b, "DES"));
    }

    /**
     * Konvertiert ein byte in einen Hexwert und schreibt das Resultat in den angegeben Puffer.
     */
    private void byte2hex(byte b, StringBuffer buf) {
        char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                'e', 'f' };
        int high = ((b & 0xf0) >> 4);
        int low = (b & 0x0f);
        buf.append(hexChars[high]);
        buf.append(hexChars[low]);
    }

    /**
     * Konvertiert ein byte Array in einen Hex String
     */
    private String toHexString(byte[] block) {
        StringBuffer buf = new StringBuffer();
        int len = block.length;

        for (int i = 0; i < len; i++) {
            byte2hex(block[i], buf);
            if (i < len - 1) {
                buf.append(":");
            }
        }
        return (buf.toString());
    }

    /**
     * Verwandelt einen Hex-String wieder in ein byte Array.
     */
    private byte[] toByteString(String hexString) {
        int len = hexString.length(), pos = 0;
        StringBuffer s;
        byte[] bt = new byte[(len / 3) + 1];

        for (int i = 0; i < len; i += 3) {
            s = new StringBuffer(hexString.substring(i, i + 2));
            bt[pos++] = (byte) Integer.parseInt(s.toString(), 16);
        }

        return (bt);
    }

    /**
     * Gibt die Usage Message aus am Error Stream aus.
     * 
     * @param msg
     *            Die Nachricht, die als Fehlermeldung auszugeben ist.
     */
    private void usage(String msg) {
        System.err.println(msg);
        System.err.println("Usage: scrypt.sh --s=<wert> [--d]");
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////
    // ///

    /**
     * Bildet aus einem übergeben Passwortstring einen Hash nach dem SHA Algorithmus
     * 
     * @param passwort
     *            Das Passwort als String im Klartext
     * @return Einen String mit dem Passworthash
     */
    public String encryptOneWaySHA1(String password) {

        byte[] passBuffer = password.getBytes();
        char[] HEXCODES = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
                'E', 'F' };

        MessageDigest hashMethode = null;
        try {
            hashMethode = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            log.fatal(e.getMessage(), e);
        }

        hashMethode.reset();
        hashMethode.update(passBuffer);

        byte[] encoded = hashMethode.digest();
        String ret = "";
        for (int i = 0; i < encoded.length; i++) {
            byte b = encoded[i];
            ret += HEXCODES[((b >> 4) & 15)];
            ret += HEXCODES[(b & 15)];
        }
        return ret;
    }
    
    public String encryptOneWayMD5(String password) {

        byte[] passBuffer = password.getBytes();
        char[] HEXCODES = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
                'E', 'F' };

        MessageDigest hashMethode = null;
        try {
            hashMethode = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.fatal(e.getMessage(), e);
        }

        hashMethode.reset();
        hashMethode.update(passBuffer);

        byte[] encoded = hashMethode.digest();
        String ret = "";
        for (int i = 0; i < encoded.length; i++) {
            byte b = encoded[i];
            ret += HEXCODES[((b >> 4) & 15)];
            ret += HEXCODES[(b & 15)];
        }
        return ret;
    }
}
