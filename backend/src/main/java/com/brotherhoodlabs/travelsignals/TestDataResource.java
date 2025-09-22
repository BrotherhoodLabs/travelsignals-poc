package com.brotherhoodlabs.travelsignals;

import com.brotherhoodlabs.travelsignals.entity.AlertAggregateEntity;
import com.brotherhoodlabs.travelsignals.entity.Destination;
import com.brotherhoodlabs.travelsignals.entity.Provider;
import com.brotherhoodlabs.travelsignals.entity.PriceEvent;
import com.brotherhoodlabs.travelsignals.entity.WeatherEvent;
import com.brotherhoodlabs.travelsignals.entity.FlightEvent;
import com.brotherhoodlabs.travelsignals.entity.VisaEvent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Path("/api/test-data")
@Tag(name = "Test Data", description = "API pour créer des données de test")
public class TestDataResource {

    @Inject
    com.brotherhoodlabs.travelsignals.service.DestinationService destinationService;

    @Inject
    com.brotherhoodlabs.travelsignals.service.ProviderService providerService;

    @POST
    @Path("/populate")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Peupler la base de données avec des données de test")
    public Response populateTestData() {
        try {
            // Créer des destinations
            createDestinations();
            
            // Créer des providers
            createProviders();
            
            // Créer des alertes agrégées
            createAlertAggregates();
            
            // Créer des événements
            createEvents();
            
            return Response.ok("Données de test créées avec succès").build();
        } catch (Exception e) {
            return Response.status(500).entity("Erreur: " + e.getMessage()).build();
        }
    }

    private void createDestinations() {
        String[] destinations = {
            "Paris", "Londres", "New York", "Tokyo", "Dubai", 
            "Bangkok", "Sydney", "Rome", "Barcelone", "Amsterdam"
        };
        
        String[] countries = {
            "France", "Royaume-Uni", "États-Unis", "Japon", "Émirats arabes unis",
            "Thaïlande", "Australie", "Italie", "Espagne", "Pays-Bas"
        };

        for (int i = 0; i < destinations.length; i++) {
            Destination dest = new Destination();
            dest.name = destinations[i];
            dest.country = countries[i];
            dest.timezone = "Europe/Paris";
            dest.persist();
        }
    }

    private void createProviders() {
        String[][] providers = {
            {"Air France", "AIRLINE", "https://www.airfrance.fr"},
            {"Lufthansa", "AIRLINE", "https://www.lufthansa.com"},
            {"Emirates", "AIRLINE", "https://www.emirates.com"},
            {"Singapore Airlines", "AIRLINE", "https://www.singaporeair.com"},
            {"British Airways", "AIRLINE", "https://www.britishairways.com"},
            {"Booking.com", "HOTEL", "https://www.booking.com"},
            {"Expedia", "HOTEL", "https://www.expedia.com"},
            {"Airbnb", "HOTEL", "https://www.airbnb.com"}
        };

        for (String[] provider : providers) {
            Provider prov = new Provider();
            prov.name = provider[0];
            prov.type = provider[1];
            prov.website = provider[2];
            prov.persist();
        }
    }

