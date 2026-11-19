package com.fixitcampus.backend.service;

import com.fixitcampus.backend.conexion.ImportadorBBDD;
import com.fixitcampus.backend.constructores.*;
import com.fixitcampus.backend.logger.ColaLogs;
import com.fixitcampus.backend.logger.LogEvento;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;



public class LeerExcel {

    //List<T> nos evita repetir código, ya que transforma en el constructor que esté asignado al nombre de la hoja
    // cuando hagamos la llamada en leerExcel para x hoja, por ejemplo List<Rol> si en nombreHoja ponemos "rol"
    public static <T> List<T> leerHoja(Workbook libro, String nombreHoja, Function<Row, T> transformador) {
        List<T> lista = new ArrayList<>();
        Sheet hoja = libro.getSheet(nombreHoja);
        // Si la hoja no existe en el Excel, avisamos y devolvemos la lista vacía
        if (hoja == null) {
            System.out.println("Aviso: La hoja '" + nombreHoja + "' no existe en el archivo.");
            return lista;
        }
        // El bucle de siempre, pero escrito UNA SOLA VEZ para el proyecto entero
        for (int i = 1; i <= hoja.getLastRowNum(); i++) {
            Row fila = hoja.getRow(i);
            if (fila == null) {
                continue; // Si la fila está vacía, la saltamos
            }
            // Aquí le aplicamos el molde a la fila:
            T objeto = transformador.apply(fila);
            if (objeto != null) {
                lista.add(objeto);
            }
        }
        return lista;
    }

    public static void leerExcel(ColaLogs cola) throws IOException {

        try (FileInputStream archivo = new FileInputStream("data/BBDD.xlsx"); XSSFWorkbook libro =
                new XSSFWorkbook(archivo);) {

            //Leemos la hoja de roles
            List<Rol> roles = leerHoja(libro, "rol", fila -> {
                String nombre = leerTexto(fila, 0);
                return new Rol(nombre);
            });

            //Leemos la hoja de estado
            List<Estado> estados = leerHoja(libro, "estado", fila -> {
                String nombre = leerTexto(fila, 0);
                int orden = leerInt(fila, 1);
                return new Estado(nombre, orden);
            });

            //Leemos la hoja de prioridad
            List<Prioridad> prioridades = leerHoja(libro, "prioridad", fila -> {
                String nombre = leerTexto(fila, 0);
                int plazo_dias_laborables = leerInt(fila, 1);
                return new Prioridad(nombre, plazo_dias_laborables);
            });

            //Leemos la hoja de categoria
            List<Categoria> categorias = leerHoja(libro, "categoria", fila -> {
                String nombre = leerTexto(fila, 0);
                return new Categoria(nombre);
            });

            //Leemos la hoja de categoria
            List<TipoEspacio> tipo_espacios = leerHoja(libro, "tipo_espacio", fila -> {
                String nombre = leerTexto(fila, 0);
                return new TipoEspacio(nombre);
            });

            //Leemos la hoja de categoria
            List<Edificio> edificios = leerHoja(libro, "edificio", fila -> {
                String nombre = leerTexto(fila, 0);
                return new Edificio(nombre);
            });

            //Leemos la hoja de usuario
            List<Usuario> usuarios = leerHoja(libro, "usuario", fila -> {
                int id_rol = leerInt(fila, 0);
                String nombre = leerTexto(fila, 1);
                String email = leerTexto(fila, 2);
                String pass_hash = leerTexto(fila, 3);
                String pass_salt = leerTexto(fila, 4);
                boolean activo = leerBoolean(fila, 5);
                return new Usuario(id_rol, nombre, email, pass_hash, pass_salt, activo);
            });

            //Leemos la hoja de espacio
            List<Espacio> espacios = leerHoja(libro, "espacio", fila -> {
                int id_edificio = leerInt(fila, 0);
                int id_tipo_espacio = leerInt(fila, 1);
                int planta = leerInt(fila, 2);
                String nombre = leerTexto(fila, 3);
                boolean activo = leerBoolean(fila, 4);
                return new Espacio(id_edificio, id_tipo_espacio, planta, nombre, activo);
            });

            //Leemos la hoja de proveedores
            List<Proveedor> proveedores = leerHoja(libro, "proveedor", fila -> {
                String nif = leerTexto(fila, 0);
                String razonSocial = leerTexto(fila, 1);
                String especialidad = leerTexto(fila, 2);
                String contacto = leerTexto(fila, 3);
                double precioHora = leerDouble(fila, 4);
                boolean activo = leerBoolean(fila, 5);
                return new Proveedor(nif, razonSocial, especialidad, contacto, precioHora, activo);
            });

            //Leemos la hoja de incidencias
            List<Incidencia> incidencias = leerHoja(libro, "incidencia", fila -> {

                String codigo = leerTexto(fila, 0);
                int id_creador = leerInt(fila, 1);
                int id_espacio = leerInt(fila, 2);
                int id_categoria = leerInt(fila, 3);
                int id_prioridad = leerInt(fila, 4);
                int id_estado = leerInt(fila, 5);
                Integer id_tecnico = leerInteger(fila, 6);
                Integer id_proveedor = leerInteger(fila, 7);
                String descripcion = leerTexto(fila, 8);
                String imagen = leerTexto(fila, 9);

                boolean riesgo_personas = leerBoolean(fila, 10);
                boolean impide_uso = leerBoolean(fila, 11);
                LocalDateTime fecha_creacion = leerFecha(fila, 12);
                LocalDate fecha_objetivo = leerFecha(fila, 13).toLocalDate();
                LocalDateTime fecha_resolucion = leerFecha(fila, 14);

                boolean facturada = leerBoolean(fila, 15);
                int version = leerInt(fila, 16);
                return new Incidencia(codigo, id_creador, id_espacio, id_categoria, id_prioridad, id_estado, id_tecnico,
                        id_proveedor, descripcion, imagen, riesgo_personas, impide_uso, fecha_creacion,
                        fecha_objetivo, fecha_resolucion, facturada, version);
            });

            //Leemos la hoja de comentarios
            List<Comentario> comentarios = leerHoja(libro, "comentario", fila -> {
                int id_incidencia = leerInt(fila, 0);
                int id_autor = leerInt(fila, 1);
                String texto = leerTexto(fila, 2);
                LocalDateTime fecha = leerFecha(fila, 3);
                return new Comentario(id_incidencia, id_autor, texto, fecha);
            });

            //Leemos la hoja de historial de cambios
            List<HistorialCambio> historiales = leerHoja(libro, "historial_cambio", fila -> {
                int id_incidencia = leerInt(fila, 0);
                int id_usuario = leerInt(fila, 1);
                String tipo = leerTexto(fila, 2);
                String valor_anterior = leerTexto(fila, 3);
                String valor_nuevo = leerTexto(fila, 4);
                LocalDateTime fecha = leerFecha(fila, 5);
                return new HistorialCambio(id_incidencia, id_usuario, tipo, valor_anterior, valor_nuevo, fecha);
            });

            //Leemos la hoja de log de eventos
            List<LogEvento> logs = leerHoja(libro, "log_evento", fila -> {
                int id_usuario = leerInt(fila, 0);
                String accion = leerTexto(fila, 1);
                String entidad = leerTexto(fila, 2);
                String detalle = leerTexto(fila, 3);
                LocalDateTime fecha = leerFecha(fila, 4);
                return new LogEvento(id_usuario, accion, entidad, detalle, fecha);
            });


            ColaLogs colaLogs = null;
            ImportadorBBDD.importarDatos(
                    roles,
                    estados,
                    prioridades,
                    categorias,
                    tipo_espacios,
                    edificios,
                    usuarios,
                    espacios,
                    proveedores,
                    incidencias,
                    comentarios,
                    historiales,
                    logs,
                    cola);

            colaLogs = new ColaLogs();


        }catch (Exception e){

            System.out.println("El archivo excel no se ha podido leer" + " " + e.getMessage());

        }
    }

