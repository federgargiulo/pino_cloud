# Stage 1: Costruzione ambiente Python
FROM python:3.11-slim as python-base

WORKDIR /app/ai_ext

# Copia il modulo Python
COPY ./ai_ext /app/ai_ext

# Crea e popola il virtualenv
RUN python -m venv  --copies /opt/venv && \
    /opt/venv/bin/pip install --no-cache-dir -r /app/ai_ext/requirements.txt

# Stage 2: App Java + ambiente Python
FROM eclipse-temurin:24

WORKDIR /app

# Copia virtualenv Python dal primo stage
COPY --from=python-base /opt/venv /opt/venv
COPY --from=python-base /app/ai_ext /app/ai_ext

# Attiva l'env virtuale nel PATH
ENV PATH="/opt/venv/bin:$PATH"

# Copia il JAR Java
COPY ./rest/target/rest-0.0.1-SNAPSHOT.jar /app/app.jar

# Copia lo script di avvio
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

EXPOSE 8080 5000

ENV JAVA_OPTS=""
ENV SPRING_OPTS=""

ENTRYPOINT ["/entrypoint.sh"]
