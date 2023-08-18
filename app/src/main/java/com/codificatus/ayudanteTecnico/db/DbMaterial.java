package com.codificatus.ayudanteTecnico.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.codificatus.ayudanteTecnico.clases.materialMenor;

import java.util.ArrayList;
import java.util.List;

public class DbMaterial extends DbHelper {

    Context context;

    public DbMaterial(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public int buscarCantidadPorNombre(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();
        int cantidad = -1; // Valor por defecto en caso de no encontrar el elemento

        String[] projection = {COLUMN_CANTIDAD};
        String selection = COLUMN_NOMBRE + " = ?";
        String[] selectionArgs = {nombre};

        Cursor cursor = db.query(
                TABLE_MATERIALM,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            cantidad = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CANTIDAD));
        }

        cursor.close();
        return cantidad;
    }

    public void actualizarCantidadPorNombre(String nombre, int nuevaCantidad) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CANTIDAD, nuevaCantidad);

        String whereClause = COLUMN_NOMBRE + " = ?";
        String[] whereArgs = {nombre};

        db.update(TABLE_MATERIALM, values, whereClause, whereArgs);
    }

    public List<materialMenor> obtenerTodosLosMateriales() {
        List<materialMenor> listaMateriales = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {COLUMN_NOMBRE, COLUMN_CANTIDAD};
        Cursor cursor = db.query(
                TABLE_MATERIALM,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE));
            int cantidad = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CANTIDAD));
            materialMenor material = new materialMenor(nombre, cantidad);
            listaMateriales.add(material);
        }

        cursor.close();
        return listaMateriales;
    }

    // ... (otros métodos y código)

}









