package com.codificatus.ayudanteTecnico.agregar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.codificatus.ayudanteTecnico.R;
import com.codificatus.ayudanteTecnico.db.DbMaterial;

public class agregarMaterialActivity extends AppCompatActivity {

    Spinner spinnerMaterial;
    Button btnAdd, btnDesconMa;
    EditText editCantidad;
    private DbMaterial dbMaterial;
    private int nuevaCantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_material);

        btnAdd = (Button) findViewById(R.id.btnAgregarMaterial);
        btnDesconMa = (Button) findViewById(R.id.btnDescontarMa);
        editCantidad = (EditText) findViewById(R.id.editCantidad);

        spinnerMaterial = (Spinner) findViewById(R.id.spinnerTipoMaterial);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.listadoMaterialMenor, android.R.layout.simple_spinner_item);
        spinnerMaterial.setAdapter(adapter);

        dbMaterial = new DbMaterial(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seleccion = spinnerMaterial.getSelectedItem().toString();
                // Llama a la función buscarCantidadPorNombre para obtener la cantidad
                String nombreBuscado = seleccion; // Reemplaza con el nombre que deseas buscar
                int cantidadEncontrada = dbMaterial.buscarCantidadPorNombre(nombreBuscado);

                if (cantidadEncontrada != -1) {
                    // Aquí puedes usar la cantidad encontrada en tu lógica al hacer clic en el botón
                    // Por ejemplo, mostrarla en un TextView o realizar alguna acción.
                    // Puedes mostrarla en un Toast como ejemplo:
                    //Toast.makeText(agregarMaterialActivity.this, "Cantidad: " + cantidadEncontrada, Toast.LENGTH_SHORT).show();
                } else {
                    // El elemento no fue encontrado, maneja esta situación según tu necesidad.
                    Toast.makeText(agregarMaterialActivity.this, "Material no encontrado", Toast.LENGTH_SHORT).show();
                }

                String cantidadStr = editCantidad.getText().toString();
                int nuevaCantidad = Integer.parseInt(cantidadStr);

                int total = cantidadEncontrada + nuevaCantidad;

                dbMaterial.actualizarCantidadPorNombre(nombreBuscado, total);
                // Mostrar un mensaje de éxito
                Toast.makeText(agregarMaterialActivity.this, "Cantidad actualizada " + total, Toast.LENGTH_SHORT).show();
            }
        });

        btnDesconMa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String seleccion = spinnerMaterial.getSelectedItem().toString();
                // Llama a la función buscarCantidadPorNombre para obtener la cantidad
                String nombreBuscado = seleccion; // Reemplaza con el nombre que deseas buscar
                int cantidadEncontrada = dbMaterial.buscarCantidadPorNombre(nombreBuscado);

                if (cantidadEncontrada != -1) {
                    // Aquí puedes usar la cantidad encontrada en tu lógica al hacer clic en el botón
                    // Por ejemplo, mostrarla en un TextView o realizar alguna acción.
                    // Puedes mostrarla en un Toast como ejemplo:
                    //Toast.makeText(agregarMaterialActivity.this, "Cantidad: " + cantidadEncontrada, Toast.LENGTH_SHORT).show();
                } else {
                    // El elemento no fue encontrado, maneja esta situación según tu necesidad.
                    Toast.makeText(agregarMaterialActivity.this, "Material no encontrado", Toast.LENGTH_SHORT).show();
                }

                String cantidadStr = editCantidad.getText().toString();
                int nuevaCantidad = Integer.parseInt(cantidadStr);

                if (cantidadEncontrada < nuevaCantidad) {
                    Toast.makeText(agregarMaterialActivity.this, "No existe cantidad disponibles de recursos para descontar", Toast.LENGTH_SHORT).show();
                } else {
                    int total = cantidadEncontrada - nuevaCantidad;

                    dbMaterial.actualizarCantidadPorNombre(nombreBuscado, total);
                    // Mostrar un mensaje de éxito
                    Toast.makeText(agregarMaterialActivity.this, "Cantidad actualizada: " + total, Toast.LENGTH_SHORT).show();
                }


            }
        });

        /*

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String seleccion = spinnerMaterial.getSelectedItem().toString();
                String cant = editCantidad.getText().toString();
                if (cant.isEmpty()) {
                    Toast.makeText(agregarMaterialActivity.this, "Por favor, ingresa una cantidad.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int cantidadExistente = DbMaterial.consultarCantidadPorNombre(seleccion);
                if (cantidadExistente == -1) {
                    Toast.makeText(agregarMaterialActivity.this, "El material no existe en la base de datos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int cantSumar = Integer.parseInt(cant);
                if (cantSumar <= 0) {
                    Toast.makeText(agregarMaterialActivity.this, "La cantidad debe ser mayor que cero.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int nuevaCantidad = cantidadExistente + cantSumar;
                DbMaterial.actualizarCantidadPorNombre(seleccion, nuevaCantidad);

                Toast.makeText(agregarMaterialActivity.this, "Cantidad actualizada exitosamente.", Toast.LENGTH_SHORT).show();
                editCantidad.setText(""); // Limpia el campo después de actualizar
            }
        });*/
    }
}