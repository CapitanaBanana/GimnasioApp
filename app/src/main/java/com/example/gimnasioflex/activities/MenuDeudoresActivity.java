package com.example.gimnasioflex.activities;

import static com.example.gimnasioflex.activities.ListarAlumnosActivity.EXTRA_PERSONA;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gimnasioflex.adapters.ListadoDeudoresAdapter;
import com.example.gimnasioflex.adapters.ListAdapter;
import com.example.gimnasioflex.models.Persona;
import com.example.gimnasioflex.R;

import java.util.ArrayList;

public class MenuDeudoresActivity extends AppCompatActivity implements ListAdapter.ItemClickListener {
    private ListadoDeudoresAdapter adapter;
    private final DBHelper db = new DBHelper(this);
    private ArrayList<Persona> data = new ArrayList<>();
    private ArrayList<Persona> listaFiltrada;

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
        crearListado();
    }
    protected void onResume() {
        super.onResume();
        crearListado();
    }
    private void crearListado(){
        setContentView(R.layout.activity_listado_deudores);
        EditText editText = findViewById(R.id.Buscar_alumno);
        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            data = db.fetchCuotasVencidas();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Crea e inicializa el adaptador con la lista de personas
        if (!data.isEmpty()) {
            adapter = new ListadoDeudoresAdapter(this, data);
            adapter.setClickListener(this);
            // Asigna el adaptador al RecyclerView
            recyclerView.setAdapter(adapter);
        }
        else Toast.makeText(MenuDeudoresActivity.this, "No hay nadie que deba cuota", Toast.LENGTH_SHORT).show();


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Filtra la lista según el texto ingresado
                listaFiltrada = filtrarLista(charSequence.toString());
                adapter.update(listaFiltrada);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private ArrayList<Persona> filtrarLista(String textoBusqueda) {
        ArrayList<Persona> listaFiltrada = new ArrayList<>();
        for (Persona item : data) {
            if (item.getNom().toLowerCase().contains(textoBusqueda.toLowerCase()) || item.getApe().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                    item.getDni().contains(textoBusqueda)) {
                listaFiltrada.add(item);
            }
        }
        return listaFiltrada;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, MenuCuotasActivity.class);
        intent.putExtra(EXTRA_PERSONA, adapter.getItem(position));
        startActivity(intent);
    }
    public void menuPrincipal(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    public void listarAlumnos(View view) {
        Intent intent = new Intent(this, ListarAlumnosActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    public void listarDeudores(View view) {
    }
}