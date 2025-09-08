#!/usr/bin/env bash
host="$1"
shift
port="${host##*:}"
host="${host%:*}"

echo "Attendo $host:$port..."

while ! nc -z "$host" "$port"; do
  sleep 1
done

echo "$host:$port è raggiungibile, avvio applicazione..."
exec "$@"
