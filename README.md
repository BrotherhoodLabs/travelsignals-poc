# TravelSignals PoC

🚀 **PoC Event-Driven Alerts for Travel** - Architecture complète avec Quarkus + Vue.js + PostgreSQL

## 📋 Description

TravelSignals est un système d'alertes événementielles pour le voyage qui écoute des événements (prix, météo, vols, rappels visa) et génère des alertes consolidées en temps réel avec stockage persistant.

## 🏗️ Architecture

- **Backend**: Quarkus 3.6.0 (Java 17) avec Panache ORM
- **Frontend**: Vue.js 3 + TypeScript + Vite
- **Base de données**: PostgreSQL 15 avec données de test
- **Message Broker**: Apache Kafka + Zookeeper
- **Containerisation**: Docker Compose
- **APIs**: REST + OpenAPI/Swagger

## ⚙️ Prérequis

- **Java 17+** (recommandé: OpenJDK 17)
- **Node.js 18+** (pour le développement frontend)
- **Docker & Docker Compose** (pour l'infrastructure)
- **Maven 3.8+** (pour le backend)

## 🚀 Démarrage rapide

```bash
# 1. Cloner le repository
git clone git@github.com:BrotherhoodLabs/travelsignals-poc.git
cd travelsignals-poc

# 2. Démarrer l'architecture complète
docker-compose -f infra/docker-compose.yml up -d

# 3. Attendre que tous les services démarrent (30-60 secondes)
# 4. Ouvrir le frontend dans votre navigateur
```

## 🌐 Endpoints

| Service | URL | Description |
|---------|-----|-------------|
| **Frontend** | http://localhost:8081 | Interface utilisateur principale |
| **Backend API** | http://localhost:8080 | API REST Quarkus |
| **OpenAPI/Swagger** | http://localhost:8080/q/swagger-ui | Documentation interactive |
| **Health Check** | http://localhost:8080/q/health | État des services |
| **PostgreSQL** | localhost:5432 | Base de données (user: travelsignals) |
| **Kafka** | localhost:9092 | Message broker |

## 📁 Structure du projet

```
travelsignals-poc/
├── backend/                    # API Quarkus + JPA
│   ├── src/main/java/         # Code source Java
│   │   └── com/brotherhoodlabs/travelsignals/
│   │       ├── entity/        # Entités JPA (Destination, Provider, etc.)
│   │       ├── model/         # Modèles d'événements Kafka
│   │       ├── service/       # Services métier
│   │       └── *Resource.java # Endpoints REST
│   └── src/main/resources/    # Configuration (application.properties)
├── frontend/                   # Interface Vue.js 3 + TypeScript
│   ├── src/
│   │   ├── components/        # Composants Vue réutilisables
│   │   ├── views/            # Pages de l'application
│   │   ├── services/         # Services API (axios)
│   │   └── stores/           # Stores Pinia
│   └── dist/                 # Build de production
├── infra/                     # Infrastructure Docker
│   ├── docker-compose.yml    # Orchestration des services
│   └── init-db.sql          # Script d'initialisation PostgreSQL
├── docs/                     # Documentation technique
│   ├── architecture.md      # Architecture détaillée
│   ├── event-schemas.md     # Schémas d'événements Kafka
│   └── integration.md       # Guide d'intégration
└── scripts/                  # Scripts utilitaires
    ├── dev.sh               # Développement local
    ├── seed.sh              # Peuplement des données
    └── demo.sh              # Démonstration
```

## 🔧 APIs disponibles

### Alertes
- `GET /api/alerts` - Liste toutes les alertes
- `GET /api/alerts/{id}` - Détail d'une alerte
- `GET /api/alerts/type/{type}` - Alertes par type
- `GET /api/alerts/priority/{priority}` - Alertes par priorité

### Simulation d'événements
- `POST /api/simulate/price-update` - Simuler une mise à jour de prix
- `POST /api/simulate/weather-alert` - Simuler une alerte météo
- `POST /api/simulate/flight-status` - Simuler un changement de vol
- `POST /api/simulate/visa-reminder` - Simuler un rappel visa

### Données de test
- `POST /api/test-data/populate` - Peupler la base avec des données de test
- `POST /api/generate/random` - Générer une alerte aléatoire

### CRUD Destinations/Providers
- `GET/POST/PUT/DELETE /api/destinations` - Gestion des destinations
- `GET/POST/PUT/DELETE /api/providers` - Gestion des fournisseurs

## 🎯 Fonctionnalités

✅ **Architecture événementielle** avec Kafka  
✅ **Base de données PostgreSQL** avec données de test  
✅ **APIs REST complètes** avec OpenAPI  
✅ **Interface utilisateur** Vue.js 3 + TypeScript  
✅ **Containerisation** Docker Compose  
✅ **CORS configuré** pour le développement  
✅ **Tests d'intégration** fonctionnels  

## 📊 Statut du projet

- **Backend**: ✅ Complet et fonctionnel
- **Frontend**: ✅ Interface de base opérationnelle
- **Base de données**: ✅ Intégrée avec données de test
- **APIs**: ✅ Toutes les opérations CRUD disponibles
- **Documentation**: ✅ Complète et à jour

## 📄 License

MIT License - voir [LICENSE](LICENSE)
