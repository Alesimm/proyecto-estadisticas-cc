## Seguimiento de tareas por integrante
------------------------------------------------------------------

### Alexander Simpertigue (Desarrollador responsable: Jugadores-service, Rendimiento-service y Auth-service)

**Servicio de Jugadores**
* Creamos el CRUD completo para manejar la informacion de los jugadores del plantel.
* Usamos DTOs para separar lo que el usuario ve de lo que esta en la base de datos.
* Organizamos el codigo en tres capas: Controller, Service y Repository, para que cada parte haga una sola cosa.
* Agregamos validaciones automaticas en los datos que llegan, como edad minima y campos obligatorios.
* Implementamos una regla que impide que dos jugadores tengan el mismo numero de camiseta.
* Creamos un manejador central de errores que devuelve mensajes claros cuando algo sale mal.

**Actualizaciones (Flyway y Busquedas)**
* **Flyway:** Integramos Flyway para que la base de datos se cree y se pueble automaticamente al arrancar el servicio, sin configuracion manual.
* **MariaDB:** Cambiamos el driver a MariaDB para tener compatibilidad perfecta con XAMPP y evitar errores al migrar.
* **CRUD Completo:** Agregamos busqueda por ID y por posicion para tener el servicio completamente operativo.

**Actualizaciones Eliminacion (Eureka)**
* **Principal:** Eliminamos Eureka y el Gateway del proyecto.
* **WebClient:** Reemplazamos la comunicacion por WebClient, que permite que un servicio le pida datos a otro directamente, sin intermediarios.

**Nuevos Avances (Rendimiento-service, Auth-service y Arreglos)**
* **Rendimiento:** Desarrollamos el servicio que calcula la nota de cada jugador consultando su posicion y sus estadisticas en tiempo real desde otros dos servicios.
* **Auth:** Implementamos el sistema de login que valida las credenciales del usuario contra usuarios-service y genera un token unico si todo es correcto.
* **Git:** Resolvimos los conflictos de fusion entre ramas y limpiamos los archivos de configuracion local para que el repositorio quedara ordenado.

**Mejoras en Reportes-service**
* **Fecha automatica:** Quitamos el campo de fecha del body. Ahora el sistema asigna automaticamente la fecha del dia en que se genera el reporte.
* **CRUD Completo:** Agregamos los endpoints GET por ID y DELETE que faltaban.
* **WebClient:** Corregimos el header Content-Type en la llamada al rendimiento-service para que el calculo del promedio del equipo funcionara correctamente.
* **Logs:** Mejoramos los mensajes de consola para que sea facil saber cuando el calculo del promedio falla y por que razon.

**Mejoras en Recomendaciones-service**
* **WebClient:** Aplicamos la misma correccion del header Content-Type en la llamada al rendimiento-service.
* **Logs:** Revisamos los logs del servicio para que quede registrado cuando el analisis de un jugador no puede completarse por falta de datos.

---------------------------------------------------------------------------------------------------------------------------------------------

### Cristobal Moya (Desarrollador responsable: Formaciones-service, Estadisticas-service, Lesiones-service y Reportes-service)

**Servicio de Formaciones**
* Creamos el CRUD para registrar y consultar las alineaciones tacticas del equipo.
* Usamos DTOs para proteger la estructura interna de la base de datos.
* Organizamos el codigo bajo el patron CSR para separar responsabilidades claramente.
* **Flyway:** Automatizamos la creacion de la tabla y la carga de formaciones iniciales de prueba.

**Servicio de Estadisticas**
* Implementamos el registro de metricas de partido por jugador: goles, asistencias, minutos y recuperaciones.
* Agregamos validaciones para rechazar datos incoherentes, como tener goles con cero minutos jugados.
* Creamos el manejador de errores centralizado para devolver mensajes claros al usuario.
* **MariaDB:** Ajustamos la conexion para eliminar los mensajes de ruido que aparecian en la consola al arrancar.

**Servicio de Lesiones**
* Construimos el modulo medico del sistema para registrar y gestionar el estado de salud del plantel.
* Implementamos reglas de negocio como bloquear una segunda lesion activa para el mismo jugador, y forzar los dias de recuperacion a cero cuando el estado es Alta Medica.
* **CRUD Completo:** Agregamos las funciones de busqueda especificas para tener el servicio listo para ser consumido por otros.

**Servicio de Reportes (Orquestacion y WebClient)**
* **Agregador Central:** Desarrollamos el servicio que consolida informacion de tres fuentes distintas en un solo reporte: el total del plantel, los jugadores en tratamiento y el promedio de rendimiento del equipo.
* **WebClient:** El sistema viaja automaticamente a jugadores-service, lesiones-service y rendimiento-service para recolectar los datos en tiempo real.
* **Tolerancia a Fallos:** Si alguno de los servicios externos no responde, el reporte se genera igual con los datos disponibles sin que el sistema se caiga.

**Solucion de Errores y Git**
* **Git:** Resolvimos los conflictos de fusion entre ramas y aislamos los archivos de configuracion local para que el repositorio quedara limpio y profesional.

---------------------------------------------------------------------------------------------------------------------------------------------

### Joseph Rivas (Desarrollador responsable: Usuarios-service, Partidos-service y Recomendaciones-service)

**Servicio de Usuarios**
* Construimos el CRUD completo para gestionar los perfiles de acceso al sistema, conectado a MariaDB.
* Organizamos el codigo bajo el patron CSR para mantener las responsabilidades separadas.
* Implementamos reglas de negocio como impedir correos duplicados y validar que solo existan los roles ADMIN, DT y MEDICO, y los estados ACTIVO e INACTIVO.
* **Manejo de Errores:** Creamos un GlobalExceptionHandler que intercepta errores de validacion y de negocio, devolviendo mensajes JSON claros en vez de errores tecnicos del servidor.

**Servicio de Partidos**
* **CRUD Completo:** Desarrollamos las operaciones de crear, listar y eliminar partidos del calendario deportivo, siguiendo el patron CSR.
* **Sin dependencias externas:** Eliminamos Eureka y Gateway para que el servicio se comunique directamente como proveedor de datos REST.
* **DTOs y Validacion:** Usamos Bean Validation para rechazar datos invalidos como goles negativos o campos vacios.
* **Reglas de Negocio:** Bloqueamos el registro de partidos duplicados con el mismo rival, torneo y fecha, y tambien impedimos que Colo-Colo quede registrado jugando contra si mismo.
* **Manejo de Errores:** Integramos el mismo GlobalExceptionHandler que los otros servicios para devolver respuestas JSON consistentes en todos los casos.
* **Flyway:** Automatizamos la creacion de la tabla y la carga de partidos iniciales de prueba.

**Servicio de Recomendaciones (Motor Analitico y WebClient)**
* **Orquestador:** Desarrollamos el motor de sugerencias tacticas que cruza datos de tres servicios en tiempo real usando WebClient: jugadores, estadisticas y rendimiento.
* **Algoritmo Tactico:** Si un jugador tiene nota alta y pocos minutos, el sistema sugiere que sea titular. Si acumula mucha fatiga, sugiere descanso. Si su nota es baja, sugiere entrenamiento especial. Cada sugerencia tiene prioridad ALTA, MEDIA o BAJA.
* **Tolerancia a Fallos:** Si un servicio externo no responde, el sistema captura el error internamente y devuelve un mensaje controlado en lugar de caerse con un 500.
* **Lectura Flexible de Datos:** Configuramos el cliente para leer los datos con List<Map>, lo que permite adaptarse a variaciones en los nombres de los campos entre servicios.
