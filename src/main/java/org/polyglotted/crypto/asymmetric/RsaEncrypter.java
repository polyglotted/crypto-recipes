package org.polyglotted.crypto.asymmetric;

import java.security.PublicKey;

import javax.crypto.Cipher;

import lombok.SneakyThrows;

import org.polyglotted.crypto.AbstractCrypto;
import org.polyglotted.crypto.utils.Base64;

/**
 * RsaEncrypter uses RSA algorithm of Asymmetric key cryptography to convert plain text into unreadable cipher text.
 * 
 * @author Shankar Vasudevan
 */
public class RsaEncrypter extends AbstractCrypto {

    /**
     * Create a new RsaEncrypter
     * 
     * @param key
     *            the PublicKey for the encryption
     */
    public RsaEncrypter(PublicKey key) {
        super(createCipher(key));
    }

    @SneakyThrows
    private static Cipher createCipher(PublicKey key) {
        final Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher;
    }

    @Override
    public String crypt(String text) {
        return Base64.encodeBytes(encrypt(text)).replace("\n", "");
    }
}
