# TravelSignals PoC

PoC Event-Driven Alerts for Travel - Quarkus + Vue.js

## Description

TravelSignals est un système d'alertes événementielles pour le voyage qui écoute des événements (prix, météo, vols, rappels visa) et génère des alertes consolidées.

## Architecture

- **Backend**: Quarkus (Java 21) avec Kafka
- **Frontend**: Vue.js 3 + Vite
- **Message Broker**: Apache Kafka
- **Containerisation**: Docker Compose

## Prérequis

- Java 21+
- Node.js 18+
- Docker & Docker Compose
- Maven 3.8+

## Démarrage rapide

```bash
# Cloner le repository
git clone git@github.com:BrotherhoodLabs/travelsignals-poc.git
cd travelsignals-poc

# Démarrer l'infrastructure
make up

# Démarrer le développement
make dev
```

## Endpoints

- Backend: http://localhost:8080
- Frontend: http://localhost:8081
- Health Check: http://localhost:8080/q/health
- OpenAPI: http://localhost:8080/q/openapi

## Structure du projet

```
├── backend/          # API Quarkus
├── frontend/         # Interface Vue.js
├── infra/           # Docker Compose
├── docs/            # Documentation
└── scripts/         # Scripts utilitaires
```

## License

MIT License - voir [LICENSE](LICENSE)
