FROM eclipse-temurin:21-jdk

WORKDIR /app

# Install Python + pip + venv
RUN apt-get update && \
    apt-get install -y python3 python3-pip python3-venv && \
    ln -s /usr/bin/python3 /usr/local/bin/python && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Crea virtualenv (senza symlink)
RUN python -m venv --copies /opt/venv

# Copia modulo Python e installa requirements
COPY ./ai_ext /app/ai_ext
RUN /opt/venv/bin/pip install --no-cache-dir -r /app/ai_ext/requirements.txt

# Copia il JAR Java
COPY ./rest/target/rest-0.0.1-SNAPSHOT.jar /app/app.jar

# Copia lo script di avvio
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# Imposta PATH al virtualenv
ENV PATH="/opt/venv/bin:$PATH"

EXPOSE 8080 5000

ENV JAVA_OPTS=""
ENV SPRING_OPTS=""

ENTRYPOINT ["/entrypoint.sh"]
