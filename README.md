## Seguimiento de tareas por integrante
------------------------------------------------------------------

### Alexander Simpertigue (Desarrollador responsable: Jugadores-service, Rendimiento-service y Auth-service)

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

### Cristobal Moya (Desarrollador responsable: Formaciones-service, Estadisticas-service, Lesiones-service y Reportes-service)**

**Servicio de Formaciones**
* Creación de las operaciones básicas para gestionar las alineaciones tácticas del equipo en la base de datos.
* Implementación de objetos de transferencia de datos (DTO) para proteger la estructura interna de la base de datos.
* Reorganización del código en tres capas independientes (Controlador, Servicio y Repositorio) para mejorar el mantenimiento.
* **Control de Versiones (Flyway):** Se integró Flyway para automatizar la creación de las tablas y asegurar la estructura táctica inicial.

**Servicio de Estadísticas**
* Creación de las operaciones básicas para registrar y consultar las métricas de juego (goles, recuperaciones, minutos).
* Configuración de reglas automáticas para validar que los datos recibidos sean correctos.
* Desarrollo de un sistema centralizado para capturar fallos y entregar mensajes de error claros al usuario.
* **Optimización para MariaDB:** Se ajustaron los parámetros de conexión nativa para evitar bloqueos y limpiar los ruidos del servidor en la consola.

**Servicio de Lesiones**
* Creación de las operaciones básicas para gestionar el estado médico y los tiempos de recuperación del plantel.
* Creación de una lógica en el servidor para evitar errores de negocio y mantener la coherencia sobre qué jugadores están disponibles.
* **CRUD Completo:** Se agregaron funciones de búsqueda específicas para tener el microservicio 100% operativo y listo para su consumo.

**Servicio de Reportes (Orquestación y WebClient)**
* **Orquestación Centralizada:** Creación del servicio que actúa como un "agregador", consolidando la información de múltiples puertos en un solo documento final.
* **Conexión Inteligente con WebClient:** Ahora usamos un "cliente web" avanzado. El sistema viaja automáticamente para listar a los jugadores, cruza los datos con los lesionados y calcula matemáticamente el rendimiento.
* **Resiliencia y Tolerancia a Fallos:** Se implementó un mecanismo de protección para asegurar que el sistema de reportes no colapse si un microservicio externo falla, permitiendo generar informes estables siempre.

**Solución de Errores y Git**
* **Limpieza en Git:** Identificamos y corregimos los conflictos de código que aparecían al fusionar las ramas del proyecto, aislando los archivos de entorno local para asegurar que el repositorio quede limpio y profesional en GitHub.
---------------------------------------------------------------------------------------------------------------------------------------------

### Joseph Rivas

