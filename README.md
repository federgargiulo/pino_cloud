PINO - Deployment with Multi-Platform Docker

This guide explains how to download, build, and run the Pliot application (nemo_pino) using Docker, compatible with different architectures (e.g., AMD64 and ARM64).

Requirements
	Before getting started, make sure you have:
	Docker installed (Download Docker)
	Docker Buildx enabled (included in Docker Desktop)
	A running PostgreSQL database
	A working Keycloak instance
	Configuration file (application.yml) available locally
	A directory to store the logs

Create a multi-platform Docker builder

docker buildx create \
  --name container-builder \
  --driver docker-container \
  --bootstrap --use

This command creates a new builder named container-builder and sets it as active.

Then, build the image for multiple architectures and push it to Docker Hub:

docker buildx build \
  --platform linux/amd64,linux/aarch64 \
  -t cogitoprediction/nemo_pino:latest \
  --push .

Note: You must be logged in to Docker Hub to perform the push.

To start the application, use the following Docker command:

docker run --rm -it -p 9080:8080 \
  -v "<PATH/TO/CONFIG>:/config" \
  -v "<PATH/TO/LOGS>:/app/logs" \
  -e JAVA_OPTS="-Dspring.profiles.active=server" \
  -e SPRING_OPTS="--spring.config.location=/config/application.yml" \
  cogitoprediction/nemo_pino

Where:
	<PATH/TO/CONFIG> → local path to the folder containing application.yml
	<PATH/TO/LOGS> → local path where you want to store the log files
	
Once started, the application will be accessible at:

http://localhost:9080
