# sify-flix-api

## About this project
* [About](#about)
* [Architecture](#Architecture)
* [Technologies](#Technologies)
* [How to run](#how-to-run)
    * [Run with Docker](#run-with-docker-compose)
    * [Run with mvn spring-boot:run](#run-with-mvn-spring-boot)
* [Contributors](#contributors)

# About
This is a Rest API developed based on the requirements given for the selection process by the company W2M.
This API was built with Java 21, Spring Boot, H2 Database, Flyway Migration, Spring Doc OpenAPI, Docker and Docker-compose.

# Architecture
The application is built following the **MVC** design pattern.
Each of the components will be described below:
* **View**: contains response from the Rest controller that will be shown in JSON format to the client.
* **Rest Controller**: responsible for receiving calls and directing them to the correct service.
* **Service**: a design pattern layer responsible for calling the service from the repository layer.
* **Repository**: this layer is responsible for connecting to the database and for persisting, retrieving, updating and deleting data.

# Technologies
- Java 21
- Spring Boot 3.2.2
- H2 Database
- Flyway Migration
- Docker
- Docker-compose
- Junit, Mockito

# How to Run
<p>There two option to run this application, run by docker-compose or run as spring-boot</p>
### Run with docker-compose
<p>Make sure you have Docker installed and execute the command</p>

```
docker-compose up
```
### Run with mvn spring-boot
```shell
mvn spring-boot:run
```

## Contributors
[@LauroSilveira](https://github.com/LauroSilveira)

<p>Fell free to fork and contribute :wink:</p>
