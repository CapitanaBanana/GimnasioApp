package com.example.gimnasioflex.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gimnasioflex.R;
import com.example.gimnasioflex.models.Persona;

import java.util.List;

public class PersonaAdapter extends ArrayAdapter<Persona> {

    public PersonaAdapter(Context context, List<Persona> personas) {
        super(context, 0, personas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Obtener la persona en la posición actual
        Persona persona = getItem(position);

        // Reutilizar la vista si está disponible; de lo contrario, inflarla
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_main_manu, parent, false);
        }

        // Configurar el contenido de la vista con los datos de la persona
        TextView nombreTextView = convertView.findViewById(R.id.nombreTextView);
        TextView dniTextView = convertView.findViewById(R.id.dniTextView);

        nombreTextView.setText(persona.getNom() + " " + persona.getApe());
        dniTextView.setText("DNI: " + persona.getDni());

        return convertView;
    }
}