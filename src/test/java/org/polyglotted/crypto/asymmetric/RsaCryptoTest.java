package org.polyglotted.crypto.asymmetric;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.polyglotted.crypto.TestUtils.asStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;
import org.polyglotted.crypto.utils.IoUtils;

public class RsaCryptoTest extends RsaKeyReader {

    @Test
    public void testCrypto() {
        String testValue = "Polyglotted Crypto";
        String encrypted = RsaEncrypter.encrypt(asStream("keys/public_key.txt"), testValue);
        String decrypted = RsaDecrypter.decrypt(asStream("keys/private_key.txt"), encrypted);
        assertEquals(testValue, decrypted);
    }

    @Test
    public void testEncryptFile() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        RsaEncrypter.encryptFile(asStream("keys/public_key.txt"), asStream("files/plain-text.properties"), output);

        ByteArrayInputStream encryptedIs = new ByteArrayInputStream(output.toByteArray());
        output.reset();
        RsaDecrypter.decryptFile(asStream("keys/private_key.txt"), encryptedIs, output);

        byte[] expecteds = IoUtils.readBytes(asStream("files/plain-text.properties"));
        assertArrayEquals(expecteds, output.toByteArray());
    }

    @Test
    public void testDecryptFile() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        RsaDecrypter.decryptFile(asStream("keys/private_key.txt"), asStream("files/cipher-rsa.out"), output);

        byte[] expecteds = IoUtils.readBytes(asStream("files/plain-text.properties"));
        assertArrayEquals(expecteds, output.toByteArray());
    }

    @Test
    public void testEncryptPropertyFile() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        RsaEncrypter.encryptPropertyFile(asStream("keys/public_key.txt"), asStream("files/plain-text.properties"),
                output, "&");

        ByteArrayInputStream encryptedIs = new ByteArrayInputStream(output.toByteArray());
        output.reset();
        RsaDecrypter.decryptPropertyFile(asStream("keys/private_key.txt"), encryptedIs, output, "&");

        byte[] expecteds = IoUtils.readBytes(asStream("files/plain-text.properties"));
        assertArrayEquals(expecteds, output.toByteArray());
    }

    @Test
    public void testDecryptPropertyFile() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        RsaDecrypter.decryptPropertyFile(asStream("keys/private_key.txt"), asStream("files/cipher-rsa.properties"),
                output, "&");
        byte[] expecteds = IoUtils.readBytes(asStream("files/plain-text.properties"));
        assertArrayEquals(expecteds, output.toByteArray());
    }
}
