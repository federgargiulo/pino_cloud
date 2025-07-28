docker buildx build \
  --platform linux/amd64,linux/arm64/v8 \
  -t cogitoprediction/nemo_pino:ai1 \
  --push .