# Spring Boot, Spring Data y RestApi
Minimos requisitos
- Java 11
- Maven 3.3+
- lombok
- Docker CE

Para comenzar
- Si queres utilizar esta aplicacion, clona esta aplicacion https://github.com/bullDeveloper/bull.SpringRestApi.git
- Ejecutar en la raiz del proyecto, mvn clean install
- Ejecutar la clase com.bull.springboot.application.Application como una aplicacion java
- El servicio se ejecutara en localhost:8888
- La documentacion de toda la api se encuentra en [http://localhost:8888/swagger-ui.html](http://localhost:8888/swagger-ui.html)

![2022-06-09 16_40_51-Calendar](https://user-images.githubusercontent.com/33255456/172931050-734cb8ca-2a0d-46c2-bbb8-cc0a4517765f.png)

- Los servicios publicados se encuentran desarrollados en com.bull.springboot.application.controller.ApiRestController

## Para ejecutar la aplicacion como un container (Docker)
Construir la imagen docker (Situado en el directorio Raiz del proyecto):

Precondicion: Antes de iniciar el docker build tener el target del proyecto creado spring-boot-bull.jar (Esto se realiza con un mvn clean install)
```
docker build -f DockerFile -t docker-spring-boot-mendel .
```

Validacion de container creado exitosamente, al ejecutar el siguiente comando debe aparecer el containter docker-spring-boot-mendel:
```
docker images
```

Ejecutar container:
```
docker run -p 8888:8888 docker-spring-boot-mendel
```
