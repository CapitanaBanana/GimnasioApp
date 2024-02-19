package com.example.gimnasioflex;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;

public abstract class Cuota {
    private LocalDate inicio, fin;
    private String dni;
    DBHelper db;
    public Cuota(String dni,LocalDate inicio, LocalDate fin){
        this.inicio=inicio;
        this.fin=fin;
        this.dni=dni;
    }
    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public String getDni() {
        return dni;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean estaVencida(){
        if (LocalDate.now().isAfter(getFin()))
            return true;
        else return false;
    }
}

