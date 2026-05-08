CREATE TABLE jugadores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    posicion VARCHAR(50) NOT NULL,
    numero_camiseta INT NOT NULL UNIQUE,
    nacionalidad VARCHAR(50) NOT NULL,
    edad INT NOT NULL,
    correo_contacto VARCHAR(150) NOT NULL UNIQUE
);