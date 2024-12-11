# Instrucciones para la puesta en marcha

### Requisitos
* Docker Engine instalado y permisos para construir imagen, arrancar un contenedor, etc.
* OpenJDK 23
* Maven 3.5+
* Variable de entorno JAVA_HOME apuntando a la ruta raiz de openjdk 23 (sin el /bin, el directorio donde se instaló o se descomprimió, que contiene bin/java.exe del jdk deseado para la prueba)
* $JAVA_HOME/bin debe formar parte de los valores en la cadena del valor de la variable de entorno $PATH. Comprobar qué versión de java se está ejecutando al escribir java -version en simbolo del sistema para verificar que se ejecuta la deseada.
* La ruta al ejecutable mvn/mvnw debe ser accesible y con permisos para el usuario ejecutando los comandos maven.
* 

### Limpiar
mvn clean

### API-FIRST: Generar código de los contralodores REST de servicio desde la definición de API en OAS3.
mvn compile

### Arrancar el servicio localmente (sin docker)
mvn spring-boot:run

### Si estás en Windows

### Si estás en Linux

### Otras recomendaciones y consideraciones
Pendiente


### Otros comandos.
For further reference, please consider the following sections:

[//]: # ()
[//]: # (* [Official Apache Maven documentation]&#40;https://maven.apache.org/guides/index.html&#41;)

[//]: # (* [Spring Boot Maven Plugin Reference Guide]&#40;https://docs.spring.io/spring-boot/3.4.0/maven-plugin&#41;)

[//]: # (* [Create an OCI image]&#40;https://docs.spring.io/spring-boot/3.4.0/maven-plugin/build-image.html&#41;)

[//]: # ()
[//]: # (### Maven Parent overrides)

[//]: # ()
[//]: # (Due to Maven's design, elements are inherited from the parent POM to the project POM.)

[//]: # (While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.)

[//]: # (To prevent this, the project POM contains empty overrides for these elements.)

[//]: # (If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.)

[//]: # ()
