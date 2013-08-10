package org.polyglotted.crypto.symmetric;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.polyglotted.crypto.TestUtils.asStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;
import org.polyglotted.crypto.utils.IoUtils;

public class AesCryptoTest extends Aes {

    static final String passPhrase = "RandomP@ssw0rd";

    @Test
    public void testCrypto() {
        String testValue = "Polyglotted Crypto";
        String encrypted = AesEncrypter.encrypt(passPhrase, testValue);
        String decrypted = AesDecrypter.decrypt(passPhrase, encrypted);
        assertEquals(testValue, decrypted);
    }

    @Test
    public void testEncryptPropertyFile() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        AesEncrypter.encryptPropertyFile(passPhrase, asStream("files/plain-text.properties"), output, "&");

        ByteArrayInputStream encryptedIs = new ByteArrayInputStream(output.toByteArray());
        output.reset();
        AesDecrypter.decryptPropertyFile(passPhrase, encryptedIs, output, "&");

        byte[] expecteds = IoUtils.readBytes(asStream("files/plain-text.properties"));
        assertArrayEquals(expecteds, output.toByteArray());
    }

    @Test
    public void testDecryptPropertyFile() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        AesDecrypter.decryptPropertyFile(passPhrase, asStream("files/cipher-aes.properties"), output, "&");

        byte[] expecteds = IoUtils.readBytes(asStream("files/plain-text.properties"));
        assertArrayEquals(expecteds, output.toByteArray());
    }
}
