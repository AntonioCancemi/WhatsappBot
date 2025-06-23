#!/bin/bash
mvn clean package -DskipTests
java -jar backend/bot/target/bot-0.0.1-SNAPSHOT.jar --spring.profiles.active=h2 &
cd frontend && npm install && npm run dev
