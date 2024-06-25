package com.jdrll.test1.ransomewareBackend;

import java.io.File;
import java.util.Date;

public class Archivo extends ArchivoBase {
    private String fileId;
    private String fileName;
    private Date encryptionDate;
    public Archivo(File archivo, Cifrado cifrado, String fileId, String fileName, Date encryptionDate) {
        super(archivo, cifrado);
        this.fileId = fileId;
        this.fileName = fileName;
        this.encryptionDate = encryptionDate;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getEncryptionDate() {
        return encryptionDate;
    }

    public void setEncryptionDate(Date encryptionDate) {
        this.encryptionDate = encryptionDate;
    }

    @Override
    public void manejarArchivo() {
        System.out.println("Manejando archivo: " + getArchivo().getName());
    }
}
