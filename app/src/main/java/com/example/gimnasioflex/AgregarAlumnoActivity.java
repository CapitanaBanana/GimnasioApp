package com.example.gimnasioflex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AgregarAlumnoActivity extends AppCompatActivity {
    EditText dniTxt,nomTxt,apeTxt;
    Button regBtn;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_alumno);

        dniTxt = findViewById(R.id.DNI);
        nomTxt = findViewById(R.id.Nombre);
        apeTxt = findViewById(R.id.Apellido);
        regBtn = findViewById(R.id.registrar);
        db = new DBHelper(AgregarAlumnoActivity.this);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = nomTxt.getText().toString();
                String ape = apeTxt.getText().toString();
                String dni = dniTxt.getText().toString();
                if (dni.length() == 8)
                {
                    Persona p = db.getPersonaPorDNI(dni);
                    if(p!=null){
                        Toast.makeText(AgregarAlumnoActivity.this, "El dni ingresado ya está en el sistema!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        boolean check = db.addClient(nom, ape, dni);
                        if (check)
                            Toast.makeText(AgregarAlumnoActivity.this, "Cliente guardado con exito!", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(AgregarAlumnoActivity.this, "El cliente no se pudo guardar!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                else
                    Toast.makeText(AgregarAlumnoActivity.this,"Datos Incorrectos", Toast.LENGTH_SHORT).show();

            }
        });


    }
}