FROM eclipse-temurin:21
# Imposta la working directory
WORKDIR /app

# Copia il file JAR
COPY /rest/target/rest-0.0.1-SNAPSHOT.jar /app/app.jar

ENV JAVA_OPTS=""
ENV SPRING_OPTS=""

# Comando di default, ma modificabile in fase di run
CMD ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar $SPRING_OPTS"]