# Architecture TravelSignals

## Vue d'ensemble

TravelSignals est une application event-driven qui traite des événements de voyage en temps réel et génère des alertes consolidées.

## Diagramme de séquence

```
[Simulateurs] → [Kafka Topics] → [Agrégateur] → [Kafka] → [Dashboard]
     ↓              ↓              ↓           ↓         ↓
[EventSimulator] [price-updates] [AlertAggregation] [alert-aggregates] [Vue.js]
                 [weather-alerts]    Service         [REST API]
                 [flight-status]
                 [visa-reminders]
```

## Composants

### Backend (Quarkus)

- **AlertResource**: API REST pour exposer les alertes
- **AlertAggregationService**: Service d'agrégation des événements
- **EventSimulatorService**: Générateur d'événements simulés
- **Modèles**: PriceUpdate, WeatherAlert, FlightStatus, VisaReminder, AlertAggregate

### Frontend (Vue.js)

- **Dashboard**: Vue d'ensemble des alertes
- **Alerts**: Liste détaillée des alertes avec filtres
- **Counters**: Compteurs d'événements en temps réel
- **Stores**: Gestion d'état avec Pinia

### Infrastructure

- **Kafka**: Message broker pour les événements
- **Zookeeper**: Coordination des services Kafka
- **Docker Compose**: Orchestration des conteneurs

## Flux de données

1. **Génération d'événements**: Les simulateurs génèrent des événements périodiquement
2. **Publication Kafka**: Les événements sont publiés sur des topics dédiés
3. **Agrégation**: Le service d'agrégation consomme les événements et applique les règles
4. **Génération d'alertes**: Les alertes sont générées selon les règles de priorité
5. **Stockage**: Les alertes sont stockées en mémoire et exposées via l'API REST
6. **Affichage**: Le frontend consomme l'API pour afficher les alertes

## Règles de priorité

- **P1**: Alertes météo rouges (critique)
- **P2**: Baisses de prix > 10% (important)
- **P3**: Autres événements (information)

## Endpoints API

- `GET /api/alerts`: Liste des alertes
- `GET /api/alerts/counters`: Compteurs d'événements
- `GET /q/health`: Health check
- `GET /q/openapi`: Documentation OpenAPI
