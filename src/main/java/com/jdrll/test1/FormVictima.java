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
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializar componentes
        nombreField = new JTextField(20);
        apellidoField = new JTextField(20);
        emailField = new JTextField(20);
        statusLabel = new JLabel("Estado: ");

        // Configurar panel de persona con un borde vacío a los lados
        JPanel panelPersona = new JPanel();
        panelPersona.setLayout(new BorderLayout()); // Usamos BorderLayout para colocar el botón arriba
        panelPersona.setBorder(new EmptyBorder(10, 10, 10, 10)); // Añade márgenes a los lados

        // Botón para volver al menú principal
        JButton volverButton = new JButton("Volver al Menú");
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra el formulario actual
                new ModuleMenu().setVisible(true); // Muestra el ModuleMenu
            }
        });
        panelPersona.add(volverButton, BorderLayout.NORTH); // Añadimos el botón arriba

        // Panel para los campos de entrada y labels
        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new GridLayout(4, 2, 10, 10)); // Espacio entre celdas
        panelCampos.add(new JLabel("Nombre: "));
        panelCampos.add(nombreField);
        panelCampos.add(new JLabel("Apellido: "));
        panelCampos.add(apellidoField);
        panelCampos.add(new JLabel("Email: "));
        panelCampos.add(emailField);
        JButton registrarButton = new JButton("Registrar");
        panelCampos.add(registrarButton);
        panelCampos.add(statusLabel);

        // Agregar evento al botón de registrar
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombre = nombreField.getText();
                    String apellido = apellidoField.getText();
                    String email = emailField.getText();

                    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                    keyGen.init(128);
                    SecretKey keyCifrado = keyGen.generateKey();
                    SecretKey keyDescifrado = keyGen.generateKey();

                    persona = new VictimDeri(nombre, apellido, email, keyCifrado, keyDescifrado);
                    persona.displayInfo();
                    statusLabel.setText("Estado: Persona registrada con éxito.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    statusLabel.setText("Estado: Error al registrar persona.");
                }
            }
        });

        // Agregar panel de campos al panel principal
        panelPersona.add(panelCampos, BorderLayout.CENTER);

        // Establecer el contenido del JFrame
        setContentPane(panelPersona);
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
