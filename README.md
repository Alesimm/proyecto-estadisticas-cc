## Seguimiento de tareas por integrante
------------------------------------------------------------------

### Alexander Simpertigue (Desarrollador responsable: Eureka-server y Jugadores-service)

**Eureka Server**
* Configuración del servidor de descubrimiento para permitir la comunicación entre todos los microservicios del proyecto.
* Establecimiento de los parámetros básicos para que cada módulo se registre automáticamente al iniciar.

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


---------------------------------------------------------------------------------------------------------------------------------------------

### Joseph Rivas




---------------------------------------------------------------------------------------------------------------------------------------------

## Cristobal Moya



---------------------------------------------------------------------------------------------------------------------------------------------

