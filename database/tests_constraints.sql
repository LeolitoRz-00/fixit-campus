-- =====================================================================
-- FixIt Campus - tests_constraints.sql
-- Comprueba que la base de datos rechaza datos incoherentes.
-- Ejecutar con --force para que continue tras cada error esperado:
--   mariadb -u root -p --force fixit_campus < database/tests_constraints.sql
-- Todo ocurre dentro de una transaccion que se deshace al final.
-- =====================================================================

USE fixit_campus;
START TRANSACTION;

INSERT INTO edificio (id_edificio, nombre) VALUES (900, 'Edificio test');
INSERT INTO espacio (id_espacio, id_edificio, id_tipo_espacio, planta, nombre)
  VALUES (900, 900, 1, 1, 'Aula test');
INSERT INTO proveedor (id_proveedor, nif, razon_social, especialidad, precio_hora)
  VALUES (900, 'B00000000', 'Proveedor test', 'Electricidad', 30.00);

-- OK: incidencia valida (debe insertarse)
INSERT INTO incidencia (codigo, id_creador, id_espacio, id_categoria, id_prioridad,
                        id_estado, descripcion, fecha_objetivo)
  VALUES ('TEST-0001', 4, 900, 1, 3, 1, 'Enchufe sin corriente', CURDATE());

-- DEBE FALLAR: tecnico y proveedor a la vez (ck_incidencia_asignacion)
INSERT INTO incidencia (codigo, id_creador, id_espacio, id_categoria, id_prioridad,
                        id_estado, id_tecnico, id_proveedor, descripcion, fecha_objetivo)
  VALUES ('TEST-0002', 4, 900, 1, 3, 1, 2, 900, 'Doble asignacion', CURDATE());

-- DEBE FALLAR: descripcion vacia (ck_incidencia_descripcion)
INSERT INTO incidencia (codigo, id_creador, id_espacio, id_categoria, id_prioridad,
                        id_estado, descripcion, fecha_objetivo)
  VALUES ('TEST-0003', 4, 900, 1, 3, 1, '   ', CURDATE());

-- DEBE FALLAR: espacio inexistente (fk_incidencia_espacio)
INSERT INTO incidencia (codigo, id_creador, id_espacio, id_categoria, id_prioridad,
                        id_estado, descripcion, fecha_objetivo)
  VALUES ('TEST-0004', 4, 99999, 1, 3, 1, 'Sin espacio valido', CURDATE());

-- DEBE FALLAR: codigo duplicado (uq_incidencia_codigo)
INSERT INTO incidencia (codigo, id_creador, id_espacio, id_categoria, id_prioridad,
                        id_estado, descripcion, fecha_objetivo)
  VALUES ('TEST-0001', 4, 900, 1, 3, 1, 'Codigo repetido', CURDATE());

-- DEBE FALLAR: email duplicado (uq_usuario_email)
INSERT INTO usuario (id_rol, nombre, email, pass_hash)
  VALUES (3, 'Duplicado', 'admin@fixit.test', 'x');

-- DEBE FALLAR: espacio duplicado en el mismo edificio y planta (uq_espacio_ubicacion)
INSERT INTO espacio (id_edificio, id_tipo_espacio, planta, nombre)
  VALUES (900, 1, 1, 'Aula test');

-- DEBE FALLAR: tipo de historial no permitido (ck_historial_tipo)
INSERT INTO historial_cambio (id_incidencia, id_usuario, tipo)
  SELECT id_incidencia, 2, 'OTRO' FROM incidencia WHERE codigo = 'TEST-0001';

ROLLBACK;
