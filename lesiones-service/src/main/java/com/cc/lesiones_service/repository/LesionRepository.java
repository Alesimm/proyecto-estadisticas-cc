package com.cc.lesiones_service.repository;

import com.cc.lesiones_service.model.Lesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LesionRepository extends JpaRepository<Lesion, Long> {
    boolean existsByIdJugadorAndEstadoMedico(Long idJugador, String estadoMedico);
}