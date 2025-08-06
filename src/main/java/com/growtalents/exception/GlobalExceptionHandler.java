package com.growtalents.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.growtalents.dto.response.GlobalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Validate DTO
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
    // 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<GlobalResponse<Void>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                GlobalResponse.error(ex.getMessage(), 404)
        );
    }
    // 400
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<GlobalResponse<Void>> handleBadRequest(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                GlobalResponse.error(ex.getMessage(), 400)
        );
    }
    // 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponse<Void>> handleAllOtherExceptions(Exception ex) {
        ex.printStackTrace(); // debug log nếu cần
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                GlobalResponse.error("Unexpected error: " + ex.getMessage(), 500)
        );
    }
    // Lỗi định dạng ngày
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GlobalResponse<Void>> handleInvalidFormat(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException || cause instanceof DateTimeParseException || ex.getMessage().contains("LocalDate")) {
            return ResponseEntity.badRequest().body(
                    GlobalResponse.error("Invalid date format. Expected format: dd/MM/yyyy (e.g. 20/08/2025)", 400)
            );
        }

        return ResponseEntity.badRequest().body(
                GlobalResponse.error("Malformed JSON request", 400)
        );
    }

}

