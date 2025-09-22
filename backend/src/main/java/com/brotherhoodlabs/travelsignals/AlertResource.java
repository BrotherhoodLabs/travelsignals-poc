package com.brotherhoodlabs.travelsignals;

import com.brotherhoodlabs.travelsignals.model.AlertAggregate;
import com.brotherhoodlabs.travelsignals.entity.AlertAggregateEntity;
import com.brotherhoodlabs.travelsignals.service.AlertAggregationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/api/alerts")
@Tag(name = "Alerts", description = "API de gestion des alertes de voyage")
public class AlertResource {
    
    @Inject
    AlertAggregationService alertService;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Récupérer les alertes", description = "Récupère la liste des alertes agrégées")
    public Response getAlerts(@QueryParam("limit") Integer limit) {
        List<AlertAggregateEntity> entities = AlertAggregateEntity.findRecent(limit != null ? limit : 100);
        
        List<AlertAggregate> alerts = entities.stream()
            .map(this::convertToModel)
            .collect(Collectors.toList());
        
        return Response.ok(alerts).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Récupérer une alerte par ID")
    public Response getAlert(@PathParam("id") Long id) {
        AlertAggregateEntity entity = AlertAggregateEntity.findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(convertToModel(entity)).build();
    }
    
    @GET
    @Path("/type/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Récupérer les alertes par type")
    public Response getAlertsByType(@PathParam("type") String type) {
        List<AlertAggregateEntity> entities = AlertAggregateEntity.findByType(type);
        List<AlertAggregate> alerts = entities.stream()
            .map(this::convertToModel)
            .collect(Collectors.toList());
        return Response.ok(alerts).build();
    }
    
    @GET
    @Path("/priority/{priority}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Récupérer les alertes par priorité")
    public Response getAlertsByPriority(@PathParam("priority") String priority) {
        List<AlertAggregateEntity> entities = AlertAggregateEntity.findByPriority(priority);
        List<AlertAggregate> alerts = entities.stream()
            .map(this::convertToModel)
            .collect(Collectors.toList());
        return Response.ok(alerts).build();
    }
    
    private AlertAggregate convertToModel(AlertAggregateEntity entity) {
        AlertAggregate alert = new AlertAggregate();
        alert.type = entity.type;
        alert.destination = entity.destination;
        alert.priority = entity.priority;
        alert.title = entity.title;
        alert.ts = entity.eventTimestamp.atZone(java.time.ZoneId.systemDefault()).toInstant();
        
        // Parse JSON details
        try {
            if (entity.details != null) {
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                alert.details = mapper.readValue(entity.details, Map.class);
            }
        } catch (Exception e) {
            alert.details = new java.util.HashMap<>();
        }
        
        return alert;
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
