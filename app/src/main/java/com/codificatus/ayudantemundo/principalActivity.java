package com.codificatus.ayudantemundo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codificatus.ayudantemundo.agregar.agregarEquiposActivity;
import com.codificatus.ayudantemundo.agregar.agregarPrincipalActivity;
import com.codificatus.ayudantemundo.db.DbHelper;
import com.codificatus.ayudantemundo.descontar.descontarGeneralActivity;
import com.codificatus.ayudantemundo.inventario.inventarioActivity;

public class principalActivity extends AppCompatActivity {

    Button btnAgregar;
    Button btnDescontar;
    Button btnInventario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        btnAgregar = (Button)findViewById(R.id.agregarInventario);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(principalActivity.this, agregarPrincipalActivity.class);
                startActivity(i);
            }
        });

        btnDescontar = (Button) findViewById(R.id.descontarInventario);
        btnDescontar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(principalActivity.this, descontarGeneralActivity.class);
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



    }
}