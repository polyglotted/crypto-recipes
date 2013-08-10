package org.polyglotted.crypto.digest;

import static org.polyglotted.crypto.utils.Charsets.UTF8;

import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

import lombok.SneakyThrows;

import org.polyglotted.crypto.utils.HexUtils;
import org.polyglotted.crypto.utils.IoUtils;

//NotThreadSafe
public abstract class AbstractDigest {

    private static final byte[] SALT = HexUtils.decode("ba29f91d6158c26d");

    private final MessageDigest digest;

    protected AbstractDigest(MessageDigest digest) {
        this.digest = digest;
    }

    @SneakyThrows
    public String create(InputStream input) {
        digest.reset();
        DigestInputStream stream = new DigestInputStream(input, digest);
        byte[] buffer = new byte[8192];
        try {
            while (stream.read(buffer) != -1)
                ;
        }
        finally {
            IoUtils.safeClose(stream);
        }
        digest.update(SALT);
        return HexUtils.encodeString(digest.digest());
    }

    public String create(String value) {
        return create(UTF8.getBytes(value));
    }

    @SneakyThrows
    public String create(byte[] value) {
        digest.reset();
        digest.update(value);
        digest.update(SALT);
        return HexUtils.encodeString(digest.digest());
    }
}
