-- =====================================================================
-- FixIt Campus - schema.sql
-- Motor: MariaDB 10.5+ (los CHECK se aplican desde la 10.2.1)
-- Uso:   mariadb -u root -p < database/schema.sql
-- Es repetible: borra y recrea la base de datos completa.
-- =====================================================================

DROP DATABASE IF EXISTS fixit_campus;
CREATE DATABASE fixit_campus
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
USE fixit_campus;

-- ---------------------------------------------------------------------
-- 1. Catalogos
-- ---------------------------------------------------------------------
CREATE TABLE rol (
  id_rol  INT          NOT NULL AUTO_INCREMENT,
  nombre  VARCHAR(30)  NOT NULL,
  CONSTRAINT pk_rol PRIMARY KEY (id_rol),
  CONSTRAINT uq_rol_nombre UNIQUE (nombre)
) ENGINE=InnoDB;

CREATE TABLE estado (
  id_estado  INT          NOT NULL AUTO_INCREMENT,
  nombre     VARCHAR(30)  NOT NULL,
  orden      INT          NOT NULL,
  CONSTRAINT pk_estado PRIMARY KEY (id_estado),
  CONSTRAINT uq_estado_nombre UNIQUE (nombre),
  CONSTRAINT uq_estado_orden UNIQUE (orden)
) ENGINE=InnoDB;

CREATE TABLE prioridad (
  id_prioridad          INT          NOT NULL AUTO_INCREMENT,
  nombre                VARCHAR(30)  NOT NULL,
  plazo_dias_laborables INT          NOT NULL,
  CONSTRAINT pk_prioridad PRIMARY KEY (id_prioridad),
  CONSTRAINT uq_prioridad_nombre UNIQUE (nombre),
  CONSTRAINT ck_prioridad_plazo CHECK (plazo_dias_laborables >= 0)
) ENGINE=InnoDB;

CREATE TABLE categoria (
  id_categoria  INT          NOT NULL AUTO_INCREMENT,
  nombre        VARCHAR(50)  NOT NULL,
  CONSTRAINT pk_categoria PRIMARY KEY (id_categoria),
  CONSTRAINT uq_categoria_nombre UNIQUE (nombre)
) ENGINE=InnoDB;

CREATE TABLE tipo_espacio (
  id_tipo_espacio  INT          NOT NULL AUTO_INCREMENT,
  nombre           VARCHAR(30)  NOT NULL,
  CONSTRAINT pk_tipo_espacio PRIMARY KEY (id_tipo_espacio),
  CONSTRAINT uq_tipo_espacio_nombre UNIQUE (nombre)
) ENGINE=InnoDB;

