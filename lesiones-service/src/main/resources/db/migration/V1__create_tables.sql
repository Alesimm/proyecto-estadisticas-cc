CREATE TABLE lesiones (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     id_jugador BIGINT NOT NULL,
     tipo_lesion VARCHAR(100) NOT NULL,
     grado_gravedad INT NOT NULL,
     fecha_lesion VARCHAR(20) NOT NULL,
     dias_recuperacion INT NOT NULL,
     estado_medico VARCHAR(50) NOT NULL
);