FROM eclipse-temurin:24

WORKDIR /app

# Copia il JAR
COPY ./rest/target/rest-0.0.1-SNAPSHOT.jar /app/app.jar

# Copia l'entrypoint
COPY entrypoint.sh /entrypoint.sh

# Rendi eseguibile lo script
RUN chmod +x /entrypoint.sh

# Imposta le variabili di default (modificabili a runtime)
ENV JAVA_OPTS=""
ENV SPRING_OPTS=""

# Usa lo script come entrypoint
ENTRYPOINT ["/entrypoint.sh"]