package com.example.gimnasioflex.models;

import com.example.gimnasioflex.models.Cuota;

import java.time.LocalDate;

public class CuotaLibre extends Cuota {
    public CuotaLibre(String dni,LocalDate inicio, LocalDate fin){
        super(dni,inicio,fin);
    }
}
