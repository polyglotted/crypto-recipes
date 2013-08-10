package org.polyglotted.crypto.utils;

import java.nio.charset.Charset;

public enum Charsets {

    UTF8("UTF-8"), ASCII("US-ASCII"), UTF16("UTF-16"), UTF32("UTF-32");

    private final Charset charset;

    private Charsets(String value) {
        this.charset = Charset.forName(value);
    }

    public Charset charset() {
        return charset;
    }

    public byte[] getBytes(String value) {
        return value.getBytes(charset());
    }

    public String getString(byte[] bytes) {
        return new String(bytes, charset());
    }
}
