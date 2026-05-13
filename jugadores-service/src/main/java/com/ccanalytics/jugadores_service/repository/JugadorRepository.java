package com.ccanalytics.jugadores_service.repository;

import com.ccanalytics.jugadores_service.entity.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {
    // metodos clave para validar que no se repitan los datos
    boolean existsByNumeroCamiseta(Integer numeroCamiseta);

    // buscador simple que ignora mayusculas
    List<Jugador> findByPosicionIgnoreCase(String posicion);
}