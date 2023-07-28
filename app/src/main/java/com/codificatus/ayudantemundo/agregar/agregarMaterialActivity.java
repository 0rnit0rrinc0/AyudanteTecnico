package com.codificatus.ayudantemundo.agregar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.codificatus.ayudantemundo.R;
import com.codificatus.ayudantemundo.db.DbMaterial;

public class agregarMaterialActivity extends AppCompatActivity {

    Spinner spinnerMaterial;
    Button btnAdd;
    EditText editCantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_material);

        btnAdd = (Button) findViewById(R.id.btnAgregarMaterial);
        editCantidad = (EditText) findViewById(R.id.editCantidad);

        spinnerMaterial = (Spinner) findViewById(R.id.spinnerTipoMaterial);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.listadoMaterialMenor, android.R.layout.simple_spinner_item);
        spinnerMaterial.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String seleccion = spinnerMaterial.getSelectedItem().toString();
                String cant = editCantidad.getText().toString();
                int cantAgregar = Integer.parseInt(cant);
                DbMaterial dbMaterial = new DbMaterial(agregarMaterialActivity.this);
                dbMaterial.insertarMaterial(seleccion.toString(), Integer.valueOf(cant.toString()));

            }
        });
    }
}