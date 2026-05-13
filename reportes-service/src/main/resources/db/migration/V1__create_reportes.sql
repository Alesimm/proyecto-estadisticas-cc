CREATE TABLE reportes (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  autor_reporte VARCHAR(100) NOT NULL,
  tipo_reporte VARCHAR(100) NOT NULL,
  fecha_generacion VARCHAR(20) NOT NULL,
  total_plantel INT NOT NULL,
  jugadores_lesionados INT NOT NULL,
  promedio_equipo DOUBLE NOT NULL
);