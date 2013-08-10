package org.polyglotted.crypto;

import java.io.File;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;

public abstract class AbstractMain {

    @Parameter(names = { "--algo", "-a" },
            description = "Valid algorithm values are [RsaEncrypt / RsaDecrypt / AesEncrypt / AesDecrypt]",
            required = true,
            converter = AlgoConverter.class)
    protected Algo algo;

    @Parameter(names = { "--passphrase", "-r" },
            description = "Password for encryption / decryption in case of AES cryptography")
    protected String passPhrase;

    @Parameter(names = { "--keyfile", "-k" },
            description = "Public key file for encryption / private key file for decryption in case of RSA cryptography",
            converter = FileConverter.class)
    protected File keyFile;

}
