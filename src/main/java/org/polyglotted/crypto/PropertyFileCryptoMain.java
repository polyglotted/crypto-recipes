package org.polyglotted.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import lombok.SneakyThrows;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class PropertyFileCryptoMain extends FileCryptoMain {

    @Parameter(names = { "--prefix", "-p" },
            description = "Prefix for attribute lines that have to be crypted",
            required = true)
    private String prefix;

    public static void main(String args[]) throws Exception {
        PropertyFileCryptoMain main = new PropertyFileCryptoMain();
        JCommander cmd = new JCommander(main);
        try {
            cmd.parse(args);
            main.crypt();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            cmd.usage();
            throw ex;
        }
    }

    @SneakyThrows
    private void crypt() {
        algo.cryptPropertyFile(keyFile, passPhrase, new FileInputStream(inputFile), new FileOutputStream(outputFile),
                prefix);
    }
}
