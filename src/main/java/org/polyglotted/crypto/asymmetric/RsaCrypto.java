package org.polyglotted.crypto.asymmetric;

/**
 * Marker interface for all RSA crtpto classes
 * 
 * @author Shankar Vasudevan
 */
public interface RsaCrypto {

    /** The RSA Algorithm */
    String ALGORITHM = "RSA";

    /**
     * Perform encrypt or decrypt action
     * 
     * @param text
     *            the String to crypt
     * @return the result String
     */
    String crypt(String text);
}