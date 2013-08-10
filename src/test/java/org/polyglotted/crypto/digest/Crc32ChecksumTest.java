package org.polyglotted.crypto.digest;

import static org.junit.Assert.assertEquals;
import static org.polyglotted.crypto.TestUtils.asStream;

import org.junit.Test;

public class Crc32ChecksumTest extends Crc32Checksum {

    @Test
    public void testCreateInputStream() {
        String checksum = Crc32Checksum.create(asStream("files/plain-text.properties"));
        assertEquals("6ef7fd6b", checksum);
    }

    @Test
    public void testCreateString() {
        String checksum = Crc32Checksum.create("test value");
        assertEquals("ecdd412b", checksum);
    }
}
