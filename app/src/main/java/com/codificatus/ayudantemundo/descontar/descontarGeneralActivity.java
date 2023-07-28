package com.codificatus.ayudantemundo.descontar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codificatus.ayudantemundo.R;

public class descontarGeneralActivity extends AppCompatActivity {

    Button btnDescEquipos;
    Button btnDescMaterial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descontar_general);

        btnDescEquipos = (Button) findViewById(R.id.btnDescontarEquipos);
        btnDescEquipos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(descontarGeneralActivity.this, descontarEquiposActivity.class);
                startActivity(i);
            }
        });

        btnDescMaterial = (Button) findViewById(R.id.btnDescontarMaterial);
        btnDescMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(descontarGeneralActivity.this, descontarMaterialActivity.class);
                startActivity(i);
            }
        });

    }
}