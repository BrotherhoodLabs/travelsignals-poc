package com.brotherhoodlabs.travelsignals.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public class WeatherAlert {
    @JsonProperty("destination")
    public String destination;
    
    @JsonProperty("level")
    public String level; // GREEN, YELLOW, RED
    
    @JsonProperty("message")
    public String message;
    
    @JsonProperty("ts")
    public Instant ts;

    public WeatherAlert() {}

    public WeatherAlert(String destination, String level, String message) {
        this.destination = destination;
        this.level = level;
        this.message = message;
        this.ts = Instant.now();
    }

    public boolean isRedAlert() {
        return "RED".equals(level);
    }
}
