package org.polyglotted.crypto.symmetric;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.polyglotted.crypto.utils.HexUtils;

public class AesCryptoTest {

    @Test
    public void testCrypto() {
        String passPhrase = "Random Seed";
        String testValue = "Polyglotted Crypto";

        AesEncrypter aesEncrypter = new AesEncrypter(passPhrase);
        String encrypted = aesEncrypter.crypt(testValue);

        int dollarIndex = encrypted.indexOf('$');
        byte[] iv = HexUtils.decode(encrypted.substring(0, dollarIndex));
        String decrypted = new AesDecrypter(passPhrase, iv).crypt(encrypted.substring(dollarIndex + 1));
        assertEquals(testValue, decrypted);
    }
}