CREATE TABLE edificio (
  id_edificio  INT          NOT NULL AUTO_INCREMENT,
  nombre       VARCHAR(50)  NOT NULL,
  CONSTRAINT pk_edificio PRIMARY KEY (id_edificio),
  CONSTRAINT uq_edificio_nombre UNIQUE (nombre)
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- 2. Usuarios, espacios y proveedores
-- ---------------------------------------------------------------------
CREATE TABLE usuario (
  id_usuario  INT           NOT NULL AUTO_INCREMENT,
  id_rol      INT           NOT NULL,
  nombre      VARCHAR(100)  NOT NULL,
  email       VARCHAR(150)  NOT NULL,
  pass_hash   VARCHAR(255)  NOT NULL,
  pass_salt   VARCHAR(64)   NULL COMMENT 'NULL si el algoritmo (BCrypt) ya incluye el salt en el hash',
  activo      BOOLEAN       NOT NULL DEFAULT TRUE,
  CONSTRAINT pk_usuario PRIMARY KEY (id_usuario),
  CONSTRAINT uq_usuario_email UNIQUE (email),
  CONSTRAINT fk_usuario_rol FOREIGN KEY (id_rol) REFERENCES rol (id_rol)
) ENGINE=InnoDB;

CREATE TABLE espacio (
  id_espacio       INT           NOT NULL AUTO_INCREMENT,
  id_edificio      INT           NOT NULL,
  id_tipo_espacio  INT           NOT NULL,
  planta           INT           NOT NULL,
  nombre           VARCHAR(100)  NOT NULL,
  activo           BOOLEAN       NOT NULL DEFAULT TRUE,
  CONSTRAINT pk_espacio PRIMARY KEY (id_espacio),
  CONSTRAINT uq_espacio_ubicacion UNIQUE (id_edificio, planta, nombre),
  CONSTRAINT fk_espacio_edificio FOREIGN KEY (id_edificio) REFERENCES edificio (id_edificio),
  CONSTRAINT fk_espacio_tipo FOREIGN KEY (id_tipo_espacio) REFERENCES tipo_espacio (id_tipo_espacio)
) ENGINE=InnoDB;

CREATE TABLE proveedor (
  id_proveedor  INT            NOT NULL AUTO_INCREMENT,
  nif           VARCHAR(15)    NOT NULL,
  razon_social  VARCHAR(150)   NOT NULL,
  especialidad  VARCHAR(50)    NOT NULL,
  contacto      VARCHAR(150)   NULL,
  precio_hora   DECIMAL(8,2)   NOT NULL,
  activo        BOOLEAN        NOT NULL DEFAULT TRUE,
  CONSTRAINT pk_proveedor PRIMARY KEY (id_proveedor),
  CONSTRAINT uq_proveedor_nif UNIQUE (nif),
  CONSTRAINT ck_proveedor_precio CHECK (precio_hora >= 0)
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- 3. Incidencias
-- ---------------------------------------------------------------------
CREATE TABLE incidencia (
  id_incidencia     INT           NOT NULL AUTO_INCREMENT,
  codigo            VARCHAR(20)   NOT NULL COMMENT 'Ej.: FIX-0031',
  id_creador        INT           NOT NULL,
  id_espacio        INT           NOT NULL,
  id_categoria      INT           NOT NULL,
  id_prioridad      INT           NOT NULL,
  id_estado         INT           NOT NULL,
  id_tecnico        INT           NULL,
  id_proveedor      INT           NULL,
  descripcion       VARCHAR(1000) NOT NULL,
  imagen            VARCHAR(255)  NULL COMMENT 'Ruta relativa de la foto',
  riesgo_personas   BOOLEAN       NOT NULL DEFAULT FALSE,
  impide_uso        BOOLEAN       NOT NULL DEFAULT FALSE,
  fecha_creacion    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_objetivo    DATE          NOT NULL,
  fecha_resolucion  DATETIME      NULL,
  facturada         BOOLEAN       NOT NULL DEFAULT FALSE,
  version           INT           NOT NULL DEFAULT 0,
  CONSTRAINT pk_incidencia PRIMARY KEY (id_incidencia),
  CONSTRAINT uq_incidencia_codigo UNIQUE (codigo),
  CONSTRAINT fk_incidencia_creador   FOREIGN KEY (id_creador)   REFERENCES usuario (id_usuario),
  CONSTRAINT fk_incidencia_espacio   FOREIGN KEY (id_espacio)   REFERENCES espacio (id_espacio),
  CONSTRAINT fk_incidencia_categoria FOREIGN KEY (id_categoria) REFERENCES categoria (id_categoria),
  CONSTRAINT fk_incidencia_prioridad FOREIGN KEY (id_prioridad) REFERENCES prioridad (id_prioridad),
  CONSTRAINT fk_incidencia_estado    FOREIGN KEY (id_estado)    REFERENCES estado (id_estado),
  CONSTRAINT fk_incidencia_tecnico   FOREIGN KEY (id_tecnico)   REFERENCES usuario (id_usuario),
  CONSTRAINT fk_incidencia_proveedor FOREIGN KEY (id_proveedor) REFERENCES proveedor (id_proveedor),
  CONSTRAINT ck_incidencia_asignacion
    CHECK (id_tecnico IS NULL OR id_proveedor IS NULL),
  CONSTRAINT ck_incidencia_descripcion
    CHECK (CHAR_LENGTH(TRIM(descripcion)) > 0)
) ENGINE=InnoDB;

CREATE INDEX idx_incidencia_estado    ON incidencia (id_estado);
CREATE INDEX idx_incidencia_prioridad ON incidencia (id_prioridad);
CREATE INDEX idx_incidencia_espacio   ON incidencia (id_espacio);
CREATE INDEX idx_incidencia_categoria ON incidencia (id_categoria);
CREATE INDEX idx_incidencia_creador   ON incidencia (id_creador);
CREATE INDEX idx_incidencia_tecnico   ON incidencia (id_tecnico);
CREATE INDEX idx_incidencia_proveedor ON incidencia (id_proveedor);
CREATE INDEX idx_incidencia_objetivo  ON incidencia (fecha_objetivo);

-- ---------------------------------------------------------------------
-- 4. Seguimiento y trazabilidad
-- ---------------------------------------------------------------------
CREATE TABLE comentario (
  id_comentario  INT            NOT NULL AUTO_INCREMENT,
  id_incidencia  INT            NOT NULL,
  id_autor       INT            NOT NULL,
  texto          VARCHAR(1000)  NOT NULL,
  fecha          DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT pk_comentario PRIMARY KEY (id_comentario),
  CONSTRAINT fk_comentario_incidencia FOREIGN KEY (id_incidencia) REFERENCES incidencia (id_incidencia),
  CONSTRAINT fk_comentario_autor FOREIGN KEY (id_autor) REFERENCES usuario (id_usuario),
  CONSTRAINT ck_comentario_texto CHECK (CHAR_LENGTH(TRIM(texto)) > 0)
) ENGINE=InnoDB;

CREATE INDEX idx_comentario_incidencia ON comentario (id_incidencia);

CREATE TABLE historial_cambio (
  id_historial_cambio  INT           NOT NULL AUTO_INCREMENT,
  id_incidencia        INT           NOT NULL,
  id_usuario           INT           NOT NULL,
  tipo                 VARCHAR(20)   NOT NULL,
  valor_anterior       VARCHAR(100)  NULL,
  valor_nuevo          VARCHAR(100)  NULL,
  fecha                DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT pk_historial_cambio PRIMARY KEY (id_historial_cambio),
  CONSTRAINT fk_historial_incidencia FOREIGN KEY (id_incidencia) REFERENCES incidencia (id_incidencia),
  CONSTRAINT fk_historial_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario),
  CONSTRAINT ck_historial_tipo CHECK (tipo IN ('ESTADO', 'PRIORIDAD', 'ASIGNACION'))
) ENGINE=InnoDB;

CREATE INDEX idx_historial_incidencia ON historial_cambio (id_incidencia);

CREATE TABLE log_evento (
  id_log_evento  INT           NOT NULL AUTO_INCREMENT,
  id_usuario     INT           NULL,
  accion         VARCHAR(50)   NOT NULL,
  entidad        VARCHAR(50)   NULL,
  detalle        VARCHAR(500)  NULL,
  fecha          DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT pk_log_evento PRIMARY KEY (id_log_evento),
  CONSTRAINT fk_log_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario) ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE INDEX idx_log_fecha ON log_evento (fecha);
