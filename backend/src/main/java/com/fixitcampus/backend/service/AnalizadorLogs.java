package com.fixitcampus.backend.service;

import com.fixitcampus.backend.model.TipoEvento;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Cuenta cuántos eventos hay de cada tipo en la carpeta de logs de una ejecución.
 * Es un Callable: como un Runnable, pero devuelve un resultado.
 */
public class AnalizadorLogs implements Callable<Map<String, Integer>> {

    private static final String[] TIPOS = {
            TipoEvento.INCIDENCIA,
            TipoEvento.FACTURA,
            TipoEvento.ERROR
    };

    private final String carpeta;

    public AnalizadorLogs(String carpeta) {
        this.carpeta = carpeta;
    }

    @Override
    public Map<String, Integer> call() throws IOException {

        // LinkedHashMap mantiene el orden en que insertamos los tipos
        Map<String, Integer> conteo = new LinkedHashMap<>();

        for (String tipo : TIPOS) {
            String ruta = carpeta + File.separator + tipo.toLowerCase() + ".log";
            int lineas = 0;

            // Cada línea del fichero es un evento de ese tipo
            try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {
                while (lector.readLine() != null) {
                    lineas++;
                }
            }
            conteo.put(tipo, lineas);
        }
        return conteo;
    }
}