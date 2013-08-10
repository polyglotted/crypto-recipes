package org.polyglotted.crypto;

import lombok.SneakyThrows;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class CryptoMain extends AbstractMain {

    @Parameter(names = { "--text", "-t" },
            required = true,
            description = "text to be crypted")
    private String text;

    public static void main(String args[]) throws Exception {
        CryptoMain main = new CryptoMain();
        JCommander cmd = new JCommander(main);
        try {
            cmd.parse(args);
            System.out.println(main.crypt());
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            cmd.usage();
            throw ex;
        }
    }

    @SneakyThrows
    private String crypt() {
        return algo.cryptText(keyFile, passPhrase, text);
    }
}
