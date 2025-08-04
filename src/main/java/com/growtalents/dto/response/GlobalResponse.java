package com.growtalents.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalResponse<T> {
    private int status;
    private String message;
    private T data;
    private Instant timestamp;

    public static <T> GlobalResponse<T> success(T data) {
        return new GlobalResponse<>(200, "Success", data, Instant.now());
    }

    public static <T> GlobalResponse<T> success(String message, T data) {
        return new GlobalResponse<>(200, message, data, Instant.now());
    }

    public static <T> GlobalResponse<T> error(String msg, int code) {
        return new GlobalResponse<>(code, msg, null, Instant.now());
    }
}
