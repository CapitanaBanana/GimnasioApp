package com.example.gimnasioflex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class ListarAlumnosActivity extends AppCompatActivity implements ListAdapter.ItemClickListener{
    public static final String EXTRA_PERSONA= "com.example.GimnasioFlex.PERSONA";
    ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_alumnos);
        DBHelper db = new DBHelper(this);
        ArrayList<Persona> data= db.fetchClient();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Crea e inicializa el adaptador con la lista de personas
        adapter= new ListAdapter(this, data);
        adapter.setClickListener(this);
        // Asigna el adaptador al RecyclerView
        recyclerView.setAdapter(adapter);
    }

    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, MostrarMasInformacionActivity.class);
        intent.putExtra(EXTRA_PERSONA, (Serializable) adapter.getItem(position));
        startActivity(intent);
    }

}