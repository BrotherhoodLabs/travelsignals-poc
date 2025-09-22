package com.brotherhoodlabs.travelsignals.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "price_events")
public class PriceEvent extends PanacheEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id", nullable = false)
    public Destination destination;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    public Provider provider;
    
    @Column(name = "old_price", nullable = false, precision = 10, scale = 2)
    public BigDecimal oldPrice;
    
    @Column(name = "new_price", nullable = false, precision = 10, scale = 2)
    public BigDecimal newPrice;
    
    @Column(name = "currency", length = 3, nullable = false)
    public String currency = "EUR";
    
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
    
    public BigDecimal getPriceChangePercentage() {
        if (oldPrice == null || newPrice == null || oldPrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return newPrice.subtract(oldPrice).divide(oldPrice, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }
    
    public static List<PriceEvent> findByDestination(Long destinationId) {
        return find("destination.id", destinationId).list();
    }
    
    public static List<PriceEvent> findByProvider(Long providerId) {
        return find("provider.id", providerId).list();
    }
    
    public static List<PriceEvent> findRecent(int limit) {
        return find("ORDER BY eventTimestamp DESC").page(0, limit).list();
    }
}