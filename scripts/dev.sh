#!/bin/bash

echo "🚀 Démarrage de TravelSignals en mode développement..."

# Vérifier que Docker est en cours d'exécution
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker n'est pas en cours d'exécution. Veuillez démarrer Docker."
    exit 1
fi

# Démarrer l'infrastructure
echo "📦 Démarrage de l'infrastructure (Kafka, Zookeeper)..."
docker-compose -f infra/docker-compose.yml up -d zookeeper kafka

# Attendre que Kafka soit prêt
echo "⏳ Attente que Kafka soit prêt..."
sleep 30

# Démarrer le backend
echo "🔧 Démarrage du backend Quarkus..."
cd backend
mvn quarkus:dev &
BACKEND_PID=$!
cd ..

# Attendre que le backend soit prêt
echo "⏳ Attente que le backend soit prêt..."
sleep 15

# Démarrer le frontend
echo "🎨 Démarrage du frontend Vue.js..."
cd frontend
npm install
npm run dev &
FRONTEND_PID=$!
cd ..

echo "✅ TravelSignals est démarré !"
echo "🌐 Frontend: http://localhost:3000"
echo "🔧 Backend: http://localhost:8080"
echo "📊 Kafka: localhost:9092"
echo "📖 OpenAPI: http://localhost:8080/q/openapi"
echo "❤️  Health: http://localhost:8080/q/health"

# Fonction de nettoyage
cleanup() {
    echo "🛑 Arrêt des services..."
    kill $BACKEND_PID $FRONTEND_PID 2>/dev/null
    docker-compose -f infra/docker-compose.yml down
    exit 0
}

# Capturer Ctrl+C
trap cleanup SIGINT

# Attendre indéfiniment
wait
