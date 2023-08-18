package com.codificatus.ayudanteTecnico.clases;


import java.util.Date;

public class Reporte {
    private String codigoBarras;
    private String tipoEquipo;
    private Date fechaHora;
    private double latitud;
    private double longitud;

    public Reporte(String codigoBarras, String tipoEquipo, Date fechaHora, double latitud, double longitud) {
        this.codigoBarras = codigoBarras;
        this.tipoEquipo = tipoEquipo;
        this.fechaHora = fechaHora;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public String getTipoEquipo() {
        return tipoEquipo;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
}
