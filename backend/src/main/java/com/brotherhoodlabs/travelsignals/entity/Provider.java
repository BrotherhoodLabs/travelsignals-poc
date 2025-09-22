package com.brotherhoodlabs.travelsignals.entity;

import com.brotherhoodlabs.travelsignals.entity.enums.ProviderType;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "providers")
public class Provider extends PanacheEntity {
    
    @Column(name = "name", unique = true, nullable = false, length = 100)
    public String name;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    public ProviderType type;
    
    @Column(name = "website")
    public String website;
    
    @Column(name = "created_at")
    public LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<PriceEvent> priceEvents;
    
    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
    
    public static List<Provider> findByName(String name) {
        return find("name", name).list();
    }
    
    public static List<Provider> findByType(ProviderType type) {
        return find("type", type).list();
    }
}