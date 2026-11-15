package com.fixitcampus.backend;

public class Categoria {

    String nombre;

    public Categoria(String nombre) {

        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "\nCategoria{" +
                "nombre='" + nombre +
                '}';
    }
}
