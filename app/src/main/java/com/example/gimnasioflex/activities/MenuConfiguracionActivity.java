package com.example.gimnasioflex.activities;

import static com.example.gimnasioflex.utils.Common.precioDosDias;
import static com.example.gimnasioflex.utils.Common.precioLibre;
import static com.example.gimnasioflex.utils.Common.precioTresDias;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gimnasioflex.R;

public class MenuConfiguracionActivity extends AppCompatActivity {
    private TextView libre;
    private TextView dosDias;
    private TextView tresDias;
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
        setContentView(R.layout.activity_menu_configuracion);

         libre= findViewById(R.id.valorLibre);
         dosDias= findViewById(R.id.valorDosDias);
         tresDias= findViewById(R.id.valorTresDias);

         libre.setHint("Actual: $"+ precioLibre);
         dosDias.setHint("Actual: $"+precioDosDias);
         tresDias.setHint("Actual: $"+precioTresDias);
    }

    public void guardarCambios(View view){
        String libreText=libre.getText().toString();
        String dosText=dosDias.getText().toString();
        String tresText=tresDias.getText().toString();
        if (!libreText.isEmpty()){
            precioLibre= Integer.valueOf(libreText);
        }
        if (!dosText.isEmpty()){
            precioDosDias= Integer.valueOf(dosText);
        }
        if (!tresText.isEmpty()){
            precioTresDias= Integer.valueOf(tresText);
        }
        Toast.makeText(MenuConfiguracionActivity.this, "Valores actualizados exitosamente!", Toast.LENGTH_SHORT).show();
        finish();
    }
}