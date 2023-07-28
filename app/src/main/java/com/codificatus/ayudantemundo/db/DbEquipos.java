package com.codificatus.ayudantemundo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DbEquipos extends DbHelper{

    Context context;
    public DbEquipos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarEquipos(String codigoBarras, String tipo){
        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("codigoBarras", codigoBarras);
            values.put("tipo", tipo);

            db.insert(TABLE_EQUIPOS, null, values);
        }catch (Exception ex){
            ex.toString();
        }
        return id;
    }

}
