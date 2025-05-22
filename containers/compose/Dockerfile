FROM eclipse-temurin:21

# Imposta la working directory
WORKDIR /app

# Copia il file JAR
COPY lib/rest-0.0.1-SNAPSHOT.jar /app/lib/rest-0.0.1-SNAPSHOT.jar

# Avvia direttamente il processo senza nohup e &
CMD ["java", "-jar", "/app/lib/rest-0.0.1-SNAPSHOT.jar", "it.pliot.equipment.EquipmentApplication", "--spring.config.location=/app/config/application.yml"]