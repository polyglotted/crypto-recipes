package org.polyglotted.crypto.api;

import static org.polyglotted.crypto.utils.Charsets.UTF8;

import java.io.PrintWriter;

import javax.crypto.Cipher;

import lombok.SneakyThrows;

public abstract class AbstractCrypto implements Crypto {

    public enum Mode {
        ENCRYPT, DECRYPT;
    }

    protected final Cipher cipher;

    public AbstractCrypto(Cipher cipher) {
        this.cipher = cipher;
    }

    @Override
    public Cipher getCipher() {
        return cipher;
    }

    @Override
    public String getAlgorithm() {
        return cipher.getAlgorithm();
    }

    public boolean handlePropertyFirstLine(String firstLine, PrintWriter writer) {
        if (getMode() == Mode.ENCRYPT) {
            writer.println(getHeaderLine());
            return false;
        }
        else {
            handleFirstLine(firstLine);
            return true;
        }
    }

    protected void handleFirstLine(String firstLine) {
        // marker
    }

    protected String getHeaderLine() {
        return "#" + getAlgorithm();
    }

    /**
     * encrypt the given text
     * 
     * @param text
     *            the String representing plain text
     * @return byte[] representing cipher value
     */
    @SneakyThrows
    protected byte[] encrypt(String text) {
        return cipher.doFinal(UTF8.getBytes(text));
    }

    /**
     * crypt the given text
     * 
     * @param cipherText
     *            the byte[] cipher value
     * @return byte[] representing plain text
     */
    @SneakyThrows
    public final byte[] crypt(byte[] cipherText) {
        return cipher.doFinal(cipherText);
    }

    /**
     * @return if this crypto performs an encrypt or decrypt operation
     */
    public abstract Mode getMode();
}
