package com.example.gimnasioflex.adapters;

import static com.example.gimnasioflex.utils.Common.formatter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gimnasioflex.R;
import com.example.gimnasioflex.models.Persona;

import java.util.ArrayList;

public class ListadoDeudoresAdapter extends RecyclerView.Adapter<ListadoDeudoresAdapter.ViewHolder> {
    private ArrayList<Persona> mData;
    private LayoutInflater mInflater;
    private com.example.gimnasioflex.adapters.ListAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public ListadoDeudoresAdapter(Context context, ArrayList<Persona> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    public void update(ArrayList<Persona> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_deudores, parent, false);
        return new ViewHolder(view);
    }


    // binds the data to the TextView in each row

    public void onBindViewHolder(ViewHolder holder, int position) {
        Persona Persona = mData.get(position);
        holder.nombre.setText(Persona.getNom() + " " + Persona.getApe());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.ultimaCuota.setText(Persona.getUltimaCuota().getFin().format(formatter));
            holder.diasVencida.setText(String.valueOf(Persona.getUltimaCuota().diasVencida()));
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nombre;
        TextView ultimaCuota;
        TextView diasVencida;

        ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.NombreCompleto);
            itemView.setOnClickListener(this);
            ultimaCuota = itemView.findViewById(R.id.VencimientoUltimaCuota);
            itemView.setOnClickListener(this);
            diasVencida = itemView.findViewById(R.id.DiasVencida);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Persona getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(com.example.gimnasioflex.adapters.ListAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

