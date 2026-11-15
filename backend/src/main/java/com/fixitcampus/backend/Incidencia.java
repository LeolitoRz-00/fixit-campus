package com.fixitcampus.backend;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Incidencia {

    String codigo;
    int id_creador;
    int id_espacio;
    int id_categoria;
    int id_prioridad;
    int id_estado;
    Integer id_tecnico;
    Integer id_proveedor;
    String descripcion;
    String imagen;
    //Tu vas a ser un boolean, pero de momento no
    String riesgo_personas;
    //Tu vas a ser un boolean, pero de momento no
    String impide_uso;
    LocalDateTime fecha_creacion;
    LocalDate fecha_objetivo;
    LocalDateTime fecha_resolucion;
    //Tu vas a ser un boolean, pero de momento no
    String facturada;
    int version;

    public Incidencia(String codigo, int id_creador, int id_espacio, int id_categoria, int id_prioridad, int id_estado,
                      Integer id_tecnico, Integer id_proveedor, String descripcion, String imagen, String riesgo_personas,
                      String impide_uso, LocalDateTime fecha_creacion, LocalDate fecha_objetivo,
                      LocalDateTime fecha_resolucion, String facturada, int version) {

        this.codigo = codigo;
        this.id_creador = id_creador;
        this.id_espacio = id_espacio;
        this.id_categoria = id_categoria;
        this.id_prioridad = id_prioridad;
        this.id_estado = id_estado;
        this.id_tecnico = id_tecnico;
        this.id_proveedor = id_proveedor;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.riesgo_personas = riesgo_personas;
        this.impide_uso = impide_uso;
        this.fecha_creacion = fecha_creacion;
        this.fecha_objetivo = fecha_objetivo;
        this.fecha_resolucion = fecha_resolucion;
        this.facturada = facturada;
        this.version = version;

    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "codigo=" + codigo +
                ", id_creador=" + id_creador +
                ", id_espacio=" + id_espacio +
                ", id_categoria=" + id_categoria +
                ", id_prioridad=" + id_prioridad +
                ", id_estado=" + id_estado +
                ", id_tecnico=" + id_tecnico +
                ", id_proveedor=" + id_proveedor +
                ", descripcion='" + descripcion + '\'' +
                ", imagen='" + imagen + '\'' +
                ", riesgo_personas='" + riesgo_personas + '\'' +
                ", impide_uso='" + impide_uso + '\'' +
                ", fecha_creacion=" + fecha_creacion +
                ", fecha_objetivo=" + fecha_objetivo +
                ", fecha_resolucion=" + fecha_resolucion +
                ", facturada='" + facturada + '\'' +
                ", version=" + version +
                '}';
    }
}