package org.polyglotted.crypto;

import java.io.InputStream;

public class TestUtils {

    public static InputStream asStream(String path) {
        return TestUtils.class.getClassLoader().getResourceAsStream(path);
    }
}
