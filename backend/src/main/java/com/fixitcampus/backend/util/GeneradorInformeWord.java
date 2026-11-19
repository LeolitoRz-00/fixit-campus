package com.fixitcampus.backend.util;

import com.fixitcampus.backend.conexion.ConexionBBDD;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.sql.*;
import java.time.LocalDateTime;

public class GeneradorInformeWord {

    public static void generarInforme() {
        String[] tablas = {
                "rol", "estado", "prioridad", "categoria", "tipo_espacio",
                "edificio", "usuario", "espacio", "proveedor", "incidencia",
                "comentario", "historial_cambio", "log_evento"
        };

        try (Connection conn = ConexionBBDD.getConnection();
             XWPFDocument doc = new XWPFDocument();
             FileOutputStream out = new FileOutputStream("data/informe_estado_bbdd.docx")) {

            // 1. Título y fecha
            XWPFParagraph p = doc.createParagraph();
            XWPFRun r = p.createRun();
            r.setText("Informe de Estado de la Base de Datos");
            r.setBold(true);
            r.setFontSize(16);

            XWPFParagraph fecha = doc.createParagraph();
            fecha.createRun().setText("Fecha de ejecución: " + LocalDateTime.now());

            // 2. Tabla resumen en el Word
            XWPFTable tablaDoc = doc.createTable();
            XWPFTableRow cabecera = tablaDoc.getRow(0);
            cabecera.getCell(0).setText("Tabla");
            cabecera.addNewTableCell().setText("Registros Insertados");
            cabecera.addNewTableCell().setText("Estado");

            // 3. Consultar conteo real de cada tabla en la BBDD
            for (String tabla : tablas) {
                int total = 0;
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tabla)) {
                    if (rs.next()) total = rs.getInt(1);
                }

                XWPFTableRow fila = tablaDoc.createRow();
                fila.getCell(0).setText(tabla);
                fila.getCell(1).setText(String.valueOf(total));
                fila.getCell(2).setText(total > 0 ? "OK (Con datos)" : "AVISO (Vacía)");
            }

            // 4. Guardar archivo
            doc.write(out);
            System.out.println("📄 Word generado: informe_estado_bbdd.docx");

        } catch (Exception e) {
            System.err.println("Error generando informe Word: " + e.getMessage());
        }
    }
}