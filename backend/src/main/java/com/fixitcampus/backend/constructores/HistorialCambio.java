package com.fixitcampus.backend.constructores;

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

    public int getId_incidencia() {
        return id_incidencia;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public String getValor_anterior() {
        return valor_anterior;
    }

    public String getValor_nuevo() {
        return valor_nuevo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}
