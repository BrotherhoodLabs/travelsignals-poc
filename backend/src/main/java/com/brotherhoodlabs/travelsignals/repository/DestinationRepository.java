package com.brotherhoodlabs.travelsignals.repository;

import com.brotherhoodlabs.travelsignals.entity.Destination;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class DestinationRepository implements PanacheRepository<Destination> {
    
    public List<Destination> findByNameContaining(String name) {
        return find("name ILIKE ?1", "%" + name + "%").list();
    }
    
    public List<Destination> findByCountry(String country) {
        return find("country", country).list();
    }
    
    public Optional<Destination> findByName(String name) {
        return find("name", name).firstResultOptional();
    }
    
    public List<Destination> findWithEvents() {
        return find("SELECT DISTINCT d FROM Destination d LEFT JOIN FETCH d.priceEvents LEFT JOIN FETCH d.weatherEvents").list();
    }
}
