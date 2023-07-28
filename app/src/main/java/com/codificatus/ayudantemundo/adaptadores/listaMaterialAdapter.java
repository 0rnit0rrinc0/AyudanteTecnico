package com.codificatus.ayudantemundo.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codificatus.ayudantemundo.R;
import com.codificatus.ayudantemundo.clases.materialMenor;

import java.util.ArrayList;

public class listaMaterialAdapter extends RecyclerView.Adapter<listaMaterialAdapter.materialViewHolder> {

    ArrayList<materialMenor>  listaMaterial;

    public  listaMaterialAdapter(ArrayList<materialMenor> listaMaterial){
        this.listaMaterial = listaMaterial;
    }


    @NonNull
    @Override
    public materialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_inventario, null, false);
        return new materialViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull materialViewHolder holder, int position) {

        holder.viewMaterial.setText(listaMaterial.get(position).getNombre());
        holder.viewCantidad.setText(listaMaterial.get(position).getCantidad());

    }

    @Override
    public int getItemCount() {
        return listaMaterial.size();
    }

    public class materialViewHolder extends RecyclerView.ViewHolder {

        TextView viewMaterial, viewID, viewCantidad;

        public materialViewHolder(@NonNull View itemView) {
            super(itemView);

            viewMaterial = itemView.findViewById(R.id.viewMaterial);
            viewCantidad = itemView.findViewById(R.id.viewCantidad);

        }
    }
}
