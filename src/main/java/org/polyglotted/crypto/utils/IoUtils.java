package org.polyglotted.crypto.utils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class IoUtils {

    public static void copy(InputStream is, OutputStream os) throws IOException {
        try {
            int read;
            byte[] b = new byte[1024];
            while ((read = is.read(b)) != -1) {
                os.write(b, 0, read);
            }
        }
        finally {
            safeClose(is);
            safeClose(os);
        }
    }

    public static byte[] readBytes(InputStream is) throws IOException {
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        try {
            byte[] transfer = new byte[1024];
            int read = -1;
            while ((read = is.read(transfer)) > 0) {
                bytesOut.write(transfer, 0, read);
            }
        }
        finally {
            safeClose(is);
        }
        return bytesOut.toByteArray();
    }

    public static void safeClose(Closeable closeable) {
        try {
            closeable.close();
        }
        catch (IOException ignored) {}
    }
}
