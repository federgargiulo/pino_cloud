#!/bin/sh

echo "JAVA_OPTS: $JAVA_OPTS"
echo "SPRING_OPTS: $SPRING_OPTS"

echo "🔍 Checking for UrlDecoder in app.jar..."
jar tf /app/app.jar | grep UrlDecoder || echo "UrlDecoder not found."

echo "🚀 Starting application..."


exec java $JAVA_OPTS -jar /app/app.jar $SPRING_OPTS