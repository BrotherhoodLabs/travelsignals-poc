package com.brotherhoodlabs.travelsignals.service;

import com.brotherhoodlabs.travelsignals.model.*;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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
    
    @Incoming("price-updates")
    public void processPriceUpdate(PriceUpdate priceUpdate) {
        LOG.info("Processing price update for " + priceUpdate.destination);
        
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
            
            alerts.add(alert);
            alertEmitter.send(alert);
            LOG.info("Generated P2 alert for price drop: " + priceUpdate.destination);
        }
        
        updateCounter("price-updates");
    }
    
    @Incoming("weather-alerts")
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
    
    @Incoming("flight-status")
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
    
    @Incoming("visa-reminders")
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
}
