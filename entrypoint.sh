#!/bin/sh




echo "==> AI engine start 5000..."

cd /app/ai_ext
python3 /app/ai_ext/inference_service.py &


echo "JAVA_OPTS: $JAVA_OPTS"
echo "SPRING_OPTS: $SPRING_OPTS"

echo "🔍 Checking content of /app/ ..."

ls -ltr /app/

gijar tf /app/app.jar | grep UrlDecoder || echo "UrlDecoder not found."

echo "🚀 Starting application..."

echo "command: java $JAVA_OPTS -jar /app/app.jar $SPRING_OPTS"

cd /app/
exec java $JAVA_OPTS -jar /app/app.jar $SPRING_OPTS