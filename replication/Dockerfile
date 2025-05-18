FROM debian:bullseye-slim

RUN apt-get update && \
    apt-get install -y postgresql-client && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

ENTRYPOINT ["psql"]