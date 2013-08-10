package org.polyglotted.crypto.digest;

import static org.junit.Assert.assertEquals;
import static org.polyglotted.crypto.TestUtils.asStream;

import org.junit.Test;

public class Md5DigestTest {

    @Test
    public void testCreateInputStream() {
        String checksum = new Md5Digest().create(asStream("files/plain-text.properties"));
        assertEquals("c6cbbadd6300ccdad29efd85d8514a93", checksum);
    }

    @Test
    public void testCreateString() {
        String checksum = new Md5Digest().create("test value");
        assertEquals("6d8d9a5cfed47bcf814ecac40b72c045", checksum);
    }
}
