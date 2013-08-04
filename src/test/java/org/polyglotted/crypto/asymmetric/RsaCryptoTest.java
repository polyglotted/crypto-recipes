package org.polyglotted.crypto.asymmetric;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.Test;

public class RsaCryptoTest {

    @Test
    public void testCrypto() {
        String testValue = "Polyglotted Crypto";

        PublicKey pubKey = RsaKeyReader.readPublicKey(asStream("keys/public_key.der"));
        String encrypted = new RsaEncrypter(pubKey).crypt(testValue);

        PrivateKey privKey = RsaKeyReader.readPrivateKey(asStream("keys/private_key.der"));
        String decrypted = new RsaDecrypter(privKey).crypt(encrypted);
        
        assertEquals(testValue, decrypted);
    }

    private static InputStream asStream(String path) {
        return RsaCryptoTest.class.getClassLoader().getResourceAsStream(path);
    }
}
