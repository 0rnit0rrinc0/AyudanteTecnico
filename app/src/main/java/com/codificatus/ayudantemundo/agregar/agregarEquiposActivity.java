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

import com.codificatus.ayudantemundo.R;
import com.codificatus.ayudantemundo.db.DbEquipos;
import com.codificatus.ayudantemundo.escaner.CaptureAct;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class agregarEquiposActivity extends AppCompatActivity {

    Button btnAgregar;
    EditText editCodigo;
    ImageButton btnScan;
    Spinner spinnerTipoEquipos;


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

                DbEquipos dbEquipos = new DbEquipos(agregarEquiposActivity.this);
                dbEquipos.insertarEquipos(editCodigo.getText().toString(), seleccion.toString());
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