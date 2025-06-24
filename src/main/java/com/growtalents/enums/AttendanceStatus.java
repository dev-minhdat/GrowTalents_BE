package com.growtalents.enums;

public enum AttendanceStatus {
    PRESENT("Có mặt"),
    LATE("Đi trễ"),
    ABSENT("Vắng không phép"),
    PERMITTED_ABSENCE("Vắng có phép");

    private final String displayName;

    AttendanceStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
