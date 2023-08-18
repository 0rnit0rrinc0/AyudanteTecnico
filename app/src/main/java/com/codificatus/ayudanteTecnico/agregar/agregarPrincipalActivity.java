package com.codificatus.ayudanteTecnico.agregar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codificatus.ayudanteTecnico.R;

public class agregarPrincipalActivity extends AppCompatActivity {

    Button btnEquiposAgregarPrin;
    Button btnMaterialMenor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_principal);

        //Asignación y evento del Boton EQUIPOS de la ventana principal.
        btnEquiposAgregarPrin = (Button) findViewById(R.id.btnAgregarEquiposPrin);
        btnEquiposAgregarPrin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(agregarPrincipalActivity.this, agregarEquiposActivity.class);
                startActivity(i);
            }
        });

        btnMaterialMenor = (Button) findViewById(R.id.btnMaterialMenor);
        btnMaterialMenor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(agregarPrincipalActivity.this, agregarMaterialActivity.class);
                startActivity(i);
            }
        });

    }
}