CREATE TABLE rendimientos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_jugador BIGINT NOT NULL UNIQUE,
    posicion VARCHAR(50) NOT NULL,
    minutos_jugados INT NOT NULL,
    goles_impacto INT NOT NULL,
    recuperaciones INT NOT NULL,
    nota_final DOUBLE NOT NULL
);