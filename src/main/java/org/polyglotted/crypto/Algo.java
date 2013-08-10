package org.polyglotted.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.polyglotted.crypto.asymmetric.RsaDecrypter;
import org.polyglotted.crypto.asymmetric.RsaEncrypter;
import org.polyglotted.crypto.symmetric.AesDecrypter;
import org.polyglotted.crypto.symmetric.AesEncrypter;

enum Algo {
    RsaEncrypt {
        @Override
        String cryptText(File keyFile, String passPhrase, String text) throws IOException {
            return RsaEncrypter.encrypt(new FileInputStream(keyFile), text);
        }

        @Override
        void cryptFile(File keyFile, String passPhrase, InputStream in, OutputStream out) throws IOException {
            RsaEncrypter.encryptFile(new FileInputStream(keyFile), in, out);
        }

        @Override
        void cryptPropertyFile(File keyFile, String passPhrase, InputStream in, OutputStream out, String pref)
                throws IOException {
            RsaEncrypter.encryptPropertyFile(new FileInputStream(keyFile), in, out, pref);
        }
    },
    RsaDecrypt {
        @Override
        String cryptText(File keyFile, String passPhrase, String text) throws IOException {
            return RsaDecrypter.decrypt(new FileInputStream(keyFile), text);
        }

        @Override
        void cryptFile(File keyFile, String passPhrase, InputStream in, OutputStream out) throws IOException {
            RsaDecrypter.decryptFile(new FileInputStream(keyFile), in, out);
        }

        @Override
        void cryptPropertyFile(File keyFile, String passPhrase, InputStream in, OutputStream out, String pref)
                throws IOException {
            RsaDecrypter.decryptPropertyFile(new FileInputStream(keyFile), in, out, pref);
        }
    },
    AesEncrypt {
        @Override
        String cryptText(File keyFile, String passPhrase, String text) throws IOException {
            return AesEncrypter.encrypt(passPhrase, text);
        }

        @Override
        void cryptFile(File keyFile, String passPhrase, InputStream in, OutputStream out) throws IOException {
            throw new UnsupportedOperationException("AES not supported for encrypting simple files");
        }

        @Override
        void cryptPropertyFile(File keyFile, String passPhrase, InputStream in, OutputStream out, String pref)
                throws IOException {
            AesEncrypter.encryptPropertyFile(passPhrase, in, out, pref);
        }
    },
    AesDecrypt {
        @Override
        String cryptText(File keyFile, String passPhrase, String text) throws IOException {
            return AesDecrypter.decrypt(passPhrase, text);
        }

        @Override
        void cryptFile(File keyFile, String passPhrase, InputStream in, OutputStream out) throws IOException {
            throw new UnsupportedOperationException("AES not supported for decrypting simple files");
        }

        @Override
        void cryptPropertyFile(File keyFile, String passPhrase, InputStream in, OutputStream out, String pref)
                throws IOException {
            AesDecrypter.decryptPropertyFile(passPhrase, in, out, pref);
        }
    };

    abstract String cryptText(File keyFile, String passPhrase, String text) throws IOException;

    abstract void cryptFile(File keyFile, String passPhrase, InputStream fileInputStream, OutputStream fileOutputStream)
            throws IOException;

    abstract void cryptPropertyFile(File keyFile, String passPhrase, InputStream fileInputStream,
            OutputStream fileOutputStream, String prefix) throws IOException;
}