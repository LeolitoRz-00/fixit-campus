package com.fixitcampus.backend;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class EstructuraCarpetas {

    public static void crearEstructura(){
        //1. Preparación del entorno de trabajo
        //Lista de todas las carpetas necesarias

        List<String> rutasCarpetas = List.of(
                "backend/src/main/java",
                "backend/src/main/resources",
                "backend/src/test/java",
                "analytics/data",
                "web",
                "android-app",
                "facturacion",
                "database",
                "data",
                "design",
                "docs/api",
                "docs/english",
                "docs/digitalizacion",
                "docs/ipe",
                "docs/sostenibilidad"
        );

        //Se crean las carpetas de todas las rutas que se han definido en la lista de arriba

        // Obtenemos la raíz del proyecto (si se ejecuta desde /backend, subimos un nivel)
        File directorioActual = new File("").getAbsoluteFile();
        File raizProyecto = directorioActual.getName().equalsIgnoreCase("backend")
                ? directorioActual.getParentFile()
                : directorioActual;

        for (String ruta : rutasCarpetas) {
            File carpeta = new File(raizProyecto, ruta);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            // Si la carpeta está vacía, creamos un archivo .gitkeep
            File gitkeep = new File(carpeta, ".gitkeep");
            if (!gitkeep.exists() && carpeta.list() != null && carpeta.list().length == 0) {
                try {
                    gitkeep.createNewFile();
                } catch (IOException e) {
                    System.err.println("No se pudo crear .gitkeep en: " + ruta);
                }
            }
        }
        System.out.println("Estructura de carpetas verificada/creada correctamente en: " + raizProyecto.getAbsolutePath());
    }
}
