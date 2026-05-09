CREATE TABLE estadisticas (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              id_jugador BIGINT NOT NULL UNIQUE,
                              minutos_jugados INT NOT NULL,
                              goles_totales INT NOT NULL,
                              asistencias INT NOT NULL,
                              recuperaciones INT NOT NULL,
                              goles_recibidos INT NOT NULL
);