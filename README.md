#Cryptographic Recipes

A utility library for using common cryptographic functions for application usage. Rather than providing 
generic classes that can be used in multiple ways, this library provides readymade recipes that can be 
applied to situations directly. It has utilities for asymmetric key based encryption, password / 
passphrase based symmetric encryption and message digest functionality.

[![Build Status](https://buildhive.cloudbees.com/job/polyglotted/job/crypto-recipes/badge/icon)](https://buildhive.cloudbees.com/job/polyglotted/job/crypto-recipes/)

##Dependency
The project stores a full binary jar file that could be executed.

      <dependency>
          <groupId>org.polyglotted</groupId>
          <artifactId>crypto-recipes</artifactId>
          <version>1.0.0</version>
          <classifier>full</classifier>
      <dependency>

Note, use the classifier only if you are interested in getting the library as an executable. For inclusion 
into your project, you might also use the simple jar. 

##Message Digest

Message digests produce a small "fingerprint" of a larger set of data. A message digest takes an arbitrary 
amount of input data and produces a short, digested version of the data. This library supports usage of 
MD5 and SHA1 message digests. For ease of use, a CRC32 checksum is also provided.

##Symmetric Cryptography

Symmetric-key algorithms[1] are a class of algorithms for cryptography that use the same cryptographic 
keys for both encryption of plaintext and decryption of ciphertext. The adaption for this cryptography 
is based on <http://stackoverflow.com/questions/992019/java-256-bit-aes-password-based-encryption>

##Asymmetric Key based Cryptography

The asymmetric key cryptography a.k.a public key cryptography, which requires the use of two key files which 
are mathematically linked. This functionality is adapted based on the blog post at 
<http://codeartisan.blogspot.co.uk/2009/05/public-key-cryptography-in-java.html>

###Managing Keys
<b>Openssl</b> This is the de-facto tool sysadmins use for managing public/private keys, X.509 certificates, 
etc. This is what we want to create/manage our keys with, so that they can be stored in formats that are 
common across most Un*x systems and utilities.

<b>Creating the keypair</b> We are going to create a keypair, saving it in openssl's preferred PEM format. 
PEM formats are ASCII and hence easy to email around as needed. However, we will need to convert the keys to 
the binary DER format so Java can read them. Also we will base64 encode them so that they still are in ASCII 
format to be emailed around.

      # generate a 2048-bit RSA private key
      $ openssl genrsa -out private_key.pem 2048

      # convert private Key to PKCS#8 format (so Java can read it)
      $ openssl pkcs8 -topk8 -inform PEM -outform DER -in private_key.pem -nocrypt | openssl base64 > private_key.txt

      # output public key portion in DER format (so Java can read it)
      $ openssl rsa -in private_key.pem -pubout -outform DER | openssl base64 > public_key.txt

## Launching the different utilities

There are 3 main utility programs provided in this recipe - all of them either use AES based symmetric key 
or RSA based asymmetric key based algorithms. 

### CryptoMain

This utility helps to encrypt and decrypt text to the system output. To launch the utility

      $ java -classpath crypto-recipes-1.0.0-full.jar org.polyglotted.crypto.CryptoMain

The program expects the following options:

      * --algo, -a
           Valid algorithm values are [RsaEncrypt / RsaDecrypt / AesEncrypt / AesDecrypt]
        --keyfile, -k
           Public key file for encryption / private key file for decryption in case of RSA cryptography
        --passphrase, -r
           Password for encryption / decryption in case of AES cryptography
      * --text, -t
           text to be crypted

### FileCryptoMain

This utility encrypts and decrypts the entire contents of a file into another file. This utility 
does not support the AES cryptography and always expects the keyfile. To launch the utility

      $ java -classpath crypto-recipes-1.0.0-full.jar org.polyglotted.crypto.FileCryptoMain

The program expects the following options:

      * --algo, -a
           Valid algorithm values are [RsaEncrypt / RsaDecrypt / AesEncrypt / AesDecrypt]
        --keyfile, -k
           Public key file for encryption / private key file for decryption in case of RSA cryptography
        --passphrase, -r
           Password for encryption / decryption in case of AES cryptography
      * --input, -i
           Input file for crypting the text
      * --output, -o
           Output file for writing the transformed content

### PropertyFileCryptoMain

This utility encrypts and decrypts specific key-value pair within a property file based on a prefix for the 
attribute. This is used in the <https://github.com/polyglotted/attribute-repo.git> project. To launch 
the utility, use the following command

      $ java -classpath crypto-recipes-1.0.0-full.jar org.polyglotted.crypto.PropertyFileCryptoMain
        
The program expects the following options:

      * --algo, -a
           Valid algorithm values are [RsaEncrypt / RsaDecrypt / AesEncrypt / AesDecrypt]
        --keyfile, -k
           Public key file for encryption / private key file for decryption in case of RSA cryptography
        --passphrase, -r
           Password for encryption / decryption in case of AES cryptography
      * --input, -i
           Input file for crypting the text
      * --output, -o
           Output file for writing the transformed content
      * --prefix, -p
           Prefix for attribute lines that have to be crypted
