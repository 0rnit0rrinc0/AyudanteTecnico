package com.codificatus.ayudanteTecnico.clases;

public class equipos {

    private String codigoBarras;
    private String tipo;

    public equipos(String codigoBarras , String tipo) {
        this.codigoBarras = codigoBarras;
        this.tipo = tipo;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
