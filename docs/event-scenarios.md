# Scénarios d'événements TravelSignals

## Scénario 1: Baisse de prix significative

### Événement d'entrée
```json
{
  "destination": "Paris",
  "provider": "Air France", 
  "oldPrice": 1000.0,
  "newPrice": 750.0,
  "currency": "EUR",
  "ts": "2025-09-22T14:30:00Z"
}
```

### Règle appliquée
- Calcul: (750 - 1000) / 1000 = -25%
- Seuil: > 10% de baisse
- Priorité: P2

### Alerte générée
```json
{
  "type": "PRICE",
  "destination": "Paris",
  "priority": "P2",
  "title": "Baisse de prix significative à Paris",
  "details": {
    "provider": "Air France",
    "oldPrice": 1000.0,
    "newPrice": 750.0,
    "currency": "EUR",
    "changePercentage": -25.0
  },
  "ts": "2025-09-22T14:30:00Z"
}
```

## Scénario 2: Alerte météo rouge

### Événement d'entrée
```json
{
  "destination": "Paris",
  "level": "RED",
  "message": "Tempête violente avec vents de 120 km/h",
  "ts": "2025-09-22T14:33:00Z"
}
```

### Règle appliquée
- Condition: level === "RED"
- Priorité: P1

### Alerte générée
```json
{
  "type": "WEATHER",
  "destination": "Paris", 
  "priority": "P1",
  "title": "Alerte météo rouge à Paris",
  "details": {
    "level": "RED",
    "message": "Tempête violente avec vents de 120 km/h"
  },
  "ts": "2025-09-22T14:33:00Z"
}
```

## Scénario 3: Annulation de vol

### Événement d'entrée
```json
{
  "flightNo": "AF456",
  "status": "CANCELLED",
  "departure": "CDG",
  "arrival": "LHR",
  "ts": "2025-09-22T14:36:00Z"
}
```

### Règle appliquée
- Condition: status !== "ON_TIME"
- Priorité: P3

### Alerte générée
```json
{
  "type": "FLIGHT",
  "destination": "LHR",
  "priority": "P3", 
  "title": "Changement de statut vol AF456",
  "details": {
    "flightNo": "AF456",
    "status": "CANCELLED",
    "departure": "CDG",
    "arrival": "LHR"
  },
  "ts": "2025-09-22T14:36:00Z"
}
```

## Scénario 4: Rappel visa urgent

### Événement d'entrée
```json
{
  "country": "États-Unis",
  "daysBefore": 2,
  "message": "URGENT: Votre ESTA expire dans 2 jours",
  "ts": "2025-09-22T14:39:00Z"
}
```

### Règle appliquée
- Tous les rappels visa génèrent une alerte
- Priorité: P3

### Alerte générée
```json
{
  "type": "VISA",
  "destination": "États-Unis",
  "priority": "P3",
  "title": "Rappel visa États-Unis dans 2 jours", 
  "details": {
    "country": "États-Unis",
    "daysBefore": 2,
    "message": "URGENT: Votre ESTA expire dans 2 jours"
  },
  "ts": "2025-09-22T14:39:00Z"
}
```

## Démonstration complète

Pour tester tous les scénarios :

```bash
# Démarrer l'infrastructure
make up

# Démarrer les services
make dev

# Dans un autre terminal, exécuter la démo
./scripts/demo.sh
```

Les alertes apparaîtront automatiquement sur le dashboard à l'adresse http://localhost:3000.
