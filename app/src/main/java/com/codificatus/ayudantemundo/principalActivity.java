package com.codificatus.ayudantemundo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.codificatus.ayudantemundo.agregar.agregarEquiposActivity;
import com.codificatus.ayudantemundo.agregar.agregarMaterialActivity;
import com.codificatus.ayudantemundo.agregar.agregarPrincipalActivity;
import com.codificatus.ayudantemundo.db.DbHelper;
import com.codificatus.ayudantemundo.descontar.descontarGeneralActivity;
import com.codificatus.ayudantemundo.inventario.inventarioActivity;

import java.net.URI;

public class principalActivity extends AppCompatActivity {

    Button btnEqui;
    Button btnFerre;
    Button btnInventario;
    ImageButton btnSalir;
    ImageButton btnComentarios;

    String urlTelegram = "https://t.me/CodificatusMorapps";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

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




    }
}