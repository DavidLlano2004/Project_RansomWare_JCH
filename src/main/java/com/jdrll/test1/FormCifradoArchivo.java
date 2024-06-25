package com.jdrll.test1;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FormCifradoArchivo extends JFrame {

    private JTextArea logArea;

    public FormCifradoArchivo() {
        setTitle("Cifrado y Descifrado de Archivos");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton encryptButton = new JButton("Cifrar");
        JButton decryptButton = new JButton("Descifrar");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(FormCifradoArchivo.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    logArea.append("Archivo seleccionado: " + selectedFile.getAbsolutePath() + "\n");

                    // Generar clave AES automáticamente
                    try {
                        SecretKey secretKey = generateAESKey();
                        File keyFile = new File("key.key");
                        saveKey(secretKey, keyFile);

                        // Cifrar el archivo seleccionado
                        File encryptedFile = new File(selectedFile.getParentFile(), "archivo_cifrado.txt");
                        Cifrado.encrypt(selectedFile, encryptedFile);
                        logArea.append("Archivo cifrado exitosamente: " + encryptedFile.getAbsolutePath() + "\n");

                    } catch (Cifrado.CryptoException | IOException ex) {
                        logArea.append("Error durante el cifrado: " + ex.getMessage() + "\n");
                    }
                }
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(FormCifradoArchivo.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    logArea.append("Archivo seleccionado: " + selectedFile.getAbsolutePath() + "\n");

                    // Descifrar el archivo seleccionado
                    try {
                        File decryptedFile = new File(selectedFile.getParentFile(), "archivo_descifrado.txt");
                        Cifrado.decrypt(selectedFile, decryptedFile);
                        logArea.append("Archivo descifrado exitosamente: " + decryptedFile.getAbsolutePath() + "\n");

                    } catch (Cifrado.CryptoException ex) {
                        logArea.append("Error durante el descifrado: " + ex.getMessage() + "\n");
                    }
                }
            }
        });

        add(panel);
        setVisible(true);
    }

    private SecretKey generateAESKey() throws Cifrado.CryptoException {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128); // Tamaño de clave AES 128 bits
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException ex) {
            throw new Cifrado.CryptoException("Error generando la clave AES: Algoritmo no encontrado", ex);
        }
    }

    private void saveKey(SecretKey key, File keyFile) throws IOException {
        byte[] encodedKey = key.getEncoded();
        Files.write(keyFile.toPath(), encodedKey);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FormCifradoArchivo();
            }
        });
    }
}

class Cifrado {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    public static void encrypt(File inputFile, File outputFile) throws CryptoException {
        try {
            SecretKey secretKey = loadKey(new File("key.key"));

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
