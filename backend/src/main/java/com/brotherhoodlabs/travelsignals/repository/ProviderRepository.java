package com.brotherhoodlabs.travelsignals.repository;

import com.brotherhoodlabs.travelsignals.entity.Provider;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProviderRepository implements PanacheRepository<Provider> {
    
    public List<Provider> findByNameContaining(String name) {
        return find("name ILIKE ?1", "%" + name + "%").list();
    }
    
    public List<Provider> findByType(String type) {
        return find("type", type).list();
    }
    
    public Optional<Provider> findByName(String name) {
        return find("name", name).firstResultOptional();
    }
    
    public List<Provider> findAirlines() {
        return find("type", "AIRLINE").list();
    }
    
    public List<Provider> findHotels() {
        return find("type", "HOTEL").list();
    }
    
    public List<Provider> findActivities() {
        return find("type", "ACTIVITY").list();
    }
}
