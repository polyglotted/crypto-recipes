package org.polyglotted.crypto;

import static org.polyglotted.crypto.PropertyFileCrypto.decrypt;
import static org.polyglotted.crypto.PropertyFileCrypto.encrypt;

import java.io.*;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.converters.FileConverter;

public class PropertyFileCryptoMain {

    @Parameter(names = { "--mode", "-m" }, description = "Mode of opertion, either encrypt or decrypt", required = true, converter = ModeConverter.class)
    private Mode mode;

    @Parameter(names = { "--keyfile", "-k" }, description = "Public key file for encryption / private key file for decryption", required = true, converter = FileConverter.class)
    private File keyFile;

    @Parameter(names = { "--input", "-i" }, description = "Input file for crypting the text", required = true, converter = FileConverter.class)
    private File inputFile;

    @Parameter(names = { "--output", "-o" }, description = "Output file for writing the transformed content", required = true, converter = FileConverter.class)
    private File outputFile;

    @Parameter(names = { "--prefix", "-p" }, description = "Prefix for attribute lines that have to be crypted", required = true)
    private String prefix;

    public static void main(String args[]) {
        PropertyFileCryptoMain main = new PropertyFileCryptoMain();
        JCommander cmd = new JCommander(main);
        try {
            cmd.parse(args);
            main.crypt();

        } catch (ParameterException ex) {
            System.out.println(ex.getMessage());
            cmd.usage();
            System.exit(-1);
        }
    }

    private void crypt() {
        try {
            mode.crypt(new FileInputStream(keyFile), new FileInputStream(inputFile), new FileOutputStream(outputFile),
                    prefix);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    enum Mode {
        encrypt {
            @Override
            void crypt(InputStream keyIs, InputStream in, OutputStream out, String pref) {
                encrypt(keyIs, in, out, pref);
            }
        },
        decrypt {
            @Override
            void crypt(InputStream keyIs, InputStream in, OutputStream out, String pref) {
                decrypt(keyIs, in, out, pref);
            }
        };
        abstract void crypt(InputStream keyIs, InputStream in, OutputStream out, String prefix);
    }

    private static class ModeConverter implements IStringConverter<Mode> {
        @Override
        public Mode convert(String value) {
            try {
                return Mode.valueOf(value);
            } catch (Exception ex) {
                throw new ParameterException("illegal mode of operation");
            }
        }
    }
}
