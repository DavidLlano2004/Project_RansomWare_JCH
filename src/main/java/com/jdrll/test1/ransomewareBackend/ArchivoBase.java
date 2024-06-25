package com.jdrll.test1.ransomewareBackend;

import java.io.File;

public abstract class ArchivoBase {
    private File archivo;
    private Cifrado cifrado;

    public ArchivoBase(File archivo, Cifrado cifrado) {
        this.archivo = archivo;
        this.cifrado = cifrado;
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public Cifrado getCifrado() {
        return cifrado;
    }

    public void setCifrado(Cifrado cifrado) {
        this.cifrado = cifrado;
    }

    public abstract void manejarArchivo();

}
