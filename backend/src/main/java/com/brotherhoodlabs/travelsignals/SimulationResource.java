package com.brotherhoodlabs.travelsignals;

import com.brotherhoodlabs.travelsignals.model.PriceUpdate;
import com.brotherhoodlabs.travelsignals.model.WeatherAlert;
import com.brotherhoodlabs.travelsignals.model.FlightStatus;
import com.brotherhoodlabs.travelsignals.model.VisaReminder;
import com.brotherhoodlabs.travelsignals.service.EventSimulatorService;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.time.Instant;
import java.util.Random;

@Path("/api/simulate")
@Tag(name = "Simulation", description = "API de simulation d'événements")
public class SimulationResource {

    @Inject
    EventSimulatorService eventSimulator;

    private final Random random = new Random();

    @POST
    @Path("/price-update")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Simuler une mise à jour de prix")
    public Response simulatePriceUpdate() {
        try {
            PriceUpdate priceUpdate = new PriceUpdate();
            priceUpdate.destination = "Paris";
            priceUpdate.provider = "Air France";
            priceUpdate.oldPrice = 500.0;
            priceUpdate.newPrice = 400.0; // Baisse de 20% pour déclencher une alerte
            priceUpdate.currency = "EUR";
            priceUpdate.ts = Instant.now();

            eventSimulator.priceEmitter.send(priceUpdate);
            return Response.ok("Price update sent").build();
        } catch (Exception e) {
            return Response.status(500).entity("Error: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/weather-alert")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Simuler une alerte météo")
    public Response simulateWeatherAlert() {
        try {
            WeatherAlert weatherAlert = new WeatherAlert();
            weatherAlert.destination = "Tokyo";
            weatherAlert.level = "RED";
            weatherAlert.message = "Typhon imminent";
            weatherAlert.ts = Instant.now();

            eventSimulator.weatherEmitter.send(weatherAlert);
            return Response.ok("Weather alert sent").build();
        } catch (Exception e) {
            return Response.status(500).entity("Error: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/flight-status")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Simuler un changement de statut de vol")
    public Response simulateFlightStatus() {
        try {
            FlightStatus flightStatus = new FlightStatus();
            flightStatus.flightNo = "AF123";
            flightStatus.arrival = "New York";
            flightStatus.status = "DELAYED";
            flightStatus.ts = Instant.now();

            eventSimulator.flightEmitter.send(flightStatus);
            return Response.ok("Flight status sent").build();
        } catch (Exception e) {
            return Response.status(500).entity("Error: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/visa-reminder")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Simuler un rappel de visa")
    public Response simulateVisaReminder() {
        try {
            VisaReminder visaReminder = new VisaReminder();
            visaReminder.country = "Chine";
            visaReminder.daysBefore = 30;
            visaReminder.message = "Vérifiez les exigences de visa pour la Chine";
            visaReminder.ts = Instant.now();

            eventSimulator.visaEmitter.send(visaReminder);
            return Response.ok("Visa reminder sent").build();
        } catch (Exception e) {
            return Response.status(500).entity("Error: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Simuler tous les types d'événements")
    public Response simulateAll() {
        try {
            simulatePriceUpdate();
            simulateWeatherAlert();
            simulateFlightStatus();
            simulateVisaReminder();
            return Response.ok("All events sent").build();
        } catch (Exception e) {
            return Response.status(500).entity("Error: " + e.getMessage()).build();
        }
    }
}
