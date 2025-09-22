package com.brotherhoodlabs.travelsignals;

import com.brotherhoodlabs.travelsignals.entity.Provider;
import com.brotherhoodlabs.travelsignals.service.ProviderService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("/api/providers")
@Tag(name = "Providers", description = "API de gestion des fournisseurs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProviderResource {
    
    @Inject
    ProviderService providerService;
    
    @GET
    @Operation(summary = "Lister tous les fournisseurs")
    public List<Provider> getAllProviders() {
        return providerService.findAll();
    }
    
    @GET
    @Path("/{id}")
    @Operation(summary = "Récupérer un fournisseur par ID")
    public Response getProvider(@PathParam("id") Long id) {
        Optional<Provider> provider = providerService.findById(id);
        return provider.map(p -> Response.ok(p).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
    
    @GET
    @Path("/search")
    @Operation(summary = "Rechercher des fournisseurs par nom")
    public List<Provider> searchProviders(@QueryParam("name") String name) {
        return providerService.findByName(name);
    }
    
    @GET
    @Path("/type/{type}")
    @Operation(summary = "Lister les fournisseurs par type")
    public List<Provider> getProvidersByType(@PathParam("type") String type) {
        return providerService.findByType(type);
    }
    
    @GET
    @Path("/airlines")
    @Operation(summary = "Lister les compagnies aériennes")
    public List<Provider> getAirlines() {
        return providerService.findAirlines();
    }
    
    @GET
    @Path("/hotels")
    @Operation(summary = "Lister les hôtels")
    public List<Provider> getHotels() {
        return providerService.findHotels();
    }
    
    @GET
    @Path("/activities")
    @Operation(summary = "Lister les activités")
    public List<Provider> getActivities() {
        return providerService.findActivities();
    }
    
    @POST
    @Operation(summary = "Créer un nouveau fournisseur")
    public Response createProvider(Provider provider) {
        try {
            Provider created = providerService.create(provider);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erreur lors de la création: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    @Operation(summary = "Mettre à jour un fournisseur")
    public Response updateProvider(@PathParam("id") Long id, Provider provider) {
        Optional<Provider> updated = providerService.update(id, provider);
        return updated.map(p -> Response.ok(p).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
    
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Supprimer un fournisseur")
    public Response deleteProvider(@PathParam("id") Long id) {
        boolean deleted = providerService.delete(id);
        return deleted ? Response.noContent().build() 
                      : Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @GET
    @Path("/count")
    @Operation(summary = "Compter le nombre de fournisseurs")
    public Response getProviderCount() {
        long count = providerService.count();
        return Response.ok().entity("{\"count\": " + count + "}").build();
    }
}
