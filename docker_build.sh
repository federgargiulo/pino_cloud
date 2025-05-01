docker buildx build \
  --platform linux/amd64,linux/aarch64 \
  -t yourusername/nemo_pino:latest \
  --push .