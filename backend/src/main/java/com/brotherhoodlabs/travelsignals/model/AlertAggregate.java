package com.brotherhoodlabs.travelsignals.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.Map;

public class AlertAggregate {
    @JsonProperty("type")
    public String type; // PRICE, WEATHER, FLIGHT, VISA
    
    @JsonProperty("destination")
    public String destination;
    
    @JsonProperty("priority")
    public String priority; // P1, P2, P3
    
    @JsonProperty("title")
    public String title;
    
    @JsonProperty("details")
    public Map<String, Object> details;
    
    @JsonProperty("ts")
    public Instant ts;

    public AlertAggregate() {}

    public AlertAggregate(String type, String destination, String priority, String title, Map<String, Object> details) {
        this.type = type;
        this.destination = destination;
        this.priority = priority;
        this.title = title;
        this.details = details;
        this.ts = Instant.now();
    }
    
    public AlertAggregate(String type, String destination, String priority, String title, Map<String, Object> details, Instant ts) {
        this.type = type;
        this.destination = destination;
        this.priority = priority;
        this.title = title;
        this.details = details;
        this.ts = ts;
    }
}
