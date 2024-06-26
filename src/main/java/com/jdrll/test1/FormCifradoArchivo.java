package com.jdrll.test1;

import com.jdrll.test1.ransomewareBackend.Cifrado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class FormCifradoArchivo extends JFrame {

    private JTextArea logArea;
    private ImageIcon skullIcon;
    private ImageIcon sunIcon;
    private JPanel iconPanel;

    public FormCifradoArchivo() {
        setTitle("Cifrado y Descifrado de Archivos");
        setSize(1000, 723);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);

        skullIcon = new ImageIcon("Danger.png");
        skullIcon = new ImageIcon(skullIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));

        sunIcon = new ImageIcon("checkim.png");
        sunIcon = new ImageIcon(sunIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.BLACK);

        iconPanel = new JPanel();
        iconPanel.setBackground(Color.BLACK);
        iconPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        panel.add(iconPanel, BorderLayout.NORTH);

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setBackground(Color.BLACK);
        logArea.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(logArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton encryptButton = new JButton("Cifrar");
        JButton decryptButton = new JButton("Descifrar");


        encryptButton.setBackground(Color.DARK_GRAY);
        encryptButton.setForeground(Color.WHITE);
        decryptButton.setBackground(Color.DARK_GRAY);
        decryptButton.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(Color.BLACK);
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

                    try {

                        File encryptedFile = new File(selectedFile.getParentFile(), getEncryptedFileName(selectedFile));
                        Cifrado.encrypt(selectedFile, encryptedFile);
                        updateIcons(true);
                        logArea.append("Archivo cifrado exitosamente: " + encryptedFile.getAbsolutePath() + "\n");

                    } catch (Cifrado.CryptoException ex) {
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

                    try {

                        File decryptedFile = new File(selectedFile.getParentFile(), getDecryptedFileName(selectedFile));
                        Cifrado.decrypt(selectedFile, decryptedFile);
                        updateIcons(false);
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
        return nameWithoutExtension + ".enc";
    }

    private String getDecryptedFileName(File inputFile) {
        String originalName = inputFile.getName();
        int dotIndex = originalName.lastIndexOf('.');
        String nameWithoutExtension = dotIndex == -1 ? originalName : originalName.substring(0, dotIndex);
        return nameWithoutExtension + ".dec";
    }

    private void updateIcons(boolean isCifrado) {
        if (isCifrado) {
            iconPanel.removeAll();
            JLabel skullLabel = new JLabel(skullIcon);
            iconPanel.add(skullLabel);
        } else {
            iconPanel.removeAll();
            JLabel sunLabel = new JLabel(sunIcon);
            iconPanel.add(sunLabel);
        }
        iconPanel.revalidate();
        iconPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FormCifradoArchivo();
            }
        });
    }
}
