package com.fixitcampus.backend;

public class Proveedor {

    String nif;
    String razon_social;
    String especialidad;
    String contacto;
    double precio_hora;

    public Proveedor(String nif, String razon_social, String especialidad, String contacto, double precio_hora) {
        // Añade estas 4 líneas para guardar los datos
        this.nif = nif;
        this.razon_social = razon_social;
        this.especialidad = especialidad;
        this.contacto = contacto;
        this.precio_hora = precio_hora;
    }

    @Override
    public String toString() {
        return "Proveedor{" +
                "nif='" + nif + '\'' +
                ", razon_social=" + razon_social +
                ", especialidad=" + especialidad +
                ", contacto=" + contacto +
                ", precio_hora=" + precio_hora +
                '}';
    }
}
