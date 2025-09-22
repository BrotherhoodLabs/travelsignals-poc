package com.brotherhoodlabs.travelsignals.entity.enums;

public enum Priority {
    P1("Critique", 1),
    P2("Important", 2),
    P3("Normal", 3);
    
    private final String displayName;
    private final int level;
    
    Priority(String displayName, int level) {
        this.displayName = displayName;
        this.level = level;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public int getLevel() {
        return level;
    }
    
    public boolean isHigherThan(Priority other) {
        return this.level < other.level;
    }
}
