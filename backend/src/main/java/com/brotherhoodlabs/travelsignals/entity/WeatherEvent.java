package com.brotherhoodlabs.travelsignals.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "weather_events")
public class WeatherEvent extends PanacheEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id", nullable = false)
    public Destination destination;
    
    @Column(name = "level", nullable = false, length = 10)
    public String level; // GREEN, YELLOW, RED
    
    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    public String message;
    
    @Column(name = "event_timestamp", nullable = false)
    public LocalDateTime eventTimestamp;
    
    @Column(name = "created_at")
    public LocalDateTime createdAt;
    
    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
    
    public boolean isRedAlert() {
        return "RED".equals(level);
    }
    
    public static List<WeatherEvent> findByDestination(Long destinationId) {
        return find("destination.id", destinationId).list();
    }
    
    public static List<WeatherEvent> findByLevel(String level) {
        return find("level", level).list();
    }
    
    public static List<WeatherEvent> findRedAlerts() {
        return find("level", "RED").list();
    }
    
    public static List<WeatherEvent> findRecent(int limit) {
        return find("ORDER BY eventTimestamp DESC").page(0, limit).list();
    }
}