package com.brotherhoodlabs.travelsignals.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public class PriceUpdate {
    @JsonProperty("destination")
    public String destination;
    
    @JsonProperty("provider")
    public String provider;
    
    @JsonProperty("oldPrice")
    public double oldPrice;
    
    @JsonProperty("newPrice")
    public double newPrice;
    
    @JsonProperty("currency")
    public String currency;
    
    @JsonProperty("ts")
    public Instant ts;

    public PriceUpdate() {}

    public PriceUpdate(String destination, String provider, double oldPrice, double newPrice, String currency) {
        this.destination = destination;
        this.provider = provider;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.currency = currency;
        this.ts = Instant.now();
    }

    public double getPriceChangePercentage() {
        return ((newPrice - oldPrice) / oldPrice) * 100;
    }
}
