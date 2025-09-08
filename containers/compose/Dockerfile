FROM eclipse-temurin:21

# Installa netcat
RUN apt-get update && apt-get install -y netcat-openbsd && apt-get clean


# Imposta la working directory
WORKDIR /app

# Copia il file JAR e lo script di attesa
COPY lib/rest-0.0.1-SNAPSHOT.jar /app/lib/rest-0.0.1-SNAPSHOT.jar
COPY wait-for-keycloak.sh /app/wait-for-keycloak.sh

# Rendi eseguibile lo script
RUN chmod +x /app/wait-for-keycloak.sh


# Avvia prima lo script di attesa, poi l'applicazione
CMD ["/app/wait-for-keycloak.sh", "keycloak:8080", "--", "java", "-jar", "/app/lib/rest-0.0.1-SNAPSHOT.jar", "it.pliot.equipment.EquipmentApplication", "--spring.config.location=/app/config/application.yml"]
