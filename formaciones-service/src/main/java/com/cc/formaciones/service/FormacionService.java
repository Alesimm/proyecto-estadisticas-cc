package com.cc.formaciones.service;

import com.cc.formaciones.dto.FormacionRequestDTO;
import com.cc.formaciones.dto.FormacionResponseDTO;
import com.cc.formaciones.entity.Formacion;
import com.cc.formaciones.repository.FormacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FormacionService {

    private final FormacionRepository formacionRepository;

    public FormacionResponseDTO registrarFormacion(FormacionRequestDTO requestDTO) {
        log.info("Iniciando registro de nueva formación para el equipo ID: {}", requestDTO.getEquipoId());

        validarEsquemaTactico(requestDTO.getEsquemaTactico());

        Formacion nuevaFormacion = Formacion.builder()
                .partidoId(requestDTO.getPartidoId())
                .equipoId(requestDTO.getEquipoId())
                .esquemaTactico(requestDTO.getEsquemaTactico())
                .directorTecnico(requestDTO.getDirectorTecnico())
                .build();

        Formacion guardada = formacionRepository.save(nuevaFormacion);
        log.info("Formación registrada exitosamente con ID: {}", guardada.getId());

        return mapearAResponseDTO(guardada, "Formación registrada con éxito");
    }

    public List<FormacionResponseDTO> obtenerTodas() {
        log.info("Consultando todas las formaciones en la base de datos");
        return formacionRepository.findAll().stream()
                .map(formacion -> mapearAResponseDTO(formacion, "OK"))
                .collect(Collectors.toList());
    }

    public FormacionResponseDTO obtenerPorId(Long id) {
        log.info("Buscando formación con ID: {}", id);
        Formacion formacion = formacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la formación con el ID: " + id));
        return mapearAResponseDTO(formacion, "OK");
    }

    public FormacionResponseDTO actualizarFormacion(Long id, FormacionRequestDTO requestDTO) {
        log.info("Actualizando formación con ID: {}", id);
        validarEsquemaTactico(requestDTO.getEsquemaTactico());

        Formacion formacionExistente = formacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la formación con el ID: " + id + " para actualizar"));

        formacionExistente.setPartidoId(requestDTO.getPartidoId());
        formacionExistente.setEquipoId(requestDTO.getEquipoId());
        formacionExistente.setEsquemaTactico(requestDTO.getEsquemaTactico());
        formacionExistente.setDirectorTecnico(requestDTO.getDirectorTecnico());

        Formacion actualizada = formacionRepository.save(formacionExistente);
        log.info("Formación ID {} actualizada correctamente", id);

        return mapearAResponseDTO(actualizada, "Formación actualizada con éxito");
    }

    public void eliminarFormacion(Long id) {
        log.warn("Solicitud para eliminar la formación con ID: {}", id);
        Formacion formacion = formacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se puede eliminar. Formación no encontrada con ID: " + id));

        formacionRepository.delete(formacion);
        log.info("Formación ID {} eliminada exitosamente", id);
    }

    // Regla de negocio interna
    private void validarEsquemaTactico(String esquema) {
        if (!esquema.contains("-")) {
            log.error("Esquema táctico inválido intentado: {}", esquema);
            throw new IllegalArgumentException("El esquema táctico debe contener guiones (Ej: 4-3-3)");
        }
    }

    private FormacionResponseDTO mapearAResponseDTO(Formacion formacion, String estado) {
        return FormacionResponseDTO.builder()
                .id(formacion.getId())
                .partidoId(formacion.getPartidoId())
                .equipoId(formacion.getEquipoId())
                .esquemaTactico(formacion.getEsquemaTactico())
                .directorTecnico(formacion.getDirectorTecnico())
                .estado(estado)
                .build();
    }
}