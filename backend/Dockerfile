# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml ./
COPY bot/pom.xml ./bot/pom.xml
COPY bot/src ./bot/src
RUN mvn -B -pl bot -am package

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/bot/target/bot-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
