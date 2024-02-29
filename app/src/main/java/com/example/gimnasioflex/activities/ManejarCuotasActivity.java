package com.example.gimnasioflex.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.gimnasioflex.models.Cuota;
import com.example.gimnasioflex.adapters.ListAdapter;
import com.example.gimnasioflex.models.Persona;
import com.example.gimnasioflex.R;

import java.io.Serializable;
import java.util.ArrayList;

public class ManejarCuotasActivity extends AppCompatActivity implements ListAdapter.ItemClickListener {
    public static final String EXTRA_PERSONA= "com.example.GimnasioFlex.PERSONA";
    private ListAdapter adapter;
    private EditText editText;
    private RecyclerView recyclerView;
    private DBHelper db = new DBHelper(this);
    private ArrayList<Persona> data;
    private ArrayList<Persona> listaFiltrada = new ArrayList<>();
    private Spinner spinnerFiltrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        else{
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_manejar_cuotas);
        recyclerView = findViewById(R.id.recyclerView2);
        spinnerFiltrado= findViewById(R.id.spinnerFiltrado);
        data= db.fetchClient();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Crea e inicializa el adaptador con la lista de personas
        adapter= new ListAdapter(this, data);
        adapter.setClickListener(this);
        // Asigna el adaptador al RecyclerView
        recyclerView.setAdapter(adapter);
        adapter.update(data);

        // Obtén las opciones de filtrado del recurso de cadenas
        String[] opcionesFiltrado = getResources().getStringArray(R.array.filter_options);

        // Configura el adaptador para el Spinner
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcionesFiltrado);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFiltrado.setAdapter(adapter2);


        // Maneja el evento de selección del Spinner
        spinnerFiltrado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Lógica para manejar la opción de filtrado seleccionada
                String selectedFilter = opcionesFiltrado[position];
                // Actualiza la lista según el filtro seleccionado
                updateListAccordingToFilter(selectedFilter);
            }
            public void onNothingSelected(AdapterView<?> parentView) {
                // No se necesita realizar ninguna acción cuando no se selecciona nada
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateListAccordingToFilter(String selectedFilter) {

        if(selectedFilter.equals("Listar todo"))
            adapter.update(data);
        else if(selectedFilter.equals("Ver personas que deben cuota")){
            for (Cuota cuota :db.fetchCuotas()) {
                if (cuota.estaVencida())
                    listaFiltrada.add(db.getPersonaPorDNI(cuota.getDni()));
            }
            adapter.update(listaFiltrada);
        }
        else if(selectedFilter.equals("Ver personas que no cumplieron con su cuota")){
            //implementar
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, MostrarMasInformacionActivity.class);
        intent.putExtra(EXTRA_PERSONA, (Serializable) adapter.getItem(position));
        startActivity(intent);
    }

}