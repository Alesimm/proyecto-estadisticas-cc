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

**Mejoras en Reportes-service**
* **Fecha automatica:** Se elimino el campo fechaGeneracion del DTO de entrada. El sistema ahora asigna la fecha del dia en que se genera el reporte de forma automatica, simplificando el uso del endpoint.
* **CRUD Completo:** Se agregaron los endpoints GET por ID y DELETE que faltaban, dejando el servicio con las 4 operaciones necesarias.
* **Correccion del cliente WebClient:** Se agrego el header Content-Type explicitamente en la llamada al rendimiento-service para asegurar que el calculo del promedio funcione correctamente en todos los casos.
* **Mejora de logs:** Se mejoraron los mensajes de trazabilidad en el RendimientoClient para que quede claro en consola por que razon falla o tiene exito el calculo del promedio de cada jugador.

**Mejoras en Recomendaciones-service**
* **Correccion del cliente de rendimiento:** Se agrego el header Content-Type explicitamente en la llamada POST al rendimiento-service, corrigiendo el mismo problema que afectaba al reportes-service.
* **Trazabilidad en el flujo de analisis:** Se revisaron los logs del servicio para asegurar que quede registrado cuando el analisis de un jugador falla por falta de datos en los servicios dependientes.
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

### Joseph Rivas (Desarrollador responsable: Usuarios-service, Partidos-service y Recomendaciones-service)

**Servicio de Usuarios**
* Creación de las operaciones básicas para gestionar los perfiles de acceso al sistema, implementando un CRUD completo conectado a MariaDB.
* Reorganización del código bajo el patrón de arquitectura CSR (Controlador, Servicio y Repositorio) para asegurar la separación de responsabilidades.
* Desarrollo de reglas de negocio críticas, incluyendo la prevención de correos duplicados (tanto en creación como actualización) y la validación estricta de dominios permitidos para roles (ADMIN, DT, MEDICO) y estados (ACTIVO, INACTIVO).
* **Control de Excepciones Centralizado:** Implementación de un `GlobalExceptionHandler` para interceptar fallos de Bean Validation y reglas de dominio, devolviendo mensajes JSON limpios y semánticos (Error 400) en lugar de trazas de error del servidor.

**Servicio de Partidos**
* **CRUD Completo y Arquitectura CSR:** Desarrollo de las operaciones (crear, listar y eliminar) para la gestión del calendario de encuentros deportivos, estructurando el microservicio bajo el patrón estricto de Controlador, Servicio y Repositorio.
* **Limpieza Arquitectónica:** Adaptación del microservicio para actuar puramente como "Proveedor de Datos" mediante API REST, eliminando dependencias externas (Eureka/Gateway) para establecer una comunicación directa y optimizada.
* **Implementación de DTOs y Validación:** Uso de objetos de transferencia de datos (DTO) con `Bean Validation` para asegurar la integridad de los datos entrantes (ej. evitar el registro de goles negativos o campos en blanco).
* **Reglas de Negocio Estrictas:** Configuración de validaciones en el servidor para evitar la duplicidad exacta de partidos (mismo rival, torneo y fecha) y prevenir incoherencias lógicas (como el bloqueo de registros donde el equipo local juegue contra sí mismo).
* **Control de Excepciones Estandarizado:** Integración de un `GlobalExceptionHandler` unificado (espejo de `jugadores-service`) para atrapar errores de formulario, violaciones a reglas de negocio y fallos graves del servidor, devolviendo respuestas JSON amigables.
* **Control de Versiones (Flyway):** Automatización de la creación de la estructura de tablas en MariaDB y carga de scripts con datos de prueba iniciales.

**Servicio de Recomendaciones (Motor Analítico y WebClient)**
* **Orquestación de Microservicios:** Desarrollo del motor de decisiones del sistema, cruzando información en tiempo real mediante `WebClient` hacia los puertos de Jugadores, Rendimiento y Estadísticas.
* **Implementación de Algoritmo Táctico:** Creación de la lógica condicional que evalúa dinámicamente las notas de rendimiento y la fatiga física (minutos acumulados) para generar prioridades (ALTA, MEDIA, BAJA) y sugerencias tácticas automáticas.
* **Resiliencia y Tolerancia a Fallos (Blindaje de Código):** Integración de bloques de protección avanzados para aislar fallos de red. Si un microservicio externo se desconecta o no tiene datos, el sistema captura el error internamente evitando que la aplicación colapse (Error 500), devolviendo un mensaje semántico de estado.
* **Mapeo Dinámico de Datos:** Configuración de lectura de estructuras de datos tipo `List<Map>` para extraer valores independientemente de las variaciones de nomenclatura en las bases de datos externas (ej. compatibilidad entre `nota_final` y `notaRendimiento`).

