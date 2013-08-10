package org.polyglotted.crypto.digest;

import java.security.MessageDigest;

import lombok.SneakyThrows;

public class Md5Digest extends AbstractDigest {

    public Md5Digest() {
        super(md5());
    }

    @SneakyThrows
    private static final MessageDigest md5() {
        return MessageDigest.getInstance("MD5");
    }
}
