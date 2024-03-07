package com.example.gimnasioflex.models;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.gimnasioflex.activities.DBHelper;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Cuota implements Serializable {
    private String tipo;
    private LocalDate inicio, fin;
    private String dni;
    private double precio;
    DBHelper db;
    public Cuota(String dni,LocalDate inicio, LocalDate fin, String tipo, int precio){
        this.inicio=inicio;
        this.fin=fin;
        this.dni=dni;
        this.tipo=tipo;
        this.precio=precio;
    }
    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFin() {
        return fin;
    }
    public double getPrecio() {
        return precio;
    }
    public String getTipo() {
        return tipo;
    }

    public String getDni() {
        return dni;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int diasVencida(){
        return (int)ChronoUnit.DAYS.between(fin, LocalDate.now());
    }

}

