package com.growtalents.enums;

public enum UserStatus {
    ACTIVE("Hoạt động"),
    FROZEN("Đóng băng"),
    BANNED("Cấm");

    private final String displayName;

    UserStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
