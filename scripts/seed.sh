#!/bin/bash

echo "🌱 Envoi d'événements de test vers Kafka..."

# Vérifier que Kafka est en cours d'exécution
if ! docker-compose -f infra/docker-compose.yml ps kafka | grep -q "Up"; then
    echo "❌ Kafka n'est pas en cours d'exécution. Veuillez démarrer l'infrastructure d'abord."
    exit 1
fi

# Fonction pour envoyer un événement
send_event() {
    local topic=$1
    local message=$2
    echo "$message" | docker exec -i kafka kafka-console-producer --bootstrap-server localhost:9092 --topic "$topic"
}

echo "📊 Envoi d'événements de prix..."
send_event "price-updates" '{"destination":"Paris","provider":"Air France","oldPrice":800,"newPrice":650,"currency":"EUR","ts":"2025-09-22T14:30:00Z"}'
send_event "price-updates" '{"destination":"New York","provider":"Lufthansa","oldPrice":1200,"newPrice":900,"currency":"EUR","ts":"2025-09-22T14:31:00Z"}'
send_event "price-updates" '{"destination":"Tokyo","provider":"Emirates","oldPrice":1500,"newPrice":1800,"currency":"EUR","ts":"2025-09-22T14:32:00Z"}'

echo "🌤️ Envoi d'alertes météo..."
send_event "weather-alerts" '{"destination":"Paris","level":"RED","message":"Tempête violente prévue","ts":"2025-09-22T14:33:00Z"}'
send_event "weather-alerts" '{"destination":"Londres","level":"YELLOW","message":"Pluie modérée","ts":"2025-09-22T14:34:00Z"}'
send_event "weather-alerts" '{"destination":"Dubai","level":"GREEN","message":"Temps ensoleillé","ts":"2025-09-22T14:35:00Z"}'

echo "✈️ Envoi de statuts de vol..."
send_event "flight-status" '{"flightNo":"AF123","status":"DELAYED","departure":"CDG","arrival":"JFK","ts":"2025-09-22T14:36:00Z"}'
send_event "flight-status" '{"flightNo":"LH456","status":"CANCELLED","departure":"FRA","arrival":"LAX","ts":"2025-09-22T14:37:00Z"}'
send_event "flight-status" '{"flightNo":"EK789","status":"ON_TIME","departure":"DXB","arrival":"SIN","ts":"2025-09-22T14:38:00Z"}'

echo "📋 Envoi de rappels visa..."
send_event "visa-reminders" '{"country":"États-Unis","daysBefore":7,"message":"N\'oubliez pas de faire votre ESTA","ts":"2025-09-22T14:39:00Z"}'
send_event "visa-reminders" '{"country":"Japon","daysBefore":14,"message":"Vérifiez les exigences de visa","ts":"2025-09-22T14:40:00Z"}'
send_event "visa-reminders" '{"country":"Australie","daysBefore":30,"message":"Rappel visa eVisitor","ts":"2025-09-22T14:41:00Z"}'

echo "✅ Événements de test envoyés avec succès !"
echo "🔍 Vérifiez le dashboard pour voir les alertes générées."
