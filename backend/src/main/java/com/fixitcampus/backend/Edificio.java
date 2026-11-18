package com.fixitcampus.backend;

public class Edificio {

    String nombre;

    public Edificio(String nombre) {

        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "\nEdificio{" +
                "nombre='" + nombre +
                '}';
    }

    public String getNombre() {
        return nombre;
    }
}
