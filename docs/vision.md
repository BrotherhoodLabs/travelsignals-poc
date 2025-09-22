# Vision TravelSignals PoC

## Objectif

TravelSignals est un système d'alertes événementielles pour le voyage qui écoute des événements en temps réel et génère des alertes consolidées pour les voyageurs.

## Fonctionnalités principales

### Écoute d'événements
- **Prix** : Variations de prix des vols, hôtels, activités
- **Météo** : Alertes météorologiques pour les destinations
- **Vols** : Changements de statut des vols (retards, annulations)
- **Visa** : Rappels de documents et délais

### Génération d'alertes
- Agrégation intelligente des événements
- Système de priorité (P1: météo rouge, P2: baisse prix >10%, P3: autres)
- Alertes consolidées par destination

## Usage

### Mode autonome
- Dashboard web pour visualiser les alertes
- Filtres par type et priorité
- Détails des alertes en temps réel

### Intégration MusafirGO
- Webhook REST pour recevoir les alertes
- Événements Kafka pour l'intégration en temps réel
- API REST pour consultation

## Portée du PoC

- **Flux simulés** : Générateurs d'événements internes
- **Règles simples** : Logique d'agrégation basique
- **Persistance en mémoire** : Pas de base de données
- **Démo end-to-end** : Scénarios de démonstration complets

## Architecture technique

```
[Simulateurs] → [Kafka] → [Agrégateur] → [Kafka] → [Dashboard]
                     ↓
                [Webhook API]
```

## Prochaines étapes

1. Persistance avec PostgreSQL
2. Authentification OIDC
3. WebSockets/SSE pour temps réel
4. Déploiement Kubernetes
5. Monitoring et observabilité avancée
