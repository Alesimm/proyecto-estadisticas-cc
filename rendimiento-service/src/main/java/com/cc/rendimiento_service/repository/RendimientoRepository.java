package com.cc.rendimiento_service.repository;

import com.cc.rendimiento_service.entity.Rendimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RendimientoRepository extends JpaRepository<Rendimiento, Long> {
    Optional<Rendimiento> findByIdJugador(Long idJugador);
}