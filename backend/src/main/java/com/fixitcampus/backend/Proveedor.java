package com.fixitcampus.backend;

public class Proveedor {

    String nif;
    String razon_social;
    String especialidad;
    String contacto;
    double precio_hora;
    boolean activo;

    public Proveedor(String nif, String razon_social, String especialidad, String contacto, double precio_hora, boolean activo) {

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

    public String getNif() {
        return nif;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String getContacto() {
        return contacto;
    }

    public double getPrecio_hora() {
        return precio_hora;
    }

    public boolean isActivo() {
        return activo;
    }
}
