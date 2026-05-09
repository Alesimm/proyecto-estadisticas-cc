CREATE TABLE estadisticas (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              jugador_id BIGINT NOT NULL,
                              partido_id BIGINT NOT NULL,
                              goles INT DEFAULT 0,
                              asistencias INT DEFAULT 0,
                              intercepciones INT DEFAULT 0,
                              recuperaciones INT DEFAULT 0,
                              atajadas INT DEFAULT 0
);