package com.cc.formaciones.service;

import com.cc.formaciones.dto.FormacionRequestDTO;
import com.cc.formaciones.dto.FormacionResponseDTO;
import com.cc.formaciones.model.Formacion;
import com.cc.formaciones.repository.FormacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FormacionService {

    private final FormacionRepository repository;

    @Transactional
    public FormacionResponseDTO crear(FormacionRequestDTO dto) {
        log.info("Procesando nueva formacion tactica: {}", dto.getEsquemaTactico());

        if ("Defensiva".equalsIgnoreCase(dto.getMentalidad()) && dto.getLineaDefensiva() > 5) {
            log.error("Inconsistencia detectada: Mentalidad defensiva con linea alta ({})", dto.getLineaDefensiva());
            throw new IllegalArgumentException("No tiene sentido tactico usar mentalidad Defensiva con una linea defensiva mayor a 5");
        }

        Formacion formacion = Formacion.builder()
                .esquemaTactico(dto.getEsquemaTactico())
                .estiloJuego(dto.getEstiloJuego())
                .mentalidad(dto.getMentalidad())
                .presion(dto.getPresion())
                .lineaDefensiva(dto.getLineaDefensiva())
                .estado(dto.getEstado())
                .build();

        Formacion guardada = repository.save(formacion);
        log.info("Formacion guardada en base de datos con ID: {}", guardada.getId());

        return mapearResponse(guardada);
    }

    @Transactional(readOnly = true)
    public List<FormacionResponseDTO> listar() {
        log.info("Consultando todas las formaciones registradas");
        return repository.findAll().stream().map(this::mapearResponse).collect(Collectors.toList());
    }

    private FormacionResponseDTO mapearResponse(Formacion entidad) {
        return FormacionResponseDTO.builder()
                .id(entidad.getId())
                .esquemaTactico(entidad.getEsquemaTactico())
                .estiloJuego(entidad.getEstiloJuego())
                .mentalidad(entidad.getMentalidad())
                .presion(entidad.getPresion())
                .lineaDefensiva(entidad.getLineaDefensiva())
                .estado(entidad.getEstado())
                .build();
    }
}