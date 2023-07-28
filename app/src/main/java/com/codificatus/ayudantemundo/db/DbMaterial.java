package com.codificatus.ayudantemundo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.codificatus.ayudantemundo.clases.materialMenor;

import java.util.ArrayList;

public class DbMaterial extends DbHelper{

    Context context;

    public DbMaterial(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarMaterial (String nombre, Integer cantidad){
        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("cantidad", cantidad);
        }catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    public ArrayList<materialMenor> mostrarMaterial(){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<materialMenor> listaMaterial = new ArrayList<>();

        materialMenor material = null;
        Cursor cursorMaterial = null;

        cursorMaterial = db.rawQuery("SELECT * FROM " + TABLE_MATERIALM, null);

        if (cursorMaterial.moveToFirst()){
            do {
                material = new materialMenor();
                material.setId(cursorMaterial.getInt(0));
                material.setNombre(cursorMaterial.getString(1));
                material.setCantidad(cursorMaterial.getInt(2));

                listaMaterial.add(material);
            }while (cursorMaterial.moveToNext());
        }

        cursorMaterial.close();
        return listaMaterial;

    }
}
