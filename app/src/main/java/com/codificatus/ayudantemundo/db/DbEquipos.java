package com.codificatus.ayudantemundo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.codificatus.ayudantemundo.clases.equipos;

import java.util.ArrayList;
import java.util.List;

public class DbEquipos extends DbHelper{

    Context context;
    public DbEquipos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarEquipos(String codigoBarras, String tipo) {
        long id = -1;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Verificamos si el registro ya existe antes de intentar insertarlo
            Cursor cursor = db.query(TABLE_EQUIPOS, new String[]{"codigoBarras"}, "codigoBarras = ?", new String[]{codigoBarras}, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                // El registro ya existe, no se realizará la inserción
                cursor.close();
                db.close();
                return id; // Devolvemos -1 para indicar que no se realizó la inserción
            }

            cursor.close(); // Cerramos el cursor antes de continuar

            ContentValues values = new ContentValues();
            values.put("codigoBarras", codigoBarras);
            values.put("tipo", tipo);

            id = db.insert(TABLE_EQUIPOS, null, values);
            db.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return id;
    }

    public boolean eliminar(String codigoBarras) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Verificamos si el registro existe antes de intentar eliminarlo
        Cursor cursor = db.query(TABLE_EQUIPOS, new String[]{"codigoBarras"}, "codigoBarras = ?", new String[]{codigoBarras}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            // Si el registro existe, procedemos a eliminarlo
            int rowsAffected = db.delete(TABLE_EQUIPOS, "codigoBarras = ?", new String[]{codigoBarras});
            cursor.close();
            db.close();

            // Si se eliminó al menos una fila, devuelve true, de lo contrario, devuelve false
            return rowsAffected > 0;
        } else {
            // Si el registro no existe, cerramos los recursos y devolvemos false
            if (cursor != null) {
                cursor.close();
            }
            db.close();
            return false;
        }
    }

    public List<equipos> obtenerTodosLosEquipos() {
        List<equipos> equiposList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EQUIPOS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String codigoBarras = cursor.getString(cursor.getColumnIndex("codigoBarras"));
                String tipo = cursor.getString(cursor.getColumnIndex("tipo"));
                equipos equipo = new equipos(codigoBarras, tipo);
                equiposList.add(equipo);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();

        return equiposList;
    }

    public equipos obtenerEquipoPorCodigo(String codigoBarras) {
        SQLiteDatabase db = this.getReadableDatabase();
        equipos equipo = null;

        Cursor cursor = db.query(TABLE_EQUIPOS, null, "codigoBarras = ?", new String[]{codigoBarras}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String tipo = cursor.getString(cursor.getColumnIndex("tipo"));
            equipo = new equipos(codigoBarras, tipo);
            cursor.close();
        }

        db.close();

        return equipo;
    }



}
