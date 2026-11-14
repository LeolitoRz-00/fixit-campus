package com.fixitcampus.backend;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Comentario {

    int id_incidencia;
    int id_autor;
    String texto;
    LocalDateTime fecha;

    public Comentario(int  id_incidencia, int id_autor, String texto, LocalDateTime fecha) {

        this.id_incidencia = id_incidencia;
        this.id_autor = id_autor;
        this.texto = texto;
        this.fecha = fecha;

    }

    @Override
    public String toString() {
        return "\nComentario{" +
                "id_incidencia=" + id_incidencia +
                ", id_autor=" + id_autor +
                ", texto='" + texto +
                ", fecha=" + fecha +
                '}';
    }
}
