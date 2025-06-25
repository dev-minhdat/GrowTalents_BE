package com.growtalents.enums;

public enum UserCourseRole {
    ADMIN("Quản trị viên"),
    TEACHER("Giáo viên"),
    ASSISTANT("Trợ giảng"),
    ACCOUNTANT("Kế toán"),
    STUDENT("Học sinh");
    private final String displayName;

    UserCourseRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
