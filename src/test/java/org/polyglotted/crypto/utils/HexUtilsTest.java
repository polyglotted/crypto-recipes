package org.polyglotted.crypto.utils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HexUtilsTest extends HexUtils {

    private static final byte[] HW_BYTES = "helloworld".getBytes();

    @Test
    public void testEncode() {
        String hex = encodeString(HW_BYTES);
        assertEquals("68656c6c6f776f726c64", hex);
    }

    @Test
    public void testDecode() {
        byte[] bytes = decode("68656c6c6f776f726c64");
        assertArrayEquals(HW_BYTES, bytes);
    }
}
