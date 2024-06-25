package com.jdrll.test1;
import com.jdrll.test1.ransomewareBackend.Archivo;
import com.jdrll.test1.ransomewareBackend.ArchivoBase;
import com.jdrll.test1.ransomewareBackend.Cifrado;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

public class FormCifradoArchivo extends JFrame {
    private JTextField filePathField;
    private JLabel statusLabel;
    private ArchivoBase archivo;
    private JButton cargarArchivoButton;
    private JButton cifrarArchivoButton;
    private JButton descifrarArchivoButton;

    public FormCifradoArchivo() {
        setTitle("Cargar y Cifrar/Descifrar Archivo");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        filePathField = new JTextField(20);
        statusLabel = new JLabel("Estado: ");
        cargarArchivoButton = new JButton("Cargar Archivo");
        cifrarArchivoButton = new JButton("Cifrar Archivo");
        descifrarArchivoButton = new JButton("Descifrar Archivo");

        JPanel panelArchivo = new JPanel();
        panelArchivo.setLayout(new GridLayout(6, 1));

        // Añadir un borde con espacio alrededor del panel
        panelArchivo.setBorder(new EmptyBorder(10, 10, 10, 10)); // 10 píxeles de margen en todos los lados

        panelArchivo.add(new JLabel("Ruta del Archivo: "));
        panelArchivo.add(filePathField);
        panelArchivo.add(cargarArchivoButton);
        panelArchivo.add(cifrarArchivoButton);
        panelArchivo.add(descifrarArchivoButton);
        panelArchivo.add(statusLabel);

        cifrarArchivoButton.setVisible(false);
        descifrarArchivoButton.setVisible(false);

        cargarArchivoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filePathField.setText(selectedFile.getAbsolutePath());
                    try {
                        // Crear una instancia de Archivo con los nuevos atributos
                        String fileId = "123456"; // Puedes generar un fileId único aquí
                        String fileName = selectedFile.getName();
                        Date encryptionDate = new Date(); // Fecha actual de cifrado
                        archivo = new Archivo(selectedFile, new Cifrado(), fileId, fileName, encryptionDate);
                        statusLabel.setText("Estado: Archivo cargado con éxito.");
                        cifrarArchivoButton.setVisible(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        statusLabel.setText("Estado: Error al cargar el archivo.");
                    }
                }
            }
        });

        cifrarArchivoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Asegurarse de que 'archivo' sea de tipo Archivo
                    if (archivo instanceof Archivo) {
                        Archivo archivoCasteado = (Archivo) archivo;
                        File archivoCifrado = new File(archivoCasteado.getArchivo().getAbsolutePath() + ".enc");
                        archivoCasteado.getCifrado().cifrarArchivo(archivoCasteado, archivoCifrado);
                        statusLabel.setText("Estado: Archivo cifrado con éxito.");
                        // Mostrar botones después de cifrar
                        descifrarArchivoButton.setVisible(true);
                    } else {
                        statusLabel.setText("Estado: No se puede cifrar un archivo base.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    statusLabel.setText("Estado: Error al cifrar el archivo.");
                }
            }
        });

        descifrarArchivoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (archivo instanceof Archivo) {
                        Archivo archivoCasteado = (Archivo) archivo;
                        File archivoDescifrado = new File(archivoCasteado.getArchivo().getAbsolutePath()+ ".dec");
                        archivoCasteado.getCifrado().descifrarArchivo(archivoCasteado, archivoDescifrado);
                        statusLabel.setText("Estado: Archivo descifrado con éxito.");
                        // Mostrar botón para descargar archivo descifrado
                    } else {
                        statusLabel.setText("Estado: No se puede descifrar un archivo base.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    statusLabel.setText("Estado: Error al descifrar el archivo.");
                }
            }
        });

        setContentPane(panelArchivo);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FormCifradoArchivo().setVisible(true);
            }
        });
    }
}
