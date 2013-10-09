package org.polyglotted.crypto.digest;

import static org.junit.Assert.*;
import static org.polyglotted.crypto.TestUtils.asStream;

import org.junit.Test;

public class Sha512DigestTest {

    @Test
    public void testCreateInputStream() {
        String checksum = new Sha512Digest().create(asStream("files/plain-text.properties"));
        assertEquals("c2d448ed1ee17c7ff0e0d0a90f8bc9e1867566afdbee1ac499a5d46debb957aa42e3c1d6bc1cc96f131033f82a59917c59ec2761ed5f0d5396237df863f833e2",
                        checksum);
    }

    @Test
    public void testCreateString() {
        String checksum = new Sha512Digest().create("test value");
        assertEquals("97cf21a4cd96245900c10f38d4cfa86e6abfdcb9ea7f707536fbcd5a18a6ab4970da83ebc9696ee94711afadaaa3c6ad997e4f8eafe75d28228b12c0c932a09b",
                        checksum);
    }
}