    private void createAlertAggregates() {
        // Alerte prix P2
        AlertAggregateEntity alert1 = new AlertAggregateEntity();
        alert1.type = "PRICE";
        alert1.destination = "Paris";
        alert1.priority = "P2";
        alert1.title = "Baisse de prix significative à Paris";
        Map<String, Object> details1 = new HashMap<>();
        details1.put("provider", "Air France");
        details1.put("oldPrice", 800.0);
        details1.put("newPrice", 650.0);
        details1.put("currency", "EUR");
        details1.put("changePercentage", -18.75);
        alert1.details = "{\"provider\":\"Air France\",\"oldPrice\":800.0,\"newPrice\":650.0,\"currency\":\"EUR\",\"changePercentage\":-18.75}";
        alert1.eventTimestamp = LocalDateTime.now().minusHours(1);
        alert1.persist();

        // Alerte météo P1
        AlertAggregateEntity alert2 = new AlertAggregateEntity();
        alert2.type = "WEATHER";
        alert2.destination = "Paris";
        alert2.priority = "P1";
        alert2.title = "Alerte météo rouge à Paris";
        alert2.details = "{\"level\":\"RED\",\"message\":\"Tempête violente avec vents de 120 km/h\"}";
        alert2.eventTimestamp = LocalDateTime.now().minusHours(1);
        alert2.persist();

        // Alerte vol P3
        AlertAggregateEntity alert3 = new AlertAggregateEntity();
        alert3.type = "FLIGHT";
        alert3.destination = "New York";
        alert3.priority = "P3";
        alert3.title = "Changement de statut vol AF123";
        alert3.details = "{\"flightNo\":\"AF123\",\"status\":\"DELAYED\",\"departure\":\"CDG\",\"arrival\":\"JFK\"}";
        alert3.eventTimestamp = LocalDateTime.now().minusHours(1);
        alert3.persist();

        // Alerte visa P3
        AlertAggregateEntity alert4 = new AlertAggregateEntity();
        alert4.type = "VISA";
        alert4.destination = "États-Unis";
        alert4.priority = "P3";
        alert4.title = "Rappel visa États-Unis dans 7 jours";
        alert4.details = "{\"country\":\"États-Unis\",\"daysBefore\":7,\"message\":\"N'oubliez pas de faire votre ESTA\"}";
        alert4.eventTimestamp = LocalDateTime.now().minusHours(1);
        alert4.persist();

        // Alerte prix P2
        AlertAggregateEntity alert5 = new AlertAggregateEntity();
        alert5.type = "PRICE";
        alert5.destination = "Londres";
        alert5.priority = "P2";
        alert5.title = "Baisse de prix significative à Londres";
        alert5.details = "{\"provider\":\"Lufthansa\",\"oldPrice\":1200.0,\"newPrice\":900.0,\"currency\":\"EUR\",\"changePercentage\":-25.0}";
        alert5.eventTimestamp = LocalDateTime.now().minusHours(2);
        alert5.persist();

        // Alerte météo P2
        AlertAggregateEntity alert6 = new AlertAggregateEntity();
        alert6.type = "WEATHER";
        alert6.destination = "Tokyo";
        alert6.priority = "P2";
        alert6.title = "Alerte typhon niveau 2 à Tokyo";
        alert6.details = "{\"level\":\"YELLOW\",\"message\":\"Alerte typhon niveau 2\"}";
        alert6.eventTimestamp = LocalDateTime.now().minusMinutes(45);
        alert6.persist();
    }

    private void createEvents() {
        // Créer quelques événements de prix
        Destination paris = Destination.find("name", "Paris").firstResult();
        Provider airFrance = Provider.find("name", "Air France").firstResult();
        
        if (paris != null && airFrance != null) {
            PriceEvent priceEvent = new PriceEvent();
            priceEvent.destination = paris;
            priceEvent.provider = airFrance;
            priceEvent.oldPrice = java.math.BigDecimal.valueOf(800.0);
            priceEvent.newPrice = java.math.BigDecimal.valueOf(650.0);
            priceEvent.currency = "EUR";
            priceEvent.eventTimestamp = LocalDateTime.now().minusHours(1);
            priceEvent.persist();
        }

        // Créer quelques événements météo
        if (paris != null) {
            WeatherEvent weatherEvent = new WeatherEvent();
            weatherEvent.destination = paris;
            weatherEvent.level = "RED";
            weatherEvent.message = "Tempête violente avec vents de 120 km/h";
            weatherEvent.eventTimestamp = LocalDateTime.now().minusHours(1);
            weatherEvent.persist();
        }

        // Créer quelques événements de vol
        FlightEvent flightEvent = new FlightEvent();
        flightEvent.flightNumber = "AF123";
        flightEvent.departureAirport = "CDG";
        flightEvent.arrivalAirport = "JFK";
        flightEvent.status = "DELAYED";
        flightEvent.eventTimestamp = LocalDateTime.now().minusHours(1);
        flightEvent.persist();

        // Créer quelques événements visa
        VisaEvent visaEvent = new VisaEvent();
        visaEvent.country = "États-Unis";
        visaEvent.daysBefore = 7;
        visaEvent.message = "N'oubliez pas de faire votre ESTA";
        visaEvent.eventTimestamp = LocalDateTime.now().minusHours(1);
        visaEvent.persist();
    }

    @DELETE
    @Path("/clear")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Vider la base de données")
    public Response clearTestData() {
        try {
            AlertAggregateEntity.deleteAll();
            PriceEvent.deleteAll();
            WeatherEvent.deleteAll();
            FlightEvent.deleteAll();
            VisaEvent.deleteAll();
            Destination.deleteAll();
            Provider.deleteAll();
            
            return Response.ok("Base de données vidée").build();
        } catch (Exception e) {
            return Response.status(500).entity("Erreur: " + e.getMessage()).build();
        }
    }
}
