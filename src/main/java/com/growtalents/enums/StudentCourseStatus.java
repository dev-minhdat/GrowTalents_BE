package com.growtalents.enums;

public enum StudentCourseStatus {
    ENROLLED("Đang học"),
    DROPPED("Đã rút"),
    COMPLETED("Hoàn thành");

    private final String displayName;

    StudentCourseStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
