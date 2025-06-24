package com.growtalents.enums;

public enum AssignmentType {
    MULTIPLE_CHOICE("Trắc nghiệm"),
    ESSAY("Tự luận");

    private final String displayName;

    AssignmentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
