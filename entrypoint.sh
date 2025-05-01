#!/bin/sh

echo "JAVA_OPTS: $JAVA_OPTS"
echo "SPRING_OPTS: $SPRING_OPTS"

exec java $JAVA_OPTS -jar /app/app.jar $SPRING_OPTS