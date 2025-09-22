package com.brotherhoodlabs.travelsignals.entity.enums;

public enum ProviderType {
    AIRLINE("Compagnie aérienne"),
    HOTEL("Hôtel"),
    ACTIVITY("Activité"),
    TRANSPORT("Transport");
    
    private final String displayName;
    
    ProviderType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
