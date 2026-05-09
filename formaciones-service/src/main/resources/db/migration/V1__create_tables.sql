CREATE TABLE formaciones (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             partido_id BIGINT NOT NULL,
                             equipo_id BIGINT NOT NULL,
                             esquema_tactico VARCHAR(15) NOT NULL,
                             director_tecnico VARCHAR(100) NOT NULL, -- Usamos el nombre original de tu código
                             capitan_id BIGINT,
                             asistentes INT,
                             cambios_permitidos INT
);