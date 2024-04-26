#FROM maven:latest AS build
FROM maven:3.9.5 AS build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app
RUN mvn clean install

#FROM openjdk:8-jre-alpine
FROM openjdk:21
#FROM openjdk:8u121-jre-alpine

COPY --from=build /app/target/desafio-picpay-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
