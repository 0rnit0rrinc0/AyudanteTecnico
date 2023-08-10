package com.codificatus.ayudantemundo.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codificatus.ayudantemundo.clases.materialMenor;

import java.util.List;

public class materialAdapter extends ArrayAdapter<materialMenor> {
    private Context context;

    public materialAdapter(Context context, List<materialMenor> materials) {
        super(context, android.R.layout.simple_list_item_1, materials);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        materialMenor material = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText("Nombre: " + material.getNombre() + " | Cantidad: " + material.getCantidad());

        return convertView;
    }
}