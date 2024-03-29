package com.example.gimnasioflex.activities;

import static com.example.gimnasioflex.utils.Common.precioDeTipo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.gimnasioflex.models.Persona;
import com.example.gimnasioflex.adapters.PersonaAdapter;
import com.example.gimnasioflex.R;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Persona> listaPersonas;
    private ArrayList<Persona> listaFiltrada;
    private DBHelper db = new DBHelper(this);
    private ListView listView;
    private SearchView searchView;
    private RadioButton dosVeces;
    private RadioButton tresVeces;
    private RadioButton libre;
    private Persona seleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_main);

        //seteo cosas búsqueda
        searchView = findViewById(R.id.busqueda);

        //seteo cosas para que ande el listado
        actualizarDB();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filtra las personas según el texto ingresado
                listaFiltrada.clear();
                for (Persona persona : listaPersonas) {
                    if (persona.getNom().toLowerCase().contains(newText.toLowerCase()) ||
                            persona.getApe().toLowerCase().contains(newText.toLowerCase()) ||
                            persona.getDni().contains(newText)) {
                        listaFiltrada.add(persona);
                    }
                }
                listView.setVisibility(newText.isEmpty() ? View.GONE : View.VISIBLE);
                // actualiza adaptador
                ArrayAdapter<Persona> newAdapter = new PersonaAdapter(MainActivity.this, listaFiltrada);
                // Establecer el nuevo Adapter en el ListView
                listView.setAdapter(newAdapter);

                return true;
            }
        });
        // Manejar clics en elementos del ListView si es necesario
        listView.setOnItemClickListener((parent, view, position, id) -> {
            listView.setVisibility(View.GONE);
            seleccionado = listaFiltrada.get(position);
            searchView.setQuery(seleccionado.toString(), false);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        });
        dosVeces = findViewById(R.id.DosVeces);
        tresVeces = findViewById(R.id.TresVeces);
        libre = findViewById(R.id.Libre);
    }

    private void actualizarDB() {
        listaPersonas = db.fetchClient();
        listaFiltrada = new ArrayList<>(listaPersonas);
        ArrayAdapter<Persona> adapter = new PersonaAdapter(this, listaFiltrada);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        searchView.setQuery("", true);
    }

    protected void onResume() {
        super.onResume();
        actualizarDB();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void registrarCuota(View view) {
        if (dosVeces.isChecked())
            agregarCuota("Dos Dias");
        else if (tresVeces.isChecked())
            agregarCuota("Tres Dias");
        else if (libre.isChecked())
            agregarCuota("Libre");
        else
            Toast.makeText(MainActivity.this, "Debe seleccionar un tipo de cuota!", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void agregarCuota(String tipo) {
        LocalDate inicio = LocalDate.now();
        db = new DBHelper(MainActivity.this);
        boolean check = db.addCuota(seleccionado.getDni(), inicio, inicio.plusDays(30), tipo, precioDeTipo(tipo));
        if (check)
            Toast.makeText(MainActivity.this, "Cuota agregada con exito!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivity.this, "La cuota no se pudo registrar!", Toast.LENGTH_SHORT).show();
    }
    //modulo buscar cliente

    public void menuPrincipal(View view) {
    }

    public void listarAlumnos(View view) {
        Intent intent = new Intent(this, ListarAlumnosActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    public void listarDeudores(View view) {
        Intent intent = new Intent(this, MenuDeudoresActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }
    public void ajustes(View view) {
        Intent intent = new Intent(this, MenuConfiguracionActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }
}