package com.jdrll.test1;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModuleMenu extends JFrame {
    private JPanel PanelMain;
    private JButton btnRegister;
    private JButton btnFile;
    private JLabel imageCala;

    public ModuleMenu() {
        // Configurar el JFrame
        setTitle("Menú Principal");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Agregar eventos a los botones
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

        // Establecer el contenido del JFrame
        setContentPane(PanelMain);
    }

    public static void main(String[] args) {
        // Crear y mostrar la ventana
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ModuleMenu().setVisible(true);
            }
        });
    }
}
