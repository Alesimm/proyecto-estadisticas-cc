package com.cc.formaciones.service;

import com.cc.formaciones.dto.FormacionDTO;
import com.cc.formaciones.entity.Formacion;
import com.cc.formaciones.repository.FormacionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j // Anotación para activar los logs estructurados
@Service
public class FormacionService {

    private final FormacionRepository formacionRepository;

    public FormacionService(FormacionRepository formacionRepository) {
        this.formacionRepository = formacionRepository;
    }

    public FormacionDTO crearFormacion(FormacionDTO dto) {
        log.info("Iniciando creación de la formación: {}", dto.getNombre());

        // 1. Validación de negocio
        int totalJugadores = dto.getDefensas() + dto.getMediocampistas() + dto.getDelanteros();

        if (totalJugadores != 10) {
            log.error("Error Táctico: La suma de jugadores es {}, se esperaban 10", totalJugadores);
            throw new IllegalArgumentException("Error Táctico: La formación debe tener exactamente 10 jugadores de campo. Suma actual: " + totalJugadores);
        }

        // 2. Mapeo DTO -> Entidad
        Formacion entidad = new Formacion();
        entidad.setNombre(dto.getNombre());
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setDefensas(dto.getDefensas());
        entidad.setMediocampistas(dto.getMediocampistas());
        entidad.setDelanteros(dto.getDelanteros());

        // 3. Guardar en BD
        Formacion guardada = formacionRepository.save(entidad);
        log.info("Formación guardada exitosamente en BD con ID: {}", guardada.getId());

        // 4. Actualizar ID y devolver DTO
        dto.setId(guardada.getId());
        return dto;
    }

    public List<FormacionDTO> obtenerTodas() {
        log.info("Consultando todas las formaciones en la base de datos");
        return formacionRepository.findAll().stream().map(entidad -> {
            FormacionDTO dto = new FormacionDTO();
            dto.setId(entidad.getId());
            dto.setNombre(entidad.getNombre());
            dto.setDescripcion(entidad.getDescripcion());
            dto.setDefensas(entidad.getDefensas());
            dto.setMediocampistas(entidad.getMediocampistas());
            dto.setDelanteros(entidad.getDelanteros());
            return dto;
        }).collect(Collectors.toList());
    }
}