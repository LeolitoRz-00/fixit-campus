package com.fixitcampus.backend.threads;

import com.fixitcampus.backend.logger.ColaLogs;
import com.fixitcampus.backend.model.LogMessage;
import com.fixitcampus.backend.model.TipoEvento;

/**
 * Genera 5 averías inventadas y las deja en la cola.
 * Hilo con implements Runnable (clase aparte).
 */
public class SimuladorIncidencias implements Runnable {

    private final ColaLogs cola; // cola compartida donde dejamos los mensajes

    public SimuladorIncidencias(ColaLogs cola) {
        this.cola = cola;
    }

    @Override
    public void run() {
        String[] averias = {
                "Fuga de agua en baños de planta 1",
                "Luz fundida en pasillo B",
                "Puerta atascada en aula 5",
                "Aire acondicionado sin funcionar en sala de profesores",
                "Enchufe roto en laboratorio 2"
        };

        for (String averia : averias) {
            cola.agregar(new LogMessage(TipoEvento.INCIDENCIA, averia));
            try {
                Thread.sleep(300); // pausa entre averías
            } catch (InterruptedException e) {
                return; // si nos interrumpen, terminamos
            }
        }
    }
}