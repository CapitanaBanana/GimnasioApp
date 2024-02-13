package com.example.gimnasioflex;

import static com.example.gimnasioflex.ListarAlumnosActivity.EXTRA_PERSONA;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;

public class MenuCuotasActivity extends AppCompatActivity {
    private Persona persona;
    EditText fechaInicio;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cuotas);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_PERSONA)) {
            persona = (Persona) intent.getSerializableExtra(EXTRA_PERSONA);
        }
    }
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void cuotaDosDias(){
            agregarCuota("Dos Dias");
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void cuotaTresDias(){
            agregarCuota("Tres Dias");
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void cuotaLibre(){
            agregarCuota("Libre");
        }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void agregarCuota(String tipo){
        fechaInicio = findViewById(R.id.FechaInicio);
        LocalDate inicio= LocalDate.parse(fechaInicio.toString());
        db = new DBHelper(MenuCuotasActivity.this);
        boolean check = db.addCuota(persona.getDni(), inicio, inicio.plusDays(30), tipo);
        if (check)
            Toast.makeText(MenuCuotasActivity.this, "Cuota agregada con exito!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MenuCuotasActivity.this, "La cuota no se pudo registrar!", Toast.LENGTH_SHORT).show();
    }


}