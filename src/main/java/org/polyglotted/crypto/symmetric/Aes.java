package org.polyglotted.crypto.symmetric;

import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.polyglotted.crypto.utils.HexUtils;

import lombok.SneakyThrows;

public abstract class Aes {

    private static final String PKCS_SHA1 = "PBKDF2WithHmacSHA1";
    private static final String AES_ENCODING = "AES";

    private static final byte[] SALT = HexUtils.decode("ba29f91d6158c26d");

    public static SecretKey createSecret(String passPhrase) throws InvalidKeySpecException,
            NoSuchAlgorithmException {
        KeySpec spec = new PBEKeySpec(passPhrase.toCharArray(), SALT, 65536, 128);
        SecretKey tmp = SecretKeyFactory.getInstance(PKCS_SHA1).generateSecret(spec);
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), AES_ENCODING);
        return secret;
    }

    @SneakyThrows
    public static byte[] generateIv(Cipher cipher) {
        AlgorithmParameters params = cipher.getParameters();
        return params.getParameterSpec(IvParameterSpec.class).getIV();
    }
}
