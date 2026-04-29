package com.cc.partidosservice.service;

import com.cc.partidosservice.dto.PartidoRequestDTO;
import com.cc.partidosservice.dto.PartidoResponseDTO;
import com.cc.partidosservice.model.Partido;
import com.cc.partidosservice.repository.PartidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartidoService {

    private final PartidoRepository partidoRepository;

    // Metodo privado para transformar de Entidad a DTO (Igual al del profesor)
    private PartidoResponseDTO mapPartido(Partido partido) {
        return new PartidoResponseDTO(
                partido.getId(),
                partido.getRival(),
                partido.getFecha(),
                partido.getEstadio()
        );
    }

    public List<PartidoResponseDTO> obtenerTodos() {
        return partidoRepository.findAll()
                .stream()
                .map(this::mapPartido)
                .collect(Collectors.toList());
    }

    // ESTE ES EL METODO QUE ESTÁ CAUSANDO EL ERROR EN EL CONTROLADOR
    // Ahora recibe un RequestDTO y devuelve un ResponseDTO
    public PartidoResponseDTO guardar(PartidoRequestDTO partidoDTO) {
        Partido partido = new Partido();
        partido.setRival(partidoDTO.getRival());
        partido.setFecha(partidoDTO.getFecha());
        partido.setEstadio(partidoDTO.getEstadio());

        return mapPartido(partidoRepository.save(partido));
    }

    // Método para eliminar un partido por su ID
    public void eliminar(Long id) {
        partidoRepository.deleteById(id);
    }
}