    /**
     * Lee el texto de una celda de forma 100% segura.
     * Si la celda está vacía o no existe, devuelve una cadena vacía "" en vez de fallar.
     */
    public static String leerTexto(Row fila, int columna) {
        if (fila == null) {
            return "";
        }

        Cell celda = fila.getCell(columna);
        if (celda == null) {
            return "";
        }

        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(celda).trim();
    }

    public static double leerDouble(Row fila, int columna) {
        if (fila == null) return 0.0;
        Cell celda = fila.getCell(columna);
        if (celda == null) return 0.0;

        // Si la celda es numérica directa en Excel, la leemos nativamente:
        if (celda.getCellType() == CellType.NUMERIC) {
            return celda.getNumericCellValue();
        }

        DataFormatter formatter = new DataFormatter();
        String valor = formatter.formatCellValue(celda).trim();
        if (valor.isEmpty()) return 0.0;

        // Reemplazamos la coma por un punto antes de parsear:
        valor = valor.replace(",", ".");
        return Double.parseDouble(valor);
    }

    public static int leerInt(Row fila, int columna) {
        if (fila == null) return 0;
        Cell celda = fila.getCell(columna);
        if (celda == null) return 0;

        DataFormatter formatter = new DataFormatter();
        String valor = formatter.formatCellValue(celda).trim();
        if (valor.isEmpty()) return 0;

        return Integer.parseInt(formatter.formatCellValue(celda).trim());
    }

    public static boolean leerBoolean(Row fila, int columna) {
        if (fila == null) return false;
        Cell celda = fila.getCell(columna);
        if (celda == null) return false;
        DataFormatter formatter = new DataFormatter();
        String valor = formatter.formatCellValue(celda).trim().toLowerCase();
        // Solo aceptamos "true" o "false"
        if (valor.equals("true")) {
            return true;
        } else if (valor.equals("false")) {
            return false;
        } else {
            // ¡AQUÍ FORZAMOS EL ERROR!
            throw new IllegalArgumentException(
                    "Error de formato booleano en la columna " + columna + ": Se encontró '" + valor + "' pero solo se admite 'true' o 'false'."
            );
        }
    }

    public static Integer leerInteger(Row fila, int columna) {
        if (fila == null) return null;
        Cell celda = fila.getCell(columna);
        if (celda == null) return null;

        DataFormatter formatter = new DataFormatter();
        String valor = formatter.formatCellValue(celda).trim();
        if (valor.isEmpty() || valor.equals("0")) return null;

        return Integer.parseInt(valor);
    }

    //Esto me lo ha dado la IA
    public static LocalDateTime leerFecha(Row fila, int columna) {
        if (fila == null) return null;
        Cell celda = fila.getCell(columna);
        if (celda == null) return null;
        try {
            if (celda.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(celda)) {
                return celda.getLocalDateTimeCellValue();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }



}