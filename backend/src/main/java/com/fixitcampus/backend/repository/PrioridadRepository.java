package com.fixitcampus.backend.repository;

import com.fixitcampus.backend.model.Prioridad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PrioridadRepository extends JpaRepository<Prioridad, Integer> {

    Optional<Prioridad> findByNombre(String nombre);
}
