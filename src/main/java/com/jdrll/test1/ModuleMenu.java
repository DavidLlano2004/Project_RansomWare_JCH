package com.jdrll.test1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModuleMenu extends JFrame {
    private JPanel PanelMain;
    private JButton btnRegister;
    private JButton btnFile;
    private JButton decryptButton;
    private JLabel backgroundLabel;
    private JLabel imageCala;

    public ModuleMenu() {
        setTitle("Menú Principal");
        setSize(1000, 723);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new CardLayout());

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 750));

        ImageIcon backgroundImage = new ImageIcon("ImgMessageENcrypted.png");
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 1000, 720);
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

        decryptButton = new JButton("Decrypt");
        styleButton(decryptButton);
        decryptButton.setBounds(462, 644, 290, 33);
        layeredPane.add(decryptButton, JLayeredPane.PALETTE_LAYER);

        PanelMain = new JPanel();
        PanelMain.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
        buttonPanel.setOpaque(false);

        btnRegister = new JButton("Registrar");
        btnFile = new JButton("Archivos");

        styleButton(btnRegister);
        styleButton(btnFile);

        buttonPanel.add(btnRegister);
        buttonPanel.add(btnFile);

        imageCala = new JLabel(new ImageIcon("ImgMainCala.png"));
        imageCala.setHorizontalAlignment(JLabel.CENTER);

        PanelMain.add(imageCala, BorderLayout.CENTER);
        PanelMain.add(buttonPanel, BorderLayout.SOUTH);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormVictima().setVisible(true);
            }
        });

        btnFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormCifradoArchivo().setVisible(true);
            }
        });

        // Crear el panel principal con CardLayout
        JPanel mainPanel = new JPanel(new CardLayout());
        mainPanel.add(layeredPane, "encrypted");
        mainPanel.add(PanelMain, "main");

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (mainPanel.getLayout());
                cl.show(mainPanel, "main");
            }
        });

        // Agregar el mainPanel al JFrame
        add(mainPanel);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(169, 169, 169));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ModuleMenu().setVisible(true);
            }
        });
    }
}
