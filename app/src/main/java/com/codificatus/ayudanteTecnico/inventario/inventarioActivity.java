package com.codificatus.ayudanteTecnico.inventario;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.codificatus.ayudanteTecnico.MenuItemClickListener;
import com.codificatus.ayudanteTecnico.R;
import com.codificatus.ayudanteTecnico.adaptadores.equiposAdapter;
import com.codificatus.ayudanteTecnico.adaptadores.materialAdapter;
import com.codificatus.ayudanteTecnico.clases.Reporte;
import com.codificatus.ayudanteTecnico.clases.equipos;
import com.codificatus.ayudanteTecnico.clases.materialMenor;
import com.codificatus.ayudanteTecnico.db.DbEquipos;
import com.codificatus.ayudanteTecnico.db.DbMaterial;
import com.codificatus.ayudanteTecnico.db.DbReporte;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class inventarioActivity extends AppCompatActivity implements MenuItemClickListener {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private DbEquipos dbEquipos;
    private ListView listViewEquipos;
    private equiposAdapter equiposAdapter;
    private List<equipos> equiposList;
    private DbMaterial dbMaterial;
    private ListView listViewMaterial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        dbMaterial = new DbMaterial(this);
        //ListView de MaterialMenor
        listViewMaterial = findViewById(R.id.listViewMaterial);

        // Obtén todos los materiales menores de la base de datos
        List<materialMenor> listaMateriales = dbMaterial.obtenerTodosLosMateriales();
        // Crea un adaptador personalizado y úsalo para el ListView
        materialAdapter adapter = new materialAdapter(this, listaMateriales);
        // Asigna el adaptador al ListView
        listViewMaterial.setAdapter(adapter);

        dbEquipos = new DbEquipos(this);

        // Obtén todos los equipos de la base de datos
        equiposList = dbEquipos.obtenerTodosLosEquipos();

        // Inicializa el ListView y el adaptador
        listViewEquipos = findViewById(R.id.listViewEquipos);
        equiposAdapter = new equiposAdapter(this, equiposList);
        equiposAdapter.setMenuItemClickListener(this); // Establecer el listener
        listViewEquipos.setAdapter(equiposAdapter);
        actualizarListaEquipos(); // Agregar esta línea para mostrar la cantidad de equipos por tipo

        // Configura el listener para el long click en los equipos
        listViewEquipos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                equipos equipoSeleccionado = equiposList.get(position);
                mostrarDialogoConfirmacion(equipoSeleccionado);
                return true;
            }
        });
    }

    @Override
    public void onMenuItemClick(int itemId, int position) {
        if (itemId == R.id.menuE) {
            equipos equipoSeleccionado = equiposList.get(position);
            eliminarEquipo(equipoSeleccionado);
        }
    }

    private void mostrarDialogoConfirmacion(final equipos equipo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar equipo");
        builder.setMessage("¿Estás seguro de que deseas eliminar este equipo?");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                eliminarEquipo(equipo);
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }


    private void eliminarEquipo(equipos equipo) {
        String codigoBarras = equipo.getCodigoBarras();

        // Elimina el equipo de la base de datos de equipos
        dbEquipos.eliminar(codigoBarras);

        // Actualiza la lista de equipos y notifica al adaptador
        equiposList.remove(equipo);
        equiposAdapter.notifyDataSetChanged();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Obtén la latitud y longitud actuales
            double latitud = obtenerLatitudActual();
            double longitud = obtenerLongitudActual();

            // Crea un nuevo objeto Reporte y almacénalo en la base de datos de reportes
            Reporte reporte = new Reporte(codigoBarras, "Tipo de Equipo", new Date(), latitud, longitud);
            DbReporte dbReporte = new DbReporte(this);
            long reporteId = dbReporte.insertarReporte(reporte);

            if (reporteId != -1) {
                // El reporte se insertó correctamente
                Log.d("EliminarEquipo", "Reporte insertado correctamente. ID: " + reporteId);
            } else {
                // Hubo un errorManager al insertar el reporte
                Log.d("EliminarEquipo", "Error al insertar el reporte en la base de datos");
            }
        } else {
            Toast.makeText(this, "Los permisos de ubicación no están concedidos.", Toast.LENGTH_SHORT).show();
        }
    }


    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Si el permiso no está concedido, solicítalo
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            // El permiso ya está concedido, puedes continuar con la obtención de la ubicación
            // o cualquier otra operación relacionada con la ubicación
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, puedes continuar con la obtención de la ubicación
                // o cualquier otra operación relacionada con la ubicación
            } else {
                // Permiso denegado, muestra un mensaje o realiza acciones apropiadas
            }
        }
    }
    private double obtenerLatitudActual() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                return location.getLatitude();
            }
        }
        return 0.0; // Valor predeterminado si no se puede obtener la ubicación
    }

    private double obtenerLongitudActual() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                return location.getLongitude();
            }
        }
        return 0.0; // Valor predeterminado si no se puede obtener la ubicación
    }

    private void actualizarListaEquipos() {
        Map<String, Integer> tiposEquiposMap = new HashMap<>();

        // Contar la cantidad de equipos por tipo
        for (equipos equipo : equiposList) {
            String tipo = equipo.getTipo();
            tiposEquiposMap.put(tipo, tiposEquiposMap.getOrDefault(tipo, 0) + 1);
        }

        // Crear una lista de equipos con cantidades en los tipos
        List<equipos> equiposConCantidades = new ArrayList<>();
        for (equipos equipo : equiposList) {
            String tipo = equipo.getTipo();
            int cantidad = tiposEquiposMap.getOrDefault(tipo, 0);
            String tipoConCantidad = tipo + " (" + cantidad + ")";
            equipo.setTipo(tipoConCantidad);
            equiposConCantidades.add(equipo);
        }

        // Actualizar el adaptador con la lista de equipos con cantidades en los tipos
        equiposAdapter.clear();
        equiposAdapter.addAll(equiposConCantidades);
        equiposAdapter.notifyDataSetChanged();
    }
}


