package com.fixitcampus.backend.logger;

import com.fixitcampus.backend.model.LogMessage;

import java.util.ArrayList;

public class ColaLogs {

    // LA IDEA DE QUE SEA UN ARRAY ES QUE SEA COMO UNA BANDEJA DE ENTRADA
    // DONDE SE GUARDARAN LOS MENSAJES QUE NO SE HAN ESCRITO
    private ArrayList<LogMessage> mensajes = new ArrayList<>();

    // SE USA synchronized PARA QUE SOLO 1 HILO ACCEDA A LA VEZ
    // SE QUEDA ESPERANDO HASTA QUE TERMINE DE ESCRIBIR Y PASA OTRO
    public synchronized void agregar (LogMessage mensaje) {

        // ENTRA EL MENSAJE A NUESTRA "BANDEJA DE ENTRADA"
        mensajes.add(mensaje);
        notify(); // PARA AVISAR AL HILO QUE ALGO HA LLEGADO
    }

    // TAMBIEN ESTA EN synchronized PARA QUE NO SE PUEDA ACCEDER A
    // SACAR EL MISMO MENSAJE Y EVITAR QUE SE REPITA
    public synchronized LogMessage sacar() throws InterruptedException {
        while (mensajes.isEmpty()) {
            wait(); // NUESTRO HILO SE QUEDA ESPERANDO MIENTRAS
            // LA BANDEJA DE ENTRADA NO TENGA NADA
        }

        // SE DEVUELVE EL MENSAJE QUE HAYA LLEGADO Y SE ELIMINA
        return mensajes.remove(0);
    }

    public synchronized int pendientes() {
        return mensajes.size(); // REVISA CUANTOS MENSAJES QUEDAN POR ESCRIBIR
    }
}
