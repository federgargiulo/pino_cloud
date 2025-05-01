docker buildx build \
  --platform linux/amd64,linux/aarch64 \
  -t cogitoprediction/nemo_pino:latest \
  --push .