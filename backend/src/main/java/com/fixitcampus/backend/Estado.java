package com.fixitcampus.backend;

public class Estado {

    String nombre;
    int orden;

    public Estado(String nombre, int orden) {

        this.nombre = nombre;
        this.orden = orden;
    }

    @Override
    public String toString() {
        return "\nEstado{" +
                "nombre='" + nombre +
                ", orden=" + orden +
                '}';
    }
}
