package com.cc.recomendacionesservice.repository;

import com.cc.recomendacionesservice.entity.Partido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidoRepository extends JpaRepository<Partido, Long> {

    // Consulta automatica para evitar duplicados exactos
    boolean existsByRivalAndTorneoAndFecha(String rival, String torneo, String fecha);
}