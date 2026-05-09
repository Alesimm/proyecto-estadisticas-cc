package com.cc.estadisticas_service.repository;

import com.cc.estadisticas_service.model.Estadistica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadisticaRepository extends JpaRepository<Estadistica, Long> {
    boolean existsByIdJugador(Long idJugador);
}