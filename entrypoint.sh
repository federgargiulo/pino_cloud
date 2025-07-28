#!/bin/sh


t -e

echo "==> AI engine start 5000..."
python3 /app/ai_ext/app.py &


echo "JAVA_OPTS: $JAVA_OPTS"
echo "SPRING_OPTS: $SPRING_OPTS"

echo "🔍 Checking content of /app/ ..."

ls -ltr /app/

echo "🔍Nuovo Checking for UrlDecoder in app.jar..."

jar tf /app/app.jar | grep UrlDecoder || echo "UrlDecoder not found."

echo "🚀 Starting application..."

echo "command: java $JAVA_OPTS -jar /app/app.jar $SPRING_OPTS"


exec java $JAVA_OPTS -jar /app/app.jar $SPRING_OPTS