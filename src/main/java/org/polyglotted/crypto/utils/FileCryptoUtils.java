package org.polyglotted.crypto.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import lombok.SneakyThrows;

import org.polyglotted.crypto.api.AbstractCrypto;

/**
 * PropertyFileCrypto is a attrib-repo utility that encrypts and decrypts '.properties' files.This file encrypts only
 * property keys that are prefixed with an ampersand
 * 
 * @author Shankar Vasudevan
 */
public abstract class FileCryptoUtils {

    protected static final String EQUALS = "=";

    /**
     * Copy the contents of the inputFile into outputFile while en(/de)crypting the contents of a line prefixed with a
     * special character
     * 
     * @param crypto
     *            the cryptography algorithm to be used
     * @param propertyIs
     *            the property file input stream
     * @param output
     *            the output stream to write the decrypted values
     * @param prefix
     *            the String prefix for lines which have to be decryted
     */
    @SneakyThrows
    public static void cryptCopy(AbstractCrypto crypto, InputStream propertyIs, OutputStream output, String prefix) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(propertyIs));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(output));
        try {
            int i = 0;
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (i++ == 0) {
                    boolean handled = false;
                    handled = crypto.handlePropertyFirstLine(line, writer);
                    if (!handled)
                        writer.println(handle(line, 1, crypto, prefix));
                }
                else {
                    writer.println(handle(line, i + 1, crypto, prefix));
                }
            }
        }
        finally {
            IoUtils.safeClose(writer);
            IoUtils.safeClose(reader);
        }
    }

    private static String handle(String line, int lineNum, AbstractCrypto crypto, String prefix) {
        if (line.startsWith(prefix)) {
            String[] parts = splitProperty(line, lineNum);
            return parts[0] + EQUALS + crypto.crypt(parts[1]).replace("\n", "");
        }
        return line;
    }

    private static String[] splitProperty(String line, int lineNum) {
        int eqIndex = line.indexOf(EQUALS);
        if(eqIndex < 0) 
            throw new IllegalArgumentException("equals sign missing in line number " + lineNum);
        String[] parts = new String[2];
        parts[0] = line.substring(0, eqIndex);
        parts[1] = line.substring(eqIndex + 1);
        return parts;
    }
}
