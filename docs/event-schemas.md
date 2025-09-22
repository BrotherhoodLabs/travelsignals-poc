# Schémas d'événements TravelSignals

## Événements d'entrée

### 1. price.update
```json
{
  "destination": "string",
  "provider": "string", 
  "oldPrice": "number",
  "newPrice": "number",
  "currency": "string",
  "ts": "string (ISO 8601)"
}
```

### 2. weather.alert
```json
{
  "destination": "string",
  "level": "string (GREEN|YELLOW|RED)",
  "message": "string",
  "ts": "string (ISO 8601)"
}
```

### 3. flight.status
```json
{
  "flightNo": "string",
  "status": "string (ON_TIME|DELAYED|CANCELLED)",
  "departure": "string (airport code)",
  "arrival": "string (airport code)",
  "ts": "string (ISO 8601)"
}
```

### 4. visa.reminder
```json
{
  "country": "string",
  "daysBefore": "number",
  "message": "string",
  "ts": "string (ISO 8601)"
}
```

## Événements de sortie

### alert.aggregate
```json
{
  "type": "string (PRICE|WEATHER|FLIGHT|VISA)",
  "destination": "string",
  "priority": "string (P1|P2|P3)",
  "title": "string",
  "details": "object",
  "ts": "string (ISO 8601)"
}
```

## Règles de priorité

- **P1** : Météo rouge (level: RED)
- **P2** : Baisse de prix > 10%
- **P3** : Autres événements

## Topics Kafka

- `price-updates`
- `weather-alerts` 
- `flight-status`
- `visa-reminders`
- `alert-aggregates`
