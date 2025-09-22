package com.brotherhoodlabs.travelsignals;

import com.brotherhoodlabs.travelsignals.entity.AlertAggregateEntity;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.util.Random;

@Path("/api/generate")
@Tag(name = "Alert Generator", description = "API pour générer des alertes en temps réel")
public class AlertGeneratorResource {

    private final Random random = new Random();
    private final String[] destinations = {"Paris", "Londres", "New York", "Tokyo", "Dubai", "Bangkok", "Sydney", "Rome"};
    private final String[] providers = {"Air France", "Lufthansa", "Emirates", "Singapore Airlines", "British Airways"};

    @POST
    @Path("/price-alert")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Générer une alerte de prix")
    public Response generatePriceAlert() {
        try {
            String destination = destinations[random.nextInt(destinations.length)];
            String provider = providers[random.nextInt(providers.length)];
            double oldPrice = 500 + random.nextDouble() * 1000;
            double changePercentage = -30 + random.nextDouble() * 20; // -30% à -10%
            double newPrice = oldPrice * (1 + changePercentage / 100);

            AlertAggregateEntity alert = new AlertAggregateEntity();
            alert.type = "PRICE";
            alert.destination = destination;
            alert.priority = "P2";
            alert.title = "Baisse de prix significative à " + destination;
            alert.details = String.format(
                "{\"provider\":\"%s\",\"oldPrice\":%.2f,\"newPrice\":%.2f,\"currency\":\"EUR\",\"changePercentage\":%.2f}",
                provider, oldPrice, newPrice, changePercentage
            );
            alert.eventTimestamp = LocalDateTime.now();
            alert.persist();

            return Response.ok("Alerte de prix générée pour " + destination).build();
        } catch (Exception e) {
            return Response.status(500).entity("Erreur: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/weather-alert")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Générer une alerte météo")
    public Response generateWeatherAlert() {
        try {
            String destination = destinations[random.nextInt(destinations.length)];
            String[] levels = {"RED", "YELLOW"};
            String[] messages = {
                "Tempête violente avec vents de 120 km/h",
                "Alerte typhon niveau 2",
                "Pluie torrentielle prévue",
                "Vent fort et conditions dangereuses"
            };
            
            String level = levels[random.nextInt(levels.length)];
            String message = messages[random.nextInt(messages.length)];
            String priority = "RED".equals(level) ? "P1" : "P2";

            AlertAggregateEntity alert = new AlertAggregateEntity();
            alert.type = "WEATHER";
            alert.destination = destination;
            alert.priority = priority;
            alert.title = "Alerte météo " + level + " à " + destination;
            alert.details = String.format(
                "{\"level\":\"%s\",\"message\":\"%s\"}",
                level, message
            );
            alert.eventTimestamp = LocalDateTime.now();
            alert.persist();

            return Response.ok("Alerte météo générée pour " + destination).build();
        } catch (Exception e) {
            return Response.status(500).entity("Erreur: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/flight-alert")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Générer une alerte de vol")
    public Response generateFlightAlert() {
        try {
            String destination = destinations[random.nextInt(destinations.length)];
            String[] statuses = {"DELAYED", "CANCELLED"};
            String[] flightNumbers = {"AF123", "LH456", "EK789", "SQ321", "BA654"};
            
            String status = statuses[random.nextInt(statuses.length)];
            String flightNo = flightNumbers[random.nextInt(flightNumbers.length)];

            AlertAggregateEntity alert = new AlertAggregateEntity();
            alert.type = "FLIGHT";
            alert.destination = destination;
            alert.priority = "P3";
            alert.title = "Changement de statut vol " + flightNo;
            alert.details = String.format(
                "{\"flightNo\":\"%s\",\"status\":\"%s\",\"departure\":\"CDG\",\"arrival\":\"%s\"}",
                flightNo, status, destination
            );
            alert.eventTimestamp = LocalDateTime.now();
            alert.persist();

            return Response.ok("Alerte de vol générée pour " + flightNo).build();
        } catch (Exception e) {
            return Response.status(500).entity("Erreur: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/visa-alert")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Générer une alerte visa")
    public Response generateVisaAlert() {
        try {
            String[] countries = {"États-Unis", "Japon", "Australie", "Chine", "Inde", "Canada", "Brésil"};
            String country = countries[random.nextInt(countries.length)];
            int daysBefore = 1 + random.nextInt(30);

            AlertAggregateEntity alert = new AlertAggregateEntity();
            alert.type = "VISA";
            alert.destination = country;
            alert.priority = "P3";
            alert.title = "Rappel visa " + country + " dans " + daysBefore + " jours";
            alert.details = String.format(
                "{\"country\":\"%s\",\"daysBefore\":%d,\"message\":\"Vérifiez les exigences de visa pour %s\"}",
                country, daysBefore, country
            );
            alert.eventTimestamp = LocalDateTime.now();
            alert.persist();

            return Response.ok("Alerte visa générée pour " + country).build();
        } catch (Exception e) {
            return Response.status(500).entity("Erreur: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/random")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Générer une alerte aléatoire")
    public Response generateRandomAlert() {
        int choice = random.nextInt(4);
        switch (choice) {
            case 0: return generatePriceAlert();
            case 1: return generateWeatherAlert();
            case 2: return generateFlightAlert();
            case 3: return generateVisaAlert();
            default: return generatePriceAlert();
        }
    }
}
