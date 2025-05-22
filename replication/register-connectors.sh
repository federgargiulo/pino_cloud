

echo "Registering connector: for postgres from /etc/kafka-connect/connectors/equipment.json"
curl -s -X POST -H "Content-Type: application/json" --data @/etc/kafka-connect/connectors/equipment.json http://localhost:8083/connectors
# tail logs to keep container running if needed
sleep infinity