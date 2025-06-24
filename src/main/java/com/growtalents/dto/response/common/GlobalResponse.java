package com.growtalents.dto.response.common;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class GlobalResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private Map<String, String> errors;
}
