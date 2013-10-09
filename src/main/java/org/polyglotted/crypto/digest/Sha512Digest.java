package org.polyglotted.crypto.digest;

import java.security.MessageDigest;

import lombok.SneakyThrows;

public class Sha512Digest extends AbstractDigest {

    public Sha512Digest() {
        super(sha512());
    }

    @SneakyThrows
    private static final MessageDigest sha512() {
        return MessageDigest.getInstance("SHA-512");
    }
}
