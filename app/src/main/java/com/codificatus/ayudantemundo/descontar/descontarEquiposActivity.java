package com.codificatus.ayudantemundo.descontar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.codificatus.ayudantemundo.R;
import com.codificatus.ayudantemundo.agregar.agregarEquiposActivity;
import com.codificatus.ayudantemundo.db.DbEquipos;
import com.codificatus.ayudantemundo.escaner.CaptureAct;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

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
                    boolean eliminado = dbEquipos.eliminar(codigoBarras);

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
            AlertDialog.Builder builder = new AlertDialog.Builder(descontarEquiposActivity.this);
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
}