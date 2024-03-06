package com.example.gimnasioflex.models;

import android.os.Build;
import android.os.Parcelable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Persona implements Serializable {
    private static final long serialVersionUID = 1L; // Identificador de versión serializable
    private String dni, nom, ape;
    private LocalDate registro;

    private ArrayList<Cuota> cuotas;
    private Cuota ultimaCuota;


    public Persona(String nom, String ape, String dni, String reg){
        this.dni= dni;
        this.nom=nom;
        this.ape=ape;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.registro=LocalDate.parse(reg);
        }
        this.cuotas = new ArrayList<>();
    }
    public void agregarCuota(Cuota c) {
        cuotas.add(c);
    }
    @Override
    public String toString(){
        return (this.nom+" "+ this.ape);
    }
    public String getApe() {
        return ape;
    }

    public  String getDni() {
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
    public ArrayList<Cuota> getCuotas() {
        return cuotas;
    }
    public Cuota getUltimaCuota() {
        return ultimaCuota;
    }

    public void setCuotas(ArrayList<Cuota> cuota) {
        this.cuotas = cuota;
    }
    public void setUltimaCuota(Cuota cuota) {
        this.ultimaCuota = cuota;
    }

}
