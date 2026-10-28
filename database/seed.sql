-- =====================================================================
-- FixIt Campus - seed.sql
-- Carga inicial de catalogos y usuarios de prueba (datos ficticios).
-- Ejecutar DESPUES de schema.sql:
--   mariadb -u root -p fixit_campus < database/seed.sql
-- =====================================================================

USE fixit_campus;

INSERT INTO rol (id_rol, nombre) VALUES
  (1, 'ADMIN'),
  (2, 'TECNICO'),
  (3, 'USUARIO');

INSERT INTO estado (id_estado, nombre, orden) VALUES
  (1, 'ABIERTA',  1),
  (2, 'EN CURSO', 2),
  (3, 'RESUELTA', 3),
  (4, 'CERRADA',  4);

INSERT INTO prioridad (id_prioridad, nombre, plazo_dias_laborables) VALUES
  (1, 'URGENTE', 0),
  (2, 'ALTA',    1),
  (3, 'MEDIA',   3),
  (4, 'BAJA',   10);

INSERT INTO categoria (id_categoria, nombre) VALUES
  (1, 'Electricidad'),
  (2, 'Fontaneria'),
  (3, 'Climatizacion'),
  (4, 'Informatica'),
  (5, 'Mobiliario'),
  (6, 'Persianas y ventanas'),
  (7, 'Pintura y obra'),
  (8, 'Otros');

INSERT INTO tipo_espacio (id_tipo_espacio, nombre) VALUES
  (1, 'AULA'),
  (2, 'LABORATORIO'),
  (3, 'TALLER'),
  (4, 'ASEO'),
  (5, 'ZONA COMUN');

-- ---------------------------------------------------------------------
-- Usuarios de prueba (todos con la contrasena de demo: Fixit2026!)
-- Hash BCrypt (coste 10): el salt va incluido en el propio hash,
-- por eso pass_salt queda a NULL. Cambiar en cualquier uso real.
-- ---------------------------------------------------------------------
INSERT INTO usuario (id_usuario, id_rol, nombre, email, pass_hash, pass_salt, activo) VALUES
  (1, 1, 'Admin Demo',   'admin@fixit.test',    '$2b$10$Zk3QmP9vLw2XnR5tYb8HcuKrBlqex6V4nYiCjW2XP1KpkfuyL2hUO', NULL, TRUE),
  (2, 2, 'Tecnico Uno',  'tecnico1@fixit.test', '$2b$10$Ae7TgD1sFh4JkL6oNq0WxeGVatkuXDSJxb1rrRG0aY2ecNSL0QZcm', NULL, TRUE),
  (3, 2, 'Tecnico Dos',  'tecnico2@fixit.test', '$2b$10$Mn2BvC5xZa8SdF1gHj4KlORUmoQf4d/xL665UMvMhCvFI0FszOkFC', NULL, TRUE),
  (4, 3, 'Usuario Demo', 'usuario1@fixit.test', '$2b$10$Qw9ErT3yUi6OpA0sDf7Gh.AN5R56Cr3hXTHTYAhi7/id8pxBP1O6i', NULL, TRUE);

-- Espacios, proveedores e incidencias llegan desde los ficheros de data/
-- mediante la importacion (o desde las aplicaciones).
