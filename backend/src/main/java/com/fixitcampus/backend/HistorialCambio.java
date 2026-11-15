package com.fixitcampus.backend;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HistorialCambio {

    int id_incidencia;
    int id_usuario;
    String tipo;
    String valor_anterior;
    String valor_nuevo;
    LocalDateTime fecha;

    public HistorialCambio(int id_incidencia, int id_usuario, String tipo, String valor_anterior, String valor_nuevo,
                           LocalDateTime fecha) {

        this.id_incidencia = id_incidencia;
        this.id_usuario = id_usuario;
        this.tipo = tipo;
        this.valor_anterior = valor_anterior;
        this.valor_nuevo = valor_nuevo;
        this.fecha = fecha;

    }

    @Override
    public String toString() {
        return "\nHistorial Cambio{" +
                "id_incidencia=" + id_incidencia +
                ", id_usuario=" + id_usuario +
                ", tipo='" + tipo +
                ", valor_anterior='" + valor_anterior +
                ", valor_nuevo='" + valor_nuevo +
                ", fecha=" + fecha +
                '}';
    }
}
