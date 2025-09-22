package com.brotherhoodlabs.travelsignals.entity.enums;

public enum AlertType {
    PRICE("Prix"),
    WEATHER("Météo"),
    FLIGHT("Vol"),
    VISA("Visa");
    
    private final String displayName;
    
    AlertType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
