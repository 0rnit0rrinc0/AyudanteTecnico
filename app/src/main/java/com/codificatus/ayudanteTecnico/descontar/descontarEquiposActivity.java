package com.codificatus.ayudanteTecnico.descontar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.codificatus.ayudanteTecnico.R;
import com.codificatus.ayudanteTecnico.clases.Reporte;
import com.codificatus.ayudanteTecnico.clases.equipos;
import com.codificatus.ayudanteTecnico.db.DbEquipos;
import com.codificatus.ayudanteTecnico.db.DbReporte;
import com.codificatus.ayudanteTecnico.escaner.CaptureAct;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.Date;

public class descontarEquiposActivity extends AppCompatActivity {

    ImageButton btnScanDesc;
    EditText txtBarras;
    Button btnDescEquipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descontar_equipos);

        btnDescEquipo = (Button) findViewById(R.id.btnDescEquipos);
        btnScanDesc = (ImageButton) findViewById(R.id.btnEscanearDescontar);
        txtBarras = (EditText) findViewById(R.id.editCodigoDescontarEquipo);

        btnDescEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigoBarras = txtBarras.getText().toString().trim();

                if (!codigoBarras.isEmpty()) {
                    DbEquipos dbEquipos = new DbEquipos(descontarEquiposActivity.this);
                    equipos equipoEliminado = dbEquipos.obtenerEquipoPorCodigo(codigoBarras);

                    boolean eliminado = dbEquipos.eliminar(codigoBarras);
                    if (ContextCompat.checkSelfPermission(descontarEquiposActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        double latitud = obtenerLatitudActual(); // Reemplaza con el método correcto
                        double longitud = obtenerLongitudActual(); // Reemplaza con el método correcto
                        guardarReporte(codigoBarras, equipoEliminado.getTipo(), latitud, longitud);
                    } else {
                        Toast.makeText(descontarEquiposActivity.this, "Los permisos de ubicación no están concedidos.", Toast.LENGTH_SHORT).show();
                    }

                    if (eliminado) {
                        // La eliminación fue exitosa
                        Toast.makeText(descontarEquiposActivity.this, "Equipo eliminado exitosamente", Toast.LENGTH_SHORT).show();
                        txtBarras.setText(""); // Limpiar el campo de texto
                    } else {
                        // No se pudo eliminar el equipo
                        Toast.makeText(descontarEquiposActivity.this, "No se pudo eliminar el equipo", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // El código de barras está vacío
                    Toast.makeText(descontarEquiposActivity.this, "Por favor, ingrese un código de barras", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnScanDesc.setOnClickListener(view -> {
            scanCode();
        });


    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Presiona el boton de subir volúmen para encender el flash");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null){
            String codigoBarras = result.getContents().toString();
            eliminarEquipo(codigoBarras);
        }
    });

    private void eliminarEquipo(String codigoBarras) {
        if (!codigoBarras.isEmpty()) {
            DbEquipos dbEquipos = new DbEquipos(descontarEquiposActivity.this);
            equipos equipoEliminado = dbEquipos.obtenerEquipoPorCodigo(codigoBarras);
            boolean eliminado = dbEquipos.eliminar(codigoBarras);
            if (eliminado) {
                // La eliminación fue exitosa
                Toast.makeText(descontarEquiposActivity.this, "Equipo eliminado exitosamente", Toast.LENGTH_SHORT).show();

                // Obtén la latitud y longitud actuales
                double latitud = obtenerLatitudActual();
                double longitud = obtenerLongitudActual();

                // Crea un nuevo objeto Reporte y almacénalo en la base de datos de reportes
                guardarReporte(codigoBarras, equipoEliminado.getTipo(), latitud, longitud);
            } else {
                // No se pudo eliminar el equipo
                Toast.makeText(descontarEquiposActivity.this, "No se pudo eliminar el equipo", Toast.LENGTH_SHORT).show();
            }
        } else {
            // El código de barras está vacío
            Toast.makeText(descontarEquiposActivity.this, "Por favor, ingrese un código de barras", Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarReporte(String codigoBarras, String tipoEquipo, double latitud, double longitud) {
        DbReporte dbReporte = new DbReporte(descontarEquiposActivity.this);

        // Crea un nuevo objeto Reporte con los detalles necesarios
        Reporte nuevoReporte = new Reporte(codigoBarras, tipoEquipo, new Date(), latitud, longitud);

        // Inserta el nuevo reporte en la base de datos de reportes
        long idReporte = dbReporte.insertarReporte(nuevoReporte);

        if (idReporte != -1) {
            // El reporte se insertó exitosamente
            Toast.makeText(descontarEquiposActivity.this, "Eliminado exitosamente. Reporte de eliminación registrado exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            // No se pudo insertar el reporte
            Toast.makeText(descontarEquiposActivity.this, "No se pudo registrar el reporte de eliminación", Toast.LENGTH_SHORT).show();
        }
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


}