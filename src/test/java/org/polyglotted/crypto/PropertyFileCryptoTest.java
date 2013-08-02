package org.polyglotted.crypto;

import static org.junit.Assert.assertArrayEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.polyglotted.crypto.PropertyFileCrypto;

public class PropertyFileCryptoTest {

    @Test
    public void testEncrypt() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PropertyFileCrypto.encrypt(asStream("keys/public_key.der"), asStream("files/plain-text.properties"), output,
                "&");

        ByteArrayInputStream encryptedIs = new ByteArrayInputStream(output.toByteArray());
        output.reset();
        PropertyFileCrypto.decrypt(asStream("keys/private_key.der"), encryptedIs, output, "&");

        byte[] expecteds = readAll("files/plain-text.properties");
        assertArrayEquals(expecteds, output.toByteArray());
    }

    @Test
    public void testDecrypt() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PropertyFileCrypto.decrypt(asStream("keys/private_key.der"), asStream("files/cipher-text.properties"), output,
                "&");
        byte[] expecteds = readAll("files/plain-text.properties");
        assertArrayEquals(expecteds, output.toByteArray());
    }

    private static byte[] readAll(String path) throws IOException {
        InputStream from = asStream(path);
        ByteArrayOutputStream to = new ByteArrayOutputStream();
        try {
            byte[] buf = new byte[256];
            int read = -1;
            while ((read = from.read(buf)) != -1) {
                to.write(buf, 0, read);
            }
        } finally {
            PropertyFileCrypto.safeClose(from);
        }
        return to.toByteArray();
    }

    private static InputStream asStream(String path) {
        return PropertyFileCryptoTest.class.getClassLoader().getResourceAsStream(path);
    }
}
