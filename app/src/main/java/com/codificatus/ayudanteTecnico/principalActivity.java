package com.codificatus.ayudanteTecnico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.codificatus.ayudanteTecnico.agregar.agregarEquiposActivity;
import com.codificatus.ayudanteTecnico.agregar.agregarMaterialActivity;
import com.codificatus.ayudanteTecnico.db.DbHelper;
import com.codificatus.ayudanteTecnico.inventario.inventarioActivity;
import com.codificatus.ayudanteTecnico.reportes.reportesActivity;
import com.codificatus.ayudanteTecnico.respaldodb.respaldoDbActivity;

public class principalActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    Button btnEqui;
    Button btnFerre;
    Button btnInventario;
    ImageButton btnSalir;
    ImageButton btnComentarios;

    Button btnReportes;

    TextView txtName;

    String urlTelegram = "https://t.me/CodificatusMorapps";
    public static final String PREF_KEY_USER_ID = "user_id";
    private static final String PREFS_NAME = "MyPrefs"; // Nombre de las preferencias compartidas
    private static final String PREF_FIRST_RUN = "firstRun"; // Clave para el indicador de primera ejecución

    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // Verificar si es la primera ejecución
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isFirstRun = prefs.getBoolean(PREF_FIRST_RUN, true);

        if (isFirstRun) {
            showWelcomePopup();
            // Guardar el indicador de primera ejecución como falso
            prefs.edit().putBoolean(PREF_FIRST_RUN, false).apply();
        }




        checkLocationPermission();

/*
        Button crashButton = new Button(this);
        crashButton.setText("Test Crash");
        crashButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                throw new RuntimeException("Test Crash"); // Force a crash
            }
        });



        addContentView(crashButton, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
*/
        btnEqui = (Button)findViewById(R.id.btnPrinEquipos);

        btnEqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(principalActivity.this, agregarEquiposActivity.class);
                startActivity(i);
            }
        });

        btnFerre = (Button) findViewById(R.id.btnPrinFerreteria);
        btnFerre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(principalActivity.this, agregarMaterialActivity.class);
                startActivity(i);
            }
        });

        btnInventario = (Button) findViewById(R.id.btnInventario);
        btnInventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(principalActivity.this, inventarioActivity.class);
                startActivity(i);
            }
        });



        DbHelper dbHelper = new DbHelper(principalActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db != null){
            Toast.makeText(principalActivity.this, "Iniciado Correctamente", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(principalActivity.this, "Problemas al iniciar Correctamente", Toast.LENGTH_SHORT).show();
        }


        btnSalir = (ImageButton) findViewById(R.id.imgSalir);

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });

        btnComentarios = (ImageButton) findViewById(R.id.imgComentarios);

        btnComentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(principalActivity.this, "Envia tu comentario a traves telegram.", Toast.LENGTH_LONG).show();
                Uri _link = Uri.parse(urlTelegram);

                Intent i = new Intent(Intent.ACTION_VIEW, _link);
                startActivity(i);


            }
        });

        btnReportes = (Button) findViewById(R.id.btnReportes);

        btnReportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(principalActivity.this, reportesActivity.class);
                startActivity(i);
            }
        });

    }

    private void showWelcomePopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View popupView = getLayoutInflater().inflate(R.layout.popup_welcome, null);
        builder.setView(popupView);

        EditText etName = popupView.findViewById(R.id.etName);
        Button btnContinue = popupView.findViewById(R.id.btnContinue);

        AlertDialog dialog = builder.create();
        dialog.show();

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etName.getText().toString();
                dialog.dismiss(); // Cerrar la ventana emergente

                // Ahora puedes usar la variable 'userName' en tu aplicación
            }
        });
    }


    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_backup) {
            Intent i = new Intent(principalActivity.this, respaldoDbActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}