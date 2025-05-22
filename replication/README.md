📦 Kafka & MongoDB Dev Stack
Questa configurazione docker-compose avvia un ambiente completo per lo sviluppo e il testing di applicazioni basate su Kafka, Kafka Connect, MongoDB e strumenti correlati.

🧰 Servizi disponibili
🐘 Zookeeper
Image: confluentinc/cp-zookeeper:7.4.4

Porta esposta: 22181

Descrizione: Coordinatore centrale per Kafka. Necessario per la gestione del cluster Kafka.

🦁 Kafka Broker
Image: confluentinc/cp-kafka:7.4.4

Porte esposte:

9092 (interno)

29092 (per accesso da host)

Descrizione: Broker Kafka principale per la pubblicazione e sottoscrizione di messaggi.

🧬 Schema Registry
Image: confluentinc/cp-schema-registry:7.4.4

Porta esposta: 28081

Descrizione: Gestione centralizzata degli Avro schemas usati nei messaggi Kafka.

🖥 Kafka UI
Image: provectuslabs/kafka-ui:latest

Porta esposta: 8084

Descrizione: Interfaccia web per esplorare topic Kafka, partizioni, consumer, messaggi, etc.

URL di accesso: http://localhost:8084

🔌 Kafka Connect
Image: confluentinc/cp-kafka-connect:7.4.4

Porta esposta: 8083

Descrizione: Framework per connettere Kafka con sistemi esterni (es. DB, file, API) tramite connector.

Plugin Path montato: ./plugins

Script registrazione connectors (opzionale): ./register-connectors.sh

Connettori consigliati:

MongoDB Sink

JDBC Source

🍃 MongoDB
Image: mongo:4.4

Porta esposta: 27017

Descrizione: Database NoSQL utilizzato per la persistenza dei dati provenienti da Kafka tramite Sink Connector.

Volume persistente: ./mongo_data

🌐 Mongo Express
Image: mongo-express:1.0.2

Porta esposta: 27081

Accesso web: http://localhost:27081

Credenziali default:

User: admin

Password: admin

Descrizione: Interfaccia grafica web per esplorare database e collezioni MongoDB.

▶️ Come avviare l'ambiente
bash
Copia
Modifica
docker compose up -d
🧪 Verifica accesso
Servizio	URL
Kafka UI	http://localhost:8084
Schema Registry	http://localhost:28081
Mongo Express	http://localhost:27081 (admin/admin)
Kafka Connect API	http://localhost:8083/connectors

📁 Struttura dei volumi locali
./mongo_data → dati persistenti di MongoDB

./plugins → jar dei connector Kafka Connect (es. MongoDB connector) contiene i jar per leggere da postgres e per scrivere su mongo

./connectors → configurazioni JSON per i connettori Kafka Connect

./register-connectors.sh → script opzionale per registrare connettori all'avvio

I connector sono nella directory connectors per equipment
    - equipment.json contiene le configurazione bulk ( da modificare in incelemntale )
    - contiente la scripttura a partire da una coda ksfka