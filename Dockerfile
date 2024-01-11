# syntax = docker/dockerfile:1.2
#
# Build stage
#
FROM maven:3.8.6-openjdk-18 AS build
COPY ../2023-tpa-mi-no-grupo-04 .
RUN mvn clean package assembly:single -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build target/ejercicio-1.0-SNAPSHOT-jar-with-dependencies.jar ejercicio.jar
# ENV PORT=7777
EXPOSE 7777
CMD ["java","-classpath","ejercicio.jar","ar.edu.utn.frba.dds.server.App"]
