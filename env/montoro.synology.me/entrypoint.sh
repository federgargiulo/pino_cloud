#!/bin/sh

echo "JAVA_OPTS: $JAVA_OPTS"
echo "SPRING_OPTS: $SPRING_OPTS"

echo "🔍 Checking content of /app/ ..."

ls -ltr /app/



echo "🚀 Java Version..."

java -version
echo "🔍nuovo Checking for UrlDecoder in app.jar..."

chmod +x /app/*

ls -ltr /app/app.jar




jar tf /app/app.jar | grep UrlDecoder || echo "UrlDecoder not found."

echo "🚀 Starting application..."

echo "command: java $JAVA_OPTS -cp /app/app.jar $SPRING_OPTS"

exec  java -cp /app/app.jar it.pliot.equipment.EquipmentApplication


