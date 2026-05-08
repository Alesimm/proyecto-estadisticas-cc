package com.cc.partidosservice.repository;

import com.cc.partidosservice.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar las operaciones de base de datos de la entidad Partido.
 */
@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {

    /**
     * Consulta derivada de Spring Data JPA.
     * REGLA DE NEGOCIO (IE 2.2.1): Verifica si ya existe un partido en un estadio y fecha específicos.
     */
    boolean existsByEstadioAndFecha(String estadio, String fecha);
}