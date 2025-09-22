package com.brotherhoodlabs.travelsignals;

import com.brotherhoodlabs.travelsignals.entity.Destination;
import com.brotherhoodlabs.travelsignals.service.DestinationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("/api/destinations")
@Tag(name = "Destinations", description = "API de gestion des destinations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DestinationResource {
    
    @Inject
    DestinationService destinationService;
    
    @GET
    @Operation(summary = "Lister toutes les destinations")
    public List<Destination> getAllDestinations() {
        return destinationService.findAll();
    }
    
    @GET
    @Path("/{id}")
    @Operation(summary = "Récupérer une destination par ID")
    public Response getDestination(@PathParam("id") Long id) {
        Optional<Destination> destination = destinationService.findById(id);
        return destination.map(d -> Response.ok(d).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
    
    @GET
    @Path("/search")
    @Operation(summary = "Rechercher des destinations par nom")
    public List<Destination> searchDestinations(@QueryParam("name") String name) {
        return destinationService.findByName(name);
    }
    
    @GET
    @Path("/country/{country}")
    @Operation(summary = "Lister les destinations par pays")
    public List<Destination> getDestinationsByCountry(@PathParam("country") String country) {
        return destinationService.findByCountry(country);
    }
    
    @POST
    @Operation(summary = "Créer une nouvelle destination")
    public Response createDestination(Destination destination) {
        try {
            Destination created = destinationService.create(destination);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erreur lors de la création: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    @Operation(summary = "Mettre à jour une destination")
    public Response updateDestination(@PathParam("id") Long id, Destination destination) {
        Optional<Destination> updated = destinationService.update(id, destination);
        return updated.map(d -> Response.ok(d).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
    
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Supprimer une destination")
    public Response deleteDestination(@PathParam("id") Long id) {
        boolean deleted = destinationService.delete(id);
        return deleted ? Response.noContent().build() 
                      : Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @GET
    @Path("/count")
    @Operation(summary = "Compter le nombre de destinations")
    public Response getDestinationCount() {
        long count = destinationService.count();
        return Response.ok().entity("{\"count\": " + count + "}").build();
    }
}
