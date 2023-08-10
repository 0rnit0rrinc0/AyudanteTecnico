package com.codificatus.ayudantemundo.agregar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.codificatus.ayudantemundo.R;
import com.codificatus.ayudantemundo.db.DbEquipos;
import com.codificatus.ayudantemundo.descontar.descontarEquiposActivity;
import com.codificatus.ayudantemundo.escaner.CaptureAct;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class agregarEquiposActivity extends AppCompatActivity {

    Button btnAgregar;
    EditText editCodigo;
    ImageButton btnScan;
    Spinner spinnerTipoEquipos;
    Button btnDesEq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_equipos);

        //Lector y edit de codigo de barras
        editCodigo = (EditText) findViewById(R.id.editCodigo);
        btnScan = (ImageButton) findViewById(R.id.btnEscanear);

        //Se asigna array al Spinner
        spinnerTipoEquipos = (Spinner) findViewById(R.id.spinnerTipoEquipos);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.listadoTiposEquipos, android.R.layout.simple_spinner_item);
        spinnerTipoEquipos.setAdapter(adapter);

        //Se asigna la funcion a agregar boton
        btnAgregar = (Button) findViewById(R.id.btnAgregarEquipos);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String seleccion = spinnerTipoEquipos.getSelectedItem().toString();
                String codigoBarras = editCodigo.getText().toString().trim();

                if (!codigoBarras.isEmpty()) {
                    DbEquipos dbEquipos = new DbEquipos(agregarEquiposActivity.this);
                    long insertedRowId = dbEquipos.insertarEquipos(codigoBarras, seleccion);

                    if (insertedRowId != -1) {
                        // La inserción fue exitosa
                        Toast.makeText(agregarEquiposActivity.this, "Equipo agregado exitosamente", Toast.LENGTH_SHORT).show();
                        editCodigo.setText(""); // Limpiar el campo de texto
                    } else {
                        // No se pudo insertar el equipo
                        Toast.makeText(agregarEquiposActivity.this, "No se pudo agregar el equipo", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // El código de barras está vacío
                    Toast.makeText(agregarEquiposActivity.this, "Por favor, ingrese un código de barras", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDesEq = (Button) findViewById(R.id.btnDescoEq);
        btnDesEq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigoBarras = editCodigo.getText().toString().trim();

                if (!codigoBarras.isEmpty()) {
                    DbEquipos dbEquipos = new DbEquipos(agregarEquiposActivity.this);
                    boolean eliminado = dbEquipos.eliminar(codigoBarras);

                    if (eliminado) {
                        // La eliminación fue exitosa
                        Toast.makeText(agregarEquiposActivity.this, "Equipo eliminado exitosamente", Toast.LENGTH_SHORT).show();
                        editCodigo.setText(""); // Limpiar el campo de texto
                    } else {
                        // No se pudo eliminar el equipo
                        Toast.makeText(agregarEquiposActivity.this, "No se pudo eliminar el equipo", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // El código de barras está vacío
                    Toast.makeText(agregarEquiposActivity.this, "Por favor, ingrese un código de barras", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnScan.setOnClickListener(view -> {
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
            AlertDialog.Builder builder = new AlertDialog.Builder(agregarEquiposActivity.this);
            builder.setTitle("Codigo: ");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("Copiar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("EditText", result.getContents().toString());
                    clipboard.setPrimaryClip(clip);
                }
            }).show();
        }
    });


/*
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
            AlertDialog.Builder builder = new AlertDialog.Builder(agregarEquiposActivity.this);
            builder.setTitle("Codigo: ");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("Copiar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("EditText", result.getContents().toString());
                    clipboard.setPrimaryClip(clip);
                }
            }).show();
        }
    });

     */
}