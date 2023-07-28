package com.codificatus.ayudantemundo.inventario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.codificatus.ayudantemundo.R;
import com.codificatus.ayudantemundo.adaptadores.listaMaterialAdapter;
import com.codificatus.ayudantemundo.clases.materialMenor;
import com.codificatus.ayudantemundo.db.DbMaterial;

import java.util.ArrayList;

public class inventarioActivity extends AppCompatActivity {

    RecyclerView listadoInventarioMaterial;
    ArrayList<materialMenor> listaArrayInventario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        listadoInventarioMaterial = (RecyclerView) findViewById(R.id.listadoInventarioMaterial);

        listadoInventarioMaterial.setLayoutManager(new LinearLayoutManager(this));
        DbMaterial dbMaterial = new DbMaterial(inventarioActivity.this);

        listaArrayInventario = new ArrayList<>();

        listaMaterialAdapter  adapter = new listaMaterialAdapter(dbMaterial.mostrarMaterial());
        listadoInventarioMaterial.setAdapter(adapter);


    }
}