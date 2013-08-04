package org.polyglotted.crypto.symmetric;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

import lombok.SneakyThrows;

import org.polyglotted.crypto.AbstractCrypto;
import org.polyglotted.crypto.utils.HexUtils;

public class AesDecrypter extends AbstractCrypto {

    /**
     * Create a new AesDecrypter
     * 
     * @param passPhrase
     *            the Passphrase for the secret key
     * @param salt
     *            the salt for the secret key
     */
    public AesDecrypter(String passPhrase, byte[] iv) {
        super(createCipher(passPhrase, iv));
    }

    @SneakyThrows
    private static Cipher createCipher(String passPhrase, byte[] iv) {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, Aes.createSecret(passPhrase), new IvParameterSpec(iv));
        return cipher;
    }

    @Override
    public String crypt(String cipherText) {
        return decrypt(HexUtils.decode(cipherText));
    }
}
