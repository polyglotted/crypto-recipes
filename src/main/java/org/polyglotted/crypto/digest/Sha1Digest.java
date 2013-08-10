package org.polyglotted.crypto.digest;

import java.security.MessageDigest;

import lombok.SneakyThrows;

public class Sha1Digest extends AbstractDigest {

    public Sha1Digest() {
        super(sha1());
    }

    @SneakyThrows
    private static final MessageDigest sha1() {
        return MessageDigest.getInstance("SHA1");
    }
}
