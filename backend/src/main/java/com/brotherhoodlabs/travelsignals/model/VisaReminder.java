package com.brotherhoodlabs.travelsignals.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public class VisaReminder {
    @JsonProperty("country")
    public String country;
    
    @JsonProperty("daysBefore")
    public int daysBefore;
    
    @JsonProperty("message")
    public String message;
    
    @JsonProperty("ts")
    public Instant ts;

    public VisaReminder() {}

    public VisaReminder(String country, int daysBefore, String message) {
        this.country = country;
        this.daysBefore = daysBefore;
        this.message = message;
        this.ts = Instant.now();
    }
}
