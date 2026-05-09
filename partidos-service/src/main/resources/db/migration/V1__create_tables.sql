CREATE TABLE partido (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         rival VARCHAR(100) NOT NULL,
                         torneo VARCHAR(100) NOT NULL,
                         fecha VARCHAR(20) NOT NULL,
                         goles_colo_colo INT NOT NULL,
                         goles_rival INT NOT NULL,
                         estado VARCHAR(50) NOT NULL
);