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
  
**Actualizaciones (Eureka)**

* **Fuera lo complejo:** Eliminamos Eureka y el Gateway. Ahora los microservicios se hablan directamente. Esto hace que el sistema sea más rápido de levantar y mucho más fácil de testear en esta etapa.
* **Conexión inteligente con WebClient:** Ahora usamos un "cliente web" moderno para que un microservicio pueda pedirle datos a otro. Por ejemplo, cuando necesitemos saber el rendimiento de un jugador, el sistema irá a buscar su posición automáticamente.
* **Orden en la casa (Patrón CSR):** Limpiamos el código para que cada cosa esté en su lugar. El *Controller* solo recibe los pedidos, el *Service* piensa la lógica y el *Repository* guarda los datos.
* **Regla de Oro de los 7 Campos:** Ajustamos todos nuestros formularios para que tengan exactamente 7 campos, con validaciones de verdad. Si alguien intenta meter datos malos o duplicar un número de camiseta, el sistema lo frena de inmediato.
* **Base de datos con Flyway:** Ya no creamos tablas a mano en XAMPP. Ahora el código es el que manda: al iniciar el proyecto, Flyway construye todo solito y deja los datos listos para probar.

---------------------------------------------------------------------------------------------------------------------------------------------

### Joseph Rivas




---------------------------------------------------------------------------------------------------------------------------------------------

### Cristobal Moya



---------------------------------------------------------------------------------------------------------------------------------------------

