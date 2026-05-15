package com.cc.recomendacionesservice.repository;

import com.cc.recomendacionesservice.entity.Recomendacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecomendacionRepository extends JpaRepository<Recomendacion, Long> {
    Optional<Recomendacion> findByIdJugador(Long idJugador);
}