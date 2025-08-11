package com.growtalents.enums;

public enum DocumentType {
    LECTURE("Bài giảng"),
    REFERENCE("Tài liệu tham khảo");


    private final String displayName;

    DocumentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
