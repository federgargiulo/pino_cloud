docker buildx build \
  --platform linux/amd64,linux/arm64 \
  -t cogitoprediction/nemo_pino:latest \
  --push .