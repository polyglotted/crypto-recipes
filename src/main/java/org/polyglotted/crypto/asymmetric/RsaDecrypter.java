package org.polyglotted.crypto.asymmetric;

import static org.polyglotted.crypto.asymmetric.RsaKeyReader.readPrivateKey;
import static org.polyglotted.crypto.utils.Charsets.UTF8;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.PrivateKey;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;

import lombok.SneakyThrows;
import net.iharder.Base64;

import org.polyglotted.crypto.api.AbstractCrypto;
import org.polyglotted.crypto.utils.FileCryptoUtils;
import org.polyglotted.crypto.utils.IoUtils;

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
    public Mode getMode() {
        return Mode.DECRYPT;
    }

    @Override
    @SneakyThrows
    public String crypt(String text) {
        return UTF8.getString(crypt(Base64.decode(text)));
    }

    /**
     * Decrypt the given text with a private key
     * 
     * @param privKeyIs
     *            the private key input stream
     * @param input
     *            the cipher data to be decryted
     * @return the String containing plain text
     */
    public static String decrypt(InputStream privKeyIs, String cipherText) {
        final RsaDecrypter decrypter = new RsaDecrypter(readPrivateKey(privKeyIs));
        return decrypter.crypt(cipherText);
    }

    /**
     * Decrypt the incoming file and copy the resulting data into the output stream.
     * 
     * @param privKeyIs
     *            the private key input stream
     * @param input
     *            the file input stream
     * @param output
     *            the output stream to write the decrypted values
     */
    @SneakyThrows
    public static void decryptFile(InputStream privKeyIs, InputStream input, OutputStream output) {
        final RsaDecrypter decrypter = new RsaDecrypter(readPrivateKey(privKeyIs));
        IoUtils.copy(new CipherInputStream(input, decrypter.getCipher()), output);
    }

    /**
     * Decrypt the incoming property file and copy the resulting data into the output stream.
     * 
     * @param privKeyIs
     *            the private key input stream
     * @param propertyIs
     *            the property file input stream
     * @param output
     *            the output stream to write the decrypted values
     * @param prefix
     *            the String prefix for lines which have to be decryted
     */
    public static void decryptPropertyFile(InputStream privKeyIs, InputStream propertyIs, OutputStream output,
            String prefix) {
        final RsaDecrypter decrypter = new RsaDecrypter(readPrivateKey(privKeyIs));
        FileCryptoUtils.cryptCopy(decrypter, propertyIs, output, prefix);
    }
}
