package org.polyglotted.crypto;

import static org.polyglotted.crypto.asymmetric.RsaKeyReader.readPrivateKey;
import static org.polyglotted.crypto.asymmetric.RsaKeyReader.readPublicKey;

import java.io.*;

import org.polyglotted.crypto.asymmetric.RsaDecrypter;
import org.polyglotted.crypto.asymmetric.RsaEncrypter;

/**
 * PropertyFileCrypto is a attrib-repo utility that encrypts and decrypts '.properties' files using the RSA public key
 * cryptography. This file encrypts only property keys that are prefixed with an ampersand
 * 
 * @author Shankar Vasudevan
 */
public class PropertyFileCrypto {

    protected static final String EQUALS = "=";

    /**
     * Encrypt the incoming property file and copy the resulting data into the output stream.
     * 
     * @param pubKeyIs
     *            the public key input stream
     * @param propertyIs
     *            the property file input stream
     * @param output
     *            the output stream to write the encrypted values
     * @param prefix
     *            the String prefix for lines which have to be encrypted
     */
    public static void encrypt(InputStream pubKeyIs, InputStream propertyIs, OutputStream output, String prefix) {
        final RsaEncrypter encrypter = new RsaEncrypter(readPublicKey(pubKeyIs));
        copyFrom(propertyIs, output, encrypter, prefix);
    }

    /**
     * Decrypt the incoming property file and copy the resulting data into the output stream.
     * 
     * @param privKeyIs
     *            the private key input stream
     * @param propertyIs
     *            the property file input stream
     * @param output
     *            the output stream to write the decrypted values
     * @param prefix
     *            the String prefix for lines which have to be decryted
     */
    public static void decrypt(InputStream privKeyIs, InputStream propertyIs, OutputStream output, String prefix) {
        final RsaDecrypter decrypter = new RsaDecrypter(readPrivateKey(privKeyIs));
        copyFrom(propertyIs, output, decrypter, prefix);
    }

    private static void copyFrom(InputStream propertyIs, OutputStream output, Crypto crypto, String prefix) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(propertyIs));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(output));
        try {
            String line = null;
            while ((line = reader.readLine()) != null) {
                writer.println(handle(line, crypto, prefix));
            }
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception ex) {
            throw new RuntimeException("readCallback failed", ex);

        } finally {
            safeClose(writer);
            safeClose(reader);
        }
    }

    private static String handle(String line, Crypto crypto, String prefix) {
        if (line.startsWith(prefix)) {
            String[] parts = splitProperty(line);
            return parts[0] + EQUALS + crypto.crypt(parts[1]);
        }
        return line;
    }

    private static String[] splitProperty(String line) {
        int eqIndex = -1;
        if ((eqIndex = line.indexOf(EQUALS)) < 0) {
            throw new IllegalArgumentException("illegal property line");
        }
        String[] parts = new String[2];
        parts[0] = line.substring(0, eqIndex);
        parts[1] = line.substring(eqIndex + 1);
        return parts;
    }

    /* visible for testing */
    static void safeClose(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException ignored) {
        }
    }
}
