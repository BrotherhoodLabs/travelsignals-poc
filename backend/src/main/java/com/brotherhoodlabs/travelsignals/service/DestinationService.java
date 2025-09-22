package com.brotherhoodlabs.travelsignals.service;

import com.brotherhoodlabs.travelsignals.entity.Destination;
import com.brotherhoodlabs.travelsignals.repository.DestinationRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class DestinationService {
    
    @Inject
    DestinationRepository destinationRepository;
    
    public List<Destination> findAll() {
        return destinationRepository.listAll();
    }
    
    public Optional<Destination> findById(Long id) {
        return destinationRepository.findByIdOptional(id);
    }
    
    public List<Destination> findByName(String name) {
        return destinationRepository.findByNameContaining(name);
    }
    
    public List<Destination> findByCountry(String country) {
        return destinationRepository.findByCountry(country);
    }
    
    @Transactional
    public Destination create(Destination destination) {
        destination.persist();
        return destination;
    }
    
    @Transactional
    public Optional<Destination> update(Long id, Destination updatedDestination) {
        return destinationRepository.findByIdOptional(id)
                .map(destination -> {
                    destination.name = updatedDestination.name;
                    destination.country = updatedDestination.country;
                    destination.timezone = updatedDestination.timezone;
                    return destination;
                });
    }
    
    @Transactional
    public boolean delete(Long id) {
        return destinationRepository.deleteById(id);
    }
    
    public long count() {
        return destinationRepository.count();
    }
}
