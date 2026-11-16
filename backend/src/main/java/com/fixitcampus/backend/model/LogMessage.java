package com.fixitcampus.backend.model;

public class LogMessage {

    // LAS DEFINIMOS COMO PRIVADOS PARA QUE NO SE PUEDAN CAMBIAR
    private String tipo;
    private String texto;
    private long momento;

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
    public String toString() {
        return "LogMessage -> "
                + "[" + tipo + "] "
                + texto
                + " (" + momento + ")";
    }
}
