[//]: # (- METADATOS: //TODO: Añadir más elegantemente   )
- Author: Francisco Luis Serrano Teruel (Kapeas)
- Proyecto: HexAF


### Video with cloning/downloading, compiling, and E2E instructions
[![Loading video placeholder](http://img.youtube.com/vi/IhbEldq94vk/1.jpg)](http://www.youtube.com/watch?v=IhbEldq94vk)

# Project features:
* Hexagonal Architecture (AIMS to be...) (Every advice/polite critic/suggestion is greatly welcome.)
* API First.
* Spring boot 3.4.0
* OAS3 docs on every served api
* Interfaces code for consuming and serving APIs generated automatically from the OAS3 spec included in the project. Implement those to customize behaviour
* Java 23 
* Docker + compose


# Tests Included 
* (PENDING) Unit Tests
* (PENDING) Integration tests 
* Test e2e with postman. (E2E Postman collection included in root, with 12 requests and 38 e2e tests written in postman script api)
* Swagger UI working for all 3 modules/layers (domain, application, adapter)
* CI-pipeline-phase1: GHAction for compiling everything after push (Github Actions).
* CI-pipeline-phase2: If CI-pipeline-phase1) went ok, create docker image for each module/layer. (Github Actions)
* CI-pipeline-phase3: when CI-pipeline-phase2 is completed, verify docker compose file (Github Actions) 

## Swagger UI. Access all APIs
- [Adapter](http://localhost:8080/swagger-ui/index.html) http://localhost:8080/swagger-ui/index.html
- [Application](http://localhost:8081/swagger-ui/index.html) http://localhost:8081/swagger-ui/index.html
- [Domain](http://localhost:8082/swagger-ui/index.html) http://localhost:8082/swagger-ui/index.html
 
# Instructions to build, run and test
### Requisites
Setting up Environment Variables:

![alt text][captura_env_vars]

[captura_env_vars]: https://github.com/kapeas/HexAF/blob/main/enviroment-vars.png?raw=true "env vars alt text"

* Docker Desktop ([Windows](https://docs.docker.com/desktop/setup/install/windows-install/)) or Docker Engine ([Linux/Mac](https://docs.docker.com/engine/install/)) installed.
* OpenJDK 23.
* Apache Maven. 3.8+, or any other recent version. (with mvn executable folder included in PATH environment variable)  
* JAVA_HOME pointing to root of jdk23 (tar.gz or zip versions recommended)
* $JAVA_HOME/bin must be part of the value of PATH environment variable.


If all the above is met, clone repo and from root folder run:
```bash
mvn clean package
docker compose up --build
```

### Running E2E tests included with postman
- Import new collection from file E2E-TESTS-Collection.postman_collection.json
- On this collection, open menu and choose "Run collection". Choose/confirm run options and execute all tests. All must pass. 12 requests and 38 tests.

### API-FIRST: Generate code for interfaces from definitions.
```bash
mvn clean compile
```
