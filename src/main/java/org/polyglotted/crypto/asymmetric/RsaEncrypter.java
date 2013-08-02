package org.polyglotted.crypto.asymmetric;

import java.security.PublicKey;

import javax.crypto.Cipher;

import org.polyglotted.crypto.utils.Base64;

/**
 * RsaEncrypter uses RSA algorithm of Asymmetric key cryptography to convert plain text into unreadable cipher text.
 * 
 * @author Shankar Vasudevan
 */
public class RsaEncrypter implements RsaCrypto {

    private final Cipher cipher;

    /**
     * Create a new RsaDecrypter
     * 
     * @param key
     *            the PublicKey for the encryption
     */
    public RsaEncrypter(PublicKey key) {
        this.cipher = createCipher(key);
    }

    private Cipher createCipher(PublicKey key) {
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher;

        }
        catch (Exception ex) {
            throw new RuntimeException("encrypter failed", ex);
        }
    }

    /**
     * encrypt the given text
     * 
     * @param text
     *            the String representing plain text
     * @return base64 encoded cipher text
     */
    @Override
    public String crypt(String text) {
        try {
            byte[] cipherText = cipher.doFinal(text.getBytes());
            return Base64.encodeBytes(cipherText).replace("\n", "");
        }
        catch (Exception ex) {
            throw new RuntimeException("encrypt failed", ex);
        }
    }
}
