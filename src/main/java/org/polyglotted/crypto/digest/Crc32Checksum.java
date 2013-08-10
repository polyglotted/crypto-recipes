package org.polyglotted.crypto.digest;

import static org.polyglotted.crypto.utils.Charsets.UTF8;

import java.io.InputStream;
import java.util.zip.CRC32;

import lombok.SneakyThrows;

public abstract class Crc32Checksum {

    @SneakyThrows
    public static String create(InputStream input) {
        CRC32 crc32 = new CRC32();
        try {
            byte[] buffer = new byte[8192];
            int read;
            do {
                read = input.read(buffer);
                if (read > 0)
                    crc32.update(buffer, 0, read);
            }
            while (read != -1);
        }
        finally {
            input.close();
        }
        return Long.toHexString(crc32.getValue());
    }

    public static String create(String value) {
        return create(UTF8.getBytes(value));
    }

    @SneakyThrows
    public static String create(byte[] value) {
        CRC32 crc32 = new CRC32();
        crc32.update(value);
        return Long.toHexString(crc32.getValue());
    }
}
