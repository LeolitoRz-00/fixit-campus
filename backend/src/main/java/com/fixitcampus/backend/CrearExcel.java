package com.fixitcampus.backend;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CrearExcel {

    public static void crearExcel() {
        try (XSSFWorkbook libro = new XSSFWorkbook()) {

            //Definimos los estilos de fuente y tipo de letra
            CellStyle estilos = libro.createCellStyle();
            Font fuente = libro.createFont();
            fuente.setBold(true);
            fuente.setColor(IndexedColors.BLACK.getIndex());
            estilos.setFont(fuente);

            //Hacemos la lista de todas las hojas que queremos en el libro
            List<String> hojas = List.of("rol", "estado", "prioridad", "categoria", "tipo_espacio", "edificio",
                    "usuario", "espacio", "proveedor", "incidencia", "comentario", "historial_cambio", "log_evento");

            for(int i = 0; i < hojas.size(); i++){
                Sheet sheet = libro.createSheet(hojas.get(i));
            }

            //Creamos los campos de las columnas de cada hoja llamando al metodo creado más abajo.
            crearFilaCabecera(libro, "rol", List.of("nombre"), estilos);
            crearFilaCabecera(libro, "estado", List.of("nombre", "orden"), estilos);
            crearFilaCabecera(libro, "prioridad", List.of("nombre", "plazo_dias_laborables"), estilos);
            crearFilaCabecera(libro, "categoria", List.of("nombre"), estilos);
            crearFilaCabecera(libro, "tipo_espacio", List.of("nombre"), estilos);
            crearFilaCabecera(libro, "edificio", List.of("nombre"), estilos);
            crearFilaCabecera(libro, "usuario", List.of("id_rol", "nombre", "email", "pass_hash", "pass_salt", "activo"), estilos);
            crearFilaCabecera(libro, "espacio", List.of("id_edificio", "id_tipo_espacio", "planta", "nombre", "activo"), estilos);
            crearFilaCabecera(libro, "proveedor", List.of("nif", "razon_social", "especialidad", "contacto", "precio_hora", "activo"), estilos);
            crearFilaCabecera(libro, "incidencia", List.of("codigo", "id_creador", "id_espacio", "id_categoria", "id_prioridad",
                    "id_estado", "id_tecnico", "id_proveedor", "descripcion", "imagen", "riesgo_personas",
                    "impide_uso", "fecha_creacion", "fecha_objetivo", "fecha_resolucion", "facturada",
                    "version"), estilos);
            crearFilaCabecera(libro, "comentario", List.of("id_incidencia", "id_autor", "texto", "fecha"), estilos);
            crearFilaCabecera(libro, "historial_cambio", List.of("id_incidencia", "id_usuario", "tipo", "valor_anterior", "valor_nuevo", "fecha"), estilos);
            crearFilaCabecera(libro, "log_evento", List.of("id_usuario", "accion", "entidad", "detalle", "fecha"), estilos);

            // --- AJUSTE AUTOMÁTICO DE ANCHO DE COLUMNAS ---
            for (Sheet hojaActual : libro) {

                Row primeraFila = hojaActual.getRow(0);
                if (primeraFila != null) {
                    int totalColumnas = primeraFila.getLastCellNum();
                    for (int i = 0; i < totalColumnas; i++) {
                        hojaActual.autoSizeColumn(i);
                    }
                }
            }

            //Escribimos el archivo excel para crearlo
            try (FileOutputStream archivo = new FileOutputStream("BBDD.xlsx")) {
                libro.write(archivo);
                System.out.println("Archivo Excel 'BBDD.xlsx' creado con éxito.");
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo Excel: " + e.getMessage());
        }
    }
    public static void crearFilaCabecera(Workbook libro, String nombreHoja, List<String> cabeceras, CellStyle estilos) {
        Row filaCabecera = libro.getSheet(nombreHoja).createRow(0);

        for (int i = 0; i < cabeceras.size(); i++) {
            Cell c = filaCabecera.createCell(i);
            c.setCellValue(cabeceras.get(i));
            c.setCellStyle(estilos);
        }
    }
}
