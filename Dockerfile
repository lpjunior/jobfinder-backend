# Etapa de build
FROM maven:3.9.11-eclipse-temurin-24 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

## Etapa de execução
FROM eclipse-temurin:24
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
