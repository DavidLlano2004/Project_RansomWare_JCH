package com.jdrll.test1;

import com.jdrll.test1.ransomewareBackend.VictimDeri;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormVictima extends JFrame {
    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField emailField;
    private JLabel statusLabel;
    private VictimDeri persona;

    public FormVictima() {
        // Configurar el JFrame
        setTitle("Registrar Usuario");
        setSize(1000, 723);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK); // Fondo negro

        // Inicializar componentes
        nombreField = new JTextField(20);
        apellidoField = new JTextField(20);
        emailField = new JTextField(20);
        statusLabel = new JLabel("Estado: ");
        statusLabel.setForeground(Color.WHITE); // Texto blanco

        // Configurar panel principal con un borde vacío a los lados
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10)); // Márgenes a los lados
        panelPrincipal.setBackground(Color.BLACK); // Fondo negro

        // Botón para volver al menú principal
        JButton volverButton = new JButton("Volver al Menú");
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra el formulario actual
                new ModuleMenu().setVisible(true); // Muestra el ModuleMenu
            }
        });
        styleButton(volverButton); // Estilizar botón
        panelPrincipal.add(volverButton, BorderLayout.NORTH); // Añadir botón arriba

        // Panel para los campos de entrada y labels
        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new GridLayout(4, 2, 10, 10)); // Espacio entre celdas
        panelCampos.setBackground(Color.BLACK); // Fondo negro

        // Estilizar campos de entrada y labels
        styleFormField(nombreField);
        panelCampos.add(new JLabel("Nombre: "));
        panelCampos.add(nombreField);

        styleFormField(apellidoField);
        panelCampos.add(new JLabel("Apellido: "));
        panelCampos.add(apellidoField);

        styleFormField(emailField);
        panelCampos.add(new JLabel("Email: "));
        panelCampos.add(emailField);

        // Estilizar botón de registrar
        JButton registrarButton = new JButton("Registrar");
        styleButton(registrarButton);
        panelCampos.add(registrarButton);

        // Añadir statusLabel al panelCampos
        panelCampos.add(statusLabel);

        // Agregar panel de campos al panel principal
        panelPrincipal.add(panelCampos, BorderLayout.CENTER);

        // Establecer el contenido del JFrame
        setContentPane(panelPrincipal);
    }

    // Método para estilizar botones
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(180, 180, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }


    private void styleFormField(JTextField field) {
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBackground(new Color(40, 40, 40));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                field.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }

    public VictimDeri getPersona() {
        return persona;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FormVictima().setVisible(true);
            }
        });
    }
}
