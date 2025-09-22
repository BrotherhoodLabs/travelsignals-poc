package com.brotherhoodlabs.travelsignals.entity;

import com.brotherhoodlabs.travelsignals.entity.enums.AlertType;
import com.brotherhoodlabs.travelsignals.entity.enums.Priority;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "alert_aggregates")
public class AlertAggregateEntity extends PanacheEntity {
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    public AlertType type;
    
    @Column(name = "destination", nullable = false, length = 100)
    public String destination;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 5)
    public Priority priority;
    
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
        return Priority.P1.equals(priority);
    }
    
    public boolean isMediumPriority() {
        return Priority.P2.equals(priority);
    }
    
    public boolean isLowPriority() {
        return Priority.P3.equals(priority);
    }
    
    public static List<AlertAggregateEntity> findByType(AlertType type) {
        return find("type", type).list();
    }
    
    public static List<AlertAggregateEntity> findByPriority(Priority priority) {
        return find("priority", priority).list();
    }
    
    public static List<AlertAggregateEntity> findByDestination(String destination) {
        return find("destination", destination).list();
    }
    
    public static List<AlertAggregateEntity> findHighPriority() {
        return find("priority", Priority.P1).list();
    }
    
    public static List<AlertAggregateEntity> findRecent(int limit) {
        return find("ORDER BY eventTimestamp DESC").page(0, limit).list();
    }
}