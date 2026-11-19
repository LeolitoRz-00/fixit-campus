package com.fixitcampus.backend.threads;

import com.fixitcampus.backend.logger.ColaLogs;
import com.fixitcampus.backend.model.LogMessage;
import com.fixitcampus.backend.model.TipoEvento;

public class AvisoSistema {

    private final ColaLogs cola;

    public AvisoSistema(ColaLogs cola) {
        this.cola = cola;
    }

    public Thread crearHilo() {
        // CLASE ANÓNIMA: una clase sin nombre que implementa Runnable al vuelo
        Runnable tarea = new Runnable() {
            private int avisosEnviados = 0; // atributo propio, algo que la lambda no puede tener

            @Override
            public void run() {
                for (int i = 1; i <= 2; i++) {
                    avisosEnviados++;
                    cola.agregar(new LogMessage(TipoEvento.INCIDENCIA,
                            "Aviso del sistema: revisión periódica (aviso " + avisosEnviados + ")"));
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        };
        return new Thread(tarea, "Aviso");
    }
}