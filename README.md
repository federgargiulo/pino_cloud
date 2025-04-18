#Docker command per avviare l'applicazione
#Precondizione Postgres e keycloak già disponibili
#Sostituire con la directory dove sono contenuti i file di configurazione al posto di <DIRECTORY CONTENENTE IL FILE DI CONFIGURZIONE>
#Sostituire con la directory dove sono contenuti i file di log al posto di <DIRECTORY CONTENENTE IL FILE DI LOG>

docker run --rm -it -p 9080:8080 -v "<DIRECTORY CONTENENTE IL FILE DI CONFIGURZIONE>:/config"  -v "<DIRECTORY CONTENENTE IL FILE DI LOG>:/app/logs" -e JAVA_OPTS="-Dspring.profiles.active=server" -e SPRING_OPTS="--spring.config.location=/config/application.yml" cogitoprediction/nemo_pino