package com.brotherhoodlabs.travelsignals;

import com.brotherhoodlabs.travelsignals.model.AlertAggregate;
import com.brotherhoodlabs.travelsignals.service.AlertAggregationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Map;

@Path("/api/alerts")
@Tag(name = "Alerts", description = "API de gestion des alertes de voyage")
public class AlertResource {
    
    @Inject
    AlertAggregationService alertService;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Récupérer les alertes", description = "Récupère la liste des alertes agrégées")
    public Response getAlerts(@QueryParam("limit") Integer limit) {
        List<AlertAggregate> alerts = alertService.getAlerts();
        
        if (limit != null && limit > 0) {
            alerts = alerts.stream()
                .sorted((a, b) -> b.ts.compareTo(a.ts))
                .limit(limit)
                .toList();
        }
        
        return Response.ok(alerts).build();
    }
    
    @GET
    @Path("/counters")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Récupérer les compteurs", description = "Récupère les compteurs d'événements")
    public Response getCounters() {
        Map<String, Object> counters = alertService.getEventCounters();
        return Response.ok(counters).build();
    }
}
