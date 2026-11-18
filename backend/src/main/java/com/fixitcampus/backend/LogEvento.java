package com.fixitcampus.backend;

import java.time.LocalDateTime;

public class LogEvento {


    int id_usuario;
    String accion;
    String entidad;
    String detalle;
    LocalDateTime fecha;

    public LogEvento(int id_usuario, String accion, String entidad, String detalle, LocalDateTime fecha) {

        this.id_usuario = id_usuario;
        this.accion = accion;
        this.entidad = entidad;
        this.detalle = detalle;
        this.fecha = fecha;

    }

    @Override
    public String toString() {
        return "\nLogEvento{" +
                ", id_usuario=" + id_usuario +
                ", accion='" + accion +
                ", entidad='" + entidad +
                ", detalle='" + detalle +
                ", fecha=" + fecha +
                '}';
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public String getAccion() {
        return accion;
    }

    public String getEntidad() {
        return entidad;
    }

    public String getDetalle() {
        return detalle;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}
