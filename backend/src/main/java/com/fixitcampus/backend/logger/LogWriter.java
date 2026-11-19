package com.fixitcampus.backend.logger;

import com.fixitcampus.backend.model.LogMessage;
import com.fixitcampus.backend.model.TipoEvento;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LogWriter extends Thread {

    // Contador compartido por TODOS los LogWriter (static): mensajes ya escritos
    private static final AtomicInteger escritos = new AtomicInteger(0);

    // Candado compartido: evita que dos LogWriter mezclen líneas en el mismo fichero
    private static final Object CANDADO_FICHEROS = new Object();

    // Un fichero por cada tipo de evento
    private static final String[] TIPOS = {
            TipoEvento.INCIDENCIA,
            TipoEvento.FACTURA,
            TipoEvento.ERROR
    };

    public static int getEscritos() {
        return escritos.get();
    }

    private final String nombre;
    private final ColaLogs cola;
    private final String carpeta; // carpeta de esta ejecución

    public LogWriter(String nombre, ColaLogs cola, String carpeta) {
        super(nombre); // le pone nombre al hilo
        this.nombre = nombre;
        this.cola = cola;
        this.carpeta = carpeta;
    }

    @Override
    public void run() {
        // tipo -> fichero donde se escribe (true = modo append, no borra lo anterior)
        Map<String, PrintWriter> ficheros = new HashMap<>();

        try {


            // Abrimos los 3 ficheros: incidencia.log, factura.log y error.log
            for (String tipo : TIPOS) {
                String ruta = carpeta + File.separator + tipo.toLowerCase() + ".log";
                ficheros.put(tipo, new PrintWriter(new FileWriter(ruta, true)));
            }

            while (true) {

                // PARA TOMAR UN MENSAJE Y ESPERAR QUE LLEGUE UNO EN CASO DE NO HABER
                LogMessage mensaje = cola.sacar();

                // ELEGIMOS EL FICHERO SEGÚN EL TIPO (si el tipo no existe, va a error.log)
                PrintWriter destino = ficheros.get(mensaje.getTipo());
                if (destino == null) {
                    destino = ficheros.get(TipoEvento.ERROR);
                }

                // Solo un LogWriter a la vez escribe en los ficheros
                synchronized (CANDADO_FICHEROS) {
                    destino.println(mensaje);
                    destino.flush(); // se escribe en el disco y no se queda en memoria
                }

                escritos.incrementAndGet(); // suma 1 de forma atómica

                Thread.sleep(1000); // fuera del candado, para no frenar al otro escritor
            }
        } catch (InterruptedException e) {
            // ESTO NO ES UN ERROR, SOLO ES UNA FORMA DE DECIR QUE SE HA INTERRUMPIDO
            System.out.println("Se ha detenido exitosamente: " + nombre);
        } catch (IOException e) {
            // ESTO SÍ ES UN ERROR: no se pudo abrir o escribir algún fichero
            System.out.println("Error con el fichero: " + e.getMessage());
        } finally {
            // Cerramos los ficheros pase lo que pase
            for (PrintWriter fichero : ficheros.values()) {
                fichero.close();
            }
        }
    }
}