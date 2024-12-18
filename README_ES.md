[//]: # (- METADATOS: //TODO: Añadir más elegantemente   )
- Autor: Francisco Luis Serrano Teruel
- Proyecto: HexAF


### Video completo del proceso de descarga y puesta en marcha
[![Cargando video placeholder](http://img.youtube.com/vi/IhbEldq94vk/1.jpg)](http://www.youtube.com/watch?v=IhbEldq94vk)

# Características de la solución:
* API First.
* OAS3
* Java 23
* Docker + compose
* El código de las interfaces a implementar para consumir y servir las API, se genera automáticamente desde la definición OAS3 de cada uno de los microservicios/módulos. (Se puede usar el comportamiento por defecto o se puede personalizar, cada módulo. Personalizar permite añadir lógica adicional requerida por el negocio. En el controlador que implemente dicha interfaz, añadir la lógica personalizada)

# Pruebas incluidas en la solución
* Test unitarios (PENDING. few self code to test, but still... we can test the language itself, np)
* Integration tests (PENDING. Redundant with e2e tests but still...)
* Test e2e/"Extremo a extremo" Con postman. (Colección includida en repo, 12 peticiones 38 tests)
* Swagger UI funcionando para las tres APIs de servicio (domain, application, adapter)
* Inicialización de datos H2 correcta (Se puede probar la consola si se arranca domain independientemente y se accede por localhost:8082/h2/console usuario sa, contraseña en blanco)
* CI-pipeline-fase1: Cada vez que se hace un push, se verica que el código compila y construye los artefactos necesarios con maven (Github Actions)
* CI-pipeline-fase2: Si la ejecución de maven (CI-pipeline-fase1) es correcta, se generan las imágenes de docker para cada uno de los módulos/microservicios  (Github Actions)
* CI-pipeline-fase3: Cuando la fase 2 (CI-pipeline-fase2) se haya completado, se comprueba que el fichero docker-compose.yml tenga una sintaxis correcta, así como que todos los servicios incluidos en él levanten correctamente un contenedor de Docker  (Github Actions) 

## Swagger UI. Accesso a todas las APIs
- [Adapter](http://localhost:8080/swagger-ui/index.html) http://localhost:8080/swagger-ui/index.html
- [Application](http://localhost:8081/swagger-ui/index.html) http://localhost:8081/swagger-ui/index.html
- [Domain](http://localhost:8082/swagger-ui/index.html) http://localhost:8082/swagger-ui/index.html
 
# Instrucciones para la puesta en marcha
### Requisitos
Configuración de las variables de entorno:

![alt text][captura_env_vars]

[captura_env_vars]: https://github.com/kapeas/HexAF/blob/main/enviroment-vars.png?raw=true "env vars alt text"

* Docker Desktop ([Windows](https://docs.docker.com/desktop/setup/install/windows-install/)) o Docker Engine ([Linux/Mac](https://docs.docker.com/engine/install/)) instalado y permisos para construir imagen, arrancar un contenedor, etc.
* OpenJDK 23.
* Apache Maven. 3.8+, o cualquier versión reciente.  
* Variable de entorno JAVA_HOME apuntando a la ruta raiz de openjdk 23  (Recomendadas las versiones .tar.gz o .zip).
* $JAVA_HOME/bin debe formar parte de los valores en la cadena del valor de la variable de entorno $PATH. Comprobar qué versión de java se está ejecutando al escribir java -version en simbolo del sistema para verificar que se ejecuta la deseada.


Si se cumplen esos requisitos, clonamos el repositorio, y desde el directorio del repo ejecutamos el siguiente comando
```bash
mvn clean package
docker compose up --build
```

### Ejecución de las pruebas E2E con Postman:
- Importamos una nueva colección postman desde el fichero: E2E-TESTS-Collection.postman_collection.json
- Sobre esa colección, botón derecho, Ejecutar colección. Deben ejecutarse las 12 peticiones y sus tests, y mostrar los resultados de cada uno de los pasos. Todos OK.
- (Insertar capturas resultados.)

### API-FIRST: Generar código de los contralodores REST de servicio y cliente desde la definición de API en OAS3. También los clientes
Esto nos permite generar las interfaces a implementar para los clientes y servicios

```bash
mvn clean compile
```

### Arrancar el servicio/módulo localmente (sin docker)
Desde la raíz de los módulos, en caso de querer probar un sólo módulo.

```bash
mvn clean spring-boot:run
```

### Otras recomendaciones y consideraciones
Pendiente



### Limpiar
Para eliminar código/ficheros generados automáticamente desde maven (Clases REST de servidor y/o cliente)

```bash
mvn clean
```

### Otros comandos.
For further reference, please consider the following sections:
