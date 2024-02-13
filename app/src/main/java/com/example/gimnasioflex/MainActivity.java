package com.example.gimnasioflex;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_main);
    }
    public void verificarExistencia(View view){
        {
            TextView txtDNI = findViewById(R.id.txtDNI);
            String dni = txtDNI.getText().toString();
            if (dni.length()!=8)
                Toast.makeText(MainActivity.this, "El formato del DNI es inválido", Toast.LENGTH_SHORT).show();
            else{
                DBHelper db = new DBHelper(this);
                Persona p= db.getPersonaPorDNI(dni);
                if (p==null){
                    Toast.makeText(MainActivity.this, "El DNI ingresado no existe!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Hola, "+ p.getNom()+"!", Toast.LENGTH_SHORT).show();
                    db.addAsistencia(dni);
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