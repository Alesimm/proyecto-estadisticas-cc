package com.cc.reportes_service.repository;

import com.cc.reportes_service.entity.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
}