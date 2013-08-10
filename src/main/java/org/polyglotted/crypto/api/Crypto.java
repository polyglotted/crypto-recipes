package org.polyglotted.crypto.api;

import javax.crypto.Cipher;


/**
 * Marker interface for all RSA crtpto classes
 * 
 * @author Shankar Vasudevan
 */
public interface Crypto {

    String RSA_ALGORITHM = "RSA";
    String AES_ALGORITHM = "AES/CBC/PKCS5Padding";

    /**
     * Perform encrypt or decrypt action
     * 
     * @param bytes
     *            the byte[] to crypt
     * @return the result byte[]
     */
    byte[] crypt(byte[] bytes);

    /**
     * Perform encrypt or decrypt action
     * 
     * @param text
     *            the String to crypt
     * @return the result String
     */
    String crypt(String text);

    /**
     * @return the cipher that is used by this crypto
     */
    Cipher getCipher();

    /**
     * @return the algorithm that is used by this crypto
     */
    String getAlgorithm();
}