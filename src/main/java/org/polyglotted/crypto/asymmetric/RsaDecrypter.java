package org.polyglotted.crypto.asymmetric;

import java.security.PrivateKey;

import javax.crypto.Cipher;

import lombok.SneakyThrows;

import org.polyglotted.crypto.AbstractCrypto;
import org.polyglotted.crypto.utils.Base64;

/**
 * RsaDecrypter uses RSA algorithm of Asymmetric key cryptography to convert unreadable cipher text into plain text.
 * 
 * @author Shankar Vasudevan
 */
public class RsaDecrypter extends AbstractCrypto {

    /**
     * Create a new RsaDecrypter
     * 
     * @param key
     *            the PrivateKey for the decryption
     */
    public RsaDecrypter(PrivateKey key) {
        super(createCipher(key));
    }

    @SneakyThrows
    private static Cipher createCipher(PrivateKey key) {
        final Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher;
    }

    @Override
    public String crypt(String text) {
        return decrypt(Base64.decode(text));
    }
}
