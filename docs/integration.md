# Intégration MusafirGO

## Webhook REST

### Endpoint
```
POST /api/webhook/alerts
```

### Headers
```
Content-Type: application/json
X-API-Key: your-api-key-here
```

### Format de l'alerte
```json
{
  "type": "PRICE|WEATHER|FLIGHT|VISA",
  "destination": "string",
  "priority": "P1|P2|P3", 
  "title": "string",
  "details": {
    // Détails spécifiques selon le type
  },
  "ts": "2025-09-22T14:30:00Z"
}
```

### Exemple d'utilisation

```bash
curl -X POST http://localhost:8080/api/webhook/alerts \
  -H "Content-Type: application/json" \
  -H "X-API-Key: your-api-key" \
  -d '{
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
  }'
```

## Événements Kafka

### Topic de sortie
```
alert-aggregates
```

### Configuration Kafka
```properties
bootstrap.servers=localhost:9092
group.id=musafirgo-consumer
auto.offset.reset=latest
```

### Exemple de consommation

```java
@Incoming("alert-aggregates")
public void processAlert(AlertAggregate alert) {
    // Traitement de l'alerte dans MusafirGO
    log.info("Received alert: {} for {}", alert.title, alert.destination);
    
    // Logique métier spécifique à MusafirGO
    if ("P1".equals(alert.priority)) {
        // Traitement urgent
        sendUrgentNotification(alert);
    }
}
```

## Sécurité

### Clé API
- Configuration via variable d'environnement `WEBHOOK_API_KEY`
- Validation côté backend avant traitement
- Logs des tentatives d'accès

### Rate Limiting
- Limite de 100 requêtes/minute par clé API
- Headers de réponse pour monitoring

## Monitoring

### Métriques disponibles
- Nombre d'alertes reçues par type
- Temps de traitement moyen
- Taux d'erreur des webhooks

### Endpoints de monitoring
- `GET /api/webhook/stats`: Statistiques des webhooks
- `GET /q/health`: Health check général

## Configuration

### Variables d'environnement
```bash
WEBHOOK_API_KEY=your-secret-key
WEBHOOK_RATE_LIMIT=100
KAFKA_BOOTSTRAP_SERVERS=localhost:9092
```

### Exemple de configuration MusafirGO
```yaml
travelsignals:
  webhook:
    url: http://travelsignals:8080/api/webhook/alerts
    api-key: ${TRAVELSIGNALS_API_KEY}
    timeout: 5000ms
  kafka:
    bootstrap-servers: ${KAFKA_BOOTSTRAP_SERVERS}
    topic: alert-aggregates
    group-id: musafirgo
```
