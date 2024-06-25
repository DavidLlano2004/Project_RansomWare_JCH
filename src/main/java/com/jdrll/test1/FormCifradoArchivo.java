package com.jdrll.test1;

import com.jdrll.test1.ransomewareBackend.Cifrado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

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

                    try {
                        // Cifrar el archivo seleccionado
                        File encryptedFile = new File(selectedFile.getParentFile(), getEncryptedFileName(selectedFile));
                        Cifrado.encrypt(selectedFile, encryptedFile);
                        logArea.append("Archivo cifrado exitosamente: " + encryptedFile.getAbsolutePath() + "\n");

                    } catch (Cifrado.CryptoException
                            ex) {
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

                    try {
                        // Descifrar el archivo seleccionado
                        File decryptedFile = new File(selectedFile.getParentFile(), getDecryptedFileName(selectedFile));
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

    private String getEncryptedFileName(File inputFile) {
        String originalName = inputFile.getName();
        int dotIndex = originalName.lastIndexOf('.');
        String nameWithoutExtension = dotIndex == -1 ? originalName : originalName.substring(0, dotIndex);
        return nameWithoutExtension + ".enc"; // Cambiar la extensión para archivos cifrados
    }

    private String getDecryptedFileName(File inputFile) {
        String originalName = inputFile.getName();
        int dotIndex = originalName.lastIndexOf('.');
        String nameWithoutExtension = dotIndex == -1 ? originalName : originalName.substring(0, dotIndex);
        return nameWithoutExtension + "_decrypted.txt"; // Cambiar la extensión para archivos descifrados
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FormCifradoArchivo();
            }
        });
    }
}
