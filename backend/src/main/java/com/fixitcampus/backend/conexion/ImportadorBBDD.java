package com.fixitcampus.backend.conexion;

import com.fixitcampus.backend.*;
import com.fixitcampus.backend.constructores.*;
import com.fixitcampus.backend.logger.ColaLogs;
import com.fixitcampus.backend.model.LogMessage;
import com.fixitcampus.backend.model.TipoEvento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class ImportadorBBDD {

    public static boolean importarDatos(
            List<Rol> roles,
            List<Estado> estados,
            List<Prioridad> prioridades,
            List<Categoria> categorias,
            List<TipoEspacio> tiposEspacio,
            List<Edificio> edificios,
            List<Usuario> usuarios,
            List<Espacio> espacios,
            List<Proveedor> proveedores,
            List<Incidencia> incidencias,
            List<Comentario> comentarios,
            List<HistorialCambio> historialCambios,
            List<LogEvento> logEventos,
            ColaLogs colaLogs
    ) {
        Connection conn = null;

        try {
            conn = ConexionBBDD.getConnection();


            conn.setAutoCommit(false);

            // 1. INSERTAR ROLES
            String sqlRol = "INSERT INTO rol (nombre) VALUES (?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlRol)) {
                for (Rol r : roles) {
                    stmt.setString(1, r.getNombre());
                    stmt.addBatch(); // Para insertar por lotes de forma rápida
                }
                stmt.executeBatch();
            }

            // 2. INSERTAR ESTADOS
            String sqlEstado = "INSERT INTO estado (nombre, orden) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlEstado)) {
                for (Estado e : estados) {
                    stmt.setString(1, e.getNombre());
                    stmt.setInt(2, e.getOrden());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            //3. INSERTAR PRIORIDADES
            String sqlPrioridad = "INSERT INTO prioridad (nombre, plazo_dias_laborables) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlPrioridad)) {
                for (Prioridad p : prioridades) {
                    stmt.setString(1, p.getNombre());
                    stmt.setInt(2, p.getPlazo_dias_laborables());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }
            //4. INSERTAR CATEGORIAS
            String sqlCategoria = "INSERT INTO categoria (nombre) VALUES (?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlCategoria)) {
                for (Categoria c : categorias) {
                    stmt.setString(1, c.getNombre());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }
            //5. INSERTAR TIPO ESPACIOS
            String sqlTipoEspacio = "INSERT INTO tipo_espacio (nombre) VALUES (?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlTipoEspacio)) {
                for (TipoEspacio t : tiposEspacio) {
                    stmt.setString(1, t.getNombre());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }
            //6. INSERTAR EDIFICIOS
            String sqlEdificio = "INSERT INTO edificio (nombre) VALUES (?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlEdificio)) {
                for (Edificio e : edificios) {
                    stmt.setString(1, e.getNombre());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }
            //7. INSERTAR PROVEEDORES
            String sqlProveedores = "INSERT INTO proveedor (nif, razon_social, especialidad, contacto, precio_hora, " +
                    "activo) values (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlProveedores)) {
                for (Proveedor p : proveedores) {
                    stmt.setString(1, p.getNif());
                    stmt.setString(2,p.getRazon_social());
                    stmt.setString(3,p.getEspecialidad());
                    stmt.setString(4,p.getContacto());
                    stmt.setDouble(5,p.getPrecio_hora());
                    stmt.setBoolean(6,p.isActivo());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }
            // 8. INSERTAR USUARIOS
            String sqlUsuario = "INSERT INTO usuario (id_rol, nombre, email, pass_hash, pass_salt, activo) VALUES " +
                    "(?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlUsuario)) {
                for (Usuario u : usuarios) {
                    stmt.setInt(1, u.getIdRol());
                    stmt.setString(2, u.getNombre());
                    stmt.setString(3, u.getEmail());
                    stmt.setString(4, u.getPasswordHash());
                    stmt.setString(5, u.getPass_salt());
                    stmt.setBoolean(6, u.isActivo());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            //9.  INSERTAR ESPACIOS
            String sqlEspacio = "INSERT INTO espacio (id_edificio, id_tipo_espacio, planta, nombre, activo) values (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlEspacio)) {
                for (Espacio e : espacios) {
                    stmt.setInt(1, e.getId_edificio());
                    stmt.setInt(2, e.getId_tipo_espacio());
                    stmt.setInt(3, e.getPlanta());
                    stmt.setString(4, e.getNombre());
                    stmt.setBoolean(5, e.isActivo());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            //10. INSERTAR INCIDENCIAS
            String sqlIncidencias = "INSERT INTO incidencia (codigo, id_creador, id_espacio, id_categoria, id_prioridad, id_estado, id_tecnico," +
                    "                        id_proveedor, descripcion, imagen, riesgo_personas, impide_uso, fecha_creacion," +
                    "                        fecha_objetivo, fecha_resolucion, facturada, version) values (?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlIncidencias)) {
                for (Incidencia i : incidencias) {
                    stmt.setString(1, i.getCodigo());
                    stmt.setInt(2, i.getId_creador());
                    stmt.setInt(3, i.getId_espacio());
                    stmt.setInt(4, i.getId_categoria());
                    stmt.setInt(5, i.getId_prioridad());
                    stmt.setInt(6, i.getId_estado());
                    if (i.getId_tecnico() != null && i.getId_tecnico() > 0) {
                        stmt.setInt(7, i.getId_tecnico());
                    } else {
                        stmt.setNull(7, java.sql.Types.INTEGER);
                    }

                    if (i.getId_proveedor() != null && i.getId_proveedor() > 0) {
                        stmt.setInt(8, i.getId_proveedor());
                    } else {
                        stmt.setNull(8, java.sql.Types.INTEGER);
                    }
                    stmt.setString(9, i.getDescripcion());
                    stmt.setString(10, i.getImagen());
                    stmt.setBoolean(11, i.isRiesgo_personas());
                    stmt.setBoolean(12, i.isImpide_uso());
                    // Para fecha_creacion (siempre tiene valor)
                    stmt.setTimestamp(13, Timestamp.valueOf(i.getFecha_creacion()));
                    stmt.setDate(14, java.sql.Date.valueOf(i.getFecha_objetivo()));
                    // Para fecha_resolucion (puede ser null)
                    if (i.getFecha_resolucion() != null) {
                        stmt.setTimestamp(15, Timestamp.valueOf(i.getFecha_resolucion()));
                    } else {
                        stmt.setNull(15, java.sql.Types.TIMESTAMP);
                    }
                    stmt.setBoolean(16,i.isFacturada());
                    stmt.setInt(17,i.getVersion());
                    stmt.addBatch();

                }
                stmt.executeBatch();
            }

            //11. INSERTAR COMENTARIOS
            String sqlComentarios = "INSERT INTO comentario (id_incidencia, id_autor, texto, fecha) " +
                    "values (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlComentarios)) {
                for (Comentario c : comentarios) {
                    stmt.setInt(1, c.getId_incidencia());
                    stmt.setInt(2, c.getId_autor());
                    stmt.setString(3, c.getTexto());
                    stmt.setTimestamp(4, Timestamp.valueOf(c.getFecha()));
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }
            //12. INSERTAR HISTORIAL CAMBIOS
            String sqlHistorial = "INSERT INTO historial_cambio (id_incidencia, id_usuario, tipo, valor_anterior, valor_nuevo, fecha) values (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlHistorial)) {
                for (HistorialCambio h  : historialCambios) {
                    stmt.setInt(1, h.getId_incidencia());
                    stmt.setInt(2, h.getId_usuario());
                    stmt.setString(3, h.getTipo());
                    stmt.setString(4, h.getValor_anterior());
                    stmt.setString(5, h.getValor_nuevo());
                    stmt.setTimestamp(6, Timestamp.valueOf(h.getFecha()));
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }
            //13. INSERTAR LOGS DE EVENTOS
            String sqlLogs = "INSERT INTO log_evento (id_usuario, accion, entidad, detalle, fecha) values (?, ?, ?, " +
                    "?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sqlLogs)) {
                for (LogEvento l : logEventos) {
                    stmt.setInt(1, l.getId_usuario());
                    stmt.setString(2, l.getAccion());
                    stmt.setString(3, l.getEntidad());
                    stmt.setString(4, l.getDetalle());
                    stmt.setTimestamp(5, Timestamp.valueOf(l.getFecha()));
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }



            // SI HA IDO BIEN -> CONFIRMAMOS LA TRANSACCIÓN
            conn.commit();
            System.out.println("✅ Importación completada con éxito en la base de datos.");

            if (colaLogs != null) {
                colaLogs.agregar(new LogMessage(TipoEvento.SISTEMA, "Importación completada con éxito desde Excel"));
            }
            return true;

        } catch (Exception e) {
            // 6. SI ALGO FALLA -> DESHACER EL (ROLLBACK)
            System.err.println("❌ Error durante la importación. Realizando ROLLBACK: " + e.getMessage());

            if (conn != null) {
                try {
                    conn.rollback(); // Deja la base de datos exactamente como estaba antes
                    System.err.println("🔄 Rollback ejecutado correctamente. La BBDD no ha quedado corrupta.");
                } catch (SQLException ex) {
                    System.err.println("Error al hacer rollback: " + ex.getMessage());
                }
            }

            // Registramos el error en el sistema de logs de Leo
            if (colaLogs != null) {
                colaLogs.agregar(new LogMessage(TipoEvento.ERROR, "Fallo en importación. Rollback aplicado: " + e.getMessage()));
            }
            return false;

        } finally {
            // 7. SIEMPRE CERRAR LA CONEXIÓN
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error cerrando conexión: " + e.getMessage());
                }
            }
        }
    }
}