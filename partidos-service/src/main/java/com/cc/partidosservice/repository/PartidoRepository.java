package com.cc.partidosservice.repository;

import com.cc.partidosservice.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Le dice a Spring que este componente maneja la base de datos
public interface PartidoRepository extends JpaRepository<Partido, Long> {
    // JpaRepository ya trae métodos como save(), findAll(), findById(), delete()
}