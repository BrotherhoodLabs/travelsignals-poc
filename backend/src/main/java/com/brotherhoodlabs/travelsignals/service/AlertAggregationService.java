package com.brotherhoodlabs.travelsignals.service;

import com.brotherhoodlabs.travelsignals.model.*;
import com.brotherhoodlabs.travelsignals.entity.*;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

@ApplicationScoped
public class AlertAggregationService {
    
    private static final Logger LOG = Logger.getLogger(AlertAggregationService.class);
    
    @Inject
    @Channel("alert-aggregates")
    Emitter<AlertAggregate> alertEmitter;
    
    // Stockage en mémoire des alertes
    private final List<AlertAggregate> alerts = new CopyOnWriteArrayList<>();
    private final Map<String, Object> eventCounters = new ConcurrentHashMap<>();
    
    @Incoming("price-updates-in")
    @Transactional
    public void processPriceUpdate(PriceUpdate priceUpdate) {
        LOG.info("Processing price update for " + priceUpdate.destination);
        
        // Sauvegarder l'événement en base
        savePriceEvent(priceUpdate);
        
        // Règle P2: Baisse de prix > 10%
        if (priceUpdate.getPriceChangePercentage() < -10) {
            Map<String, Object> details = new HashMap<>();
            details.put("provider", priceUpdate.provider);
            details.put("oldPrice", priceUpdate.oldPrice);
            details.put("newPrice", priceUpdate.newPrice);
            details.put("currency", priceUpdate.currency);
            details.put("changePercentage", priceUpdate.getPriceChangePercentage());
            
            AlertAggregate alert = new AlertAggregate(
                "PRICE",
                priceUpdate.destination,
                "P2",
                "Baisse de prix significative à " + priceUpdate.destination,
                details
            );
            
            // Sauvegarder l'alerte en base
            saveAlertAggregate(alert);
            
            alerts.add(alert);
            alertEmitter.send(alert);
            LOG.info("Generated P2 alert for price drop: " + priceUpdate.destination);
        }
        
        updateCounter("price-updates");
    }
    
    @Incoming("weather-alerts-in")
    public void processWeatherAlert(WeatherAlert weatherAlert) {
        LOG.info("Processing weather alert for " + weatherAlert.destination);
        
        // Règle P1: Météo rouge
        if (weatherAlert.isRedAlert()) {
            Map<String, Object> details = new HashMap<>();
            details.put("level", weatherAlert.level);
            details.put("message", weatherAlert.message);
            
            AlertAggregate alert = new AlertAggregate(
                "WEATHER",
                weatherAlert.destination,
                "P1",
                "Alerte météo rouge à " + weatherAlert.destination,
                details
            );
            
            alerts.add(alert);
            alertEmitter.send(alert);
            LOG.info("Generated P1 alert for red weather: " + weatherAlert.destination);
        }
        
        updateCounter("weather-alerts");
    }
    
    @Incoming("flight-status-in")
    public void processFlightStatus(FlightStatus flightStatus) {
        LOG.info("Processing flight status for " + flightStatus.flightNo);
        
        // Règle P3: Changements de statut de vol
        if (!"ON_TIME".equals(flightStatus.status)) {
            Map<String, Object> details = new HashMap<>();
            details.put("flightNo", flightStatus.flightNo);
            details.put("status", flightStatus.status);
            details.put("departure", flightStatus.departure);
            details.put("arrival", flightStatus.arrival);
            
            AlertAggregate alert = new AlertAggregate(
                "FLIGHT",
                flightStatus.arrival,
                "P3",
                "Changement de statut vol " + flightStatus.flightNo,
                details
            );
            
            alerts.add(alert);
            alertEmitter.send(alert);
            LOG.info("Generated P3 alert for flight status: " + flightStatus.flightNo);
        }
        
        updateCounter("flight-status");
    }
    
    @Incoming("visa-reminders-in")
    public void processVisaReminder(VisaReminder visaReminder) {
        LOG.info("Processing visa reminder for " + visaReminder.country);
        
        // Règle P3: Rappels visa
        Map<String, Object> details = new HashMap<>();
        details.put("country", visaReminder.country);
        details.put("daysBefore", visaReminder.daysBefore);
        details.put("message", visaReminder.message);
        
        AlertAggregate alert = new AlertAggregate(
            "VISA",
            visaReminder.country,
            "P3",
            "Rappel visa " + visaReminder.country + " dans " + visaReminder.daysBefore + " jours",
            details
        );
        
        alerts.add(alert);
        alertEmitter.send(alert);
        LOG.info("Generated P3 alert for visa reminder: " + visaReminder.country);
        
        updateCounter("visa-reminders");
    }
    
    public List<AlertAggregate> getAlerts() {
        return List.copyOf(alerts);
    }
    
    public Map<String, Object> getEventCounters() {
        return new HashMap<>(eventCounters);
    }
    
    private void updateCounter(String eventType) {
        eventCounters.merge(eventType, 1, (old, newVal) -> (Integer) old + 1);
    }
    
    @Transactional
    private void savePriceEvent(PriceUpdate priceUpdate) {
        try {
            // Trouver ou créer la destination
            Destination destination = (Destination) Destination.find("name", priceUpdate.destination)
                    .firstResultOptional()
                    .orElseGet(() -> {
                        Destination newDest = new Destination();
                        newDest.name = priceUpdate.destination;
                        newDest.country = "Unknown";
                        newDest.persist();
                        return newDest;
                    });
            
            // Trouver ou créer le provider
            Provider provider = (Provider) Provider.find("name", priceUpdate.provider)
                    .firstResultOptional()
                    .orElseGet(() -> {
                        Provider newProv = new Provider();
                        newProv.name = priceUpdate.provider;
                        newProv.type = "AIRLINE";
                        newProv.persist();
                        return newProv;
                    });
            
            // Créer l'événement de prix
            PriceEvent event = new PriceEvent();
            event.destination = destination;
            event.provider = provider;
            event.oldPrice = java.math.BigDecimal.valueOf(priceUpdate.oldPrice);
            event.newPrice = java.math.BigDecimal.valueOf(priceUpdate.newPrice);
            event.currency = priceUpdate.currency;
            event.eventTimestamp = priceUpdate.ts.atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
            event.persist();
        } catch (Exception e) {
            LOG.error("Error saving price event: " + e.getMessage(), e);
        }
    }
    
    @Transactional
    private void saveAlertAggregate(AlertAggregate alert) {
        try {
            AlertAggregateEntity entity = new AlertAggregateEntity();
            entity.type = alert.type;
            entity.destination = alert.destination;
            entity.priority = alert.priority;
            entity.title = alert.title;
            entity.details = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(alert.details);
            entity.eventTimestamp = alert.ts.atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
            entity.persist();
        } catch (Exception e) {
            LOG.error("Error saving alert aggregate: " + e.getMessage(), e);
        }
    }
}
