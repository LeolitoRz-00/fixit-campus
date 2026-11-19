package com.fixitcampus.backend.model;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LogMessage {

    // Formato de la fecha en el log: 18/11/2026 14:35:12
    // withZone() usa la zona horaria del equipo, para poder formatear un Instant directamente
    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                    .withZone(ZoneId.systemDefault());

    // 'final': una vez creado, el mensaje no se puede modificar
    private final String tipo;
    private final String texto;
    private final long momento; // se guarda en milisegundos y se formatea al mostrarlo

    public LogMessage(String tipo, String texto) {
        this.tipo = tipo;
        this.texto = texto;
        this.momento = System.currentTimeMillis();
    }

    // NUESTROS GETTERS PARA PODER LEERLOS DESDE OTROS LUGARES
    public long getMomento() {
        return momento;
    }

    public String getTexto() {
        return texto;
    }

    public String getTipo() {
        return tipo;
    }

    // MOSTRAR COMO SE VERA EL MENSAJE
    @Override
    public String toString() {
        return "LogMessage -> "
                + "[" + tipo + "] "
                + texto
                + " (" + FORMATO_FECHA.format(Instant.ofEpochMilli(momento)) + ")";
    }
}