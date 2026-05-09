CREATE TABLE formaciones (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             esquema_tactico VARCHAR(20) NOT NULL,
                             estilo_juego VARCHAR(50) NOT NULL,
                             mentalidad VARCHAR(50) NOT NULL,
                             presion INT NOT NULL,
                             linea_defensiva INT NOT NULL,
                             estado VARCHAR(20) NOT NULL
);