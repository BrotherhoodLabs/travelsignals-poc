package com.brotherhoodlabs.travelsignals.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "destinations")
public class Destination extends PanacheEntity {
    
    @Column(name = "name", unique = true, nullable = false)
    public String name;
    
    @Column(name = "country", nullable = false)
    public String country;
    
    @Column(name = "timezone")
    public String timezone;
    
    @Column(name = "created_at")
    public LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<PriceEvent> priceEvents;
    
    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<WeatherEvent> weatherEvents;
    
    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
    
    public static List<Destination> findByName(String name) {
        return find("name", name).list();
    }
    
    public static List<Destination> findByCountry(String country) {
        return find("country", country).list();
    }
}
