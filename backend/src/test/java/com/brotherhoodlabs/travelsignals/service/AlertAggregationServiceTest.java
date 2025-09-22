package com.brotherhoodlabs.travelsignals.service;

import com.brotherhoodlabs.travelsignals.model.*;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class AlertAggregationServiceTest {

    @Inject
    AlertAggregationService alertService;

    @Test
    public void testPriceUpdateWithSignificantDrop() {
        // Test P2: Baisse de prix > 10%
        PriceUpdate priceUpdate = new PriceUpdate("Paris", "Air France", 1000.0, 800.0, "EUR");
        alertService.processPriceUpdate(priceUpdate);
        
        List<AlertAggregate> alerts = alertService.getAlerts();
        assertFalse(alerts.isEmpty());
        
        AlertAggregate alert = alerts.get(0);
        assertEquals("PRICE", alert.type);
        assertEquals("Paris", alert.destination);
        assertEquals("P2", alert.priority);
        assertTrue(alert.title.contains("Baisse de prix significative"));
    }

    @Test
    public void testWeatherRedAlert() {
        // Test P1: Météo rouge
        WeatherAlert weatherAlert = new WeatherAlert("Paris", "RED", "Tempête violente");
        alertService.processWeatherAlert(weatherAlert);
        
        List<AlertAggregate> alerts = alertService.getAlerts();
        assertFalse(alerts.isEmpty());
        
        AlertAggregate alert = alerts.stream()
            .filter(a -> "WEATHER".equals(a.type))
            .findFirst()
            .orElse(null);
        
        assertNotNull(alert);
        assertEquals("WEATHER", alert.type);
        assertEquals("Paris", alert.destination);
        assertEquals("P1", alert.priority);
        assertTrue(alert.title.contains("Alerte météo rouge"));
    }

    @Test
    public void testFlightStatusChange() {
        // Test P3: Changement de statut de vol
        FlightStatus flightStatus = new FlightStatus("AF123", "DELAYED", "CDG", "JFK");
        alertService.processFlightStatus(flightStatus);
        
        List<AlertAggregate> alerts = alertService.getAlerts();
        assertFalse(alerts.isEmpty());
        
        AlertAggregate alert = alerts.stream()
            .filter(a -> "FLIGHT".equals(a.type))
            .findFirst()
            .orElse(null);
        
        assertNotNull(alert);
        assertEquals("FLIGHT", alert.type);
        assertEquals("JFK", alert.destination);
        assertEquals("P3", alert.priority);
        assertTrue(alert.title.contains("Changement de statut vol"));
    }
}
