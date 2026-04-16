package com.ccanalytics.jugadores_service.repository;

import com.ccanalytics.jugadores_service.entity.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {
}