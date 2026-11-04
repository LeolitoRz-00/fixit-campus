package com.fixitcampus.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fixitcampus.backend.model.Prioridad;
import com.fixitcampus.backend.repository.EstadoRepository;
import com.fixitcampus.backend.repository.PrioridadRepository;
import com.fixitcampus.backend.repository.RolRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CatalogosRepositoryTest {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private PrioridadRepository prioridadRepository;

    @Test
    void cargaLosCatalogosDelSeed() {
        assertEquals(3, rolRepository.count());
        assertEquals(4, estadoRepository.count());
        assertEquals(4, prioridadRepository.count());
    }

    @Test
    void urgenteTienePlazoDeCeroDias() {
        Prioridad urgente = prioridadRepository.findByNombre("URGENTE").orElseThrow();
        assertEquals(0, urgente.getPlazoDiasLaborables());
        assertTrue(estadoRepository.findByNombre("ABIERTA").isPresent());
    }
}
