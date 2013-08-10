package org.polyglotted.crypto.symmetric;

import static org.polyglotted.crypto.utils.Charsets.UTF8;

import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

import lombok.SneakyThrows;

import org.polyglotted.crypto.api.AbstractCrypto;
import org.polyglotted.crypto.utils.HexUtils;
import org.polyglotted.crypto.utils.FileCryptoUtils;

public class AesDecrypter extends AbstractCrypto {

    private final String passPhrase;

    /**
     * Create a new AesDecrypter. This method does not initialize the cipher and cannot be used for encrypting without
     * initializing it first
     * 
     * @param passPhrase
     *            the Passphrase for the secret key
     */
    public AesDecrypter(String passPhrase) {
        super(createCipher());
        this.passPhrase = passPhrase;
    }

    /**
     * Create a new AesDecrypter
     * 
     * @param passPhrase
     *            the Passphrase for the secret key
     * @param salt
     *            the salt for the secret key
     */
    public AesDecrypter(String passPhrase, byte[] iv) {
        this(passPhrase);
        initialiseCipher(iv);
    }

    @SneakyThrows
    private static Cipher createCipher() {
        return Cipher.getInstance(AES_ALGORITHM);
    }

    @SneakyThrows
    private void initialiseCipher(byte[] iv) {
        cipher.init(Cipher.DECRYPT_MODE, Aes.createSecret(passPhrase), new IvParameterSpec(iv));
    }

    @Override
    public Mode getMode() {
        return Mode.DECRYPT;
    }

    @Override
    public String crypt(String cipherText) {
        return UTF8.getString(crypt(HexUtils.decode(cipherText)));
    }

    @Override
    protected void handleFirstLine(String firstLine) {
        int dollarIndex = firstLine.indexOf('$');
        byte[] iv = HexUtils.decode(firstLine.substring(dollarIndex + 1));
        initialiseCipher(iv);
    }

    /**
     * Decrypt the given text with a private key
     * 
     * @param passPhrase
     *            the password to decrypt with
     * @param input
     *            the cipher data to be decryted
     * @return the String containing plain text
     */
    public static String decrypt(String passPhrase, String cipherText) {
        int dollarIndex = cipherText.indexOf('$');
        byte[] iv = HexUtils.decode(cipherText.substring(0, dollarIndex));
        final AesDecrypter aesDecrypter = new AesDecrypter(passPhrase, iv);
        return aesDecrypter.crypt(cipherText.substring(dollarIndex + 1));
    }

    /**
     * Decrypt the incoming property file and copy the resulting data into the output stream.
     * 
     * @param passPhrase
     *            the password to decrypt with
     * @param propertyIs
     *            the property file input stream
     * @param output
     *            the output stream to write the decrypted values
     * @param prefix
     *            the String prefix for lines which have to be decryted
     */
    public static void decryptPropertyFile(String passPhrase, InputStream propertyIs, OutputStream output, String prefix) {
        final AesDecrypter decrypter = new AesDecrypter(passPhrase);
        FileCryptoUtils.cryptCopy(decrypter, propertyIs, output, prefix);
    }
}
