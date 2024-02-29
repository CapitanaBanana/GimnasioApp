package com.example.gimnasioflex.models;

import com.example.gimnasioflex.models.Cuota;

import java.time.LocalDate;

public class CuotaDosDias extends Cuota {
    public CuotaDosDias(String dni, LocalDate inicio, LocalDate fin) {
        super(dni, inicio, fin);
    }
}
