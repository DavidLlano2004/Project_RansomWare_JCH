package com.jdrll.test1.ransomewareBackend;

import javax.crypto.SecretKey;

public abstract class Victim {
    private static int idCounter = 0;
    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private SecretKey keyCifrado;
    private SecretKey keyDescifrado;

    public Victim(String nombre, String apellido, String email, SecretKey keyCifrado, SecretKey keyDescifrado) {
        this.id = ++idCounter;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.keyCifrado = keyCifrado;
        this.keyDescifrado = keyDescifrado;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SecretKey getKeyCifrado() {
        return keyCifrado;
    }

    public void setKeyCifrado(SecretKey keyCifrado) {
        this.keyCifrado = keyCifrado;
    }

    public SecretKey getKeyDescifrado() {
        return keyDescifrado;
    }

    public void setKeyDescifrado(SecretKey keyDescifrado) {
        this.keyDescifrado = keyDescifrado;
    }

    public abstract void displayInfo();
}
