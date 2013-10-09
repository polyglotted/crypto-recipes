package org.polyglotted.crypto;

import org.junit.Test;

import com.beust.jcommander.ParameterException;

public class CryptoMainTest {

    @Test(expected = ParameterException.class)
    public void testInvalidUsage() throws Exception {
        CryptoMain.main(new String[0]);
    }

    @Test(expected = ParameterException.class)
    public void testInvalidAlgo() throws Exception {
        CryptoMain.main(new String[] { "-a", "MyOwnAlgo" });
    }

    @Test
    public void testRsaEncrypt() throws Exception {
        CryptoMain.main(new String[] { "-a", "RsaEncrypt", "-k", "src/test/resources/keys/public_key.txt", "-t",
                "value" });
    }

    @Test
    public void testRsaDecrypt() throws Exception {
        String cipherText = "omzSEPbh+gck5VrXkeZG8Pw62Nt8XZxLM8NC9tvYSVztfntde/AUtCgFjLmo8e3uMcP7ot41tmIM"
                + "NVUuXBkzaneWcezfE9gfiJWzqkuE6Muyng2BU6N7UohkW61l+XFzFnHOQ0QCug2JDOwcXo60CXPS"
                + "WptbSPKkOv6t/nP2Tn+/kbxrhKWYdxHMqhzBilcHQic8Bo4RaypNiKg93BYVhhPX0JjJBXfoBoJh"
                + "c0c7l6WDWzTW27WcBUiNEZNhfWPI5k9HOp70QkOrc28eLlofdwBDPz3s+L5sqbjmiEqEZcIiW9f3"
                + "BV1hRdBRbrSJJuxjEZok0b+ZjTml/C9hV4OEiw==";
        CryptoMain.main(new String[] { "-a", "RsaDecrypt", "-k", "src/test/resources/keys/private_key.txt", "-t",
                cipherText });
    }

    @Test
    public void testAesEncrypt() throws Exception {
        CryptoMain.main(new String[] { "-a", "AesEncrypt", "-r", "RandomP@ssw0rd", "-t", "value" });
    }

    @Test
    public void testAesDecrypt() throws Exception {
        CryptoMain.main(new String[] { "-a", "AesDecrypt", "-r", "RandomP@ssw0rd", "-t",
                "83b72e454d5197294b394dad2a2104df$90a3ff8a7fdac488e721636e481df000" });
    }
}
