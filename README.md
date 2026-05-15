## Seguimiento de tareas por integrante
------------------------------------------------------------------

### Alexander Simpertigue (Desarrollador responsable: Jugadores-service)

**Servicio de Jugadores**
* Creación de las operaciones básicas para gestionar la información de los jugadores en la base de datos.
* Implementación de objetos de transferencia de datos (DTO) para proteger la estructura interna de la base de datos.
* Reorganización del código en tres capas independientes (Controlador, Servicio y Repositorio) para mejorar el mantenimiento.
* Configuración de reglas automáticas para validar que los datos recibidos sean correctos.
* Creación de una lógica en el servidor para evitar errores de negocio, como la duplicación de números de camiseta.
* Desarrollo de un sistema centralizado para capturar fallos y entregar mensajes de error claros al usuario.

**Actualizaciones (Flyway y Busquedas)**
* **Control de Versiones (Flyway):** Se integro Flyway para automatizar la creacion de la tabla y la insercion de los datos iniciales, asegurando que todos tengan la misma estructura de base de datos.
* **Optimizacion para XAMPP:** Se actualizo el driver a MariaDB para tener compatibilidad nativa perfecta y evitar errores en las migraciones.
* **CRUD Completo:** Se agregaron funciones de busqueda especificas (por ID y por posicion) para tener el microservicio 100% operativo.
  
**Actualizaciones Eliminacion (Eureka)**

* **Principal:** Eliminamos Eureka y el Gateway.
* **Conexión inteligente con WebClient:** Ahora usamos un "cliente web" para que un microservicio pueda pedirle datos a otro. Por ejemplo, cuando necesitemos saber el rendimiento de un jugador, el sistema irá a buscar su posición automáticamente.

**Nuevos Avances (Rendimiento-service, auth-service y Arreglos)**
* **Capa Analítica de Rendimiento:** Se integró el servicio de rendimiento para evaluar el nivel real de cada futbolista, conectando los goles, asistencias y minutos jugados directamente con sus datos personales.
* **Seguridad y Control de Acceso (Auth):** Se implementó el servicio de autenticación para proteger el sistema, permitiendo crear sesiones seguras y validar de forma interna las credenciales de los usuarios que entran a la plataforma.
* **Solución de Errores y Limpieza en Git:** Identificamos y corregimos los conflictos de código que aparecían al fusionar las ramas del proyecto, limpiando los archivos de configuración de base de datos para asegurar que todo el equipo trabaje sobre la versión correcta en GitHub.
---------------------------------------------------------------------------------------------------------------------------------------------

### Joseph Rivas




---------------------------------------------------------------------------------------------------------------------------------------------

### Cristobal Moya



---------------------------------------------------------------------------------------------------------------------------------------------

