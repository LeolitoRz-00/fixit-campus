package com.fixitcampus.backend;

public class Rol {

    String nombre;

    public Rol(String nombre) {

        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "\nRol{" +
                "nombre='" + nombre + 
                '}';
    }

    public String getNombre() {
        return nombre;
    }
}
