package com.codificatus.ayudantemundo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.codificatus.ayudantemundo.clases.Reporte;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbReporte {
    private DbHelper dbHelper;

    public DbReporte(Context context) {
        dbHelper = new DbHelper(context);
    }

    public long insertarReporte(Reporte reporte) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_CODIGO_BARRAS, reporte.getCodigoBarras());
        values.put(DbHelper.COLUMN_TIPO_EQUIPO, reporte.getTipoEquipo());
        values.put(DbHelper.COLUMN_FECHA_HORA, reporte.getFechaHora().getTime());
        values.put(DbHelper.COLUMN_LATITUD, reporte.getLatitud());
        values.put(DbHelper.COLUMN_LONGITUD, reporte.getLongitud());
        long id = db.insert(DbHelper.TABLE_REPORTES, null, values);
        db.close();
        if (id != -1) {
            // El reporte se insertó correctamente
            Log.d("DbReporte", "Reporte insertado correctamente. ID: " + id);
        } else {
            // Hubo un error al insertar el reporte
            Log.d("DbReporte", "Error al insertar el reporte en la base de datos");
        }

        return id;
    }

    public List<Reporte> obtenerTodosLosReportes() {
        List<Reporte> reportesList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columnas = {
                DbHelper.COLUMN_ID,
                DbHelper.COLUMN_CODIGO_BARRAS,
                DbHelper.COLUMN_TIPO_EQUIPO,
                DbHelper.COLUMN_FECHA_HORA,
                DbHelper.COLUMN_LATITUD,
                DbHelper.COLUMN_LONGITUD
        };

        Cursor cursor = db.query(DbHelper.TABLE_REPORTES, columnas, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(DbHelper.COLUMN_ID));
                String codigoBarras = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_CODIGO_BARRAS));
                String tipoEquipo = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_TIPO_EQUIPO));
                long fechaHoraMillis = cursor.getLong(cursor.getColumnIndex(DbHelper.COLUMN_FECHA_HORA));
                double latitud = cursor.getDouble(cursor.getColumnIndex(DbHelper.COLUMN_LATITUD));
                double longitud = cursor.getDouble(cursor.getColumnIndex(DbHelper.COLUMN_LONGITUD));
                Date fechaHora = new Date(fechaHoraMillis);

                Reporte reporte = new Reporte(codigoBarras, tipoEquipo, fechaHora, latitud, longitud);
                reportesList.add(reporte);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();

        return reportesList;
    }
}
