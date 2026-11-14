package com.fixitcampus.backend;

public class Proveedor {

    String nif;
    String razon_social;
    String especialidad;
    String contacto;
    double precio_hora;
    //Vas a ser un boolean, pero de momento no porque me das problemas
    String activo;

    public Proveedor(String nif, String razon_social, String especialidad, String contacto, double precio_hora, String activo) {

        this.nif = nif;
        this.razon_social = razon_social;
        this.especialidad = especialidad;
        this.contacto = contacto;
        this.precio_hora = precio_hora;
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "\nProveedor{" +
                "nif='" + nif +
                ", razon_social=" + razon_social +
                ", especialidad=" + especialidad +
                ", contacto=" + contacto +
                ", precio_hora=" + precio_hora +
                ", activo='" + activo +
                '}';
    }
}
