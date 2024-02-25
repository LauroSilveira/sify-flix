FROM maven:3-amazoncorretto-21 AS build
COPY . .
RUN mvn clean package

FROM eclipse-temurin:21-jdk
WORKDIR /sify-flix-api
ARG JAR_FILE=target/*.jar
COPY --from=build ${JAR_FILE} sify-flix-api.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "sify-flix-api.jar"]