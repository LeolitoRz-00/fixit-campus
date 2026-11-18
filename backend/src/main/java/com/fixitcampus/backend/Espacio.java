package com.fixitcampus.backend;

public class Espacio {

    int id_edificio;
    int id_tipo_espacio;
    int planta;
    String nombre;
    boolean activo;

    public Espacio(int id_edificio, int id_tipo_espacio, int planta, String nombre, boolean activo) {

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

    public int getId_edificio() {
        return id_edificio;
    }

    public int getId_tipo_espacio() {
        return id_tipo_espacio;
    }

    public int getPlanta() {
        return planta;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isActivo() {
        return activo;
    }
}
