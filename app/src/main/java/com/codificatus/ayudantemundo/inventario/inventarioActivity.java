package com.codificatus.ayudantemundo.inventario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codificatus.ayudantemundo.MenuItemClickListener;
import com.codificatus.ayudantemundo.R;
import com.codificatus.ayudantemundo.adaptadores.equiposAdapter;
import com.codificatus.ayudantemundo.adaptadores.materialAdapter;
import com.codificatus.ayudantemundo.clases.equipos;
import com.codificatus.ayudantemundo.clases.materialMenor;
import com.codificatus.ayudantemundo.db.DbEquipos;
import com.codificatus.ayudantemundo.db.DbMaterial;

import java.util.List;

public class inventarioActivity extends AppCompatActivity implements MenuItemClickListener {

    private DbEquipos dbEquipos;
    private ListView listViewEquipos;
    private equiposAdapter equiposAdapter;
    private List<equipos> equiposList;
    private DbMaterial dbMaterial;
    private ListView listViewMaterial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        dbMaterial = new DbMaterial(this);
        //ListView de MaterialMenor
        listViewMaterial = findViewById(R.id.listViewMaterial);

        // Obtén todos los materiales menores de la base de datos
        List<materialMenor> listaMateriales = dbMaterial.obtenerTodosLosMateriales();
        // Crea un adaptador personalizado y úsalo para el ListView
        materialAdapter adapter = new materialAdapter(this, listaMateriales);
        // Asigna el adaptador al ListView
        listViewMaterial.setAdapter(adapter);

        dbEquipos = new DbEquipos(this);

        // Obtén todos los equipos de la base de datos
        equiposList = dbEquipos.obtenerTodosLosEquipos();

        // Inicializa el ListView y el adaptador
        listViewEquipos = findViewById(R.id.listViewEquipos);
        equiposAdapter = new equiposAdapter(this, equiposList);
        equiposAdapter.setMenuItemClickListener(this); // Establecer el listener
        listViewEquipos.setAdapter(equiposAdapter);

        // Configura el listener para el long click en los equipos
        listViewEquipos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                equipos equipoSeleccionado = equiposList.get(position);
                mostrarDialogoConfirmacion(equipoSeleccionado);
                return true;
            }
        });
    }

    @Override
    public void onMenuItemClick(int itemId, int position) {
        if (itemId == R.id.menuE) {
            equipos equipoSeleccionado = equiposList.get(position);
            eliminarEquipo(equipoSeleccionado);
        }
    }

    private void mostrarDialogoConfirmacion(final equipos equipo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar equipo");
        builder.setMessage("¿Estás seguro de que deseas eliminar este equipo?");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                eliminarEquipo(equipo);
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void eliminarEquipo(equipos equipo) {
        dbEquipos.eliminar(equipo.getCodigoBarras());
        equiposList.remove(equipo);
        equiposAdapter.notifyDataSetChanged();
    }
}
