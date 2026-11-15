package com.fixitcampus.backend;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CrearExcel {

    public static void crearExcel() {
        try (XSSFWorkbook libro = new XSSFWorkbook()) {
            Sheet hoja = libro.createSheet("proveedores");
            CellStyle estilos = libro.createCellStyle();
            Font fuente = libro.createFont();
            fuente.setBold(true);
            fuente.setColor(IndexedColors.BLACK.getIndex());
            estilos.setFont(fuente);

            Row filaCabecera = hoja.createRow(0);
            List<String> cabeceras = List.of("nif", "razon_social", "especialidad", "contacto", "precio_hora");

            for (int i = 0; i < cabeceras.size(); i++) {
                Cell c = filaCabecera.createCell(i);
                c.setCellValue(cabeceras.get(i));
                c.setCellStyle(estilos);
            }

            // --- AJUSTE AUTOMÁTICO DE ANCHO DE COLUMNAS ---
            for (int i = 0; i < cabeceras.size(); i++) {
                hoja.autoSizeColumn(i);
            }

            try (FileOutputStream archivo = new FileOutputStream("proveedores.xlsx")) {
                libro.write(archivo);
                System.out.println("Archivo Excel 'proveedores.xlsx' creado con éxito.");
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo Excel: " + e.getMessage());
        }
    }
}
