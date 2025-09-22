#!/bin/bash

echo "🎬 Démonstration TravelSignals - Scénario de démo"

# Vérifier que les services sont en cours d'exécution
echo "🔍 Vérification des services..."

if ! curl -s http://localhost:8080/q/health > /dev/null; then
    echo "❌ Backend non disponible. Veuillez démarrer les services d'abord."
    exit 1
fi

if ! curl -s http://localhost:3000 > /dev/null; then
    echo "❌ Frontend non disponible. Veuillez démarrer les services d'abord."
    exit 1
fi

echo "✅ Services disponibles !"

# Fonction pour envoyer un événement et attendre
send_and_wait() {
    local topic=$1
    local message=$2
    local description=$3
    
    echo "📤 $description"
    echo "$message" | docker exec -i kafka kafka-console-producer --bootstrap-server localhost:9092 --topic "$topic" > /dev/null
    sleep 2
}

echo ""
echo "🎯 Scénario 1: Baisse de prix significative (P2)"
send_and_wait "price-updates" '{"destination":"Paris","provider":"Air France","oldPrice":1000,"newPrice":750,"currency":"EUR","ts":"'$(date -u +%Y-%m-%dT%H:%M:%SZ)'"}' "Envoi d'une baisse de prix de 25% pour Paris"

echo ""
echo "🌪️ Scénario 2: Alerte météo rouge (P1)"
send_and_wait "weather-alerts" '{"destination":"Paris","level":"RED","message":"Tempête violente avec vents de 120 km/h","ts":"'$(date -u +%Y-%m-%dT%H:%M:%SZ)'"}' "Envoi d'une alerte météo rouge pour Paris"

echo ""
echo "✈️ Scénario 3: Annulation de vol (P3)"
send_and_wait "flight-status" '{"flightNo":"AF456","status":"CANCELLED","departure":"CDG","arrival":"LHR","ts":"'$(date -u +%Y-%m-%dT%H:%M:%SZ)'"}' "Envoi d'une annulation de vol AF456"

echo ""
echo "📋 Scénario 4: Rappel visa urgent (P3)"
send_and_wait "visa-reminders" '{"country":"États-Unis","daysBefore":2,"message":"URGENT: Votre ESTA expire dans 2 jours","ts":"'$(date -u +%Y-%m-%dT%H:%M:%SZ)'"}' "Envoi d'un rappel visa urgent pour les États-Unis"

echo ""
echo "🎉 Démonstration terminée !"
echo ""
echo "📊 Résultats attendus:"
echo "   - 1 alerte P1 (météo rouge Paris)"
echo "   - 1 alerte P2 (baisse prix Paris 25%)"
echo "   - 2 alertes P3 (annulation vol + rappel visa)"
echo ""
echo "🌐 Ouvrez http://localhost:3000 pour voir les résultats sur le dashboard"
echo "📖 Consultez http://localhost:8080/q/openapi pour l'API"
