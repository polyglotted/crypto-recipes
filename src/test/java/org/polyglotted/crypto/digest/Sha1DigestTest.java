package org.polyglotted.crypto.digest;

import static org.junit.Assert.assertEquals;
import static org.polyglotted.crypto.TestUtils.asStream;

import org.junit.Test;

public class Sha1DigestTest {

    @Test
    public void testCreateInputStream() {
        String checksum = new Sha1Digest().create(asStream("files/plain-text.properties"));
        assertEquals("f5331248330179971e299cd98ebb2619010ca440", checksum);
    }

    @Test
    public void testCreateString() {
        String checksum = new Sha1Digest().create("test value");
        assertEquals("ca29587b51dca2169155f72187fa4ebc738526e2", checksum);
    }
}
