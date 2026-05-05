package com.cc.partidosservice.repository;

import com.cc.partidosservice.model.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar las operaciones de base de datos de la entidad Torneo.
 */
@Repository
public interface TorneoRepository extends JpaRepository<Torneo, Long> {
}