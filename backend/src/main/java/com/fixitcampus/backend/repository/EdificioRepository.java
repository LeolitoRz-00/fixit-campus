package com.fixitcampus.backend.repository;

import com.fixitcampus.backend.model.Edificio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EdificioRepository extends JpaRepository<Edificio, Integer> {
}
