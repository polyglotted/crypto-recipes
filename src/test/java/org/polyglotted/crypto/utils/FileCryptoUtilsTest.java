package org.polyglotted.crypto.utils;

import static org.polyglotted.crypto.TestUtils.asStream;

import java.io.ByteArrayOutputStream;

import org.junit.Test;
import org.polyglotted.crypto.symmetric.AesEncrypter;

public class FileCryptoUtilsTest extends FileCryptoUtils {

    @Test(expected = IllegalArgumentException.class)
    public void testCryptCopy() {
        final AesEncrypter aesEncrypter = new AesEncrypter("randomPass");
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        FileCryptoUtils.cryptCopy(aesEncrypter, asStream("files/fail-text.properties"), output, "&");
    }
}
