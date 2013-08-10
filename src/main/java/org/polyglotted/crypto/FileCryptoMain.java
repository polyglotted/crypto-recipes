package org.polyglotted.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import lombok.SneakyThrows;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;

public class FileCryptoMain extends AbstractMain {

    @Parameter(names = { "--input", "-i" },
            description = "Input file for crypting the text",
            required = true,
            converter = FileConverter.class)
    protected File inputFile;

    @Parameter(names = { "--output", "-o" },
            description = "Output file for writing the transformed content",
            required = true,
            converter = FileConverter.class)
    protected File outputFile;

    public static void main(String args[]) throws Exception {
        FileCryptoMain main = new FileCryptoMain();
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
        algo.cryptFile(keyFile, passPhrase, new FileInputStream(inputFile), new FileOutputStream(outputFile));
    }
}
