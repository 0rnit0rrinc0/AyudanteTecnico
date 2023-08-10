package com.codificatus.ayudantemundo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "inventario.db";
    public static final String TABLE_EQUIPOS = "t_equipos";
    public static final String TABLE_MATERIALM = "t_materialMenor";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_CANTIDAD = "cantidad";




    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_EQUIPOS + " (" +
                "codigoBarras TEXT PRIMARY KEY," +
                "tipo TEXT NOT NULL) ");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_MATERIALM + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NOMBRE + " TEXT NOT NULL," +
                COLUMN_CANTIDAD + " INTEGER NOT NULL)");
        insertarDatosIniciales(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MATERIALM + TABLE_EQUIPOS);
        onCreate(sqLiteDatabase);

    }

    private void insertarDatosIniciales(SQLiteDatabase db) {
        String[] nombresMateriales = {
                "Amarras Plasticas (150 mm)",
                "Atenuador RF (20 db)",
                "Cable Drop F.O. Acometida Plana con mensajero",
                "Cable Pin telefonico Interior (2x24)",
                "Cable plano (Preconectorizado)",
                "Cable RCA",
                "Cable RG6 S/P (Blanco 60% Commscope)",
                "Cancamo Metalico (8 mm)",
                "Conector de Campo SC/APC",
                "Conector RG6-Belden",
                "Conector RJ45 CAT6",
                "Ficha identificación amarilla",
                "Grampa DROP Negra",
                "Grampa Negra Cable F.O. DROP (5 mm)",
                "Grampa RG6 (Blanca)",
                "Pasa cable (Blanco)",
                "Pasa cable (Negro)",
                "Roseta Telefonica Simple",
                "Router N2 AC1200 (Dual Band 4 5dbi)",
                "Splitter 2 Way 1GHZ Horizontal 6 KVA",
                "Splitter 3 Way 1GHZ Horizontal 6 KVA",
                "Tarugos Plasticos 8mm",
                "Telefono operaciones",
                "Drop cable Patchcord 90 mts + Adaptador universal",
                "Conector para preconectorizado",
                "APK Mundo GO",
                "Router Wifi SR120A FiberHome"
                // ... añade los otros elementos de la lista aquí
        };

        for (String nombre : nombresMateriales) {
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("cantidad", 0); // Establece la cantidad inicial en 0
            db.insert(TABLE_MATERIALM, null, values);
        }
    }
}
