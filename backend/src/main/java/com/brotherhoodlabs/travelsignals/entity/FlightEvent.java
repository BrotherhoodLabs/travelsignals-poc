package com.brotherhoodlabs.travelsignals.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "flight_events")
public class FlightEvent extends PanacheEntity {
    
    @Column(name = "flight_number", nullable = false, length = 20)
    public String flightNumber;
    
    @Column(name = "departure_airport", nullable = false, length = 10)
    public String departureAirport;
    
    @Column(name = "arrival_airport", nullable = false, length = 10)
    public String arrivalAirport;
    
    @Column(name = "status", nullable = false, length = 20)
    public String status; // ON_TIME, DELAYED, CANCELLED
    
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
    
    public boolean isOnTime() {
        return "ON_TIME".equals(status);
    }
    
    public boolean isDelayed() {
        return "DELAYED".equals(status);
    }
    
    public boolean isCancelled() {
        return "CANCELLED".equals(status);
    }
    
    public static List<FlightEvent> findByFlightNumber(String flightNumber) {
        return find("flightNumber", flightNumber).list();
    }
    
    public static List<FlightEvent> findByStatus(String status) {
        return find("status", status).list();
    }
    
    public static List<FlightEvent> findDelayedFlights() {
        return find("status", "DELAYED").list();
    }
    
    public static List<FlightEvent> findCancelledFlights() {
        return find("status", "CANCELLED").list();
    }
    
    public static List<FlightEvent> findRecent(int limit) {
        return find("ORDER BY eventTimestamp DESC").page(0, limit).list();
    }
}