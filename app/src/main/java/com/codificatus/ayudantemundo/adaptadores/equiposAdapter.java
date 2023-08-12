package com.codificatus.ayudantemundo.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.codificatus.ayudantemundo.MenuItemClickListener;
import com.codificatus.ayudantemundo.R;
import com.codificatus.ayudantemundo.clases.equipos;

import java.util.List;

public class equiposAdapter extends ArrayAdapter<equipos> {

    private LayoutInflater inflater;
    private MenuItemClickListener clickListener; // Agrega esta variable

    public equiposAdapter(Context context, List<equipos> equiposList) {
        super(context, 0, equiposList);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = inflater.inflate(R.layout.item_equipo, parent, false);
        }

        equipos equipo = getItem(position);

        TextView txtCodigo = itemView.findViewById(R.id.txtCodigo);
        TextView txtTipo = itemView.findViewById(R.id.txtTipo);

        txtCodigo.setText("Código: " + equipo.getCodigoBarras());
        txtTipo.setText("Tipo: " + equipo.getTipo());

        final int currentPosition = position;

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
                popupMenu.inflate(R.menu.context_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (clickListener != null) {
                            clickListener.onMenuItemClick(item.getItemId(), currentPosition);
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            }
        });

        return itemView;
    }

    // Agrega el método para establecer el listener
    public void setMenuItemClickListener(MenuItemClickListener listener) {
        this.clickListener = listener;
    }


}
