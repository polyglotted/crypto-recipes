package org.polyglotted.crypto.symmetric;

import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;

import lombok.SneakyThrows;

import org.polyglotted.crypto.api.AbstractCrypto;
import org.polyglotted.crypto.utils.HexUtils;
import org.polyglotted.crypto.utils.FileCryptoUtils;

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
    public Mode getMode() {
        return Mode.ENCRYPT;
    }
    
    @Override
    @SneakyThrows
    public String crypt(String text) {
        return HexUtils.encodeString(encrypt(text));
    }

    public String generateIv() {
        return HexUtils.encodeString(Aes.generateIv(cipher));
    }

    @Override
    protected String getHeaderLine() {
        return super.getHeaderLine() + "$" + generateIv();
    }

    /**
     * Utility method to encrypt any password protected data
     * 
     * @param passPhrase
     *            the password to encrypt with
     * @param text
     *            the text to be encrypted
     * @return the encrypted String
     */
    public static String encrypt(String passPhrase, String text) {
        final AesEncrypter aesEncrypter = new AesEncrypter(passPhrase);
        return aesEncrypter.generateIv() + "$" + aesEncrypter.crypt(text);
    }

    /**
     * Encrypt the incoming property file and copy the resulting data into the output stream.
     * 
     * @param passPhrase
     *            the password to encrypt with
     * @param propertyIs
     *            the property file input stream
     * @param output
     *            the output stream to write the encrypted values
     * @param prefix
     *            the String prefix for lines which have to be encrypted
     */
    public static void encryptPropertyFile(String passPhrase, InputStream propertyIs, OutputStream output, String prefix) {
        final AesEncrypter aesEncrypter = new AesEncrypter(passPhrase);
        FileCryptoUtils.cryptCopy(aesEncrypter, propertyIs, output, prefix);
    }
}
