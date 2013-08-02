package org.polyglotted.crypto.asymmetric;

import static org.polyglotted.crypto.asymmetric.RsaCrypto.ALGORITHM;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RsaKeyReader is a utility used to read DER formatted private and public key files. To create the keys, you would use
 * the "openssl" commands. This is adapted from the blog entry at
 * http://codeartisan.blogspot.co.uk/2009/05/public-key-cryptography-in-java.html
 * 
 * <p>
 * To generate a 2048-bit RSA private key<br>
 * $ openssl genrsa -out private_key.pem 2048
 * </p>
 * 
 * <p>
 * To convert private Key to PKCS#8 format<br>
 * $ openssl pkcs8 -topk8 -inform PEM -outform DER -in private_key.pem -out private_key.der -nocrypt
 * </p>
 * 
 * <p>
 * To output public key portion in DER format<br>
 * $ openssl rsa -in private_key.pem -pubout -outform DER -out public_key.der
 * </p>
 * 
 * @author Shankar Vasudevan
 */
public class RsaKeyReader {

    /**
     * Read the private key from the file
     * 
     * @param is
     *            the InputStream for the key file
     * @return the PrivateKey
     */
    public static PrivateKey readPrivateKey(InputStream is) {
        try {
            byte[] keyBytes = readBytes(is);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
            return kf.generatePrivate(spec);
        }
        catch (Exception ex) {
            throw new RuntimeException("readPrivateKey failed", ex);
        }
    }

    /**
     * Read the public key from the file
     * 
     * @param is
     *            the InputStream for the key file
     * @return the PublicKey
     */
    public static PublicKey readPublicKey(InputStream is) {
        try {
            byte[] keyBytes = readBytes(is);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
            return kf.generatePublic(spec);
        }
        catch (Exception ex) {
            throw new RuntimeException("readPublicKey failed", ex);
        }
    }

    private static byte[] readBytes(InputStream is) throws IOException {
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        try {
            byte[] transfer = new byte[128];
            int read = -1;
            while ((read = is.read(transfer)) > 0) {
                bytesOut.write(transfer, 0, read);
            }
        }
        finally {
            try {
                is.close();
            }
            catch (IOException ignored) {}
        }
        return bytesOut.toByteArray();
    }
}
