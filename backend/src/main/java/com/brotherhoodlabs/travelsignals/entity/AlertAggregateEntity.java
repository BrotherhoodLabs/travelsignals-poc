package com.brotherhoodlabs.travelsignals.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "alert_aggregates")
public class AlertAggregateEntity extends PanacheEntity {
    
    @Column(name = "type", nullable = false, length = 20)
    public String type; // PRICE, WEATHER, FLIGHT, VISA
    
    @Column(name = "destination", nullable = false, length = 100)
    public String destination;
    
    @Column(name = "priority", nullable = false, length = 5)
    public String priority; // P1, P2, P3
    
    @Column(name = "title", nullable = false, length = 255)
    public String title;
    
    @Column(name = "details", columnDefinition = "jsonb")
    public String details; // JSON string
    
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
    
    public boolean isHighPriority() {
        return "P1".equals(priority);
    }
    
    public boolean isMediumPriority() {
        return "P2".equals(priority);
    }
    
    public boolean isLowPriority() {
        return "P3".equals(priority);
    }
    
    public static List<AlertAggregateEntity> findByType(String type) {
        return find("type", type).list();
    }
    
    public static List<AlertAggregateEntity> findByPriority(String priority) {
        return find("priority", priority).list();
    }
    
    public static List<AlertAggregateEntity> findByDestination(String destination) {
        return find("destination", destination).list();
    }
    
    public static List<AlertAggregateEntity> findHighPriority() {
        return find("priority", "P1").list();
    }
    
    public static List<AlertAggregateEntity> findRecent(int limit) {
        return find("ORDER BY eventTimestamp DESC").page(0, limit).list();
    }
}