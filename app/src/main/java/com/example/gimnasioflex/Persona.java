package com.example.gimnasioflex;

import android.os.Build;
import android.os.Parcelable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Persona implements Serializable {
    private static final long serialVersionUID = 1L; // Identificador de versión serializable
    private String dni, nom, ape;
    private LocalDate registro;

    public ArrayList<LocalDate> getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(ArrayList<LocalDate> asistencia) {
        this.asistencia = asistencia;
    }

    private ArrayList<LocalDate> asistencia;

    public String getApe() {
        return ape;
    }

    public String getDni() {
        return dni;
    }

    public String getNom() {
        return nom;
    }

    public LocalDate getRegistro() {
        return registro;
    }
    public void setRegistro(LocalDate reg) {
        registro=reg;
    }


    Persona(String nom, String ape, String dni, String reg){
        this.dni= dni;
        this.nom=nom;
        this.ape=ape;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.registro=LocalDate.parse(reg);
        }
        this.asistencia = new ArrayList<>();
    }
    @Override
    public String toString(){
        return (this.nom+" "+ this.ape);
    }

}
