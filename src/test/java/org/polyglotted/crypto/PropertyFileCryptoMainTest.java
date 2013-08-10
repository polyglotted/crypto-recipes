package org.polyglotted.crypto;

import org.junit.Test;

import com.beust.jcommander.ParameterException;

public class PropertyFileCryptoMainTest {

    @Test(expected = ParameterException.class)
    public void testInvalidUsage() throws Exception {
        PropertyFileCryptoMain.main(new String[0]);
    }

    @Test(expected = ParameterException.class)
    public void testInvalidAlgo() throws Exception {
        PropertyFileCryptoMain.main(new String[] { "-a", "MyOwnAlgo" });
    }

    @Test
    public void testRsaEncrypt() throws Exception {
        PropertyFileCryptoMain.main(new String[] { "-a", "RsaEncrypt", "-i",
                "src/test/resources/files/plain-text.properties", "-k", "src/test/resources/keys/public_key.txt", "-o",
                "target/test-classes/files/rsa-encrypted.properties", "-p", "&" });
    }

    @Test
    public void testRsaDecrypt() throws Exception {
        PropertyFileCryptoMain.main(new String[] { "-a", "RsaDecrypt", "-i",
                "src/test/resources/files/cipher-rsa.properties", "-k", "src/test/resources/keys/private_key.txt",
                "-o", "target/test-classes/files/rsa-decrypted.properties", "-p", "&" });
    }

    @Test
    public void testAesEncrypt() throws Exception {
        PropertyFileCryptoMain.main(new String[] { "-a", "AesEncrypt", "-i",
                "src/test/resources/files/plain-text.properties", "-r", "RandomP@ssw0rd", "-o",
                "target/test-classes/files/aes-encrypted.properties", "-p", "&" });
    }

    @Test
    public void testAesDecrypt() throws Exception {
        PropertyFileCryptoMain.main(new String[] { "-a", "AesDecrypt", "-i",
                "src/test/resources/files/cipher-aes.properties", "-r", "RandomP@ssw0rd", "-o",
                "target/test-classes/files/aes-decrypted.properties", "-p", "&" });
    }
}
