package com.example.gimnasioflex;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListarAlumnosActivity extends AppCompatActivity implements ListAdapter.ItemClickListener{
    public static final String EXTRA_PERSONA= "com.example.GimnasioFlex.PERSONA";
    private ListAdapter adapter;
    private EditText editText;
    private RecyclerView recyclerView;
    private DBHelper db = new DBHelper(this);
    private ArrayList<Persona> data;
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

        setContentView(R.layout.activity_listar_alumnos);
        editText=findViewById(R.id.Buscar_alumno);
        recyclerView = findViewById(R.id.recyclerView);
        data= db.fetchClient();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Crea e inicializa el adaptador con la lista de personas
        adapter= new ListAdapter(this, data);
        adapter.setClickListener(this);
        // Asigna el adaptador al RecyclerView
        recyclerView.setAdapter(adapter);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Filtra la lista según el texto ingresado
                ArrayList<Persona> listaFiltrada = filtrarLista(charSequence.toString());
                adapter.update(ListarAlumnosActivity.this,listaFiltrada);
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
        Intent intent = new Intent(this, MostrarMasInformacionActivity.class);
        intent.putExtra(EXTRA_PERSONA, (Serializable) adapter.getItem(position));
        startActivity(intent);
    }

}