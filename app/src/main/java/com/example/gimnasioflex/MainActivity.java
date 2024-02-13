package com.example.gimnasioflex;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText txtDNI;
    private Button loginBtn;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper db = new DBHelper(this);
        db.addClient("juan","lopez","33333333");
        db.addAsistencia("33333333");
    }
    public void verificarExistencia(View view){
        {
            TextView txtDNI = findViewById(R.id.txtDNI);
            String dni ="0";
            dni = txtDNI.getText().toString();
            if (dni.length()!=8)
                Toast.makeText(MainActivity.this, "El formato del DNI es inválido", Toast.LENGTH_SHORT).show();
            else{
                DBHelper db = new DBHelper(this);
                ArrayList<Persona> data= db.fetchClient();
                String finalDni = dni;
                Persona p=data.stream().filter(persona->persona.getDni().equals(finalDni)).findAny().orElse(null);
                if (p==null){
                    Toast.makeText(MainActivity.this, "El DNI ingresado no existe!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Hola, "+ p.getNom()+"!", Toast.LENGTH_SHORT).show();
                    boolean check = db.addAsistencia(dni);
                }
            }

        }
    }

    public void agregarAlumno(View view) {
        Intent intent = new Intent(this, AgregarAlumnoActivity.class);
        startActivity(intent);
    }
    public void listarAlumnos(View view) {
        Intent intent = new Intent(this, ListarAlumnosActivity.class);
        startActivity(intent);
    }
}