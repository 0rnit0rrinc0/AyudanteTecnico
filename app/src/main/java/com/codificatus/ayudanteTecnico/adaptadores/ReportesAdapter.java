package com.codificatus.ayudanteTecnico.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codificatus.ayudanteTecnico.R;
import com.codificatus.ayudanteTecnico.clases.Reporte;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class ReportesAdapter extends ArrayAdapter<Reporte> {
    private LayoutInflater inflater;
    private Context context; // Agrega esta variable

    public ReportesAdapter(Context context, List<Reporte> reportes) {
        super(context, 0, reportes);
        inflater = LayoutInflater.from(context);
        this.context = context; // Inicializa la variable con el contexto recibido
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = inflater.inflate(R.layout.item_reporte, parent, false);
        }

        Reporte reporte = getItem(position);

        TextView txtCodigoBarras = itemView.findViewById(R.id.txtCodigoBarras);
        TextView txtTipoEquipo = itemView.findViewById(R.id.txtTipoEquipo);
        TextView txtFechaHora = itemView.findViewById(R.id.txtFechaHora);
        MapView mapView = itemView.findViewById(R.id.mapView);

        txtCodigoBarras.setText(reporte.getCodigoBarras());
        txtTipoEquipo.setText(reporte.getTipoEquipo());
        txtFechaHora.setText(reporte.getFechaHora().toString());

        mapView.onCreate(null);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng ubicacion = new LatLng(reporte.getLatitud(), reporte.getLongitud());
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(ubicacion));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15));

                // Habilita la interacción del usuario con el mapa
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getUiSettings().setZoomGesturesEnabled(true);
                googleMap.getUiSettings().setScrollGesturesEnabled(true);
                googleMap.getUiSettings().setRotateGesturesEnabled(true);
            }
        });

        return itemView;
    }

    // Elimina este método, ya que no es necesario aquí
}
