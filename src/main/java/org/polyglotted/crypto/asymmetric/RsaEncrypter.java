package org.polyglotted.crypto.asymmetric;

import static org.polyglotted.crypto.asymmetric.RsaKeyReader.readPublicKey;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;

import lombok.SneakyThrows;
import net.iharder.Base64;

import org.polyglotted.crypto.api.AbstractCrypto;
import org.polyglotted.crypto.utils.FileCryptoUtils;
import org.polyglotted.crypto.utils.IoUtils;

/**
 * RsaEncrypter uses RSA algorithm of Asymmetric key cryptography to convert plain text into unreadable cipher text.
 * 
 * @author Shankar Vasudevan
 */
public class RsaEncrypter extends AbstractCrypto {

    private int base64Options = Base64.NO_OPTIONS;

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
    public Mode getMode() {
        return Mode.ENCRYPT;
    }

    @Override
    @SneakyThrows
    public String crypt(String text) {
        return Base64.encodeBytes(encrypt(text), base64Options);
    }

    /**
     * @param options
     *            the Base64Options for encoding
     */
    public void setBase64Options(int options) {
        this.base64Options = options;
    }

    /**
     * Encrypt the given text with a public key
     * 
     * @param pubKeyIs
     *            the public key input stream
     * @param input
     *            the input data
     * @return the encrypted cipher value
     */
    public static String encrypt(InputStream pubKeyIs, String input) {
        final RsaEncrypter encrypter = new RsaEncrypter(readPublicKey(pubKeyIs));
        encrypter.setBase64Options(Base64.DO_BREAK_LINES);
        return encrypter.crypt(input);
    }

    /**
     * Encrypt the incoming property file and copy the resulting data into the output stream.
     * 
     * @param pubKeyIs
     *            the public key input stream
     * @param input
     *            the file input stream
     * @param output
     *            the output stream to write the encrypted values
     */
    @SneakyThrows
    public static void encryptFile(InputStream pubKeyIs, InputStream input, OutputStream output) {
        final RsaEncrypter encrypter = new RsaEncrypter(readPublicKey(pubKeyIs));
        IoUtils.copy(input, new CipherOutputStream(output, encrypter.getCipher()));
    }

    /**
     * Encrypt the incoming property file and copy the resulting data into the output stream.
     * 
     * @param pubKeyIs
     *            the public key input stream
     * @param propertyIs
     *            the property file input stream
     * @param output
     *            the output stream to write the encrypted values
     * @param prefix
     *            the String prefix for lines which have to be encrypted
     */
    public static void encryptPropertyFile(InputStream pubKeyIs, InputStream propertyIs, OutputStream output,
            String prefix) {
        final RsaEncrypter encrypter = new RsaEncrypter(readPublicKey(pubKeyIs));
        FileCryptoUtils.cryptCopy(encrypter, propertyIs, output, prefix);
    }
}
