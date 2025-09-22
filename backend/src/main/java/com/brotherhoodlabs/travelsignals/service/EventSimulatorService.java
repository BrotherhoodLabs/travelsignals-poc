package com.brotherhoodlabs.travelsignals.service;

import com.brotherhoodlabs.travelsignals.model.*;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class EventSimulatorService {
    
    private static final Logger LOG = Logger.getLogger(EventSimulatorService.class);
    
    @Inject
    @Channel("price-updates")
    Emitter<PriceUpdate> priceEmitter;
    
    @Inject
    @Channel("weather-alerts")
    Emitter<WeatherAlert> weatherEmitter;
    
    @Inject
    @Channel("flight-status")
    Emitter<FlightStatus> flightEmitter;
    
    @Inject
    @Channel("visa-reminders")
    Emitter<VisaReminder> visaEmitter;
    
    private ScheduledExecutorService scheduler;
    private final Random random = new Random();
    
    private final List<String> destinations = Arrays.asList(
        "Paris", "Londres", "New York", "Tokyo", "Dubai", "Bangkok", "Sydney", "Rome"
    );
    
    private final List<String> providers = Arrays.asList(
        "Air France", "Lufthansa", "Emirates", "Singapore Airlines", "British Airways"
    );
    
    private final List<String> weatherLevels = Arrays.asList("GREEN", "YELLOW", "RED");
    
    private final List<String> flightStatuses = Arrays.asList("ON_TIME", "DELAYED", "CANCELLED");
    
    private final List<String> countries = Arrays.asList(
        "France", "Allemagne", "États-Unis", "Japon", "Émirats arabes unis", "Thaïlande", "Australie", "Italie"
    );
    
    @PostConstruct
    public void startSimulation() {
        LOG.info("Starting event simulation...");
        scheduler = Executors.newScheduledThreadPool(4);
        
        // Simuler les mises à jour de prix toutes les 30 secondes
        scheduler.scheduleAtFixedRate(this::simulatePriceUpdate, 0, 30, TimeUnit.SECONDS);
        
        // Simuler les alertes météo toutes les 45 secondes
        scheduler.scheduleAtFixedRate(this::simulateWeatherAlert, 10, 45, TimeUnit.SECONDS);
        
        // Simuler les statuts de vol toutes les 60 secondes
        scheduler.scheduleAtFixedRate(this::simulateFlightStatus, 20, 60, TimeUnit.SECONDS);
        
        // Simuler les rappels visa toutes les 90 secondes
        scheduler.scheduleAtFixedRate(this::simulateVisaReminder, 30, 90, TimeUnit.SECONDS);
    }
    
    @PreDestroy
    public void stopSimulation() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }
    
    private void simulatePriceUpdate() {
        String destination = destinations.get(random.nextInt(destinations.size()));
        String provider = providers.get(random.nextInt(providers.size()));
        double oldPrice = 500 + random.nextDouble() * 1000;
        double changePercentage = -20 + random.nextDouble() * 40; // -20% à +20%
        double newPrice = oldPrice * (1 + changePercentage / 100);
        
        PriceUpdate priceUpdate = new PriceUpdate(
            destination, provider, oldPrice, newPrice, "EUR"
        );
        
        priceEmitter.send(priceUpdate);
        LOG.info("Simulated price update: " + destination + " " + provider + " " + oldPrice + " -> " + newPrice);
    }
    
    private void simulateWeatherAlert() {
        String destination = destinations.get(random.nextInt(destinations.size()));
        String level = weatherLevels.get(random.nextInt(weatherLevels.size()));
        String message = "Conditions météorologiques " + level.toLowerCase() + " à " + destination;
        
        WeatherAlert weatherAlert = new WeatherAlert(destination, level, message);
        weatherEmitter.send(weatherAlert);
        LOG.info("Simulated weather alert: " + destination + " " + level);
    }
    
    private void simulateFlightStatus() {
        String flightNo = "AF" + (1000 + random.nextInt(9000));
        String status = flightStatuses.get(random.nextInt(flightStatuses.size()));
        String departure = "CDG";
        String arrival = destinations.get(random.nextInt(destinations.size()));
        
        FlightStatus flightStatus = new FlightStatus(flightNo, status, departure, arrival);
        flightEmitter.send(flightStatus);
        LOG.info("Simulated flight status: " + flightNo + " " + status);
    }
    
    private void simulateVisaReminder() {
        String country = countries.get(random.nextInt(countries.size()));
        int daysBefore = 1 + random.nextInt(30);
        String message = "N'oubliez pas de vérifier les exigences de visa pour " + country;
        
        VisaReminder visaReminder = new VisaReminder(country, daysBefore, message);
        visaEmitter.send(visaReminder);
        LOG.info("Simulated visa reminder: " + country + " in " + daysBefore + " days");
    }
}
