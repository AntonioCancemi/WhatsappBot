FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copia i file dalla sottodirectory `bot`
COPY bot/pom.xml ./pom.xml
COPY bot/src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

RUN mkdir -p /app/logs

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "mkdir -p logs && java -jar app.jar"]
