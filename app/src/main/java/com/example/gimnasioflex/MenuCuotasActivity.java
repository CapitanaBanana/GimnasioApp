package com.example.gimnasioflex;

import static com.example.gimnasioflex.ListarAlumnosActivity.EXTRA_PERSONA;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ReplacementSpan;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;

public class MenuCuotasActivity extends AppCompatActivity {
    private Persona persona;
    private DatePicker fechaInicio;
    private DBHelper db;
    RadioButton dosVeces;
    RadioButton tresVeces;
    RadioButton libre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cuotas);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_PERSONA)) {
            persona = (Persona) intent.getSerializableExtra(EXTRA_PERSONA);
        }
        fechaInicio = findViewById(R.id.datePicker);
        TextView textView = findViewById(R.id.Nombre);
        textView.setText("Alumno: " + persona.getNom()+ " " + persona.getApe());

        dosVeces=findViewById(R.id.DosVeces);
        tresVeces = findViewById(R.id.TresVeces);
        libre = findViewById(R.id.Libre);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void registrarCuota(View view){
        if(dosVeces.isChecked())
            agregarCuota("Dos Dias");
        else if(tresVeces.isChecked())
            agregarCuota("tres Dias");
        else if (libre.isChecked())
            agregarCuota("Libre");
        else
            Toast.makeText(MenuCuotasActivity.this, "Debe seleccionar un tipo de cuota!", Toast.LENGTH_SHORT).show();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void agregarCuota(String tipo){
        LocalDate inicio= LocalDate.of(fechaInicio.getYear(), fechaInicio.getMonth(), fechaInicio.getDayOfMonth());
        db = new DBHelper(MenuCuotasActivity.this);
        boolean check = db.addCuota(persona.getDni(), inicio, inicio.plusDays(30), tipo);
        if (check)
            Toast.makeText(MenuCuotasActivity.this, "Cuota agregada con exito!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MenuCuotasActivity.this, "La cuota no se pudo registrar!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
