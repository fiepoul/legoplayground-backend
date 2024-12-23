# Brug et letvægtigt Java-baseret base-image
FROM openjdk:17-jdk-slim

LABEL authors="fiepoulsen"

# Angiv et arbejdsdirektorie
WORKDIR /app

# Kopier Spring Boot JAR-filen fra Maven build-output til Docker-image
COPY target/legoassi-backend-0.0.1-SNAPSHOT.jar app.jar

# Eksponér den relevante port
EXPOSE 8080

# Angiv kommandoen til at køre Spring Boot-applikationen
ENTRYPOINT ["java", "-jar", "app.jar"]
