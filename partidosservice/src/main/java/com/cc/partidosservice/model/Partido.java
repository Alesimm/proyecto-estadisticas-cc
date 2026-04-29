package com.cc.partidosservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // <-- ESTA ES LA CLAVE: Genera automáticamente getRival() y setRival()
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "partidos")
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rival;
    private String fecha;
    private String estadio;
}