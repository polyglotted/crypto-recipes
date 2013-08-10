package org.polyglotted.crypto.utils;

import static org.junit.Assert.assertEquals;
import static org.polyglotted.crypto.TestUtils.asStream;

import java.io.IOException;

import org.junit.Test;

public class IoUtilsTest extends IoUtils {

    @Test
    public void testRead() throws IOException {
        assertEquals(89, readBytes(asStream("files/plain-text.properties")).length);
    }
}
