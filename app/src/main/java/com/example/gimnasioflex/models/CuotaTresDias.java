package com.example.gimnasioflex.models;

import com.example.gimnasioflex.models.Cuota;

import java.time.LocalDate;

public class CuotaTresDias extends Cuota {
    public CuotaTresDias(String dni, LocalDate inicio, LocalDate fin){
        super(dni,inicio,fin);
    }
}
