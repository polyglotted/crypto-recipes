package org.polyglotted.crypto.asymmetric;

import java.security.PrivateKey;

import javax.crypto.Cipher;

import org.polyglotted.crypto.utils.Base64;

/**
 * RsaDecrypter uses RSA algorithm of Asymmetric key cryptography to convert unreadable cipher text into plain text.
 * 
 * @author Shankar Vasudevan
 */
public class RsaDecrypter implements RsaCrypto {

    private final Cipher cipher;

    /**
     * Create a new RsaDecrypter
     * 
     * @param key
     *            the PrivateKey for the decryption
     */
    public RsaDecrypter(PrivateKey key) {
        this.cipher = createCipher(key);
    }

    private Cipher createCipher(PrivateKey key) {
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher;

        } catch (Exception ex) {
            throw new RuntimeException("decrypter failed", ex);
        }
    }

    /**
     * decrypt the given text
     * 
     * @param text
     *            the base64 encoded cipher text
     * @return String representing plain text
     */
    @Override
    public String crypt(String text) {
        try {
            byte[] cipherValue = Base64.decode(text);
            return new String(cipher.doFinal(cipherValue));

        } catch (Exception ex) {
            throw new RuntimeException("decrypt failed", ex);
        }
    }
}
