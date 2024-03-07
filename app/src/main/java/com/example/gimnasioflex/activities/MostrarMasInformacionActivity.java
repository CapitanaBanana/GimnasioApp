package com.example.gimnasioflex.activities;

import static com.example.gimnasioflex.activities.ListarAlumnosActivity.EXTRA_PERSONA;
import static com.example.gimnasioflex.utils.Common.formatter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gimnasioflex.models.Persona;
import com.example.gimnasioflex.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MostrarMasInformacionActivity extends AppCompatActivity {
    private Persona persona;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_mas_informacion);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_PERSONA)) {
            persona = (Persona) intent.getSerializableExtra(EXTRA_PERSONA);
            // Mostrar datos
            TextView textView = findViewById(R.id.NombreCompleto);
            textView.setText(String.format("%s %s", persona.getNom(), persona.getApe()));
            TextView textView1 = findViewById(R.id.FechaRegistro);
            textView1.setText(persona.getRegistro().format(formatter));
            db = new DBHelper(this);
        }
    }

    public void actualizarCliente(View view) {
        db = new DBHelper(this);
        boolean res = db.updateClientData(persona.getNom(), persona.getApe(), persona.getDni());

    }

    public void eliminarCliente(View view) {
        db = new DBHelper(this);
        Boolean res = db.deleteClient(persona.getDni());
        if (res) {
            Toast.makeText(this, "Cliente eliminado con exito!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ListarAlumnosActivity.class);
            intent.putExtra("recreate", true);
            startActivity(intent);
            finish();
        } else
            Toast.makeText(this, "El cliente no se pudo eliminar", Toast.LENGTH_SHORT).show();
    }


    public void agregarCuota(View view) {
        Intent intent = new Intent(this, MenuCuotasActivity.class);
        intent.putExtra(EXTRA_PERSONA, persona);
        startActivity(intent);
        finish();
    }
}