1) build project with mvn
   
3) docker 
    - docker ps #identifica i processi
    - docker inspect <container_id> --format='{{.Image}}' # identifica l'hash dell'immagine
4) docker aggiornare l'immagine 
    - docker stop <container_name_or_id>
    - docker rm <container_name_or_id>

4) docker aggiornare l'immagine:
   # build e push della nuova immagine:
   - docker login
   - docker build -t cogitoprediction/nemo_pino:latest .
   - docker push cogitoprediction/nemo_pino:latest
