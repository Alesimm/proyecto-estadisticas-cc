package com.cc.formaciones.service;

import com.cc.formaciones.entity.Formacion;
import com.cc.formaciones.repository.FormacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormacionService {

    private final FormacionRepository formacionRepository;

    public FormacionService(FormacionRepository formacionRepository) {
        this.formacionRepository = formacionRepository;
    }

    public Formacion crearFormacion(Formacion formacion) {
        int totalJugadores = formacion.getDefensas() + formacion.getMediocampistas() + formacion.getDelanteros();

        if (totalJugadores != 10) {
            throw new IllegalArgumentException("Error Táctico: La formación debe tener exactamente 10 jugadores de campo. Suma actual: " + totalJugadores);
        }

        return formacionRepository.save(formacion);
    }

    public List<Formacion> obtenerTodas() {
        return formacionRepository.findAll();
    }
}