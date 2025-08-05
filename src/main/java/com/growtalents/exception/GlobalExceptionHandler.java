package com.growtalents.exception;

import com.growtalents.dto.response.GlobalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Xử lý lỗi validate DTO (NotBlank, Email,...)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponse<Void>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(
                GlobalResponse.error("Validation Failed", 400, errors)
        );
    }

    // Xử lý các lỗi Runtime như không tìm thấy bản ghi
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GlobalResponse<Void>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                GlobalResponse.error(ex.getMessage(), 400)
        );
    }

    // Bắt fallback cho các lỗi khác
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponse<Void>> handleAllOtherExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                GlobalResponse.error("Unexpected error: " + ex.getMessage(), 500)
        );
    }
}
