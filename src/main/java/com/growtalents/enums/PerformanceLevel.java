package com.growtalents.enums;

public enum PerformanceLevel {
    EXCELLENT("Xuất sắc"),
    GOOD("Tốt"),
    FAIR("Khá"),
    AVERAGE("Trung bình");

    private final String displayName;

    PerformanceLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

