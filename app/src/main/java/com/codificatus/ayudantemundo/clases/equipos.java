package com.codificatus.ayudantemundo.clases;

public class equipos {

    private String codigoBarras;
    private String nombre;
    private String tipo;

    public equipos(String codigoBarras, String nombre, String tipo) {
        this.codigoBarras = codigoBarras;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
