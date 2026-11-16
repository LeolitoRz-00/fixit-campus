package com.fixitcampus.backend.logger;

import model.LogMessage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogWriter extends Thread {

    // TRAEMOS ColaLogs PARA PODER TENER LA BANDEJA DE MENSAJES
    private String nombre;
    private ColaLogs cola;
    private String nombreFichero;


    public LogWriter(String nombre, ColaLogs cola, String nombreFichero) {
        this.nombre = nombre;
        this.cola = cola;
        this.nombreFichero = nombreFichero;
    }

    public void run() {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(nombreFichero, true))) {

            while (true) {

                // PARA TOMAR UN MENSAJE Y ESPERAR QUE LLEGUE UNO EN CASO DE NO HABER
                LogMessage mensaje = cola.sacar();

                // SE ESCRIBE EL MENSAJE
                escritor.println(mensaje);

                // PARA QUE SE ESCRIBA DE MANERA FORZADA EN EL DISCO Y NO EN MEMORIA
                escritor.flush();

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            // ESTO NO ES UN ERROR, SOLO ES UNA FORMA DE DECIR QUE SE HA INTERRUMPIDO
            System.out.println("Se ha detenido exitosamente: " + nombre);
        } catch (IOException e) {
            // ESTO SI ES EL MENSAJE QUE SE MUESTRA DE QUE ALGO FALLA
            System.out.println("Error con el fichero: " + e.getMessage());
        }
    }
}
