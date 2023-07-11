<p align="center">
  <a href="https://spring.io/projects/spring-boot" target="blank"><img src="https://frontbackend.com/storage/tutorials/thymeleaf/spring-boot-logo.png" width="150" alt="Nest Logo" /></a>
   <a href="https://www.docker.com/" target="blank"><img src="https://storage.googleapis.com/static.ianlewis.org/prod/img/docker/large_v-trans.png" width="150" alt="Nest Logo" /></a>
</p>

# Levantar la base de datos de Mysql con docker

1) clone el repositorio


```bash
 git clone https://github.com/velfin13/prueba-tecnica-viamatica.git

  cd prueba-tecnica-viamatica/db
```
    
2) Configure sus credenciales de la base de datos en el archivo **.env**
```bash
MYSQL_ROOT_PASSWORD=password
MYSQL_USER=user
MYSQL_PASSWORD=password
```

3) Levante el servicio con
```bash
  docker-compose up -d
```

4) Para bajar el servicio
```bash
  docker-compose down
```
## Para poder gestionar la base de datos en **phpmyadmin** escriba en su navegador

```bash
  http://localhost:8080/
```

Ingresa las credenciales que configuraste y listo!
