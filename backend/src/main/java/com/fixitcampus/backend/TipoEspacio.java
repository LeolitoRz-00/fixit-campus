package com.fixitcampus.backend;

public class TipoEspacio {

    String nombre;

    public TipoEspacio(String nombre) {

        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Tipo Espacio{" +
                "nombre='" + nombre + '\'' +
                '}';
    }
}
