package com.brotherhoodlabs.travelsignals.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "visa_events")
public class VisaEvent extends PanacheEntity {
    
    @Column(name = "country", nullable = false, length = 100)
    public String country;
    
    @Column(name = "days_before", nullable = false)
    public Integer daysBefore;
    
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
    
    public boolean isUrgent() {
        return daysBefore != null && daysBefore <= 7;
    }
    
    public static List<VisaEvent> findByCountry(String country) {
        return find("country", country).list();
    }
    
    public static List<VisaEvent> findByDaysBefore(Integer daysBefore) {
        return find("daysBefore", daysBefore).list();
    }
    
    public static List<VisaEvent> findUrgent() {
        return find("daysBefore <= ?1", 7).list();
    }
    
    public static List<VisaEvent> findRecent(int limit) {
        return find("ORDER BY eventTimestamp DESC").page(0, limit).list();
    }
}