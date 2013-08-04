package org.polyglotted.crypto;

import javax.crypto.Cipher;

import lombok.SneakyThrows;

public abstract class AbstractCrypto implements Crypto {

    protected final Cipher cipher;

    public AbstractCrypto(Cipher cipher) {
        this.cipher = cipher;
    }

    @Override
    public Cipher getCipher() {
        return cipher;
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
        return cipher.doFinal(text.getBytes());
    }

    /**
     * decrypt the given text
     * 
     * @param cipherText
     *            the byte[] cipher value
     * @return String representing plain text
     */
    @SneakyThrows
    protected String decrypt(byte[] cipherText) {
        return new String(cipher.doFinal(cipherText));
    }
}
