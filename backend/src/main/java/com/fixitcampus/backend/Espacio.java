package com.fixitcampus.backend;

public class Espacio {

    int id_edificio;
    int id_tipo_espacio;
    int planta;
    String nombre;
    //Tu eres un booleano, pero de momento no
    String activo;

    public Espacio(int id_edificio, int id_tipo_espacio, int planta, String nombre, String activo) {

        this.id_edificio = id_edificio;
        this.id_tipo_espacio = id_tipo_espacio;
        this.planta = planta;
        this.nombre = nombre;
        this.activo = activo;

    }

    @Override
    public String toString() {
        return "\nEspacio{" +
                "id_edificio=" + id_edificio +
                ", id_tipo_espacio=" + id_tipo_espacio +
                ", planta=" + planta +
                "nombre='" + nombre +
                "activo='" + activo +
                '}';
    }
}
