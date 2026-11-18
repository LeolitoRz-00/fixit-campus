package com.fixitcampus.backend.constructores;

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
    boolean riesgo_personas;
    //Tu vas a ser un boolean, pero de momento no
    boolean impide_uso;
    LocalDateTime fecha_creacion;
    LocalDate fecha_objetivo;
    LocalDateTime fecha_resolucion;
    //Tu vas a ser un boolean, pero de momento no
    boolean facturada;
    int version;

    public Incidencia(String codigo, int id_creador, int id_espacio, int id_categoria, int id_prioridad, int id_estado,
                      Integer id_tecnico, Integer id_proveedor, String descripcion, String imagen, boolean riesgo_personas,
                      boolean impide_uso, LocalDateTime fecha_creacion, LocalDate fecha_objetivo,
                      LocalDateTime fecha_resolucion, boolean facturada, int version) {

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
        return "\nIncidencia{" +
                "codigo=" + codigo +
                ", id_creador=" + id_creador +
                ", id_espacio=" + id_espacio +
                ", id_categoria=" + id_categoria +
                ", id_prioridad=" + id_prioridad +
                ", id_estado=" + id_estado +
                ", id_tecnico=" + id_tecnico +
                ", id_proveedor=" + id_proveedor +
                ", \ndescripcion='" + descripcion +
                ", imagen='" + imagen +
                ", riesgo_personas='" + riesgo_personas +
                ", impide_uso='" + impide_uso +
                ", fecha_creacion=" + fecha_creacion +
                ", fecha_objetivo=" + fecha_objetivo +
                ", fecha_resolucion=" + fecha_resolucion +
                ", facturada='" + facturada +
                ", version=" + version +
                '}';
    }

    public String getCodigo() {
        return codigo;
    }

    public int getId_creador() {
        return id_creador;
    }

    public int getId_espacio() {
        return id_espacio;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public int getId_prioridad() {
        return id_prioridad;
    }

    public int getId_estado() {
        return id_estado;
    }

    public Integer getId_tecnico() {
        return id_tecnico;
    }

    public Integer getId_proveedor() {
        return id_proveedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public boolean isRiesgo_personas() {
        return riesgo_personas;
    }

    public boolean isImpide_uso() {
        return impide_uso;
    }

    public LocalDateTime getFecha_creacion() {
        return fecha_creacion;
    }

    public LocalDate getFecha_objetivo() {
        return fecha_objetivo;
    }

    public LocalDateTime getFecha_resolucion() {
        return fecha_resolucion;
    }

    public boolean isFacturada() {
        return facturada;
    }

    public int getVersion() {
        return version;
    }
}