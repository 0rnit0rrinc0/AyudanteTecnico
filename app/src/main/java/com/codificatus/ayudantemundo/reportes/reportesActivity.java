package com.codificatus.ayudantemundo.reportes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codificatus.ayudantemundo.R;
import com.codificatus.ayudantemundo.adaptadores.ReportesAdapter;
import com.codificatus.ayudantemundo.clases.Reporte;
import com.codificatus.ayudantemundo.db.DbReporte;

import java.util.List;

public class reportesActivity extends AppCompatActivity {

    private ListView listViewReportes;
    private ReportesAdapter reportesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);

        listViewReportes = findViewById(R.id.listViewReportes);

        // Obtener la lista de reportes desde la base de datos
        DbReporte dbReporte = new DbReporte(this);
        List<Reporte> reportesList = dbReporte.obtenerTodosLosReportes();

        // Crear el adaptador y asignarlo al ListView
        reportesAdapter = new ReportesAdapter(this, reportesList);
        listViewReportes.setAdapter(reportesAdapter);

        listViewReportes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Reporte reporteSeleccionado = reportesAdapter.getItem(position);
                mostrarUbicacionEnMapa(reporteSeleccionado);
            }
        });
    }

    private void mostrarUbicacionEnMapa(Reporte reporte) {
        double latitud = reporte.getLatitud();
        double longitud = reporte.getLongitud();

        Uri gmmIntentUri = Uri.parse("geo:" + latitud + "," + longitud + "?q=" + latitud + "," + longitud);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

}
