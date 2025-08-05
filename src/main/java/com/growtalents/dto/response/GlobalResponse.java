package com.growtalents.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GlobalResponse<T> {
    private int status;
    private boolean success;
    private String message;
    private T data;
    private Object errors; // Có thể là Map, List, String, etc.
    private Instant timestamp;

    // Trả về thành công mặc định
    public static <T> GlobalResponse<T> success(T data) {
        return GlobalResponse.<T>builder()
                .status(200)
                .success(true)
                .message("Success")
                .data(data)
                .timestamp(Instant.now())
                .build();
    }

    public static <T> GlobalResponse<T> success(String message, T data) {
        return GlobalResponse.<T>builder()
                .status(200)
                .success(true)
                .message(message)
                .data(data)
                .timestamp(Instant.now())
                .build();
    }

    public static <T> GlobalResponse<T> error(String msg, int code) {
        return GlobalResponse.<T>builder()
                .status(code)
                .success(false)
                .message(msg)
                .timestamp(Instant.now())
                .build();
    }

    public static <T> GlobalResponse<T> error(String msg, int code, Object errors) {
        return GlobalResponse.<T>builder()
                .status(code)
                .success(false)
                .message(msg)
                .errors(errors)
                .timestamp(Instant.now())
                .build();
    }
}
