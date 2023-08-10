package com.codificatus.ayudantemundo.clases;

public class materialMenor {

    private String nombre;
    private int cantidad;

    public materialMenor(String nombre, int cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
