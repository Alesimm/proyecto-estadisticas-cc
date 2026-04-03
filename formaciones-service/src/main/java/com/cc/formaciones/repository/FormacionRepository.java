package com.cc.formaciones.repository;

import com.cc.formaciones.entity.Formacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormacionRepository extends JpaRepository<Formacion, Long> {
}