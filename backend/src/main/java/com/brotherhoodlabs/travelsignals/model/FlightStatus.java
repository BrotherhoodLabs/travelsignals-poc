package com.brotherhoodlabs.travelsignals.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public class FlightStatus {
    @JsonProperty("flightNo")
    public String flightNo;
    
    @JsonProperty("status")
    public String status; // ON_TIME, DELAYED, CANCELLED
    
    @JsonProperty("departure")
    public String departure;
    
    @JsonProperty("arrival")
    public String arrival;
    
    @JsonProperty("ts")
    public Instant ts;

    public FlightStatus() {}

    public FlightStatus(String flightNo, String status, String departure, String arrival) {
        this.flightNo = flightNo;
        this.status = status;
        this.departure = departure;
        this.arrival = arrival;
        this.ts = Instant.now();
    }
}
