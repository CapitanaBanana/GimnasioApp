package com.example.gimnasioflex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getBooleanExtra("recreate", false)) {
            // Recrear la actividad
            getIntent().removeExtra("recreate");
            recreate();
        }
    }

}