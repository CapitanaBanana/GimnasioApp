package com.example.gimnasioflex;

import java.time.LocalDate;

public abstract class Cuota {
    private LocalDate inicio, fin;
    private String dni;
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

}
