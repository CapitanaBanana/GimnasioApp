package com.example.gimnasioflex;

import static com.example.gimnasioflex.ListarAlumnosActivity.EXTRA_PERSONA;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class MostrarMasInformacionActivity extends AppCompatActivity {
    private Persona persona;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_mas_informacion);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_PERSONA)) {
            persona = (Persona) intent.getSerializableExtra(EXTRA_PERSONA);

        // Mostrar datos
        TextView textView = findViewById(R.id.NombreCompleto);
        textView.setText(persona.getNom() + " " + persona.getApe());
        TextView textView1 = findViewById(R.id.FechaRegistro);
        textView1.setText(persona.getRegistro().toString());

        DBHelper db = new DBHelper(this);
        ArrayList<LocalDate> data= db.fetchAsistencia(persona.getDni());
        TextView textView2 = findViewById(R.id.UltimaAsistencia);
        textView2.setText(data.get(data.size()-1).toString());
    }
}
    public void agregarCuota(View view) {
        Intent intent = new Intent(this, MenuCuotasActivity.class);
        intent.putExtra(EXTRA_PERSONA, persona);
        startActivity(intent);
    }
}