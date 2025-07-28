FROM eclipse-temurin:24

WORKDIR /app


RUN apt-get update && apt-get install -y python3 python3-pip && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

RUN python3 -m venv /opt/venv
ENV PATH="/opt/venv/bin:$PATH"

COPY ./ai_ext /app/ai_ext
RUN pip3 install --no-cache-dir -r /app/ai_ext/requirements.txt





COPY ./rest/target/rest-0.0.1-SNAPSHOT.jar /app/app.jar


COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh



ENV JAVA_OPTS=""
ENV SPRING_OPTS=""

# Entrypoint
ENTRYPOINT ["/entrypoint.sh"]
