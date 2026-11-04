package com.fixitcampus.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "prioridad")
public class Prioridad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prioridad")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 30, unique = true)
    private String nombre;

    @Column(name = "plazo_dias_laborables", nullable = false)
    private int plazoDiasLaborables;

    protected Prioridad() {
    }

    public Prioridad(String nombre, int plazoDiasLaborables) {
        this.nombre = nombre;
        this.plazoDiasLaborables = plazoDiasLaborables;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPlazoDiasLaborables() {
        return plazoDiasLaborables;
    }
}
