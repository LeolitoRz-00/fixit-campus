package com.fixitcampus.backend.constructores;

public class Prioridad {

    String nombre;
    int plazo_dias_laborables;

    public Prioridad(String nombre,  int plazo_dias_laborables) {

        this.nombre = nombre;
        this.plazo_dias_laborables = plazo_dias_laborables;
    }

    @Override
    public String toString() {
        return "\nPrioridad{" +
                "nombre='" + nombre +
                "plazo_laborables=" + plazo_dias_laborables +
                '}';
    }

    public String getNombre() {
        return nombre;
    }

    public int getPlazo_dias_laborables() {
        return plazo_dias_laborables;
    }
}
