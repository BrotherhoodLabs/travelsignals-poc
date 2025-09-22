package com.brotherhoodlabs.travelsignals.service;

import com.brotherhoodlabs.travelsignals.entity.Provider;
import com.brotherhoodlabs.travelsignals.repository.ProviderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProviderService {
    
    @Inject
    ProviderRepository providerRepository;
    
    public List<Provider> findAll() {
        return providerRepository.listAll();
    }
    
    public Optional<Provider> findById(Long id) {
        return providerRepository.findByIdOptional(id);
    }
    
    public List<Provider> findByName(String name) {
        return providerRepository.findByNameContaining(name);
    }
    
    public List<Provider> findByType(String type) {
        return providerRepository.findByType(type);
    }
    
    public List<Provider> findAirlines() {
        return providerRepository.findAirlines();
    }
    
    public List<Provider> findHotels() {
        return providerRepository.findHotels();
    }
    
    public List<Provider> findActivities() {
        return providerRepository.findActivities();
    }
    
    @Transactional
    public Provider create(Provider provider) {
        provider.persist();
        return provider;
    }
    
    @Transactional
    public Optional<Provider> update(Long id, Provider updatedProvider) {
        return providerRepository.findByIdOptional(id)
                .map(provider -> {
                    provider.name = updatedProvider.name;
                    provider.type = updatedProvider.type;
                    provider.website = updatedProvider.website;
                    return provider;
                });
    }
    
    @Transactional
    public boolean delete(Long id) {
        return providerRepository.deleteById(id);
    }
    
    public long count() {
        return providerRepository.count();
    }
}
