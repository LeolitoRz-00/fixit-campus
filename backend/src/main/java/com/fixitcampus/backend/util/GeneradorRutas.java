package com.fixitcampus.backend.util;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Crea la carpeta de logs de cada ejecución, con la fecha y hora en el nombre.
 */
public class GeneradorRutas {

    // Formato apto para nombres de carpeta (sin ":" ni "/", que Windows no admite)
    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    // Clase solo con métodos estáticos: no se instancia
    private GeneradorRutas() {
    }

    // Crea y devuelve la ruta, por ejemplo "logs/2026-11-18_14-35-12"
    public static String nuevaCarpetaLogs() {
        String ruta = "logs" + File.separator + LocalDateTime.now().format(FORMATO);
        new File(ruta).mkdirs(); // crea "logs" y la subcarpeta si no existen
        return ruta;
    }
}