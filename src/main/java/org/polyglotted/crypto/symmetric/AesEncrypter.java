package org.polyglotted.crypto.symmetric;

import javax.crypto.Cipher;

import lombok.SneakyThrows;

import org.polyglotted.crypto.AbstractCrypto;
import org.polyglotted.crypto.utils.HexUtils;

public class AesEncrypter extends AbstractCrypto {

    /**
     * Create a new AesEncrypter
     * 
     * @param passPhrase
     *            the Passphrase for the secret key
     * @param salt
     *            the salt for the secret key
     */
    public AesEncrypter(String passPhrase) {
        super(createCipher(passPhrase));
    }

    @SneakyThrows
    private static Cipher createCipher(String passPhrase) {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, Aes.createSecret(passPhrase));
        return cipher;
    }

    @Override
    @SneakyThrows
    public String crypt(String text) {
        byte[] iv = Aes.generateIv(cipher);
        byte[] cipherText = encrypt(text);
        return HexUtils.encodeString(iv) + "$" + HexUtils.encodeString(cipherText);
    }
}
