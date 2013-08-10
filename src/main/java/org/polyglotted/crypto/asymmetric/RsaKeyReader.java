package org.polyglotted.crypto.asymmetric;

import static org.polyglotted.crypto.api.Crypto.RSA_ALGORITHM;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import lombok.SneakyThrows;
import net.iharder.Base64;

import org.polyglotted.crypto.utils.IoUtils;

/**
 * RsaKeyReader is a utility used to read DER formatted private and public key files. To create the keys, you would use
 * the "openssl" commands. This is adapted from the blog entry at
 * http://codeartisan.blogspot.co.uk/2009/05/public-key-cryptography-in-java.html
 * 
 * @author Shankar Vasudevan
 */
public abstract class RsaKeyReader {

    /**
     * Read the private key from the file
     * 
     * @param is
     *            the InputStream for the key file
     * @return the PrivateKey
     */
    @SneakyThrows
    public static PrivateKey readPrivateKey(InputStream is) {
        byte[] keyBytes = Base64.decode(IoUtils.readBytes(is));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(RSA_ALGORITHM);
        return kf.generatePrivate(spec);
    }

    /**
     * Read the public key from the file
     * 
     * @param is
     *            the InputStream for the key file
     * @return the PublicKey
     */
    @SneakyThrows
    public static PublicKey readPublicKey(InputStream is) {
        byte[] keyBytes = Base64.decode(IoUtils.readBytes(is));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(RSA_ALGORITHM);
        return kf.generatePublic(spec);
    }
}
