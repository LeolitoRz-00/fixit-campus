package com.fixitcampus.backend.threads;

import com.fixitcampus.backend.logger.ColaLogs;
import com.fixitcampus.backend.model.LogMessage;
import com.fixitcampus.backend.model.TipoEvento;

/**
 * Simula la sincronización con Odoo.
 * Crea un hilo usando una LAMBDA (no implementa Runnable).
 * Un interruptor decide si Odoo "funciona" o "está dañado".
 */
public class SimuladorErroresOdoo {

    private final ColaLogs cola;          // cola compartida donde dejamos los mensajes
    private final boolean odooFunciona;   // INTERRUPTOR: true = sin errores, false = con errores

    public SimuladorErroresOdoo(ColaLogs cola, boolean odooFunciona) {
        this.cola = cola;
        this.odooFunciona = odooFunciona;
    }

    // Crea el hilo, pero NO lo arranca: eso lo decide quien lo llame
    public Thread crearHilo() {

        // LAMBDA: la tarea es un Runnable guardado en una variable
        Runnable tarea = () -> {
            for (int i = 1; i <= 3; i++) {

                if (odooFunciona) {
                    // Odoo responde: mensaje normal
                    cola.agregar(new LogMessage(TipoEvento.INCIDENCIA,
                            "Sincronización con Odoo correcta (intento " + i + ")"));
                } else {
                    // Odoo dañado: mensaje de error
                    cola.agregar(new LogMessage(TipoEvento.ERROR,
                            "Fallo de sincronización con Odoo (intento " + i + ")"));
                }

                try {
                    Thread.sleep(400); // pausa entre intentos
                } catch (InterruptedException e) {
                    return; // si nos interrumpen, terminamos
                }
            }
        };

        return new Thread(tarea, "Errores");
    }
}