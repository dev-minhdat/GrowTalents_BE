package com.growtalents.enums;

public enum SemesterStatus {
    ONGOING("Đang diễn ra"),
    UPCOMING("Sắp tới"),
    FINISHED("Đã kết thúc");

    private final String displayName;

    SemesterStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
