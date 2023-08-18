package com.codificatus.ayudanteTecnico.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NOMBRE = "inventario.db";
    public static final String TABLE_EQUIPOS = "t_equipos";
    public static final String TABLE_MATERIALM = "t_materialMenor";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_CANTIDAD = "cantidad";
    public static final String TABLE_REPORTES = "reportes";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CODIGO_BARRAS = "codigo_barras";
    public static final String COLUMN_TIPO_EQUIPO = "tipo_equipo";
    public static final String COLUMN_FECHA_HORA = "fecha_hora";
    public static final String COLUMN_LATITUD = "latitud";
    public static final String COLUMN_LONGITUD = "longitud";


    public DbHelper(Context context) {
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

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_REPORTES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CODIGO_BARRAS + " TEXT NOT NULL, " +
                COLUMN_TIPO_EQUIPO + " TEXT NOT NULL, " +
                COLUMN_FECHA_HORA + " DATETIME NOT NULL, " +
                COLUMN_LATITUD + " REAL, " +
                COLUMN_LONGITUD + " REAL)");
        insertarDatosIniciales(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MATERIALM);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORTES);
        onCreate(sqLiteDatabase);
    }


    private void insertarDatosIniciales(SQLiteDatabase db) {
        String[] nombresMateriales = {
                "Amarras Plásticas (150 mm)",
                "Atenuador RF (20 dB)",
                "Cable Drop F.O. Acometida Plana con Mensajero",
                "Cable Pin Telefónico Interior (2x24)",
                "Cable Plano (Preconectorizado)",
                "Cable RCA",
                "Cable RG6 S/P (Blanco 60% CommScope)",
                "Cáncamo Metálico (8 mm)",
                "Conector de Campo SC/APC",
                "Conector RG6-Belden",
                "Conector RJ45 CAT6",
                "Ficha Identificación Amarilla",
                "Grampa DROP Negra",
                "Grampa Negra Cable F.O. DROP (5 mm)",
                "Grampa RG6 (Blanca)",
                "Pasa Cable (Blanco)",
                "Pasa Cable (Negro)",
                "Roseta Telefónica Simple",
                "Router N2 AC1200 (Dual Band 4 5dBi)",
                "Splitter 2 Way 1GHz Horizontal 6 kVA",
                "Splitter 3 Way 1GHz Horizontal 6 kVA",
                "Tarugos Plásticos 8 mm",
                "Teléfono Operaciones",
                "Drop Cable Patchcord 90 mts + Adaptador Universal",
                "Conector para Preconectorizado",
                "APK Mundo GO",
                "Router WiFi SR120A FiberHome"
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
