package com.ccanalytics.jugadores_service.repository;

import com.ccanalytics.jugadores_service.entity.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {

    boolean existsByNumeroCamiseta(Integer numeroCamiseta);

    // busca por posicion dando igual si lo escriben en mayuscula o minuscula
    List<Jugador> findByPosicionIgnoreCase(String posicion);
}