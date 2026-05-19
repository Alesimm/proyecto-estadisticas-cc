CREATE TABLE recomendaciones (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 id_jugador BIGINT NOT NULL UNIQUE,
                                 nombre_jugador VARCHAR(150) NOT NULL,
                                 nota_rendimiento DOUBLE NOT NULL,
                                 minutos_acumulados INT NOT NULL,
                                 sugerencia_tactica VARCHAR(255) NOT NULL,
                                 prioridad VARCHAR(50) NOT NULL
);