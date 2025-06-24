package com.growtalents.enums;

public enum CourseType {
    MATH("Toán"),
    PHYSICS("Vật lý"),
    CHEMISTRY("Hóa học"),
    LITERATURE("Ngữ văn"),
    ENGLISH("Tiếng Anh");

    private final String displayName;

    CourseType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
